package sample;

import javax.swing.*;
import java.sql.*;
import java.util.Random;

public class WalletServices {
    String url = "jdbc:mysql://localhost:3306/walletdata";
    String username = "root";
    String password = "80080088";

    public int withdraw(int amount , Customer cObj , int balance , int id) throws SQLException { // this method retrieves from the database for the user and it is doing the withdrawal
        Connection connection = DriverManager.getConnection(url, username, password);

        String sql2 = "INSERT INTO transactions (user_name , lastName , trans_id , wallet_id , transaction_type , transaction_balance) values ( ? , ? , ? , ? , ? , ?)";
        PreparedStatement statement4 = connection.prepareStatement(sql2);
        // this method has a parameter amount that is passed from the user and doing the withdraw transaction for the user
        if (amount > balance){
            JOptionPane.showMessageDialog(null , "SORRY YOUR BALANCE IS INSUFFICIENT " ,"WARNING" ,  JOptionPane.WARNING_MESSAGE);
        }else{
            Random random = new Random();
            int transId = random.nextInt(2000);
            balance = balance - amount;
            statement4.setString(1, cObj.getName());
            statement4.setString(2, cObj.getLastName());
            statement4.setInt(3, transId);
            statement4.setInt(4, id);
            statement4.setString(5, "Withdraw");
            statement4.setDouble(6, amount);
            int rows = statement4.executeUpdate();
            if (rows > 0) {
                System.out.println("A transaction row has been inserted");
            }

        }
        return  balance;
    }
    public int deposit(int amount , Customer cObj , int balance , int id) throws SQLException { // this method retrieves from the database for the user and it is doing the deposit
        Connection connection = DriverManager.getConnection(url, username, password);

        String sql2 = "INSERT INTO transactions (user_name , lastName , trans_id , wallet_id , transaction_type , transaction_balance) values ( ? , ? , ? , ? , ? , ?)";
        PreparedStatement statement4 = connection.prepareStatement(sql2);
        // this method has a parameter amount that is passed from the user and doing the deposit transaction for the user
        if (amount < 50){ // checks on the amount value if it is less than 50 the deposit transaction will not be done
            System.out.println("PLEASE ENTER AN AMOUNT ABOVE OR EQUALS 50 \n");
        }
        else {
            Random random = new Random();
            int transId = random.nextInt(2000);
            balance = balance + amount;
            statement4.setString(1, cObj.getName());
            statement4.setString(2, cObj.getLastName());
            statement4.setInt(3, transId);
            statement4.setInt(4, id);
            statement4.setString(5, "Deposit");
            statement4.setDouble(6, amount);
            int rows = statement4.executeUpdate();
            if (rows > 0) {
                System.out.println("A transaction row has been inserted");
            }

            return balance;
        }
        return balance;
    }

    public int sendMoney(int amount , Customer cObj , int balance , int id) throws SQLException { // this method retrieves from the database for the user and it is doing transfer money
        Connection connection = DriverManager.getConnection(url, username, password);

        String sql2 = "INSERT INTO transactions (user_name , lastName , trans_id , wallet_id , transaction_type , transaction_balance) values ( ? , ? , ? , ? , ? , ?)";
        PreparedStatement statement4 = connection.prepareStatement(sql2);
        // this method has a parameter amount that is passed from the user and doing the withdraw transaction for the user
        if (amount > balance){
            JOptionPane.showMessageDialog(null , "SORRY YOUR BALANCE IS INSUFFICIENT " ,"WARNING" ,  JOptionPane.WARNING_MESSAGE);
        }else{
            Random random = new Random();
            int transId = random.nextInt(2000);
            balance = balance - amount;
            statement4.setString(1, cObj.getName());
            statement4.setString(2, cObj.getLastName());
            statement4.setInt(3, transId);
            statement4.setInt(4, id);
            statement4.setString(5, "Sending");
            statement4.setDouble(6, amount);
            int rows = statement4.executeUpdate();
            if (rows > 0) {
                System.out.println("A transaction row has been inserted");
            }

            String query2 = "UPDATE user_account SET balance = " + balance + " WHERE wallet_id = " + id;
            Statement statement2 = connection.createStatement();
            int rowss = statement2.executeUpdate(query2);
            if (rowss > 0) {
                System.out.println("updated");
            }

        }
        return balance;
    }
}
