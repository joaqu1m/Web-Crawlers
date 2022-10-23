from json import loads
from time import sleep
from urllib3 import PoolManager


def conversor(valor):
    return float(valor[0:4].replace(",", '.'))

with PoolManager() as pool:
    while True:
        response = pool.request('GET', 'http://localhost:8085/data.json')
        data = loads(response.data.decode('utf-8'))
        print("\r\n\r\n\r\n\r\n")
        print(data['Children'][0])
        #print(data['Children'][0]['Children'][1])
        sleep(1)
