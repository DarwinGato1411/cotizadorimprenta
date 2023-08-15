/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package   imp.servicios;

import imp.entidades.ParametrizacionPorcentajes;
import imp.entidades.Materiales;
import imp.entidades.ParametrizacionPorcentajes;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioParametros {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(ParametrizacionPorcentajes parametrizacionPorcentajes) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(parametrizacionPorcentajes);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar ParametrizacionPorcentajes");
        } finally {
            em.close();
        }

    }

    public void eliminar(ParametrizacionPorcentajes parametrizacionPorcentajes) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(parametrizacionPorcentajes));
            em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  ParametrizacionPorcentajes" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(ParametrizacionPorcentajes parametrizacionPorcentajes) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(parametrizacionPorcentajes);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar ParametrizacionPorcentajes");
        } finally {
            em.close();
        }

    }

    public List<ParametrizacionPorcentajes> FindALlParametrizacionPorcentajes() {

        List<ParametrizacionPorcentajes> listaParametrizacionPorcentajes = new ArrayList<ParametrizacionPorcentajes>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("ParametrizacionPorcentajes.findAll", ParametrizacionPorcentajes.class);
            // query.setParameter("codMaterial", materiales);
            listaParametrizacionPorcentajes = (List<ParametrizacionPorcentajes>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ParametrizacionPorcentajes");
        } finally {
            em.close();
        }

        return listaParametrizacionPorcentajes;
    }

    public ParametrizacionPorcentajes porcentajeUtilidad() {
        ParametrizacionPorcentajes parametrizacionPorcentajes = new ParametrizacionPorcentajes();
        List<ParametrizacionPorcentajes> listaParametrizacionPorcentajes = new ArrayList<ParametrizacionPorcentajes>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("ParametrizacionPorcentajes.findAll", ParametrizacionPorcentajes.class);
            // query.setParameter("codMaterial", materiales);
            listaParametrizacionPorcentajes = (List<ParametrizacionPorcentajes>) query.getResultList();
            em.getTransaction().commit();
            for (ParametrizacionPorcentajes parametrizacionPorcentajes1 : listaParametrizacionPorcentajes) {
                parametrizacionPorcentajes = parametrizacionPorcentajes1;
            }
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ParametrizacionPorcentajes");
        } finally {
            em.close();
        }

        return parametrizacionPorcentajes;
    }
}
