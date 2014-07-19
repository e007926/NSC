package Ver2;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author SmallFour
 */
public class Scatter extends ApplicationFrame {

    FileInputStream fis;
    POIFSFileSystem fs;
    HSSFWorkbook wb;
    HSSFSheet sheet;
    int COUNT;
    double B1 = 0;
    double B0 = 0;
    double[][] store;

    public Scatter(final String title) {
        super(title);
        try {
            fis = new FileInputStream("D:\\co.xls");
            fs = new POIFSFileSystem(fis);
            wb = new HSSFWorkbook(fs);
            sheet = wb.getSheetAt(0);
            COUNT = sheet.getLastRowNum();

            int size = COUNT - 6 + 1;
            store = new double[size][2];
            for (int i = 6; i <= COUNT; i++) {
                for (int j = 0; j < 2; j++) {
                    store[i - 6][0] = sheet.getRow(i).getCell(0).getNumericCellValue();
                    store[i - 6][1] = sheet.getRow(i).getCell(1).getNumericCellValue();
                }
            }

            double sumOfPoint = 0;
            double sumOfLine = 0;
            double averageOfPoint;
            double averageOfLine;
            double Sxx;
            //double Syy;
            double Sxy;

            for (int i = 0; i < store.length; i++) {
                sumOfPoint += store[i][0];
                sumOfLine += store[i][1];
            }
            averageOfPoint = sumOfPoint / size;
            averageOfLine = sumOfLine / size;

            int temp1 = 0;
            //int temp2 = 0;
            int temp3 = 0;

            for (int i = 0; i < store.length; i++) {
                temp1 += Math.pow(store[i][0], 2);
                //temp2 += Math.pow(store[i][1], 2);
                temp3 += store[i][0] * store[i][1];
            }
            Sxx = temp1 - store.length * Math.pow(averageOfPoint, 2);
            //Syy = temp2 - store.length * Math.pow(averageOfLine, 2);
            Sxy = temp3 - store.length * averageOfPoint * averageOfLine;
            B1 = Sxy / Sxx;
            B0 = averageOfLine - B1 * averageOfPoint;
        } catch (IOException ex) {
        }
    }

    public String getEq() {
        return "y = " + B1 + " x + " + B0;
    }
    // create a dataset...
    private static final XYSeries series = new XYSeries("案例");

    private XYDataset createDataset() {
        XYSeriesCollection result = new XYSeriesCollection();
        populateData();
        result.addSeries(series);
        return result;
    }

    /**
     * Populates the data array with the values in co.xls.
     */
    public void populateData() {
        for (int i = 6; i <= COUNT; i++) {
            series.add(sheet.getRow(i).getCell(0).getNumericCellValue(), sheet.getRow(i).getCell(1).getNumericCellValue());
        }
    }

    public double show(Scatter show) {
        // create a chart...
        JFreeChart chart = ChartFactory.createScatterPlot(
                show.getEq(), // chart title
                "功能點數", // x axis label
                "程式碼行數", // y axis label
                show.createDataset(), // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
                );
        Font font = new Font("標楷體", Font.BOLD, 16);
        chart.getLegend().setItemFont(font); // 圖例
        chart.getTitle().setFont(font); // 標題
        chart.getXYPlot().getDomainAxis().setLabelFont(font); // 左列標題
        chart.getXYPlot().getDomainAxis().setTickLabelFont(font); // 左列
        chart.getXYPlot().getRangeAxis().setLabelFont(font); // 上列標題
        chart.getXYPlot().getRangeAxis().setTickLabelFont(font); // 上列
        // create and display a frame...
        ChartFrame frame = new ChartFrame("散佈圖", chart);
        frame.pack();
        //畫面置中
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);
        return B1;
    }

    public int estimate(int a) {
        int point = 0;
        int line = 0;
        for(int i = 0 ; i < store.length ; i++) {
            point += store[i][0];
            line += store[i][1];
        }
        return (int) (a * line / point);
    }
}
