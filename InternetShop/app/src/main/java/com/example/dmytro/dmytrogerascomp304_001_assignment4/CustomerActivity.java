package com.example.dmytro.dmytrogerascomp304_001_assignment4;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity {
    public DBmanager dbManager;
    private EditText txt_itemId, txt_itemName, txt_price, txt_category;
    private Button btnAdd, btnOrders;
    ArrayList<Item> listAllItems;
    ArrayList <String> listAllItemsNames;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    int custId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        SharedPreferences myPref = getSharedPreferences("UserData", MODE_PRIVATE);
        String myString = myPref.getString("userName","");
        custId = myPref.getInt("userId",0);
        TextView tView = (TextView)findViewById(R.id.welcomeUser);
        tView.setText("Welcome: "+myString+" !");

        dbManager = new DBmanager(this);

        txt_itemId = (EditText) findViewById(R.id.txt_itemId);
        txt_itemName = (EditText) findViewById(R.id.tx_itemName);
        txt_price = (EditText) findViewById(R.id.txt_price);
        txt_category = (EditText) findViewById(R.id.txt_category);
        //
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnOrders = (Button) findViewById(R.id.btnOrders);
        listAllItemsNames = new ArrayList <String>();

        LoadListView ();
    }
    public void LoadListView ()
    {
        listView = (ListView)findViewById(R.id.lv_items);

        try {
            listAllItems = dbManager.getAllItems();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Item x : listAllItems) {
            String line= null;
            if (x.getItemName()!= null) {
                line = x.getItemName() + "\n" + "Price: $ " + Double.toString(x.price);
                listAllItemsNames.add(line);
            }
        }


        // Create The Adapter with passing ArrayList as 3rd parameter
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listAllItemsNames);
        // Set The Adapter
        listView.setAdapter(arrayAdapter);


        // register onClickListener to handle click events on each item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3)
            {
                String selectedItem=listAllItemsNames.get(position);
                Toast.makeText(getApplicationContext(), "Item Selected : "+selectedItem,   Toast.LENGTH_LONG).show();
                Item item = new Item();
                try {
                    listAllItems = dbManager.getAllItems();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                item = listAllItems.get(position);
                txt_itemId.setText(Integer.toString(item.getItemId()));
                txt_itemName.setText(item.getItemName());
                txt_price.setText(Double.toString(item.getPrice()));
                txt_category.setText(item.getCategory());

            }
        });

    }
    public void addOrder(View view)
    {
        //read values for text fields //(orderId integer primary key autoincrement, itemId text, itemName text, amount real, DeliveryDate text,
        // status text, customerId integer,
        // FOREIGN KEY(customerId) REFERENCES CUSTOMER(customerId), FOREIGN KEY(itemId) REFERENCES Item(itemId));";
        int itemId = Integer.parseInt(txt_itemId.getText().toString());
        String itemName = txt_itemName.getText().toString();
        double amount = Double.parseDouble(txt_price.getText().toString());
        String status = "In-Process";



        //initialize ContentValues object with the new item
        ContentValues contentValues = new ContentValues();
        contentValues.put("itemId", itemId);
        contentValues.put("itemName", itemName);
        contentValues.put("amount", amount);
        contentValues.put("DeliveryDate", "");
        contentValues.put("status", status);
        contentValues.put("customerId", custId);
        Toast.makeText(CustomerActivity.this,"Added to your orders successfully",Toast.LENGTH_LONG).show();

        //
        try {
            dbManager.addRow(contentValues,"Orders");
        } catch (Exception exception) {
            //
            Toast.makeText(CustomerActivity.this,
                    exception.getMessage(), Toast.LENGTH_LONG).show();
            Log.i("Error: ", exception.getMessage());

        }
    }
    public void btnViewOrdersClicked (View view)
    {
        Intent intent = new Intent(this, CustomerOrdersActivity.class);
        startActivity(intent);

    }
}
