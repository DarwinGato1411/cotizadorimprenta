/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.servicios;

import  imp.entidades.TipoOperacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioTipoOperacion {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(TipoOperacion tipoOperacion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(tipoOperacion);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar tipoOperacion " + e);
        } finally {
            em.close();
        }

    }

    public void eliminar(TipoOperacion tipoOperacion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(tipoOperacion));
            em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  tipoOperacion " + e);
        } finally {
            em.close();
        }

    }

    public void modificar(TipoOperacion tipoOperacion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(tipoOperacion);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar tipoOperacion " + e);
        } finally {
            em.close();
        }

    }

    public List<TipoOperacion> FindAll() {

        List<TipoOperacion> listaPersonas = new ArrayList<TipoOperacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("TipoOperacion.findAll", TipoOperacion.class);
//            query.setParameter("perCedula", cedula);
            listaPersonas = (List<TipoOperacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta tipoOperacion " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }

    public TipoOperacion FindForNombre(String nombre) {

        List<TipoOperacion> listTipoOperacions = new ArrayList<TipoOperacion>();
        TipoOperacion tipoOperacion = new TipoOperacion();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("TipoOperacion.findByTipOperacion", TipoOperacion.class);
            query.setParameter("tipOperacion", nombre);
            listTipoOperacions = (List<TipoOperacion>) query.getResultList();
            if (listTipoOperacions.size() > 0) {
                tipoOperacion = listTipoOperacions.get(0);
            } else {
                return null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta tipoOperacion " + e);
        } finally {
            em.close();
        }

        return tipoOperacion;
    }
}
