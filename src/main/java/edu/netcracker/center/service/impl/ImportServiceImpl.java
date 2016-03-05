package edu.netcracker.center.service.impl;

import edu.netcracker.center.domain.Student;
import edu.netcracker.center.domain.enumeration.TypeEnumeration;
import edu.netcracker.center.repository.StudentRepository;
import edu.netcracker.center.service.StudentXslView;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Service Implementation for importing.
 */
@Service
@Transactional
public class ImportServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(ImportServiceImpl.class);

    StudentRepository studentRepository;

//    HibernateValidator validator;

    void handleImport(MultipartFile file) {
        Workbook workbook;
        try (ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes())) {
            workbook = new HSSFWorkbook(stream);
        } catch (IOException exc) {
            LOG.error("Cant export file of Students", exc);
            throw new IllegalArgumentException("Cant read file");
        }

        for (Row row : workbook.getSheetAt(0)) {
            if (row.getRowNum() != 0) {
                Student student = new Student();
                student.setId(new Double(row.getCell(StudentXslView.ID).getNumericCellValue()).longValue());
                student.setFirstName(row.getCell(StudentXslView.FIRST_NAME).getStringCellValue());
                student.setLastName(row.getCell(StudentXslView.LAST_NAME).getStringCellValue());
                student.setMiddleName(row.getCell(StudentXslView.MIDDLE_NAME).getStringCellValue());
                student.setType(TypeEnumeration.valueOf(row.getCell(StudentXslView.TYPE).getStringCellValue()));
                student.setEmail(row.getCell(StudentXslView.EMAIL).getStringCellValue());
                student.setPhone(row.getCell(StudentXslView.PHONE).getStringCellValue());
                student.setUniversity(row.getCell(StudentXslView.UNIVER).getStringCellValue());
                student.setSpecialty(row.getCell(StudentXslView.SPECIALTY).getStringCellValue());
                student.setCourse(row.getCell(StudentXslView.COURSE).getStringCellValue());
                student.setGroupName(row.getCell(StudentXslView.GROUP).getStringCellValue());

                studentRepository.save(student);
            }
        }
    }
}
