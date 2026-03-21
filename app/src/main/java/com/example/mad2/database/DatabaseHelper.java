package com.example.mad2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mad2.model.LinhKien;
import com.example.mad2.model.LoaiLinhKien;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        //name - version
        super(context, "linhkien.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createLoaiTable = "CREATE TABLE loai_linh_kien ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "ten_loai TEXT NOT NULL,"
                + "mo_ta TEXT"
                + ")";
        db.execSQL(createLoaiTable);

        // Tạo bảng linh kiện
        String createLinhKienTable = "CREATE TABLE linh_kien ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "ma_linh_kien TEXT UNIQUE,"
                + "ten_linh_kien TEXT NOT NULL,"
                + "loai_id INTEGER,"
                + "so_luong_ton INTEGER DEFAULT 0,"
                + "gia_nhap REAL DEFAULT 0,"
                + "ngay_tao TEXT,"
                + "ghi_chu TEXT,"
                + "FOREIGN KEY(loai_id) REFERENCES loai_linh_kien(id)"
                + ")";
        db.execSQL(createLinhKienTable);

        // Thêm dữ liệu mẫu
        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS linh_kien");
        db.execSQL("DROP TABLE IF EXISTS loai_linh_kien");
        onCreate(db);
    }

    private void insertSampleData(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        // Thêm loại linh kiện mẫu
        String[] loaiNames = {"Điện trở", "Tụ điện", "IC", "Cảm biến", "LED"};
        for (String tenLoai : loaiNames) {
            cv.put("ten_loai", tenLoai);
            cv.put("mo_ta", "Các loại " + tenLoai.toLowerCase());
            db.insert("loai_linh_kien", null, cv);
            cv.clear();  // clear sau mỗi insert (gốc mày clear sau vòng lặp, nhưng để an toàn thì clear mỗi lần)
        }

        // Thêm linh kiện mẫu
        cv.clear();

        // Điện trở (loai_id = 1)
        cv.put("ma_linh_kien", "R001");
        cv.put("ten_linh_kien", "Điện trở 100Ω");
        cv.put("loai_id", 1);
        cv.put("so_luong_ton", 50);
        cv.put("gia_nhap", 500);
        cv.put("ghi_chu", "1/4W, 5%");
        db.insert("linh_kien", null, cv);

        cv.put("ma_linh_kien", "R002");
        cv.put("ten_linh_kien", "Điện trở 220Ω");
        cv.put("so_luong_ton", 30);
        db.insert("linh_kien", null, cv);

        cv.put("ma_linh_kien", "R003");
        cv.put("ten_linh_kien", "Điện trở 1kΩ");
        cv.put("so_luong_ton", 5); // Tồn thấp
        db.insert("linh_kien", null, cv);

        cv.put("ma_linh_kien", "R004");
        cv.put("ten_linh_kien", "Điện trở 10kΩ");
        cv.put("so_luong_ton", 25);
        db.insert("linh_kien", null, cv);

        // Tụ điện (loai_id = 2)
        cv.put("ma_linh_kien", "C001");
        cv.put("ten_linh_kien", "Tụ điện 100µF");
        cv.put("loai_id", 2);
        cv.put("so_luong_ton", 20);
        cv.put("gia_nhap", 2000);
        cv.put("ghi_chu", "16V");
        db.insert("linh_kien", null, cv);

        cv.put("ma_linh_kien", "C002");
        cv.put("ten_linh_kien", "Tụ hóa 1000µF");
        cv.put("so_luong_ton", 7); // Tồn thấp
        cv.put("gia_nhap", 5000);
        cv.put("ghi_chu", "25V");
        db.insert("linh_kien", null, cv);

        // IC (loai_id = 3)
        cv.put("ma_linh_kien", "I001");
        cv.put("ten_linh_kien", "IC LM358");
        cv.put("loai_id", 3);
        cv.put("so_luong_ton", 8); // Tồn thấp
        cv.put("gia_nhap", 15000);
        cv.put("ghi_chu", "");
        db.insert("linh_kien", null, cv);

        cv.put("ma_linh_kien", "I002");
        cv.put("ten_linh_kien", "IC 555");
        cv.put("so_luong_ton", 15);
        cv.put("gia_nhap", 8000);
        db.insert("linh_kien", null, cv);

        // Cảm biến (loai_id = 4)
        cv.put("ma_linh_kien", "S001");
        cv.put("ten_linh_kien", "Cảm biến DHT11");
        cv.put("loai_id", 4);
        cv.put("so_luong_ton", 2); // Tồn thấp
        cv.put("gia_nhap", 50000);
        db.insert("linh_kien", null, cv);

        cv.put("ma_linh_kien", "S002");
        cv.put("ten_linh_kien", "Cảm biến PIR");
        cv.put("so_luong_ton", 5); // Tồn thấp
        cv.put("gia_nhap", 45000);
        db.insert("linh_kien", null, cv);

        // LED (loai_id = 5)
        cv.put("ma_linh_kien", "L001");
        cv.put("ten_linh_kien", "LED đỏ");
        cv.put("loai_id", 5);
        cv.put("so_luong_ton", 100);
        cv.put("gia_nhap", 1000);
        db.insert("linh_kien", null, cv);

        cv.put("ma_linh_kien", "L002");
        cv.put("ten_linh_kien", "LED xanh");
        cv.put("so_luong_ton", 80);
        db.insert("linh_kien", null, cv);
    }

    public List<LinhKien> getAllLinhKien() {
        List<LinhKien> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT lk.*, l.ten_loai FROM linh_kien lk "
                + "LEFT JOIN loai_linh_kien l ON lk.loai_id = l.id "
                + " ORDER BY lk.id DESC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                LinhKien lk = new LinhKien();
                lk.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                lk.setMaLinhKien(cursor.getString(cursor.getColumnIndexOrThrow("ma_linh_kien")));
                lk.setTenLinhKien(cursor.getString(cursor.getColumnIndexOrThrow("ten_linh_kien")));
                lk.setSoLuongTon(cursor.getInt(cursor.getColumnIndexOrThrow("so_luong_ton")));
                lk.setGiaNhap(cursor.getDouble(cursor.getColumnIndexOrThrow("gia_nhap")));
                lk.setNgayTao(cursor.getString(cursor.getColumnIndexOrThrow("ngay_tao")));
                lk.setGhiChu(cursor.getString(cursor.getColumnIndexOrThrow("ghi_chu")));

                // Lấy thông tin loại
                LoaiLinhKien loai = new LoaiLinhKien();
                loai.setId(cursor.getInt(cursor.getColumnIndexOrThrow("loai_id")));
                loai.setTenLoai(cursor.getString(cursor.getColumnIndexOrThrow("ten_loai")));
                lk.setLoai(loai);

                list.add(lk);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public List<LinhKien> getLinhKienTonKhoThap(int threshold) {
        List<LinhKien> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT lk.*, l.ten_loai FROM linh_kien lk "
                + "LEFT JOIN loai_linh_kien l ON lk.loai_id = l.id "
                + " WHERE lk.so_luong_ton < " + threshold;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                LinhKien lk = new LinhKien();
                lk.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                lk.setMaLinhKien(cursor.getString(cursor.getColumnIndexOrThrow("ma_linh_kien")));
                lk.setTenLinhKien(cursor.getString(cursor.getColumnIndexOrThrow("ten_linh_kien")));
                lk.setSoLuongTon(cursor.getInt(cursor.getColumnIndexOrThrow("so_luong_ton")));
                lk.setGiaNhap(cursor.getDouble(cursor.getColumnIndexOrThrow("gia_nhap")));

                LoaiLinhKien loai = new LoaiLinhKien();
                loai.setId(cursor.getInt(cursor.getColumnIndexOrThrow("loai_id")));
                loai.setTenLoai(cursor.getString(cursor.getColumnIndexOrThrow("ten_loai")));
                lk.setLoai(loai);

                list.add(lk);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public int countLowStock() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM linh_kien WHERE so_luong_ton < 10";
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }

    public List<LoaiLinhKien> getAllLoai() {
        List<LoaiLinhKien> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM loai_linh_kien ORDER BY ten_loai";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                LoaiLinhKien loai = new LoaiLinhKien();
                loai.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                loai.setTenLoai(cursor.getString(cursor.getColumnIndexOrThrow("ten_loai")));
                loai.setMoTa(cursor.getString(cursor.getColumnIndexOrThrow("mo_ta")));

                // Đếm số linh kiện trong loại này
                String countQuery = "SELECT COUNT(*) FROM linh_kien WHERE loai_id = " + loai.getId();
                Cursor countCursor = db.rawQuery(countQuery, null);
                if (countCursor.moveToFirst()) {
                    loai.setSoLuong(countCursor.getInt(0));
                }
                countCursor.close();

                list.add(loai);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    //add new lk
    public void insertLinhKien(LinhKien lk) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("ma_linh_kien", lk.getMaLinhKien());
        cv.put("ten_linh_kien", lk.getTenLinhKien());
        cv.put("loai_id", lk.getLoai().getId());
        cv.put("so_luong_ton", lk.getSoLuongTon());
        cv.put("gia_nhap", lk.getGiaNhap());
        cv.put("ngay_tao", lk.getNgayTao());
        cv.put("ghi_chu", lk.getGhiChu());

        db.insert("linh_kien", null, cv);
        db.close();
    }

    public void updateLinhKien(LinhKien lk) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("ma_linh_kien", lk.getMaLinhKien());
        cv.put("ten_linh_kien", lk.getTenLinhKien());
        cv.put("loai_id", lk.getLoai().getId());
        cv.put("so_luong_ton", lk.getSoLuongTon());
        cv.put("gia_nhap", lk.getGiaNhap());
        cv.put("ghi_chu", lk.getGhiChu());

        db.update("linh_kien", cv, "id = ?", new String[]{String.valueOf(lk.getId())});
        db.close();
    }

    public void deleteLinhKien(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("linh_kien", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}