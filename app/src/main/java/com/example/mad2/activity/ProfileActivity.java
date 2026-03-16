package com.example.mad2.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mad2.R;
import com.example.mad2.database.DatabaseHelper;
import com.example.mad2.database.DatabaseManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView statsTotal, statsLowStock, statsCategories;
    private Button btnLogout;
    private DatabaseHelper dbHelper;



    private BottomNavigationView bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dbHelper = DatabaseManager.getInstance();

        initViews();
        setupToolbar();
        loadStats();
        setupListeners();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        statsTotal = findViewById(R.id.statsTotal);
        statsLowStock = findViewById(R.id.statsLowStock);
        statsCategories = findViewById(R.id.statsCategories);
        btnLogout = findViewById(R.id.btnLogout);




        bottomNavigation = findViewById(R.id.bottomNavigation);

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadStats() {
        int total = dbHelper.getAllLinhKien().size();
        int lowStock = dbHelper.countLowStock();
        int categories = dbHelper.getAllLoai().size();

        statsTotal.setText(String.valueOf(total));
        statsLowStock.setText(String.valueOf(lowStock));
        statsCategories.setText(String.valueOf(categories));
    }

    private void setupListeners() {
//        // Nhập kho
//        findViewById(R.id.menuNhapKho).setOnClickListener(v -> {
//            Intent intent = new Intent(ProfileActivity.this, NhapXuatActivity.class);
//            intent.putExtra("mode", "NHAP");
//            startActivity(intent);
//        });
//
//        // Xuất kho
//        findViewById(R.id.menuXuatKho).setOnClickListener(v -> {
//            Intent intent = new Intent(ProfileActivity.this, NhapXuatActivity.class);
//            intent.putExtra("mode", "XUAT");
//            startActivity(intent);
//        });



        // Nhập kho
        findViewById(R.id.menuNhapKho).setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, NhapXuatActivity.class);
            intent.putExtra("mode", "NHAP");
            startActivity(intent);
        });

        // Xuất kho
        findViewById(R.id.menuXuatKho).setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, NhapXuatActivity.class);
            intent.putExtra("mode", "XUAT");
            startActivity(intent);
        });


        // Giới thiệu
        findViewById(R.id.menuAbout).setOnClickListener(v -> showAboutDialog());

        // Đăng xuất
        btnLogout.setOnClickListener(v -> showLogoutConfirmation());






        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_low_stock) {
                startActivity(new Intent(ProfileActivity.this, LowStockActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_bell) {
                startActivity(new Intent(ProfileActivity.this, NotificationActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_profile) {
                return true; // Đang ở profile
            }
            return false;
        });

        bottomNavigation.setSelectedItemId(R.id.nav_profile);
    }

    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Giới thiệu");
        builder.setMessage("Ứng dụng quản lý kho linh kiện điện tử\n\n" +
                "Phiên bản: 1.0.0\n" +
                "Phát triển bởi: B24DTCN442_PhamHuongQuynh\n" +
                "Môn học: Phát triển ứng dụng trên thiết bị di động (mobile)\n\n" +
                "Chức năng chính:\n" +
                "• Quản lý linh kiện (Thêm/Sửa/Xóa)\n" +
                "• Nhập/Xuất kho\n" +
                "• Cảnh báo tồn kho thấp\n" +
                "• Thống kê theo loại\n" +
                "• Thông báo");
        builder.setPositiveButton("Ok", null);
        builder.show();
    }

    private void showLogoutConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đăng xuất");
        builder.setMessage("Bạn có chắc muốn đăng xuất?");
        builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                performLogout();
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void performLogout() {
        // Xóa trạng thái đăng nhập
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        prefs.edit().putBoolean("isLoggedIn", false).apply();

        // Quay về màn hình đăng nhập
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

        Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
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