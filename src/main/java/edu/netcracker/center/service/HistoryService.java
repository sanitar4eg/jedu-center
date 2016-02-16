package edu.netcracker.center.service;

import edu.netcracker.center.domain.Student;

import java.util.Date;
import java.util.List;

public interface HistoryService {

    /**
     * get history of students by date.
     *
     * @return the list of entities
     */
    public List<Student> getHistoryOfStudents(Date date);
}
