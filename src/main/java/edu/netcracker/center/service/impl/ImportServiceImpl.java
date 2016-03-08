package edu.netcracker.center.service.impl;

import edu.netcracker.center.domain.Student;
import edu.netcracker.center.domain.enumeration.TypeEnumeration;
import edu.netcracker.center.repository.StudentRepository;
import edu.netcracker.center.service.ImportService;
import edu.netcracker.center.service.StudentXslView;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Service Implementation for importing.
 */
@Service
@Transactional
public class ImportServiceImpl implements ImportService {

    private static final Logger LOG = LoggerFactory.getLogger(ImportServiceImpl.class);

    @Inject
    StudentRepository studentRepository;

//    HibernateValidator validator;

    @Override
    public void handleImportOfStudents(MultipartFile file) {
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
                setId(row,StudentXslView.ID, student::setId);
                setParameter(row, StudentXslView.FIRST_NAME, student::setFirstName);
                setParameter(row, StudentXslView.LAST_NAME, student::setLastName);
                setParameter(row, StudentXslView.MIDDLE_NAME, student::setMiddleName);
                setType(row, StudentXslView.TYPE, student::setType);
                setParameter(row, StudentXslView.EMAIL, student::setEmail);
                setParameter(row, StudentXslView.PHONE, student::setPhone);
                setParameter(row, StudentXslView.UNIVER, student::setUniversity);
                setParameter(row, StudentXslView.SPECIALTY, student::setSpecialty);
                setParameter(row, StudentXslView.COURSE, student::setCourse);
                setParameter(row, StudentXslView.GROUP, student::setGroupName);

                studentRepository.save(student);
            }
        }
    }

    private void setType(Row row, int field, Consumer<TypeEnumeration> consumer) {
        getNullableCell(row.getCell(field)).
            ifPresent(cell -> consumer.accept(TypeEnumeration.valueOf(cell.getStringCellValue())));
    }

    private void setId(Row row, int field, Consumer<Long> consumer) {
        getNullableCell(row.getCell(field)).
            ifPresent(cell -> consumer.accept(new Long(cell.getStringCellValue())));
    }

    private void setParameter(Row row, int field, Consumer<String> consumer) {
        getNullableCell(row.getCell(field)).
            ifPresent(cell -> consumer.accept(cell.getStringCellValue()));
    }

    private Optional<Cell> getNullableCell(Cell value) {
        return Optional.ofNullable(value);
    }
}
