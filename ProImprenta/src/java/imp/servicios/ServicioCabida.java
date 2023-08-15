/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package  imp.servicios;

import imp.entidades.Cabida;
import imp.entidades.Cabida;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioCabida {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Cabida cabida) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(cabida);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar cabida");
        } finally {
            em.close();
        }

    }

    public void eliminar(Cabida cabida) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(cabida));
            em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  cabida" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(Cabida cabida) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(cabida);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar cabida");
        } finally {
            em.close();
        }

    }

    public List<Cabida> FindALlCabidaLikeNombre(String descripcion) {

        List<Cabida> listaCabida = new ArrayList<Cabida>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Cabida.findByCodCabidaLike", Cabida.class);
            query.setParameter("descripcion", "%" + descripcion + "%");
            listaCabida = (List<Cabida>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta cabida");
        } finally {
            em.close();
        }

        return listaCabida;
    }

 public List<Cabida> FindALlCabiPorUXF(Integer uxf) {

        List<Cabida> listaCabida = new ArrayList<Cabida>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Cabida.findByUxf", Cabida.class);
            query.setParameter("cabUxf", uxf);
            listaCabida = (List<Cabida>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta cabida por uxf");
        } finally {
            em.close();
        }

        return listaCabida;
    }


    public Cabida obtenerCabidaOptima(Integer valor) {

        Cabida cabida = new Cabida();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Cabida.findCabidaOptima", Cabida.class);
            query.setParameter("cabCategoria", valor);
            cabida = (Cabida) query.getResultList().get(0);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta cabida");
        } finally {
            em.close();
        }

        return cabida;
    }
}
