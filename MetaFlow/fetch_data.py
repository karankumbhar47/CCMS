import pymongo
from dotenv import load_dotenv
import os
import csv
import sys

def save_complaints_to_csv(csv_file_path):
    # Load environment variables from .env file
    load_dotenv()

    # Retrieve MongoDB connection details from environment variables
    mongo_database = os.getenv('MONGO_DATABASE')
    mongo_user = os.getenv('MONGO_USER')
    mongo_password = os.getenv('MONGO_PASSWORD')
    mongo_cluster = os.getenv('MONGO_CLUSTER')

    # Construct MongoDB connection URI
    mongo_uri = f'mongodb+srv://{mongo_user}:{mongo_password}@{mongo_cluster}/{mongo_database}?retryWrites=true&w=majority'

    # Establish connection to MongoDB
    client = pymongo.MongoClient(mongo_uri)

    # Access the Complaint collection
    db = client[mongo_database]
    complaint_collection = db['Complaints']

    # Fetch data from the Complaint collection
    complaints_cursor = complaint_collection.find()

    # Write complaint data to CSV file
    with open(csv_file_path, 'w', newline='', encoding='utf-8') as csvfile:
        fieldnames = ['complain', 'severity']
        writer = csv.DictWriter(csvfile, fieldnames=fieldnames)

        # Write header row
        writer.writeheader()

        # Iterate over the complaints and write data to CSV
        for complaint in complaints_cursor:
            writer.writerow({'complain': complaint.get('description', ''), 'severity': complaint.get('priority', '')})

    print(f"Complaints data saved to {csv_file_path}")

if __name__ == "__main__":
    if len(sys.argv) == 2:
        csv_file_path = sys.argv[1]
    else:
        csv_file_path = "complaints.csv"
    
    save_complaints_to_csv(csv_file_path)