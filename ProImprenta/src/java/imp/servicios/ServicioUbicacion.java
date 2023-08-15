/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.servicios;

import  imp.entidades.Ubicacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioUbicacion {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Ubicacion ubicacion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(ubicacion);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar ubicacion " + e);
        } finally {
            em.close();
        }

    }

    public void eliminar(Ubicacion ubicacion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(ubicacion));
            em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  ubicacion " + e);
        } finally {
            em.close();
        }

    }

    public void modificar(Ubicacion ubicacion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(ubicacion);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar ubicacion " + e);
        } finally {
            em.close();
        }

    }

    public List<Ubicacion> FindAll() {

        List<Ubicacion> listaPersonas = new ArrayList<Ubicacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Ubicacion.findAll", Ubicacion.class);
//            query.setParameter("perCedula", cedula);
            listaPersonas = (List<Ubicacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta ubicacion " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }

    public Ubicacion FindAllForNombre(String nombre) {

        List<Ubicacion> listaPersonas = new ArrayList<Ubicacion>();
        Ubicacion ubicacion= new Ubicacion();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Ubicacion.findByUbiNombre", Ubicacion.class);
            query.setParameter("ubiNombre", nombre);
            listaPersonas = (List<Ubicacion>) query.getResultList();
              if (listaPersonas.size() > 0) {
                ubicacion = listaPersonas.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta ubicacion " + e);
        } finally {
            em.close();
        }

        return ubicacion;
    }
}
