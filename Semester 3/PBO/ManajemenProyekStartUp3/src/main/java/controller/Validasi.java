/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * 
 * @author WINDOWS 11 PRO
 */
public final class Validasi {
 
    private Validasi() {
    }
 
    public static boolean isTeksValid(String teks) {
        return teks != null && !teks.trim().isEmpty();
    }
 
    public static boolean isAngka(String teks) {
        if (!isTeksValid(teks)) {
            return false;
        }
        final String bersih = teks.trim();
        for (int i = 0; i < bersih.length(); i++) {
            char karakter = bersih.charAt(i);
            if (karakter < '0' || karakter > '9') {
                return false;
            }
        }
        return true;
    }
 
    public static boolean isPilihanValid(String teks) {
        return isAngka(teks) && teks.trim().length() <= 9;
    }
 
    public static boolean isDeadlineValid(String teks) {
        return parseTanggal(teks) != null;
    }
 
    public static LocalDate parseTanggal(String teks) {
        if (!isTeksValid(teks)) {
            return null;
        }
 
        final String[] bagian = teks.trim().split("-");
        if (bagian.length != 3) {
            return null;
        }
 
        final String tanggal = bagian[0];
        final String bulan = bagian[1];
        final String tahun = bagian[2];
 
        if (!isAngka(tanggal) || !isAngka(bulan) || !isAngka(tahun)) {
            return null;
        }
 
        if (tanggal.length() != 2 || bulan.length() != 2 || tahun.length() != 4) {
            return null;
        }
 
        try {
            return LocalDate.of(Integer.parseInt(tahun), Integer.parseInt(bulan), Integer.parseInt(tanggal));
        } catch (DateTimeException e) {
            return null;
        }
    }
 
    public static String formatTanggal(LocalDate tanggal) {
        if (tanggal == null) {
            return null;
        }
        return String.format("%02d-%02d-%04d", tanggal.getDayOfMonth(), tanggal.getMonthValue(), tanggal.getYear());
    }
}   
