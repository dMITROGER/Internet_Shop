package com.example.dmytro.dmytrogerascomp304_001_assignment4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DBmanager extends SQLiteOpenHelper {
    //database name and version
    private static final String DATABASE_NAME = "StoreDB";
    private static final int DATABASE_VERSION = 1;
    private static String tableCreatorString1;
    //private static String tableName1;
    private static String tableCreatorString2;
    private static String tableCreatorString3;
    private static String tableCreatorString4;
    //private static String tableName2;

    // table name and table creator string (SQL statement to create the table)
    private final static String TABLE_CUSTOMER_NAME = "CUSTOMER";
    //sql string to create the table
    private static final String tableCustomerCreatorString =
            "CREATE TABLE " + TABLE_CUSTOMER_NAME + " (customerId integer primary key, userName text,password text, firstName text, lastName text,address text, postalCode text);";

    private final static String TABLE_OrderRep_NAME = "OrderRep";
    private static final String tableOrderRepCreatorString =
            "CREATE TABLE " + TABLE_OrderRep_NAME + " (employeeId integer primary key autoincrement, userName text,password text, firstName text, lastName text);";

    private final static String TABLE_Item_NAME = "Item";
    private static final String tableItemCreatorString =
            "CREATE TABLE " + TABLE_Item_NAME + " (itemId integer, itemName text, price real, category text);";

    private final static String TABLE_Orders_NAME = "Orders";
    private static final String tableOrderCreatorString =
            "CREATE TABLE " + TABLE_Orders_NAME + " (orderId integer primary key autoincrement, itemId text,amount real, DeliveryDate text, status text, customerId integer);";

    private static final String itemsData1 = "INSERT INTO ITEM VALUES(null,'samsung galaxy note8', 900,'fablets')";
    private static final String itemsData2 = "INSERT INTO ITEM VALUES(null,'samsung galaxy s8', 750,'phones')";
    private static final String itemsData3 = "INSERT INTO ITEM VALUES(null,'samsung galaxy s8+', 855,'phones')";
    private static final String itemsData4 = "INSERT INTO ITEM VALUES(null,'samsung galaxy s9', 980,'phones')";
    private static final String itemsData5 = "INSERT INTO ITEM VALUES(null,'samsung galaxy s9+', 1140,'phones')";

    private static final String orderRepData1 = "INSERT INTO OrderRep VALUES(null,'orderRep1','111','Ivan','Ivanenko')";
    private static final String orderRepData2 = "INSERT INTO OrderRep VALUES(null,'orderRep2','222','John','Johnson')";
    private static final String orderRepData3 = "INSERT INTO OrderRep VALUES(null,'orderRep3','333','Peter','Peterson')";
    private static final String orderRepData4 = "INSERT INTO OrderRep VALUES(null,'orderRep4','444','Andrew','Smith')";
    private static final String orderRepData5 = "INSERT INTO OrderRep VALUES(null,'orderRep5','555','Boris','Borisovich')";

//    private String password; (employeeId integer primary key autoincrement, userName text,password text,
// firstName text, lastName text)
//    private String firstName;
//    private String lastName;
//    private String userName;

    //
    // no-argument constructor
    public DBmanager(Context context)
    {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }
    // Called when the database is created for the first time.
    // This is where the creation of tables and the initial population
    // of the tables should happen.
    @Override
    public void onCreate(SQLiteDatabase db) {
        //create the table
//        db.execSQL(tableCustomerCreatorString);
//        db.execSQL(tableItemCreatorString);
//        db.execSQL(tableOrderCreatorString);
//        db.execSQL(tableOrderRepCreatorString);
        db.execSQL(tableCreatorString1);
        db.execSQL(tableCreatorString2);
        db.execSQL(tableCreatorString3);
        db.execSQL(tableCreatorString4);
        db.execSQL(itemsData1);
        db.execSQL(itemsData2);
        db.execSQL(itemsData3);
        db.execSQL(itemsData4);
        db.execSQL(itemsData5);

        db.execSQL(orderRepData1);
        db.execSQL(orderRepData2);
        db.execSQL(orderRepData3);
        db.execSQL(orderRepData4);
        db.execSQL(orderRepData5);

        //Log.i("oNCREATE : ", "CREATED");

    }
    //
    // Called when the database needs to be upgraded.
    // The implementation should use this method to drop tables,
    // add tables, or do anything else it needs to upgrade
    // to the new schema version.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop table if existed
        db.execSQL("DROP TABLE IF EXISTS CUSTOMER" + TABLE_CUSTOMER_NAME);
        db.execSQL("DROP TABLE IF EXISTS CUSTOMER" + TABLE_OrderRep_NAME);
        db.execSQL("DROP TABLE IF EXISTS CUSTOMER" + TABLE_Item_NAME);
        db.execSQL("DROP TABLE IF EXISTS CUSTOMER" + TABLE_Orders_NAME);

        //recreate the table
        onCreate(db);
    }
    //
    //
    //
    // initialize table names and CREATE TABLE statement
    // called by activity to create a table in the database
    // The following arguments should be passed:
    // tableName - a String variable which holds the table name
    // tableCreatorString - a String variable which holds the CREATE Table statement

    public void dbInitialize(String tableCreatorString1,String tableCreatorString2,String tableCreatorString3,String tableCreatorString4)
    {
       // this.tableName1=tableName1;
        this.tableCreatorString1=tableCreatorString1;
       // this.tableName2=tableName2;
        this.tableCreatorString2=tableCreatorString2;
        this.tableCreatorString3=tableCreatorString3;
        this.tableCreatorString4=tableCreatorString4;

    }
    //
    // CRUD Operations
    //
    //This method is called by the activity to add a row in the table
    // The following arguments should be passed:
    // values - a ContentValues object that holds row values
    public boolean addRow  (ContentValues values, String tabName) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        // Insert the row

        long nr= db.insert(tabName, null, values);
        db.close(); //close database connection
        return nr> -1;
    }



    // This method returns a task object which holds the table row with the given id
    // The following argument should be passed:
    // id - an Object which holds the primary key value
    // fieldName - the  name of the primary key field
    public Item getItemById(Object id, String fieldName) throws Exception{
        SQLiteDatabase db = this.getReadableDatabase();
        String tabName = "Item";
        Cursor cursor =  db.rawQuery( "select * from " + tabName + " where "+ fieldName + "='"+String.valueOf(id)+"'", null );
        Item item = new Item(); //create a new item object
        if (cursor.moveToFirst()) { //if row exists
            cursor.moveToFirst(); //move to first row
            //initialize the instance variables of task object
            item.setItemId(cursor.getInt(0));
            item.setItemName(cursor.getString(1));
            item.setPrice(Double.parseDouble(cursor.getString(2)));
            item.setCategory(cursor.getString(3));
            cursor.close();

        } else {
            item = null;
        }
        db.close();
        return item;

    }

    public ArrayList<Item> getAllItems() throws Exception{
        SQLiteDatabase db = this.getReadableDatabase();
        String tabName = "Item";
        ArrayList <Item> listAllItems = new ArrayList<Item>();
        Cursor cursor =  db.rawQuery( "select * from " + tabName, null );
         //create a new item object
        if (cursor.moveToFirst()) { //if row exists
            cursor.moveToFirst(); //move to first row

            while (!cursor.isAfterLast()) {
                Item item = new Item();
                //initialize the instance variables
                item.setItemId(cursor.getInt(0));
                item.setItemName(cursor.getString(1));
                item.setPrice(Double.parseDouble(cursor.getString(2)));
                item.setCategory(cursor.getString(3));
                listAllItems.add(item);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return listAllItems;
    }

    public ArrayList<Order> getAllOrders() throws Exception{
        SQLiteDatabase db = this.getReadableDatabase();
        String tabName = "Orders";
        ArrayList <Order> listAllItems = new ArrayList<Order>();
        Cursor cursor =  db.rawQuery( "select * from " + tabName, null );
        //create a new item object
        if (cursor.moveToFirst()) { //if row exists
            cursor.moveToFirst(); //move to first row

            while (!cursor.isAfterLast()) {
                Order order = new Order();
                //initialize the instance variables
                order.setOrderId(cursor.getInt(0));
                order.setItemID(cursor.getString(1));
                order.setItemName(cursor.getString(2));
                order.setAmount(cursor.getDouble(3));
                order.setDeliveryDate(cursor.getString(4));
                order.setStatus(cursor.getString(5));
                order.setCustomerId(cursor.getInt(6));
                listAllItems.add(order);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return listAllItems;
    }

    public ArrayList<Order> getOrdersById(Object id, String fieldName) throws Exception{
        SQLiteDatabase db = this.getReadableDatabase();
        String tabName = "Orders";
        ArrayList <Order> listAllItems = new ArrayList<Order>();
        Cursor cursor =  db.rawQuery( "select * from " + tabName + " where "+ fieldName + "='"+String.valueOf(id)+"'", null );
        //create a new item object
        if (cursor.moveToFirst()) { //if row exists
            cursor.moveToFirst(); //move to first row

            while (!cursor.isAfterLast()) {
                Order order = new Order();
                //initialize the instance variables
                order.setOrderId(cursor.getInt(0));
                order.setItemID(cursor.getString(1));
                order.setItemName(cursor.getString(2));
                order.setAmount(cursor.getDouble(3));
                order.setDeliveryDate(cursor.getString(4));
                order.setStatus(cursor.getString(5));
                order.setCustomerId(cursor.getInt(6));
                listAllItems.add(order);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return listAllItems;
    }


    // The following argument should be passed:
    // id - an Object which holds the primary key value
    // fieldName - the  name of the primary key field
    // values - a ContentValues object that holds row values
    public boolean editRow (Object id, String fieldName, ContentValues values, String tabName) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        //
        int nr = db.update(tabName, values, fieldName + " = ? ", new String[]{String.valueOf(id)});
        db.close();
        return nr > 0;
    }
    public void deleteItem(Item item) {
        long id = item.getItemId();
        System.out.println("the deleted item has the id: " + id);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Item_NAME, "itemId = " + id, null);
        db.close();
    }

    public void deleteOrder(Order order) {
        long id = order.getOrderId();
        System.out.println("the deleted item has the id: " + id);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Orders", "orderId = " + id, null);
        db.close();
    }

    public OrderRep getRepById(Object id, String fieldName) throws Exception{
        SQLiteDatabase db = this.getReadableDatabase();
        String tabName = "OrderRep";
        Cursor cursor =  db.rawQuery( "select * from " + tabName + " where "+ fieldName + "='"+String.valueOf(id)+"'", null );
        OrderRep orderRep = new OrderRep(); //create a new  object
        if (cursor.moveToFirst()) { //if row exists
            cursor.moveToFirst(); //move to first row
            //initialize the instance variables of task object
            orderRep.setEmployeeId(cursor.getInt(0));
            orderRep.setUserName(cursor.getString(1));
            orderRep.setPassword(cursor.getString(2));
            orderRep.setFirstName(cursor.getString(3));
            orderRep.setLastName(cursor.getString(4));
            cursor.close();
//employeeId integer primary key autoincrement, userName text,password text, firstName text, lastName text);";
        } else {
            orderRep = null;
        }
        db.close();
        return orderRep;

    }

    public Customer getCustomerById(Object id, String fieldName) throws Exception{
        SQLiteDatabase db = this.getReadableDatabase();
        String tabName = "Customer";
        Cursor cursor =  db.rawQuery( "select * from " + tabName + " where "+ fieldName + "='"+String.valueOf(id)+"'", null );
        Customer customer = new Customer(); //create a new  object
        if (cursor.moveToFirst()) { //if row exists
            cursor.moveToFirst(); //move to first row
            //initialize the instance variables of task object
            customer.setCustomerId(cursor.getInt(0));
            customer.setUserName(cursor.getString(1));
            customer.setPassword(cursor.getString(2));
            customer.setFirstName(cursor.getString(3));
            customer.setLastName(cursor.getString(4));
            customer.setAddress(cursor.getString(5));
            customer.setPostalCode(cursor.getString(6));
            cursor.close();
//(customerId integer primary key autoincrement, userName text,password text, firstName text, lastName text,
// address text, postalCode text);";
        } else {
            customer = null;
        }
        db.close();
        return customer;

    }
}
