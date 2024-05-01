from metaflow import FlowSpec, step, Parameter, conda
import torch
from torch.utils.data import Dataset, DataLoader
import pandas as pd
from transformers import BertTokenizer, BertForSequenceClassification
from sklearn.preprocessing import LabelEncoder
from sklearn.model_selection import train_test_split

class ComplaintDataset(Dataset):
    def __init__(self, data, tokenizer, max_len):
        self.data = data
        self.tokenizer = tokenizer
        self.max_len = max_len

    def __len__(self):
        return len(self.data)

    def __getitem__(self, index):
        text = str(self.data.iloc[index]['complain'])
        label = self.data.iloc[index]['severity']

        encoding = self.tokenizer.encode_plus(
            text,
            add_special_tokens=True,
            max_length=self.max_len,
            return_token_type_ids=False,
            padding='max_length',
            truncation=True,
            return_attention_mask=True,
            return_tensors='pt',
        )

        return {
            'input_ids': encoding['input_ids'].flatten(),
            'attention_mask': encoding['attention_mask'].flatten(),
            'label': torch.tensor(label, dtype=torch.long)
        }

# @conda(libraries={'transformers': '4.11.3', 'torch': '1.10.0', 'pandas': '1.3.3'})
class TrainBertModel(FlowSpec):
    data_file = Parameter(name = 'data', default='complaints.csv')

    @step
    def start(self):
        # Load the dataset
        self.data = pd.read_csv(self.data_file)

        # Encode the target labels
        le = LabelEncoder()
        self.data['severity'] = le.fit_transform(self.data['severity'])

        # Define the tokenizer
        self.tokenizer = BertTokenizer.from_pretrained('bert-base-uncased')

        self.next(self.split_data)

    @step
    def split_data(self):
        # Split the data into train and validation sets
        self.train_data, self.val_data = train_test_split(self.data, test_size=0.2, random_state=42)

        self.next(self.load_model)

    @step
    def load_model(self):
        # Load the pre-trained BERT model and set up the classification head
        self.model = BertForSequenceClassification.from_pretrained('bert-base-uncased', num_labels=len(set(self.data['severity'])))

        # Freeze all layers except the last two
        for param in self.model.parameters():
            param.requires_grad = False
        for param in self.model.bert.pooler.parameters():
            param.requires_grad = True

        self.next(self.train_model)

    @step
    def train_model(self):
        # Define the datasets
        max_len = 512
        train_dataset = ComplaintDataset(self.train_data, self.tokenizer, max_len)
        val_dataset = ComplaintDataset(self.val_data, self.tokenizer, max_len)

        # Create data loaders
        train_loader = DataLoader(train_dataset, batch_size=16, shuffle=True)
        val_loader = DataLoader(val_dataset, batch_size=16)

        # Define the loss function and optimizer
        criterion = torch.nn.CrossEntropyLoss()
        optimizer = torch.optim.Adam(filter(lambda p: p.requires_grad, self.model.parameters()), lr=2e-5)

        # Training loop
        for epoch in range(3):
            self.model.train()
            train_loss = 0.0
            for batch in train_loader:
                optimizer.zero_grad()

                input_ids = batch['input_ids']
                attention_mask = batch['attention_mask']
                labels = batch['label']

                outputs = self.model(input_ids, attention_mask=attention_mask, labels=labels)
                loss = outputs.loss
                loss.backward()
                optimizer.step()

                train_loss += loss.item()

            print(f'Epoch {epoch+1}/{3}, Train Loss: {train_loss/len(train_loader):.4f}')

        self.trained_model = self.model.state_dict()
        self.next(self.end)

    @step
    def end(self):
        # Save the trained model
        torch.save(self.trained_model, 'trained_model.pth')

if __name__ == '__main__':
    TrainBertModel() 

