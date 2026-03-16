//package com.example.mad2.activity;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//
//import androidx.appcompat.widget.SearchView;  // SỬA IMPORT NÀY
//
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;  // THÊM DÒNG NÀY
//import android.widget.ImageView;
////import android.widget.SearchView;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.mad2.R;
//import com.example.mad2.adapter.LinhKienAdapter;
//import com.example.mad2.database.DatabaseHelper;
//import com.example.mad2.database.DatabaseManager;
//import com.example.mad2.model.LinhKien;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MainActivity extends AppCompatActivity {
//
//    private RecyclerView recyclerView;
//    private LinhKienAdapter adapter;
//    private List<LinhKien> linhKienList = new ArrayList<>();
//    private DatabaseHelper dbHelper;
//    private TextView tvTotalCount;
//    private ImageView avatarIcon;
//    private TextView tvNotificationBadge;
//    private Toolbar toolbar;
//    private FloatingActionButton fabAdd;
//
//    private SearchView searchView;
//    private Spinner spinnerFilter;
//    private BottomNavigationView bottomNavigation;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // KHỞI TẠO DATABASE
//        DatabaseManager.init(this);
//        dbHelper = DatabaseManager.getInstance();
//
//        initViews();
//        setupToolbar();
//        setupRecyclerView();
//        loadData(); // LOAD TỪ DATABASE THẬT
//        setupListeners();
//    }
//
//    private void initViews() {
//        recyclerView = findViewById(R.id.recyclerView);
////        tvTotalCount = findViewById(R.id.tvTotalCount);
//        avatarIcon = findViewById(R.id.nav_bell);
//        tvNotificationBadge = findViewById(R.id.tvNotificationBadge);
//        toolbar = findViewById(R.id.toolbar);
//        fabAdd = findViewById(R.id.fabAdd);
////        fabSync = findViewById(R.id.fabSync);
//
////        searchView = findViewById(R.id.searchView);
////        spinnerFilter = findViewById(R.id.spinnerFilter);
//        bottomNavigation = findViewById(R.id.bottomNavigation);
//    }
//
//    private void setupToolbar() {
//        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle("KHO LINH KIỆN");
//            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        }
//    }
//
//    private void setupRecyclerView() {
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new LinhKienAdapter(linhKienList);
//
//        adapter.setOnItemClickListener(new LinhKienAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(LinhKien linhKien) {
//                Toast.makeText(MainActivity.this, "Click: " + linhKien.getTenLinhKien(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        recyclerView.setAdapter(adapter);
//    }
//
//    // Thêm hàm filter
//    private void filterData(String query) {
//        // TODO: Filter list theo query
//    }
//
//    private void filterByCategory(String category) {
//        // TODO: Filter list theo category
//    }
//
////    private void loadData() {
////        linhKienList.clear();
////
////        // LOAD TỪ DATABASE THẬT
////        linhKienList.addAll(dbHelper.getAllLinhKien());
////
////        adapter.notifyDataSetChanged();
////        tvTotalCount.setText("Tổng: " + linhKienList.size() + " linh kiện");
////
////        // CẬP NHẬT BADGE THÔNG BÁO
////        int lowStockCount = dbHelper.countLowStock();
////        if (lowStockCount > 0) {
////            tvNotificationBadge.setVisibility(View.VISIBLE);
////            tvNotificationBadge.setText(String.valueOf(lowStockCount));
////        } else {
////            tvNotificationBadge.setVisibility(View.GONE);
////        }
////    }
//
//    private void loadData() {
//        linhKienList.clear();
//
//        // LOAD TỪ DATABASE THẬT
//        linhKienList.addAll(dbHelper.getAllLinhKien());
//
//        // THÊM LOG ĐỂ KIỂM TRA
//        Log.d("MainActivity", "Số linh kiện load được: " + linhKienList.size());
//        for (LinhKien lk : linhKienList) {
//            Log.d("MainActivity", "Linh kiện: " + lk.getTenLinhKien());
//        }
//
//        adapter.notifyDataSetChanged();
//        tvTotalCount.setText("Tổng: " + linhKienList.size() + " linh kiện");
//
//        // CẬP NHẬT BADGE THÔNG BÁO
//        int lowStockCount = dbHelper.countLowStock();
//        if (lowStockCount > 0) {
//            tvNotificationBadge.setVisibility(View.VISIBLE);
//            tvNotificationBadge.setText(String.valueOf(lowStockCount));
//        } else {
//            tvNotificationBadge.setVisibility(View.GONE);
//        }
//    }
//
//    private void setupListeners() {
//        avatarIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        fabAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
//                intent.putExtra("mode", "add");
//                startActivity(intent);
//            }
//        });
//
////        fabSync.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Toast.makeText(MainActivity.this, "Đang đồng bộ...", Toast.LENGTH_SHORT).show();
////            }
////        });
//
//        // Xử lý search
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                filterData(query);
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                filterData(newText);
//                return false;
//            }
//        });
//
//        // Xử lý filter
//        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selected = parent.getItemAtPosition(position).toString();
//                filterByCategory(selected);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {}
//        });
//
//        // Xử lý bottom navigation
//        bottomNavigation.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id = item.getItemId();
//                if (id == R.id.nav_home) {
//                    return true;
//                } else if (id == R.id.nav_list) {
//                    startActivity(new Intent(MainActivity.this, CategoryActivity.class));
//                    return true;
//                } else if (id == R.id.nav_profile) {
//                    startActivity(new Intent(MainActivity.this, NotificationActivity.class));
//                    return true;
//                }
//                return false;
//            }
//        });
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_filter) {
//            Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
//            startActivity(intent);
//            return true;
//        } else if (id == R.id.action_low_stock) {
//            Intent intent = new Intent(MainActivity.this, LowStockActivity.class);
//            startActivity(intent);
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        loadData(); // Refresh dữ liệu khi quay lại màn hình
//    }
//}
package com.example.mad2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mad2.R;
import com.example.mad2.adapter.LinhKienAdapter;
import com.example.mad2.database.DatabaseHelper;
import com.example.mad2.database.DatabaseManager;
import com.example.mad2.model.LinhKien;
import com.example.mad2.model.LoaiLinhKien;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinhKienAdapter adapter;
    private List<LinhKien> linhKienList = new ArrayList<>();
    private List<LinhKien> originalList = new ArrayList<>();

    private DatabaseHelper dbHelper;
    private Toolbar toolbar;
    private Spinner toolbarSpinner;
    private FloatingActionButton fabAdd;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseManager.init(this);
        dbHelper = DatabaseManager.getInstance();

        initViews();
        setupToolbar();
        setupSpinner();
        setupRecyclerView();
        loadData();
        setupListeners();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolbar);
        toolbarSpinner = findViewById(R.id.toolbar_spinner);
        fabAdd = findViewById(R.id.fabAdd);
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Kho Linh Kiện");
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    private void setupSpinner() {
        List<LoaiLinhKien> loaiList = dbHelper.getAllLoai();
        List<String> categoryNames = new ArrayList<>();
        categoryNames.add("Tất cả");
        for (LoaiLinhKien loai : loaiList) {
            categoryNames.add(loai.getTenLoai());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categoryNames
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toolbarSpinner.setAdapter(spinnerAdapter);

        toolbarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                filterByCategory(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void filterByCategory(String category) {
        if (category.equals("Tất cả")) {
            linhKienList.clear();
            linhKienList.addAll(originalList);
        } else {
            List<LinhKien> filtered = new ArrayList<>();
            for (LinhKien lk : originalList) {
                if (lk.getLoai() != null && lk.getLoai().getTenLoai().equals(category)) {
                    filtered.add(lk);
                }
            }
            linhKienList.clear();
            linhKienList.addAll(filtered);
        }
        adapter.notifyDataSetChanged();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LinhKienAdapter(linhKienList);

        adapter.setOnItemClickListener(linhKien ->
                Toast.makeText(MainActivity.this, "Click: " + linhKien.getTenLinhKien(), Toast.LENGTH_SHORT).show()
        );

        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        // Load dữ liệu từ database
        originalList.clear();
        originalList.addAll(dbHelper.getAllLinhKien());

        linhKienList.clear();
        linhKienList.addAll(originalList);

        Log.d("MainActivity", "Số linh kiện load được: " + linhKienList.size());
        adapter.notifyDataSetChanged();
    }

    private void setupListeners() {
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            intent.putExtra("mode", "add");
            startActivity(intent);
        });

        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                return true;
            } else if (id == R.id.nav_low_stock) {
                startActivity(new Intent(MainActivity.this, LowStockActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_bell) {
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
//                Toast.makeText(this, "Cá nhân", Toast.LENGTH_SHORT).show();
//                return true;

                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                return true;
            }
            return false;
        });

        bottomNavigation.setSelectedItemId(R.id.nav_home);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_filter) {
//            startActivity(new Intent(MainActivity.this, CategoryActivity.class));
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        bottomNavigation.setSelectedItemId(R.id.nav_home);
    }
}