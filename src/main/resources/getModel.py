import os
import requests
import json
import re

# 读取 ModelList.json 文件
with open('ModelList.json', 'r', encoding='utf-8') as file:
    data = json.load(file)

# 确保读取的数据是字典并包含 data 键
if isinstance(data, dict) and "data" in data:
    model_list = data["data"]
else:
    print("JSON 数据格式不正确")
    exit(1)

# 创建基础下载目录
base_download_dir = 'down'
if not os.path.exists(base_download_dir):
    os.makedirs(base_download_dir)

# 下载并保存文件
def download_file(url, file_name, download_dir):
    response = requests.get(url)
    if response.status_code == 200:
        file_path = os.path.join(download_dir, file_name)
        with open(file_path, 'wb') as file:
            file.write(response.content)
        print(f"文件已下载并保存到: {file_path}")
    else:
        print(f"无法下载文件: {url}")

# 处理每个条目，下载链接中的文件
for item in model_list:
    if isinstance(item, dict):
        for key, value in item.items():
            if isinstance(value, str) and value.startswith('http'):
                # 解析日期部分
                match = re.search(r'/(\d{8})/', value)
                if match:
                    date_part = match.group(1)
                    # 创建日期文件夹
                    download_dir = os.path.join(base_download_dir, date_part)
                    if not os.path.exists(download_dir):
                        os.makedirs(download_dir)
                    # 下载文件到对应文件夹
                    file_name = value.split('/')[-1]
                    download_file(value, file_name, download_dir)

print("所有文件已成功下载并保存到相应的日期文件夹中。")
