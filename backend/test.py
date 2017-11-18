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
    print(res.text[0:1000])

if __name__ == "__main__":
    #get_endpoint("/exchange/data?fromCur=DOT&toCur=BTC&time=24")
	print("Minute candles, 4 hours, yesterday")
	get_endpoint("/exchange/candle?fromCur=btc&toCur=usd&period=60&begin=1510934400&end=1510948800")

	print("Day candles, yesterday to today")
    #error if period length does not evenly divide begin-> end / is more than it -- ie 86399 and 86401 dont work
	get_endpoint("/exchange/candle?fromCur=btc&toCur=usd&period=86400&begin=1510790400&end=1510876800")

	print("day length candles, one week")
	get_endpoint("/exchange/candle?fromCur=btc&toCur=usd&period=86400&begin=1510272000&end=1510876800")
    
	print("day length candles, 2 week")
	get_endpoint("/exchange/candle?fromCur=btc&toCur=usd&period=86400&begin=1509667200&end=1510876800")
    
	print("day length candles, one month")
	get_endpoint("/exchange/candle?fromCur=btc&toCur=usd&period=86400&begin=1507593600&end=1510876800")
    
	print("day length candles, 6 months")
	get_endpoint("/exchange/candle?fromCur=btc&toCur=usd&period=86400&begin=1493769600&end=1510876800")
	
	print("day length candles, 1 year")
	get_endpoint("/exchange/candle?fromCur=btc&toCur=usd&period=86400&begin=1478131200&end=1510876800")
	
	
	
	
	