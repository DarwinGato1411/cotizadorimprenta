/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.servicios;

import  imp.entidades.IngresoProducto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioVistaIngreso {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(IngresoProducto ingresoProducto) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(ingresoProducto);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar ingresoProducto " + e);
        } finally {
            em.close();
        }

    }

    public void eliminar(IngresoProducto ingresoProducto) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(ingresoProducto));
            em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  ingresoProducto " + e);
        } finally {
            em.close();
        }

    }

    public void modificar(IngresoProducto ingresoProducto) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(ingresoProducto);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar ingresoProducto " + e);
        } finally {
            em.close();
        }

    }

    public List<IngresoProducto> FindAll() {

        List<IngresoProducto> listaPersonas = new ArrayList<IngresoProducto>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("IngresoProducto.findAll", IngresoProducto.class);
//            query.setParameter("perCedula", cedula);
            listaPersonas = (List<IngresoProducto>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta ingresoProducto " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }

    public List<IngresoProducto> FindLikeNombre(String nombre) {

        List<IngresoProducto> listaPersonas = new ArrayList<IngresoProducto>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("IngresoProducto.findLikeNombre", IngresoProducto.class);
            query.setParameter("prodNombre", "%" + nombre + "%");
            listaPersonas = (List<IngresoProducto>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta ingresoProducto " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }
}
