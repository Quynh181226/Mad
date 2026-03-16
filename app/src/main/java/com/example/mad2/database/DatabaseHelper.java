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

    // Database Info
    private static final String DATABASE_NAME = "linhkien.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_LOAI = "loai_linh_kien";
    private static final String TABLE_LINH_KIEN = "linh_kien";

    // LoaiLinhKien Table Columns
    private static final String COLUMN_LOAI_ID = "id";
    private static final String COLUMN_LOAI_TEN = "ten_loai";
    private static final String COLUMN_LOAI_MOTA = "mo_ta";

    // LinhKien Table Columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_MA = "ma_linh_kien";
    private static final String COLUMN_TEN = "ten_linh_kien";
    private static final String COLUMN_LOAI_ID_FK = "loai_id";
    private static final String COLUMN_SO_LUONG = "so_luong_ton";
    private static final String COLUMN_GIA = "gia_nhap";
    private static final String COLUMN_NGAY_TAO = "ngay_tao";
    private static final String COLUMN_GHI_CHU = "ghi_chu";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng loại linh kiện
        String createLoaiTable = "CREATE TABLE " + TABLE_LOAI + "("
                + COLUMN_LOAI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_LOAI_TEN + " TEXT NOT NULL,"
                + COLUMN_LOAI_MOTA + " TEXT"
                + ")";
        db.execSQL(createLoaiTable);

        // Tạo bảng linh kiện
        String createLinhKienTable = "CREATE TABLE " + TABLE_LINH_KIEN + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_MA + " TEXT UNIQUE,"
                + COLUMN_TEN + " TEXT NOT NULL,"
                + COLUMN_LOAI_ID_FK + " INTEGER,"
                + COLUMN_SO_LUONG + " INTEGER DEFAULT 0,"
                + COLUMN_GIA + " REAL DEFAULT 0,"
                + COLUMN_NGAY_TAO + " TEXT,"
                + COLUMN_GHI_CHU + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_LOAI_ID_FK + ") REFERENCES " + TABLE_LOAI + "(" + COLUMN_LOAI_ID + ")"
                + ")";
        db.execSQL(createLinhKienTable);

        // Thêm dữ liệu mẫu
        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LINH_KIEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOAI);
        onCreate(db);
    }

    private void insertSampleData(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        // Thêm loại linh kiện mẫu
        String[] loaiNames = {"Điện trở", "Tụ điện", "IC", "Cảm biến", "LED"};
        for (String tenLoai : loaiNames) {
            cv.put(COLUMN_LOAI_TEN, tenLoai);
            cv.put(COLUMN_LOAI_MOTA, "Các loại " + tenLoai.toLowerCase());
            db.insert(TABLE_LOAI, null, cv);
        }

        // Thêm linh kiện mẫu
        cv.clear();

        // Điện trở (loai_id = 1)
        cv.put(COLUMN_MA, "R001");
        cv.put(COLUMN_TEN, "Điện trở 100Ω");
        cv.put(COLUMN_LOAI_ID_FK, 1);
        cv.put(COLUMN_SO_LUONG, 50);
        cv.put(COLUMN_GIA, 500);
        cv.put(COLUMN_GHI_CHU, "1/4W, 5%");
        db.insert(TABLE_LINH_KIEN, null, cv);

        cv.put(COLUMN_MA, "R002");
        cv.put(COLUMN_TEN, "Điện trở 220Ω");
        cv.put(COLUMN_SO_LUONG, 30);
        db.insert(TABLE_LINH_KIEN, null, cv);

        cv.put(COLUMN_MA, "R003");
        cv.put(COLUMN_TEN, "Điện trở 1kΩ");
        cv.put(COLUMN_SO_LUONG, 5); // Tồn thấp
        db.insert(TABLE_LINH_KIEN, null, cv);

        cv.put(COLUMN_MA, "R004");
        cv.put(COLUMN_TEN, "Điện trở 10kΩ");
        cv.put(COLUMN_SO_LUONG, 25);
        db.insert(TABLE_LINH_KIEN, null, cv);

        // Tụ điện (loai_id = 2)
        cv.put(COLUMN_MA, "C001");
        cv.put(COLUMN_TEN, "Tụ điện 100µF");
        cv.put(COLUMN_LOAI_ID_FK, 2);
        cv.put(COLUMN_SO_LUONG, 20);
        cv.put(COLUMN_GIA, 2000);
        cv.put(COLUMN_GHI_CHU, "16V");
        db.insert(TABLE_LINH_KIEN, null, cv);

        cv.put(COLUMN_MA, "C002");
        cv.put(COLUMN_TEN, "Tụ hóa 1000µF");
        cv.put(COLUMN_SO_LUONG, 7); // Tồn thấp
        cv.put(COLUMN_GIA, 5000);
        cv.put(COLUMN_GHI_CHU, "25V");
        db.insert(TABLE_LINH_KIEN, null, cv);

        // IC (loai_id = 3)
        cv.put(COLUMN_MA, "I001");
        cv.put(COLUMN_TEN, "IC LM358");
        cv.put(COLUMN_LOAI_ID_FK, 3);
        cv.put(COLUMN_SO_LUONG, 8); // Tồn thấp
        cv.put(COLUMN_GIA, 15000);
        cv.put(COLUMN_GHI_CHU, "");
        db.insert(TABLE_LINH_KIEN, null, cv);

        cv.put(COLUMN_MA, "I002");
        cv.put(COLUMN_TEN, "IC 555");
        cv.put(COLUMN_SO_LUONG, 15);
        cv.put(COLUMN_GIA, 8000);
        db.insert(TABLE_LINH_KIEN, null, cv);

        // Cảm biến (loai_id = 4)
        cv.put(COLUMN_MA, "S001");
        cv.put(COLUMN_TEN, "Cảm biến DHT11");
        cv.put(COLUMN_LOAI_ID_FK, 4);
        cv.put(COLUMN_SO_LUONG, 2); // Tồn thấp
        cv.put(COLUMN_GIA, 50000);
        db.insert(TABLE_LINH_KIEN, null, cv);

        cv.put(COLUMN_MA, "S002");
        cv.put(COLUMN_TEN, "Cảm biến PIR");
        cv.put(COLUMN_SO_LUONG, 5); // Tồn thấp
        cv.put(COLUMN_GIA, 45000);
        db.insert(TABLE_LINH_KIEN, null, cv);

        // LED (loai_id = 5)
        cv.put(COLUMN_MA, "L001");
        cv.put(COLUMN_TEN, "LED đỏ");
        cv.put(COLUMN_LOAI_ID_FK, 5);
        cv.put(COLUMN_SO_LUONG, 100);
        cv.put(COLUMN_GIA, 1000);
        db.insert(TABLE_LINH_KIEN, null, cv);

        cv.put(COLUMN_MA, "L002");
        cv.put(COLUMN_TEN, "LED xanh");
        cv.put(COLUMN_SO_LUONG, 80);
        db.insert(TABLE_LINH_KIEN, null, cv);
    }

    // ==================== LẤY TẤT CẢ LINH KIỆN ====================
    public List<LinhKien> getAllLinhKien() {
        List<LinhKien> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT lk.*, l.ten_loai FROM " + TABLE_LINH_KIEN + " lk "
                + "LEFT JOIN " + TABLE_LOAI + " l ON lk." + COLUMN_LOAI_ID_FK + " = l." + COLUMN_LOAI_ID
                + " ORDER BY lk." + COLUMN_ID + " DESC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                LinhKien lk = new LinhKien();
                lk.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                lk.setMaLinhKien(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MA)));
                lk.setTenLinhKien(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN)));
                lk.setSoLuongTon(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SO_LUONG)));
                lk.setGiaNhap(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_GIA)));
                lk.setNgayTao(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NGAY_TAO)));
                lk.setGhiChu(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GHI_CHU)));

                // Lấy thông tin loại
                LoaiLinhKien loai = new LoaiLinhKien();
                loai.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LOAI_ID_FK)));
                loai.setTenLoai(cursor.getString(cursor.getColumnIndexOrThrow("ten_loai")));
                lk.setLoai(loai);

                list.add(lk);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    // ==================== LẤY LINH KIỆN TỒN KHO THẤP ====================
    public List<LinhKien> getLinhKienTonKhoThap(int threshold) {
        List<LinhKien> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT lk.*, l.ten_loai FROM " + TABLE_LINH_KIEN + " lk "
                + "LEFT JOIN " + TABLE_LOAI + " l ON lk." + COLUMN_LOAI_ID_FK + " = l." + COLUMN_LOAI_ID
                + " WHERE lk." + COLUMN_SO_LUONG + " < " + threshold;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                LinhKien lk = new LinhKien();
                lk.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                lk.setMaLinhKien(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MA)));
                lk.setTenLinhKien(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN)));
                lk.setSoLuongTon(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SO_LUONG)));
                lk.setGiaNhap(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_GIA)));

                LoaiLinhKien loai = new LoaiLinhKien();
                loai.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LOAI_ID_FK)));
                loai.setTenLoai(cursor.getString(cursor.getColumnIndexOrThrow("ten_loai")));
                lk.setLoai(loai);

                list.add(lk);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    // ==================== ĐẾM SỐ LINH KIỆN TỒN THẤP ====================
    public int countLowStock() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_LINH_KIEN + " WHERE " + COLUMN_SO_LUONG + " < 10";
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }

    // ==================== LẤY TẤT CẢ LOẠI LINH KIỆN ====================
    public List<LoaiLinhKien> getAllLoai() {
        List<LoaiLinhKien> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_LOAI + " ORDER BY " + COLUMN_LOAI_TEN;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                LoaiLinhKien loai = new LoaiLinhKien();
                loai.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LOAI_ID)));
                loai.setTenLoai(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOAI_TEN)));
                loai.setMoTa(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOAI_MOTA)));

                // Đếm số linh kiện trong loại này
                String countQuery = "SELECT COUNT(*) FROM " + TABLE_LINH_KIEN + " WHERE " + COLUMN_LOAI_ID_FK + " = " + loai.getId();
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

    // ==================== THÊM LINH KIỆN ====================
    public long insertLinhKien(LinhKien lk) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MA, lk.getMaLinhKien());
        cv.put(COLUMN_TEN, lk.getTenLinhKien());
        cv.put(COLUMN_LOAI_ID_FK, lk.getLoai().getId());
        cv.put(COLUMN_SO_LUONG, lk.getSoLuongTon());
        cv.put(COLUMN_GIA, lk.getGiaNhap());
        cv.put(COLUMN_NGAY_TAO, lk.getNgayTao());
        cv.put(COLUMN_GHI_CHU, lk.getGhiChu());

        long id = db.insert(TABLE_LINH_KIEN, null, cv);
        db.close();
        return id;
    }

    // ==================== CẬP NHẬT LINH KIỆN ====================
    public int updateLinhKien(LinhKien lk) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MA, lk.getMaLinhKien());
        cv.put(COLUMN_TEN, lk.getTenLinhKien());
        cv.put(COLUMN_LOAI_ID_FK, lk.getLoai().getId());
        cv.put(COLUMN_SO_LUONG, lk.getSoLuongTon());
        cv.put(COLUMN_GIA, lk.getGiaNhap());
        cv.put(COLUMN_GHI_CHU, lk.getGhiChu());

        return db.update(TABLE_LINH_KIEN, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(lk.getId())});
    }

    // ==================== XÓA LINH KIỆN ====================
    public void deleteLinhKien(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LINH_KIEN, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // ==================== LẤY LINH KIỆN THEO ID ====================
    public LinhKien getLinhKienById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT lk.*, l.ten_loai FROM " + TABLE_LINH_KIEN + " lk "
                + "LEFT JOIN " + TABLE_LOAI + " l ON lk." + COLUMN_LOAI_ID_FK + " = l." + COLUMN_LOAI_ID
                + " WHERE lk." + COLUMN_ID + " = " + id;

        Cursor cursor = db.rawQuery(query, null);
        LinhKien lk = null;

        if (cursor.moveToFirst()) {
            lk = new LinhKien();
            lk.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            lk.setMaLinhKien(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MA)));
            lk.setTenLinhKien(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN)));
            lk.setSoLuongTon(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SO_LUONG)));
            lk.setGiaNhap(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_GIA)));
            lk.setNgayTao(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NGAY_TAO)));
            lk.setGhiChu(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GHI_CHU)));

            LoaiLinhKien loai = new LoaiLinhKien();
            loai.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LOAI_ID_FK)));
            loai.setTenLoai(cursor.getString(cursor.getColumnIndexOrThrow("ten_loai")));
            lk.setLoai(loai);
        }

        cursor.close();
        db.close();
        return lk;
    }
}