<template>
  <div>
    <v-data-table
      v-bind:headers="headers"
      :items="items"
      hide-actions
      class="elevation-1">
      <template slot="items" slot-scope="props">
        <td>{{ props.item.transactionType }}</td>
        <td>{{ props.item.exchange }}</td>
        <td class="text-xs-right">{{ props.item.price }}</td>
        <td class="text-xs-right">{{ props.item.quantity }}</td>
        <td class="text-xs-right">{{ props.item.total }}</td>
      </template>
    </v-data-table>
  </div>
</template>

<script>
export default {
  props: ['fromCurrency'],
  data () {
    return {
      socket: null,
      coinMessage: "",
      maxSize: 10,
      items: [],
      headers: [
        {
          text: 'Transaction Type',
          align: 'left',
          sortable: false,
          value: 'transactionType'
        },
        {
          text: 'Exchange',
          align: 'left',
          sortable: false,
          value: 'exchange'
        },
        {
          text: 'Price',
          sortable: false,
          value: 'price'
        },
        {
          text: 'Quantity',
          sortable: false,
          value: 'quantity'
        },
        {
          text: 'Total',
          sortable: false,
          value: 'total'
        }
      ]
    }
  },
  methods: {
    addSubscriptionCurrency: function () {
      var useFromCurrency = this.fromCurrency.toUpperCase();
      var exchangesList = ["Cryptsy", "Bitstamp", "OKCoin", "Coinbase", "Poloniex", "Cexio", "BTCE", "BitTrex", "Kraken", "Bitfinex", "LocalBitcoins", 
						"itBit", "HitBTC", "Coinfloor", "Huobi", "LakeBTC", "Coinsetter", "CCEX", "MonetaGo", "Gatecoin", "Gemini", "CCEDK", "Exmo", 
						"Yobit", "BitBay", "QuadrigaCX", "BitSquare", "TheRockTrading", "Quoine", "LiveCoin", "WavesDEX", "Lykke", "Remitano", 
						"Coinroom", "Abucoins"];
      var subscriptionList = [];
      var subscriptionID = 0; // 0 as subscriptionID means trade data
      var toCurrency = "USD";
      for (var i = 0; i < exchangesList.length; i++) {
        var subscription = subscriptionID + "~" + exchangesList[i] + "~" + useFromCurrency + "~" + toCurrency;
        subscriptionList.push(subscription);
      }

      this.socket.emit('SubAdd', { subs: subscriptionList });
    },
    addToCoinUpdates: function (parsedMessage) {
      var toCur = CCC.STATIC.CURRENCY.getSymbol("USD");
      
      var from = parsedMessage['F'];
      var transactionType = "";
      if (from == 1) 
        transactionType = "BUY";
      else if (from == 2) 
        transactionType = "SELL";
      else 
        transactionType == "UNKNOWN";
      
      var exchange = parsedMessage['M'];
      var price = parsedMessage['P'];
      var quantity = parsedMessage['Q'];
      var total = parsedMessage['TOTAL'];
      
      var priceString = CCC.convertValueToDisplay(toCur, price);
      var quantityString = CCC.convertValueToDisplay("", quantity);
      var totalString = CCC.convertValueToDisplay(toCur, total);

      if (exchange == "LOADCOMPLETE") {
        return;
      }
      
      var message = {
        transactionType: transactionType,
        exchange: exchange,
        price: priceString,
        quantity: quantityString,
        total: totalString,
        value: false
      };

      this.items.push(message);

      if (this.items.length > this.maxSize) {
        this.items.shift();
      }
    }
  },
  mounted: function () {
    this.socket = io.connect('https://streamer.cryptocompare.com/');
    var _this = this;
    this.socket.on("m", function(message) {
      var parsedMessage = CCC.TRADE.unpack(message);
      _this.addToCoinUpdates(parsedMessage);
    });
    this.addSubscriptionCurrency();
  }

}
</script>
