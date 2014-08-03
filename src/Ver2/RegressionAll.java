import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author SmallFour
 */
public class RegressionAll {

    String filePath = "";
    FileInputStream fis;
    POIFSFileSystem fs;
    HSSFWorkbook wb;
    public RegressionAll(String docPath) {
        filePath = docPath;
        if (!new File(filePath).exists()) {
            new RegressionAll().createFile(filePath);
        }
    }

     RegressionAll() {
    }

    public void createFile(String docPath) {
        try {
             wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("sheet1");
            filePath = docPath;

            HSSFRow row1 = sheet.createRow(0);
            HSSFRow row2 = sheet.createRow(1);
            HSSFRow row3 = sheet.createRow(2);
            HSSFRow row4 = sheet.createRow(3);
            HSSFRow row6 = sheet.createRow(5);
            HSSFRow row7 = sheet.createRow(6);
            HSSFRow row8 = sheet.createRow(7);

            HSSFCell cell = row1.createCell(0);
            cell.setCellValue("相關係數");
            cell = row2.createCell(0);
            cell.setCellValue("迴歸方程式：y = bx + B");
            cell = row3.createCell(0);
            cell.setCellValue("斜率(b)");
            cell = row4.createCell(0);
            cell.setCellValue("截距(B)");
            cell = row6.createCell(0);
            cell.setCellValue("功能點數(x)");
            cell = row6.createCell(1);
            cell.setCellValue("行數(y)");
            
            cell = row7.createCell(2);          
            cell.setCellValue("功能點數");
            cell = row7.createCell(3);
            cell.setCellValue("循環複雜度");
            cell = row7.createCell(6);
            cell.setCellValue("方法中呼叫的子方法");
            cell = row7.createCell(9);
            cell.setCellValue("方法中呼叫的物件");
            
            cell = row8.createCell(0);
            cell.setCellValue("方法名稱");
            cell = row8.createCell(1);
            cell.setCellValue("物件名");
            cell = row8.createCell(2);
            cell.setCellValue("個數");
            cell = row8.createCell(3);
            cell.setCellValue("複雜度");
            cell = row8.createCell(4);
            cell.setCellValue("結果");
            cell = row8.createCell(6);
            cell.setCellValue("個數");
            cell = row8.createCell(7);
            cell.setCellValue("結果");            
            cell = row8.createCell(9);
            cell.setCellValue("個數");
            cell = row8.createCell(10);
            cell.setCellValue("結果");
            
            
            try (FileOutputStream fOut = new FileOutputStream(docPath)) {
                wb.write(fOut);
                fOut.flush();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void addData(String method,String objName,double cfp,int cc , String ccResult,Double mt,String mtResult,int oo,String ooResult) {
        try {
        	fis = new FileInputStream(filePath);
            fs = new POIFSFileSystem(fis);
            wb = new HSSFWorkbook(fs);

            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);

           /* HSSFCell cell = row.createCell(0);
            cell.setCellValue(a);
            cell = row.createCell(1);
            cell.setCellValue(b);*/
            
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(method);
            cell = row.createCell(1);
            cell.setCellValue(objName);
            cell = row.createCell(2);
            cell.setCellValue(cfp);
            cell = row.createCell(3);
            cell.setCellValue(cc);
            cell = row.createCell(4);
            cell.setCellValue(ccResult);
            
            cell = row.createCell(6);
            cell.setCellValue(mt);
            cell = row.createCell(7);
            cell.setCellValue(mtResult);
            
            cell = row.createCell(9);
            cell.setCellValue(oo);
            cell = row.createCell(10);
            cell.setCellValue(ooResult);

            FileOutputStream fOut = new FileOutputStream(filePath);
            wb.write(fOut);
            fOut.flush();
            // 操作結束，關閉檔
            fOut.close();

            /*HSSFRow row0 = sheet.getRow(0);
            HSSFRow row1 = sheet.getRow(1);
            HSSFRow row2 = sheet.getRow(2);
            HSSFRow row3 = sheet.getRow(3);

            row0.createCell(0).setCellFormula("\"相關係數 = \"&CORREL(A$7:A$65536, B$7:B$65536)");
            row1.createCell(0).setCellFormula("\"迴歸方程式：y = bx + B = \"&SLOPE(B$7:B$65536, A$7:A$65536)&\" x + \"&INTERCEPT(B$7:B$65536,A$7:A$65536)");
            row2.createCell(0).setCellFormula("\"斜率(b) = \"&SLOPE(B$7:B$65536, A$7:A$65536)");
            row3.createCell(0).setCellFormula("\"截距(B) = \"&INTERCEPT(B$7:B$65536,A$7:A$65536)");*/

            // 新建一輸出檔案流
           // fOut = new FileOutputStream(filePath);
            //wb.write(fOut);
            //fOut.flush();
            // 操作結束，關閉檔
            //fOut.close();
        } catch (IOException ex) {
        }
    }
}
