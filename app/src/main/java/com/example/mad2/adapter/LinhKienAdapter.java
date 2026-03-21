//package com.example.mad2.adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.mad2.R;
//import com.example.mad2.model.LinhKien;
//
//import java.text.NumberFormat;
//import java.util.List;
//import java.util.Locale;
//
//public class LinhKienAdapter extends RecyclerView.Adapter<LinhKienAdapter.ViewHolder> {
//
//    public static final int MODE_DISPLAY = 0;
//    public static final int MODE_SELECT = 1;
//
//    private List<LinhKien> list;
//    private List<LinhKien> selectedList;
//    private int mode = MODE_DISPLAY;
//    private OnItemClickListener itemClickListener;
//    private OnQuantityChangeListener quantityChangeListener;
//    private NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
//
//    public interface OnItemClickListener {
//        void onItemClick(LinhKien linhKien);
//    }
//
//    public interface OnQuantityChangeListener {
//        void onQuantityChange();
//    }
//
//    public LinhKienAdapter(List<LinhKien> list) {
//        this.list = list;
//    }
//
//    public void setMode(int mode) {
//        this.mode = mode;
//    }
//
//    public void setSelectedList(List<LinhKien> selectedList) {
//        this.selectedList = selectedList;
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.itemClickListener = listener;
//    }
//
//    public void setOnQuantityChangeListener(OnQuantityChangeListener listener) {
//        this.quantityChangeListener = listener;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_linh_kien, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        LinhKien item = list.get(position);
//
//        holder.tvMa.setText(item.getMaLinhKien());
//        holder.tvTen.setText(item.getTenLinhKien());
//        holder.tvLoai.setText(item.getLoai() != null ? item.getLoai().getTenLoai() : "");
//        holder.tvSoLuong.setText(String.valueOf(item.getSoLuongTon()));
//        holder.tvGia.setText(formatter.format(item.getGiaNhap()) + "đ");
//
//        // Đổi màu nếu tồn kho thấp
//        if (item.getSoLuongTon() < 10) {
//            holder.tvSoLuong.setTextColor(holder.itemView.getContext().getColor(android.R.color.holo_red_dark));
//            holder.tvSoLuong.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_warning, 0);
//        } else {
//            holder.tvSoLuong.setTextColor(holder.itemView.getContext().getColor(android.R.color.black));
//            holder.tvSoLuong.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//        }
//
//        if (mode == MODE_SELECT) {
//            holder.checkBox.setVisibility(View.VISIBLE);
//            holder.checkBox.setChecked(selectedList != null && selectedList.contains(item));
//
//            holder.edtQuantity.setVisibility(View.VISIBLE);
//            holder.edtQuantity.setText(String.valueOf(item.getSelectedQuantity()));
//
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (itemClickListener != null) {
//                        itemClickListener.onItemClick(item);
//                    }
//                }
//            });
//
//            holder.edtQuantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    if (!hasFocus) {
//                        String text = holder.edtQuantity.getText().toString();
//                        int quantity = text.isEmpty() ? 0 : Integer.parseInt(text);
//                        item.setSelectedQuantity(quantity);
//                        if (quantityChangeListener != null) {
//                            quantityChangeListener.onQuantityChange();
//                        }
//                    }
//                }
//            });
//        } else {
//            holder.checkBox.setVisibility(View.GONE);
//            holder.edtQuantity.setVisibility(View.GONE);
//
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (itemClickListener != null) {
//                        itemClickListener.onItemClick(item);
//                    }
//                }
//            });
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView tvMa, tvTen, tvLoai, tvSoLuong, tvGia;
//        CheckBox checkBox;
//        EditText edtQuantity;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tvMa = itemView.findViewById(R.id.tvMa);
//            tvTen = itemView.findViewById(R.id.tvTen);
//            tvLoai = itemView.findViewById(R.id.tvLoai);
//            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
//            tvGia = itemView.findViewById(R.id.tvGia);
//            checkBox = itemView.findViewById(R.id.checkBox);
//            edtQuantity = itemView.findViewById(R.id.edtQuantity);
//        }
//    }
//}
package com.example.mad2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad2.R;
import com.example.mad2.database.DatabaseHelper;
import com.example.mad2.model.LinhKien;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;







import androidx.appcompat.app.AlertDialog;
import android.content.Intent;
import com.example.mad2.activity.AddEditActivity;
import com.example.mad2.database.DatabaseManager;
import com.example.mad2.utils.Constants;

public class LinhKienAdapter extends RecyclerView.Adapter<LinhKienAdapter.ViewHolder> {

    public static final int MODE_DISPLAY = 0;
    public static final int MODE_SELECT = 1;

    private List<LinhKien> list;
    private List<LinhKien> selectedList;
    private int mode = MODE_DISPLAY;
    private OnItemClickListener itemClickListener;
    private OnQuantityChangeListener quantityChangeListener;
    private NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

    public interface OnItemClickListener {
        void onItemClick(LinhKien linhKien);
    }

    public interface OnQuantityChangeListener {
        void onQuantityChange();
    }

    public LinhKienAdapter(List<LinhKien> list) {
        this.list = list;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setSelectedList(List<LinhKien> selectedList) {
        this.selectedList = selectedList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setOnQuantityChangeListener(OnQuantityChangeListener listener) {
        this.quantityChangeListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_linh_kien, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        if (list == null || list.isEmpty()) return;
//
//        LinhKien item = list.get(position);
//
//        holder.tvMa.setText(item.getMaLinhKien());
//        holder.tvTen.setText(item.getTenLinhKien());
//        holder.tvLoai.setText(item.getLoai() != null ? item.getLoai().getTenLoai() : "Loại: ");
//        holder.tvSoLuong.setText(String.valueOf(item.getSoLuongTon()));
//        holder.tvGia.setText(formatter.format(item.getGiaNhap()) + " VNĐ");
        if (list == null || list.isEmpty()) return;

        LinhKien item = list.get(position);

// Mã linh kiện
        holder.tvMa.setText("Mã: " + item.getMaLinhKien());

// Tên linh kiện
        holder.tvTen.setText(item.getTenLinhKien());

// Loại linh kiện
        holder.tvLoai.setText("Loại: " + (item.getLoai() != null ? item.getLoai().getTenLoai() : ""));

// Tồn kho
        holder.tvSoLuong.setText("Tồn: " + item.getSoLuongTon());

// Giá nhập
        holder.tvGia.setText("Giá: " + formatter.format(item.getGiaNhap()) + " VNĐ");


        // Đổi màu nếu tồn kho thấp
        if (item.getSoLuongTon() < 10) {
            holder.tvSoLuong.setTextColor(holder.itemView.getContext().getColor(android.R.color.holo_red_dark));
//            holder.tvSoLuong.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_warning, 0);
        } else {
            holder.tvSoLuong.setTextColor(holder.itemView.getContext().getColor(android.R.color.black));
            holder.tvSoLuong.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

//        // Xử lý nút sửa
//        holder.btnEdit.setOnClickListener(v ->
//                Toast.makeText(holder.itemView.getContext(), "Sửa: " + item.getTenLinhKien(), Toast.LENGTH_SHORT).show()
//        );
//
//        // Xử lý nút xóa
//        holder.btnDelete.setOnClickListener(v ->
//                Toast.makeText(holder.itemView.getContext(), "Xóa: " + item.getTenLinhKien(), Toast.LENGTH_SHORT).show()
//        );



        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), AddEditActivity.class);
            intent.putExtra("mode", "edit");
            intent.putExtra("linh_kien", item);
            holder.itemView.getContext().startActivity(intent);
        });


        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc muốn xóa " + item.getTenLinhKien() + "?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        // Xóa trong database
                        DatabaseHelper dbHelper = DatabaseManager.getInstance();
                        dbHelper.deleteLinhKien(item.getId());

                        // Xóa khỏi list và cập nhật adapter
                        list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, list.size());

                        Toast.makeText(holder.itemView.getContext(),
                                "Đã xóa " + item.getTenLinhKien(), Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });

        if (mode == MODE_SELECT) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(selectedList != null && selectedList.contains(item));

            holder.edtQuantity.setVisibility(View.VISIBLE);
            holder.edtQuantity.setText(String.valueOf(item.getSelectedQuantity()));

            holder.itemView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(item);
                }
            });

            holder.edtQuantity.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    String text = holder.edtQuantity.getText().toString();
                    int quantity = text.isEmpty() ? 0 : Integer.parseInt(text);
                    item.setSelectedQuantity(quantity);
                    if (quantityChangeListener != null) {
                        quantityChangeListener.onQuantityChange();
                    }
                }
            });
        } else {
            holder.checkBox.setVisibility(View.GONE);
            holder.edtQuantity.setVisibility(View.GONE);

            holder.itemView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(item);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMa, tvTen, tvLoai, tvSoLuong, tvGia;


        ImageView btnEdit;


        ImageButton btnDelete;

        CheckBox checkBox;
        EditText edtQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMa = itemView.findViewById(R.id.tvMa);
            tvTen = itemView.findViewById(R.id.tvTen);
            tvLoai = itemView.findViewById(R.id.tvLoai);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            tvGia = itemView.findViewById(R.id.tvGia);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            checkBox = itemView.findViewById(R.id.checkBox);
            edtQuantity = itemView.findViewById(R.id.edtQuantity);
        }
    }
}