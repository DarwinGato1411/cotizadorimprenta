/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package   imp.servicios;

import imp.entidades.Cotizacion;
import imp.entidades.DetalleCotizacion;
import imp.entidades.DetalleCotizacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioDetalleCotizacion {
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(DetalleCotizacion detalleCotizacion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(detalleCotizacion);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar mano obra");
        } finally {
            em.close();
        }

    }

    public void eliminar(DetalleCotizacion detalleCotizacion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(detalleCotizacion));
            em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  mano obra" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(DetalleCotizacion detalleCotizacion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(detalleCotizacion);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar mano obra");
        } finally {
            em.close();
        }

    }
     public List<DetalleCotizacion> FindALlDetalleCotizacionLikeNombre(String nombreMano) {

        List<DetalleCotizacion> listaDetalleCotizacion = new ArrayList<DetalleCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("DetalleCotizacion.findAllNombreLike", DetalleCotizacion.class);
           query.setParameter("nombreManoObra", "%"+nombreMano+"%");
            listaDetalleCotizacion = (List<DetalleCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra");
        } finally {
            em.close();
        }

        return listaDetalleCotizacion;
    }
      public List<DetalleCotizacion> FindDetalleCotizacionPorCotizacion(Cotizacion cotizacion) {

        List<DetalleCotizacion> listaDetalleCotizacion = new ArrayList<DetalleCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("DetalleCotizacion.findPorCotizacion", DetalleCotizacion.class);
           query.setParameter("codCotizacion",cotizacion);
            listaDetalleCotizacion = (List<DetalleCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra");
        } finally {
            em.close();
        }

        return listaDetalleCotizacion;
    }
      public List<DetalleCotizacion> FindDetalleCotizacionPorCotizacionXml(Cotizacion cotizacion) {

        List<DetalleCotizacion> listaDetalleCotizacion = new ArrayList<DetalleCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("DetalleCotizacion.findPorCotizacionXml", DetalleCotizacion.class);
           query.setParameter("codCotizacion",cotizacion);
            listaDetalleCotizacion = (List<DetalleCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra");
        } finally {
            em.close();
        }

        return listaDetalleCotizacion;
    }
}
