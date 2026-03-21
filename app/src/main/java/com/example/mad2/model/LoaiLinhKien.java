package com.example.mad2.model;

import java.io.Serializable;

//Cho phép object của class này được “biến thành dạng byte” để truyền đi / lưu trữ
//Hiểu đơn giản:
//Object (Java) → chuyển thành dữ liệu → gửi qua nơi khác → dựng lại object
//Quá trình này gọi là serialization
//Giả sử bạn có object:
//LinhKien lk = new LinhKien("R001", "Điện trở");
//👉 Android KHÔNG thể truyền object trực tiếp giữa Activity
//👉 Phải “đóng gói” nó lại
//➡️ Serializable giúp làm việc này
//Khi nào cần dùng?
//1. 🔥 Truyền dữ liệu giữa Activity
//Intent intent = new Intent(this, DetailActivity.class);
//intent.putExtra("linh_kien", lk); // lk phải Serializable
//startActivity(intent);
//👉 Bên kia nhận:
//LinhKien lk = (LinhKien) getIntent().getSerializableExtra("linh_kien");
//2. 💾 Lưu object vào file
//ObjectOutputStream oos = new ObjectOutputStream(...);
//oos.writeObject(lk);
//3. 📦 Lưu vào Bundle (Fragment, rotation)
public class LoaiLinhKien implements Serializable {
    private int id;
    private String tenLoai;
    private String moTa;
    //Slg lkien trg loai nay
    private int soLuong;

    public LoaiLinhKien() {
    }

    public LoaiLinhKien(int id, String tenLoai, String moTa, int soLuong) {
        this.id = id;
        this.tenLoai = tenLoai;
        this.moTa = moTa;
        this.soLuong = soLuong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return tenLoai;
    }
}