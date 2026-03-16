package com.example.mad2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad2.R;
import com.example.mad2.adapter.ChonLinhKienAdapter;
import com.example.mad2.database.DatabaseHelper;
import com.example.mad2.database.DatabaseManager;
import com.example.mad2.model.LinhKien;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NhapXuatActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btnHuy, btnXacNhan;
    private EditText edtSearch;
    private RecyclerView recyclerView;
    private TextView tvTongSoLuong, tvNgay, tvWarning;


    private TextView tvTongTien;
//    private NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));



    private ChonLinhKienAdapter adapter;
    private List<LinhKien> linhKienList;
    private List<LinhKien> filteredList;

    private String mode; // "NHAP" hoặc "XUAT"
    private NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_xuat);

        // Nhận mode từ Intent
        mode = getIntent().getStringExtra("mode");
        if (mode == null) mode = "NHAP";

        DatabaseManager.init(this);
        dbHelper = DatabaseManager.getInstance();

        initViews();
        setupToolbar();
        setupRecyclerView();
        loadData();
        setupListeners();

        // Set ngày hiện tại
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        tvNgay.setText(sdf.format(new Date()));
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        btnHuy = findViewById(R.id.btnHuy);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        edtSearch = findViewById(R.id.edtSearch);
        recyclerView = findViewById(R.id.recyclerView);
        tvTongSoLuong = findViewById(R.id.tvTongSoLuong);
        tvNgay = findViewById(R.id.tvNgay);
        tvWarning = findViewById(R.id.tvWarning);


        tvTongTien = findViewById(R.id.tvTongTien);


        // Đổi giao diện theo mode
        if (mode.equals("NHAP")) {
            toolbar.setTitle("Nhập Kho");
//            btnXacNhan.setBackgroundResource(R.drawable.button_primary);
//            btnXacNhan.setText("XÁC NHẬN NHẬP");
//            tvWarning.setVisibility(View.GONE);
        } else {
            toolbar.setTitle("Xuất Kho");
//            btnXacNhan.setBackgroundResource(R.drawable.button_primary);
//            btnXacNhan.setText("XÁC NHẬN XUẤT");
        }
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupRecyclerView() {
        linhKienList = new ArrayList<>();
        filteredList = new ArrayList<>();



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new ChonLinhKienAdapter(filteredList);

        // Trong NhapXuatActivity.java - dòng setupRecyclerView()
        adapter = new ChonLinhKienAdapter(filteredList, mode); // THÊM mode

        adapter.setOnItemSelectedListener(new ChonLinhKienAdapter.OnItemSelectedListener() {
            @Override
            public void onSelectionChanged(List<LinhKien> selectedList) {
                calculateTotal(selectedList);
            }

            @Override
            public void onQuantityChanged() {
                calculateTotal(adapter.getSelectedList());
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        linhKienList.clear();
        linhKienList.addAll(dbHelper.getAllLinhKien());
        filterData("");
    }

    private void setupListeners() {
        btnHuy.setOnClickListener(v -> finish());

        btnXacNhan.setOnClickListener(v -> confirmTransaction());

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterData(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(linhKienList);
        } else {
            query = query.toLowerCase();
            for (LinhKien lk : linhKienList) {
                if (lk.getMaLinhKien().toLowerCase().contains(query) ||
                        lk.getTenLinhKien().toLowerCase().contains(query)) {
                    filteredList.add(lk);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

//    private void calculateTotal(List<LinhKien> selectedList) {
//        int totalQuantity = 0;
//        double totalMoney = 0;
//        boolean hasWarning = false;
//        StringBuilder warningText = new StringBuilder();
//
//        for (LinhKien lk : selectedList) {
//            int quantity = lk.getSelectedQuantity();
//            totalQuantity += quantity;
//            totalMoney += quantity * lk.getGiaNhap();
//
//            // Kiểm tra cảnh báo cho xuất kho
//            if (mode.equals("XUAT") && quantity > lk.getSoLuongTon()) {
//                hasWarning = true;
//                warningText.append("• ").append(lk.getTenLinhKien())
//                        .append(" chỉ còn ").append(lk.getSoLuongTon())
//                        .append(", xuất ").append(quantity)
//                        .append(" → thiếu ").append(quantity - lk.getSoLuongTon())
//                        .append("\n");
//            }
//        }
//
//        tvTongSoLuong.setText(String.valueOf(totalQuantity));
//
//        // Hiển thị cảnh báo nếu có
//        if (mode.equals("XUAT")) {
//            if (hasWarning) {
//                tvWarning.setVisibility(View.VISIBLE);
//                tvWarning.setText(warningText.toString().trim());
//            } else {
//                tvWarning.setVisibility(View.GONE);
//            }
//        }
//    }


    private void calculateTotal(List<LinhKien> selectedList) {
        int totalQuantity = 0;
        double totalMoney = 0;

        for (LinhKien lk : selectedList) {
            int quantity = lk.getSelectedQuantity();
            totalQuantity += quantity;
            totalMoney += quantity * lk.getGiaNhap();
        }

        tvTongSoLuong.setText(String.valueOf(totalQuantity));
        tvTongTien.setText("Giá: " + formatter.format(totalMoney) + " VNĐ");
    }


//    private void confirmTransaction() {
//        List<LinhKien> selectedList = adapter.getSelectedList();
//
//        if (selectedList.isEmpty()) {
//            Toast.makeText(this, "Vui lòng chọn linh kiện", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Kiểm tra số lượng nhập/xuất
//        for (LinhKien lk : selectedList) {
//            if (lk.getSelectedQuantity() <= 0) {
//                Toast.makeText(this,
//                        "Vui lòng nhập số lượng cho " + lk.getTenLinhKien(),
//                        Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            if (mode.equals("XUAT") && lk.getSelectedQuantity() > lk.getSoLuongTon()) {
//                Toast.makeText(this,
//                        lk.getTenLinhKien() + " chỉ còn " + lk.getSoLuongTon() + " cái",
//                        Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }
//
//        // Cập nhật database
//        for (LinhKien lk : selectedList) {
//            if (mode.equals("NHAP")) {
//                lk.setSoLuongTon(lk.getSoLuongTon() + lk.getSelectedQuantity());
//            } else {
//                lk.setSoLuongTon(lk.getSoLuongTon() - lk.getSelectedQuantity());
//            }
//            dbHelper.updateLinhKien(lk);
//        }
//
//        String action = mode.equals("NHAP") ? "NHẬP" : "XUẤT";
//        Toast.makeText(this,
//                action + " KHO THÀNH CÔNG!\nTổng số lượng: " + tvTongSoLuong.getText(),
//                Toast.LENGTH_LONG).show();
//
//        finish();
//    }


    // Sửa confirmTransaction() - thêm dialog cảnh báo
    private void confirmTransaction() {
        List<LinhKien> selectedList = adapter.getSelectedList();

        if (selectedList.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn linh kiện", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra xuất quá số lượng
        if (mode.equals("XUAT")) {
            boolean hasError = false;
            for (LinhKien lk : selectedList) {
                if (lk.getSelectedQuantity() > lk.getSoLuongTon()) {
                    hasError = true;
                    showWarningDialog(lk);
                    break;
                }
            }
            if (hasError) return;
        }

        // Tiến hành nhập/xuất
        processTransaction();
    }

    private void showWarningDialog(LinhKien lk) {
        new AlertDialog.Builder(this)
                .setTitle("Cảnh báo")
                .setMessage("Số lượng xuất " + lk.getTenLinhKien() +
                        " (" + lk.getSelectedQuantity() + ") vượt quá tồn kho (" +
                        lk.getSoLuongTon() + ").\nVui lòng điều chỉnh lại.")
                .setPositiveButton("OK", (dialog, which) -> {
                    // Reset số lượng về 0
                    lk.setSelectedQuantity(0);
                    adapter.notifyDataSetChanged();
                    calculateTotal(adapter.getSelectedList());
                })
                .show();
    }

    private void processTransaction() {
        List<LinhKien> selectedList = adapter.getSelectedList();

        for (LinhKien lk : selectedList) {
            if (mode.equals("NHAP")) {
                lk.setSoLuongTon(lk.getSoLuongTon() + lk.getSelectedQuantity());
            } else {
                lk.setSoLuongTon(lk.getSoLuongTon() - lk.getSelectedQuantity());
            }
            dbHelper.updateLinhKien(lk);
        }

        String action = mode.equals("NHAP") ? "Nhập" : "Xuất";
        Toast.makeText(this,
                action + " kho thành công!\n" +
                        "Tổng SL: " + tvTongSoLuong.getText() + "\n" +
                        "Tổng tiền: " + tvTongTien.getText(),
                Toast.LENGTH_LONG).show();

        finish();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}