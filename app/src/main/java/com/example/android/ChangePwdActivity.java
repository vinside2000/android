package com.example.android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.entity.Student;
import com.example.android.util.DatabaseUtil;
import com.example.android.util.HttpAddress;
import com.example.android.util.Result;
import com.google.gson.Gson;

import java.util.Date;

public class ChangePwdActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText oldPwd = null;
    private EditText newPwd = null;
    private EditText confirmPwd = null;
    private Button btn_changePwd = null;

    private Gson gson = new Gson();

    private Context mContext;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        mContext = ChangePwdActivity.this;

        oldPwd = findViewById(R.id.oldPwd);
        newPwd = findViewById(R.id.newPwd);
        confirmPwd = findViewById(R.id.confirmPwd);
        btn_changePwd = findViewById(R.id.btn_changePwd);
        btn_changePwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String oldPwd_text = oldPwd.getText().toString();
        String newPwd_text = newPwd.getText().toString();
        String confirmPwd_text = confirmPwd.getText().toString();
        Intent ittDataIn = getIntent();
        String str = ittDataIn.getStringExtra("student");
        Student stu = gson.fromJson(str,Student.class);

        if ("".equals(oldPwd_text)||"".equals(newPwd_text)||"".equals(confirmPwd_text)){
            Toast.makeText(ChangePwdActivity.this,"输入不能为空!",Toast.LENGTH_SHORT).show();
        }else if (!stu.getPassword().equals(oldPwd_text)){
            Toast.makeText(ChangePwdActivity.this,"旧密码输入错误!",Toast.LENGTH_SHORT).show();
        }else if (!newPwd_text.equals(confirmPwd_text)){
            Toast.makeText(ChangePwdActivity.this,"新密码与确认密码不一致!",Toast.LENGTH_SHORT).show();
        }else {
            stu.setPassword(confirmPwd_text);
            //请求后端学生修改端口
            Result result= DatabaseUtil.insert(
                    stu,
                    HttpAddress.get("student","update"));
            if (result.getCode() == 200){
                alert = null;
                builder = new AlertDialog.Builder(mContext);
                alert = builder.setTitle("系统提示：")
                        .setMessage("修改成功")
                        .setPositiveButton("点击返回登录", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent CPA = new Intent(ChangePwdActivity.this, MainActivity.class);
                                startActivityForResult(CPA,0);
                            }
                        }).create();             //创建AlertDialog对象
                alert.show();                    //显示对话框
            }else if (result.getCode() == 400){
                alert = null;
                builder = new AlertDialog.Builder(mContext);
                alert = builder.setTitle("系统提示：")
                        .setMessage("系统正在繁忙，请稍后重试！").create();             //创建AlertDialog对象
                alert.show();                    //显示对话框
            }
        }


    }
}