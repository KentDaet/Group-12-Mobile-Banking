package com.example.mobilebankinggroup12;

public class DepositOB {

    private double balance;
    private double dpValue;
    //set current balance
    public void setBalance(double b) {

        balance = b;

    }//end setBalance

    //set deposit value entered by user
    public void setDeposit (double dp) {

        dpValue = dp;

    }//end setDeposit
    //calculate and return new balance
    public double getNewBalance() {

        return balance + dpValue;

    }// end getNewBalance
}//end Deposit
