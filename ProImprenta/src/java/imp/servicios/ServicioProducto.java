/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.servicios;

import imp.entidades.Categoria;
import imp.entidades.Producto;
import imp.entidades.Ubicacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioProducto {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Producto producto) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(producto);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar producto " + e);
        } finally {
            em.close();
        }

    }

    public void eliminar(Producto producto) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(producto));
            em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  producto " + e);
        } finally {
            em.close();
        }

    }

    public void modificar(Producto producto) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(producto);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar producto " + e);
        } finally {
            em.close();
        }

    }

    public List<Producto> FindAll() {

        List<Producto> listaPersonas = new ArrayList<Producto>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Producto.findAll", Producto.class);
//            query.setParameter("perCedula", cedula);
            listaPersonas = (List<Producto>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta producto " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }

    public List<Producto> FindLikeNombre(String nombre) {

        List<Producto> listaPersonas = new ArrayList<Producto>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Producto.findLikeNombre", Producto.class);
            query.setParameter("prodNombre", "%" + nombre + "%");
            listaPersonas = (List<Producto>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta producto " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }

    public List<Producto> findByCodCategoria(Categoria categoria) {

        List<Producto> listaPersonas = new ArrayList<Producto>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Producto.findByCodCategoria", Producto.class);
            query.setParameter("codCategoria", categoria);
            listaPersonas = (List<Producto>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta producto " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }

    public List<Producto> findByCodUbicacion(Ubicacion ubicacion) {

        List<Producto> listaPersonas = new ArrayList<Producto>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Producto.findByCodUbicacion", Producto.class);
            query.setParameter("codUbicacion", ubicacion);
            listaPersonas = (List<Producto>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta producto " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }

    public List<Producto> findByCodCategoriaCodUbicacion(Ubicacion ubicacion, Categoria categoria) {

        List<Producto> listaPersonas = new ArrayList<Producto>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Producto.findByCodCategoriaCodUbicacion", Producto.class);
            query.setParameter("codCategoria", categoria);
            query.setParameter("codUbicacion", ubicacion);
            listaPersonas = (List<Producto>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta producto " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }
}
