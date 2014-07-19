package Ver2;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExeclOutput {

    String filePath = "";
    FileInputStream fis;
    POIFSFileSystem fs;
    HSSFWorkbook wb;
    
    public ExeclOutput(String docPath) {
        filePath = docPath;
        if (!new File(filePath).exists()) {
            new ExeclOutput().createFile(filePath);
        }
    }

    ExeclOutput() {
    }

    public void createFile(String docPath) {
        try {
            wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("sheet1");
            filePath = docPath;
           
            HSSFRow row1 = sheet.createRow(0);
            HSSFRow row2 = sheet.createRow(1);
            
            
            //HSSFCell cell = row1.createCell(0);
            //cell.setCellValue("類別名稱");
            HSSFCell cell = row1.createCell(2);          
            cell.setCellValue("循環複雜度");
            cell = row1.createCell(5);
            cell.setCellValue("方法中呼叫的子方法");
            cell = row1.createCell(8);
            cell.setCellValue("方法中呼叫的物件");
            
            cell = row2.createCell(0);
            cell.setCellValue("方法名稱");
            cell = row2.createCell(1);
            cell.setCellValue("物件名");
            cell = row2.createCell(2);
            cell.setCellValue("複雜度");
            cell = row2.createCell(3);
            cell.setCellValue("結果");          
            cell = row2.createCell(5);
            cell.setCellValue("個數");
            cell = row2.createCell(6);
            cell.setCellValue("結果");            
            cell = row2.createCell(8);
            cell.setCellValue("個數");
            cell = row2.createCell(9);
            cell.setCellValue("結果");
                              
            try (FileOutputStream fOut = new FileOutputStream(docPath)) {
                wb.write(fOut);
                fOut.flush();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public void addClassName(String className){
    	try {
            fis = new FileInputStream(filePath);
            fs = new POIFSFileSystem(fis);
            wb = new HSSFWorkbook(fs);
            
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
            
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(className);
            
            
            FileOutputStream fOut = new FileOutputStream(filePath);
            wb.write(fOut);
            fOut.flush();
            // 操作結束，關閉檔
            fOut.close();

        } catch (IOException ex) {
        }
    }

    public void addData(String method,String objName,int cc , String ccResult,Double mt,String mtResult,int oo,String ooResult) {
        try {
            fis = new FileInputStream(filePath);
            fs = new POIFSFileSystem(fis);
            wb = new HSSFWorkbook(fs);

            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
            
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(method);
            cell = row.createCell(1);
            cell.setCellValue(objName);
            cell = row.createCell(2);
            cell.setCellValue(cc);
            cell = row.createCell(3);
            cell.setCellValue(ccResult);
            
            cell = row.createCell(5);
            cell.setCellValue(mt);
            cell = row.createCell(6);
            cell.setCellValue(mtResult);
            
            cell = row.createCell(8);
            cell.setCellValue(oo);
            cell = row.createCell(9);
            cell.setCellValue(ooResult);

            FileOutputStream fOut = new FileOutputStream(filePath);
            wb.write(fOut);
            fOut.flush();
            // 操作結束，關閉檔
            fOut.close();

        } catch (IOException ex) {
        }
    }
}