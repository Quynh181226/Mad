//package com.example.mad2.model;
//
//public class LoaiLinhKien {
//    private int id;
//    private String tenLoai;
//    private String moTa;
//    private int soLuong;
//
//    public LoaiLinhKien(int id, String tenLoai, String moTa, int soLuong) {
//        this.id = id;
//        this.tenLoai = tenLoai;
//        this.moTa = moTa;
//        this.soLuong = soLuong;
//    }
//
//    // Getters và Setters
//    public int getId() { return id; }
//    public void setId(int id) { this.id = id; }
//
//    public String getTenLoai() { return tenLoai; }
//    public void setTenLoai(String tenLoai) { this.tenLoai = tenLoai; }
//
//    public String getMoTa() { return moTa; }
//    public void setMoTa(String moTa) { this.moTa = moTa; }
//
//    public int getSoLuong() { return soLuong; }
//    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
//
//    @Override
//    public String toString() {
//        return tenLoai;
//    }
//}
package com.example.mad2.model;

import java.io.Serializable;

public class LoaiLinhKien implements Serializable {
    private int id;
    private String tenLoai;
    private String moTa;
    private int soLuong; // Số lượng linh kiện trong loại này

    public LoaiLinhKien() {
    }

    public LoaiLinhKien(int id, String tenLoai, String moTa, int soLuong) {
        this.id = id;
        this.tenLoai = tenLoai;
        this.moTa = moTa;
        this.soLuong = soLuong;
    }

    // Getters và Setters
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