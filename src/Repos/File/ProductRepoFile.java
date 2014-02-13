package Repos.File;

import Entities.Product;
import Exceptions.UnavailableRepoTypeException;
import Interfaces.ProductRepoInterface;
import Repos.Iterator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProductRepoFile implements ProductRepoInterface {
    private final int ID_CELL_COLUMN = 0;
    private final int PRICE_CELL_COLUMN = 1;
    private final int AMOUNT_CELL_COLUMN = 2;
    private final int NAME_CELL_COLUMN = 3;
    private final int VENDOR_CELL_COLUMN = 4;
    private final int PROTOTYPE_CELL_COLUMN = 5;

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
        row.createCell(PRICE_CELL_COLUMN).setCellValue("Price");
        row.createCell(AMOUNT_CELL_COLUMN).setCellValue("Amount");
        row.createCell(NAME_CELL_COLUMN).setCellValue("Name");
        row.createCell(VENDOR_CELL_COLUMN).setCellValue("Vendor");
        row.createCell(PROTOTYPE_CELL_COLUMN).setCellValue("Prototype");

        try {
            output = new FileOutputStream(handle);
            workbook.write(output);
            output.close();
        } catch (Exception ignore) {
            throw new UnavailableRepoTypeException();
        }
    }

    public ProductRepoFile(String path) throws UnavailableRepoTypeException {
        this.path = path;

        handle = new File(path);

        if (!handle.exists()) {
            this.createRepo();
            return;
        }

        this.loadRepo();
    }

    public boolean add(Product product) {
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);

        row.createCell(ID_CELL_COLUMN).setCellValue(product.getId());
        row.createCell(PRICE_CELL_COLUMN).setCellValue(String.valueOf(product.getPrice()));
        row.createCell(AMOUNT_CELL_COLUMN).setCellValue(String.valueOf(product.getAmount()));
        row.createCell(NAME_CELL_COLUMN).setCellValue(product.getName());
        row.createCell(VENDOR_CELL_COLUMN).setCellValue(product.getVendor());
        row.createCell(PROTOTYPE_CELL_COLUMN).setCellValue(product.getPrototype());

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

    public Product get(String id) {
        Row row;

        for (int counter = 0, rows = sheet.getPhysicalNumberOfRows(); counter < rows; counter += 1) {
            row = sheet.getRow(counter);

            if (!row.getCell(ID_CELL_COLUMN).getStringCellValue().equalsIgnoreCase(id)) {
                continue;
            }

            return new Product(row.getCell(ID_CELL_COLUMN).getStringCellValue(),
                    Integer.parseInt(row.getCell(PRICE_CELL_COLUMN).getStringCellValue()),
                    row.getCell(NAME_CELL_COLUMN).getStringCellValue(),
                    row.getCell(VENDOR_CELL_COLUMN).getStringCellValue(),
                    row.getCell(PROTOTYPE_CELL_COLUMN).getStringCellValue(),
                    Integer.parseInt(row.getCell(AMOUNT_CELL_COLUMN).getStringCellValue())
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

    public boolean update(String id, int price, String name, String vendor, int amount) {
        Row row;
        Product product;

        for (int counter = 1, rows = sheet.getPhysicalNumberOfRows(); counter < rows; counter += 1) {
            row = sheet.getRow(counter);

            if (sheet.getRow(counter).getCell(ID_CELL_COLUMN).getStringCellValue().equalsIgnoreCase(id)) {
                product = this.get(id);

                product.setAmount(amount);
                product.setPrice(price);
                product.setName(name);
                product.setVendor(vendor);

                row.getCell(AMOUNT_CELL_COLUMN).setCellValue(String.valueOf(amount));
                row.getCell(PRICE_CELL_COLUMN).setCellValue(String.valueOf(price));
                row.getCell(NAME_CELL_COLUMN).setCellValue(name);
                row.getCell(VENDOR_CELL_COLUMN).setCellValue(vendor);

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

    public Iterator getIterator() {
        int rows = sheet.getPhysicalNumberOfRows();
        Row row;
        Product[] products = new Product[rows];
        Product product;

        for (int counter = 0; counter < rows; counter += 1) {
            row = sheet.getRow(counter);

            product = new Product(row.getCell(ID_CELL_COLUMN).getStringCellValue(),
                    Integer.parseInt(row.getCell(PRICE_CELL_COLUMN).getStringCellValue()),
                    row.getCell(NAME_CELL_COLUMN).getStringCellValue(),
                    row.getCell(VENDOR_CELL_COLUMN).getStringCellValue(),
                    row.getCell(PROTOTYPE_CELL_COLUMN).getStringCellValue(),
                    Integer.parseInt(row.getCell(AMOUNT_CELL_COLUMN).getStringCellValue())
            );

            products[counter] = product;
        }

        return new Iterator<Product>(products);
    }
}
