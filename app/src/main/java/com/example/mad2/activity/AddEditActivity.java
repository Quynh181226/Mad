package com.example.mad2.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.mad2.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class AddEditActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText edtMaLinhKien, edtTenLinhKien, edtSoLuong, edtGia, edtGhiChu;
//    private Spinner spinnerLoai;
private AutoCompleteTextView spinnerLoai;
    private Button btnCancel, btnSave;

    private List<LoaiLinhKien> loaiList;
    private ArrayAdapter<LoaiLinhKien> spinnerAdapter;
    private LoaiLinhKien selectedLoai;

    private String mode = Constants.MODE_ADD;
    private LinhKien currentLinhKien;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        dbHelper = DatabaseManager.getInstance(); // THÊM DÒNG NÀY


        // Nhận dữ liệu từ Intent
        getIntentData();

        initViews();
        setupToolbar();
        loadLoaiData();
        setupSpinner();
        setupListeners();

        if (mode.equals(Constants.MODE_EDIT) && currentLinhKien != null) {
            displayData();
        }
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mode = intent.getStringExtra(Constants.KEY_MODE);
        if (mode == null) mode = Constants.MODE_ADD;

        if (mode.equals(Constants.MODE_EDIT)) {
            currentLinhKien = (LinhKien) intent.getSerializableExtra(Constants.KEY_LINH_KIEN);
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
            if (mode.equals(Constants.MODE_ADD)) {
                getSupportActionBar().setTitle("Thêm linh kiện");
            } else {
                getSupportActionBar().setTitle("Sửa linh kiện");
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadLoaiData() {
        // TODO: Load từ database sau
        loaiList = new ArrayList<>();
        loaiList.add(new LoaiLinhKien(1, "Điện trở", "Các loại điện trở", 0));
        loaiList.add(new LoaiLinhKien(2, "Tụ điện", "Các loại tụ điện", 0));
        loaiList.add(new LoaiLinhKien(3, "IC", "Các loại IC", 0));
        loaiList.add(new LoaiLinhKien(4, "Cảm biến", "Các loại cảm biến", 0));
        loaiList.add(new LoaiLinhKien(5, "LED", "Các loại LED", 0));
    }

    private void setupSpinner() {
//        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, loaiList);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerLoai.setAdapter(spinnerAdapter);


        spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                loaiList
        );

        spinnerLoai.setAdapter(spinnerAdapter);
//        spinnerLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                selectedLoai = loaiList.get(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                selectedLoai = null;
//            }
//        });
        spinnerLoai.setOnItemClickListener((parent, view, position, id) -> {
            selectedLoai = loaiList.get(position);
        });
    }

    private void displayData() {
        if (currentLinhKien != null) {
            edtMaLinhKien.setText(currentLinhKien.getMaLinhKien());
            edtTenLinhKien.setText(currentLinhKien.getTenLinhKien());
            edtSoLuong.setText(String.valueOf(currentLinhKien.getSoLuongTon()));
            edtGia.setText(String.valueOf(currentLinhKien.getGiaNhap()));
            edtGhiChu.setText(currentLinhKien.getGhiChu());

            // Chọn loại tương ứng trong Spinner
            if (currentLinhKien.getLoai() != null) {
                for (int i = 0; i < loaiList.size(); i++) {
                    if (loaiList.get(i).getId() == currentLinhKien.getLoai().getId()) {
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
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitConfirmationDialog();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void showExitConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận")
                .setMessage("Bạn có chắc muốn thoát? Các thông tin chưa lưu sẽ bị mất.")
                .setPositiveButton("Ở lại", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    private void saveData() {
        // Validate dữ liệu
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

//        // TODO: Lưu vào database sau
//        Toast.makeText(this,
//                mode.equals(Constants.MODE_ADD) ? "Thêm thành công!" : "Cập nhật thành công!",
//                Toast.LENGTH_SHORT).show();
//
//        finish();
        LinhKien lk = new LinhKien();
        lk.setMaLinhKien(edtMaLinhKien.getText().toString().trim());
        lk.setTenLinhKien(edtTenLinhKien.getText().toString().trim());
        lk.setLoai(selectedLoai);
        lk.setSoLuongTon(Integer.parseInt(edtSoLuong.getText().toString()));
        lk.setGiaNhap(Double.parseDouble(edtGia.getText().toString()));
        lk.setGhiChu(edtGhiChu.getText().toString().trim());

        if (mode.equals(Constants.MODE_ADD)) {
            // Thêm mới
            dbHelper.insertLinhKien(lk);
            Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
        } else {
            // Cập nhật
            lk.setId(currentLinhKien.getId());
            dbHelper.updateLinhKien(lk);
            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            showExitConfirmationDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showExitConfirmationDialog();
    }
}