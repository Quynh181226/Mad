package com.example.mad2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad2.R;
import com.example.mad2.model.ThongBao;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<ThongBao> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ThongBao thongBao);
        void onMarkReadClick(ThongBao thongBao);
    }

    public NotificationAdapter(List<ThongBao> list) {
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThongBao item = list.get(position);

        holder.tvTieuDe.setText(item.getTieuDe());
        holder.tvNoiDung.setText(item.getNoiDung());
        holder.tvThoiGian.setText(item.getThoiGian());

        // Đánh dấu đã đọc/chưa đọc
        if (item.isDaDoc()) {
            holder.itemView.setAlpha(0.6f);
            holder.tvTieuDe.setTextColor(holder.itemView.getContext().getColor(android.R.color.darker_gray));



            holder.btnMarkRead.setVisibility(View.GONE);  // ẨN NÚT KHI ĐÃ ĐỌC

        } else {
            holder.itemView.setAlpha(1.0f);
            holder.tvTieuDe.setTextColor(holder.itemView.getContext().getColor(android.R.color.black));



            holder.btnMarkRead.setVisibility(View.VISIBLE); // HIỆN NÚT KHI CHƯA ĐỌC

        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });

        holder.btnMarkRead.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMarkReadClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTieuDe, tvNoiDung, tvThoiGian, btnMarkRead;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTieuDe = itemView.findViewById(R.id.tvTieuDe);
            tvNoiDung = itemView.findViewById(R.id.tvNoiDung);
            tvThoiGian = itemView.findViewById(R.id.tvThoiGian);
            btnMarkRead = itemView.findViewById(R.id.btnMarkRead);
        }
    }
}