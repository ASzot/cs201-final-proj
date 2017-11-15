# Automatically test backend endpoints

import requests

HOST = "localhost"
PORT = 8060


def post_endpoint(endpoint):
    res = requests.post("http://" + HOST + ":" + str(PORT) + endpoint )
    print(res)
    print(res.text)

def get_endpoint(endpoint):
    res = requests.get("http://" + HOST + ":" + str(PORT) + endpoint )
    print(res)
    print(res.text)

if __name__ == "__main__":
    get_endpoint("/exchange/data?fromCur=DOT&toCur=BTC&time=24")
