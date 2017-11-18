package com.cv.model;

//Cryptopia API
public class TradePoint extends TimeSeriesPoint{
  
  private int tradePairId;
  private String label;
  private String type;
  private double price;
  private double amount;
  private double total;
  
  public TradePoint(long timestamp, int tradePairId, String label, String type, double price, double amount, double total) {
    super(timestamp);
    this.tradePairId = tradePairId;
    this.label = label;
    this.type = type;
    this.price = price;
    this.amount = amount;
    this.total = total;
  }
  
//  @SerializedName("TradePairId")
//  private int tradePairId;
//
//  @SerializedName("Label")
//  private String label;
//
//  @SerializedName("Type")
//  private String type;
//
//  @SerializedName("Price")
//  private double price;
//
//  @SerializedName("Amount")
//  private double amount;
//
//  @SerializedName("Total")
//  private double total;
//
//  @SerializedName("Timestamp")
//  private long timestamp;

  private String timeStr;

  public void setTimeStr(String timeStr) {
    this.timeStr = timeStr;
  }

  public String getTimeStr() {
    return this.timeStr;
  }

  public int getTradePairId() {
    return tradePairId;
  }

  public String getLabel() {
    return label;
  }

  public String getType() {
    return type;
  }

  public double getPrice() {
    return price;
  }

  public double getAmount() {
    return amount;
  }

  public double getTotal() {
    return total;
  }

}
