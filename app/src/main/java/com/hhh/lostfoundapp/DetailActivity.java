package com.hhh.lostfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_result;
    private Button bt_submit;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String name,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tv_result = findViewById(R.id.tv_result);
        bt_submit = findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(this);
        sp = getSharedPreferences("user",MODE_PRIVATE);
        editor = sp.edit();
        name = sp.getString("name","");
        id = sp.getString("id","");
        tv_result.setText("Found "+name+"\n2days ago\nAt Burwood campus");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_submit:
                deleteRecord(id);
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    public void deleteRecord(String i){
        MySqlite mySqlite=new MySqlite(this,1);
        SQLiteDatabase db=mySqlite.getWritableDatabase();
        db.delete("record","id=?",new String[]{i});
        db.close();
    }

}
