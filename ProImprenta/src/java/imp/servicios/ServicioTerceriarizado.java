/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package   imp.servicios;

import imp.entidades.Cliente;
import imp.entidades.Terciarizado;
import imp.entidades.Terciarizado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioTerceriarizado {
        
     private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
     public void crear(Terciarizado terciarizado) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(terciarizado);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar terciarizado");
        } finally {
            em.close();
        }

    }
      public void eliminar(Terciarizado terciarizado) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(terciarizado));
            em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  terciarizado" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(Terciarizado terciarizado) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(terciarizado);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar terciarizado");
        } finally {
            em.close();
        }

    }
    
     public List<Terciarizado> FindALlTerciarizadoPorNombre(String nombre) {

        List<Terciarizado> listaTerciarizado = new ArrayList<Terciarizado>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Terciarizado.findAllNombre", Terciarizado.class);
           query.setParameter("nombre", "%"+nombre+"%");
            listaTerciarizado = (List<Terciarizado>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta terciarizado");
        } finally {
            em.close();
        }

        return listaTerciarizado;
    }
     public Terciarizado findCodTerciarizado(Integer codigo) {

        List<Terciarizado> listaTerciarizado = new ArrayList<Terciarizado>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("select a from Terciarizado a where a.codTercearizado=:codTercearizado");
           query.setParameter("codTercearizado",codigo );
            listaTerciarizado = (List<Terciarizado>) query.getResultList();
            em.getTransaction().commit();
            return listaTerciarizado.size()>0?listaTerciarizado.get(0):null;
        } catch (Exception e) {
            System.out.println("Error en lsa consulta terciarizado");
        } finally {
            em.close();
        }

        return null;
    }
}
