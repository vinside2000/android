package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.entity.Student;
import com.example.android.util.DatabaseUtil;
import com.example.android.util.HttpAddress;
import com.example.android.util.Result;

public class SaveActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ed_name;
    private EditText ed_number;
    private EditText ed_class;
    private EditText ed_phone;

    private Button btn_saveStu;
    private Button btn_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        ed_name = findViewById(R.id.ed_name);
        ed_number = findViewById(R.id.ed_number);
        ed_class = findViewById(R.id.ed_class);
        ed_phone = findViewById(R.id.ed_phone);
        btn_saveStu = findViewById(R.id.btn_saveStu);
        btn_saveStu.setOnClickListener(this);
        btn_return = findViewById(R.id.btn_return);
        btn_return.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_saveStu:
                String ed_name_text = ed_name.getText().toString();
                String ed_number_text = ed_number.getText().toString();
                String ed_class_text = ed_class.getText().toString();
                String ed_phone_text = ed_phone.getText().toString();
                if ("".equals(ed_name_text)||"".equals(ed_number_text)||"".equals(ed_class_text)){
                    Toast.makeText(SaveActivity.this,"输入不能为空!",Toast.LENGTH_SHORT).show();
                }else {
                    Student student = new Student(ed_name_text,ed_number_text,ed_class_text,ed_phone_text);
                    //请求添加端口
                    Result result= DatabaseUtil.insert(
                            student,
                            HttpAddress.get("admin","saveStu"));
                    if (result.getCode() == 200){
                        Toast.makeText(SaveActivity.this,"添加成功！",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(SaveActivity.this,"系统繁忙，请稍后重试！",Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.btn_return:
                finish();
        }
    }
}