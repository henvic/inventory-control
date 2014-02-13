package Repos.File;

import Entities.ProductPrototype;
import Exceptions.UnavailableRepoTypeException;
import Interfaces.ProductPrototypeRepoInterface;
import Repos.Iterator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProductPrototypeRepoFile implements ProductPrototypeRepoInterface {
    private final int ID_CELL_COLUMN = 0;
    private final int PRICE_CELL_COLUMN = 1;
    private final int AMOUNT_CELL_COLUMN = 2;
    private final int NAME_CELL_COLUMN = 3;
    private final int VENDOR_CELL_COLUMN = 4;

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

        try {
            output = new FileOutputStream(handle);
            workbook.write(output);
            output.close();
        } catch (Exception ignore) {
            throw new UnavailableRepoTypeException();
        }
    }

    public ProductPrototypeRepoFile(String path) throws UnavailableRepoTypeException {
        this.path = path;

        handle = new File(path);

        if (!handle.exists()) {
            this.createRepo();
            return;
        }

        this.loadRepo();
    }

    public boolean add(ProductPrototype productPrototype) {
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);

        row.createCell(ID_CELL_COLUMN).setCellValue(productPrototype.getId());
        row.createCell(PRICE_CELL_COLUMN).setCellValue(String.valueOf(productPrototype.getPrice()));
        row.createCell(AMOUNT_CELL_COLUMN).setCellValue(String.valueOf(productPrototype.getAmount()));
        row.createCell(NAME_CELL_COLUMN).setCellValue(productPrototype.getName());
        row.createCell(VENDOR_CELL_COLUMN).setCellValue(productPrototype.getVendor());

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

    public ProductPrototype get(String id) {
        Row row;

        for (int counter = 0, rows = sheet.getPhysicalNumberOfRows(); counter < rows; counter += 1) {
            row = sheet.getRow(counter);

            if (!row.getCell(ID_CELL_COLUMN).getStringCellValue().equalsIgnoreCase(id)) {
                continue;
            }

            return new ProductPrototype(row.getCell(ID_CELL_COLUMN).getStringCellValue(),
                    Integer.parseInt(row.getCell(PRICE_CELL_COLUMN).getStringCellValue()),
                    Integer.parseInt(row.getCell(AMOUNT_CELL_COLUMN).getStringCellValue()),
                    row.getCell(NAME_CELL_COLUMN).getStringCellValue(),
                    row.getCell(VENDOR_CELL_COLUMN).getStringCellValue()
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

    public boolean update(String id, int price, int amount, String name, String vendor) {
        Row row;
        ProductPrototype productPrototype;

        for (int counter = 1, rows = sheet.getPhysicalNumberOfRows(); counter < rows; counter += 1) {
            row = sheet.getRow(counter);

            if (sheet.getRow(counter).getCell(ID_CELL_COLUMN).getStringCellValue().equalsIgnoreCase(id)) {
                productPrototype = this.get(id);

                productPrototype.setAmount(amount);
                productPrototype.setPrice(price);
                productPrototype.setName(name);
                productPrototype.setVendor(vendor);

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
        ProductPrototype[] productPrototypes = new ProductPrototype[rows];
        ProductPrototype productPrototype;

        for (int counter = 0; counter < rows; counter += 1) {
            row = sheet.getRow(counter);

            productPrototype = new ProductPrototype(row.getCell(ID_CELL_COLUMN).getStringCellValue(),
                    Integer.parseInt(row.getCell(PRICE_CELL_COLUMN).getStringCellValue()),
                    Integer.parseInt(row.getCell(AMOUNT_CELL_COLUMN).getStringCellValue()),
                    row.getCell(NAME_CELL_COLUMN).getStringCellValue(),
                    row.getCell(VENDOR_CELL_COLUMN).getStringCellValue()
            );

            productPrototypes[counter] = productPrototype;
        }

        return new Iterator<ProductPrototype>(productPrototypes);
    }
}
