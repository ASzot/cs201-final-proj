package com.cv.model;

import com.google.gson.annotations.SerializedName;

public class Trade {
  @SerializedName("TradePairId")
  private int tradePairId;

  @SerializedName("Label")
  private String label;

  @SerializedName("Type")
  private String type;

  @SerializedName("Price")
  private double price;

  @SerializedName("Amount")
  private double amount;

  @SerializedName("Total")
  private double total;

  @SerializedName("Timestamp")
  private long timestamp;

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

  public long getTimestamp() {
    return timestamp;
  }
}
