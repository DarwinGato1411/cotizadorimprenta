/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package   imp.servicios;

import imp.entidades.Cotizacion;
import imp.entidades.DetalleCotizacion;
import imp.entidades.OrdenDeProduccion;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import imp.utilitario.GraficarCotizacion;
import java.util.Arrays;

/**
 *
 * @author gato
 */
public class ServicioCotizacion {

    private EntityManager em;
    ServicioDetalleCotizacion servicioDetalleCotizacion = new ServicioDetalleCotizacion();
    ServicioOrden servicioOrden = new ServicioOrden();
    ServicioEstadoCotizacion servicioEstadoCotizacion = new ServicioEstadoCotizacion();
    ServicioEstadoOrden servicioEstadoOrden = new ServicioEstadoOrden();

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Cotizacion cotizacion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(cotizacion);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar mano obra");
        } finally {
            em.close();
        }

    }

    public void guardarCotizacion(Cotizacion cotizacion, List<DetalleCotizacion> listaDetalle) {

        try {
            System.out.println("NUEVA COTIZACIONMMMMMMMMMMMMMMMMMM");
            cotizacion.setCodEstCotizacion(servicioEstadoCotizacion.FindALlEstadoSigla("G"));
//            System.out.println("EstadoEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE " + servicioEstadoCotizacion.FindALlEstadoSigla("G").getEstCotNombre());
//            OrdenDeProduccion ordenDeProduccion = new OrdenDeProduccion();
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(cotizacion);
            em.flush();

            for (DetalleCotizacion item : listaDetalle) {
                System.out.println("DETALLE DE COTIZACION "+item);
                item.setCodCotizacion(cotizacion);
                em.persist(item);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar mano obra "+e.getMessage());
            System.out.println("Error en insertar mano obra "+Arrays.toString(e.getStackTrace()));
        } finally {
            em.close();
        }

    }

    public void modificarCotizacion(Cotizacion cotizacion, List<DetalleCotizacion> listaDetalle) {

        try {
            OrdenDeProduccion ordenDeProduccion = new OrdenDeProduccion();
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(cotizacion);
            em.flush();
//            ordenDeProduccion.setCodCotizacion(cotizacion);
//            ordenDeProduccion.setOrdNumero(cotizacion.getCotNumero());
//            servicioOrden.crear(ordenDeProduccion);

            for (DetalleCotizacion item : listaDetalle) {
                item.setCodCotizacion(cotizacion);
                servicioDetalleCotizacion.modificar(item);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar mano obra");
        } finally {
            em.close();
        }

    }

    public void eliminar(Cotizacion cotizacion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(cotizacion));
            em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  mano obra" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(Cotizacion cotizacion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(cotizacion);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar cotizacion");
        } finally {
            em.close();
        }

    }

    public Cotizacion ultimaCotizacion() {
        Cotizacion cotizacion = new Cotizacion();
        List<Cotizacion> listaCotizacion = new ArrayList<Cotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Cotizacion.findAllDescendente", Cotizacion.class);
            listaCotizacion = (List<Cotizacion>) query.getResultList();
            if (listaCotizacion.size() > 0) {
                System.out.println("ultima cotizacion .. " + listaCotizacion.get(0).getCotNumero());
                cotizacion = listaCotizacion.get(0);

            } else {
                cotizacion = null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra");
        } finally {
            em.close();
        }

        return cotizacion;
    }

    public Cotizacion findByCotNumero(String numCotizacion) {

        List<Cotizacion> listaCotizacion = new ArrayList<Cotizacion>();
        Cotizacion cotizacion = new Cotizacion();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Cotizacion.findByCotNumero", Cotizacion.class);
            query.setParameter("cotNumero", numCotizacion);
            listaCotizacion = (List<Cotizacion>) query.getResultList();
            if (listaCotizacion.size() > 0) {
                for (Cotizacion item : listaCotizacion) {
                    cotizacion = item;
                }
            } else {
                cotizacion = null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra");
        } finally {
            em.close();
        }

        return cotizacion;
    }

    public List<Cotizacion> listaCotizacionLikeNumero(String numCotizacion) {

        List<Cotizacion> listaCotizacion = new ArrayList<Cotizacion>();
        Cotizacion cotizacion = new Cotizacion();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Cotizacion.findAllLikeNumero", Cotizacion.class);
            query.setParameter("numeroCotizacion", "%" + numCotizacion + "%");
            listaCotizacion = (List<Cotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra" +e.getMessage());
        } finally {
            em.close();
        }

        return listaCotizacion;
    }

    public List<Cotizacion> findAllLikeCliente(String numCotizacion) {

        List<Cotizacion> listaCotizacion = new ArrayList<Cotizacion>();
        Cotizacion cotizacion = new Cotizacion();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Cotizacion.findAllLikeCliente", Cotizacion.class);
            query.setParameter("cliente", "%" + numCotizacion + "%");
            listaCotizacion = (List<Cotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra");
        } finally {
            em.close();
        }

        return listaCotizacion;
    }

    public List<Cotizacion> listaPorFechas(Date fechaInicio, Date fechaFin) {

        List<Cotizacion> listaCotizacion = new ArrayList<Cotizacion>();
        Cotizacion cotizacion = new Cotizacion();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Cotizacion.findPorFechas", Cotizacion.class);
            query.setParameter("fecha1", fechaInicio);
            query.setParameter("fecha2", fechaFin);
            listaCotizacion = (List<Cotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta lista de cotizacion por fechas ");
        } finally {
            em.close();
        }

        return listaCotizacion;
    }
    public List<Cotizacion> listaPorFechasCerradas(Date fechaInicio, Date fechaFin) {

        List<Cotizacion> listaCotizacion = new ArrayList<Cotizacion>();
        Cotizacion cotizacion = new Cotizacion();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Cotizacion.findPorFechasCerradas", Cotizacion.class);
            query.setParameter("fecha1", fechaInicio);
            query.setParameter("fecha2", fechaFin);
            listaCotizacion = (List<Cotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta lista de cotizacion por fechas ");
        } finally {
            em.close();
        }

        return listaCotizacion;
    }
    public List<Cotizacion> listaPorFechasGeneradas(Date fechaInicio, Date fechaFin) {

        List<Cotizacion> listaCotizacion = new ArrayList<Cotizacion>();
        Cotizacion cotizacion = new Cotizacion();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Cotizacion.findPorFechasGeneradas", Cotizacion.class);
            query.setParameter("fecha1", fechaInicio);
            query.setParameter("fecha2", fechaFin);
            listaCotizacion = (List<Cotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta lista de cotizacion por fechas ");
        } finally {
            em.close();
        }

        return listaCotizacion;
    }

    public List<Cotizacion> listaPorFechasGiganto(Date fechaInicio, Date fechaFin) {

        List<Cotizacion> listaCotizacion = new ArrayList<Cotizacion>();
        Cotizacion cotizacion = new Cotizacion();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Cotizacion.findPorFechasGiganto", Cotizacion.class);
            query.setParameter("fecha1", fechaInicio);
            query.setParameter("fecha2", fechaFin);
            listaCotizacion = (List<Cotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta lista de cotizacion por fechas ");
        } finally {
            em.close();
        }

        return listaCotizacion;
    }

    public List<GraficarCotizacion> listaCotizacionesCerradas(Date fechaInicio, Date fechaFin) {

        List<GraficarCotizacion> listaCotizacion = new ArrayList<GraficarCotizacion>();
        Cotizacion cotizacion = new Cotizacion();
        try {

            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
//             Query query = em.createQuery("SELECT NEW imp.utilitario.GraficarCotizacion( COUNT(c.cotNumero),c.codCliente.vendedorAsignado) FROM Cotizacion c WHERE c.codEstCotizacion.estCotSigla='A' AND c.cotFechaEmision BETWEEN :fechaInicio AND :fechaFin GROUP BY c.codCliente.vendedorAsignado");
//            query.setParameter("fechaInicio", inicio);
//            query.setParameter("fechaFin", fin);
            Query query = em.createNamedQuery("Cotizacion.findAllCerradas", Cotizacion.class);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);
            listaCotizacion = (List<GraficarCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta lista de cotizacion por fechas " + e);
        } finally {
            em.close();
        }

        return listaCotizacion;
    }

    //Cotizaciones cerradas
    public List<Cotizacion> listaCotizacionLikeNumeroCerradas(String numCotizacion) {

        List<Cotizacion> listaCotizacion = new ArrayList<Cotizacion>();
        Cotizacion cotizacion = new Cotizacion();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Cotizacion.findAllLikeNumeroCerradas", Cotizacion.class);
            query.setParameter("numeroCotizacion", "%" + numCotizacion + "%");
            listaCotizacion = (List<Cotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra");
        } finally {
            em.close();
        }

        return listaCotizacion;
    }

    public List<Cotizacion> findAllLikeClienteCerradas(String numCotizacion) {

        List<Cotizacion> listaCotizacion = new ArrayList<Cotizacion>();
        Cotizacion cotizacion = new Cotizacion();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Cotizacion.findAllLikeClienteCerrada", Cotizacion.class);
            query.setParameter("cliente", "%" + numCotizacion + "%");
            listaCotizacion = (List<Cotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra");
        } finally {
            em.close();
        }

        return listaCotizacion;
    }

    public List<Cotizacion> listaCotizacionLikeNumeroFacturadas(String numCotizacion) {

        List<Cotizacion> listaCotizacion = new ArrayList<Cotizacion>();
        Cotizacion cotizacion = new Cotizacion();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Cotizacion.findAllLikeNumeroFacturadas", Cotizacion.class);
            query.setParameter("numeroCotizacion", "%" + numCotizacion + "%");
            listaCotizacion = (List<Cotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra");
        } finally {
            em.close();
        }

        return listaCotizacion;
    }

    public List<Cotizacion> findAllLikeClienteFacturadas(String numCotizacion) {

        List<Cotizacion> listaCotizacion = new ArrayList<Cotizacion>();
        Cotizacion cotizacion = new Cotizacion();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Cotizacion.findAllLikeClienteFacturadas", Cotizacion.class);
            query.setParameter("cliente", "%" + numCotizacion + "%");
            listaCotizacion = (List<Cotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra");
        } finally {
            em.close();
        }

        return listaCotizacion;
    }

    public List<Cotizacion> listaCotizacionFechas(Date inicio, Date fin) {

        List<Cotizacion> listaCotizacion = new ArrayList<Cotizacion>();
        Cotizacion cotizacion = new Cotizacion();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Cotizacion.findAllFacturadaFecha", Cotizacion.class);
            query.setParameter("fechaInicio", inicio);
            query.setParameter("fechaFin", fin);
            listaCotizacion = (List<Cotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta mano obra");
        } finally {
            em.close();
        }

        return listaCotizacion;
    }
}
