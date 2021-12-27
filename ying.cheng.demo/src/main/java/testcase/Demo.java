package testcase;

import com.alibaba.fastjson.JSONObject;
import dto.CityDto;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.ConfigUtil;
import util.HttpClientUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Demo extends BaseAction {

    private static String fileName;
    private static String sheetName;
    private static String key;
    private static String url;
    private static String path = "/simpleWeather/query";
    private static HttpClientUtil httpClient;
    private static Map map;
    private static Logger logger = Logger.getLogger(Demo.class);


    static {
        key = "e01f90debe6b8a6ea43f0de3689156a9";
        url = ConfigUtil.getPropery("host") + path;
        httpClient = new HttpClientUtil();
        map = new HashMap();
    }


    @DataProvider(name = "testcase")
    public Iterator<Object[]> citys() {
        this.fileName = ConfigUtil.getPropery("fileName");
        this.sheetName = "city";
        return readCases(fileName, sheetName);
    }


    @Test(dataProvider = "testcase")
    public void runDemo(CityDto cityDto) {
        boolean flag = false;
        if (cityDto.getAction().equals("Y")) {
            map.put("key", key);
            map.put("city", cityDto.getCityName());
            String result = httpClient.doGet(url, map);
            JSONObject jsonObject = JSONObject.parseObject(result);
            logger.info(cityDto.getCityName());
            if (jsonObject.getString("reason").equals("查询成功!")) {
                flag = true;
                logger.info("接口调用正常");
            } else {
                flag = false;
                logger.error("接口调用异常");
            }
            Assert.assertEquals(flag, true, "接口调用异常");
        } else {
            logger.info("此条case无需执行！！！");
        }

    }


}
