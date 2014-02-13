package Repos.File;

import Entities.Actor;
import Exceptions.UnavailableRepoTypeException;
import Interfaces.ActorRepoInterface;
import Repos.Iterator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ActorRepoFile implements ActorRepoInterface {
    private final int ID_CELL_COLUMN = 0;
    private final int NAME_CELL_COLUMN = 1;
    private final int COMPANY_CELL_COLUMN = 2;
    private final int EMAIL_CELL_COLUMN = 3;
    private final int PHONE_CELL_COLUMN = 4;
    private final int ADDRESS_CELL_COLUMN = 5;

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
        row.createCell(NAME_CELL_COLUMN).setCellValue("Name");
        row.createCell(COMPANY_CELL_COLUMN).setCellValue("Company");
        row.createCell(EMAIL_CELL_COLUMN).setCellValue("Email");
        row.createCell(PHONE_CELL_COLUMN).setCellValue("Phone");
        row.createCell(ADDRESS_CELL_COLUMN).setCellValue("Address");

        try {
            output = new FileOutputStream(handle);
            workbook.write(output);
            output.close();
        } catch (Exception ignore) {
            throw new UnavailableRepoTypeException();
        }
    }

    public ActorRepoFile(String path) throws UnavailableRepoTypeException {
        this.path = path;

        handle = new File(path);

        if (!handle.exists()) {
            this.createRepo();
            return;
        }

        this.loadRepo();
    }

    public boolean add(Actor actor) {
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);

        row.createCell(ID_CELL_COLUMN).setCellValue(actor.getId());
        row.createCell(NAME_CELL_COLUMN).setCellValue(actor.getName());
        row.createCell(COMPANY_CELL_COLUMN).setCellValue(actor.getCompany());
        row.createCell(EMAIL_CELL_COLUMN).setCellValue(actor.getEmail());
        row.createCell(PHONE_CELL_COLUMN).setCellValue(actor.getPhone());
        row.createCell(ADDRESS_CELL_COLUMN).setCellValue(actor.getAddress());

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

    public Actor get(String id) {
        Row row;

        for (int counter = 0, rows = sheet.getPhysicalNumberOfRows(); counter < rows; counter += 1) {
            row = sheet.getRow(counter);

            if (!row.getCell(ID_CELL_COLUMN).getStringCellValue().equalsIgnoreCase(id)) {
                continue;
            }

            return new Actor(row.getCell(ID_CELL_COLUMN).getStringCellValue(),
                    row.getCell(NAME_CELL_COLUMN).getStringCellValue(),
                    row.getCell(COMPANY_CELL_COLUMN).getStringCellValue(),
                    row.getCell(EMAIL_CELL_COLUMN).getStringCellValue(),
                    row.getCell(PHONE_CELL_COLUMN).getStringCellValue(),
                    row.getCell(ADDRESS_CELL_COLUMN).getStringCellValue()
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

    public boolean update(String id, String name, String company, String email, String phone, String address) {
        Row row;
        Actor actor;

        for (int counter = 1, rows = sheet.getPhysicalNumberOfRows(); counter < rows; counter += 1) {
            row = sheet.getRow(counter);

            if (sheet.getRow(counter).getCell(ID_CELL_COLUMN).getStringCellValue().equalsIgnoreCase(id)) {
                actor = this.get(id);

                actor.setName(name);
                actor.setCompany(company);
                actor.setEmail(email);
                actor.setPhone(phone);
                actor.setAddress(address);

                row.getCell(NAME_CELL_COLUMN).setCellValue(name);
                row.getCell(COMPANY_CELL_COLUMN).setCellValue(company);
                row.getCell(EMAIL_CELL_COLUMN).setCellValue(email);
                row.getCell(PHONE_CELL_COLUMN).setCellValue(phone);
                row.getCell(ADDRESS_CELL_COLUMN).setCellValue(address);

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
        Actor[] actors = new Actor[rows];
        Actor actor;

        for (int counter = 0; counter < rows; counter += 1) {
            row = sheet.getRow(counter);

            actor = new Actor(row.getCell(ID_CELL_COLUMN).getStringCellValue(),
                    row.getCell(NAME_CELL_COLUMN).getStringCellValue(),
                    row.getCell(COMPANY_CELL_COLUMN).getStringCellValue(),
                    row.getCell(EMAIL_CELL_COLUMN).getStringCellValue(),
                    row.getCell(PHONE_CELL_COLUMN).getStringCellValue(),
                    row.getCell(ADDRESS_CELL_COLUMN).getStringCellValue()
            );

            actors[counter] = actor;
        }

        return new Iterator<Actor>(actors);
    }
}
