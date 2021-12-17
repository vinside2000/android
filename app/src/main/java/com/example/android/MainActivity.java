package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.entity.Admin;
import com.example.android.entity.Student;
import com.example.android.util.DatabaseUtil;
import com.example.android.util.HttpAddress;
import com.example.android.util.HttpUrl;
import com.example.android.util.Result;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private HttpUrl httpUrl = new HttpUrl();

    private EditText username = null;
    private EditText password = null;
    private Button login_btn = null;
    private TextView loginMethod = null;
    private TextView changeLogin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.confirmPwd);

        loginMethod = findViewById(R.id.loginMethod);
        changeLogin = findViewById(R.id.changeLogin);
        changeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String changeLogin_text = changeLogin.getText().toString();
                if ("点击切换管理员登录".equals(changeLogin_text)){
                    username.setText("");
                    password.setText("");
                    loginMethod.setText("管理员登录");
                    changeLogin.setText("点击切换学生登录");
                }else {
                    username.setText("");
                    password.setText("");
                    loginMethod.setText("学生登录");
                    changeLogin.setText("点击切换管理员登录");
                }
            }
        });


        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginMethod_text = loginMethod.getText().toString();

                String _username = username.getText().toString();
                String _password = password.getText().toString();

                if ("学生登录".equals(loginMethod_text)){
                    Student student = new Student(_username,_password);
                    if ("".equals(_username) || "".equals(_password)){
                        Toast.makeText(MainActivity.this,"请填写登录信息！", Toast.LENGTH_SHORT).show();
                    }else {
                        //请求后端学生登录验证接口
                        Result result= DatabaseUtil.insert(
                                student,
                                HttpAddress.get("student","login"));
                        if (result.getCode() == 200){
                            Toast.makeText(MainActivity.this,"登陆成功！", Toast.LENGTH_SHORT).show();
                            Intent MA = new Intent(MainActivity.this, StudentActivity.class);
                            MA.putExtra("student",result.getResult());//数据传递给下个Activity
                            startActivityForResult(MA,0);
                        }else if (result.getCode() == 400){
                            Toast.makeText(MainActivity.this,"帐号或密码错误！", Toast.LENGTH_SHORT).show();
                        }

                        System.out.println(result.getResult());
                    }

                }else if ("管理员登录".equals(loginMethod_text)){
                    Admin admin = new Admin(_username,_password);
                    if ("".equals(_username) || "".equals(_password)){
                        Toast.makeText(MainActivity.this,"请填写登录信息！", Toast.LENGTH_SHORT).show();
                    }else {
                        //请求后端管理员登录验证接口
                        Result result= DatabaseUtil.insert(
                                admin,
                                HttpAddress.get("admin","login"));
                        if (result.getCode() == 200){
                            Toast.makeText(MainActivity.this,"登陆成功！", Toast.LENGTH_SHORT).show();
                            Intent MA = new Intent(MainActivity.this, AdminActivity.class);
                            MA.putExtra("admin",result.getResult());//数据传递给下个Activity
                            startActivityForResult(MA,0);
                        }else if (result.getCode() == 400){
                            Toast.makeText(MainActivity.this,"帐号或密码错误！", Toast.LENGTH_SHORT).show();
                        }
                        System.out.println(result.getResult());
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}