package com.example.android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.entity.Student;
import com.example.android.util.DatabaseUtil;
import com.example.android.util.HttpAddress;
import com.example.android.util.Result;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView student = null;
    private TextView changePwd = null;
    private Button btn_attendance = null;

    private Context mContext;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;

    private Gson gson = new Gson();

    private String str = "";
    private Student stu = null;
    private Result res_stu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Intent ittDataIn = getIntent();
        str = ittDataIn.getStringExtra("student");
        stu = gson.fromJson(str,Student.class);

        mContext = StudentActivity.this;

        student = findViewById(R.id.student);
        changePwd = findViewById(R.id.changePwd);
        changePwd.setOnClickListener(this);
        btn_attendance = findViewById(R.id.btn_attendance);
        btn_attendance.setOnClickListener(this);

        student.setText("欢迎," + stu.getName());

        SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MM-dd");
        _sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        res_stu= DatabaseUtil.selectById(
                HttpAddress.getStuStatus("student",stu.getUuid(),_sdf.format(new Date())));
        if (res_stu.getCode() == 400){
            btn_attendance.setText("已签到");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //签到
            case R.id.btn_attendance:
                String btn_attendance_text = btn_attendance.getText().toString();
                if (res_stu.getCode() == 200){
                    Result result= DatabaseUtil.selectById(
                            HttpAddress.get("student","attendance",stu.getUuid()));
                    if (result.getCode() == 200){
                        if ("点击签到".equals(btn_attendance_text)){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                            alert = null;
                            builder = new AlertDialog.Builder(mContext);
                            alert = builder.setTitle("系统提示：")
                                    .setMessage("签到成功！"+"\n"+"签到时间：" + sdf.format(new Date())).create();             //创建AlertDialog对象
                            alert.show();                    //显示对话框
                            btn_attendance.setText("已签到");
                        }else {
                            Toast.makeText(StudentActivity.this,"已完成签到，请勿重复签到！",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(StudentActivity.this,"系统正在繁忙，请联系管理员！",Toast.LENGTH_SHORT).show();
                    }


                }

                break;

            case R.id.changePwd:
                Intent SA = new Intent(StudentActivity.this, ChangePwdActivity.class);
                SA.putExtra("student",str);//数据传递给下个Activity
                startActivityForResult(SA,0);
        }
    }
}