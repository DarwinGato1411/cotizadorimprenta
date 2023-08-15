/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package   imp.servicios;

import imp.entidades.DetalleOrdenSinCotizar;
import imp.entidades.OrdenDeProduccion;
import imp.entidades.SolicitarMaterial;
import imp.entidades.UsuarioSistema;
import imp.utilitario.DetalleOrdenSinCotizarArmar;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioSolicitarMaterial {

    ServicioDetalleOrdenSinCotizar servicioDetalleOrdenSinCotizar = new ServicioDetalleOrdenSinCotizar();
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void guardar(SolicitarMaterial solicitarMaterial) {

        try {

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(solicitarMaterial);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar solicitarMaterial obra");
        } finally {
            em.close();
        }

    }

    public void eliminar(SolicitarMaterial solicitarMaterial) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(solicitarMaterial));
            em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  solicitarMaterial obra" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(SolicitarMaterial solicitarMaterial) {

        try {
//            System.out.println("ORDEN> " + solicitarMaterial.getOrdSecuencial() + " numero" + solicitarMaterial.getOrdNumero());
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(solicitarMaterial);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar solicitarMaterial");
        } finally {
            em.close();
        }

    }

    //ordenes cerradas
    public List<SolicitarMaterial> findAllSolicitarMaterial() {

        List<SolicitarMaterial> listaOrden = new ArrayList<SolicitarMaterial>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("SolicitarMaterial.findAll", SolicitarMaterial.class);
//            query.setParameter("numero", "%" + numOrden + "%");
            listaOrden = (List<SolicitarMaterial>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta solicitarMaterial obra 0");
        } finally {
            em.close();
        }

        return listaOrden;
    }
     public List<SolicitarMaterial> findAllSolicitarMaterialEstado(String estado) {

        List<SolicitarMaterial> listaOrden = new ArrayList<SolicitarMaterial>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("SolicitarMaterial.findAllEstado", SolicitarMaterial.class);
            query.setParameter("estado", estado);
            listaOrden = (List<SolicitarMaterial>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta solicitarMaterial obra 0");
        } finally {
            em.close();
        }

        return listaOrden;
    }
    //ordenes cerradas

    public List<SolicitarMaterial> FindAllForUsuario(UsuarioSistema usuarioSistema) {

        List<SolicitarMaterial> listaOrden = new ArrayList<SolicitarMaterial>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("SolicitarMaterial.findAllUsuario", SolicitarMaterial.class);
            query.setParameter("codUsuarioSistema", usuarioSistema);
            listaOrden = (List<SolicitarMaterial>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta solicitarMaterial obra 0");
        } finally {
            em.close();
        }

        return listaOrden;
    }

   
}
