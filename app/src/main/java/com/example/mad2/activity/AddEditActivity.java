package com.example.mad2.activity;

import android.app.AlertDialog;
//import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
//import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mad2.R;
import com.example.mad2.database.DatabaseHelper;
import com.example.mad2.database.DatabaseManager;
import com.example.mad2.model.LinhKien;
import com.example.mad2.model.LoaiLinhKien;
//import com.example.mad2.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class AddEditActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText edtMaLinhKien, edtTenLinhKien, edtSoLuong, edtGia, edtGhiChu;

private AutoCompleteTextView spinnerLoai;
    private Button btnCancel, btnSave;

    private List<LoaiLinhKien> loaiList;
    private ArrayAdapter<LoaiLinhKien> spinnerAdapter;
    private LoaiLinhKien selectedLoai;

    private String mode = "add";
    private LinhKien currLinhKien;

    private DatabaseHelper dbHelper;

    //    private Spinner spinnerLoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        dbHelper = DatabaseManager.getInstance();


        // Nhận dữ liệu từ Intent
        getIntentData();

        initViews();
        setupToolbar();
        loadLoaiData();
        setupSpinner();
        setupListeners();

        if (mode.equals("edit") && currLinhKien != null) {
            displayData();
        }
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");
        if (mode == null) mode = "add";

        if (mode.equals("edit")) {
            currLinhKien = (LinhKien) intent.getSerializableExtra("linh_kien");
        }
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        edtMaLinhKien = findViewById(R.id.edtMaLinhKien);
        edtTenLinhKien = findViewById(R.id.edtTenLinhKien);
        edtSoLuong = findViewById(R.id.edtSoLuong);
        edtGia = findViewById(R.id.edtGia);
        edtGhiChu = findViewById(R.id.edtGhiChu);
        spinnerLoai = findViewById(R.id.spinnerLoai);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            if (mode.equals("add")) {
                getSupportActionBar().setTitle("Thêm linh kiện");
            } else {
                getSupportActionBar().setTitle("Sửa linh kiện");
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadLoaiData() {
        loaiList = new ArrayList<>();
        loaiList.add(new LoaiLinhKien(1, "Điện trở", "Các loại điện trở", 0));
        loaiList.add(new LoaiLinhKien(2, "Tụ điện", "Các loại tụ điện", 0));
        loaiList.add(new LoaiLinhKien(3, "IC", "Các loại IC", 0));
        loaiList.add(new LoaiLinhKien(4, "Cảm biến", "Các loại cảm biến", 0));
        loaiList.add(new LoaiLinhKien(5, "LED", "Các loại LED", 0));
    }

    private void setupSpinner() {
        spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                loaiList
        );

        spinnerLoai.setAdapter(spinnerAdapter);

        spinnerLoai.setOnItemClickListener((parent, view, position, id) -> {
            selectedLoai = loaiList.get(position);
        });
    }

    private void displayData() {
        if (currLinhKien != null) {
            edtMaLinhKien.setText(currLinhKien.getMaLinhKien());
            edtTenLinhKien.setText(currLinhKien.getTenLinhKien());
            edtSoLuong.setText(String.valueOf(currLinhKien.getSoLuongTon()));
            edtGia.setText(String.valueOf(currLinhKien.getGiaNhap()));
            edtGhiChu.setText(currLinhKien.getGhiChu());

            // Chọn loại tương ứng trong Spinner
            if (currLinhKien.getLoai() != null) {
                for (int i = 0; i < loaiList.size(); i++) {
                    if (loaiList.get(i).getId() == currLinhKien.getLoai().getId()) {
//                        spinnerLoai.setSelection(i);


                        spinnerLoai.setText(loaiList.get(i).getTenLoai(), false);
                        selectedLoai = loaiList.get(i);
                        break;
                    }
                }
            }
        }
    }

    private void setupListeners() {
        btnCancel.setOnClickListener(view -> showExitConfirmDialog());
        btnSave.setOnClickListener(v -> saveData());
    }

    private void showExitConfirmDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận")
                //Cac ttin chua save se mat
                .setMessage("Bạn có chắc muốn thoát?")
                .setPositiveButton("Ở lại", (dialog, which) -> dialog.dismiss())
                .setNegativeButton("Thoát", (dialog, which) -> finish())
                .show();
    }

    private void saveData() {
        if (edtMaLinhKien.getText().toString().trim().isEmpty()) {
            edtMaLinhKien.setError("Vui lòng nhập mã linh kiện");
            return;
        }
        if (edtTenLinhKien.getText().toString().trim().isEmpty()) {
            edtTenLinhKien.setError("Vui lòng nhập tên linh kiện");
            return;
        }
        if (selectedLoai == null) {
            Toast.makeText(this, "Vui lòng chọn loại linh kiện", Toast.LENGTH_SHORT).show();
            return;
        }

        LinhKien lk = new LinhKien();
        lk.setMaLinhKien(edtMaLinhKien.getText().toString().trim());
        lk.setTenLinhKien(edtTenLinhKien.getText().toString().trim());
        lk.setLoai(selectedLoai);
        lk.setSoLuongTon(Integer.parseInt(edtSoLuong.getText().toString()));
        lk.setGiaNhap(Double.parseDouble(edtGia.getText().toString()));
        lk.setGhiChu(edtGhiChu.getText().toString().trim());

        if (mode.equals("add")) {
            dbHelper.insertLinhKien(lk);
            Toast.makeText(this, "Thêm thành công!!", Toast.LENGTH_SHORT).show();
        } else {
            lk.setId(currLinhKien.getId());
            dbHelper.updateLinhKien(lk);
            Toast.makeText(this, "Cập nhật thành công!!", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            showExitConfirmDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showExitConfirmDialog();
    }
}