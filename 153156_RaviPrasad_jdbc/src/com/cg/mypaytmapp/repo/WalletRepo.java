package com.cg.mypaytmapp.repo;

import com.cg.mypaytmapp.beans.Customer;

public interface WalletRepo {

	public boolean save(Customer customer);

	public Customer findOne(String mobileNo);
	public Customer update(Customer customer);
	
}
