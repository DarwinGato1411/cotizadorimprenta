/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package   imp.servicios;


import imp.entidades.UsuarioSistema;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioUsuario {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(UsuarioSistema usuarioSistema) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(usuarioSistema);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar cliente");
        } finally {
            em.close();
        }

    }

    public void eliminar(UsuarioSistema usuarioSistema) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(usuarioSistema));
            em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  cliente" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(UsuarioSistema usuarioSistema) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(usuarioSistema);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar cliente");
        } finally {
            em.close();
        }

    }

    public UsuarioSistema FindALlUsuarioPorUsuario(String usuario) {

        UsuarioSistema usuarioLogeado = new UsuarioSistema();
        List<UsuarioSistema>  listaUsuarios= new ArrayList<UsuarioSistema>();
        try {
//             System.out.println("Consulta el usuarios correctamente");
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("UsuarioSistema.findPorNombre", UsuarioSistema.class);
            query.setParameter("usuUsuario", usuario);
            listaUsuarios = (List<UsuarioSistema>) query.getResultList();
            if (listaUsuarios.size() > 0) {
                if (listaUsuarios.iterator().hasNext()) {
                    usuarioLogeado = listaUsuarios.iterator().next();
                }
            } else {
                return new UsuarioSistema("","");
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta usuarios"+e.getMessage());
        } finally {
            em.close();
        }

        return usuarioLogeado;
    }
       public List<UsuarioSistema> FindALlUsuarioPorLikeNombre(String nombre) {

        UsuarioSistema usuarioLogeado = new UsuarioSistema();
        List<UsuarioSistema>  listaUsuarios= new ArrayList<UsuarioSistema>();
        try {
             System.out.println("Entra a consultar usuarios");
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("UsuarioSistema.findLikeNombre", UsuarioSistema.class);
            query.setParameter("usuUsuario", "%"+nombre+"%");
            listaUsuarios = (List<UsuarioSistema>) query.getResultList();
//            if (listaUsuarios.size() > 0) {
//                if (listaUsuarios.iterator().hasNext()) {
//                    usuarioLogeado = listaUsuarios.iterator().next();
//                }
//            } else {
//                return new UsuarioSistema("","");
//            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta usuarios");
        } finally {
            em.close();
        }

        return listaUsuarios;
    }
}
