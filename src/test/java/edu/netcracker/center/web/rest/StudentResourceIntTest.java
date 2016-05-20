package edu.netcracker.center.web.rest;

import edu.netcracker.center.Application;
import edu.netcracker.center.domain.LearningType;
import edu.netcracker.center.domain.Student;
import edu.netcracker.center.domain.enumeration.UniversityEnumeration;
import edu.netcracker.center.repository.LearningTypeRepository;
import edu.netcracker.center.repository.StudentRepository;
import edu.netcracker.center.service.StudentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.querydsl.QuerydslPredicateArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StudentResource REST controller.
 *
 * @see StudentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class StudentResourceIntTest {

    private static final String DEFAULT_LAST_NAME = "AAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBB";
    private static final String DEFAULT_FIRST_NAME = "AAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBB";
    private static final String DEFAULT_MIDDLE_NAME = "AAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBB";

    private static final String DEFAULT_EMAIL = "AAAAA@CCCC";
    private static final String UPDATED_EMAIL = "BBBBB@DDDD";
    private static final String DEFAULT_PHONE = "AAAAA";
    private static final String UPDATED_PHONE = "BBBBB";

    private static final UniversityEnumeration DEFAULT_UNIVERSITY = UniversityEnumeration.СГТУ;
    private static final UniversityEnumeration UPDATED_UNIVERSITY = UniversityEnumeration.СГУ;
    private static final String DEFAULT_SPECIALTY = "AAAAA";
    private static final String UPDATED_SPECIALTY = "BBBBB";
    private static final String DEFAULT_FACULTY = "AAAAA";
    private static final String UPDATED_FACULTY = "BBBBB";
    private static final String DEFAULT_COURSE = "AAAAA";
    private static final String UPDATED_COURSE = "BBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = true;
    private static final Boolean UPDATED_IS_ACTIVE = false;

    @Inject
    private StudentRepository studentRepository;

    @Inject
    private LearningTypeRepository learningTypeRepository;

    @Inject
    private StudentService studentService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private QuerydslPredicateArgumentResolver querydslPredicateArgumentResolver;

    private MockMvc restStudentMockMvc;

    private Student student;

    private LearningType learningType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StudentResource studentResource = new StudentResource();
        ReflectionTestUtils.setField(studentResource, "studentService", studentService);
        this.restStudentMockMvc = MockMvcBuilders.standaloneSetup(studentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver, querydslPredicateArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        student = new Student();
        learningType = learningTypeRepository.getOne(1L);
        student.setLastName(DEFAULT_LAST_NAME);
        student.setFirstName(DEFAULT_FIRST_NAME);
        student.setMiddleName(DEFAULT_MIDDLE_NAME);
        student.setEmail(DEFAULT_EMAIL);
        student.setPhone(DEFAULT_PHONE);
        student.setUniversity(DEFAULT_UNIVERSITY);
        student.setSpecialty(DEFAULT_SPECIALTY);
        student.setFaculty(DEFAULT_FACULTY);
        student.setCourse(DEFAULT_COURSE);
        student.setIsActive(DEFAULT_IS_ACTIVE);
        student.setLearningType(learningType);
    }

    @Test
    @Transactional
    public void createStudent() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Create the Student

        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isCreated());

        // Validate the Student in the database
        List<Student> students = studentRepository.findAll();
        assertThat(students).hasSize(databaseSizeBeforeCreate + 1);
        Student testStudent = students.get(students.size() - 1);
        assertThat(testStudent.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testStudent.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testStudent.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testStudent.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testStudent.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testStudent.getUniversity()).isEqualTo(DEFAULT_UNIVERSITY);
        assertThat(testStudent.getSpecialty()).isEqualTo(DEFAULT_SPECIALTY);
        assertThat(testStudent.getFaculty()).isEqualTo(DEFAULT_FACULTY);
        assertThat(testStudent.getCourse()).isEqualTo(DEFAULT_COURSE);
        assertThat(testStudent.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testStudent.getLearningType().getId()).isEqualTo(learningType.getId());
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setLastName(null);

        // Create the Student, which fails.

        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        List<Student> students = studentRepository.findAll();
        assertThat(students).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setEmail(null);

        // Create the Student, which fails.

        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        List<Student> students = studentRepository.findAll();
        assertThat(students).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setIsActive(null);

        // Create the Student, which fails.

        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        List<Student> students = studentRepository.findAll();
        assertThat(students).hasSize(databaseSizeBeforeTest);
    }


    @Test
    @Transactional
    public void checkLearningTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setLearningType(null);

        // Create the Student, which fails.

        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        List<Student> students = studentRepository.findAll();
        assertThat(students).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudents() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the students
        restStudentMockMvc.perform(get("/api/students?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
//            .andExpect(jsonPath("$.[*].university").value(hasItem(DEFAULT_UNIVERSITY.toString())))
            .andExpect(jsonPath("$.[*].specialty").value(hasItem(DEFAULT_SPECIALTY.toString())))
            .andExpect(jsonPath("$.[*].faculty").value(hasItem(DEFAULT_FACULTY.toString())))
            .andExpect(jsonPath("$.[*].course").value(hasItem(DEFAULT_COURSE.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", student.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(student.getId().intValue()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
//            .andExpect(jsonPath("$.university").value(DEFAULT_UNIVERSITY.toString()))
            .andExpect(jsonPath("$.specialty").value(DEFAULT_SPECIALTY.toString()))
            .andExpect(jsonPath("$.faculty").value(DEFAULT_FACULTY.toString()))
            .andExpect(jsonPath("$.course").value(DEFAULT_COURSE.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStudent() throws Exception {
        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student
        student.setLastName(UPDATED_LAST_NAME);
        student.setFirstName(UPDATED_FIRST_NAME);
        student.setMiddleName(UPDATED_MIDDLE_NAME);
        student.setEmail(UPDATED_EMAIL);
        student.setPhone(UPDATED_PHONE);
        student.setUniversity(UPDATED_UNIVERSITY);
        student.setSpecialty(UPDATED_SPECIALTY);
        student.setFaculty(UPDATED_FACULTY);
        student.setCourse(UPDATED_COURSE);
        student.setIsActive(UPDATED_IS_ACTIVE);

        restStudentMockMvc.perform(put("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> students = studentRepository.findAll();
        assertThat(students).hasSize(databaseSizeBeforeUpdate);
        Student testStudent = students.get(students.size() - 1);
        assertThat(testStudent.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testStudent.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testStudent.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testStudent.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testStudent.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testStudent.getUniversity()).isEqualTo(UPDATED_UNIVERSITY);
        assertThat(testStudent.getSpecialty()).isEqualTo(UPDATED_SPECIALTY);
        assertThat(testStudent.getFaculty()).isEqualTo(UPDATED_FACULTY);
        assertThat(testStudent.getCourse()).isEqualTo(UPDATED_COURSE);
        assertThat(testStudent.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void deleteStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeDelete = studentRepository.findAll().size();

        // Get the student
        restStudentMockMvc.perform(delete("/api/students/{id}", student.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Student> students = studentRepository.findAll();
        assertThat(students).hasSize(databaseSizeBeforeDelete - 1);
    }
}
