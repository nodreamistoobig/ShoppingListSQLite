package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    SimpleCursorAdapter adapter;
    DBHelper helper;
    SQLiteDatabase shopDB;
    TextView num, sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num = findViewById(R.id.num);
        sum = findViewById(R.id.sum);
        helper = new DBHelper(this);
        shopDB = helper.getWritableDatabase(); //connect to db

        Cursor shop = shopDB.rawQuery("SELECT * FROM shoppinglist", null);
        num.setText(String.valueOf(shop.getCount()));

        Cursor total_sum = shopDB.rawQuery("SELECT SUM(cost) FROM shoppinglist", null);
        total_sum.moveToLast();
        sum.setText(String.valueOf(total_sum.getInt(0)));

        int[] views = {R.id.id, R.id.name, R.id.cost, R.id.date};
        String[] list_fields = shop.getColumnNames();
        adapter = new SimpleCursorAdapter(this, R.layout.item, shop, list_fields, views, 0);
        lv = findViewById(R.id.list);
        lv.setAdapter(adapter);
    }

    public void onClick(View v){
        EditText input_name, input_cost;
        input_name = findViewById(R.id.input_name);
        input_cost = findViewById(R.id.input_cost);
        String name = input_name.getText().toString();
        String cost = input_cost.getText().toString();

        Cursor shop = shopDB.rawQuery("SELECT * FROM shoppinglist", null);
        int id = shop.getCount() + 1;

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        String query = "INSERT INTO shoppinglist VALUES(" + id + ", '" + name + "', " + cost + ", '" + dateFormat.format(currentDate) + "')";
        helper.save(shopDB, query);
        shop = shopDB.rawQuery("SELECT * FROM shoppinglist", null);
        adapter.changeCursor(shop);

        num.setText(String.valueOf(shop.getCount()));

        Cursor total_sum = shopDB.rawQuery("SELECT SUM(cost) FROM shoppinglist", null);
        total_sum.moveToLast();
        sum.setText(String.valueOf(total_sum.getInt(0)));
    }
}