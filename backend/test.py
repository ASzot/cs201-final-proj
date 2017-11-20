# Automatically test backend endpoints

import requests

HOST = "localhost"
PORT = 8060


def post_endpoint(endpoint, params):
    res = requests.post("http://" + HOST + ":" + str(PORT) + endpoint,
            params=params)
    print(res)
    print(res.text)

def get_endpoint(endpoint):
    res = requests.get("http://" + HOST + ":" + str(PORT) + endpoint )
    print(res)
    print(res.text[0:1000])

if __name__ == "__main__":
	#print("Day candles, yesterday to today")
    ##error if period length does not evenly divide begin-> end / is more than it -- ie 86399 and 86401 dont work
	#get_endpoint("/exchange/candle?fromCur=btc&toCur=usd&period=86400&begin=11/16/2017&end=11/17/2017")

	#print("day length candles, one week")
	#get_endpoint("/exchange/candle?fromCur=btc&toCur=usd&period=86400&begin=11/10/2017&end=11/17/2017")

	#print("day length candles, 2 week")
	#get_endpoint("/exchange/candle?fromCur=btc&toCur=usd&period=86400&begin=11/03/2017&end=11/17/2017")

	#print("day length candles, one month")
	#get_endpoint("/exchange/candle?fromCur=btc&toCur=usd&period=86400&begin=10/17/2017&end=11/17/2017")

	#print("day length candles, 6 months")
	#get_endpoint("/exchange/candle?fromCur=btc&toCur=usd&period=86400&begin=05/17/2017&end=11/17/2017")

	#print("day length candles, 1 year")
	#get_endpoint("/exchange/candle?fromCur=btc&toCur=usd&period=86400&begin=11/17/2016&end=11/17/2017")

	#print("market data history")
	#get_endpoint("/exchange/historicalData?fromCur=dot&toCur=btc")
	
    #post_endpoint("/user/create", {
    #        'username': 'andy',
    #        'password': 'asdf'
    #    })

	## Moving average tests for 1 month
	#print("Moving average test, 1 month")
	#get_endpoint("/exchange/candle?fromCur=btc&toCur=usd&period=86400&begin=1508198400&end=1510876800")

	#print("moving averages, 1 month")
	#get_endpoint("/exchange/movingAverage?interval=30&fromCur=btc&toCur=usd")
	
	## Moving average tests for 6 months
	#print("Moving average test, 6 months")
	#get_endpoint("/exchange/candle?fromCur=btc&toCur=usd&exchange=gdax&period=86400&begin=1495152000&end=1511121015")

	print("moving averages, 6 months")
	get_endpoint("/exchange/movingAverage?interval1=30&interval2=40&interval3=50&exchange=gdax&duration=5259486&fromCur=btc&toCur=usd")
	
	print("moving averages, 1 year")
	get_endpoint("/exchange/movingAverage?interval1=30&interval2=40&interval3=50&exchange=gdax&duration=10518972&fromCur=btc&toCur=usd")
	#print("ERROR CASE")
	#get_endpoint("/exchange/movingAverage?interval1=30&interval2=40&interval3=50&exchange=gdax&duration=10&fromCur=btc&toCur=usd")
	
	#print("market summary: BTC USD")
	#get_endpoint("/exchange/summary?fromCur=btc&toCur=usd&exchange=gdax")
	
	
	#get_endpoint("/exchange/data?fromCur=DOT&toCur=BTC&time=24")
	#print("Minute candles, 4 hours, yesterday") OLD IMPLEMENTATION
	#get_endpoint("/exchange/candle?fromCur=btc&toCur=usd&period=60&begin=1510934400&end=1510948800")

	#res = requests.get("https://api.cryptowat.ch/markets/gdax/btcusd/ohlc?before=1511049600&after=1510444800&periods=604800")
	#print(res.text)
	


