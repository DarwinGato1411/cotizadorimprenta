/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.servicios;

import imp.entidades.Categoria;
import imp.entidades.Operacion;
import imp.entidades.Producto;
import imp.entidades.TipoOperacion;
import imp.entidades.Ubicacion;
import imp.utilitario.EstadisticaCategoria;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioOperacion {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Operacion operacion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(operacion);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar operacion " + e);
        } finally {
            em.close();
        }

    }

    public void eliminar(Operacion operacion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(operacion));
            em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  operacion " + e);
        } finally {
            em.close();
        }

    }

    public void modificar(Operacion operacion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(operacion);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar operacion " + e);
        } finally {
            em.close();
        }

    }

    public List<Operacion> FindAll() {

        List<Operacion> listaPersonas = new ArrayList<Operacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Operacion.findAll", Operacion.class);
//            query.setParameter("perCedula", cedula);
            listaPersonas = (List<Operacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta operacion all " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }

    public List<Operacion> FindForProducto(Producto producto) {

        List<Operacion> listaPersonas = new ArrayList<Operacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Operacion.findForProducto", Operacion.class);
            query.setParameter("codProducto", producto);
            listaPersonas = (List<Operacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta operacion " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }

    public List<Operacion> FindForNombreProducto(String nombre) {

        List<Operacion> listaPersonas = new ArrayList<Operacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Operacion.findForNombreProducto", Operacion.class);
            query.setParameter("prodNombre", "%" + nombre + "%");
            listaPersonas = (List<Operacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta operacion " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }

    public List<Operacion> FindForRangoFechas(Date inicio, Date fin) {

        List<Operacion> listaPersonas = new ArrayList<Operacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Operacion.findByOpeRangoFecha", Operacion.class);
            query.setParameter("fechaInicio", inicio);
            query.setParameter("fechaFin", fin);
            listaPersonas = (List<Operacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta operacion " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }

    public List<Operacion> FindForProducyoAndFechas(Producto producto, Date inicio, Date fin) {

        List<Operacion> listaPersonas = new ArrayList<Operacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Operacion.findForProductoFechas", Operacion.class);
            query.setParameter("codProducto", producto);
            query.setParameter("fechaInicio", inicio);
            query.setParameter("fechaFin", fin);
            listaPersonas = (List<Operacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta operacion " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }

    public List<Operacion> FindForTipoOperacion(TipoOperacion tipoOperacion) {

        List<Operacion> listaPersonas = new ArrayList<Operacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Operacion.findByTipoOperacion", Operacion.class);
            query.setParameter("codTipoOperacion", tipoOperacion);
            listaPersonas = (List<Operacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta operacion " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }

    public List<Operacion> FindForCategoria(Categoria categoria, String nombre) {

        List<Operacion> listaPersonas = new ArrayList<Operacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Operacion.findByCodCategoria", Operacion.class);
            query.setParameter("codCategoria", categoria);
            query.setParameter("prodNombre", "%" + nombre + "%");
            listaPersonas = (List<Operacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta operacion " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }

    public List<Operacion> FindForUbicacion(Ubicacion ubicacion, String nombre) {

        List<Operacion> listaPersonas = new ArrayList<Operacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Operacion.findByCodUbicacion", Operacion.class);
            query.setParameter("codUbicacion", ubicacion);
            query.setParameter("prodNombre", "%" + nombre + "%");
            listaPersonas = (List<Operacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta operacion " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }

    public List<Operacion> FindForCategoriaUbicacion(Ubicacion ubicacion, Categoria cat) {

        List<Operacion> listaPersonas = new ArrayList<Operacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Operacion.findByCategoriaUbicacion", Operacion.class);
            query.setParameter("codUbicacion", ubicacion);
            query.setParameter("codCategoria", cat);
            listaPersonas = (List<Operacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta operacion " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }

    //ultimo movimiento
    public Operacion FindUltimaSalida() {

        List<Operacion> listaPersonas = new ArrayList<Operacion>();
        Operacion operacion = null;
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Operacion.findUltimaSalida", Operacion.class);
//            query.setParameter("perCedula", cedula);
            listaPersonas = (List<Operacion>) query.getResultList();
            if (listaPersonas.size() > 0) {
                operacion = listaPersonas.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta operacion " + e);
        } finally {
            em.close();
        }

        return operacion;
    }

    public List<Operacion> FindForSalidaRangoFechas(Date inicio, Date fin) {

        List<Operacion> listaPersonas = new ArrayList<Operacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Operacion.findSalidaForFecha", Operacion.class);
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            listaPersonas = (List<Operacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta operacion " + e);
        } finally {
            em.close();
        }
        return listaPersonas;
    }

    public List<EstadisticaCategoria> FindForEstadisticaCategoria() {

        List<EstadisticaCategoria> lista = new ArrayList<EstadisticaCategoria>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT NEW imp.utilitario.EstadisticaCategoria(o.codCategoria.catDescripcion, COUNT(o.codOperacion)) FROM Operacion o GROUP BY o.codCategoria.catDescripcion");
            lista = (List<EstadisticaCategoria>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta operacion " + e.getMessage());
        } finally {
            em.close();
        }
        return lista;
    }

    public List<EstadisticaCategoria> FindForEstadisticaCategoriaUbicacion() {

        List<EstadisticaCategoria> lista = new ArrayList<EstadisticaCategoria>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT NEW imp.utilitario.EstadisticaCategoria(o.codCategoria.catDescripcion, COUNT(o.codOperacion),o.codUbicacion.ubiNombre) FROM Operacion o GROUP BY o.codCategoria.catDescripcion,o.codUbicacion.ubiNombre");
            lista = (List<EstadisticaCategoria>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta operacion " + e.getMessage());
        } finally {
            em.close();
        }
        return lista;
    }
}
