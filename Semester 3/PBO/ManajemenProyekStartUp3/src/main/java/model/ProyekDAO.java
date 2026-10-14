/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;

/**
 *
 * @author WINDOWS 11 PRO
 */
public interface ProyekDAO {

    boolean tambahProyek(Proyek proyek);

    List<Proyek> getAllProyek();

    Proyek cariProyekById(int id);

    boolean updateProyek(Proyek proyek);

    boolean hapusProyek(int id);
}
