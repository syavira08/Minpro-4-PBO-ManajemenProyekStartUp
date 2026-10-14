/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WINDOWS 11 PRO
 */
public class JpaUtil {

    private static final String PERSISTENCE_UNIT = "manajemenProyekPU";
    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null || !emf.isOpen()) {
            Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        }
        return emf;
    }

    public static void tutup() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
