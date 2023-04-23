package com.example.mysqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;


// implements View.OnClickListener
public class MainActivity extends AppCompatActivity {
    //Khai báo các biến giao diện
    Button cr_database,delete_database, cr_table, delete_table,
            insert_row, delete_row, update_row, query_data, insert_student, querying_sinhvien
            , cr_tableSV;
    TextInputEditText type_table, type_malop;

    //Khai báo ListView
    SQLiteDatabase mydatabase;
    String msg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cr_database = (Button)findViewById(R.id.cr_database);
        delete_database = (Button) findViewById(R.id.delete_database);
        cr_table = (Button)findViewById(R.id.cr_table);
        delete_table = (Button) findViewById(R.id.delete_table);
        query_data = (Button) findViewById(R.id.query_data);
        insert_row = (Button) findViewById(R.id.insert_row);
        delete_row = (Button) findViewById(R.id.delete_row);
        update_row = (Button) findViewById(R.id.update_row);
        insert_student = (Button) findViewById(R.id.insert_student);
        cr_tableSV = (Button) findViewById(R.id.cr_tableSinhVien);
        querying_sinhvien = (Button) findViewById(R.id.query_Sinhvien);

        type_table = (TextInputEditText )findViewById(R.id.type_table);
        type_malop = (TextInputEditText )findViewById(R.id.type_malop);


        cr_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCreateDb();
            }
        });
//
        delete_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doDeleteDb();
            }
        });

        query_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAllLop();
            }
        });
//
        cr_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCreateLopTable();
            }
        });

        delete_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doDeleteLopTable();
            }
        });

//
        insert_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View insertView = inflater.inflate(R.layout.form_insert_row, null);

                builder.setView(insertView);
                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TextInputEditText  nhapMalop = (TextInputEditText )insertView.findViewById(R.id.nhap_malop);
                        String malop = nhapMalop.getText().toString();

                        TextInputEditText  nhapTenlop = (TextInputEditText )insertView.findViewById(R.id.nhap_tenlop);
                        String tenlop = nhapTenlop.getText().toString();

                        TextInputEditText  nhapSiso = (TextInputEditText )insertView.findViewById(R.id.nhap_siso);
                        String siso = nhapSiso.getText().toString();

                        doInsertRecord(malop,tenlop,siso);

                    }
                });
                builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Insert record is failed", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
//
        update_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View insertView = inflater.inflate(R.layout.form_insert_row, null);

                builder.setView(insertView);
                builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TextInputEditText  nhapMalop = (TextInputEditText )insertView.findViewById(R.id.nhap_malop);
                        String malop = nhapMalop.getText().toString();

                        TextInputEditText  nhapTenlop = (TextInputEditText )insertView.findViewById(R.id.nhap_tenlop);
                        String tenlop = nhapTenlop.getText().toString();

                        TextInputEditText  nhapSiso = (TextInputEditText )insertView.findViewById(R.id.nhap_siso);
                        String siso = nhapSiso.getText().toString();

                        updateLopName(malop,tenlop,siso);

                    }
                });
                builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Update record is failed", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        delete_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View insertView = inflater.inflate(R.layout.delete_row, null);

                builder.setView(insertView);
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TextInputEditText  nhapMalop = (TextInputEditText )insertView.findViewById(R.id.nhap_malop);
                        String malop = nhapMalop.getText().toString();

                        doDeleteRecord(malop);
                    }
                });
                builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Delete record is failed", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        insert_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View insertView = inflater.inflate(R.layout.form_insert_student, null);

                builder.setView(insertView);
                builder.setPositiveButton("Thêm sinh viên", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TextInputEditText  nhapMaSv = (TextInputEditText )insertView.findViewById(R.id.nhap_maSv);
                        String maSv = nhapMaSv.getText().toString();

                        TextInputEditText  nhapTenSv = (TextInputEditText )insertView.findViewById(R.id.nhap_tenSv);
                        String tenSv = nhapTenSv.getText().toString();

                        insert_student(maSv,tenSv);
                    }
                });
                builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Insert record is failed", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        cr_tableSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCreateSinhVienTable();
            }
        });


        querying_sinhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTableSinhVien();
            }
        });

    }
    public void doCreateDb(){
       try {
           mydatabase = openOrCreateDatabase("qlsinhvien.db",MODE_PRIVATE,null);
           Toast.makeText(MainActivity.this, "Create database is successful", Toast.LENGTH_SHORT).show();
       }catch (Exception e){
           Toast.makeText(MainActivity.this, "Create database is failed", Toast.LENGTH_SHORT).show();
       }
    }
    public void doDeleteDb(){
        // Xoa database
        if(deleteDatabase("qlsinhvien.db") == true){
            Toast.makeText(MainActivity.this, "Delete database [qlsinhvien.db] is successful", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "Delete database [qlsinhvien.db] is failed", Toast.LENGTH_SHORT).show();
        }
    }
    public void doCreateLopTable(){
        // Tao bang
        try {
            String sql = "CREATE TABLE tbllop(";
                    sql += "malop TEXT primary key,";
                    sql += "tenlop TEXT,";
                    sql+= "siso TEXT)";
            mydatabase.execSQL(sql);
            Toast.makeText(MainActivity.this, "Create table is successor", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e("Error","Table đã tồn tại");
            Toast.makeText(MainActivity.this, "Table is existed", Toast.LENGTH_SHORT).show();
        }
    }
    public void doDeleteLopTable(){
        TextInputEditText  type_table = (TextInputEditText )findViewById(R.id.type_table);
        String tableName = type_table.getText().toString();
        try {
            String sql = "DROP TABLE IF EXISTS " + tableName;
            mydatabase.execSQL(sql);
            Toast.makeText(this, "Successfully deleted table "+ tableName, Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(this, "This table does not exist in qlsinhvien.db!", Toast.LENGTH_SHORT).show();
        }
    }
    public void doInsertRecord(String malop,String tenlop, String siso){
        // insert
        ContentValues values = new ContentValues();
        values.put("malop",malop);
        values.put("tenlop",tenlop);
        values.put("siso",siso);
        if(mydatabase.insert("tbllop",null,values) == -1){
            Toast.makeText(MainActivity.this, "Insert record is failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "Insert record is successor", Toast.LENGTH_SHORT).show();
        }
    }
    public void loadAllLop(){
        Cursor c = mydatabase.query("tbllop",null,null,null
                ,null,null,null);
        c.moveToFirst();
        String data = "";
        while (c.isAfterLast() == false){
            data+= "malop: " + c.getString(0) + " - "+
                    "tenlop: " + c.getString(1) + " - "+
                    "siso: " + c.getString(2);
            data+= "\n";
            c.moveToNext();
        }
        Toast.makeText(MainActivity.this, data, Toast.LENGTH_LONG).show();
        c.close();
    }
    public void updateLopName(String malop, String new_tenlop,String siso){
        ContentValues values = new ContentValues();
        values.put("malop", malop);
        values.put("tenlop", new_tenlop);
        values.put("siso", siso);

        int ret = mydatabase.update("tbllop",values,"malop =?",new String[]{malop});
        if(ret == 0){
            msg = "Update is failed";
        }else{
            msg = "Update is successful";
        }
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
    public void doDeleteRecord(String malop){
        int n = mydatabase.delete("tbllop","malop =?",new String[]{malop});
        if(n == 0){
            Toast.makeText(MainActivity.this, n +" No record to delete", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, n +" record is deleted", Toast.LENGTH_SHORT).show();
        }
    }
    public void doCreateSinhVienTable(){

        try {
            String sql = "CREATE TABLE tblsinhvien (" +
                    "masv TEXT PRIMARY KEY ," +
                    "tensv TEXT,"+
                    "malop TEXT NOT NULL CONSTRAINT malop "+
                    "REFERENCES tbllop(malop) ON DELETE CASCADE)";
            mydatabase.execSQL(sql);
            Toast.makeText(MainActivity.this, "Create table tblsinhvien is successor", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e("Error","Table đã tồn tại");
            Toast.makeText(MainActivity.this, "Table tblsinhvien is existed", Toast.LENGTH_SHORT).show();
        }

    }

    public void insert_student(String masv, String tensv){
        TextInputEditText  type_malopp = (TextInputEditText )findViewById(R.id.type_malop);
        String malop = type_malopp.getText().toString(); //"DHTH7C"
        // insert
        ContentValues values = new ContentValues();
        values.put("masv",masv);
        values.put("tensv",tensv);
        values.put("malop",malop);
        if(mydatabase.insert("tblsinhvien",null,values) == -1){
            Toast.makeText(MainActivity.this, "Insert record is failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "Insert record is successor", Toast.LENGTH_SHORT).show();
        }
    }
    public void loadTableSinhVien(){
        Cursor c = mydatabase.query("tblsinhvien",null,null,null
                ,null,null,null);
        c.moveToFirst();
        String data = "";
        while (c.isAfterLast() == false){
            data+= "masv: " + c.getString(0) + " - "+
                    "tensv: " + c.getString(1) + " - "+
                    "malop: " + c.getString(2);
            data+= "\n";
            c.moveToNext();
        }
        Toast.makeText(MainActivity.this, data, Toast.LENGTH_LONG).show();
        c.close();
    }
}