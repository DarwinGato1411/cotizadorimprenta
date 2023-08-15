/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.servicios;

import imp.entidades.Materiales;
import imp.entidades.RecorteMaterial;
import imp.entidades.RecorteMaterialPK;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioMateriales {

    ServicioCortez servicioCortez = new ServicioCortez();
    ServicioCorteMaterial servicioCorteMaterial = new ServicioCorteMaterial();
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Materiales material) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(material);
            em.flush();

            System.out.println("codigo de material... " + material.getCodMaterial());
            if (material.getMatTipo().equals("OTROS")
                    || material.getMatTipo().equals("GIGANTOGRAFIA")
                    || material.getMatTipo().equals("DIGITAL")) {
                System.out.println("ENTRE A INSERTAR RECORTEMATERIAL");
                RecorteMaterialPK pK = new RecorteMaterialPK();
                pK.setCodCorte(servicioCortez.FindALlCortezPorOtros().getCodCorte());
                pK.setCodMaterial(material.getCodMaterial());
                RecorteMaterial recorteMaterial = new RecorteMaterial();
                recorteMaterial.setRecorteMaterialPK(pK);
                System.out.println("pirmari PK .. rec " + pK.getCodCorte() + "  mat  " + pK.getCodMaterial());
                em.persist(recorteMaterial);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar cliente " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(Materiales material) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(material));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  cliente" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(Materiales material) {

        try {

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(material);

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar material");
        } finally {
            em.close();
        }

    }

    public List<Materiales> FindALlMaterialesLikeNombre(String nombreMater) {

        List<Materiales> listaMateriales = new ArrayList<Materiales>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Materiales.findAllNombre", Materiales.class);
            query.setParameter("nombreMaterial", "%" + nombreMater + "%");
            query.setMaxResults(300);
            listaMateriales = (List<Materiales>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta contacto");
        } finally {
            em.close();
        }

        return listaMateriales;
    }

    public List<Materiales> FindALlMaterialesImpresionLike(String nombreMater) {

        List<Materiales> listaMateriales = new ArrayList<Materiales>();
        try {
            System.out.println("Entra a consultar materiales para asignar cortez");
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Materiales.findImpresionLike", Materiales.class);
            query.setParameter("nombreMaterial", "%" + nombreMater + "%");
            listaMateriales = (List<Materiales>) query.getResultList();
            System.out.println("NUmero de registros consultados " + listaMateriales.size() + " consulta " + query);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta materiales para asignar cortes " + e);
        } finally {
            em.close();
        }

        return listaMateriales;
    }

    public List<Materiales> FindALlMaterialesImpresionLikeGiganto(String nombreMater) {

        List<Materiales> listaMateriales = new ArrayList<Materiales>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Materiales.findImpresionLikeGiganto", Materiales.class);
            query.setParameter("nombreMaterial", "%" + nombreMater + "%");
            listaMateriales = (List<Materiales>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta contacto");
        } finally {
            em.close();
        }

        return listaMateriales;
    }

    public List<Materiales> FindALlMaterialesOtrosLike(String nombreMater) {

        List<Materiales> listaMateriales = new ArrayList<Materiales>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Materiales.findOtrosLike", Materiales.class);
            query.setParameter("nombreMaterial", "%" + nombreMater + "%");
            listaMateriales = (List<Materiales>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta contacto");
        } finally {
            em.close();
        }

        return listaMateriales;
    }

    public List<Materiales> FindDigitalLike(String nombreMater) {

        List<Materiales> listaMateriales = new ArrayList<Materiales>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Materiales.findDigitalLike", Materiales.class);
            query.setParameter("nombreMaterial", "%" + nombreMater + "%");
            listaMateriales = (List<Materiales>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta contacto");
        } finally {
            em.close();
        }

        return listaMateriales;
    }
}
