package com.example.mobilebankinggroup12;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivityOB extends AppCompatActivity {

    //declare constants used with shared preferences
    public static final String MY_BALANCE = "My_Balance";
    public static final String CHECKING_KEY = "checking_key";
    public static final String SAVINGS_KEY = "savings_key";
    //declare variables for message, checking and savings balance
    String receivedString;
    public String chkBalance, savBalance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ob);

        Bundle extras = getIntent().getExtras();
        //receive welcome msg from MainActivity
            if (extras != null) {
                receivedString = extras.getString("stringReference");
                Toast.makeText(MenuActivityOB.this, receivedString, Toast.LENGTH_LONG).show();
            }//end if


        getPrefs();

       //declare menu buttons
        Button checking_BT = findViewById(R.id.checkingButton);
        Button savings_BT = findViewById(R.id.savingsButton);
        Button transfer_BT = findViewById(R.id.transferButton);
        Button profile_BT = findViewById(R.id.profileButton);


        //profile checking button
        profile_BT.setOnClickListener(v -> startActivity(new Intent(MenuActivityOB.this, com.example.mobilebankinggroup12.MainActivity.class)));//end

       //register checking button with Event Listener class, and Event handler method
        checking_BT.setOnClickListener(v -> {
             //user wants to access checking account
             Intent checkingIntent = new Intent(MenuActivityOB.this, com.example.mobilebankinggroup12.TransactionActivityOB.class);
             //send only data related to checking account
             checkingIntent.putExtra("balance", chkBalance); //checking balance
             checkingIntent.putExtra("key", CHECKING_KEY); //key used to store checking balance
             checkingIntent.putExtra("title", "Checking Account"); //title for transaction activity
             //display transaction activity screen
             startActivity(checkingIntent);

        });//end OnClickListener checking



        //register savings button with Event Listener class, and Event handler method
        savings_BT.setOnClickListener(v -> {
            //user wants to access savings account
            Intent savingsIntent = new Intent(MenuActivityOB.this, com.example.mobilebankinggroup12.TransactionActivityOB.class);
            //send only data related to savings account
            savingsIntent.putExtra("balance", savBalance); //savings balance
            savingsIntent.putExtra("key", SAVINGS_KEY); //key used to store savings balance
            savingsIntent.putExtra("title", "Savings Account"); //title for transaction activity
            //display transaction activity screen
            startActivity(savingsIntent);

        });//end OnClickListener savings

        //register transfer button with Event Listener class, and Event handler method
        transfer_BT.setOnClickListener(v -> {
            //user wants to transfer funds
            Intent transferIntent = new Intent(MenuActivityOB.this, com.example.mobilebankinggroup12.TransferActivityOB.class);
            //send both balances
            transferIntent.putExtra("balanceC", chkBalance); //checking balance
            transferIntent.putExtra("balanceS", savBalance); //savings balance
            //display transfer activity screen
            startActivity(transferIntent);

        });//end OnClickListener transfer
    }//end onCreate

    //function to retrieve current balances when program resumes
    protected void onResume() {
        super.onResume();
        getPrefs();

    }//end onResume
    //function to open shared preferences and retrieve current balances
    public void getPrefs() {
        //open shared preferences xml file
        SharedPreferences BalancePref = getSharedPreferences(MenuActivityOB.MY_BALANCE, MODE_PRIVATE);
        //retrieve checking and savings balances if they are not null
        //or set balances to default value if they are null
        chkBalance = BalancePref.getString(CHECKING_KEY, "5000.00");
        savBalance = BalancePref.getString(SAVINGS_KEY, "7000.00");

    }//end getPrefs
}//