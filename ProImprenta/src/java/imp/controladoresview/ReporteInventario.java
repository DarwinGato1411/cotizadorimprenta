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
import imp.servicios.ServicioVistaIngreso;
import imp.servicios.ServicioVistaSalida;
import imp.utilitario.InventarioGeneral;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;

/**
 *
 * @author Darwin Morocho
 */
public class ReporteInventario {

    ServicioOperacion servicioOperacion = new ServicioOperacion();
    ServicioProducto servicioProducto = new ServicioProducto();
    ServicioCategoria servicioCategoria = new ServicioCategoria();
    ServicioUbicacion servicioUbicacion = new ServicioUbicacion();
    private List<InventarioGeneral> listaInventario = new ArrayList<InventarioGeneral>();
    private List<Categoria> listaCategorias = new ArrayList<Categoria>();
    private Categoria categosiaSelected = new Categoria();
    private List<Ubicacion> listaUbicacion = new ArrayList<Ubicacion>();
    private Ubicacion ubicacionSelected = new Ubicacion();

    public ReporteInventario() {
        cargarCatUbica();
    }

    private void cargarCatUbica() {
        listaCategorias = servicioCategoria.FindAll();
        listaUbicacion = servicioUbicacion.FindAll();
    }

    private void inventarioForCategoria() {
        BigDecimal ingreso = BigDecimal.ZERO;
        BigDecimal egreso = BigDecimal.ZERO;
        BigDecimal stock = BigDecimal.ZERO;
        listaInventario.clear();


        List<Producto> lstProd = servicioProducto.findByCodCategoria(categosiaSelected);
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
                    estado, prod.getCodCategoria().getCatDescripcion(),
                    prod.getCodUbicacion().getUbiNombre()));
        }
    }

    private void inventarioForUbicacion() {
        BigDecimal ingreso = BigDecimal.ZERO;
        BigDecimal egreso = BigDecimal.ZERO;
        BigDecimal stock = BigDecimal.ZERO;
        listaInventario.clear();


        List<Producto> lstProd = servicioProducto.findByCodUbicacion(ubicacionSelected);
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
                    estado, prod.getCodCategoria().getCatDescripcion(),
                    prod.getCodUbicacion().getUbiNombre()));
        }
    }

    private void inventarioForCategoriaAndUbicacion() {
        BigDecimal ingreso = BigDecimal.ZERO;
        BigDecimal egreso = BigDecimal.ZERO;
        BigDecimal stock = BigDecimal.ZERO;
        listaInventario.clear();


        List<Producto> lstProd = servicioProducto.findByCodCategoriaCodUbicacion(ubicacionSelected, categosiaSelected);
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
            
            System.out.println("categoria "+prod.getCodCategoria().getCatDescripcion());
            System.out.println("ubicacion "+prod.getCodUbicacion().getUbiNombre());
            listaInventario.add(new InventarioGeneral(prod.getProdNombre(),
                    prod.getProdValorIncial(),
                    ingreso,
                    egreso,
                    stock,
                    prod.getProdCostoCompra(),
                    stock.multiply(prod.getProdCostoCompra()),
                    prod.getProdCantidadMinima(),
                    estado, prod.getCodCategoria().getCatDescripcion(),
                    prod.getCodUbicacion().getUbiNombre()));
        }
    }

    public List<InventarioGeneral> getListaInventario() {
        return listaInventario;
    }

    public void setListaInventario(List<InventarioGeneral> listaInventario) {
        this.listaInventario = listaInventario;
    }

    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public Categoria getCategosiaSelected() {
        return categosiaSelected;
    }

    public void setCategosiaSelected(Categoria categosiaSelected) {
        this.categosiaSelected = categosiaSelected;
    }

    public List<Ubicacion> getListaUbicacion() {
        return listaUbicacion;
    }

    public void setListaUbicacion(List<Ubicacion> listaUbicacion) {
        this.listaUbicacion = listaUbicacion;
    }

    public Ubicacion getUbicacionSelected() {
        return ubicacionSelected;
    }

    public void setUbicacionSelected(Ubicacion ubicacionSelected) {
        this.ubicacionSelected = ubicacionSelected;
    }

    @Command
    @NotifyChange({"listaInventario"})
    public void buscarForCategoria() {

        inventarioForCategoria();
    }

    @Command
    @NotifyChange({"listaInventario"})
    public void buscarForUbicacion() {

        inventarioForUbicacion();
    }

    @Command
    @NotifyChange({"listaInventario"})
    public void buscarForCategoriaUbicacion() {

        inventarioForCategoriaAndUbicacion();
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

            HSSFCell ch7 = r.createCell(6);
            ch7.setCellValue(new HSSFRichTextString("CATEGORIA"));
            ch7.setCellStyle(estiloCelda);


             HSSFCell ch8 = r.createCell(7);
            ch8.setCellValue(new HSSFRichTextString("UBICACION"));
            ch8.setCellStyle(estiloCelda);
            
            
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
                    
                      HSSFCell c6 = r.createCell(6);
                    c6.setCellValue(new HSSFRichTextString(item.getCategoria().toString()));
                    c6.setCellStyle(estiloCeldaInterna);
                    
                      HSSFCell c7 = r.createCell(7);
                    c7.setCellValue(new HSSFRichTextString(item.getUbicacion().toString()));
                    c7.setCellStyle(estiloCeldaInterna);

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
}
