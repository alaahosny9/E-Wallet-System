package sample;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class Service extends Wallet {
    Wallet account[];

    Service() {
    }

    public void setAccount(Wallet[] account) {
        this.account = account;
    }

    @Override
    public void setBalance(int balance) {
        super.setBalance(balance);
    }

    @Override
    public int getBalance() {
        return super.getBalance();
    }

    public Wallet search(Wallet[] arr , Customer c[] , int id , String mobile , double amount) { //this method is searching on the array of Account and the array of Customer to complete the sending transaction for the user
        Wallet a = null;
//        System.out.println("ENTER THE WALLET'S ID YOU WANT TO SEND MONEY "); //prompt the user to enter the wallet id that he wants to send his money to it
//        Scanner input = new Scanner(System.in);
//        int id = input.nextInt();
//        System.out.println("ENTER THE MOBILE NUMBER "); //prompt the user to enter the wallet mobile number that he wants to send his money to it
//        String mobile = input.next();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].getId() == id && mobile.equals(c[i].getMobile())) {
                a = arr[i];
                    break;
            }
        }
        return a;
    }

}



