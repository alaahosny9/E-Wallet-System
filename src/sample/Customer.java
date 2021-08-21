package sample;

import java.io.Serializable;

public class Customer extends Person implements Serializable {
    private String mobile;
    private String pin;
    private String lastName;
    int count;
    Wallet a;
    Wallet account[];
    Customer(){
    }

    Customer(int size){
        account = new Wallet[size];
    }
    Customer(String mobile){
        this.mobile = mobile;
    }
    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPin() {
        return pin;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public Wallet createAccount(Wallet a){
        Wallet accounts = new Wallet();
        if (count < account.length){
            account[count] = a;
            accounts = account[count];
            count++;
        }
        else
            System.out.println("error");

        return accounts;
    }

    public Wallet[] getAccount() {
        return account;
    }
}
