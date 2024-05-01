# BERT Model Training

This guide provides instructions for setting up a virtual environment,
installing dependencies from a requirements.txt file, and running two Python
scripts: `fetch_data.py` for fetching complaint data from MongoDB and saving it
to a CSV file, and `TrainBertModelFlow.py` for training a BERT model on the complaint
data for severity prediction.

## 1. Setting Up Virtual Environment

### Using virtualenv:

```bash
# Install virtualenv if not already installed
pip install virtualenv

# Create a new virtual environment
virtualenv venv

# Activate the virtual environment
source venv/bin/activate
```

### Using venv (Python 3.x):
```bash
# Create a new virtual environment
python3 -m venv venv

# Activate the virtual environment
source venv/bin/activate
```

## 2. Installing Dependencies
```bash
# Install dependencies from requirements.txt
pip install -r requirements.txt
```


## 3. Updating .env File

Rename the .env.example file to .env and update it with your MongoDB connection details:
```bash
MONGO_DATABASE="YourDatabaseName"
MONGO_USER="YourMongoDBUsername"
MONGO_PASSWORD="YourMongoDBPassword"
MONGO_CLUSTER="YourMongoDBCluster"
MAIL_PASSWORD="YourMailPassword"
```


## 4. Running fetch_data.py

### Usage:

```bash
python fetch_data.py [output_file_name]
``` 
[output_file_name]: Optional. Specify the filename for the output CSV file. Default filename is complaints.csv.

Example:
```bash
python fetch_data.py my_complaint_data.csv
```

## 5. Running BertModelFlow.py
### Usage:
```bash
python BertModelFlow.py --data [input_file_name]
```

[input_file_name]: Optional. Specify the filename for the input CSV file containing complaint data. Default filename is complaints.csv.

Example:
```bash
python BertModelFlow.py --data my_complaint_data.csv
```

## Note:
- Ensure that the MongoDB connection details are properly configured in the .env file.
- If no arguments are provided, default filenames will be used for both scripts.