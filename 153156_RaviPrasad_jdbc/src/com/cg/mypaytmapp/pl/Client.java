package com.cg.mypaytmapp.pl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import com.cg.mypaytmapp.beans.Customer;
import com.cg.mypaytmapp.exception.InsufficientBalanceException;
import com.cg.mypaytmapp.exception.InvalidInputException;
import com.cg.mypaytmapp.service.WalletService;
import com.cg.mypaytmapp.service.WalletServiceImpl;

public class Client {

	private WalletService walletService;
	public Client()
	{
		walletService=new WalletServiceImpl();
	}
	public void menu ()throws InvalidInputException,InsufficientBalanceException
	{
		String ans="";
		Scanner sc=new Scanner(System.in);
		System.out.println("****Paytm Application****");
		Customer customer=new Customer();
		Customer cu1 = null;
		Customer cu2 = null;
		Customer cu3 = null;
		do
		{
			System.out.println("1.create account");
			System.out.println("2.show balance");
			System.out.println("3.fund transfer");
			System.out.println("4.deposit amount");
			System.out.println("5.withdraw amount");
			System.out.println("6.exit");

			System.out.println("please enter ur choice");
			int	no=sc.nextInt();
			switch(no)
			{
			case 1:

				System.out.println("Enter name");
				String name=sc.next();
				System.out.println("Enter mobile number");
				String mobileNo=sc.next();
				System.out.println("Enter balance");
				BigDecimal amount=sc.nextBigDecimal();
				customer=walletService.createAccount(name, mobileNo, amount);
				System.out.println("Your Account is created");
				System.out.println("Account holder:"+customer.getName());
				System.out.println("mobile number:"+customer.getMobileNo());
				System.out.println("Balance :"+customer.getWallet().getBalance());
				break;
			case 2:
				System.out.println("Enter mobile number to view balance");
				String mobileNo2=sc.next();

				cu2=walletService.showBalance(mobileNo2);
				System.out.println("your balance for mobile number: "+mobileNo2+" is "+
						cu2.getWallet());
				break;
			case 3:
				System.out.println("Enter source mobile number");
				String sourceMobileNo=sc.next();

				System.out.println("Enter target mobile number");
				String targetMobileNo=sc.next();

				System.out.println("Enter amount to transfer");
				BigDecimal amount1=sc.nextBigDecimal();

				cu3=walletService.fundTransfer(sourceMobileNo, targetMobileNo, amount1);
				System.out.println("Your Account is created");
				System.out.println("Account holder:"+cu3.getName());
				System.out.println("mobile number:"+cu3.getMobileNo());
				System.out.println("Balance after transfer :"+cu3.getWallet().getBalance());
				break;
			case 4:
				System.out.println("Enter mobile number");
				String mobileNo3=sc.next();

				System.out.println("enter amount to deposit");
				BigDecimal amount3=sc.nextBigDecimal();

				cu1=walletService.depositAmount(mobileNo3, amount3);
				System.out.println("Your Account is created");
				System.out.println("Account holder:"+cu1.getName());
				System.out.println("mobile number:"+cu1.getMobileNo());
				System.out.println("Balance after deposit :"+cu1.getWallet().getBalance());
				break;
			case 5:

				System.out.println("Enter mobile number");
				String mobileNo4=sc.next();

				System.out.println("Enter amount to withdraw");
				BigDecimal amount4=sc.nextBigDecimal();

				customer=walletService.withdrawAmount(mobileNo4, amount4);
				System.out.println("Your account is successfully created ");
				System.out.println("Account Holder:"+customer.getName());
				System.out.println("Mobile Number:"+customer.getMobileNo());
				System.out.println("Balance after withdraw :"+customer.getWallet().getBalance());
				break;
			case 6:
				System.exit(0);
				break;
			default:
				System.out.println("Invalid selection");
				break;
			}
			System.out.println("\n Do you wish to continue ");
			ans=sc.next();

		}while(ans.equalsIgnoreCase("yes")||ans.equalsIgnoreCase("Yes")||ans.equalsIgnoreCase("y"));
		System.out.println("Thank you for using this service!");
	}
	public static void main(String[] args) throws InvalidInputException,InsufficientBalanceException{
		Client client=new Client();
		client.menu();
	}
}
