/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package   imp.servicios;

import imp.entidades.CortezPosibles;
import imp.entidades.CortezPosibles;
import imp.entidades.Materiales;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioCortez {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(CortezPosibles cortezPosibles) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(cortezPosibles);
            em.flush();
            System.out.println("valor de IDDDDDDDDDDDDDDDDDDDDD" + cortezPosibles.getCodCorte());
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar cliente");
        } finally {
            em.close();
        }

    }

    public void eliminar(CortezPosibles cortezPosibles) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(cortezPosibles));
            em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  cliente" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(CortezPosibles cortezPosibles) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(cortezPosibles);

            System.out.println("valor de IDDDDDDDDDDDDDDDDDDDDD" + cortezPosibles.getCodCorte());
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar cliente");
        } finally {
            em.close();
        }

    }

    public List<CortezPosibles> FindALlCortezPosiblesLikeNombre(String descripcion) {

        List<CortezPosibles> listaCortezPosibles = new ArrayList<CortezPosibles>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            System.out.println("Entra a consultar cortes");
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("CortezPosibles.findAllLike", CortezPosibles.class);
            query.setParameter("buscar", "%" + descripcion + "%");
            listaCortezPosibles = (List<CortezPosibles>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta cortes para materiales "+e);
        } finally {
            em.close();
        }

        return listaCortezPosibles;
    }

    public CortezPosibles FindALlCortezPorOtros() {

        CortezPosibles cortezPosibles = new CortezPosibles();
        List<CortezPosibles> listaCortezPosibles = new ArrayList<CortezPosibles>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            System.out.println("Entra a consultar cortes");
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("CortezPosibles.findParaOtros", CortezPosibles.class);
            // query.setParameter("codMaterial", codMaterial);
            listaCortezPosibles = (List<CortezPosibles>) query.getResultList();
            em.getTransaction().commit();
            for (CortezPosibles item : listaCortezPosibles) {
                cortezPosibles = item;
            }
        } catch (Exception e) {
            System.out.println("Error en lsa consulta cortes para otros "+e);
        } finally {
            em.close();
        }

        return cortezPosibles;
    }
}
