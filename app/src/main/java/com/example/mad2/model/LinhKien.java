package com.example.mad2.model;

import java.io.Serializable;

public class LinhKien implements Serializable {
    private int id;
    private String maLinhKien;
    private String tenLinhKien;
    private LoaiLinhKien loai;
    private int soLuongTon;
    private double giaNhap;
    private String ngayTao;
    private String ghiChu;
    private int selectedQuantity; // Số lượng được chọn khi nhập/xuất

    // Constructor rỗng
    public LinhKien() {
    }

    // Constructor đầy đủ
    public LinhKien(int id, String maLinhKien, String tenLinhKien, LoaiLinhKien loai,
                    int soLuongTon, double giaNhap, String ngayTao, String ghiChu) {
        this.id = id;
        this.maLinhKien = maLinhKien;
        this.tenLinhKien = tenLinhKien;
        this.loai = loai;
        this.soLuongTon = soLuongTon;
        this.giaNhap = giaNhap;
        this.ngayTao = ngayTao;
        this.ghiChu = ghiChu;
        this.selectedQuantity = 0;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaLinhKien() {
        return maLinhKien;
    }

    public void setMaLinhKien(String maLinhKien) {
        this.maLinhKien = maLinhKien;
    }

    public String getTenLinhKien() {
        return tenLinhKien;
    }

    public void setTenLinhKien(String tenLinhKien) {
        this.tenLinhKien = tenLinhKien;
    }

    public LoaiLinhKien getLoai() {
        return loai;
    }

    public void setLoai(LoaiLinhKien loai) {
        this.loai = loai;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public int getSelectedQuantity() {
        return selectedQuantity;
    }

    public void setSelectedQuantity(int selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }
}