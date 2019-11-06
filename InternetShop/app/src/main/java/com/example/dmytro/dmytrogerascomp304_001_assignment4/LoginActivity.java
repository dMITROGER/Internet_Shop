package com.example.dmytro.dmytrogerascomp304_001_assignment4;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public DBmanager dbManager;
    private EditText txt_userName, txt_password;
    private Button bt_signIn;
    Intent intent;


    // table name and table creator string (SQL statement to create the table)
    private final static String TABLE_CUSTOMER_NAME = "CUSTOMER";
    //sql string to create the table
    private static final String tableCustomerCreatorString =
            "CREATE TABLE " + TABLE_CUSTOMER_NAME + " (customerId integer primary key autoincrement, userName text,password text, firstName text, lastName text,address text, postalCode text);";

    private final static String TABLE_OrderRep_NAME = "OrderRep";
    private static final String tableOrderRepCreatorString =
            "CREATE TABLE " + TABLE_OrderRep_NAME + " (employeeId integer primary key autoincrement, userName text,password text, firstName text, lastName text);";

    private final static String TABLE_Item_NAME = "Item";
    private static final String tableItemCreatorString =
            "CREATE TABLE " + TABLE_Item_NAME + " (itemId integer primary key autoincrement, itemName text,price real, category text);";


    private final static String TABLE_Orders_NAME = "Orders";
    private static final String tableOrdersCreatorString =
            "CREATE TABLE " + TABLE_Orders_NAME + " (orderId integer primary key autoincrement, itemId text, itemName text, amount real, DeliveryDate text, status text, customerId integer, FOREIGN KEY(customerId) REFERENCES CUSTOMER(customerId), FOREIGN KEY(itemId) REFERENCES Item(itemId));";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_userName = (EditText) findViewById(R.id.txt_userName);
        txt_password = (EditText) findViewById(R.id.txt_password);

        bt_signIn = (Button) findViewById(R.id.btnSignIn);

        dbManager = new DBmanager(this);
         //initialize the tables
        try {
            dbManager = new DBmanager(this);
            //create the tables
            dbManager.dbInitialize(tableCustomerCreatorString, tableOrdersCreatorString,
                    tableItemCreatorString,tableOrderRepCreatorString);

            //dbManager.dbInitialize(TABLE_OrderRep_NAME, tableOrderRepCreatorString);
            //dbManager.dbInitialize(TABLE_Orders_NAME, tableOrderCreatorString);
           // dbManager.dbInitialize(TABLE_Item_NAME, tableItemCreatorString);



        } catch (Exception exception) {
            Toast.makeText(LoginActivity.this,
                    exception.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("Error: ", exception.getMessage());
        }
    }


    public void btnSignInClicked(View view) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBoxRep);
//==========================================================================
        if (checkBox.isChecked())
        {
            try
            {
                OrderRep orderRep = dbManager.getRepById(txt_userName.getText().toString(), "userName");
                if (orderRep.getPassword().equals(txt_password.getText().toString())) {
                    intent = new Intent(this, RepresActivity.class);

                    SharedPreferences myPreference =
                            getSharedPreferences("OrderRepData", 0);
                    //prepare it for edit by creating and Edit object
                    SharedPreferences.Editor prefEditor = myPreference.edit();
                    //store a string in memory
                    prefEditor.putString("userName", txt_userName.getText().toString());

                    //commit the transaction
                    prefEditor.commit();
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this,
                            "User name or password is incorrect", Toast.LENGTH_LONG).show();
                }

            } catch (Exception exception) {
                Toast.makeText(LoginActivity.this,
                        "User name or password is incorrect", Toast.LENGTH_SHORT).show();
                Log.i("Error: ", exception.getMessage());
            }
        }
        else
        {
            try
            {
                Customer customer = dbManager.getCustomerById(txt_userName.getText().toString(), "userName");
                if (customer.getPassword().equals(txt_password.getText().toString())) {
                    intent = new Intent(this, CustomerActivity.class);
                    SharedPreferences myPreference =
                            getSharedPreferences("UserData", 0);
                    //prepare it for edit by creating and Edit object
                    SharedPreferences.Editor prefEditor = myPreference.edit();
                    //store a string in memory
                    prefEditor.putString("userName", txt_userName.getText().toString());
                    prefEditor.putInt("userId", customer.getCustomerId());

                    //commit the transaction
                    prefEditor.commit();
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this,
                            "User name or password is incorrect", Toast.LENGTH_LONG).show();
                }

            } catch (Exception exception) {
                Toast.makeText(LoginActivity.this,
                        "User name or password is incorrect", Toast.LENGTH_SHORT).show();
                Log.i("Error: ", exception.getMessage());
            }
        }

    }


        public void btnclicked (View view)
        {
            intent = new Intent(this, CustomerRegistrationActivity.class);
            startActivity(intent);
        }


    }

