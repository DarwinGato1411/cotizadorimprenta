/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.Categoria;
import imp.entidades.Operacion;
import imp.entidades.Producto;
import imp.entidades.Ubicacion;
import imp.servicios.ServicioCategoria;
import imp.servicios.ServicioOperacion;
import imp.servicios.ServicioProducto;
import imp.servicios.ServicioUbicacion;
import imp.utilitario.EstadisticaCategoria;
import imp.utilitario.InventarioGeneral;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.activation.MimetypesFileTypeMap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Iframe;

/**
 *
 * @author gato
 */
public class Reportes {

    @Wire("#ifrreportp")
    Iframe ifrreportp;
    private List<Operacion> listaOperaciones = new ArrayList<Operacion>();
    private List<Operacion> listaOperacionesProdFechas = new ArrayList<Operacion>();
    private List<Operacion> listaOperacionesCatUbicacion = new ArrayList<Operacion>();
    ServicioOperacion servicioOperacion = new ServicioOperacion();
    ServicioProducto servicioProducto = new ServicioProducto();
    ServicioCategoria servicioCategoria = new ServicioCategoria();
    ServicioUbicacion servicioUbicacion = new ServicioUbicacion();
    private String nombreProducto = "";
    private Date fechaInicio = new Date();
    private Date fechaFin = new Date();
    private Date fechaInicioInv = new Date();
    private Date fechaFinInv = new Date();
    private List<InventarioGeneral> listaInventario = new ArrayList<InventarioGeneral>();
    private List<Producto> listaProductos = new ArrayList<Producto>();
    private Producto productoSelected = new Producto();
    private List<Categoria> listaCategorias = new ArrayList<Categoria>();
    private Categoria categosiaSelected = new Categoria();
    private List<Ubicacion> listaUbicacion = new ArrayList<Ubicacion>();
    Ubicacion ubicacionSelected = new Ubicacion();
    private String nombreProductoCat = "";
    private String nombreProdBuscar = "";

    public Reportes() {
//        consultarForNombreProduc();
        cargarCatUbica();
        // inventarioAll();
        consultarListaProductos();
    }

    private void cargarCatUbica() {
        listaCategorias = servicioCategoria.FindAll();
        listaUbicacion = servicioUbicacion.FindAll();
    }

    @Command
    @NotifyChange({"listaInventario"})
    public void buscarForProducto() {

        inventarioAll();
    }
    
     

    private void inventarioAll() {
        BigDecimal ingreso = BigDecimal.ZERO;
        BigDecimal egreso = BigDecimal.ZERO;
        BigDecimal stock = BigDecimal.ZERO;
        listaInventario.clear();


        List<Producto> lstProd = servicioProducto.FindLikeNombre(nombreProdBuscar);
//        InventarioGeneral inventarioGeneral;
        for (Producto prod : lstProd) {
            ingreso = BigDecimal.ZERO;
            egreso = BigDecimal.ZERO;
            stock = BigDecimal.ZERO;
            List<Operacion> listaOperacions = servicioOperacion.FindForProducto(prod);
            for (Operacion item : listaOperacions) {
                if (item.getCodTipoOperacion().getTipOperacion().equalsIgnoreCase("INGRESO")) {
                    ingreso = ingreso.add(item.getOpeCatidad());
                } else {
                    egreso = egreso.add(item.getOpeCatidad());
                }
            }
            System.out.println("producto " + prod.getProdNombre());
            stock = ingreso.add(egreso.negate());
            String estado = "";
            if (stock.doubleValue() <= prod.getProdCantidadMinima().doubleValue()) {
                estado = "BAJA";
            } else if (stock.doubleValue() > prod.getProdCantidadMinima().doubleValue()
                    && stock.doubleValue() <= (prod.getProdCantidadMinima().multiply(BigDecimal.valueOf(2))).doubleValue()) {
                estado = "MEDIA";
            } else if (stock.doubleValue() > (prod.getProdCantidadMinima().multiply(BigDecimal.valueOf(2))).doubleValue()) {
                estado = "ALTA";
            }
            listaInventario.add(new InventarioGeneral(prod.getProdNombre(),
                    prod.getProdValorIncial(),
                    ingreso,
                    egreso,
                    stock,
                    prod.getProdCostoCompra(),
                    stock.multiply(prod.getProdCostoCompra()),
                    prod.getProdCantidadMinima(),
                    estado));
        }
    }

    private void consultarForNombreProduc() {
        listaOperaciones = servicioOperacion.FindForNombreProducto(nombreProducto);
    }

    private void consultarForRangoFechas() {
        listaOperaciones = servicioOperacion.FindForRangoFechas(fechaInicio, fechaFin);
    }

    private void consultarListaProductos() {
        listaProductos = servicioProducto.FindAll();
    }

    private void consultarProductoAndFechas() {
        listaOperacionesProdFechas = servicioOperacion.FindForProducyoAndFechas(productoSelected, fechaInicio, fechaFin);
    }

    private void consultarCategoria() {
        listaOperacionesCatUbicacion = servicioOperacion.FindForCategoria(categosiaSelected, nombreProductoCat);
    }

    private void consultarUbicacion() {
        listaOperacionesCatUbicacion = servicioOperacion.FindForUbicacion(ubicacionSelected, nombreProductoCat);
    }

    private void consultarCategoriaUbicacion() {
        listaOperacionesCatUbicacion = servicioOperacion.FindForCategoriaUbicacion(ubicacionSelected, categosiaSelected);
    }

    @Command
    @NotifyChange("listaOperacionesCatUbicacion")
    public void buscarPorCategoria() {
        consultarCategoria();
    }

    @Command
    @NotifyChange("listaOperacionesCatUbicacion")
    public void buscarPorUbicacion() {
        consultarUbicacion();
    }

    @Command
    @NotifyChange("listaOperacionesCatUbicacion")
    public void buscarPorCategoriaUbicacion() {
        consultarCategoriaUbicacion();
    }

    @Command
    @NotifyChange("listaOperaciones")
    public void nuevoProducto() {
        consultarProductoAndFechas();
    }

    @Command
    @NotifyChange("listaOperacionesProdFechas")
    public void buscarProductoFechas() {
        consultarProductoAndFechas();
    }

    @Command
    @NotifyChange({"listaOperaciones", "nombreProducto"})
    public void buscarPorNombre() {
//        nombreProducto = valor;
        consultarForNombreProduc();
    }

    @Command
    @NotifyChange({"listaOperaciones", "fechaInicio", "fechaFin"})
    public void buscarPorRangoFechas() {

        consultarForRangoFechas();
    }

    @Command
    @NotifyChange({"listaInventario", "fechaInicioInv", "fechaFinInv"})
    public void inventarioGeneral() {
        inventarioAll();
    }

    @Command
    @NotifyChange("listaUbicacions")
    public void modificarUbicacion(@BindingParam("valor") Ubicacion valor) {
    }

    public List<Operacion> getListaOperaciones() {
        return listaOperaciones;
    }

    public void setListaOperaciones(List<Operacion> listaOperaciones) {
        this.listaOperaciones = listaOperaciones;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<InventarioGeneral> getListaInventario() {
        return listaInventario;
    }

    public void setListaInventario(List<InventarioGeneral> listaInventario) {
        this.listaInventario = listaInventario;
    }

    public Date getFechaInicioInv() {
        return fechaInicioInv;
    }

    public void setFechaInicioInv(Date fechaInicioInv) {
        this.fechaInicioInv = fechaInicioInv;
    }

    public Date getFechaFinInv() {
        return fechaFinInv;
    }

    public void setFechaFinInv(Date fechaFinInv) {
        this.fechaFinInv = fechaFinInv;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public Producto getProductoSelected() {
        return productoSelected;
    }

    public void setProductoSelected(Producto productoSelected) {
        this.productoSelected = productoSelected;
    }

    public List<Operacion> getListaOperacionesProdFechas() {
        return listaOperacionesProdFechas;
    }

    public void setListaOperacionesProdFechas(List<Operacion> listaOperacionesProdFechas) {
        this.listaOperacionesProdFechas = listaOperacionesProdFechas;
    }

    public List<Operacion> getListaOperacionesCatUbicacion() {
        return listaOperacionesCatUbicacion;
    }

    public void setListaOperacionesCatUbicacion(List<Operacion> listaOperacionesCatUbicacion) {
        this.listaOperacionesCatUbicacion = listaOperacionesCatUbicacion;
    }

    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public List<Ubicacion> getListaUbicacion() {
        return listaUbicacion;
    }

    public void setListaUbicacion(List<Ubicacion> listaUbicacion) {
        this.listaUbicacion = listaUbicacion;
    }

    public Categoria getCategosiaSelected() {
        return categosiaSelected;
    }

    public void setCategosiaSelected(Categoria categosiaSelected) {
        this.categosiaSelected = categosiaSelected;
    }

    public Ubicacion getUbicacionSelected() {
        return ubicacionSelected;
    }

    public void setUbicacionSelected(Ubicacion ubicacionSelected) {
        this.ubicacionSelected = ubicacionSelected;
    }

    public String getNombreProductoCat() {
        return nombreProductoCat;
    }

    public void setNombreProductoCat(String nombreProductoCat) {
        this.nombreProductoCat = nombreProductoCat;
    }

    public String getNombreProdBuscar() {
        return nombreProdBuscar;
    }

    public void setNombreProdBuscar(String nombreProdBuscar) {
        this.nombreProdBuscar = nombreProdBuscar;
    }

    @Command
    @NotifyChange({"listaDetalleCotizacionFechasGiganto", "listaCotizacionFechasGiganto"})
    public void exportarExcelGiganto() throws FileNotFoundException, IOException {

        FileInputStream inputStream;

        File dosfile = new File(generarInventario());
        if (dosfile.exists()) {
            inputStream = new FileInputStream(dosfile);
            Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(dosfile), dosfile.getName());
        } else {
        }

    }

    private String generarInventario() throws FileNotFoundException, IOException {
        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp()
                .getRealPath("/reportes");

        String pathSalida = directorioReportes + File.separator + "Inventario.xls";
        System.out.println("Direccion del reporte  " + pathSalida);
        try {

            File archivoXLS = new File(pathSalida);
            if (archivoXLS.exists()) {
                archivoXLS.delete();
            }
            archivoXLS.createNewFile();
            FileOutputStream archivo = new FileOutputStream(archivoXLS);
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet s = wb.createSheet("INVENTARIO");

            //estilo encabezado
            HSSFFont fuente = wb.createFont();
            fuente.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            HSSFCellStyle estiloCelda = wb.createCellStyle();
            estiloCelda.setWrapText(true);
            estiloCelda.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            estiloCelda.setFont(fuente);

            HSSFCellStyle estiloCeldaInterna = wb.createCellStyle();
            estiloCeldaInterna.setWrapText(true);
            estiloCeldaInterna.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
            estiloCeldaInterna.setFont(fuente);

            //estilo dos
            HSSFCellStyle estiloCelda1 = wb.createCellStyle();
            estiloCelda1.setWrapText(true);
            estiloCelda1.setFont(fuente);



            // declare a row object reference
            HSSFRow r = null;
            // declare a cell object reference
            HSSFCell c = null;
            r = s.createRow(0);
            HSSFCell ch0 = r.createCell(0);
            ch0.setCellValue(new HSSFRichTextString("INVENTARIO"));
            ch0.setCellStyle(estiloCelda);
            s.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
            r = s.createRow(1);
            HSSFCell ch00 = r.createCell(0);
            ch00.setCellValue(new HSSFRichTextString("Direccion: " + "ADMINISTRATIVA"));
            ch00.setCellStyle(estiloCelda);
            s.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));
            r = s.createRow(2);
//        HSSFCell ch000 = r.createCell(0);
//        ch000.setCellValue(new HSSFRichTextString("Responsable: " +));
//        ch000.setCellStyle(estiloCelda);
//        s.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));
//        r = s.createRow(3);
            HSSFCell ch1 = r.createCell(0);
            ch1.setCellValue(new HSSFRichTextString("PRODUCTO"));
            ch1.setCellStyle(estiloCelda);
            HSSFCell ch2 = r.createCell(1);
            ch2.setCellValue(new HSSFRichTextString("INGRESOS"));
            ch2.setCellStyle(estiloCelda);

            HSSFCell ch3 = r.createCell(2);
            ch3.setCellValue(new HSSFRichTextString("EGRESOS"));
            ch3.setCellStyle(estiloCelda);

            HSSFCell ch4 = r.createCell(3);
            ch4.setCellValue(new HSSFRichTextString("STOCK"));
            ch4.setCellStyle(estiloCelda);

            HSSFCell ch5 = r.createCell(4);
            ch5.setCellValue(new HSSFRichTextString("COSTO REFERENCIAL"));
            ch5.setCellStyle(estiloCelda);

            HSSFCell ch6 = r.createCell(5);
            ch6.setCellValue(new HSSFRichTextString("COSTO TOTAL"));
            ch6.setCellStyle(estiloCelda);

//            HSSFCell ch7 = r.createCell(6);
//            ch7.setCellValue(new HSSFRichTextString("COSTO TOTAL"));
//            ch7.setCellStyle(estiloCelda);


            int rownum = 3;

            for (InventarioGeneral item : listaInventario) {
                if (true) {


                    //PERSONAS
                    r = s.createRow(rownum);
                    HSSFCell c0 = r.createCell(0);

                    c0.setCellValue(new HSSFRichTextString(item.getProducto().toString()));
                    c0.setCellStyle(estiloCeldaInterna);


//
//                    HSSFCell c1 = r.createCell(1);
//                    c1.setCellValue(new HSSFRichTextString(item.getInicial().toString()));
//                    c1.setCellStyle(estiloCeldaInterna);

                    HSSFCell c1 = r.createCell(1);
                    c1.setCellValue(new HSSFRichTextString(item.getIngreso().toString()));
                    c1.setCellStyle(estiloCeldaInterna);

                    HSSFCell c2 = r.createCell(2);
                    c2.setCellValue(new HSSFRichTextString(item.getSalida().toString()));
                    c2.setCellStyle(estiloCeldaInterna);

                    HSSFCell c3 = r.createCell(3);
                    c3.setCellValue(new HSSFRichTextString(item.getStock().toString()));
                    c3.setCellStyle(estiloCeldaInterna);

                    HSSFCell c4 = r.createCell(4);
                    c4.setCellValue(new HSSFRichTextString(item.getCostoCompra().toString()));
                    c4.setCellStyle(estiloCeldaInterna);

                    HSSFCell c5 = r.createCell(5);
                    c5.setCellValue(new HSSFRichTextString(item.getCostoFinal().toString()));
                    c5.setCellStyle(estiloCeldaInterna);

                    rownum = rownum + 1;
                }



            }
            for (int j = 0; j <= 6; j++) {
//            s.autoSizeColumn(j);
                s.setColumnWidth(j, 4000);
            }

            wb.write(archivo);
            archivo.close();


        } catch (Exception e) {
            System.out.println("error " + e);
        }
        return pathSalida;
    }

    @Command
    @NotifyChange({"listaOperaciones"})
    public void exportaOperacionIngresoEgreso() throws FileNotFoundException, IOException {

        FileInputStream inputStream;

        File dosfile = new File(generarMovimientosIngresosEgresos(listaOperaciones));
        if (dosfile.exists()) {
            inputStream = new FileInputStream(dosfile);
            Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(dosfile), dosfile.getName());
        } else {
        }

    }

    @Command
    @NotifyChange({"listaOperacionesProdFechas"})
    public void exportaOperacionPorFechas() throws FileNotFoundException, IOException {

        FileInputStream inputStream;

        File dosfile = new File(generarMovimientosIngresosEgresos(listaOperacionesProdFechas));
        if (dosfile.exists()) {
            inputStream = new FileInputStream(dosfile);
            Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(dosfile), dosfile.getName());
        } else {
        }

    }

    @Command
    @NotifyChange({"listaOperacionesCatUbicacion"})
    public void exportaCatUbicacion() {

        try {
            FileInputStream inputStream;

            File dosfile = new File(generarMovimientosIngresosEgresos(listaOperacionesCatUbicacion));
            if (dosfile.exists()) {
                inputStream = new FileInputStream(dosfile);
                Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(dosfile), dosfile.getName());
            } else {
            }
        } catch (Exception e) {

            System.out.println("Error al exportar " + e);
        }


    }

    private String generarMovimientosIngresosEgresos(List<Operacion> listaOperaciones) throws FileNotFoundException, IOException {
        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp()
                .getRealPath("/reportes");

        String pathSalida = directorioReportes + File.separator + "Inventario.xls";
        System.out.println("Direccion del reporte  " + pathSalida);
        try {

            File archivoXLS = new File(pathSalida);
            if (archivoXLS.exists()) {
                archivoXLS.delete();
            }
            archivoXLS.createNewFile();
            FileOutputStream archivo = new FileOutputStream(archivoXLS);
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet s = wb.createSheet("INVENTARIO_INGRESOS");

            //estilo encabezado
            HSSFFont fuente = wb.createFont();
            fuente.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            HSSFCellStyle estiloCelda = wb.createCellStyle();
            estiloCelda.setWrapText(true);
            estiloCelda.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            estiloCelda.setFont(fuente);

            HSSFCellStyle estiloCeldaInterna = wb.createCellStyle();
            estiloCeldaInterna.setWrapText(true);
            estiloCeldaInterna.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
            estiloCeldaInterna.setFont(fuente);

            //estilo dos
            HSSFCellStyle estiloCelda1 = wb.createCellStyle();
            estiloCelda1.setWrapText(true);
            estiloCelda1.setFont(fuente);



            // declare a row object reference
            HSSFRow r = null;
            // declare a cell object reference
            HSSFCell c = null;
            r = s.createRow(0);
            HSSFCell ch0 = r.createCell(0);
            ch0.setCellValue(new HSSFRichTextString("INVENTARIO_INGRESOS_EGRESOS"));
            ch0.setCellStyle(estiloCelda);
            s.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
            r = s.createRow(1);
            HSSFCell ch00 = r.createCell(0);
            ch00.setCellValue(new HSSFRichTextString("Direccion: " + "ADMINISTRATIVA"));
            ch00.setCellStyle(estiloCelda);
            s.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));
            r = s.createRow(2);
//        HSSFCell ch000 = r.createCell(0);
//        ch000.setCellValue(new HSSFRichTextString("Responsable: " +));
//        ch000.setCellStyle(estiloCelda);
//        s.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));
//        r = s.createRow(3);
            HSSFCell ch1 = r.createCell(0);
            ch1.setCellValue(new HSSFRichTextString("FECHA"));
            ch1.setCellStyle(estiloCelda);
            HSSFCell ch2 = r.createCell(1);
            ch2.setCellValue(new HSSFRichTextString("CANTIDAD"));
            ch2.setCellStyle(estiloCelda);

            HSSFCell ch3 = r.createCell(2);
            ch3.setCellValue(new HSSFRichTextString("COSTO REFERENCIAL"));
            ch3.setCellStyle(estiloCelda);

            HSSFCell ch4 = r.createCell(3);
            ch4.setCellValue(new HSSFRichTextString("INGRESO/EGRESO"));
            ch4.setCellStyle(estiloCelda);

            HSSFCell ch5 = r.createCell(4);
            ch5.setCellValue(new HSSFRichTextString("MATERIAL"));
            ch5.setCellStyle(estiloCelda);

            HSSFCell ch6 = r.createCell(5);
            ch6.setCellValue(new HSSFRichTextString("CANT * COSTO_REFER"));
            ch6.setCellStyle(estiloCelda);

            HSSFCell ch7 = r.createCell(6);
            ch7.setCellValue(new HSSFRichTextString("OPERACION"));
            ch7.setCellStyle(estiloCelda);

            HSSFCell ch8 = r.createCell(7);
            ch8.setCellValue(new HSSFRichTextString("DESCRIPCION"));
            ch8.setCellStyle(estiloCelda);

            HSSFCell ch9 = r.createCell(8);
            ch9.setCellValue(new HSSFRichTextString("REFERENCIA"));
            ch9.setCellStyle(estiloCelda);

            HSSFCell ch10 = r.createCell(9);
            ch10.setCellValue(new HSSFRichTextString("FACTURA"));
            ch10.setCellStyle(estiloCelda);

//            HSSFCell ch7 = r.createCell(6);
//            ch7.setCellValue(new HSSFRichTextString("COSTO TOTAL"));
//            ch7.setCellStyle(estiloCelda);


            try {
                int rownum = 3;

                for (Operacion item : listaOperaciones) {
                    if (true) {


                        //PERSONAS
                        r = s.createRow(rownum);
                        HSSFCell c0 = r.createCell(0);

                        c0.setCellValue(new HSSFRichTextString(item.getOpeFecha().toString()));
                        c0.setCellStyle(estiloCeldaInterna);


//
//                    HSSFCell c1 = r.createCell(1);
//                    c1.setCellValue(new HSSFRichTextString(item.getInicial().toString()));
//                    c1.setCellStyle(estiloCeldaInterna);

                        HSSFCell c1 = r.createCell(1);
                        c1.setCellValue(new HSSFRichTextString(item.getOpeCatidad().toString()));
                        c1.setCellStyle(estiloCeldaInterna);

                        HSSFCell c2 = r.createCell(2);
                        c2.setCellValue(new HSSFRichTextString(item.getCodProducto().getProdCostoCompra().toString()));
                        c2.setCellStyle(estiloCeldaInterna);

                        HSSFCell c3 = r.createCell(3);
                        c3.setCellValue(new HSSFRichTextString(item.getOpeCostoUltimaCompra().toString()));
                        c3.setCellStyle(estiloCeldaInterna);

                        HSSFCell c4 = r.createCell(4);
                        c4.setCellValue(new HSSFRichTextString(item.getCodProducto().getProdNombre().toString()));
                        c4.setCellStyle(estiloCeldaInterna);

                        HSSFCell c5 = r.createCell(5);
                        c5.setCellValue(new HSSFRichTextString(item.getValorTotal().toString()));
                        c5.setCellStyle(estiloCeldaInterna);

                        HSSFCell c6 = r.createCell(6);
                        c6.setCellValue(new HSSFRichTextString(item.getCodTipoOperacion().getTipOperacion().toString()));
                        c6.setCellStyle(estiloCeldaInterna);

                        HSSFCell c7 = r.createCell(7);
                        c7.setCellValue(new HSSFRichTextString(item.getOpeConcepo().toString()));
                        c7.setCellStyle(estiloCeldaInterna);

                        HSSFCell c8 = r.createCell(8);
                        c8.setCellValue(new HSSFRichTextString(item.getOpeReferencia().toString()));
                        c8.setCellStyle(estiloCeldaInterna);

                        HSSFCell c9 = r.createCell(9);
                        c9.setCellValue(new HSSFRichTextString(item.getOpeFactura().toString()));
                        c9.setCellStyle(estiloCeldaInterna);

                        rownum = rownum + 1;
                    }



                }
            } catch (Exception e) {

                System.out.println("error en los datos " + e);
            }

            for (int j = 0; j <= 9; j++) {
//            s.autoSizeColumn(j);
                s.setColumnWidth(j, 4000);
            }

            wb.write(archivo);
            archivo.close();


        } catch (Exception e) {
            System.out.println("error " + e.getMessage() + " " + e.getLocalizedMessage());
        }
        return pathSalida;
    }
    JFreeChart jfreechart;
    private byte[] graficoBarras;
    String pathSalidaGenero = "";
    private AImage reporteGenero;

    @Command
    @NotifyChange({"reporteGenero"})
    public void graficarForGenero() throws IOException {
        List<EstadisticaCategoria> lstModel = servicioOperacion.FindForEstadisticaCategoria();

        //freechart
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();


        for (EstadisticaCategoria item : lstModel) {

            defaultcategorydataset.addValue(item.getCantidad(), item.getCategoria(), "1");


        }

        jfreechart = ChartFactory.createBarChart(
                "ESTADÍSTICA POR CATEGORIA", // título del
                // grafico
                "", // título de las categorias(eje x)
                "", // titulo de las series(eje y)
                defaultcategorydataset, // conjunto de datos
                PlotOrientation.VERTICAL, // orientación del gráfico
                true, // incluye o no las series
                false, // tooltips?
                false // URLs?
                );
        jfreechart.setBackgroundPaint(Color.decode("#ffffff"));
        // plot maneja el dataset, axes(categories and series) y el rendered
        CategoryPlot plot = (CategoryPlot) jfreechart.getPlot();

        // renderer se uitiliza para getionar las barras
        CategoryItemRenderer renderer = plot.getRenderer();
        CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator(
                "{2}", new DecimalFormat("0"));
        renderer.setBaseItemLabelGenerator(generator);

        BarRenderer rerender1 = (BarRenderer) plot.getRenderer();
        rerender1.setBaseItemLabelsVisible(true);
        rerender1.setItemMargin(0.0);
        rerender1.setShadowVisible(false);

        renderer.setSeriesPaint(0, Color.decode("#4198af"));
        renderer.setSeriesPaint(1, Color.decode("#91c3d5"));
        renderer.setBaseItemLabelPaint(Color.black);

//        plot.setBackgroundPaint(Color.WHITE);
//        plot.setDomainGridlinePaint(Color.white);
//        plot.setRangeGridlinePaint(Color.white);
//        plot.setDomainGridlinesVisible(true);
//        plot.setRangeGridlinesVisible(true);

        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.white);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.white);

        // legendSeries para ubicar las series
        LegendTitle legendSeries = jfreechart.getLegend();
        RectangleEdge posicion = null;
        legendSeries.setPosition(posicion.RIGHT);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        BufferedImage image = jfreechart.createBufferedImage(650, 480);
        graficoBarras = ChartUtilities.encodeAsPNG(image);
        reporteGenero = new AImage("foto", graficoBarras);

        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp()
                .getRealPath("/reportes");

        //crea la carpeta en el caso que no exista
        File baseDir = new File(directorioReportes);
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
        pathSalidaGenero = directorioReportes + File.separator + "reportGenero.jpg";
        System.out.println("RUTA " + pathSalidaGenero);
        ChartUtilities.saveChartAsJPEG(new File(pathSalidaGenero), jfreechart, 500,
                300);

    }

    public JFreeChart getJfreechart() {
        return jfreechart;
    }

    public void setJfreechart(JFreeChart jfreechart) {
        this.jfreechart = jfreechart;
    }

    public byte[] getGraficoBarras() {
        return graficoBarras;
    }

    public void setGraficoBarras(byte[] graficoBarras) {
        this.graficoBarras = graficoBarras;
    }

    public AImage getReporteGenero() {
        return reporteGenero;
    }

    public void setReporteGenero(AImage reporteGenero) {
        this.reporteGenero = reporteGenero;
    }
    //GRAFICA POR UBICACION
    JFreeChart jfreechartUbicacion;
    private byte[] graficoBarrasUbicacion;
    String pathSalidaUbicacion = "";
    private AImage reporteUbicacion;

    @Command
    @NotifyChange({"reporteUbicacion"})
    public void graficarForUbicacion() throws IOException {
        List<EstadisticaCategoria> lstModel = servicioOperacion.FindForEstadisticaCategoriaUbicacion();

        //freechart
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();


        for (EstadisticaCategoria item : lstModel) {

            defaultcategorydataset.addValue(item.getCantidad(), item.getCategoria(), item.getUbicacion());


        }

        jfreechartUbicacion = ChartFactory.createBarChart(
                "ESTADÍSTICA POR UBICACION", // título del
                // grafico
                "", // título de las categorias(eje x)
                "", // titulo de las series(eje y)
                defaultcategorydataset, // conjunto de datos
                PlotOrientation.VERTICAL, // orientación del gráfico
                true, // incluye o no las series
                false, // tooltips?
                false // URLs?
                );
        jfreechartUbicacion.setBackgroundPaint(Color.decode("#ffffff"));
        // plot maneja el dataset, axes(categories and series) y el rendered
        CategoryPlot plot = (CategoryPlot) jfreechartUbicacion.getPlot();

        // renderer se uitiliza para getionar las barras
        CategoryItemRenderer renderer = plot.getRenderer();
        CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator(
                "{2}", new DecimalFormat("0"));
        renderer.setBaseItemLabelGenerator(generator);

        BarRenderer rerender1 = (BarRenderer) plot.getRenderer();
        rerender1.setBaseItemLabelsVisible(true);
        rerender1.setItemMargin(0.0);
        rerender1.setShadowVisible(false);

        renderer.setSeriesPaint(0, Color.decode("#4198af"));
        renderer.setSeriesPaint(1, Color.decode("#91c3d5"));
        renderer.setBaseItemLabelPaint(Color.black);

//        plot.setBackgroundPaint(Color.WHITE);
//        plot.setDomainGridlinePaint(Color.white);
//        plot.setRangeGridlinePaint(Color.white);
//        plot.setDomainGridlinesVisible(true);
//        plot.setRangeGridlinesVisible(true);

        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.white);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.white);

        // legendSeries para ubicar las series
        LegendTitle legendSeries = jfreechartUbicacion.getLegend();
        RectangleEdge posicion = null;
        legendSeries.setPosition(posicion.RIGHT);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        BufferedImage image = jfreechartUbicacion.createBufferedImage(650, 480);
        graficoBarrasUbicacion = ChartUtilities.encodeAsPNG(image);
        reporteUbicacion = new AImage("foto", graficoBarrasUbicacion);

        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp()
                .getRealPath("/reportes");

        //crea la carpeta en el caso que no exista
        File baseDir = new File(directorioReportes);
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
        pathSalidaUbicacion = directorioReportes + File.separator + "reportGenero.jpg";
        System.out.println("RUTA " + pathSalidaUbicacion);
        ChartUtilities.saveChartAsJPEG(new File(pathSalidaUbicacion), jfreechartUbicacion, 500,
                300);

    }

    public String getPathSalidaGenero() {
        return pathSalidaGenero;
    }

    public void setPathSalidaGenero(String pathSalidaGenero) {
        this.pathSalidaGenero = pathSalidaGenero;
    }

    public JFreeChart getJfreechartUbicacion() {
        return jfreechartUbicacion;
    }

    public void setJfreechartUbicacion(JFreeChart jfreechartUbicacion) {
        this.jfreechartUbicacion = jfreechartUbicacion;
    }

    public byte[] getGraficoBarrasUbicacion() {
        return graficoBarrasUbicacion;
    }

    public void setGraficoBarrasUbicacion(byte[] graficoBarrasUbicacion) {
        this.graficoBarrasUbicacion = graficoBarrasUbicacion;
    }

    public String getPathSalidaUbicacion() {
        return pathSalidaUbicacion;
    }

    public void setPathSalidaUbicacion(String pathSalidaUbicacion) {
        this.pathSalidaUbicacion = pathSalidaUbicacion;
    }

    public AImage getReporteUbicacion() {
        return reporteUbicacion;
    }

    public void setReporteUbicacion(AImage reporteUbicacion) {
        this.reporteUbicacion = reporteUbicacion;
    }
}
