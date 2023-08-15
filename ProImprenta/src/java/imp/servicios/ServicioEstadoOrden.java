/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package   imp.servicios;

import imp.entidades.EstadoOrdenProduccion;
import imp.entidades.EstadoOrdenProduccion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioEstadoOrden {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(EstadoOrdenProduccion estado) {

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

    public void eliminar(EstadoOrdenProduccion estado) {

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

    public void modificar(EstadoOrdenProduccion estado) {

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

    public List<EstadoOrdenProduccion> FindALlEstado() {

        List<EstadoOrdenProduccion> listaClientes = new ArrayList<EstadoOrdenProduccion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("EstadoOrdenProduccion.findAll", EstadoOrdenProduccion.class);
//           query.setParameter("codigoUsuario", estado);
            listaClientes = (List<EstadoOrdenProduccion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta estado");
        } finally {
            em.close();
        }

        return listaClientes;
    }

   

    public List<EstadoOrdenProduccion> FindALlEstadoLike(String buscar) {

        List<EstadoOrdenProduccion> listaClientes = new ArrayList<EstadoOrdenProduccion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("EstadoOrdenProduccion.findAllLikeSigla", EstadoOrdenProduccion.class);
            query.setParameter("sigla", "%" + buscar + "%");
            listaClientes = (List<EstadoOrdenProduccion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta estado");
        } finally {
            em.close();
        }

        return listaClientes;
    }
    
       public EstadoOrdenProduccion FindALlEstadoLikeSigla(String buscar) {

        List<EstadoOrdenProduccion> listaClientes = new ArrayList<EstadoOrdenProduccion>();
         EstadoOrdenProduccion estado = new EstadoOrdenProduccion();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("EstadoOrdenProduccion.findAllLikeSigla", EstadoOrdenProduccion.class);
            query.setParameter("sigla", "%" + buscar + "%");
            listaClientes = (List<EstadoOrdenProduccion>) query.getResultList();
            for (EstadoOrdenProduccion item : listaClientes) {
                estado=item;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta estado");
        } finally {
            em.close();
        }

        return estado;
    }
       
//       findAllLikeCliente
         public EstadoOrdenProduccion findAllLikeCliente(String buscar) {

        List<EstadoOrdenProduccion> listaClientes = new ArrayList<EstadoOrdenProduccion>();
         EstadoOrdenProduccion estado = new EstadoOrdenProduccion();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("EstadoOrdenProduccion.findAllLikeCliente", EstadoOrdenProduccion.class);
            query.setParameter("cliente", "%" + buscar + "%");
            listaClientes = (List<EstadoOrdenProduccion>) query.getResultList();
            for (EstadoOrdenProduccion item : listaClientes) {
                estado=item;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta estado");
        } finally {
            em.close();
        }

        return estado;
    }
}
