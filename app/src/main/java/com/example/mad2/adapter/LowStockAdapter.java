package com.example.mad2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad2.R;
import com.example.mad2.model.LinhKien;

import java.util.List;

public class LowStockAdapter extends RecyclerView.Adapter<LowStockAdapter.ViewHolder> {

    private List<LinhKien> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(LinhKien linhKien);
    }

    public LowStockAdapter(List<LinhKien> list) {
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_low_stock, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinhKien item = list.get(position);

        holder.tvStt.setText(String.valueOf(position + 1));
        holder.tvTen.setText(item.getTenLinhKien());
        holder.tvLoai.setText(item.getLoai() != null ? item.getLoai().getTenLoai() : "Chưa phân loại");
        holder.tvSoLuong.setText(String.valueOf(item.getSoLuongTon()));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStt, tvTen, tvLoai, tvSoLuong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStt = itemView.findViewById(R.id.tvStt);
            tvTen = itemView.findViewById(R.id.tvTen);
            tvLoai = itemView.findViewById(R.id.tvLoai);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
        }
    }
}