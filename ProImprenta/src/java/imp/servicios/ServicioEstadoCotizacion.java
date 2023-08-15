/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package   imp.servicios;

import imp.entidades.EstadoCotizacion;
import imp.entidades.EstadoCotizacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioEstadoCotizacion {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(EstadoCotizacion estado) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(estado);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar estado");
        } finally {
            em.close();
        }

    }

    public void eliminar(EstadoCotizacion estado) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(estado));
            em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  estado" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(EstadoCotizacion estado) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(estado);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar estado");
        } finally {
            em.close();
        }

    }

    public List<EstadoCotizacion> FindALlEstado() {

        List<EstadoCotizacion> listaClientes = new ArrayList<EstadoCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("EstadoCotizacion.findAll", EstadoCotizacion.class);
//           query.setParameter("codigoUsuario", estado);
            listaClientes = (List<EstadoCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta estado");
        } finally {
            em.close();
        }

        return listaClientes;
    }

    public List<EstadoCotizacion> FindALlEstadoLike(String buscar) {

        List<EstadoCotizacion> listaClientes = new ArrayList<EstadoCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("EstadoCotizacion.findAllLike", EstadoCotizacion.class);
            query.setParameter("sigla", "%" + buscar + "%");
            listaClientes = (List<EstadoCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta estado");
        } finally {
            em.close();
        }

        return listaClientes;
    }

    public EstadoCotizacion FindALlEstadoSigla(String buscar) {

        List<EstadoCotizacion> listaClientes = new ArrayList<EstadoCotizacion>();
        EstadoCotizacion estadoCotizacion = new EstadoCotizacion();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("EstadoCotizacion.findAllLike", EstadoCotizacion.class);
            query.setParameter("sigla", "%" + buscar + "%");
            listaClientes = (List<EstadoCotizacion>) query.getResultList();
            for (EstadoCotizacion item : listaClientes) {
                estadoCotizacion = item;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta estado");
        } finally {
            em.close();
        }

        return estadoCotizacion;
    }
}
