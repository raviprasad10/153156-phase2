package com.cg.mypaytmapp.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

import com.cg.mypaytmapp.beans.Customer;
import com.cg.mypaytmapp.beans.Wallet;
import com.cg.mypaytmapp.exception.InsufficientBalanceException;
import com.cg.mypaytmapp.exception.InvalidInputException;
import com.cg.mypaytmapp.repo.WalletRepo;
import com.cg.mypaytmapp.repo.WalletRepoImpl;

public class WalletServiceImpl implements WalletService{

	private WalletRepo repo;

	public WalletServiceImpl(Map<String, Customer> data){
		repo= new WalletRepoImpl(data);
	}
	public WalletServiceImpl(WalletRepo repo) {
		super();
		this.repo = repo;
	}

	public WalletServiceImpl() {
		repo=new WalletRepoImpl();
	}

	public boolean validatephone(String mobileno) {

		String pattern1="[7-9]?[0-9]{10}";
		if(mobileno.matches(pattern1))
		{
			return true;
		}else {
			return false;
		}
	}
	public boolean validateName(String cName) {
		String pattern="[A-Z][a-zA-Z]*";
		if(cName.matches(pattern))
		{
			return true;
		}else {
			return false;
		}
	}

	WalletRepoImpl obj=new WalletRepoImpl();
	public Customer createAccount(String name, String mobileNo, BigDecimal amount)throws InvalidInputException,InsufficientBalanceException
	{

		Customer customer=new Customer(name,mobileNo,new Wallet(amount));
		acceptCustomerDetails(customer);
		boolean result=repo.save(customer);
		repo.update(customer);
		if(result==true)
			return customer;
		else
			return null;
		//create an object of customer and call dao save layer

	}

	public Customer showBalance(String mobileNo) {
		Customer customer=repo.findOne(mobileNo);
		if(customer!=null)
			return customer;
		else
			throw new InvalidInputException("Invalid mobile no ");
	}

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) throws InvalidInputException,InsufficientBalanceException{
		Customer source=new Customer();
		Customer target=new Customer();
		Wallet sw=new Wallet();
		Wallet tw=new Wallet();
		source=repo.findOne(sourceMobileNo);
		target=repo.findOne(targetMobileNo);

		if(source!=null && target!=null)
		{
			BigDecimal amtsub=source.getWallet().getBalance();
			BigDecimal diff=amtsub.subtract(amount);
			sw.setBalance(diff);
			source.setWallet(sw);
			repo.update(source);

			BigDecimal amtAdd=target.getWallet().getBalance();
			BigDecimal sum=amtAdd.add(amount);
			tw.setBalance(sum);
			target.setWallet(tw);
			repo.update(target);
			obj.getData().put(targetMobileNo, target);
			obj.getData().put(sourceMobileNo, source);
		}
		else
		{

		}
		return target;
	}

	public Customer depositAmount(String mobileNo, BigDecimal amount)throws InvalidInputException,InsufficientBalanceException {

		Customer customer=new Customer();
		Wallet wallet=new Wallet();

		customer=repo.findOne(mobileNo);
		if(customer!=null)
		{
			BigDecimal amtAdd=customer.getWallet().getBalance().add(amount);
			wallet.setBalance(amtAdd);
			customer.setWallet(wallet);
			obj.getData().put(mobileNo, customer);
			repo.update(customer);

		}

		return customer;
	}

	public Customer withdrawAmount(String mobileNo, BigDecimal amount)throws InvalidInputException,InsufficientBalanceException {
		Customer customer=new Customer();
		Wallet wallet=new Wallet();

		customer=repo.findOne(mobileNo);
		if(customer!=null)
		{
			BigDecimal amtSub=customer.getWallet().getBalance().subtract(amount);
			wallet.setBalance(amtSub);
			customer.setWallet(wallet);
			obj.getData().put(mobileNo, customer);
			repo.update(customer);
		}
		return customer;

	}
	public void acceptCustomerDetails(Customer customer) throws InvalidInputException,InsufficientBalanceException {
		Scanner sc=new Scanner(System.in);
		while (true) {
			String str=customer.getMobileNo();
			if(validatephone(str))//method validate name
			{

				break;
			}
			else
			{
				System.err.println("Wrong Phone number!!\n Please Start with 9 ");
				System.out.println("Enter Phone number Again eg:9876543210");
				customer.setMobileNo(sc.next());
			}
		}
		while (true) {
			String str1=customer.getName();
			if(validateName(str1))//method validate name
			{
				break;
			}
			else
			{
				System.err.println("Wrong  Name!!\n Please Start with Capital letter ");
				System.out.println("Enter  Name Again eg:Name");
				customer.setName(sc.next());
			}
		}
	}

}
