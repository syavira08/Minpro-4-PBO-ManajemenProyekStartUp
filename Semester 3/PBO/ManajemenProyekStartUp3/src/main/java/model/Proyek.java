/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
 
import controller.Validasi;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.time.LocalDate;
 
/**
 * 
 * @author WINDOWS 11 PRO
 */
@Entity
@Table(name = "proyek")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "jenisProjek", discriminatorType = DiscriminatorType.STRING, length = 20)

public abstract class Proyek {
   
    @Id
    @Column(name = "idProjek")
    private Integer idProjek;
 
    @Column(name = "namaProjek", nullable = false, length = 100)
    private String namaProjek;
 
    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;
 
    protected Proyek() {
    }
 
    public Proyek(String idProjek, String namaProjek, String deadline) {
        if (Validasi.isPilihanValid(idProjek)) {
            this.idProjek = Integer.valueOf(idProjek.trim());
        } else {
            System.out.println("ID proyek tidak valid!");
        }
        setNamaProjek(namaProjek);
        setDeadline(deadline);
    }
 
    public String getIdProjek() {
        return idProjek == null ? null : String.valueOf(idProjek);
    }
 
    public int getIdAngka() {
        return idProjek;
    }
 
    public void setIdAngka(int idProjek) {
        this.idProjek = idProjek;
    }
 
    public String getNamaProjek() {
        return namaProjek;
    }
 
    public void setNamaProjek(String namaProjek) {
        if (Validasi.isTeksValid(namaProjek)) {
            this.namaProjek = namaProjek;
        } else {
            System.out.println("Nama proyek tidak valid!");
        }
    }
 
    public String getDeadline() {
        return Validasi.formatTanggal(deadline);
    }
 
    public LocalDate getDeadlineTanggal() {
        return deadline;
    }
 
    public void setDeadline(String deadline) {
        LocalDate tanggal = Validasi.parseTanggal(deadline);
        if (tanggal != null) {
            this.deadline = tanggal;
        } else {
            System.out.println("Format deadline tidak valid!");
        }
    }
 
    public abstract String getJenisProjek();
 
    public abstract void update(String namaBaru, String deadlineBaru, String data1, String data2);
 
    public void update(String namaBaru, String deadlineBaru) {
        setNamaProjek(namaBaru);
        setDeadline(deadlineBaru);
    }
 
    public void cetakData() {
        System.out.println("Jenis Proyek : " + getJenisProjek());
        System.out.println("ID Proyek    : " + idProjek);
        System.out.println("Nama Proyek  : " + namaProjek);
        System.out.println("Deadline     : " + getDeadline());
    }
}

