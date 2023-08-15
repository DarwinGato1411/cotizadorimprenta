/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import imp.entidades.Cotizacion;
import imp.entidades.DetalleCotizacion;
import imp.seguridad.EnumSesion;
import imp.seguridad.UserCredential;
import  imp.servicios.ServicioCotizacion;
import  imp.servicios.ServicioDetalleCotizacion;
import imp.utilitario.CalcularCabida;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Iframe;

/**
 *
 * @author gato
 */
public class CalculadoraViewModel1 {

    private CalcularCabida calcularCabida = new CalcularCabida();
    private Date fechaInicio = new Date();
    private Date fechaFin = new Date();
    private Date fechaInicioGiganto = new Date();
    private Date fechaFinGiganto = new Date();
    private UserCredential credentialLog = new UserCredential();
    ServicioCotizacion servicioCotizacion = new ServicioCotizacion();
    ServicioDetalleCotizacion servicioDetalleCotizacion = new ServicioDetalleCotizacion();
    List<Cotizacion> listaCotizacionFechas = new ArrayList<Cotizacion>();
    List<Cotizacion> listaCotizacionFechasGiganto = new ArrayList<Cotizacion>();
    List<DetalleCotizacion> listaDetalleCotizacionFechas = new ArrayList<DetalleCotizacion>();
    List<DetalleCotizacion> listaDetalleCotizacionFechasGiganto = new ArrayList<DetalleCotizacion>();
    @Wire("#ifrreportp")
    Iframe ifrreportp;
    @Wire("#ifrreportp1")
    Iframe ifrreportp1;

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("detalle") String calor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);


    }

    public CalculadoraViewModel1() {
        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credentialLog = cre;
    }

    public CalcularCabida getCalcularCabida() {
        return calcularCabida;
    }

    public void setCalcularCabida(CalcularCabida calcularCabida) {
        this.calcularCabida = calcularCabida;
    }

    @Command
    @NotifyChange({"calcularCabida"})
    public void calcularInformacion() throws IOException {

        if (calcularCabida.getAnchoTotal().doubleValue() > 0
                && calcularCabida.getLargoTotal().doubleValue() > 0
                && calcularCabida.getAnchoDescuento().doubleValue() > 0
                && calcularCabida.getLargoDescuento().doubleValue() > 0
                && calcularCabida.getAnchorequerido().doubleValue() > 0
                && calcularCabida.getLargoRequerido().doubleValue() > 0) {
            BigDecimal anchoMenosDecuento = calcularCabida.getAnchoTotal().add(calcularCabida.getAnchoDescuento().negate());
            BigDecimal largoMenosDecuento = calcularCabida.getLargoTotal().add(calcularCabida.getLargoDescuento().negate());
            if ((anchoMenosDecuento.doubleValue() % calcularCabida.getAnchorequerido().doubleValue())
                    <= (largoMenosDecuento.doubleValue() % calcularCabida.getAnchorequerido().doubleValue())) {

                calcularCabida.setNumeroCabidas((anchoMenosDecuento.divide(calcularCabida.getAnchorequerido(), 0, RoundingMode.DOWN)).multiply(largoMenosDecuento.divide(calcularCabida.getLargoRequerido(), 0, RoundingMode.DOWN)));

            } else {
                calcularCabida.setNumeroCabidas((anchoMenosDecuento.divide(calcularCabida.getLargoRequerido(), 0, RoundingMode.DOWN)).multiply(largoMenosDecuento.divide(calcularCabida.getAnchorequerido(), 0, RoundingMode.DOWN)));
            }

            calcularCabida.setNumeroPlacas(calcularCabida.getCantidadSolicitada().divide(calcularCabida.getNumeroCabidas(), 0, RoundingMode.UP));
        }
    }

    @Command
    @NotifyChange({"calcularCabida"})
    public void nuevo() throws IOException {

        calcularCabida = new CalcularCabida();
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

    public UserCredential getCredentialLog() {
        return credentialLog;
    }

    public void setCredentialLog(UserCredential credentialLog) {
        this.credentialLog = credentialLog;
    }

    public List<Cotizacion> getListaCotizacionFechas() {
        return listaCotizacionFechas;
    }

    public void setListaCotizacionFechas(List<Cotizacion> listaCotizacionFechas) {
        this.listaCotizacionFechas = listaCotizacionFechas;
    }

    public List<DetalleCotizacion> getListaDetalleCotizacionFechas() {
        return listaDetalleCotizacionFechas;
    }

    public void setListaDetalleCotizacionFechas(List<DetalleCotizacion> listaDetalleCotizacionFechas) {
        this.listaDetalleCotizacionFechas = listaDetalleCotizacionFechas;
    }

    public List<DetalleCotizacion> getListaDetalleCotizacionFechasGiganto() {
        return listaDetalleCotizacionFechasGiganto;
    }

    public void setListaDetalleCotizacionFechasGiganto(List<DetalleCotizacion> listaDetalleCotizacionFechasGiganto) {
        this.listaDetalleCotizacionFechasGiganto = listaDetalleCotizacionFechasGiganto;
    }

    public Date getFechaInicioGiganto() {
        return fechaInicioGiganto;
    }

    public void setFechaInicioGiganto(Date fechaInicioGiganto) {
        this.fechaInicioGiganto = fechaInicioGiganto;
    }

    public Date getFechaFinGiganto() {
        return fechaFinGiganto;
    }

    public void setFechaFinGiganto(Date fechaFinGiganto) {
        this.fechaFinGiganto = fechaFinGiganto;
    }

    public List<Cotizacion> getListaCotizacionFechasGiganto() {
        return listaCotizacionFechasGiganto;
    }

    public void setListaCotizacionFechasGiganto(List<Cotizacion> listaCotizacionFechasGiganto) {
        this.listaCotizacionFechasGiganto = listaCotizacionFechasGiganto;
    }

    @Command
    @NotifyChange({"listaCotizacionFechas", "listaDetalleCotizacionFechas"})
    public void obternerMateriales() throws FileNotFoundException, IOException {
        generarReporteGeneral();
    }

    @Command
    @NotifyChange({"listaCotizacionFechas", "listaDetalleCotizacionFechas"})
    public void exportarExcel() throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(generarReporteGeneral());
        //create FileInputStream which obtains input bytes from a file in a file system
        //FileInputStream is meant for reading streams of raw bytes such as image data. For reading streams of characters, consider using FileReader.

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                //Writes to this byte array output stream
                bos.write(buf, 0, readNum);

            }
        } catch (IOException ex) {
        }

        byte[] data = bos.toByteArray();


        InputStream mediais = new ByteArrayInputStream(data);
        //media = new AMedia("PruebEXCEL", "xls", "application/vnd.ms-excel", file, true);
        AMedia amedia = new AMedia("Materiales", "xls", "application/vnd.ms-excel", mediais);
        amedia.getStreamData();
        ifrreportp.setContent(amedia);
    }

    private String generarReporteGeneral() throws FileNotFoundException, IOException {
        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp()
                .getRealPath("/reportes");

        String pathSalida = directorioReportes + File.separator + "Materiales.xls";
        System.out.println("Direccion del reporte  " + pathSalida);
        File archivoXLS = new File(pathSalida);
        if (archivoXLS.exists()) {
            archivoXLS.delete();
        }
        archivoXLS.createNewFile();
        FileOutputStream archivo = new FileOutputStream(archivoXLS);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet s = wb.createSheet();

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
        ch0.setCellValue(new HSSFRichTextString("INFORME DE MATERIALES DESDE " + fechaInicio + " HASTA " + fechaFin));
        ch0.setCellStyle(estiloCelda);
        s.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
        r = s.createRow(1);
        HSSFCell ch00 = r.createCell(0);
        ch00.setCellValue(new HSSFRichTextString("Direccion: " + "ADMINISTRATIVA"));
        ch00.setCellStyle(estiloCelda);
        s.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));
        r = s.createRow(2);
        HSSFCell ch000 = r.createCell(0);
        ch000.setCellValue(new HSSFRichTextString("Responsable: " + credentialLog.getUsuarioSistema().getUsuNombreUsuario()));
        ch000.setCellStyle(estiloCelda);
        s.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));
//        r = s.createRow(3);
//        HSSFCell ch0000 = r.createCell(0);
//        ch0000.setCellValue(
//                new HSSFRichTextString("Semana: " + "Lunes 17 hasta 24"));
//        ch0000.setCellStyle(estiloCelda);
//        s.addMergedRegion(new CellRangeAddress(3, 3, 0, 5));
        r = s.createRow(3);
        HSSFCell ch1 = r.createCell(0);
        ch1.setCellValue(new HSSFRichTextString("# COTIZACIÓN"));
        ch1.setCellStyle(estiloCelda);
        HSSFCell ch2 = r.createCell(1);
        ch2.setCellValue(new HSSFRichTextString("CANTIDAD"));
        ch2.setCellStyle(estiloCelda);

        HSSFCell ch3 = r.createCell(2);
        ch3.setCellValue(new HSSFRichTextString("MATERIAL"));
        ch3.setCellStyle(estiloCelda);

        HSSFCell ch4 = r.createCell(3);
        ch4.setCellValue(new HSSFRichTextString("TIPO DE MATERIAL"));
        ch4.setCellStyle(estiloCelda);

        HSSFCell ch5 = r.createCell(4);
        ch5.setCellValue(new HSSFRichTextString("COSTO"));
        ch5.setCellStyle(estiloCelda);

        List<Cotizacion> listaCot = servicioCotizacion.listaPorFechas(fechaInicio, fechaFin);

        listaCotizacionFechas = listaCot;
        listaDetalleCotizacionFechas.clear();
        int rownum = 5;
        for (Cotizacion item : listaCot) {

            List<DetalleCotizacion> listaDetalle = servicioDetalleCotizacion.FindDetalleCotizacionPorCotizacion(item);
            System.out.println("longitud lista " + item.getCotNumero() + " " + item.getDetalleCotizacionCollection());
            for (DetalleCotizacion detalleCotizacion : listaDetalle) {
                if (detalleCotizacion.getDetTipoDetalle().equals("M")
                        || detalleCotizacion.getDetTipoDetalle().equals("MD")) {
                    listaDetalleCotizacionFechas.add(detalleCotizacion);

                    //PERSONAS
                    r = s.createRow(rownum);
                    HSSFCell c0 = r.createCell(0);

                    c0.setCellValue(new HSSFRichTextString(item.getCotNumero()));
                    c0.setCellStyle(estiloCeldaInterna);



                    HSSFCell c1 = r.createCell(1);
                    c1.setCellValue(new HSSFRichTextString(detalleCotizacion.getDetPliegos()));
                    c1.setCellStyle(estiloCeldaInterna);

                    HSSFCell c2 = r.createCell(2);
                    c2.setCellValue(new HSSFRichTextString(detalleCotizacion.getRecorteMaterial().getMateriales().getMatNombre()));
                    c2.setCellStyle(estiloCeldaInterna);

                    HSSFCell c3 = r.createCell(3);
                    c3.setCellValue(new HSSFRichTextString(detalleCotizacion.getRecorteMaterial().getMateriales().getMatTipo()));
                    c3.setCellStyle(estiloCeldaInterna);

                    HSSFCell c4 = r.createCell(4);
                    c4.setCellValue(new HSSFRichTextString(detalleCotizacion.getRecorteMaterial().getMateriales().getMatCosto().toString()));
                    c4.setCellStyle(estiloCeldaInterna);

                    rownum = rownum + 1;
                }
            }


        }
        for (int j = 0; j <= 4; j++) {
//            s.autoSizeColumn(j);
            s.setColumnWidth(j, 8000);
        }

        wb.write(archivo);
        archivo.close();

        return pathSalida;
    }

    @Command
    @NotifyChange({"listaDetalleCotizacionFechasGiganto", "listaCotizacionFechasGiganto"})
    public void obternerMaterialesGiganto() throws FileNotFoundException, IOException {
        generarReporteGeneralGiganto();
    }

    @Command
    @NotifyChange({"listaDetalleCotizacionFechasGiganto", "listaCotizacionFechasGiganto"})
    public void exportarExcelGiganto() throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(generarReporteGeneralGiganto());
        //create FileInputStream which obtains input bytes from a file in a file system
        //FileInputStream is meant for reading streams of raw bytes such as image data. For reading streams of characters, consider using FileReader.

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                //Writes to this byte array output stream
                bos.write(buf, 0, readNum);

            }
        } catch (IOException ex) {
        }

        byte[] data = bos.toByteArray();


        InputStream mediais = new ByteArrayInputStream(data);
        //media = new AMedia("PruebEXCEL", "xls", "application/vnd.ms-excel", file, true);
        AMedia amedia = new AMedia("Materiales", "xls", "application/vnd.ms-excel", mediais);
        amedia.getStreamData();
        ifrreportp1.setContent(amedia);
    }

    private String generarReporteGeneralGiganto() throws FileNotFoundException, IOException {
        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp()
                .getRealPath("/reportes");

        String pathSalida = directorioReportes + File.separator + "MaterialesGiganto.xls";
        System.out.println("Direccion del reporte  " + pathSalida);
        File archivoXLS = new File(pathSalida);
        if (archivoXLS.exists()) {
            archivoXLS.delete();
        }
        archivoXLS.createNewFile();
        FileOutputStream archivo = new FileOutputStream(archivoXLS);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet s = wb.createSheet();

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
        ch0.setCellValue(new HSSFRichTextString("INFORME DE MATERIALES DESDE " + fechaInicioGiganto + " HASTA " + fechaFinGiganto));
        ch0.setCellStyle(estiloCelda);
        s.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
        r = s.createRow(1);
        HSSFCell ch00 = r.createCell(0);
        ch00.setCellValue(new HSSFRichTextString("Direccion: " + "ADMINISTRATIVA"));
        ch00.setCellStyle(estiloCelda);
        s.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));
        r = s.createRow(2);
        HSSFCell ch000 = r.createCell(0);
        ch000.setCellValue(new HSSFRichTextString("Responsable: " + credentialLog.getUsuarioSistema().getUsuNombreUsuario()));
        ch000.setCellStyle(estiloCelda);
        s.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));
        r = s.createRow(3);
        HSSFCell ch1 = r.createCell(0);
        ch1.setCellValue(new HSSFRichTextString("# COTIZACION"));
        ch1.setCellStyle(estiloCelda);
        HSSFCell ch2 = r.createCell(1);
        ch2.setCellValue(new HSSFRichTextString("MATERIAL"));
        ch2.setCellStyle(estiloCelda);

        HSSFCell ch3 = r.createCell(2);
        ch3.setCellValue(new HSSFRichTextString("ANCHO REAL"));
        ch3.setCellStyle(estiloCelda);

        HSSFCell ch4 = r.createCell(3);
        ch4.setCellValue(new HSSFRichTextString("LARGO REAL"));
        ch4.setCellStyle(estiloCelda);

        HSSFCell ch5 = r.createCell(4);
        ch5.setCellValue(new HSSFRichTextString("ANCHO SOLICITADO"));
        ch5.setCellStyle(estiloCelda);

        HSSFCell ch6 = r.createCell(5);
        ch6.setCellValue(new HSSFRichTextString("LARGO SOLICITADO"));
        ch6.setCellStyle(estiloCelda);

        HSSFCell ch7 = r.createCell(6);
        ch7.setCellValue(new HSSFRichTextString("M2"));
        ch7.setCellStyle(estiloCelda);

        List<Cotizacion> listaCot = servicioCotizacion.listaPorFechasGiganto(fechaInicioGiganto, fechaFinGiganto);

        listaCotizacionFechasGiganto = listaCot;
        listaDetalleCotizacionFechasGiganto.clear();
        int rownum = 5;
        for (Cotizacion item : listaCot) {
            List<DetalleCotizacion> listaDetalle = servicioDetalleCotizacion.FindDetalleCotizacionPorCotizacion(item);
            System.out.println("longitud lista " + item.getCotNumero() + " " + item.getDetalleCotizacionCollection());
            for (DetalleCotizacion detalleCotizacion : listaDetalle) {
                if (detalleCotizacion.getDetTipoDetalle().equals("MT")) {
                    listaDetalleCotizacionFechasGiganto.add(detalleCotizacion);

                    //PERSONAS
                    r = s.createRow(rownum);
                    HSSFCell c0 = r.createCell(0);

                    c0.setCellValue(new HSSFRichTextString(item.getCotNumero()));
                    c0.setCellStyle(estiloCeldaInterna);



                    HSSFCell c1 = r.createCell(1);
                    c1.setCellValue(new HSSFRichTextString(detalleCotizacion.getRecorteMaterial().getMateriales().getMatNombre()));
                    c1.setCellStyle(estiloCeldaInterna);

                    HSSFCell c2 = r.createCell(2);
                    c2.setCellValue(new HSSFRichTextString(detalleCotizacion.getRecorteMaterial().getMateriales().getMatAncho().toString()));
                    c2.setCellStyle(estiloCeldaInterna);

                    HSSFCell c3 = r.createCell(3);
                    c3.setCellValue(new HSSFRichTextString(detalleCotizacion.getDetAltoSolicitado().toString()));
                    c3.setCellStyle(estiloCeldaInterna);

                    HSSFCell c4 = r.createCell(4);
                    c4.setCellValue(new HSSFRichTextString(detalleCotizacion.getDetAnchoSolicitado().toString()));
                    c4.setCellStyle(estiloCeldaInterna);

                    HSSFCell c5 = r.createCell(5);
                    c5.setCellValue(new HSSFRichTextString(detalleCotizacion.getDetAltoSolicitado().toString()));
                    c5.setCellStyle(estiloCeldaInterna);

                    HSSFCell c6 = r.createCell(6);
                    c6.setCellValue(new HSSFRichTextString(detalleCotizacion.getDetUnidadCantidad().toString()));
                    c6.setCellStyle(estiloCeldaInterna);

                    rownum = rownum + 1;
                }
            }


        }
        for (int j = 0; j <= 6; j++) {
//            s.autoSizeColumn(j);
            s.setColumnWidth(j, 8000);
        }

        wb.write(archivo);
        archivo.close();

        return pathSalida;
    }
}
