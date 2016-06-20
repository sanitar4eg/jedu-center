package edu.netcracker.center.service;

import edu.netcracker.center.domain.StudentsSet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 *
 */
public class SetXslView extends StudentXslView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        String currentDate = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
        response.setHeader("Content-Type", "application/octet-stream");
        StudentsSet set = (StudentsSet) model.get("set");
        response.setHeader("Content-Disposition", "attachment; filename=" + set.getName() + "_" + currentDate + ".xls");

        generateWorkbook(set.getStudents(), workbook);
    }
}
