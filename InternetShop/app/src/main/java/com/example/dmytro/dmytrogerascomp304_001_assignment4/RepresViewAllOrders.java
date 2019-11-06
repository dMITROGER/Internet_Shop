package com.example.dmytro.dmytrogerascomp304_001_assignment4;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RepresViewAllOrders extends AppCompatActivity {
    public DBmanager dbManager;
    private EditText txt_OrderId, txt_DeliveryDate, txt_status;
    private Button btnUpdate, btnDelete;
    ArrayList<Order> listAllItems;
    ArrayList <String> listAllItemsNames;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repres_view_all_orders);
        dbManager = new DBmanager(this);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        txt_OrderId = findViewById(R.id.txt_OrderId);
        txt_DeliveryDate = findViewById(R.id.txt_DeliveryDate);
        txt_status = findViewById(R.id.txt_status);

        listAllItemsNames = new ArrayList <String>();

        LoadListView ();
    }
    public void LoadListView ()
    {
        listView = (ListView)findViewById(R.id.lv_items);

        try {
            listAllItems = dbManager.getAllOrders();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Order x : listAllItems) {
            String line= null;
            if (x.getItemName()!= null) {
                line ="Order Id: "+x.getOrderId()+ "\n" + x.getItemName() + "\n" + "Price: $ " + Double.toString(x.amount) +
                        "\n" + "Customer Id: " + Integer.toString(x.customerId)+"\n" + "Order status: " + x.getStatus();
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
                Order order = new Order();
                try {
                    listAllItems = dbManager.getAllOrders();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                order = listAllItems.get(position);
                txt_OrderId.setText(Integer.toString(order.getOrderId()));
                txt_DeliveryDate.setText(order.getDeliveryDate());
                txt_status.setText(order.getStatus());


            }
        });

    }
    public void deleteOrder(View view)
    {
        Order order = new Order();
        int orderId = Integer.parseInt(txt_OrderId.getText().toString());
        order.setOrderId(orderId);
        dbManager.deleteOrder(order);

        txt_OrderId.setText("");
        txt_DeliveryDate.setText("");
        txt_status.setText("");

        arrayAdapter.clear();
        LoadListView ();
    }
    public void updateOrder(View view) {
        int orderId = Integer.parseInt(txt_OrderId.getText().toString());

        String deliveryDate = txt_DeliveryDate.getText().toString();
        String status = txt_status.getText().toString();
        try {
            ContentValues contentValues = new ContentValues();
            //contentValues.put("orderId", orderId);
            contentValues.put("DeliveryDate", deliveryDate);
            contentValues.put("status", status);
            //edit the row
            boolean b = dbManager.editRow(orderId, "orderId", contentValues, "Orders");


        } catch (Exception exception) {
            Toast.makeText(RepresViewAllOrders.this,
                    exception.getMessage(), Toast.LENGTH_LONG).show();
            Log.i("Error: ", exception.getMessage());

        }
        arrayAdapter.clear();
        LoadListView ();

    }
}
