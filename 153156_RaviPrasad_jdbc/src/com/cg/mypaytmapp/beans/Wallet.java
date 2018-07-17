package com.cg.mypaytmapp.beans;

import java.math.BigDecimal;

public class Wallet {
private BigDecimal balance;//field

public Wallet(BigDecimal amount) {//constructor
	this.balance=amount;
}
public Wallet()
{
	super();
}

public BigDecimal getBalance() {//getters and setters
	return balance;
}

public void setBalance(BigDecimal balance) {
	this.balance = balance;
}

@Override//toString
	public String toString() {
	return balance+"\n";
}

}
