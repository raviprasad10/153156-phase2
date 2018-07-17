package com.cg.mypaytmapp.repo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.cg.mypaytmapp.beans.Customer;
import com.cg.mypaytmapp.beans.Wallet;
import com.cg.mypaytmapp.exception.InvalidInputException;


import java.sql.Connection;
import java.sql.DriverManager;



public class WalletRepoImpl implements WalletRepo{
	
	Connection con;
	public WalletRepoImpl() 
	{
		
		String url="jdbc:mysql://localhost:3306/test";
		String uid="root";
		String pwd="corp123";
		try{
			this.con=DriverManager.getConnection(url,uid,pwd);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private Map<String, Customer> data=new HashMap<>();
	
	public Map<String,Customer>getData(){
		return data;
	}
	 public void setData(Map<String,Customer> data) {
		 this.data=data;
	 }
	 
	 Customer cust=new Customer();

	public WalletRepoImpl(Map<String, Customer> data) {
		super();
		this.data = data;
	}
	

	public boolean save(Customer customer) {
		try{
			String query="insert into customer(name,mobileNo,balance) values(?,?,?)";
			PreparedStatement pstmt=con.prepareStatement(query); 
			pstmt.setString(1,customer.getName());
			pstmt.setString(2,customer.getMobileNo());
			pstmt.setBigDecimal(3,customer.getWallet().getBalance());
			pstmt.execute();
				}
					catch(SQLException e)
					{
						e.printStackTrace();
					}
		String mobileNo=customer.getMobileNo();
		data.put(mobileNo, customer);
		return true;
	}

	public Customer findOne(String mobileNo) {
		
		String query="select *from customer where mobileNo=?";
	 	try
		{
			
			PreparedStatement pstmt=con.prepareStatement(query);
			pstmt.setString(1, mobileNo);
			ResultSet rs=pstmt.executeQuery();

			while(rs.next())
			{
				Customer cust=new Customer();
				cust.setName(rs.getString(1));
				cust.setMobileNo(rs.getString(2));
				Wallet wallet=new Wallet();
				wallet.setBalance(rs.getBigDecimal(3));
				cust.setWallet(wallet);
				if(cust==null)
					throw new InvalidInputException("Account not found");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		cust=data.get(mobileNo);
		if(cust==null)
			throw new InvalidInputException("Account not found!");
		else
		return cust;
		
	}
	@Override
	public Customer update(Customer customer) {
		try
		{
			String query="update customer set balance=? where mobileNo=?";
			PreparedStatement pstmt=con.prepareStatement(query);
			
			pstmt.setBigDecimal(1,customer.getWallet().getBalance());
			pstmt.setString(2,customer.getMobileNo());
			
			pstmt.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return customer;
	}
	
}
