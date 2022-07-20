# Firebase Database in Mobile Banking example
This document walks through the code of a simple Android mobile application that demonstratesa mobile application with firestore database as the Back-end.

# Firebase Database of JK2R Mobile Banking Application
https://console.firebase.google.com/u/0/project/mobilebankinggroup12

# Explore the code
We're now going to walk through the most important parts of the sample code.

# OTP Authentication And Veification


```
public class mobile //holds the class for generating OTP
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
```
public class otp
```
# Registration And Log in

# Depositing and Withdrawal
