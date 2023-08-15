/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.servicios;

import imp.entidades.DetalleOrdenSinCotizar;
import imp.entidades.OrdenDeProduccion;
import imp.entidades.OrdenSinCotizacion;
import imp.utilitario.DetalleOrdenSinCotizarArmar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author gato
 */
public class ServicioOrdenSinCotizar {
    
    ServicioDetalleOrdenSinCotizar servicioDetalleOrdenSinCotizar = new ServicioDetalleOrdenSinCotizar();
    private EntityManager em;
    
    public EntityManager getEm() {
        return em;
    }
    
    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    public void guardarOrden(OrdenSinCotizacion ordenSinCotizacion,
            List<DetalleOrdenSinCotizarArmar> listaDetalle,
            List<DetalleOrdenSinCotizarArmar> listaDetalleTerce) {
        
        try {
            DetalleOrdenSinCotizar detalle = null;
            OrdenSinCotizacion cabecera = new OrdenSinCotizacion();
            cabecera = ordenSinCotizacion;
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(cabecera);
//            em.flush();
//            System.out.println("id orden " + cabecera.getCodOrdenSinCotizacion());
            
            for (DetalleOrdenSinCotizarArmar item : listaDetalle) {
                detalle = new DetalleOrdenSinCotizar(item.getDetsinCatidadCantida(), item.getDetsinSubtotal(), item.getDetsinDescripcion(), item.getDetsinValor());
                detalle.setTipoItem(item.getTipoItem());
                detalle.setCodOrdenSinCotizacion(cabecera);
                detalle.setSinTipo(item.getSinTipo());
                em.persist(detalle);
                
            }
            
             for (DetalleOrdenSinCotizarArmar item : listaDetalleTerce) {
                detalle = new DetalleOrdenSinCotizar(item.getDetsinCatidadCantida(), item.getDetsinSubtotal(), item.getDetsinDescripcion(), item.getDetsinValor());
                detalle.setTipoItem(item.getTipoItem());
                detalle.setCodOrdenSinCotizacion(cabecera);
                detalle.setSinTipo(item.getSinTipo());
                detalle.setCodTercearizado(item.getTerciarizado());
                em.persist(detalle);
                
            }
            em.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            System.out.println("Error en insertar ordenSinCotizacion " + e.getMessage());
        } finally {
            em.close();
        }
        
    }
    
    public void eliminar(OrdenSinCotizacion ordenSinCotizacion) {
        
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(ordenSinCotizacion));
            em.getTransaction().commit();
            
        } catch (Exception e) {
            System.out.println("Error en eliminar  ordenSinCotizacion obra" + e);
        } finally {
            em.close();
        }
        
    }
    
    public void modificar(OrdenSinCotizacion ordenSinCotizacion) {
        
        try {
//            System.out.println("ORDEN> " + ordenSinCotizacion.getOrdSecuencial() + " numero" + ordenSinCotizacion.getOrdNumero());
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(ordenSinCotizacion);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar ordenSinCotizacion");
        } finally {
            em.close();
        }
        
    }
    
    public OrdenSinCotizacion ultimaOrden() {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        OrdenSinCotizacion ordenSinCotizacion = new OrdenSinCotizacion();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT o FROM OrdenSinCotizacion o  ORDER BY o.sinNumero DESC");
            query.setMaxResults(2);
            
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            if (listaOrden.size() > 0) {
                System.out.println("ultima cotizacion .. " + listaOrden.get(0).getSinNumero());
                ordenSinCotizacion = listaOrden.get(0);
                
            } else {
                ordenSinCotizacion = null;
            }
            em.getTransaction().commit();
//            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la OrdenSinCotizacion");
        } finally {
            em.close();
        }
        
        return ordenSinCotizacion;
    }

    //ordenes cerradas
    public List<OrdenSinCotizacion> listaOrdenFindAll() {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAll", OrdenSinCotizacion.class);
            query.setMaxResults(200);
//            query.setParameter("numero", "%" + numOrden + "%");
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0");
        } finally {
            em.close();
        }
        
        return listaOrden;
    }
    //ordenes cerradas

    public List<OrdenSinCotizacion> listaOrdenFindAllPub() {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAllDesCierre", OrdenSinCotizacion.class);
//            query.setParameter("numero", "%" + numOrden + "%");
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0");
        } finally {
            em.close();
        }
        
        return listaOrden;
    }

    //ordenes cerradas
    public List<OrdenSinCotizacion> listaOrdenFindAllCliente(String nombre) {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAllPendienteCliente", OrdenDeProduccion.class);
            query.setParameter("nombre", "%" + nombre + "%");
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0");
        } finally {
            em.close();
        }
        
        return listaOrden;
    }
    
    public List<OrdenSinCotizacion> listaOrdenFindAllPendienteCliente(String nombre) {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAllPendienteCliente", OrdenDeProduccion.class);
            query.setParameter("nombre", "%" + nombre + "%");
            query.setMaxResults(300);
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0");
        } finally {
            em.close();
        }
        
        return listaOrden;
    }
    
    public List<OrdenSinCotizacion> listaOrdenFindAllPendienteFecha(Date inicio, Date fin) {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
            // myDate is the java.util.Date in yyyy-mm-dd format
            // Converting it into String using formatter
            String strDate = sm.format(inicio);
            Date dt = sm.parse(strDate);
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAllPendienteFechasSin", OrdenDeProduccion.class);
            query.setParameter("fechaInicio", dt);
            query.setParameter("fechaFin", fin);
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0");
        } finally {
            em.close();
        }
        
        return listaOrden;
    }
    
    public List<OrdenSinCotizacion> listaOrdenFindAllPendienteNombreFecha(Date inicio, Date fin, String valor) {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
            // myDate is the java.util.Date in yyyy-mm-dd format
            // Converting it into String using formatter
            String strDate = sm.format(inicio);
            Date dt = sm.parse(strDate);
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAllPendienteNombreFechasSin", OrdenDeProduccion.class);
            query.setParameter("fechaInicio", dt);
            query.setParameter("fechaFin", fin);
            query.setParameter("nombre", "%" + valor + "%");
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0");
        } finally {
            em.close();
        }
        
        return listaOrden;
    }
    
    public List<OrdenSinCotizacion> listaOrdenFindAllNumero(Integer numero) {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAllPendienteNumero", OrdenDeProduccion.class);
            query.setParameter("numero", numero);
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0");
        } finally {
            em.close();
        }
        
        return listaOrden;
    }

    //metodos para cotizaciones cerradas
    //ordenes cerradas
    public List<OrdenSinCotizacion> listaOrdenFindAllCerradas() {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAllCerradas", OrdenSinCotizacion.class);
//            query.setParameter("numero", "%" + numOrden + "%");
            query.setMaxResults(400);
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0");
        } finally {
            em.close();
        }
        
        return listaOrden;
    }

    //metodos para cotizaciones cerradas
    //ordenes cerradas
    public List<OrdenSinCotizacion> listaOrdenFindClienteCerradas(String nombre) {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAllCerradaCliente", OrdenSinCotizacion.class);
            query.setParameter("nombre", "%" + nombre + "%");
            query.setMaxResults(400);
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0");
        } finally {
            em.close();
        }
        
        return listaOrden;
    }
    
    public List<OrdenSinCotizacion> findAllGeneradanombreComercial(String nombre) {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAllCerradanombreComercial", OrdenSinCotizacion.class);
            query.setParameter("nombre", "%" + nombre + "%");
            query.setParameter("sinEstado", "GENERADA");
            query.setMaxResults(400);
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion nombre comercial " + e.getMessage());
        } finally {
            em.close();
        }
        
        return listaOrden;
    }
    
    public List<OrdenSinCotizacion> findAllCerradanombreComercial(String nombre) {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAllCerradanombreComercial", OrdenSinCotizacion.class);
            query.setParameter("nombre", "%" + nombre + "%");
            query.setParameter("sinEstado", "FINALIZADA");
            query.setMaxResults(400);
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion nombre comercial " + e.getMessage());
        } finally {
            em.close();
        }
        
        return listaOrden;
    }
    
    public List<OrdenSinCotizacion> findAllFacturadanombreComercial(String nombre) {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAllCerradanombreComercial", OrdenSinCotizacion.class);
            query.setParameter("nombre", "%" + nombre + "%");
            query.setParameter("sinEstado", "FACTURADO");
            query.setMaxResults(400);
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion nombre comercial " + e.getMessage());
        } finally {
            em.close();
        }
        
        return listaOrden;
    }
    
    public List<OrdenSinCotizacion> listaOrdenFindAllNumeroCerrada(Integer numero) {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAllCerradaNumero", OrdenDeProduccion.class);
            query.setParameter("numero", numero);
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0");
        } finally {
            em.close();
        }
        
        return listaOrden;
    }

    //ordenes facturadas
    public List<OrdenSinCotizacion> listaOrdenFindAllfacturadas() {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAllFacturadas", OrdenSinCotizacion.class);
//            query.setParameter("numero", "%" + numOrden + "%");
            query.setMaxResults(400);
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0");
        } finally {
            em.close();
        }
        
        return listaOrden;
    }
    
    public List<OrdenSinCotizacion> listaOrdenFindAllPendienteClienteFacturadas(String nombre) {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAllPendienteClienteFacturado", OrdenDeProduccion.class);
            query.setParameter("nombre", "%" + nombre + "%");
            query.setMaxResults(400);
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0");
        } finally {
            em.close();
        }
        
        return listaOrden;
    }
    
    public List<OrdenSinCotizacion> listaOrdenFindAllNumeroFacturadas(Integer numero) {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAllPendienteNumeroFacturado", OrdenDeProduccion.class);
            query.setParameter("numero", numero);
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0");
        } finally {
            em.close();
        }
        
        return listaOrden;
    }
    
    public List<OrdenSinCotizacion> listaOrdenFindFechas(Date inicio, Date fin) {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            // Setting the pattern
            SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
            // myDate is the java.util.Date in yyyy-mm-dd format
            // Converting it into String using formatter
            String strDate = sm.format(inicio);
            Date dt = sm.parse(strDate);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAllFechasFacturada", OrdenSinCotizacion.class);
            query.setParameter("inicio", dt);
            query.setParameter("fin", fin);
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0");
        } finally {
            em.close();
        }
        
        return listaOrden;
    }
    
    public List<OrdenSinCotizacion> findAllFechasFacturada(Date inicio, Date fin, String valor) {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            // Setting the pattern
            SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
            // myDate is the java.util.Date in yyyy-mm-dd format
            // Converting it into String using formatter
            String strDate = sm.format(inicio);
            Date dt = sm.parse(strDate);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAllFechasFacturada", OrdenSinCotizacion.class);
            query.setParameter("inicio", dt);
            query.setParameter("fin", fin);
            query.setParameter("nombre", "%" + valor + "%");
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0");
        } finally {
            em.close();
        }
        
        return listaOrden;
    }
    
    public List<OrdenSinCotizacion> findAllNombreFechasFacturada(Date inicio, Date fin, String valor) {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            // Setting the pattern
            SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
            // myDate is the java.util.Date in yyyy-mm-dd format
            // Converting it into String using formatter
            String strDate = sm.format(inicio);
            Date dt = sm.parse(strDate);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAllNombreFechasFacturada", OrdenSinCotizacion.class);
            query.setParameter("inicio", dt);
            query.setParameter("fin", fin);
            query.setParameter("nombre", "%" + valor + "%");
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0");
        } finally {
            em.close();
        }
        
        return listaOrden;
    }
    
    public List<OrdenSinCotizacion> listaOrdenFindFechasGenerada(Date inicio, Date fin) {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            // Setting the pattern
            SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
            // myDate is the java.util.Date in yyyy-mm-dd format
            // Converting it into String using formatter
            String strDate = sm.format(inicio);
            Date dt = sm.parse(strDate);
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAllFechasFacturadaGenerada", OrdenSinCotizacion.class);
            query.setParameter("inicio", dt);
            query.setParameter("fin", fin);
            
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0 ");
        } finally {
            em.close();
        }
        
        return listaOrden;
    }
    //busqueda por nombre y fechas

    public List<OrdenSinCotizacion> findAllNombreFechasFacturadaGenerada(Date inicio, Date fin, String valor) {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            // Setting the pattern
            SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
            // myDate is the java.util.Date in yyyy-mm-dd format
            // Converting it into String using formatter
            String strDate = sm.format(inicio);
            Date dt = sm.parse(strDate);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAllNombreFechasFacturadaGenerada", OrdenSinCotizacion.class);
            query.setParameter("inicio", dt);
            query.setParameter("fin", fin);
            query.setParameter("nombre", "%" + valor + "%");
            System.out.println(query);
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0 " + e.getMessage());
        } finally {
            em.close();
        }
        
        return listaOrden;
    }
    
    public List<OrdenSinCotizacion> listaOrdenFindFechasCerrada(Date inicio, Date fin) {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            // Setting the pattern
            SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
            // myDate is the java.util.Date in yyyy-mm-dd format
            // Converting it into String using formatter
            String strDate = sm.format(inicio);
            Date dt = sm.parse(strDate);
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAllFechasFacturadaCerrada", OrdenSinCotizacion.class);
            query.setParameter("inicio", dt);
            query.setParameter("fin", fin);
            
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0");
        } finally {
            em.close();
        }
        
        return listaOrden;
    }
    
    public List<OrdenSinCotizacion> findAllNombreFechasFacturadaCerrada(Date inicio, Date fin, String valor) {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            // Setting the pattern
            SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
            // myDate is the java.util.Date in yyyy-mm-dd format
            // Converting it into String using formatter
            String strDate = sm.format(inicio);
            Date dt = sm.parse(strDate);
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findAllNombreFechasFacturadaCerrada", OrdenSinCotizacion.class);
            query.setParameter("inicio", dt);
            query.setParameter("fin", fin);
            query.setParameter("nombre", "%" + valor + "%");
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0");
        } finally {
            em.close();
        }
        
        return listaOrden;
    }
    
    public List<OrdenSinCotizacion> findBySinNumeroFacturaGenerada(String numeroFactura) {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findBySinNumeroFactura", OrdenDeProduccion.class);
            query.setParameter("sinNumeroFactura", numeroFactura);
            query.setParameter("sinEstado", "GENERADA");
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0");
        } finally {
            em.close();
        }
        
        return listaOrden;
    }
    
    public List<OrdenSinCotizacion> findBySinNumeroFacturaFacturada(String numeroFactura) {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findBySinNumeroFactura", OrdenDeProduccion.class);
            query.setParameter("sinNumeroFactura", "%" + numeroFactura + "%");
            query.setParameter("sinEstado", "FACTURADO");
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0");
        } finally {
            em.close();
        }
        
        return listaOrden;
    }
    
    public List<OrdenSinCotizacion> findBySinNumeroFacturaFinalizada(String numeroFactura) {
        
        List<OrdenSinCotizacion> listaOrden = new ArrayList<OrdenSinCotizacion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("OrdenSinCotizacion.findBySinNumeroFactura", OrdenDeProduccion.class);
            query.setParameter("sinNumeroFactura", numeroFactura);
            query.setParameter("sinEstado", "FINALIZADA");
            listaOrden = (List<OrdenSinCotizacion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ordenSinCotizacion obra 0");
        } finally {
            em.close();
        }
        
        return listaOrden;
    }
}
