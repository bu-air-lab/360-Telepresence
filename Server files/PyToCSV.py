import os
import json
import csv

count = 0

for filename in os.listdir("D:\\Users\\craft\\Desktop\\Test\\Jack Tests Round 2"):
    if filename.endswith(".json"):
        with open(filename) as json_file:
            data = json.load(json_file)

        data_file = open('full_data.csv', 'a')
        csv_writer =  csv.writer(data_file)

        csv_writer.writerow(data.values())
        data_file.close()
        continue
    else:
        continue
