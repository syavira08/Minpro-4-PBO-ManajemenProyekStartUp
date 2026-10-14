/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import config.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WINDOWS 11 PRO
 */
public class ProyekDAOJpa implements ProyekDAO {

    private EntityManager buatEntityManager() {
        return JpaUtil.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public boolean tambahProyek(Proyek proyek) {
        try (EntityManager em = buatEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(proyek);
                tx.commit();
                return true;
            } catch (RuntimeException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                System.out.println("Gagal menambah proyek - " + e.getMessage());
                return false;
            }
        }
    }

    @Override
    public List<Proyek> getAllProyek() {
        try (EntityManager em = buatEntityManager()) {
            return em.createQuery("SELECT p FROM Proyek p ORDER BY p.idProjek", Proyek.class)
                    .getResultList();
        } catch (RuntimeException e) {
            System.out.println("Gagal mengambil data proyek - " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Proyek cariProyekById(int id) {
        try (EntityManager em = buatEntityManager()) {
            return em.find(Proyek.class, id); 
        } catch (RuntimeException e) {
            System.out.println("Gagal mencari proyek - " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updateProyek(Proyek proyek) {
        try (EntityManager em = buatEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                if (em.find(Proyek.class, proyek.getIdAngka()) == null) {
                    tx.rollback();
                    return false;
                }
                em.merge(proyek);
                tx.commit();
                return true;
            } catch (RuntimeException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                System.out.println("Gagal mengubah data proyek - " + e.getMessage());
                return false;
            }
        }
    }

    @Override
    public boolean hapusProyek(int id) {
        try (EntityManager em = buatEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                Proyek proyek = em.find(Proyek.class, id);
                if (proyek == null) {
                    tx.rollback();
                    return false;
                }
                em.remove(proyek);
                tx.commit();
                return true;
            } catch (RuntimeException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                System.out.println("Gagal menghapus proyek - " + e.getMessage());
                return false;
            }
        }
    }
}
