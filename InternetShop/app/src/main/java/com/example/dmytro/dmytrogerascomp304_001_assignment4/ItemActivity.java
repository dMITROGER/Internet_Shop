package com.example.dmytro.dmytrogerascomp304_001_assignment4;

import android.app.ListActivity;
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

public class ItemActivity extends AppCompatActivity {
    public DBmanager dbManager;
    private EditText txt_itemId, txt_itemName, txt_price, txt_category;
    private Button btnAdd, btnShow, btnEdit;
    ArrayList<Item> listAllItems;
    ArrayList <String> listAllItemsNames;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        dbManager = new DBmanager(this);

        txt_itemId = (EditText) findViewById(R.id.txt_itemId);
        txt_itemName = (EditText) findViewById(R.id.tx_itemName);
        txt_price = (EditText) findViewById(R.id.txt_price);
        txt_category = (EditText) findViewById(R.id.txt_category);
        //
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnShow = (Button) findViewById(R.id.btnSHow);
        btnEdit = (Button) findViewById(R.id.btnEdit);
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
                line ="Item Id: "+x.getItemId()+"\n"+ x.getItemName() + "\n" + "Price: $ " + Double.toString(x.price);
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
            public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
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

    public void addItem(View view) {
        //read values for text fields
        //int itemId = Integer.parseInt(txt_itemId.getText().toString());
        String itemName = txt_itemName.getText().toString();
        double price = Double.parseDouble(txt_price.getText().toString());
        String category = txt_category.getText().toString();
        //initialize ContentValues object with the new item
        ContentValues contentValues = new ContentValues();
        //contentValues.put("itemId", itemId);
        contentValues.put("itemName", itemName);
        contentValues.put("price", price);
        contentValues.put("category", category);
        //
        try {
            dbManager.addRow(contentValues,"Item");
        } catch (Exception exception) {
            //
            Toast.makeText(ItemActivity.this,
                    exception.getMessage(), Toast.LENGTH_LONG).show();
            Log.i("Error: ", exception.getMessage());

        }
        arrayAdapter.clear();
        LoadListView ();
    }

    public void showItem(View view) {
        try {
            Item item = dbManager.getItemById(txt_itemId.getText().toString(), "itemId");
            txt_itemName.setText(item.getItemName());
            txt_price.setText(Double.toString(item.getPrice()));
            txt_category.setText(item.getCategory());
        } catch (Exception exception) {
            Toast.makeText(ItemActivity.this,
                    exception.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("Error: ", exception.getMessage());

        }
    }
    public void editItem(View view) {
        int itemId = Integer.parseInt(txt_itemId.getText().toString());

        String itemName = txt_itemName.getText().toString();
        double price = Double.parseDouble(txt_price.getText().toString());
        String category = txt_category.getText().toString();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("itemId", itemId);
            contentValues.put("itemName", itemName);
            contentValues.put("price", price);
            contentValues.put("category", category);
            //edit the row
            boolean b = dbManager.editRow(itemId, "itemId", contentValues, "Item");


        } catch (Exception exception) {
            Toast.makeText(ItemActivity.this,
                    exception.getMessage(), Toast.LENGTH_LONG).show();
            Log.i("Error: ", exception.getMessage());

        }
        arrayAdapter.clear();
        LoadListView ();

    }
    public void deleteItem(View view)
    {
        Item item = new Item();
        int itemId = Integer.parseInt(txt_itemId.getText().toString());
        item.setItemId(itemId);
        dbManager.deleteItem(item);
        txt_itemId = (EditText) findViewById(R.id.txt_itemId);
        txt_itemName = (EditText) findViewById(R.id.tx_itemName);
        txt_price = (EditText) findViewById(R.id.txt_price);
        txt_category = (EditText) findViewById(R.id.txt_category);
        txt_itemId.setText("");
        txt_itemName.setText("");
        txt_price.setText("");
        txt_category.setText("");

        arrayAdapter.clear();
        LoadListView ();
    }
}
