/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.servicios;

import imp.entidades.Categoria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioCategoria {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Categoria categoria) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(categoria);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar categoria " + e);
        } finally {
            em.close();
        }

    }

    public void eliminar(Categoria categoria) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(categoria));
            em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  categoria " + e);
        } finally {
            em.close();
        }

    }

    public void modificar(Categoria categoria) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(categoria);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar categoria " + e);
        } finally {
            em.close();
        }

    }

    public List<Categoria> FindAll() {

        List<Categoria> listaPersonas = new ArrayList<Categoria>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Categoria.findAll", Categoria.class);
//            query.setParameter("perCedula", cedula);
            listaPersonas = (List<Categoria>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta categoria " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }

    public Categoria FindAllForDescripcion(String descripcion) {

        List<Categoria> listaPersonas = new ArrayList<Categoria>();
        Categoria categoria = null;
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Categoria.findByCatDescripcion", Categoria.class);
            query.setParameter("catDescripcion", descripcion);
            listaPersonas = (List<Categoria>) query.getResultList();
            if (listaPersonas.size() > 0) {
                categoria = listaPersonas.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta categoria " + e);
        } finally {
            em.close();
        }

        return categoria;
    }
}
