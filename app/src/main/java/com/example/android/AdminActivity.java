package com.example.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.entity.Admin;
import com.example.android.entity.Student;
import com.example.android.util.DatabaseUtil;
import com.example.android.util.HttpAddress;
import com.example.android.util.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter ;
    private List<Student> studentList = new ArrayList<>();
    private SimpleDateFormat sdf;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    private TextView admin_info;
    private Button btn_start;
    private Button btn_getAll;
    private Button btn_getStu;
    private Button btn_save;

    private Context mContext;
    private AlertDialog alert;
    private AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mContext = AdminActivity.this;
        mRecyclerView = findViewById(R.id.stu_view);

        Intent ittDataIn = getIntent();
        String str = ittDataIn.getStringExtra("admin");
        Admin admin = gson.fromJson(str,Admin.class);

        admin_info = findViewById(R.id.admin_info);
        admin_info.setText("?????????" + admin.getUsername());

        btn_start = findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
        btn_getAll = findViewById(R.id.btn_getAll);
        btn_getAll.setOnClickListener(this);
        btn_getStu = findViewById(R.id.btn_getStu);
        btn_getStu.setOnClickListener(this);
        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);

        sdf = new SimpleDateFormat("yyyy-MM-dd");
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        //??????????????????????????????
        Result _result = DatabaseUtil.selectById(HttpAddress.get("admin","ifStart"));
        if (_result.getCode() == 400){
            btn_start.setText("?????????????????????");
            btn_start.setTextColor(Color.rgb(255,0,0));
        }

        //????????????????????????
        Result result = DatabaseUtil.selectList(HttpAddress.get("admin","byDate"));
        if (result.getCode() == 200){
            List<Student> sl = gson.fromJson(result.getResult(), new TypeToken<List<Student>>(){}.getType());
            System.out.println(sl);
            for (Student student : sl) {
                studentList.add(student);
//                studentList.add(new Student("20701101","20701101"));
            }
        }else{
            Toast.makeText(AdminActivity.this,"?????????????????????????????????",Toast.LENGTH_SHORT).show();
        }

        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(AdminActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration mDivider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(mDivider);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:
                String btn_start_text = btn_start.getText().toString();
                if ("??????????????????".equals(btn_start_text)){
                    //????????????
                    Result result = DatabaseUtil.selectById(HttpAddress.get("admin","start"));
                    if (result.getCode() == 200){
                        Toast.makeText(AdminActivity.this,"?????????????????????",Toast.LENGTH_SHORT).show();
                        btn_start.setText("?????????????????????");
                        btn_start.setTextColor(Color.rgb(255,0,0));
                    }else {
                        Toast.makeText(AdminActivity.this,"?????????????????????????????????",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AdminActivity.this,"????????????????????????",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_getAll:
                String btn_getAll_text = btn_getAll.getText().toString();
                if ("??????????????????".equals(btn_getAll_text)){
                    //????????????????????????
                    Result result = DatabaseUtil.selectList(HttpAddress.get("admin","getAll"));
                    if (result.getCode() == 200){
                        studentList = null;
                        studentList = new ArrayList<>();
                        List<Student> sl = gson.fromJson(result.getResult(), new TypeToken<List<Student>>(){}.getType());
                        System.out.println(sl);
                        for (Student student : sl) {
                            studentList.add(student);
//                studentList.add(new Student("20701101","20701101"));
                        }
                        mMyAdapter.notifyDataSetChanged();
                        btn_getAll.setText("??????????????????");
                    }else{
                        Toast.makeText(AdminActivity.this,"?????????????????????????????????",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    //????????????????????????
                    Result result = DatabaseUtil.selectList(HttpAddress.get("admin","byDate"));
                    if (result.getCode() == 200){
                        studentList = null;
                        studentList = new ArrayList<>();
                        List<Student> sl = gson.fromJson(result.getResult(), new TypeToken<List<Student>>(){}.getType());
                        System.out.println(sl);
                        for (Student student : sl) {
                            studentList.add(student);
//                studentList.add(new Student("20701101","20701101"));
                        }
                        mMyAdapter.notifyDataSetChanged();
                        btn_getAll.setText("??????????????????");
                    }else{
                        Toast.makeText(AdminActivity.this,"?????????????????????????????????",Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.btn_getStu:
                //???????????????????????????
                Result result = DatabaseUtil.selectList(HttpAddress.get("admin","getStu"));
                if (result.getCode() == 200){
                    studentList = null;
                    studentList = new ArrayList<>();
                    List<Student> sl = gson.fromJson(result.getResult(), new TypeToken<List<Student>>(){}.getType());
                    System.out.println(sl);
                    for (Student student : sl) {
                        studentList.add(student);
//                studentList.add(new Student("20701101","20701101"));
                    }
                    mMyAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(AdminActivity.this,"?????????????????????????????????",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_save:
                Intent AA = new Intent(AdminActivity.this, SaveActivity.class);
                startActivityForResult(AA,0);
        }

    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(AdminActivity.this, R.layout.stu_list, null);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            Student student = studentList.get(position);
            holder.stu_name.setText(student.getName());
            holder.stu_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Result result = DatabaseUtil.selectById(HttpAddress.get("admin","detail",student.getUuid()));
                    if (result.getCode() == 200){
//                        Toast.makeText(AdminActivity.this,result.getResult(),Toast.LENGTH_SHORT).show();
                        Student stu = gson.fromJson(result.getResult(),Student.class);
                        alert = null;
                        builder = new AlertDialog.Builder(mContext);
                        alert = builder.setTitle("????????????")
                                .setMessage("?????????" + stu.getName() + "\n" +
                                            "?????????" + stu.getNumber() + "\n" +
                                            "?????????" + stu.getStudentClass() + "\n" +
                                            "?????????" + stu.getUsername() + "\n" +
                                            "?????????" + stu.getPassword() + "\n" +
                                            "???????????????" + stu.getPhone()).create();             //??????AlertDialog??????
                        alert.show();                    //???????????????
                    }else {
                        Toast.makeText(AdminActivity.this,"?????????????????????????????????",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.stu_number.setText(student.getNumber());
            holder.stu_class.setText(student.getStudentClass());
            if (student.getDate() != null){
                holder.stu_date.setText(sdf.format(student.getDate()));
                if (student.getAttendanceTime() != null){
                    holder.stu_status.setText("?????????");
                    holder.stu_status.setTextColor(Color.rgb(0,255,0));
                }else {
                    holder.stu_status.setText("?????????");
                    holder.stu_status.setTextColor(Color.rgb(255,0,0));
                }
            }else {
                holder.stu_date.setText("");
                holder.stu_status.setText("");
            }


        }

        @Override
        public int getItemCount() {
            return studentList.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView stu_name;
        private TextView stu_number;
        private TextView stu_class;
        private TextView stu_date;
        private TextView stu_status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            stu_name = itemView.findViewById(R.id.stu_name);
            stu_number = itemView.findViewById(R.id.stu_number);
            stu_class = itemView.findViewById(R.id.stu_class);
            stu_date = itemView.findViewById(R.id.stu_date);
            stu_status = itemView.findViewById(R.id.stu_status);
        }
    }
}