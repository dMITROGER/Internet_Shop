package com.example.dmytro.dmytrogerascomp304_001_assignment4;

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

public class CustomerOrdersActivity extends AppCompatActivity {
    public DBmanager dbManager;


    ArrayList<Order> listAllItems;
    ArrayList <String> listAllItemsNames;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    int custId;
    String userN;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_orders);

        SharedPreferences myPref = getSharedPreferences("UserData", MODE_PRIVATE);
        custId = myPref.getInt("userId",0);
        userN = myPref.getString("userName","");
        dbManager = new DBmanager(this);

        listAllItemsNames = new ArrayList <String>();

        LoadListView ();
    }
    public void LoadListView ()
    {
        listView = (ListView)findViewById(R.id.lv_items);

        try {
            listAllItems = dbManager.getOrdersById(custId, "customerId");
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Order x : listAllItems) {
            String line= null;
            if (x.getItemName()!= null) {
                line ="Order Id: "+x.getOrderId()+ "\n" + x.getItemName() + "\n" + "Price: $ " + Double.toString(x.amount) +
                 "\n" + "Order status: " + x.getStatus();
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


                order = listAllItems.get(position);
                int orderId = order.getOrderId();
                if (order.getStatus().equals("In-Process")) {


                    Fragment1 dialogFragment = Fragment1.newInstance(
                            "Are you sure you want to delete this?");
                    dialogFragment.show(getFragmentManager(), "dialog");
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "You can't update this order : " + orderId, Toast.LENGTH_LONG).show();
                }
                //item = listAllItems.get(position);
//                txt_itemId.setText(Integer.toString(item.getItemId()));
//                txt_itemName.setText(item.getItemName());
//                txt_price.setText(Double.toString(item.getPrice()));
//                txt_category.setText(item.getCategory());

            }

        });

    }
    public void deleteOrder(String orderId)
    {
        Order order = new Order();
        order.setOrderId(Integer.parseInt(orderId));
        dbManager.deleteOrder(order);
        arrayAdapter.clear();
        LoadListView ();
    }
    public void doPositiveClick() {
        //---perform steps when user clicks on OK---
        Log.d("DialogFragmentExample", "User clicks on OK");
        dbManager.deleteOrder(order);
        arrayAdapter.clear();
        LoadListView ();
        Toast.makeText(getApplicationContext(), "Order deleted" , Toast.LENGTH_LONG).show();
    }

    public void doNegativeClick() {
        //---perform steps when user clicks on Cancel---
        Log.d("DialogFragmentExample", "User clicks on Cancel");
        Toast.makeText(getApplicationContext(), "Cancel selected" , Toast.LENGTH_LONG).show();

    }
}
