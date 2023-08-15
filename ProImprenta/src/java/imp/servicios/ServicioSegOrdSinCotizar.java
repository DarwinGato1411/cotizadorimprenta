/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.servicios;

import imp.entidades.Categoria;
import imp.entidades.OrdenSinCotizacion;
import imp.entidades.SegOrdSinCot;
import imp.entidades.SegOrdSinCot;
import imp.entidades.Ubicacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author gato
 */
public class ServicioSegOrdSinCotizar {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(SegOrdSinCot segOrdSinCot) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(segOrdSinCot);
            em.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            // Aqui tira los errores de constraint
            for (ConstraintViolation actual : e.getConstraintViolations()) {
                System.out.println(actual.toString());
            }
        } finally {
            em.close();
        }

    }

    public void eliminar(SegOrdSinCot segOrdSinCot) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(segOrdSinCot));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  segOrdSinCot " + e.toString());
        } finally {
            em.close();
        }

    }

    public void modificar(SegOrdSinCot segOrdSinCot) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(segOrdSinCot);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar segOrdSinCot " + e.toString());
        } finally {
            em.close();
        }

    }

    public List<SegOrdSinCot> FindAll() {

        List<SegOrdSinCot> listaPersonas = new ArrayList<SegOrdSinCot>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM SegOrdSinCot a ORDER BY a.segFechaModifica ASC ");
//            query.setParameter("perCedula", cedula);
            listaPersonas = (List<SegOrdSinCot>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta segOrdSinCot " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }

    public List<SegOrdSinCot> findByOrden(OrdenSinCotizacion orden) {

        List<SegOrdSinCot> listaPersonas = new ArrayList<SegOrdSinCot>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("Select a from SegOrdSinCot a where a.segSinNumero=:segSinNumero ORDER BY a.segFechaModifica ASC");
            query.setParameter("segSinNumero", orden.getSinNumero());
            listaPersonas = (List<SegOrdSinCot>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta segOrdSinCot " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }

}
