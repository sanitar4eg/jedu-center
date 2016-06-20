package edu.netcracker.center.service.impl;

import edu.netcracker.center.domain.Student;
import edu.netcracker.center.domain.StudentsSet;
import edu.netcracker.center.domain.enumeration.UniversityEnumeration;
import edu.netcracker.center.domain.util.OperationResult;
import edu.netcracker.center.repository.StudentRepository;
import edu.netcracker.center.service.ImportService;
import edu.netcracker.center.service.StudentXslView;
import edu.netcracker.center.service.StudentsSetService;
import org.apache.commons.lang.StringUtils;
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
import java.util.*;
import java.util.function.Consumer;

/**
 * Service Implementation for importing.
 */
@Service
@Transactional
public class ImportServiceImpl implements ImportService {

    private static final Logger log = LoggerFactory.getLogger(ImportServiceImpl.class);
    public static final boolean STUDENT_IS_ACTIVE = true;

    @Inject
    StudentRepository studentRepository;

    @Inject
    StudentsSetService studentsSetService;

    @Override
    public Collection<OperationResult> handleImportOfStudents(MultipartFile file, Long setId) {
        Workbook workbook;
        try (ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes())) {
            workbook = new HSSFWorkbook(stream);
        } catch (IOException e) {
            log.error("Cant export file of Students", e);
            return Collections.singletonList(new OperationResult("1", "Неудалось прочитать файл", e.getMessage()));
        }
        Optional<StudentsSet> set = Optional.ofNullable(studentsSetService.findOne(setId));
        Collection<OperationResult> results = new LinkedList<>();
        for (Row row : workbook.getSheetAt(0)) {
            Integer rowNum = row.getRowNum();
            if (rowNum != 0) {
                OperationResult result;
                String num = rowNum.toString();
                try {
                    result = getNullableCell(row.getCell(StudentXslView.ID))
                        .map(cell -> {
                            Long id = new Long(cell.getStringCellValue());
                            Student student = studentRepository.findOne(id);
                            if (set.isPresent()) {
                                if (!Objects.equals(student.getStudentsSet(), set.get())) {
                                    return new OperationResult(num, "Студент не принадлежит данному набору",
                                        student.toString());
                                }
                            }
                            student = updateStudent(student, row);
                            return new OperationResult(num, "Студент обнавлен", student.toString());
                        })
                        .orElseGet(() -> {
                            Student student = new Student();
                            student = updateStudent(student, row);
                            student.setIsActive(STUDENT_IS_ACTIVE);
                            return new OperationResult(num, "Студент создан", student.toString());
                        });
                } catch (RuntimeException e) {
                    log.error("Cant save student", e);
                    result = new OperationResult(rowNum.toString(), "Неудалось сохранить студента", e.getMessage());
                }
                results.add(result);
            }
        }
        return results;
    }

    private Student updateStudent(Student student, Row row) {
        setParameter(row, StudentXslView.LAST_NAME, student::setLastName);
        setParameter(row, StudentXslView.FIRST_NAME, student::setFirstName);
        setParameter(row, StudentXslView.MIDDLE_NAME, student::setMiddleName);
        setParameter(row, StudentXslView.EMAIL, student::setEmail);
        setParameter(row, StudentXslView.PHONE, student::setPhone);
        setUniversity(row, StudentXslView.UNIVERSITY, student::setUniversity);
        setParameter(row, StudentXslView.SPECIALTY, student::setSpecialty);
        setParameter(row, StudentXslView.FACULTY, student::setFaculty);
        setParameter(row, StudentXslView.COURSE, student::setCourse);
        setParameter(row, StudentXslView.COMMENT, student::setComment);
        return studentRepository.save(student);
    }


    private void setUniversity(Row row, int field, Consumer<UniversityEnumeration> consumer) {
        getNullableCell(row.getCell(field)).
            ifPresent(cell -> {
                String value = cell.getStringCellValue();
                if (StringUtils.isNotEmpty(value))
                    consumer.accept(UniversityEnumeration.valueOf(value));
            });
    }

    private void setParameter(Row row, int field, Consumer<String> consumer) {
        getNullableCell(row.getCell(field)).
            ifPresent(cell -> {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                consumer.accept(cell.getStringCellValue());
            });
    }

    private Optional<Cell> getNullableCell(Cell value) {
        return Optional.ofNullable(value);
    }
}
