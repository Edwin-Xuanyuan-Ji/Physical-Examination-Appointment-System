import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class POITest {
    //使用POI读取Excel中的数据
    @Test
    public void test() throws Exception{
        //加载Excel文件
        XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File("D:\\健康项目\\资料\\day05\\poitest.xlsx")));
        //读取Excel中第一个sheet
        XSSFSheet sheet = excel.getSheetAt(0);
        //遍历每一行
        for(Row row:sheet){
            //遍历每一行的单元格
            for(Cell cell:row){
                System.out.println(cell);
            }
        }
        //释放资源
        excel.close();
    }

    //使用POI读取Excel中的数据
    @Test
    public void test2() throws Exception{
        //加载Excel文件
        XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File("D:\\健康项目\\资料\\day05\\poitest.xlsx")));
        //读取Excel中第一个sheet
        XSSFSheet sheet = excel.getSheetAt(0);
        //遍历每一行
        int lastRowNum = sheet.getLastRowNum(); //lastRowNum从0开始数行号
        for(int i=0;i<=lastRowNum;i++){
            XSSFRow row = sheet.getRow(i);
            short lastCellNum = row.getLastCellNum(); //lastCellNum从1开始数单元格
            for (int j=0;j<lastCellNum;j++){
                XSSFCell cell = row.getCell(j);
                System.out.println(cell.getStringCellValue());
            }
        }
        //释放资源
        excel.close();
    }

    //使用POI写Excel中的数据
    @Test
    public void test3() throws Exception{
        //创建Excel文件
        XSSFWorkbook excel = new XSSFWorkbook();
        //创建Excel中第一个sheet
        XSSFSheet sheet = excel.createSheet();

        //创建sheet中的行
        XSSFRow title = sheet.createRow(0);
        title.createCell(0).setCellValue("姓名");
        title.createCell(1).setCellValue("住址");
        title.createCell(2).setCellValue("年龄");

        //创建sheet中的行
        XSSFRow row = sheet.createRow(1);
        row.createCell(0).setCellValue("zhangsan");
        row.createCell(1).setCellValue("beijing");
        row.createCell(2).setCellValue("23");

        //创建输出流
        FileOutputStream out = new FileOutputStream(new File("D:\\健康项目\\资料\\day05\\poiwrite.xlsx"));
        excel.write(out);
        out.flush();

        excel.close();
    }
}
