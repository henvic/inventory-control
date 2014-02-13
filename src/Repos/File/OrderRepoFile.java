package Repos.File;

import Entities.Order;
import Exceptions.UnavailableRepoTypeException;
import Interfaces.OrderRepoInterface;
import Repos.Iterator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class OrderRepoFile implements OrderRepoInterface {
    private final int ID_CELL_COLUMN = 0;
    private final int TIMESTAMP_CELL_COLUMN = 1;
    private final int OPEN_CELL_COLUMN = 2;
    private final int BUYER_CELL_COLUMN = 3;
    private final int SELLER_CELL_COLUMN = 4;
    private final int PRODUCTS_CELL_COLUMN = 4;

    private Workbook workbook;

    private File handle;
    private FileOutputStream output;

    private Sheet sheet;

    private String path;

    private void loadRepo() throws UnavailableRepoTypeException {
        try {
            FileInputStream input;

            input = new FileInputStream(path);
            workbook = new Workbook[] { new HSSFWorkbook(input) }[0];

            sheet = workbook.getSheet("Repo");
        } catch (Exception ignore) {
            throw new UnavailableRepoTypeException();
        }
    }

    private void createRepo() throws UnavailableRepoTypeException {
        Row row;

        workbook = new Workbook[] { new HSSFWorkbook() }[0];

        sheet = workbook.createSheet("Repo");

        row = sheet.createRow(0);

        row.createCell(ID_CELL_COLUMN).setCellValue("ID");
        row.createCell(TIMESTAMP_CELL_COLUMN).setCellValue("Timestamp");
        row.createCell(OPEN_CELL_COLUMN).setCellValue("Open");
        row.createCell(BUYER_CELL_COLUMN).setCellValue("Buyer");
        row.createCell(SELLER_CELL_COLUMN).setCellValue("Seller");
        row.createCell(PRODUCTS_CELL_COLUMN).setCellValue("Products");

        try {
            output = new FileOutputStream(handle);
            workbook.write(output);
            output.close();
        } catch (Exception ignore) {
            throw new UnavailableRepoTypeException();
        }
    }

    public OrderRepoFile(String path) throws UnavailableRepoTypeException {
        this.path = path;

        handle = new File(path);

        if (!handle.exists()) {
            this.createRepo();
            return;
        }

        this.loadRepo();
    }

    private String joinProducts(String[] products) {
        String zipped = "";

        for (String each : products) {
            zipped += each + ",";
        }

        return zipped;
    }

    private ArrayList<String> splitProducts(String serializedProducts) {
        return new ArrayList<String>(Arrays.asList(serializedProducts.split(",")));
    }

    public boolean add(Order order) {
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);

        row.createCell(ID_CELL_COLUMN).setCellValue(order.getId());
        row.createCell(TIMESTAMP_CELL_COLUMN).setCellValue(String.valueOf(order.getTimestamp()));
        row.createCell(OPEN_CELL_COLUMN).setCellValue(order.isOpen());
        row.createCell(BUYER_CELL_COLUMN).setCellValue(order.getBuyer());
        row.createCell(SELLER_CELL_COLUMN).setCellValue(order.getSeller());
        row.createCell(PRODUCTS_CELL_COLUMN).setCellValue(this.joinProducts(order.getProducts()));

        try {
            output = new FileOutputStream(handle);
            workbook.write(output);
            output.close();
            return true;
        } catch (IOException ignore) {
        }

        return false;
    }

    public boolean remove(String id) {
        int pivot = 0;
        int last = sheet.getLastRowNum();

        for (int counter = 0, rows = sheet.getPhysicalNumberOfRows(); counter < rows; counter += 1) {
            if (sheet.getRow(counter).getCell(ID_CELL_COLUMN).getStringCellValue().equalsIgnoreCase(id)) {
                pivot = counter;
            }
        }

        if (pivot >= 0 && pivot < last) {
            sheet.shiftRows(pivot + 1, last, -1);
        }

        if (pivot == last && sheet.getRow(pivot) != null) {
            sheet.removeRow(sheet.getRow(pivot));
        }

        try {
            output = new FileOutputStream(handle);
            workbook.write(output);
            output.close();
            return true;
        } catch (IOException ignore) {
        }

        return false;
    }

    public Order get(String id) {
        Row row;

        for (int counter = 0, rows = sheet.getPhysicalNumberOfRows(); counter < rows; counter += 1) {
            row = sheet.getRow(counter);

            if (!row.getCell(ID_CELL_COLUMN).getStringCellValue().equalsIgnoreCase(id)) {
                continue;
            }

            return new Order(row.getCell(ID_CELL_COLUMN).getStringCellValue(),
                    Integer.parseInt(row.getCell(TIMESTAMP_CELL_COLUMN).getStringCellValue()),
                    row.getCell(OPEN_CELL_COLUMN).getBooleanCellValue(),
                    row.getCell(BUYER_CELL_COLUMN).getStringCellValue(),
                    row.getCell(SELLER_CELL_COLUMN).getStringCellValue(),
                    this.splitProducts(row.getCell(PRODUCTS_CELL_COLUMN).getStringCellValue())
            );
        }

        return null;
    }

    public boolean has(String id) {
        for (int counter = 0, rows = sheet.getPhysicalNumberOfRows(); counter < rows; counter += 1) {
            if (sheet.getRow(counter).getCell(ID_CELL_COLUMN).getStringCellValue().equalsIgnoreCase(id)) {
                return true;
            }
        }

        return false;
    }

    public boolean update(String id, String buyer, String seller) {
        Row row;
        Order order;

        for (int counter = 1, rows = sheet.getPhysicalNumberOfRows(); counter < rows; counter += 1) {
            row = sheet.getRow(counter);

            if (sheet.getRow(counter).getCell(ID_CELL_COLUMN).getStringCellValue().equalsIgnoreCase(id)) {
                order = this.get(id);

                order.setBuyer(buyer);
                order.setSeller(seller);

                row.getCell(BUYER_CELL_COLUMN).setCellValue(buyer);
                row.getCell(SELLER_CELL_COLUMN).setCellValue(seller);

                try {
                    output = new FileOutputStream(handle);
                    workbook.write(output);
                    output.close();
                    return true;
                } catch (IOException ignore) {
                    break;
                }
            }
        }

        return false;
    }

    public void addProduct(String id, String productId) {
        Order order;
        Row row;

        for (int counter = 1, rows = sheet.getPhysicalNumberOfRows(); counter < rows; counter += 1) {
            row = sheet.getRow(counter);

            if (sheet.getRow(counter).getCell(ID_CELL_COLUMN).getStringCellValue().equalsIgnoreCase(id)) {
                order = this.get(id);
                order.addProduct(productId);

                row.getCell(PRODUCTS_CELL_COLUMN).setCellValue(this.joinProducts(order.getProducts()));

                try {
                    output = new FileOutputStream(handle);
                    workbook.write(output);
                    output.close();
                } catch (IOException ignore) {
                    break;
                }
            }
        }
    }

    public Iterator getIterator() {
        int rows = sheet.getPhysicalNumberOfRows();
        Row row;
        Order[] orders = new Order[rows];
        Order order;

        for (int counter = 0; counter < rows; counter += 1) {
            row = sheet.getRow(counter);

            order = new Order(row.getCell(ID_CELL_COLUMN).getStringCellValue(),
                    Integer.parseInt(row.getCell(TIMESTAMP_CELL_COLUMN).getStringCellValue()),
                    row.getCell(OPEN_CELL_COLUMN).getBooleanCellValue(),
                    row.getCell(BUYER_CELL_COLUMN).getStringCellValue(),
                    row.getCell(SELLER_CELL_COLUMN).getStringCellValue(),
                    this.splitProducts(row.getCell(PRODUCTS_CELL_COLUMN).getStringCellValue())
            );

            orders[counter] = order;
        }

        return new Iterator<Order>(orders);
    }
}
