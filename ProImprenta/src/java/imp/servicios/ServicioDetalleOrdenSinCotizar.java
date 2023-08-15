/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package   imp.servicios;

import imp.entidades.Cotizacion;
import imp.entidades.DetalleOrdenSinCotizar;
import imp.entidades.DetalleOrdenSinCotizar;
import imp.entidades.OrdenSinCotizacion;
import imp.utilitario.DetalleOrdenSinCotizarArmar;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioDetalleOrdenSinCotizar {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(DetalleOrdenSinCotizar detalleOrdenSinCotizar) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(detalleOrdenSinCotizar);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar detalleOrdenSinCotizar obra");
        } finally {
            em.close();
        }

    }

    public void eliminar(DetalleOrdenSinCotizar detalleOrdenSinCotizar) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(detalleOrdenSinCotizar));
            em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  detalleOrdenSinCotizar obra" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(DetalleOrdenSinCotizar detalleOrdenSinCotizar) {

        try {
//            System.out.println("ORDEN> " + detalleOrdenSinCotizar.getOrdSecuencial() + " numero" + detalleOrdenSinCotizar.getOrdNumero());
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(detalleOrdenSinCotizar);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar detalleOrdenSinCotizar");
        } finally {
            em.close();
        }

    }

    //ordenes cerradas
    public List<DetalleOrdenSinCotizar> listaOrdenFindAll() {

        List<DetalleOrdenSinCotizar> listaOrden = new ArrayList<DetalleOrdenSinCotizar>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("DetalleOrdenSinCotizar.findAll", DetalleOrdenSinCotizar.class);
//            query.setParameter("numero", "%" + numOrden + "%");
            listaOrden = (List<DetalleOrdenSinCotizar>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta detalleOrdenSinCotizar obra 0");
        } finally {
            em.close();
        }

        return listaOrden;
    }

    //ordenes cerradas
    public List<DetalleOrdenSinCotizar> listaDetalleOrdenFindOrden(OrdenSinCotizacion ordenSinCotizacion) {

        List<DetalleOrdenSinCotizar> listaOrden = new ArrayList<DetalleOrdenSinCotizar>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("DetalleOrdenSinCotizar.findAllForOrden", DetalleOrdenSinCotizar.class);
            query.setParameter("codOrdenSinCotizacion", ordenSinCotizacion);
            listaOrden = (List<DetalleOrdenSinCotizar>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta detalleOrdenSinCotizar obra 0");
        } finally {
            em.close();
        }

        return listaOrden;
    }
}
