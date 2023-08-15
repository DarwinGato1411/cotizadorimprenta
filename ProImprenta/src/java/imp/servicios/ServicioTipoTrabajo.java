/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package   imp.servicios;

import imp.entidades.TipoTrabajo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioTipoTrabajo {
     
     private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(TipoTrabajo tipoTrabajo) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(tipoTrabajo);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar tipoTrabajo");
        } finally {
            em.close();
        }

    }

    public void eliminar(TipoTrabajo tipoTrabajo) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(tipoTrabajo));
            em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  tipoTrabajo" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(TipoTrabajo tipoTrabajo) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(tipoTrabajo);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar tipoTrabajo");
        } finally {
            em.close();
        }

    }
     public List<TipoTrabajo> FindALlTipoTrabajoLike(String nombreMater) {

        List<TipoTrabajo> listaTipoTrabajo = new ArrayList<TipoTrabajo>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("TipoTrabajo.findAllLike", TipoTrabajo.class);
           query.setParameter("buscar", "%"+nombreMater+"%");
            listaTipoTrabajo = (List<TipoTrabajo>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta contacto");
        } finally {
            em.close();
        }

        return listaTipoTrabajo;
    }
    
    
}
