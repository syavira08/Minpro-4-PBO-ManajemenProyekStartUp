/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
 
import controller.Validasi;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
 
/**
 * 
 * @author WINDOWS 11 PRO
 */
@Entity
@Table(name = "proyekinternal")
@DiscriminatorValue("Internal")
@PrimaryKeyJoinColumn(name = "idProjek")

public class ProyekInternal extends Proyek {
    @Column(name = "divisiPeminta", nullable = false, length = 100)
    private String divisiPeminta;
    @Column(name = "tujuanProjek", nullable = false)
    private String tujuanProjek;
 
    protected ProyekInternal() {
    }
 
    public ProyekInternal(String idProjek, String namaProjek, String deadline, String divisiPeminta, String tujuanProjek) {
        super(idProjek, namaProjek, deadline);
        setDivisiPeminta(divisiPeminta);
        setTujuanProjek(tujuanProjek);
    }
 
    public String getDivisiPeminta() {
        return divisiPeminta;
    }
 
    public void setDivisiPeminta(String divisiPeminta) {
        if (Validasi.isTeksValid(divisiPeminta)) {
            this.divisiPeminta = divisiPeminta;
        } else {
            System.out.println("Divisi peminta tidak valid!");
        }
    }
 
    public String getTujuanProjek() {
        return tujuanProjek;
    }
 
    public void setTujuanProjek(String tujuanProjek) {
        if (Validasi.isTeksValid(tujuanProjek)) {
            this.tujuanProjek = tujuanProjek;
        } else {
            System.out.println("Tujuan proyek tidak valid!");
        }
    }
    
    @Override
    public String getJenisProjek() {
        return "Internal";
    }
 
    @Override
    public void update(String namaBaru, String deadlineBaru, String divisiBaru, String tujuanBaru) {
        update(namaBaru, deadlineBaru);
        setDivisiPeminta(divisiBaru);
        setTujuanProjek(tujuanBaru);
    }
 
    @Override
    public void cetakData() {
        super.cetakData();
        System.out.println("Divisi Peminta : " + divisiPeminta);
        System.out.println("Tujuan Proyek  : " + tujuanProjek);
    }
}
