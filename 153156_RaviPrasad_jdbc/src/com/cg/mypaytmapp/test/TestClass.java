package com.cg.mypaytmapp.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cg.mypaytmapp.beans.Customer;
import com.cg.mypaytmapp.beans.Wallet;
import com.cg.mypaytmapp.exception.InsufficientBalanceException;
import com.cg.mypaytmapp.exception.InvalidInputException;
import com.cg.mypaytmapp.service.WalletService;
import com.cg.mypaytmapp.service.WalletServiceImpl;
import org.junit.Before;
public class TestClass {
	WalletService service;
	Customer cust1,cust2,cust3,cust4;	
	@Before
	public void initData(){
		Map<String,Customer> data= new HashMap<String, Customer>();
		cust1=new Customer("Amit", "9900112212",new Wallet(new BigDecimal(9000)));
		cust2=new Customer("Ajay", "9963242422",new Wallet(new BigDecimal(6000)));
		cust3=new Customer("Yogini", "9922950519",new Wallet(new BigDecimal(7000)));
		cust4=new Customer("RaviPrasad","8121101096",new Wallet(new BigDecimal(8000)));


		data.put("9900112212", cust1);
		data.put("9963242422", cust2);	
		data.put("9922950519", cust3);	
		data.put("8121101096", cust4);
		service= new WalletServiceImpl(data);

	}
	@Test(expected=NullPointerException.class)
	public void testCreateAccount() {

		service.createAccount(null,null,null);


	}
	@Test
	public void testCreateAccount1() {


		
		cust1=service.createAccount("Apps","9900112125",new BigDecimal(7000));
		
		assertEquals("Apps", cust1.getName());
		

	}
	
	@Test	
	public void testCreateAccount2() {



		Customer cust=new Customer();
		cust=service.createAccount("Amit","9900112213",new BigDecimal(7000));
		assertEquals("Amit", cust.getName());



	}
	@Test
	public void testCreateAccount3() {

		Customer cust=new Customer();
		cust=service.createAccount("Amit","9900112213",new BigDecimal(7000));
		assertEquals("9900112213", cust.getMobileNo());



	}


	@Test(expected=InvalidInputException.class)
	public void testShowBalance() {
		Customer cust=new Customer();
		cust=service.showBalance("9579405744");

	}

	@Test
	public void testShowBalance2() {
		cust1=service.createAccount("Vaishnavi", "8179793299", new BigDecimal(10000));
		cust1=service.showBalance("8179793299");
		assertEquals(new BigDecimal(10000), cust1.getWallet().getBalance());

	}


	@Test
	public void testShowBalance3() {

		cust1=service.createAccount("Teju", "9963242410", new BigDecimal(6000));
		cust1=service.showBalance("9963242410");
		BigDecimal actual=cust1.getWallet().getBalance();
		BigDecimal expected=new BigDecimal(6000);
		assertEquals(expected, actual);

	}

	@Test(expected=IllegalArgumentException.class)
	public void testFundTransfer() {
		service.fundTransfer(null, null,new BigDecimal(7000));
	}

	@Test
	public void testFundTransfer2() {
		cust2=service.createAccount("Vyshnavi", "8179793280", new BigDecimal(8000));
		cust1=service.createAccount("Ram", "9963242421", new BigDecimal(2000));   


		cust1=service.fundTransfer("8179793280","9963242421",new BigDecimal(3000));

		assertEquals(new BigDecimal(5000), cust1.getWallet().getBalance());
	}
	@Test(expected=InvalidInputException.class)
	public void testDeposit()
	{
		cust1=service.createAccount("Harshini", "9963242322", new BigDecimal(2000)); 
		cust1=service.depositAmount("9963242322", new BigDecimal(5000));
	}

	@Test
	public void testDeposit2()
	{
		cust1=service.createAccount("Ravi", "9963242422", new BigDecimal(2000));
		cust1=service.depositAmount("9963242422", new BigDecimal(2000));

		assertEquals(new BigDecimal(4000), cust1.getWallet().getBalance());
	}
	@Test(expected=InvalidInputException.class)
	public void testWithdraw()
	{
		service.withdrawAmount("900000000", new BigDecimal(2000));
	}

	@Test
	public void testWithdraw2()
	{
		cust1=service.createAccount("Raghu", "9490220257", new BigDecimal(7000));
		cust1=service.withdrawAmount("9490220257", new BigDecimal(2000));

		assertEquals(new BigDecimal(5000), cust1.getWallet().getBalance());
	}	

	@Test
	public void testGetName()
	{
		Customer cust=new Customer("Capgemini","8179793280");
		assertEquals("Capgemini",cust.getName());
	}
	@Test
	public void testGetMobileNo()
	{
		Customer cust=new Customer("Capgemini","8121101096");
		assertEquals("8121101096",cust.getMobileNo());
	}

	@Test
	public void testGetBalance()
	{
		Wallet wallet=new Wallet(new BigDecimal(1000));
		Customer cust=new Customer("Capgemini","8121101096",wallet);
		assertEquals(new BigDecimal(1000),cust.getWallet().getBalance());
	}
	@Test
	public void TestValidate()
	{
		Customer customer=new Customer("Raghu","9490220257",new Wallet(new BigDecimal(10)));
		service.acceptCustomerDetails(customer);
	}

	@After
	public void testAfter()
	{
		service=null;
	}

}
