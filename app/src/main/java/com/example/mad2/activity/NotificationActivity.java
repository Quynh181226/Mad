package com.example.mad2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad2.R;
import com.example.mad2.adapter.NotificationAdapter;
import com.example.mad2.model.ThongBao;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btnMarkAllRead;
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<ThongBao> thongBaoList;
    private List<ThongBao> filteredList;





    private BottomNavigationView bottomNavigation;





    private TabLayout tabLayout;


    private boolean showingUnread = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        initViews();
        setupToolbar();
        setupRecyclerView();
        loadData();
        setupListeners();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
//        btnChuaDoc = findViewById(R.id.btnChuaDoc);
//        btnDaDoc = findViewById(R.id.btnDaDoc);
        btnMarkAllRead = findViewById(R.id.btnMarkAllRead);
        recyclerView = findViewById(R.id.recyclerView);



        tabLayout = findViewById(R.id.tabLayout);






        bottomNavigation = findViewById(R.id.bottomNavigation);




        bottomNavigation.setSelectedItemId(R.id.nav_bell);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupRecyclerView() {
        thongBaoList = new ArrayList<>();
        filteredList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotificationAdapter(filteredList);
        adapter.setOnItemClickListener(new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ThongBao thongBao) {
                // TODO: Mở chi tiết thông báo
                Toast.makeText(NotificationActivity.this,
                        "Click: " + thongBao.getTieuDe(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMarkReadClick(ThongBao thongBao) {
                markAsRead(thongBao);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        // TODO: Load từ database sau
        thongBaoList.clear();

        thongBaoList.add(new ThongBao(1, "Cảnh báo tồn kho thấp",
                "Cảm biến DHT11 chỉ còn 2 cái", "09:45 26/02", false, "TON_KHO_THAP"));
        thongBaoList.add(new ThongBao(2, "Cảnh báo tồn kho thấp",
                "Điện trở 1kΩ chỉ còn 5 cái", "08:30 26/02", false, "TON_KHO_THAP"));
        thongBaoList.add(new ThongBao(3, "Cảnh báo tồn kho thấp",
                "IC LM358 chỉ còn 8 cái", "Hôm qua", false, "TON_KHO_THAP"));
        thongBaoList.add(new ThongBao(4, "Cảnh báo tồn kho thấp",
                "Tụ hóa 1000µF chỉ còn 7 cái", "Hôm qua", false, "TON_KHO_THAP"));
        thongBaoList.add(new ThongBao(5, "Cảnh báo tồn kho thấp",
                "LED đỏ chỉ còn 4 cái", "24/02", false, "TON_KHO_THAP"));
        thongBaoList.add(new ThongBao(6, "Nhập kho",
                "Đã nhập 20 Điện trở 100Ω", "23/02", true, "NHAP_KHO"));
        thongBaoList.add(new ThongBao(7, "Xuất kho",
                "Đã xuất 5 IC LM358", "22/02", true, "XUAT_KHO"));
        thongBaoList.add(new ThongBao(8, "Nhập kho",
                "Đã nhập 10 Cảm biến DHT11", "21/02", true, "NHAP_KHO"));

        filterUnread();
    }

    private void setupListeners() {

        // Nhớ tìm lại btnDaDoc: cái này trg xml
//        btnChuaDoc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showingUnread = true;
//                btnChuaDoc.setBackgroundResource(R.drawable.button_primary);
//                btnDaDoc.setBackgroundResource(R.drawable.button_danger);
//                filterUnread();
//            }
//        });
//
//        btnDaDoc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showingUnread = false;
//                btnDaDoc.setBackgroundResource(R.drawable.button_primary);
//                btnChuaDoc.setBackgroundResource(R.drawable.button_danger);
//                filterRead();
//            }
//        });

        btnMarkAllRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markAllAsRead();
            }
        });




        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    showingUnread = true;
                    filterUnread();



                    btnMarkAllRead.setVisibility(View.VISIBLE);  // THÊM DÒNG NÀY

                } else {
                    showingUnread = false;
                    filterRead();




                    btnMarkAllRead.setVisibility(View.GONE);     // THÊM DÒNG NÀY

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });







        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(NotificationActivity.this, MainActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_low_stock) {
                startActivity(new Intent(NotificationActivity.this, LowStockActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_bell) {
                // Đang ở notification rồi
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(NotificationActivity.this, ProfileActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }





    // Cập nhật số lượng trên tab
    private void updateTabCounts() {
        int unreadCount = 0;
        int readCount = 0;
        for (ThongBao tb : thongBaoList) {
            if (tb.isDaDoc()) readCount++;
            else unreadCount++;
        }

        TabLayout.Tab unreadTab = tabLayout.getTabAt(0);
        TabLayout.Tab readTab = tabLayout.getTabAt(1);

        if (unreadTab != null) unreadTab.setText("CHƯA ĐỌC (" + unreadCount + ")");
        if (readTab != null) readTab.setText("ĐÃ ĐỌC (" + readCount + ")");
    }


    private void filterUnread() {
        filteredList.clear();
        for (ThongBao tb : thongBaoList) {
            if (!tb.isDaDoc()) {
                filteredList.add(tb);
            }
        }
        adapter.notifyDataSetChanged();
        updateTabCounts(); // Cập nhật tab thay vì button
    }

    private void filterRead() {
        filteredList.clear();
        for (ThongBao tb : thongBaoList) {
            if (tb.isDaDoc()) {
                filteredList.add(tb);
            }
        }
        adapter.notifyDataSetChanged();
        updateTabCounts(); // Cập nhật tab thay vì button
    }




//    private void filterUnread() {
//        filteredList.clear();
//        for (ThongBao tb : thongBaoList) {
//            if (!tb.isDaDoc()) {
//                filteredList.add(tb);
//            }
//        }
//        adapter.notifyDataSetChanged();
////        updateButtonTexts();
//    }
//
//    private void filterRead() {
//        filteredList.clear();
//        for (ThongBao tb : thongBaoList) {
//            if (tb.isDaDoc()) {
//                filteredList.add(tb);
//            }
//        }
//        adapter.notifyDataSetChanged();
////        updateButtonTexts();
//    }

//    private void updateButtonTexts() {
//        int unreadCount = 0;
//        int readCount = 0;
//        for (ThongBao tb : thongBaoList) {
//            if (tb.isDaDoc()) readCount++;
//            else unreadCount++;
//        }
//        btnChuaDoc.setText("CHƯA ĐỌC (" + unreadCount + ")");
//        btnDaDoc.setText("ĐÃ ĐỌC (" + readCount + ")");
//    }

    private void markAsRead(ThongBao thongBao) {
        thongBao.setDaDoc(true);
        if (showingUnread) {
            filterUnread();
        } else {
            filterRead();
        }
//        updateButtonTexts();
    }

    private void markAllAsRead() {
        for (ThongBao tb : thongBaoList) {
            tb.setDaDoc(true);
        }
        if (showingUnread) {
            filterUnread();
        } else {
            filterRead();
        }
//        updateButtonTexts();
        Toast.makeText(this, "Đã đánh dấu tất cả đã đọc", Toast.LENGTH_SHORT).show();
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