package com.example.mad2.model;

public class ThongBao {
    private int id;
    private String tieuDe;
    private String noiDung;
    private String thoiGian;
    private boolean daDoc;
    private String loai; // "TON_KHO_THAP", "NHAP_KHO", "XUAT_KHO"

    public ThongBao(int id, String tieuDe, String noiDung, String thoiGian, boolean daDoc, String loai) {
        this.id = id;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
        this.daDoc = daDoc;
        this.loai = loai;
    }

    // Getters và Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTieuDe() { return tieuDe; }
    public void setTieuDe(String tieuDe) { this.tieuDe = tieuDe; }

    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { this.noiDung = noiDung; }

    public String getThoiGian() { return thoiGian; }
    public void setThoiGian(String thoiGian) { this.thoiGian = thoiGian; }

    public boolean isDaDoc() { return daDoc; }
    public void setDaDoc(boolean daDoc) { this.daDoc = daDoc; }

    public String getLoai() { return loai; }
    public void setLoai(String loai) { this.loai = loai; }
}