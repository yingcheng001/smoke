package util;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.Data;
import org.testng.log4testng.Logger;

import java.util.ArrayList;


@Data
public class Tabulation {

    private static Logger logger = Logger.getLogger(Tabulation.class);
    private String name;
    //行号-列名-列值
    private Table<Integer, String, String> table;
    private ArrayList<String> columns;


    public Tabulation() {
        this.table = HashBasedTable.create();
        this.columns = new ArrayList<String>();
    }

    //    public void setTable(Table<Integer, String, String> table) {
//        this.table = table;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setColumns(ArrayList<String> columns) {
//        this.columns = columns;
//    }
//
//    public Table<Integer, String, String> getTable() {
//        return this.table;
//    }
//
//    public String getName() {
//        return this.name;
//    }
//
//    public ArrayList<String> getColumns() {
//        return this.columns;
//    }

    public int size() {
        return this.table.rowMap().size();
    }
    public void put(int rowNum, String column, String value) {
        this.table.put(rowNum, column, value);
    }

    public void putRow(ArrayList<String> row) {
        if (this.columns.size() == row.size() && this.columns.size() > 0) {
            int i = 0;
            int index = this.table.rowMap().size();
            for (String col : row) {
                put(index, this.columns.get(i), row.get(i));
                i++;
            }
        } else {
            logger.info("Column's count doesn't equals row's count Or column's count bigger than 0");
        }
    }

}
