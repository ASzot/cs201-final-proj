var stompClient = null;
var socket ;


function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

//Parse message from web socket API
function addSubscriptionCurrencyBackend(message) {
	stompClient.send("/app/addSubscription", {}, message);
}

function addSubscriptionCurrency() {
	var subscriptionID = $("#subscriptionID").val();
	var exchangeName = $("#exchangeName").val();
	var fromCurrency = $("#fromCurrency").val();
	var toCurrency = $("#toCurrency").val();
	
	var subscription = subscriptionID + "~" + exchangeName + "~" + fromCurrency + "~" + toCurrency;
	var subscriptionList = [subscription];
	console.log(subscriptionList);
	socket.emit('SubAdd', { subs: subscriptionList });
	
	//send to backend to add to user's database of watched currencies
	addSubscriptionCurrencyBackend(JSON.stringify({'subscriptionID' : subscriptionID, 'exchangeName' : exchangeName, 'fromCurrency' : fromCurrency, 'toCurrency' : toCurrency}));

}

function addToCoinUpdates(parsedMessage) {
	console.log(parsedMessage);
	var fromCur = CCC.STATIC.CURRENCY.getSymbol("BTC");
	var toCur = CCC.STATIC.CURRENCY.getSymbol("USD");
	
	var from = parsedMessage['F'];
	var transactionType = "";
	if (from == 1) transactionType = "BUY";
	else if (from == 2) transactionType == "SELL";
	else transactionType == "UNKNOWN";
	
	var market = parsedMessage['M'];
	var price = parsedMessage['P'];
	var quantity = parsedMessage['Q'];
	var total = parsedMessage['TOTAL'];
	
	var priceString = CCC.convertValueToDisplay(toCur, price);
	var quantityString = CCC.convertValueToDisplay(fromCur, quantity);
	var totalString = CCC.convertValueToDisplay(toCur, total);
	
	var message = "";
	message = "Transaction type: " + transactionType + " Market: " + market + " Price: " + priceString + " Quantity: " + quantityString + " Total: " + totalString;
	console.log("before printing coin update");
	$("#coinUpdates").append(message + '\n');
}
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#sendTrade" ).click(function() { addSubscriptionCurrency(); });
	console.log("hello");

    var currentPrice = {};
	socket = io.connect('https://streamer.cryptocompare.com/');
	//Format: {SubscriptionId}~{ExchangeName}~{FromSymbol}~{ToSymbol}
	//Use SubscriptionId 0 for TRADE, 2 for CURRENT and 5 for CURRENTAGG
	//For aggregate quote updates use CCCAGG as market
	
	//Return format:
	//'{SubscriptionId}~{ExchangeName}~{FromCurrency}~{ToCurrency}~{Flag}~{Price}~{LastUpdate}~
	//{LastVolume}~{LastVolumeTo}~{LastTradeId}~{Volume24h}~{Volume24hTo}~{LastMarket}'

	socket.on("m", function(message) {
		console.log(message);
		console.log("hello");
		parsedMessage = CCC.TRADE.unpack(message);
		addToCoinUpdates(parsedMessage);
	});
    
    
});

