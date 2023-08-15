/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package   imp.servicios;

import imp.entidades.SeqFac;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioSecuencial {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public SeqFac nuevaSecuencia() {
        SeqFac nuevaSec = new SeqFac();
        try {

            SeqFac secuencia = new SeqFac();
            secuencia.setSeqVal(1);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(secuencia);
            em.getTransaction().commit();
            Query query = em.createNamedQuery("SeqFac.findAll", SeqFac.class);
            nuevaSec = (SeqFac) query.getResultList().get(0);
            System.out.println("secuencial "+nuevaSec.getIdSec());
            em.flush();
        } catch (Exception e) {
            System.out.println("Error en insertar mano obra");
        } finally {
            em.close();
        }
        return nuevaSec;

    }
}
