package com.hhh.lostfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_name,et_phone,et_des,et_date,et_location;
    private Button bt_submit,bt_get;
    private RadioButton rb_lost;
    private String name,phone,des,date,location;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    double latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
    }

    private void initView() {
        sp = getSharedPreferences("user",MODE_PRIVATE);
        editor = sp.edit();
        editor.putString("latitude","");
        editor.putString("longitude","");
        editor.commit();
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_des = findViewById(R.id.et_des);
        et_date = findViewById(R.id.et_date);
        et_location = findViewById(R.id.et_location);
        bt_submit = findViewById(R.id.bt_submit);
        bt_get = findViewById(R.id.bt_get);
        rb_lost = findViewById(R.id.rb_lost);
        bt_submit.setOnClickListener(this);
        bt_get.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_get:
                Intent intent = new Intent(AddActivity.this,LocationActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_submit:
                name = et_name.getText().toString().trim();
                phone = et_phone.getText().toString().trim();
                des = et_des.getText().toString().trim();
                date = et_date.getText().toString().trim();
                location = et_location.getText().toString().trim();
                if(name.equals("")||phone.equals("")||date.equals("")||des.equals("")||location.equals("")){
                    Toast.makeText(this, "empty！", Toast.LENGTH_SHORT).show();
                    return;
                }
                MySqlite mySQLite = new MySqlite(this, 1);
                SQLiteDatabase db= mySQLite.getWritableDatabase();
                //使用ContentValues添加数据
                ContentValues values=new ContentValues();
                values.put("name",name);
                values.put("phone",phone);
                values.put("des",des);
                values.put("date",date);
                values.put("location",location);
                values.put("statue","Lost");
                db.insert("record", null, values);
                db.close();
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String latitude = sp.getString("latitude","");
        String longitude = sp.getString("longitude","");
        if(!latitude.equals("")&&!longitude.equals("")){
            et_location.setText(latitude+","+longitude);
        }
    }
}
