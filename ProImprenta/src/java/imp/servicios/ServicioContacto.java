/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package   imp.servicios;

import imp.entidades.Cliente;
import imp.entidades.PersonaContacto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioContacto {
    
     private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
     public void crear(PersonaContacto contacto) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(contacto);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar contacto");
        } finally {
            em.close();
        }

    }
      public void eliminar(PersonaContacto contacto) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(contacto));
            em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  contacto" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(PersonaContacto contacto) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(contacto);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar contacto");
        } finally {
            em.close();
        }

    }
    
     public List<PersonaContacto> FindALlContactoPorCliente(Cliente cliente) {

        List<PersonaContacto> listaContacto = new ArrayList<PersonaContacto>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("PersonaContacto.findAllCliente", PersonaContacto.class);
           query.setParameter("codCliente", cliente);
            listaContacto = (List<PersonaContacto>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta contacto");
        } finally {
            em.close();
        }

        return listaContacto;
    }
    
}
