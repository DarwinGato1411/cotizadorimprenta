/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package  imp.servicios;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author PC
 */
public class HelperPersistencia {

    public static EntityManager getEMF() {
        EntityManager emf = (EntityManager) Persistence.createEntityManagerFactory("ProImprentaPU").createEntityManager();
        return emf;
    }
}
