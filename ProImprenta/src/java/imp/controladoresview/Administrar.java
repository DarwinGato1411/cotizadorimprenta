/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.Categoria;
import imp.entidades.Operacion;
import imp.entidades.Producto;
import imp.entidades.TipoOperacion;
import imp.entidades.Ubicacion;
import imp.seguridad.EnumSesion;
import imp.seguridad.UserCredential;
import imp.servicios.ServicioCategoria;
import imp.servicios.ServicioOperacion;
import imp.servicios.ServicioProducto;
import imp.servicios.ServicioTipoOperacion;
import imp.servicios.ServicioUbicacion;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.zkoss.io.Files;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Fileupload;

/**
 *
 * @author gato
 */
public class Administrar {

    ServicioProducto servicioProducto = new ServicioProducto();
    ServicioCategoria servicioCategoria = new ServicioCategoria();
    ServicioUbicacion servicioUbicacion = new ServicioUbicacion();
    ServicioTipoOperacion servicioTipoOperacion = new ServicioTipoOperacion();
    ServicioOperacion servicioOperacion = new ServicioOperacion();
    private List<Producto> listaProducto = new ArrayList<Producto>();
    private List<Categoria> listaCategorias = new ArrayList<Categoria>();
    private List<Ubicacion> listaUbicacions = new ArrayList<Ubicacion>();
     AMedia fileContent = null;
    private String filePath = "";
  
    File f = null;
    byte[] buffer = new byte[9 * 1024 * 1024];
    private Operacion operacion = new Operacion();
    private Producto producto = new Producto();
    UserCredential credential = new UserCredential();

    public Administrar() {
        consultarProductos();
        consultarCategorias();
        consultarUbicaciones();
        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;
    }

    private void consultarProductos() {
        listaProducto = servicioProducto.FindAll();
    }

    private void consultarCategorias() {
        listaCategorias = servicioCategoria.FindAll();
    }

    private void consultarUbicaciones() {
        listaUbicacions = servicioUbicacion.FindAll();
    }

    public List<Producto> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(List<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public List<Ubicacion> getListaUbicacions() {
        return listaUbicacions;
    }

    public void setListaUbicacions(List<Ubicacion> listaUbicacions) {
        this.listaUbicacions = listaUbicacions;
    }

    //nuevos registros
    @Command
    @NotifyChange("listaCategorias")
    public void nuevaCategoria() {
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/categoria.zul", null, null);
        window.doModal();
        consultarCategorias();

    }

    @Command
    @NotifyChange("listaUbicacions")
    public void nuevaUbicacion() {
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/ubicacion.zul", null, null);
        window.doModal();
        consultarUbicaciones();

    }

    @Command
    @NotifyChange("listaProducto")
    public void nuevoProducto() {
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/producto.zul", null, null);
        window.doModal();
        consultarProductos();

    }

//    Modificacions
    @Command
    @NotifyChange("listaCategorias")
    public void modificarCategoria(@BindingParam("valor") Categoria valor) {
        servicioCategoria.modificar(valor);
        Messagebox.show("Modificado con exito");
        consultarCategorias();

    }

    @Command
    @NotifyChange("listaUbicacions")
    public void modificarUbicacion(@BindingParam("valor") Ubicacion valor) {
        servicioUbicacion.modificar(valor);
        Messagebox.show("Modificado con exito");
        consultarUbicaciones();

    }

    @Command
    @NotifyChange("listaProducto")
    public void modificarProducto(@BindingParam("valor") Producto valor) {
        final HashMap<String, Producto> map = new HashMap<String, Producto>();
        map.put("parametro", valor);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/producto.zul", null, map);
        window.doModal();
        consultarProductos();

    }

    @Command
    public void cargarInventarioInicial() throws java.text.ParseException {

        try {

            org.zkoss.util.media.Media media = Fileupload.get();
            if (media instanceof org.zkoss.util.media.AMedia) {
                String nombre = media.getName().toString();
                if (media != null && nombre.contains("xls")) {
                    if (media.getByteData().length > 9 * 1024 * 1024) {
                        Messagebox.show("El arhivo seleccionado sobrepasa el tamaño de 9 MB.\n Por favor seleccione un archivo más pequeño.", "Atención", Messagebox.OK, Messagebox.ERROR);

                        return;
                    }

                    Calendar now = Calendar.getInstance();
                    int year = now.get(Calendar.YEAR);
                    int month = now.get(Calendar.MONTH);// Note: zero based!
                    int day = now.get(Calendar.DAY_OF_MONTH);
//                filePath = Executions.getCurrent().getDesktop().getWebApp()
//                        .getRealPath("/");
                    filePath = "D:";
                    String yearPath = year + File.separator + month + File.separator;
                    filePath = filePath + yearPath;

                    File baseDir = new File(filePath);
                    if (!baseDir.exists()) {
                        baseDir.mkdirs();
                    }
//                Filedownload.save(media);
                    Files.copy(new File(filePath + media.getName()),
                            media.getStreamData());

                    filePath = filePath + media.getName();
                    System.out.println("pathhhhhhh " + filePath);
                    f = new File(filePath);
                    // TODO add your handling code here:

//            txtEstado.setText("EN EJECUCIÓN ESPERE UN MOMENTO");

//            txtEstado.setText("FINALIZADO CON ÈXITO");

//            Workbook workbook = Workbook.getWorkbook(new File("C:" + File.separator + "usr" + File.separator + "local" + File.separator + "usuarios.xls")); //Pasamos el excel que vamos a leer
                    Workbook workbook = Workbook.getWorkbook(f); //Pasamos el excel que vamos a leer
//                    Workbook workbook = Workbook.getWorkbook(new File("/usr" + File.separator + "local" + File.separator + "usuarios.xls")); //Pasamos el excel que vamos a leer
                    Sheet sheet = workbook.getSheet(0);
                    //Seleccionamos la hoja que vamos a leer

                    for (int fila = 1; fila < sheet.getRows(); fila++) { //recorremos las filas
                        if (!sheet.getCell(0, fila).getContents().equals("")) {
                            System.out.println("Valor " + sheet.getCell(0, fila).getContents());
                            producto = new Producto();
                            //0 fecha
                            //1 valor inicial
                            //2 nombre
                            //3 marca
                            //4 serie
                            //5 unidad medida
                            //6 categoria
                            //7 estado
                            //8 ubicacion
                            //9 costo
                            //10 proveedor
                            //11 factura
                            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                            String strFecha = sheet.getCell(0, fila).getContents();
                            Date fechaDate = null;
                            try {
                                fechaDate = formato.parse(strFecha);
                                System.out.println(fechaDate.toString());

                            } catch (Exception ex) {
                                System.out.println("error " + ex);
                            }

                            TipoOperacion tipoOperacion = servicioTipoOperacion.FindForNombre("INGRESO");
                            Categoria cat = servicioCategoria.FindAllForDescripcion(sheet.getCell(6, fila).getContents());
                            Ubicacion ubicacion = servicioUbicacion.FindAllForNombre(sheet.getCell(8, fila).getContents());

                            producto.setCodCategoria(cat);
                            producto.setCodUbicacion(ubicacion);
                            producto.setProdSerie(sheet.getCell(4, fila).getContents());
                            producto.setProdNombre(sheet.getCell(2, fila).getContents());
                            producto.setProdCostoCompra(BigDecimal.valueOf(Double.valueOf(sheet.getCell(9, fila).getContents().replace(",", "."))));
                            producto.setProdCostoVenta(BigDecimal.ZERO);
                            producto.setProdValorIncial(BigDecimal.valueOf(Double.valueOf(sheet.getCell(1, fila).getContents().replace(",", "."))));
                            producto.setProdUnidadMedida(sheet.getCell(5, fila).getContents());
                            producto.setProdFechaRegistro(fechaDate);
                            producto.setProdCantidadMinima(BigDecimal.ZERO);
                            producto.setProdMarca(sheet.getCell(3, fila).getContents());



                            operacion = new Operacion(producto.getProdValorIncial(),
                                    producto.getProdFechaRegistro(),
                                    sheet.getCell(7, fila).getContents(),
                                    "INICIO VENTARIO",
                                    "INICIO VENTARIO",
                                    "INICIO VENTARIO",
                                    sheet.getCell(10, fila).getContents(),
                                     sheet.getCell(11, fila).getContents(),
                                    producto.getProdCostoCompra(),
                                    producto.getCodCategoria(),
                                    producto.getCodUbicacion(),
                                    credential.getUsuarioSistema(),
                                    tipoOperacion,
                                    producto,
                                    producto.getProdCostoCompra().multiply(producto.getProdValorIncial()));
                            servicioProducto.crear(producto);

                            servicioOperacion.crear(operacion);

                        }
                    }
                }
            }

        } catch (IOException ex) {
            System.out.println("error " + ex);
        } catch (BiffException ex) {
            System.out.println("error " + ex);

        }
    }
}
