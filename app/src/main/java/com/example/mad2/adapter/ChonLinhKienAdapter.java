package com.example.mad2.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad2.R;
import com.example.mad2.model.LinhKien;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChonLinhKienAdapter extends RecyclerView.Adapter<ChonLinhKienAdapter.ViewHolder> {

    private List<LinhKien> list;
    private List<LinhKien> selectedList = new ArrayList<>();
    private OnItemSelectedListener listener;
    private String mode;




    private NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

    public interface OnItemSelectedListener {
        void onSelectionChanged(List<LinhKien> selectedList);
        void onQuantityChanged();
    }

    public ChonLinhKienAdapter(List<LinhKien> list, String mode) {
        this.list = list;

        this.mode = mode; // THÊM DÒNG NÀY

    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    public List<LinhKien> getSelectedList() {
        return selectedList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chon_linh_kien, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinhKien item = list.get(position);

        holder.tvTen.setText(item.getTenLinhKien());
        holder.tvMa.setText("Mã: " + item.getMaLinhKien());
        holder.tvLoai.setText("Loại: " + (item.getLoai() != null ? item.getLoai().getTenLoai() : ""));
        holder.tvTonKho.setText("Tồn kho: " + item.getSoLuongTon());
        holder.edtSoLuong.setText(String.valueOf(item.getSelectedQuantity()));

        // 1. Thêm formatter ở đầu class
//        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

// 2. Trong onBindViewHolder(), thêm dòng này
        holder.tvGia.setText("Giá: " + formatter.format(item.getGiaNhap()) + " VNĐ");

        // Kiểm tra xem item đã được chọn chưa
        holder.checkBox.setChecked(selectedList.contains(item));

        // Xử lý checkbox
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!selectedList.contains(item)) {
                        selectedList.add(item);
                    }
                } else {
                    selectedList.remove(item);
                    item.setSelectedQuantity(0);
                    holder.edtSoLuong.setText("0");
                }
                if (listener != null) {
                    listener.onSelectionChanged(selectedList);
                    listener.onQuantityChanged();
                }
            }
        });

        // Xử lý ô nhập số lượng
        holder.edtSoLuong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (selectedList.contains(item)) {
                    int quantity = 0;
                    try {
                        quantity = Integer.parseInt(s.toString());
                    } catch (NumberFormatException e) {
                        quantity = 0;
                    }
                    item.setSelectedQuantity(quantity);
                    if (listener != null) {
                        listener.onQuantityChanged();
                    }
                }
            }
        });





// Trong onBindViewHolder, thêm xử lý nút - và +
        holder.btnMinus.setOnClickListener(v -> {
            int current = item.getSelectedQuantity();
            if (current > 0) {
                item.setSelectedQuantity(current - 1);
                holder.edtSoLuong.setText(String.valueOf(current - 1));
                if (listener != null) listener.onQuantityChanged();
            }
        });

//        holder.btnPlus.setOnClickListener(v -> {
//            int current = item.getSelectedQuantity();
//            // Giới hạn tối đa (có thể set theo tồn kho khi xuất)
//            if (mode.equals("XUAT") && current >= item.getSoLuongTon()) {
//                Toast.makeText(holder.itemView.getContext(),
//                        "Không thể vượt quá tồn kho", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            item.setSelectedQuantity(current + 1);
//            holder.edtSoLuong.setText(String.valueOf(current + 1));
//            if (listener != null) listener.onQuantityChanged();
//        });

        holder.btnPlus.setOnClickListener(v -> {
            int current = item.getSelectedQuantity();

            // THÊM KIỂM TRA NULL Ở ĐÂY
            if (mode != null && mode.equals("XUAT") && current >= item.getSoLuongTon()) {
                Toast.makeText(holder.itemView.getContext(),
                        "Không thể vượt quá tồn kho", Toast.LENGTH_SHORT).show();
                return;
            }
            item.setSelectedQuantity(current + 1);
            holder.edtSoLuong.setText(String.valueOf(current + 1));
            if (listener != null) listener.onQuantityChanged();
        });
    }







    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView tvTen, tvMa, tvLoai, tvTonKho,        tvGia;
        EditText edtSoLuong;

        Button btnMinus, btnPlus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            tvTen = itemView.findViewById(R.id.tvTen);
            tvMa = itemView.findViewById(R.id.tvMa);
            tvLoai = itemView.findViewById(R.id.tvLoai);


            tvGia = itemView.findViewById(R.id.tvGia);


            tvTonKho = itemView.findViewById(R.id.tvTonKho);
            edtSoLuong = itemView.findViewById(R.id.edtSoLuong);


            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
        }
    }
}