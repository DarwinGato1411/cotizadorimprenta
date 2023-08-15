/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package   imp.servicios;

import imp.entidades.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioCliente {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Cliente cliente) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar cliente");
        } finally {
            em.close();
        }

    }

    public void eliminar(Cliente cliente) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(cliente));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  cliente" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(Cliente cliente) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(cliente);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar cliente");
        } finally {
            em.close();
        }

    }

    public List<Cliente> FindALlCliente() {

        List<Cliente> listaClientes = new ArrayList<Cliente>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Cliente.findAll", Cliente.class);
            query.setMaxResults(100);
//           query.setParameter("codigoUsuario", cliente);
            listaClientes = (List<Cliente>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta cliente");
        } finally {
            em.close();
        }

        return listaClientes;
    }

    public Cliente FindClientePorCedula(String ruc) {

        Cliente cliente = new Cliente();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Cliente.findByRuc", Cliente.class);
            query.setParameter("ruc", ruc);
            List<Cliente> listaCliente = (List<Cliente>) query.getResultList();
            if (listaCliente.size() > 0) {
                if (listaCliente.iterator().hasNext()) {
                    cliente = listaCliente.iterator().next();
                }
            } else {
                return new Cliente("");
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta cliente");
        } finally {
            em.close();
        }

        return cliente;
    }

    public List<Cliente> FindALlClienteLike(String buscar) {

        List<Cliente> listaClientes = new ArrayList<Cliente>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Cliente.findByNombreLike", Cliente.class);
            query.setParameter("buscar", "%" + buscar + "%");
            listaClientes = (List<Cliente>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta cliente");
        } finally {
            em.close();
        }

        return listaClientes;
    }

    public List<Cliente> FindALlNombreComercialLike(String buscar) {

        List<Cliente> listaClientes = new ArrayList<Cliente>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Cliente.findByNombreComercialLike", Cliente.class);
            query.setParameter("buscar", "%" + buscar + "%");
            listaClientes = (List<Cliente>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta cliente");
        } finally {
            em.close();
        }

        return listaClientes;
    }

    public List<Cliente> FindALlRazonSocialLike(String buscar) {

        List<Cliente> listaClientes = new ArrayList<Cliente>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Cliente.findByRazonSocialLike", Cliente.class);
            query.setParameter("buscar", "%" + buscar + "%");
            listaClientes = (List<Cliente>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta cliente");
        } finally {
            em.close();
        }

        return listaClientes;
    }
}
