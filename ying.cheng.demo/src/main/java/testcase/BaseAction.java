package testcase;

import dto.CityDto;


import org.apache.log4j.Logger;
import org.testng.ITest;
import org.testng.annotations.BeforeMethod;
import util.ExcelReaderUtil;
import util.Tabulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class BaseAction implements ITest {

    private static Logger logger = Logger.getLogger(BaseAction.class);
    private CityDto cityDto;
    private String caseName;

    public Iterator<Object[]> readCases(String filename, String sheetName) {
        ExcelReaderUtil excelReaderUtil = new ExcelReaderUtil(filename);
        Tabulation tab = excelReaderUtil.read(sheetName);
        List<Object[]> list = new ArrayList<>();
        for (int i = 0; i < tab.size(); i++) {
            cityDto = new CityDto();
            cityDto.setCityName(tab.getTable().row(i).get("cityName"));
            cityDto.setCaseName(tab.getTable().row(i).get("caseName"));
            cityDto.setAction(tab.getTable().row(i).get("action"));
            list.add(new Object[]{cityDto});
        }
        logger.info("read {} cases from excel" + list.size());
        return list.iterator();
    }

    @BeforeMethod(alwaysRun = true)
    public void testData(Object[] testData) {
        if (testData != null && testData.length > 0) {
            this.caseName = ((CityDto) testData[0]).getCaseName();
        }
    }


    @Override
    public String getTestName() {
        return caseName;
    }
}
