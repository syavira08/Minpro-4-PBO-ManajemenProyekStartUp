/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;
 
import controller.ProyekController;
import controller.ProyekCrud;
import controller.Validasi;
import java.util.ArrayList;
import java.util.Scanner;
import model.Proyek;
import model.ProyekInternal;
import model.ProyekKlien;
 
/**
 * 
 * @author WINDOWS 11 PRO
 */
public class ProyekView {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ProyekCrud proyekCrud = new ProyekController();
 
    public static void mulai() {
        boolean ngulang = true;
 
        System.out.println("==================================================");
        System.out.println("         SISTEM MANAJEMEN PROYEK STARTUP");
        System.out.println("==================================================");
 
        while (ngulang) {
            tampilkanMenu();
            System.out.print("Pilih menu: ");
            String pilihan = scanner.nextLine();
 
            if (!Validasi.isPilihanValid(pilihan)) {
                System.out.println("Input harus berupa angka!");
                continue;
            }
 
            int menu = Integer.parseInt(pilihan.trim());
 
            if (menu == 1) {
                tambahProjek();
            } else if (menu == 2) {
                tampilkanSemuaProjek();
            } else if (menu == 3) {
                updateProjek();
            } else if (menu == 4) {
                hapusProjek();
            } else if (menu == 5) {
                ngulang = false;
                System.out.println();
                System.out.println("==================================================");
                System.out.println("     TERIMA KASIH TELAH MENGGUNAKAN SISTEM");
                System.out.println("           MANAJEMEN PROYEK STARTUP");
                System.out.println("==================================================");
            } else {
                System.out.println("Menu tidak tersedia, coba lagi.");
            }
        }
 
        scanner.close();
    }
 
    private static String inputTeks(String label) {
        String hasil = "";
        while (!Validasi.isTeksValid(hasil)) {
            System.out.print(label + ": ");
            hasil = scanner.nextLine();
            if (!Validasi.isTeksValid(hasil)) {
                System.out.println(label + " tidak boleh kosong!");
            }
        }
        return hasil;
    }
 
    private static String inputDeadline(String label) {
        String hasil = "";
        while (!Validasi.isDeadlineValid(hasil)) {
            System.out.print(label + " (format dd-mm-yyyy): ");
            hasil = scanner.nextLine();
            if (!Validasi.isDeadlineValid(hasil)) {
                System.out.println("Format deadline salah! Contoh: 21-02-2027");
            }
        }
        return hasil;
    }
 
    private static String inputTeksUpdate(String label, String dataLama) {
        System.out.print(label + ": ");
        String hasil = scanner.nextLine();
        if (!Validasi.isTeksValid(hasil)) {
            return dataLama;
        }
        return hasil;
    }
 
    private static String inputDeadlineUpdate(String dataLama) {
        while (true) {
            System.out.print("Deadline baru (format dd-mm-yyyy): ");
            String hasil = scanner.nextLine();
            if (!Validasi.isTeksValid(hasil)) {
                return dataLama;
            }
            if (Validasi.isDeadlineValid(hasil)) {
                return hasil;
            }
            System.out.println("Format deadline salah! Contoh: 21-02-2027");
        }
    }
 
    
    private static void tampilkanMenu() {
        System.out.println();
        System.out.println("=============== MENU UTAMA ===============");
        System.out.println("1. Tambah Proyek");
        System.out.println("2. Tampilkan Semua Proyek");
        System.out.println("3. Update Proyek");
        System.out.println("4. Hapus Proyek");
        System.out.println("5. Keluar");
        System.out.println("==========================================");
    }
 
    private static void tampilkanSemuaProjek() {
        ArrayList<Proyek> daftar = proyekCrud.getDaftarProjek();
 
        if (daftar.isEmpty()) {
            System.out.println("Belum ada data proyek!");
            return;
        }
 
        System.out.println();
        System.out.println("==================================================");
        System.out.println("           DAFTAR DATA PROYEK STARTUP");
        System.out.println("==================================================");
 
        for (int i = 0; i < daftar.size(); i++) {
            System.out.println();
            System.out.println("-------------------- PROYEK " + (i + 1) + " --------------------");
            daftar.get(i).cetakData();
            System.out.println("--------------------------------------------------");
        }
 
        System.out.println();
        System.out.println("Total Proyek : " + daftar.size());
        System.out.println("==================================================");
    }
 
 
    private static void tambahProjek() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("              TAMBAH PROYEK");
        System.out.println("==========================================");
 
        int jenis = 0;
        while (jenis != 1 && jenis != 2) {
            System.out.println();
            System.out.println("---------- PILIH JENIS PROYEK ----------");
            System.out.println("1. Proyek Internal");
            System.out.println("2. Proyek Klien");
            System.out.println("----------------------------------------");
            System.out.print("Pilih jenis proyek : ");
            String pilihanJenis = scanner.nextLine();
 
            if (!Validasi.isPilihanValid(pilihanJenis)) {
                System.out.println("Input harus berupa angka!");
                continue;
            }
 
            jenis = Integer.parseInt(pilihanJenis.trim());
            if (jenis != 1 && jenis != 2) {
                System.out.println("Jenis tidak tersedia, pilih 1 atau 2.");
            }
        }
 
        String id;
        while (true) {
            System.out.println();
            System.out.println("------------ INFORMASI PROYEK ------------");
            id = inputTeks("ID Proyek");
            if (!Validasi.isPilihanValid(id)) {
                System.out.println("ID Proyek harus berupa angka!");
                continue;
            }
            if (!proyekCrud.isIdSudahAda(id)) {
                break;
            }
            System.out.println("ID sudah digunakan, gunakan ID lain!");
        }
 
        String namaProjek = inputTeks("Nama Proyek");
        String deadline = inputDeadline("Deadline");
 
        Proyek proyekBaru;
        if (jenis == 1) {
            String divisiPeminta = inputTeks("Divisi Peminta (Engineering/Product/Marketing/Operations)");
            String tujuanProjek = inputTeks("Tujuan Proyek");
            proyekBaru = new ProyekInternal(id, namaProjek, deadline, divisiPeminta, tujuanProjek);
        } else {
            String namaKlien = inputTeks("Nama Klien");
            String jenisKebutuhan = inputTeks("Jenis Kebutuhan");
            proyekBaru = new ProyekKlien(id, namaProjek, deadline, namaKlien, jenisKebutuhan);
        }
 
        if (proyekCrud.tambahProjek(proyekBaru)) {
            System.out.println("Proyek berhasil ditambahkan!");
        } else {
            System.out.println("Proyek gagal ditambahkan.");
        }
    }
 
    private static void updateProjek() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("           UPDATE DATA PROYEK");
        System.out.println("==========================================");
        System.out.print("Masukkan ID Projek yang ingin diupdate: ");
        String id = scanner.nextLine();
 
        Proyek proyek = proyekCrud.cariProjekById(id);
        if (proyek == null) {
            System.out.println("Projek dengan ID tersebut tidak ditemukan.");
            return;
        }
 
        System.out.println();
        System.out.println("---------------- DATA LAMA ----------------");
        proyek.cetakData();
 
        System.out.println();
        System.out.println("-- Tekan Enter jika tidak ingin mengubah data --");
        System.out.println();
        System.out.println("---------- INFORMASI PROYEK BARU ----------");
        String namaProjek = inputTeksUpdate("Nama Proyek baru", proyek.getNamaProjek());
        String deadline = inputDeadlineUpdate(proyek.getDeadline());
 
        String data1;
        String data2;
        if (proyek instanceof ProyekInternal) {
            ProyekInternal internal = (ProyekInternal) proyek;
            data1 = inputTeksUpdate(
                    "Divisi Peminta baru (Engineering/Product/Marketing/Operations)",
                    internal.getDivisiPeminta());
            data2 = inputTeksUpdate("Tujuan Proyek baru", internal.getTujuanProjek());
        } else {
            ProyekKlien klien = (ProyekKlien) proyek;
            data1 = inputTeksUpdate("Nama Klien baru", klien.getNamaKlien());
            data2 = inputTeksUpdate("Jenis Kebutuhan baru", klien.getJenisKebutuhan());
        }
 
        if (proyekCrud.updateProjek(id, namaProjek, deadline, data1, data2)) {
            System.out.println("Proyek berhasil diupdate!");
        } else {
            System.out.println("Proyek gagal diupdate.");
        }
    }
 
    private static void hapusProjek() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("            HAPUS DATA PROYEK");
        System.out.println("==========================================");
        System.out.print("Masukkan ID Projek yang ingin dihapus: ");
        String id = scanner.nextLine();
 
        if (proyekCrud.hapusProjek(id)) {
            System.out.println("Proyek berhasil dihapus!");
        } else {
            System.out.println("Proyek dengan ID tersebut tidak ditemukan.");
        }
    }
}
