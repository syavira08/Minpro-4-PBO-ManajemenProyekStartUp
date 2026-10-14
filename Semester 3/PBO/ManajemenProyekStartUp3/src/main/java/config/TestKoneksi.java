/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author WINDOWS 11 PRO
 */
public class TestKoneksi {

    public static void main(String[] args) {
        System.out.println("Mengecek koneksi ke database...");

        Connection conn = Koneksi.getConnection();

        if (conn == null) {
            System.out.println("STATUS: GAGAL! Periksa kembali URL, USER, PASS, dan pastikan service MySQL sudah menyala.");
            return;
        }

        System.out.println("STATUS: BERHASIL! Database siap digunakan.");

        try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM proyek");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                System.out.println("Jumlah data proyek di database: " + rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Gagal membaca tabel proyek - " + e.getMessage());
        }
        Koneksi.tutup();
    }
}
