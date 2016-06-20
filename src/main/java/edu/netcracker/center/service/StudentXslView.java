package edu.netcracker.center.service;

import edu.netcracker.center.domain.Student;
import org.apache.commons.lang.ObjectUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class StudentXslView extends AbstractXlsView {

    public static final int ID = 0;
    public static final int LAST_NAME = 1;
    public static final int FIRST_NAME = 2;
    public static final int MIDDLE_NAME = 3;
    public static final int EMAIL = 4;
    public static final int PHONE = 5;
    public static final int TYPE = 6;
    public static final int UNIVERSITY = 7;
    public static final int SPECIALTY = 8;
    public static final int FACULTY = 9;
    public static final int COURSE = 10;
    public static final int CURATOR = 11;
    public static final int COMMENT = 12;
    public static final int GROUP = 13;
    public static final int SET = 14;

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        String currentDate = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=Students_" + currentDate + ".xls");

        Set<Student> students = (Set<Student>) model.get("students");

        generateWorkbook(students, workbook);
    }

    protected void generateWorkbook(Set<Student> students, Workbook workbook) {
        Sheet sheet = createSheet(workbook);

        CellStyle headerStyle = createHeaderStyle(workbook);

        createHeader(sheet, headerStyle);

        completeTable(students, sheet);
    }

    private void completeTable(Set<Student> students, Sheet sheet) {
        int counter = 1;
        for (Student student : students) {
            Row row = sheet.createRow(counter++);
            setCellValueAndStyle(row.createCell(ID), student.getId().toString(), null);
            setCellValueAndStyle(row.createCell(LAST_NAME), student.getLastName(), null);
            setCellValueAndStyle(row.createCell(FIRST_NAME), student.getFirstName(), null);
            setCellValueAndStyle(row.createCell(MIDDLE_NAME), student.getMiddleName(), null);
            setCellValueAndStyle(row.createCell(EMAIL), student.getEmail(), null);
            setCellValueAndStyle(row.createCell(PHONE), student.getPhone(), null);
            setCellValueAndStyle(row.createCell(TYPE), student.getLearningType().getName(), null);
            setCellValueAndStyle(row.createCell(UNIVERSITY), ObjectUtils.toString(student.getUniversity()), null);
            setCellValueAndStyle(row.createCell(SPECIALTY), student.getSpecialty(), null);
            setCellValueAndStyle(row.createCell(FACULTY), student.getFaculty(), null);
            setCellValueAndStyle(row.createCell(COURSE), student.getCourse(), null);
            setCellValueAndStyle(row.createCell(CURATOR), student.getCurator().getLastName(), null);
            setCellValueAndStyle(row.createCell(COMMENT), student.getComment(), null);
            setCellValueAndStyle(row.createCell(GROUP), student.getGroupOfStudent().getName(), null);
            setCellValueAndStyle(row.createCell(SET), student.getStudentsSet().getName(), null);
        }
    }

    private void createHeader(Sheet sheet, CellStyle style) {
        Row header = sheet.createRow(0);

        setCellValueAndStyle(header.createCell(ID), "ID", style);
        setCellValueAndStyle(header.createCell(LAST_NAME), "Фамилия", style);
        setCellValueAndStyle(header.createCell(FIRST_NAME), "Имя", style);
        setCellValueAndStyle(header.createCell(MIDDLE_NAME), "Отчество", style);
        setCellValueAndStyle(header.createCell(EMAIL), "Email", style);
        setCellValueAndStyle(header.createCell(PHONE), "Телефон", style);
        setCellValueAndStyle(header.createCell(TYPE), "Вид обучения", style);
        setCellValueAndStyle(header.createCell(UNIVERSITY), "Университет", style);
        setCellValueAndStyle(header.createCell(SPECIALTY), "Специальность", style);
        setCellValueAndStyle(header.createCell(FACULTY), "Факультет", style);
        setCellValueAndStyle(header.createCell(COURSE), "Курс", style);
        setCellValueAndStyle(header.createCell(CURATOR), "Куратор", style);
        setCellValueAndStyle(header.createCell(COMMENT), "Комментарий", style);
        setCellValueAndStyle(header.createCell(GROUP), "Группа", style);
        setCellValueAndStyle(header.createCell(SET), "Набор", style);
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(font);
        return style;
    }

    private Sheet createSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("Список студентов");
        sheet.setDefaultColumnWidth(15);
        return sheet;
    }

    private void setCellValueAndStyle(Cell cell, String value, CellStyle style) {
        cell.setCellValue(value);
        cell.setCellStyle(style);
        cell.setCellType(Cell.CELL_TYPE_STRING);
    }
}
