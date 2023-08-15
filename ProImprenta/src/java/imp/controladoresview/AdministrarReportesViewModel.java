
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.controladoresview;

import  imp.servicios.ServicioCotizacion;
import imp.utilitario.GraficarCotizacion;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import javax.activation.MimetypesFileTypeMap;
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
import org.jfree.data.category.DefaultCategoryDataset;

import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Filedownload;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;

/**
 *
 * @author gato
 */
public class AdministrarReportesViewModel extends SelectorComposer<Component> {

    private AImage reporteVendedor;
    private Date fechaInicio = new Date();
    private Date fechaFin = new Date();
    //reporte de visitas
    ServicioCotizacion servicioCotizacion = new ServicioCotizacion();

    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);

    }

    public AdministrarReportesViewModel() throws IOException {
//        permisosUsuario();
    }
//    DefaultCategoryModel model;
    JFreeChart jfreechartArea;
    private byte[] graficoBarrasArea;
    String pathSalidaAreas = "";
    //GRAFICAS

    @Command
    @NotifyChange({"reporteVendedor"})
    public void greficarPorVendedor() throws IOException {

//        model = new DefaultCategoryModel();
        List<GraficarCotizacion> listaCotizacion = servicioCotizacion.listaCotizacionesCerradas(fechaInicio, fechaFin);

//        for (ModelArea item : modelAreas) {
//            model.setValue("Areas", item.getArea(), item.getCantidad());
//        }
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();


        for (GraficarCotizacion item : listaCotizacion) {
//            System.out.println("cantidad " + item.getCantidad() + " " + item.getArea());
            defaultcategorydataset.addValue(item.getValor(), "a1", item.getVendedor());


        }
        //USANDO FREECHART
        jfreechartArea = ChartFactory.createBarChart(
                "ESTADISTICA DE COTIZACIONES CERRADAS POR VENDEDOR", // título del
                // grafico
                "", // título de las categorias(eje x)
                "", // titulo de las series(eje y)
                defaultcategorydataset, // conjunto de datos
                PlotOrientation.VERTICAL, // orientación del gráfico
                false, // incluye o no las series
                true, // tooltips?
                false // URLs?
                );
        jfreechartArea.setBackgroundPaint(Color.decode("#ffffff"));
        // plot maneja el dataset, axes(categories and series) y el rendered
        CategoryPlot plot = (CategoryPlot) jfreechartArea.getPlot();

        // renderer se uitiliza para getionar las barras
        CategoryItemRenderer renderer = plot.getRenderer();
        CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator(
                "{2}", new DecimalFormat("0"));
        renderer.setBaseItemLabelGenerator(generator);

        BarRenderer rerender1 = (BarRenderer) plot.getRenderer();
        rerender1.setBaseItemLabelsVisible(true);
        rerender1.setItemMargin(0.0);
        rerender1.setDrawBarOutline(false);

        rerender1.setShadowVisible(false);

        renderer.setSeriesPaint(0, Color.decode("#4198af"));

        renderer.setBaseItemLabelPaint(Color.black);
        renderer.setBasePaint(Color.BLACK);



        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.WHITE);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.white);



        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        BufferedImage image = jfreechartArea.createBufferedImage(650, 480);
        graficoBarrasArea = ChartUtilities.encodeAsPNG(image);
        reporteVendedor = new AImage("foto", graficoBarrasArea);



        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp()
                .getRealPath("/reportes");

        //crea la carpeta en el caso que no exista
        File baseDir = new File(directorioReportes);
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
        pathSalidaAreas = directorioReportes + File.separator + "reportArea.jpg";
        System.out.println("RUTA " + pathSalidaAreas);
        ChartUtilities.saveChartAsJPEG(new File(pathSalidaAreas), jfreechartArea, 500,
                300);

    }

    @Command
    public void descargarArea() throws FileNotFoundException {

        FileInputStream inputStream;

        File dosfile = new File(pathSalidaAreas);
        if (dosfile.exists()) {
            inputStream = new FileInputStream(dosfile);
            Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(dosfile), dosfile.getName());
        } else {
        }
    }

    public AImage getReporteVendedor() {
        return reporteVendedor;
    }

    public void setReporteVendedor(AImage reporteVendedor) {
        this.reporteVendedor = reporteVendedor;
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

    public String getPathSalidaAreas() {
        return pathSalidaAreas;
    }

    public void setPathSalidaAreas(String pathSalidaAreas) {
        this.pathSalidaAreas = pathSalidaAreas;
    }
    
    
}
