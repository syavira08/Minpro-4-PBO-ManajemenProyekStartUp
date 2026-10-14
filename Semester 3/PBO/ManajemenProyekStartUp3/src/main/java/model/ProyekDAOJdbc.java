/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import config.Koneksi;
import controller.Validasi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WINDOWS 11 PRO
 */
public class ProyekDAOJdbc implements ProyekDAO {

    private static final String SELECT_GABUNGAN =
            "SELECT p.idProjek, p.namaProjek, p.deadline, p.jenisProjek, "
            + "i.divisiPeminta, i.tujuanProjek, k.namaKlien, k.jenisKebutuhan "
            + "FROM proyek p "
            + "LEFT JOIN proyekinternal i ON p.idProjek = i.idProjek "
            + "LEFT JOIN proyekklien k ON p.idProjek = k.idProjek ";

    @Override
    public boolean tambahProyek(Proyek proyek) {
        Connection conn = Koneksi.getConnection();
        if (conn == null) {
            return false;
        }

        String sql = "INSERT INTO proyek (idProjek, namaProjek, deadline, jenisProjek) VALUES (?, ?, ?, ?)";

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, proyek.getIdAngka());
                pstmt.setString(2, proyek.getNamaProjek());
                pstmt.setObject(3, proyek.getDeadlineTanggal());
                pstmt.setString(4, proyek.getJenisProjek());
                pstmt.executeUpdate();
            }

            insertDetail(conn, proyek);
            conn.commit();
            return true;
        } catch (SQLException e) {
            System.out.println("Gagal menambah proyek - " + e.getMessage());
            rollback(conn);
            return false;
        } finally {
            aktifkanAutoCommit(conn);
        }
    }

    @Override
    public List<Proyek> getAllProyek() {
        List<Proyek> daftar = new ArrayList<>();
        Connection conn = Koneksi.getConnection();
        if (conn == null) {
            return daftar;
        }

        String sql = SELECT_GABUNGAN + "ORDER BY p.idProjek";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                daftar.add(petakanBaris(rs));
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data proyek - " + e.getMessage());
        }
        return daftar;
    }

    @Override
    public Proyek cariProyekById(int id) {
        Proyek proyek = null;
        Connection conn = Koneksi.getConnection();
        if (conn == null) {
            return null;
        }

        String sql = SELECT_GABUNGAN + "WHERE p.idProjek = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    proyek = petakanBaris(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal mencari proyek - " + e.getMessage());
        }
        return proyek;
    }

    @Override
    public boolean updateProyek(Proyek proyek) {
        Connection conn = Koneksi.getConnection();
        if (conn == null) {
            return false;
        }

        String sql = "UPDATE proyek SET namaProjek=?, deadline=? WHERE idProjek=?";

        try {
            conn.setAutoCommit(false);

            int barisBerubah;
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, proyek.getNamaProjek());
                pstmt.setObject(2, proyek.getDeadlineTanggal());
                pstmt.setInt(3, proyek.getIdAngka());
                barisBerubah = pstmt.executeUpdate();
            }

            if (barisBerubah == 0) {
                conn.rollback();
                return false;
            }

            updateDetail(conn, proyek);
            conn.commit();
            return true;
        } catch (SQLException e) {
            System.out.println("Gagal mengubah data proyek - " + e.getMessage());
            rollback(conn);
            return false;
        } finally {
            aktifkanAutoCommit(conn);
        }
    }

    @Override
    public boolean hapusProyek(int id) {
        Connection conn = Koneksi.getConnection();
        if (conn == null) {
            return false;
        }

        String sql = "DELETE FROM proyek WHERE idProjek=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal menghapus proyek - " + e.getMessage());
            return false;
        }
    }

    private Proyek petakanBaris(ResultSet rs) throws SQLException {
        Proyek proyek;

        if ("Internal".equalsIgnoreCase(rs.getString("jenisProjek"))) {
            ProyekInternal internal = new ProyekInternal();
            internal.setDivisiPeminta(rs.getString("divisiPeminta"));
            internal.setTujuanProjek(rs.getString("tujuanProjek"));
            proyek = internal;
        } else {
            ProyekKlien klien = new ProyekKlien();
            klien.setNamaKlien(rs.getString("namaKlien"));
            klien.setJenisKebutuhan(rs.getString("jenisKebutuhan"));
            proyek = klien;
        }

        proyek.setIdAngka(rs.getInt("idProjek"));
        proyek.setNamaProjek(rs.getString("namaProjek"));
        proyek.setDeadline(Validasi.formatTanggal(rs.getObject("deadline", LocalDate.class)));
        return proyek;
    }

    private void insertDetail(Connection conn, Proyek proyek) throws SQLException {
        if (proyek instanceof ProyekInternal internal) {
            String sql = "INSERT INTO proyekinternal (idProjek, divisiPeminta, tujuanProjek) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, internal.getIdAngka());
                pstmt.setString(2, internal.getDivisiPeminta());
                pstmt.setString(3, internal.getTujuanProjek());
                pstmt.executeUpdate();
            }
        } else if (proyek instanceof ProyekKlien klien) {
            String sql = "INSERT INTO proyekklien (idProjek, namaKlien, jenisKebutuhan) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, klien.getIdAngka());
                pstmt.setString(2, klien.getNamaKlien());
                pstmt.setString(3, klien.getJenisKebutuhan());
                pstmt.executeUpdate();
            }
        }
    }

    private void updateDetail(Connection conn, Proyek proyek) throws SQLException {
        if (proyek instanceof ProyekInternal internal) {
            String sql = "UPDATE proyekinternal SET divisiPeminta=?, tujuanProjek=? WHERE idProjek=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, internal.getDivisiPeminta());
                pstmt.setString(2, internal.getTujuanProjek());
                pstmt.setInt(3, internal.getIdAngka());
                pstmt.executeUpdate();
            }
        } else if (proyek instanceof ProyekKlien klien) {
            String sql = "UPDATE proyekklien SET namaKlien=?, jenisKebutuhan=? WHERE idProjek=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, klien.getNamaKlien());
                pstmt.setString(2, klien.getJenisKebutuhan());
                pstmt.setInt(3, klien.getIdAngka());
                pstmt.executeUpdate();
            }
        }
    }

    private void rollback(Connection conn) {
        try {
            conn.rollback();
        } catch (SQLException e) {
            System.out.println("Rollback gagal - " + e.getMessage());
        }
    }

    private void aktifkanAutoCommit(Connection conn) {
        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Gagal mengembalikan autoCommit - " + e.getMessage());
        }
    }
}
