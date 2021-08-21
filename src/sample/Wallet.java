package sample;

import javax.swing.*;
import java.io.Serializable;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Wallet implements  Serializable {
    private int id; // Wallet's id
    public int balance; // Wallet's balance
//    public static double annualInterestRate; // annual interest rate for this account
//    private double previousTransaction; // The recently Transaction the user has make
    WalletServices services = new WalletServices();
    Wallet(){
    }

    Wallet(int id , int balance){
        this.id = id;
        this.balance = balance;

    }


    public void setId(int id){
        this.id = id;
    }

    public void setBalance(int balance){
        this.balance = balance;
    }


    public int getId(){
        return id;
    }
    public int getBalance(){
        return balance;
    }

    public int withdraw(int amount , Customer cObj) throws SQLException {// this method can do withdraw transaction for the user and it takes amount and cObj as parameters
        int newBalance = services.withdraw(amount , cObj , balance , id);
        balance = newBalance;
        return balance;

    }
    public int deposit(int amount , Customer cObj) throws SQLException {  // this method can do deposit transaction for the user and it takes amount and cObj as parameters
        int newBalance = services.deposit(amount , cObj , balance , id);
        balance = newBalance;
        return balance;

    }



    public double sendMoney(int amount , Customer cObj) throws SQLException { // this method can send money for the user and it takes amount and cObj as parameters
        int newBalance = services.sendMoney(amount , cObj , balance , id);
        balance = newBalance;
        return balance;

    }

    public int sendMoney(int amount) {// this method sendMoney doing the sending transaction for the user who want to send a money
        balance += amount;
        return balance;
    }

    public  Wallet search(Wallet arr[] , int id){ // this method searches for the accounts we have
        int choice;
        Wallet a1 = null;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i].getId() == id){
                a1 = arr[i];
                break;
            }
        }
        return a1;

    }

    public Customer search(Customer customer[] ,  String mobile){  // this method searches for the customers we have
        Scanner input = new Scanner(System.in);

        Customer c = null;
        for (int i = 0; i < customer.length; i++) {
            if (customer[i].getMobile().equals(mobile)){
                c = customer[i];
                break;
            }
        }
        return c;
    }

    public Customer searchReceiver(Customer customer[] , String mobile){ // this method searches for the customer who will receive the money
        Scanner input = new Scanner(System.in);

        Customer c = null;
        for (int i = 0; i < customer.length; i++) {
            if (customer[i].getMobile().equals(mobile)){
                c = customer[i];
                break;
            }
        }
        return c;
    }


}