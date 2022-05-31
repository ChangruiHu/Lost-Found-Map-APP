package com.hhh.lostfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private TextView tv_name,tv_phone,tv_des,tv_location,tv_date;
    private List<Map<String, String>> list;
    private MyAdapter adapter;
    private SharedPreferences.Editor editor;
    private ListView lv;
    private MySqlite mySqlite;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        sp = getSharedPreferences("user",MODE_PRIVATE);
        editor = sp.edit();

        lv = (ListView) findViewById(R.id.lv);
        list = new ArrayList<>();
        getData();
        adapter = new MyAdapter(
                this,
                list,
                R.layout.item,
                new String[]{"name","phone", "des","location","date"},
                new int[]{R.id.tv_name,R.id.tv_phone,R.id.tv_des,R.id.tv_location,R.id.tv_date}
        );

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editor.putString("name",list.get(i).get("name"));
                editor.putString("id", list.get(i).get("id"));
                editor.commit();
                Intent intent = new Intent(FindActivity.this,DetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
        adapter = new MyAdapter(
                this,
                list,
                R.layout.item,
                new String[]{"name","phone", "des","location","date"},
                new int[]{R.id.tv_name,R.id.tv_phone,R.id.tv_des,R.id.tv_location,R.id.tv_date}
        );

        lv.setAdapter(adapter);
    }

    private class MyAdapter extends SimpleAdapter {
        public MyAdapter(Context context, List<? extends Map<String, ?>> data, int resource,
                         String[] from, int[] to) {
            super(context, data, resource, from, to);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);
            tv_name = v.findViewById(R.id.tv_name);
            tv_phone = v.findViewById(R.id.tv_phone);
            tv_des = v.findViewById(R.id.tv_des);
            tv_location = v.findViewById(R.id.tv_location);
            tv_date = v.findViewById(R.id.tv_date);

            tv_date.setText("Date:"+list.get(position).get("date"));
            tv_location.setText("Location:"+list.get(position).get("location"));
            tv_des.setText("Des:"+list.get(position).get("des"));
            tv_phone.setText("Phone:"+list.get(position).get("phone"));
            tv_name.setText("Name:"+list.get(position).get("name"));
            return v;
        }
    }

    public List<Map<String, String>> getData(){
        list.clear();
        MySqlite mySQLite = new MySqlite(this, 1);
        SQLiteDatabase database = mySQLite.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from record order by date desc", null);
        System.out.println(cursor.getCount());
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String des = cursor.getString(cursor.getColumnIndex("des"));
            String location = cursor.getString(cursor.getColumnIndex("location"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            Map<String, String> map = new HashMap<>();
            map.put("id",id);
            map.put("name",name);
            map.put("phone",phone);
            map.put("des",des);
            map.put("location",location);
            map.put("date",date);
            list.add(map);
        }
        return list;
    }
}
