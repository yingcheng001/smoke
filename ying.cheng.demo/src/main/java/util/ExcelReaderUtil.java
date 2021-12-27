package util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ExcelReaderUtil {
    private Workbook workbook;
    private ArrayList<Sheet> sheets;
    private ArrayList<String> sheetNames;
    private String filename;
    private String excelName;

    private static Logger logger=Logger.getLogger(ExcelReaderUtil.class);

    public ExcelReaderUtil(String filename) {
        if (StringUtils.isBlank(filename)) {
            logger.info("File not found !!!");
            return;
        }
        this.filename = filename;
        String ext = filename.substring(filename.lastIndexOf("."));
        this.excelName = filename.substring(filename.lastIndexOf("/") + 1, filename.lastIndexOf("."));
        try {
            InputStream is = new FileInputStream(filename);
            if (".xls".equals(ext)) {
                this.workbook = new HSSFWorkbook(is);
            } else if (".xlsx".equals(ext)) {
                this.workbook = new XSSFWorkbook(is);
            } else {
                this.workbook = null;
            }
            this.readSheets();
            is.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
//            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e);
//            e.printStackTrace();
        }
    }

    public ArrayList<Sheet> getSheets() {
        return this.sheets;
    }

    private void readSheets() {
        this.sheetNames = new ArrayList<String>();
        this.sheets = new ArrayList<Sheet>();
        for (int i = 0; i < this.workbook.getNumberOfSheets(); i++) {
            this.sheetNames.add(this.workbook.getSheetName(i));
            this.sheets.add(this.workbook.getSheetAt(i));
        }
    }

    public Tabulation read(String sheetName) {
        Tabulation table = new Tabulation();
        table.setName(this.excelName + "_" + sheetName);
        Sheet sheet = this.workbook.getSheet(sheetName);
        int rowNum = sheet.getLastRowNum();
        //Row row = sheet.getRow(0);
        int colNum = sheet.getRow(0).getPhysicalNumberOfCells();
        table.setColumns(this.readRow(sheet.getRow(0), colNum));
        try {
            for (int i = 1; i <= rowNum; i++) {
                table.putRow(this.readRow(sheet.getRow(i), colNum));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return table;
    }


    private ArrayList<String> readRow(Row row, int colNum) {
        ArrayList<String> cells = new ArrayList<String>();
        //int colNum=row.getPhysicalNumberOfCells();
        for (int i = 0; i < colNum; i++) {
            if (row.getCell(i) == null)
                cells.add("");
            else
                cells.add(row.getCell(i).toString());
        }
        return cells;
    }

}
