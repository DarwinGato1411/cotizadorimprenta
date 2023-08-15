/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package   imp.servicios;

import imp.entidades.ManoDeObra;
import imp.entidades.ManoDeObra;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioManoObra {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(ManoDeObra manoDeObra) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(manoDeObra);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar mano obra");
        } finally {
            em.close();
        }

    }

    public void eliminar(ManoDeObra manoDeObra) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(manoDeObra));
            em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  mano obra" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(ManoDeObra manoDeObra) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(manoDeObra);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar mano obra");
        } finally {
            em.close();
        }

    }

    public List<ManoDeObra> FindALlManoDeObraLikeNombre(String nombreMano) {

        List<ManoDeObra> listaManoDeObra = new ArrayList<ManoDeObra>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("ManoDeObra.findAllNombreLike", ManoDeObra.class);
            query.setParameter("nombreManoObra", "%" + nombreMano + "%");
            listaManoDeObra = (List<ManoDeObra>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra");
        } finally {
            em.close();
        }

        return listaManoDeObra;
    }

    
    
    public List<ManoDeObra> FindALlManoDeObraLikeNombreGiganto(String nombreMano) {

        List<ManoDeObra> listaManoDeObra = new ArrayList<ManoDeObra>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("ManoDeObra.findAllNombreLikeGiganto", ManoDeObra.class);
            query.setParameter("nombreManoObra", "%" + nombreMano + "%");
            listaManoDeObra = (List<ManoDeObra>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra");
        } finally {
            em.close();
        }

        return listaManoDeObra;
    }

    public List<ManoDeObra> FindALlManoDeObraLikeNombrePrePost(String nombreMano) {

        List<ManoDeObra> listaManoDeObra = new ArrayList<ManoDeObra>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("ManoDeObra.findAllNombrePrePost", ManoDeObra.class);
            query.setParameter("nombreManoObra", "%" + nombreMano + "%");
            listaManoDeObra = (List<ManoDeObra>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra");
        } finally {
            em.close();
        }

        return listaManoDeObra;
    }
    
    
    
    public List<ManoDeObra> FindALlManoDeObraImpreso(String nombreMano) {

        List<ManoDeObra> listaManoDeObra = new ArrayList<ManoDeObra>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("ManoDeObra.findAllImpresos", ManoDeObra.class);
            query.setParameter("nombreManoObra", "%" + nombreMano + "%");
            listaManoDeObra = (List<ManoDeObra>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra impreso");
        } finally {
            em.close();
        }

        return listaManoDeObra;
    }
        public List<ManoDeObra> FindALlManoDeObraGiganto(String nombreMano) {

        List<ManoDeObra> listaManoDeObra = new ArrayList<ManoDeObra>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("ManoDeObra.findAllGiganto", ManoDeObra.class);
            query.setParameter("nombreManoObra", "%" + nombreMano + "%");
            listaManoDeObra = (List<ManoDeObra>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra giganto");
        } finally {
            em.close();
        }

        return listaManoDeObra;
    }

        
         public List<ManoDeObra> FindALlManoDeObraLikeNombrePrePostGiganto(String nombreMano) {

        List<ManoDeObra> listaManoDeObra = new ArrayList<ManoDeObra>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("ManoDeObra.findAllNombrePrePostGiganto", ManoDeObra.class);
            query.setParameter("nombreManoObra", "%" + nombreMano + "%");
            listaManoDeObra = (List<ManoDeObra>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra");
        } finally {
            em.close();
        }

        return listaManoDeObra;
    }
}
