package com.example.mad2.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad2.R;
import com.example.mad2.adapter.LinhKienAdapter;
import com.example.mad2.database.DatabaseHelper;
import com.example.mad2.database.DatabaseManager;
import com.example.mad2.model.LinhKien;
import com.example.mad2.model.LoaiLinhKien;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout layoutCategories;
    private TextView tvCategoryTitle, tvTotalQuantity, tvTotalValue;
    private RecyclerView recyclerView;

    private LinhKienAdapter adapter;
    private List<LinhKien> linhKienList;
    private List<LinhKien> filteredList;
    private List<LoaiLinhKien> loaiList;

    private DatabaseHelper dbHelper;

    private LoaiLinhKien currentCategory;
    private NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        dbHelper = DatabaseManager.getInstance(); // THÊM DÒNG NÀY


        initViews();
        setupToolbar();
        loadData();
        setupCategoryButtons();
        setupRecyclerView();
//        filterByCategory(loaiList.get(0));
        if (!loaiList.isEmpty()) {
            filterByCategory(loaiList.get(0));
        }
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        layoutCategories = findViewById(R.id.layoutCategories);
        tvCategoryTitle = findViewById(R.id.tvCategoryTitle);
        tvTotalQuantity = findViewById(R.id.tvTotalQuantity);
        tvTotalValue = findViewById(R.id.tvTotalValue);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadData() {
//        // TODO: Load từ database sau
//        linhKienList = new ArrayList<>();
//        filteredList = new ArrayList<>();
//        loaiList = new ArrayList<>();
//
//        // Tạo loại
//        LoaiLinhKien loai1 = new LoaiLinhKien(1, "Điện trở", "Các loại điện trở", 4);
//        LoaiLinhKien loai2 = new LoaiLinhKien(2, "Tụ điện", "Các loại tụ điện", 2);
//        LoaiLinhKien loai3 = new LoaiLinhKien(3, "IC", "Các loại IC", 3);
//        LoaiLinhKien loai4 = new LoaiLinhKien(4, "Cảm biến", "Các loại cảm biến", 3);
//
//        loaiList.add(loai1);
//        loaiList.add(loai2);
//        loaiList.add(loai3);
//        loaiList.add(loai4);
//
//        // Tạo linh kiện
//        linhKienList.add(new LinhKien(1, "R001", "Điện trở 100Ω", loai1, 50, 500, "", "1/4W"));
//        linhKienList.add(new LinhKien(2, "R002", "Điện trở 220Ω", loai1, 30, 500, "", "1/4W"));
//        linhKienList.add(new LinhKien(3, "R003", "Điện trở 1kΩ", loai1, 5, 500, "", "1/4W"));
//        linhKienList.add(new LinhKien(4, "R004", "Điện trở 10kΩ", loai1, 25, 500, "", "1/4W"));
//
//        linhKienList.add(new LinhKien(5, "C001", "Tụ điện 100µF", loai2, 20, 2000, "", "16V"));
//        linhKienList.add(new LinhKien(6, "C002", "Tụ hóa 1000µF", loai2, 7, 5000, "", "25V"));
//
//        linhKienList.add(new LinhKien(7, "I001", "IC LM358", loai3, 8, 15000, "", ""));
//        linhKienList.add(new LinhKien(8, "I002", "IC 555", loai3, 15, 8000, "", ""));
//        linhKienList.add(new LinhKien(9, "I003", "IC 741", loai3, 12, 12000, "", ""));
//
//        linhKienList.add(new LinhKien(10, "S001", "Cảm biến DHT11", loai4, 2, 50000, "", ""));
//        linhKienList.add(new LinhKien(11, "S002", "Cảm biến DHT22", loai4, 5, 120000, "", ""));
//        linhKienList.add(new LinhKien(12, "S003", "Cảm biến PIR", loai4, 3, 45000, "", ""));
        linhKienList.clear();
        linhKienList.addAll(dbHelper.getAllLinhKien());

        loaiList.clear();
        loaiList.addAll(dbHelper.getAllLoai());
    }

    private void setupCategoryButtons() {
        layoutCategories.removeAllViews();

        for (int i = 0; i < loaiList.size(); i++) {
            LoaiLinhKien loai = loaiList.get(i);
            Button button = new Button(this);
            button.setText(loai.getTenLoai() + " (" + loai.getSoLuong() + ")");
            button.setTag(loai);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            if (i < loaiList.size() - 1) {
                params.setMargins(0, 0, 8, 0);
            }
            button.setLayoutParams(params);

            if (i == 0) {
                button.setBackgroundResource(R.drawable.button_primary);
            } else {
                button.setBackgroundResource(R.drawable.edit_text_border);
                button.setTextColor(getColor(android.R.color.black));
            }

            final int index = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Reset all buttons
                    for (int j = 0; j < layoutCategories.getChildCount(); j++) {
                        Button btn = (Button) layoutCategories.getChildAt(j);
                        btn.setBackgroundResource(R.drawable.edit_text_border);
                        btn.setTextColor(getColor(android.R.color.black));
                    }
                    // Highlight selected button
                    button.setBackgroundResource(R.drawable.button_primary);
                    button.setTextColor(getColor(android.R.color.white));

                    filterByCategory(loaiList.get(index));
                }
            });

            layoutCategories.addView(button);
        }
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LinhKienAdapter(filteredList);
        adapter.setMode(LinhKienAdapter.MODE_DISPLAY);
        recyclerView.setAdapter(adapter);
    }

    private void filterByCategory(LoaiLinhKien loai) {
        currentCategory = loai;
        filteredList.clear();

        int totalQuantity = 0;
        double totalValue = 0;

        for (LinhKien lk : linhKienList) {
            if (lk.getLoai() != null && lk.getLoai().getId() == loai.getId()) {
                filteredList.add(lk);
                totalQuantity += lk.getSoLuongTon();
                totalValue += lk.getSoLuongTon() * lk.getGiaNhap();
            }
        }

        tvCategoryTitle.setText(loai.getTenLoai().toUpperCase() + " (" + filteredList.size() + " sản phẩm)");
        tvTotalQuantity.setText(String.valueOf(totalQuantity));
        tvTotalValue.setText(formatter.format(totalValue) + "đ");

        adapter.notifyDataSetChanged();
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