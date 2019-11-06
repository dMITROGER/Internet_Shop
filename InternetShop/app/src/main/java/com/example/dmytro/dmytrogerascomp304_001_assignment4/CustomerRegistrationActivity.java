package com.example.dmytro.dmytrogerascomp304_001_assignment4;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomerRegistrationActivity extends AppCompatActivity {
    public DBmanager dbManager;
    private EditText txt_userName,txt_password, txt_firstName, txt_lastName, txt_address, txt_postalcode, txt_password2;
    private Button btnRegister;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);

        dbManager = new DBmanager(this);

        txt_userName = (EditText) findViewById(R.id.txt_userName);
        txt_password = (EditText) findViewById(R.id.txt_password);
        txt_password2 = (EditText) findViewById(R.id.txt_password2);
        txt_firstName = (EditText) findViewById(R.id.txt_firstName);
        txt_lastName = (EditText) findViewById(R.id.txt_lastName);
        txt_address = (EditText) findViewById(R.id.txt_address);
        txt_postalcode = (EditText) findViewById(R.id.txt_postalCode);
        //
        btnRegister = (Button) findViewById(R.id.btnRegister);

    }

    public void btnRegisterClicked(View view) {
        //read values for text fields
        String userName = txt_userName.getText().toString();
        String password = txt_password.getText().toString();
        String password2 = txt_password2.getText().toString();
        String fistName = txt_firstName.getText().toString();
        String lastName = txt_lastName.getText().toString();
        String address = txt_address.getText().toString();
        String postalcode = txt_postalcode.getText().toString();

        if (password.equals(password2))
        {
            //initialize ContentValues object with the new item
            ContentValues contentValues = new ContentValues();
            contentValues.put("userName", userName);
            contentValues.put("password", password);
            contentValues.put("firstName", fistName);
            contentValues.put("lastName", lastName);
            contentValues.put("address", address);
            contentValues.put("postalCode", fistName);
            //
            try {
                dbManager.addRow(contentValues, "Customer");

            } catch (Exception exception) {
                //
                Toast.makeText(CustomerRegistrationActivity.this,
                        exception.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("Error =============== ", exception.getMessage());

            }
            intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(CustomerRegistrationActivity.this,
                    "Please check your passwords", Toast.LENGTH_LONG).show();
        }

    }
}
