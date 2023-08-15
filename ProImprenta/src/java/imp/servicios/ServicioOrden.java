/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package   imp.servicios;

import imp.entidades.Cotizacion;
import imp.entidades.OrdenDeProduccion;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioOrden {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(OrdenDeProduccion ordenDeProduccion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(ordenDeProduccion);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar mano obra");
        } finally {
            em.close();
        }

    }

    public void eliminar(OrdenDeProduccion ordenDeProduccion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(ordenDeProduccion));
            em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  mano obra" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(OrdenDeProduccion ordenDeProduccion) {

        try {
            System.out.println("ORDEN> " + ordenDeProduccion.getOrdSecuencial() + " numero" + ordenDeProduccion.getOrdNumero());
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(ordenDeProduccion);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar ordenDeProduccion");
        } finally {
            em.close();
        }

    }

    public List<OrdenDeProduccion> listaCotizacionLikeNumero(String numOrden) {

        List<OrdenDeProduccion> listaOrden = new ArrayList<OrdenDeProduccion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenDeProduccion.findAllLikeNumero", OrdenDeProduccion.class);
            query.setParameter("numero", "%" + numOrden + "%");
            listaOrden = (List<OrdenDeProduccion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra 6");
        } finally {
            em.close();
        }

        return listaOrden;
    }

    public OrdenDeProduccion listaCotizacionNumeroCot(String numOrden) {

        List<OrdenDeProduccion> listaOrden = new ArrayList<OrdenDeProduccion>();
        OrdenDeProduccion orden = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenDeProduccion.findAllLikeNumero", OrdenDeProduccion.class);
            query.setParameter("numero", "%" + numOrden + "%");
            listaOrden = (List<OrdenDeProduccion>) query.getResultList();
            for (OrdenDeProduccion item : listaOrden) {
                orden = new OrdenDeProduccion();
                orden = item;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra 5");
        } finally {
            em.close();
        }

        return orden;
    }

    public List<OrdenDeProduccion> listaOrdenPendientesNumero(String numOrden) {

        List<OrdenDeProduccion> listaOrden = new ArrayList<OrdenDeProduccion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenDeProduccion.findAllPendientesNumero", OrdenDeProduccion.class);
            query.setParameter("numero", "%" + numOrden + "%");
            listaOrden = (List<OrdenDeProduccion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra 0");
        } finally {
            em.close();
        }

        return listaOrden;
    }

    public List<OrdenDeProduccion> listaOrdenProduccionPendientesCliente(String nombre) {

        List<OrdenDeProduccion> listaOrden = new ArrayList<OrdenDeProduccion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenDeProduccion.findAllPendientesCliente", OrdenDeProduccion.class);
            query.setParameter("nombre", "%" + nombre + "%");
            listaOrden = (List<OrdenDeProduccion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra 1");
        } finally {
            em.close();
        }

        return listaOrden;
    }

    public List<OrdenDeProduccion> listaOrdenProduccionPendientes() {

        List<OrdenDeProduccion> listaOrden = new ArrayList<OrdenDeProduccion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenDeProduccion.findAllPendientes", OrdenDeProduccion.class);

            listaOrden = (List<OrdenDeProduccion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra 2"+e.getMessage());
        } finally {
            em.close();
        }

        return listaOrden;
    }

    public List<OrdenDeProduccion> listaOrdenProduccionPendientesFechas(Date inicio, Date fin) {

        List<OrdenDeProduccion> listaOrden = new ArrayList<OrdenDeProduccion>();
        try {
            SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
            // myDate is the java.util.Date in yyyy-mm-dd format
            // Converting it into String using formatter
            String strDate = sm.format(inicio);
            Date dt = sm.parse(strDate);
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenDeProduccion.findAllFechas", OrdenDeProduccion.class);
            query.setParameter("fechaInicio", dt);
            query.setParameter("fechaFin", fin);
            listaOrden = (List<OrdenDeProduccion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra 2");
        } finally {
            em.close();
        }

        return listaOrden;
    }

    public List<OrdenDeProduccion> listaOrdenProduccionPendientesNombreFechas(Date inicio, Date fin, String valor) {

        List<OrdenDeProduccion> listaOrden = new ArrayList<OrdenDeProduccion>();
        try {
            SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
            // myDate is the java.util.Date in yyyy-mm-dd format
            // Converting it into String using formatter
            String strDate = sm.format(inicio);
            Date dt = sm.parse(strDate);
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenDeProduccion.findAllNombreFechas", OrdenDeProduccion.class);
            query.setParameter("fechaInicio", dt);
            query.setParameter("fechaFin", fin);
            query.setParameter("nombre", "%" + valor + "%");
            listaOrden = (List<OrdenDeProduccion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra 2");
        } finally {
            em.close();
        }

        return listaOrden;
    }

    public OrdenDeProduccion ultimaOrden() {

        List<OrdenDeProduccion> listaOrden = new ArrayList<OrdenDeProduccion>();
        OrdenDeProduccion ordenDeProduccion = new OrdenDeProduccion();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenDeProduccion.findAllDes", OrdenDeProduccion.class);

            listaOrden = (List<OrdenDeProduccion>) query.getResultList();
            if (listaOrden.size() > 0) {
                System.out.println("ultima cotizacion .. " + listaOrden.get(0).getOrdSecuencial());
                ordenDeProduccion = listaOrden.get(0);

            } else {
                ordenDeProduccion = null;
            }
            em.getTransaction().commit();
//            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la orden de produccion");
        } finally {
            em.close();
        }

        return ordenDeProduccion;
    }

    //ordenes cerradas
    public List<OrdenDeProduccion> listaOrdenCerradasNumero(String numOrden) {

        List<OrdenDeProduccion> listaOrden = new ArrayList<OrdenDeProduccion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenDeProduccion.findAllCerradasNumero", OrdenDeProduccion.class);
            query.setParameter("numero", "%" + numOrden + "%");
            listaOrden = (List<OrdenDeProduccion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra 0");
        } finally {
            em.close();
        }

        return listaOrden;
    }

    public List<OrdenDeProduccion> listaOrdenProduccionCerradasCliente(String nombre) {

        List<OrdenDeProduccion> listaOrden = new ArrayList<OrdenDeProduccion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenDeProduccion.findAllCerradasCliente", OrdenDeProduccion.class);
            query.setParameter("nombre", "%" + nombre + "%");
            listaOrden = (List<OrdenDeProduccion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra 1");
        } finally {
            em.close();
        }

        return listaOrden;
    }

    public List<OrdenDeProduccion> listaOrdenProduccionCerradas() {

        List<OrdenDeProduccion> listaOrden = new ArrayList<OrdenDeProduccion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenDeProduccion.findAllCerradas", OrdenDeProduccion.class);

            listaOrden = (List<OrdenDeProduccion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra 2");
        } finally {
            em.close();
        }

        return listaOrden;
    }

    public OrdenDeProduccion ordenByCotizacion(Integer cot) {

        List<OrdenDeProduccion> listaOrden = new ArrayList<OrdenDeProduccion>();
        OrdenDeProduccion ordenDeProduccion = new OrdenDeProduccion();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenDeProduccion.findCotizacion", OrdenDeProduccion.class);
            query.setParameter("codCotizacion", cot);

            listaOrden = (List<OrdenDeProduccion>) query.getResultList();
            if (listaOrden.size() > 0) {
                System.out.println("ultima cotizacion .. " + listaOrden.get(0).getOrdSecuencial());
                for (OrdenDeProduccion item : listaOrden) {
                    ordenDeProduccion = item;

                }

            } else {
                ordenDeProduccion = null;
            }
            em.getTransaction().commit();
//            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la orden de produccion");
        } finally {
            em.close();
        }

        return ordenDeProduccion;
    }

    public OrdenDeProduccion ordenForCotizacion(Cotizacion cot) {

        List<OrdenDeProduccion> listaOrden = new ArrayList<OrdenDeProduccion>();
        OrdenDeProduccion ordenDeProduccion = new OrdenDeProduccion();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenDeProduccion.findOrdenForCotizacion", OrdenDeProduccion.class);
            query.setParameter("codCotizacion", cot);

            listaOrden = (List<OrdenDeProduccion>) query.getResultList();
            if (listaOrden.size() > 0) {
                ordenDeProduccion = listaOrden.get(0);

            } else {
                ordenDeProduccion = null;
            }
            em.getTransaction().commit();
//            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la orden de produccion por cotizacion " + e);
        } finally {
            em.close();
        }

        return ordenDeProduccion;
    }
}
