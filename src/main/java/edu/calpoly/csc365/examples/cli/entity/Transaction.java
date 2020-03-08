package edu.calpoly.csc365.examples.cli.entity;

import java.sql.Date;

public class Transaction {
  private Integer id;
  private Integer customerId;
  private Integer cardNumber;
  private Date date;
  private Double amount;

  public Transaction() {
    this.id = null;
    this.customerId = null;
    this.cardNumber = null;
    this.date = null;
    this.amount = null;
  }

  public Transaction(Integer id, Integer customerId, Integer cardNumber, Date date, Double amount) {
    this.id = id;
    this.customerId = customerId;
    this.cardNumber = cardNumber;
    this.date = date;
    this.amount = amount;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Integer customerId) {
    this.customerId = customerId;
  }

  public Integer getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(Integer cardNumber) {
    this.cardNumber = cardNumber;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }
}
