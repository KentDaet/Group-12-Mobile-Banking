# Firebase Database in Mobile Banking example
This document walks through the code of a simple Android mobile application that demonstratesa mobile application with firestore database as the Back-end.

# Firebase Database of JK2R Mobile Banking Application
https://console.firebase.google.com/u/0/project/mobilebankinggroup12

# Explore the code
We're now going to walk through the most important parts of the sample code.

# OTP Authentication And Verification

inside the java class mobile holds the class for generating OTP
```
public class mobile 
```
b1 is the variable for iniating the OTP generation in the Firestore Database Auth
```
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);

        btnExit = findViewById(R.id.btnExit);
        t1=(EditText)findViewById(R.id.t1);
        ccp=(CountryCodePicker)findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(t1);
        b1=(Button)findViewById(R.id.b1);

        b1.setOnClickListener(view -> {
            Intent intent=new Intent(mobile.this,otp.class);
            intent.putExtra("mobile",ccp.getFullNumberWithPlus().replace(" ",""));
            startActivity(intent);
        });
```
b1 is the variable for iniating the OTP generation in the Firestore Database Auth

# Registration
this class holds the Registration function of the Application
```
public class Register 
```
```
mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String fullName = mFullName.getText().toString();
                //final String address = mAddress.getText().toString();
                final String phone    = mPhone.getText().toString();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // send verification link
                            FirebaseUser fuser = fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                }
                            });

                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",fullName);
                            user.put("email",email);
                            user.put("phone",phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), com.example.mobilebankinggroup12.MenuActivityOB.class));

                        }else {
                            Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
```
the button mLoginBtn is uded if the user already have an account
```
 mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.example.mobilebankinggroup12.Login.class));
            }
        });
```
# Log in
this class holds the Login function of the Application
```
public class Login 
```
```
mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
//                String phone = mphone.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

//                if(TextUtils.isEmpty(phone)){
//                    mphone.setError("Phone Number is Required.");
//                    return;
//                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MenuActivityOB.class));
                        }else {
                            Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });
  ```

# Withdrawal of Money
```
public class WithdrawOB
```
```
    private double balance;
    private double wdValue;

    //set current balance
    public void setBalance(double b) {

        balance = b;

    }//end setBalance
    //set withdraw value entered by user
    public void setWithdraw (double wd) {


        wdValue = wd;
    }//end setWithdraw
    //calculate and return new balance
    public double getNewBalance() {

        return balance - wdValue;

    }//end getNewBalance

```
# Transfer of money
this class holds transfer button with Event Listener class, and Event handler method
```
public class TransferActivityOB
```
```
transferB.setOnClickListener(new View.OnClickListener() {
 @Override
            public void onClick(View v) {

                //check if transfer amount was entered
                if (!TextUtils.isEmpty(TransferET.getText())) {

                    TransferEntered = Double.parseDouble(String.valueOf(TransferET.getText()));
                    //get index of spinner string array
                    transferChoice = TransferS.getSelectedItemPosition();
                    //choose between two available transfer options
                    switch (transferChoice) {
                     //transfer funds from checking to savings
                     case 0:

                            //check if transfer amount is valid
                             if (cBalanceD >= TransferEntered) {

                                 //withdraw from checking
                                 com.example.mobilebankinggroup12.WithdrawOB wd = new com.example.mobilebankinggroup12.WithdrawOB();
                                 wd.setBalance(cBalanceD);
                                 wd.setWithdraw(TransferEntered);
                                 //set new checking balance
                                 cNewBalance = wd.getNewBalance();

                                 cBalanceTV.setText(String.valueOf(currency.format(cNewBalance)));
                                 cBalanceD = cNewBalance;

                                 //deposit to savings
                                 DepositOB dp = new DepositOB();
                                 dp.setBalance(sBalanceD);
                                 dp.setDeposit(TransferEntered);

                                 //set new savings balance
                                 sNewBalance = dp.getNewBalance();

                                 sBalanceTV.setText(String.valueOf(currency.format(sNewBalance)));
                                 sBalanceD = sNewBalance;

                                 //reset transfer amount
                                 TransferEntered = 0;
                             }//end checking if transfer is valid
                             //transfer amount is not valid
                             else {
                                 //send msg insufficient funds
                                 noFundsMsg();
                             }//
                         return;

                     //transfer funds from savings  to checking
                     case 1:

                             //check if transfer amount is valid
                             if (sBalanceD >= TransferEntered) {

                                     //withdraw from savings
                                     com.example.mobilebankinggroup12.WithdrawOB wd = new com.example.mobilebankinggroup12.WithdrawOB();
                                     wd.setBalance(sBalanceD);
                                     wd.setWithdraw(TransferEntered);
                                     //set new savings balance
                                     sNewBalance = wd.getNewBalance();

                                     sBalanceTV.setText(String.valueOf(currency.format(sNewBalance)));
                                     sBalanceD = sNewBalance;

                                     //deposit to checking
                                     DepositOB dp = new DepositOB();
                                     dp.setBalance(cBalanceD);
                                     dp.setDeposit(TransferEntered);

                                     //set new checking balance
                                     cNewBalance = dp.getNewBalance();

                                     cBalanceTV.setText(String.valueOf(currency.format(cNewBalance)));
                                     cBalanceD = cNewBalance;

                                     //reset transfer amount
                                     TransferEntered = 0;
                                 }////end checking if transfer is valid
                             //transfer amount is not valid
                             else {

                                 //send msg insufficient funds
                                 noFundsMsg();
                             }//end transfer is not valid msg

                         return;
                 }//end switch transferChoice
                }//end check if transfer amount was entered
                //user didn't enter transfer amount
                else {

                    //send msg no amount entered
                    noAmountMsg();
                }//end transfer amount was not entered msg
            }//end if
        });/
        
  ```
        
        
# Deposit of money

  This class holds the deposit button with Event Listener class, and Event handler method
  ```
  public class TransactionActivityOB 
   ```
   
 ```
        DepositB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if deposit field is not empty, get deposit amount
                if (!TextUtils.isEmpty(DepositET.getText())) {
                    DepositEntered = Double.parseDouble(String.valueOf(DepositET.getText()));
                    //create deposit object
                    DepositOB dp = new DepositOB();
                    dp.setBalance(BalanceD);
                    dp.setDeposit(DepositEntered);

                    //calculate new balance
                    NewBalance = dp.getNewBalance();

                    BalanceTV.setText(String.valueOf(currency.format(NewBalance)));
                    BalanceD = NewBalance;
                    //reset user deposit amount to zero
                    DepositEntered = 0;
                }//end if
                //deposit filed is empty, prompt user to enter deposit amount
                else {

                    Toast.makeText(TransactionActivityOB.this, "Please enter deposit amount and try again!", Toast.LENGTH_LONG).show();
                }//end else
                //clear deposit field
                DepositET.setText(null);
            }
        });
  ```
