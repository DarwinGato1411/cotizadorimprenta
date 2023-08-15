/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package   imp.servicios;

import imp.entidades.CortezPosibles;
import imp.entidades.RecorteMaterial;
import imp.entidades.RecorteMaterial;
import imp.entidades.Materiales;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioCorteMaterial {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(RecorteMaterial recorteMaterial) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(recorteMaterial);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar cliente");
        } finally {
            em.close();
        }

    }

    public void eliminar(RecorteMaterial recorteMaterials) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(recorteMaterials));
            em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  cliente" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(RecorteMaterial recorteMaterials) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(recorteMaterials);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar cliente");
        } finally {
            em.close();
        }

    }

    public List<CortezPosibles> FindALlRecorteMaterialPorMaterial(Materiales codMaterial) {

        List<RecorteMaterial> listaRecorteMaterial = new ArrayList<RecorteMaterial>();
        List<CortezPosibles> listaCortes = new ArrayList<CortezPosibles>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            System.out.println("Entra a consultar cortes");
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("RecorteMaterial.findByCodMaterial", RecorteMaterial.class);
            query.setParameter("codMaterial", codMaterial.getCodMaterial());
            listaRecorteMaterial = (List<RecorteMaterial>) query.getResultList();
            System.out.println("Numero de elementos.... " + listaRecorteMaterial.size());
            em.getTransaction().commit();


            em.getTransaction().begin();
            CortezPosibles corte;

            for (RecorteMaterial recorteMaterial : listaRecorteMaterial) {
                corte = new CortezPosibles();
                Query query1 = em.createNamedQuery("CortezPosibles.findByCodCorte", CortezPosibles.class);
                query1.setParameter("codCorte", recorteMaterial.getRecorteMaterialPK().getCodCorte());
                System.out.println("IDDDDDDDDDDDDDDDDDDDDDDDDDD... " + recorteMaterial.getRecorteMaterialPK().getCodCorte());
                corte = (CortezPosibles) query1.getResultList().get(0);
                listaCortes.add(corte);
            }





        } catch (Exception e) {
            System.out.println("Error en lsa consulta cortes");
        } finally {
            em.close();
        }

        return listaCortes;
    }
    
    public List<RecorteMaterial> FindALlRecorteMaterialPorMaterial2(Materiales codMaterial) {

        List<RecorteMaterial> listaRecorteMaterial = new ArrayList<RecorteMaterial>();
       // List<CortezPosibles> listaCortes = new ArrayList<CortezPosibles>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            System.out.println("Entra a consultar cortes");
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("RecorteMaterial.findByCodMaterial", RecorteMaterial.class);
            query.setParameter("codMaterial", codMaterial.getCodMaterial());
            listaRecorteMaterial = (List<RecorteMaterial>) query.getResultList();
            System.out.println("Numero de elementos.... " + listaRecorteMaterial.size());
            em.getTransaction().commit();


            em.getTransaction().begin();
            CortezPosibles corte;
//
//            for (RecorteMaterial recorteMaterial : listaRecorteMaterial) {
//                corte = new CortezPosibles();
//                Query query1 = em.createNamedQuery("CortezPosibles.findByCodCorte", CortezPosibles.class);
//                query1.setParameter("codCorte", recorteMaterial.getRecorteMaterialPK().getCodCorte());
//                System.out.println("IDDDDDDDDDDDDDDDDDDDDDDDDDD... " + recorteMaterial.getRecorteMaterialPK().getCodCorte());
//                corte = (CortezPosibles) query1.getResultList().get(0);
//                listaCortes.add(corte);
//            }





        } catch (Exception e) {
            System.out.println("Error en lsa consulta cortes");
        } finally {
            em.close();
        }

        return listaRecorteMaterial;
    }
}
