package com.example.mad2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad2.R;
import com.example.mad2.adapter.LowStockAdapter;
import com.example.mad2.database.DatabaseHelper;
import com.example.mad2.database.DatabaseManager;
import com.example.mad2.model.LinhKien;
import com.example.mad2.model.LoaiLinhKien;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class LowStockActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private Button btnNhapHang;
    private LowStockAdapter adapter;
    private List<LinhKien> lowStockList;



    private BottomNavigationView bottomNavigation;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_low_stock);

        dbHelper = DatabaseManager.getInstance();

        initViews();
        setupToolbar();
        setupRecyclerView();
        loadData();
        setupListeners();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        btnNhapHang = findViewById(R.id.btnNhapHang);




        bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.setSelectedItemId(R.id.nav_low_stock);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupRecyclerView() {
        lowStockList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LowStockAdapter(lowStockList);
        adapter.setOnItemClickListener(new LowStockAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LinhKien linhKien) {
                // TODO: Mở chi tiết linh kiện
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        // TODO: Load từ database sau
        lowStockList.clear();

//        LoaiLinhKien loai1 = new LoaiLinhKien(1, "Cảm biến", "", 0);
//        LoaiLinhKien loai2 = new LoaiLinhKien(2, "Điện trở", "", 0);
//        LoaiLinhKien loai3 = new LoaiLinhKien(3, "IC", "", 0);
//        LoaiLinhKien loai4 = new LoaiLinhKien(4, "Tụ điện", "", 0);
//        LoaiLinhKien loai5 = new LoaiLinhKien(5, "LED", "", 0);
//
//        lowStockList.add(new LinhKien(1, "S001", "Cảm biến DHT11", loai1, 2, 50000, "", ""));
//        lowStockList.add(new LinhKien(2, "R003", "Điện trở 1kΩ", loai2, 5, 500, "", ""));
//        lowStockList.add(new LinhKien(3, "I001", "IC LM358", loai3, 8, 15000, "", ""));
//        lowStockList.add(new LinhKien(4, "C002", "Tụ hóa 1000µF", loai4, 7, 5000, "", ""));
//        lowStockList.add(new LinhKien(5, "L001", "LED đỏ", loai5, 4, 1000, "", ""));
        lowStockList.addAll(dbHelper.getLinhKienTonKhoThap(10));

        adapter.notifyDataSetChanged();
    }

    private void setupListeners() {
        btnNhapHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Mở màn hình nhập kho với các linh kiện này
                Intent intent = new Intent(LowStockActivity.this, NhapXuatActivity.class);
                intent.putExtra("mode", "NHAP");
                startActivity(intent);
            }
        });





        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            } else if (id == R.id.nav_low_stock) {
                return true;
            } else if (id == R.id.nav_bell) {
                startActivity(new Intent(this, NotificationActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            }
            return false;
        });
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