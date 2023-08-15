/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.servicios;

import imp.entidades.SalidaProductos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioVistaSalida {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(SalidaProductos salidaProductos) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(salidaProductos);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar salidaProductos " + e);
        } finally {
            em.close();
        }

    }

    public void eliminar(SalidaProductos salidaProductos) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(salidaProductos));
            em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  salidaProductos " + e);
        } finally {
            em.close();
        }

    }

    public void modificar(SalidaProductos salidaProductos) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(salidaProductos);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar salidaProductos " + e);
        } finally {
            em.close();
        }

    }

    public List<SalidaProductos> FindAll() {

        List<SalidaProductos> listaPersonas = new ArrayList<SalidaProductos>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("SalidaProductos.findAll", SalidaProductos.class);
//            query.setParameter("perCedula", cedula);
            listaPersonas = (List<SalidaProductos>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta salidaProductos " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }

    public List<SalidaProductos> FindLikeNombre(String nombre) {

        List<SalidaProductos> listaPersonas = new ArrayList<SalidaProductos>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("SalidaProductos.findLikeNombre", SalidaProductos.class);
            query.setParameter("prodNombre", "%" + nombre + "%");
            listaPersonas = (List<SalidaProductos>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta salidaProductos " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }
}
