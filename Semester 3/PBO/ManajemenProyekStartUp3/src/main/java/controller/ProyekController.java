/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
 
import config.Konfigurasi;
import java.util.ArrayList;
import model.Proyek;
import model.ProyekDAO;
import model.ProyekDAOJdbc;
import model.ProyekDAOJpa;
 
/**
 * 
 * @author WINDOWS 11 PRO
 */
public class ProyekController implements ProyekCrud {
    private final ProyekDAO dao = Konfigurasi.GUNAKAN_ORM ? new ProyekDAOJpa() : new ProyekDAOJdbc();
 
    @Override
    public boolean tambahProjek(Proyek proyek) {
        if (isIdSudahAda(proyek.getIdProjek())) {
            return false;
        }
        return dao.tambahProyek(proyek);
    }
 
    @Override
    public ArrayList<Proyek> getDaftarProjek() {
        return new ArrayList<>(dao.getAllProyek());
    }
 
    @Override
    public boolean isIdSudahAda(String id) {
        return cariProjekById(id) != null;
    }
 
    @Override
    public Proyek cariProjekById(String id) {
        if (!Validasi.isPilihanValid(id)) {
            return null;
        }
        return dao.cariProyekById(Integer.parseInt(id.trim()));
    }
 
    @Override
    public boolean updateProjek(String id, String namaBaru, String deadlineBaru, String data1, String data2) {
        Proyek p = cariProjekById(id);
        if (p == null) {
            return false;
        }
        
        p.update(namaBaru, deadlineBaru, data1, data2);
        return dao.updateProyek(p);
    }
 
    @Override
    public boolean hapusProjek(String id) {
        if (!Validasi.isPilihanValid(id)) {
            return false;
        }
        return dao.hapusProyek(Integer.parseInt(id.trim()));
    }
}
