/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package   imp.servicios;

import imp.entidades.SecSinCotizar;
import imp.entidades.SeqFac;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioSecuencialSinCotizar {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public SecSinCotizar nuevaSecuenciaSinCotizar() {
        SecSinCotizar nuevaSec = new SecSinCotizar();
        try {

            SeqFac secuencia = new SeqFac();
            secuencia.setSeqVal(1);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(secuencia);
            em.getTransaction().commit();
            Query query = em.createNamedQuery("SecSinCotizar.findAll", SecSinCotizar.class);
            nuevaSec = (SecSinCotizar) query.getResultList().get(0);
            System.out.println("secuencial "+nuevaSec.getCodSinContizar());
            em.flush();
        } catch (Exception e) {
            System.out.println("Error en insertar secuencial sin cotizart");
        } finally {
            em.close();
        }
        return nuevaSec;

    }
}
