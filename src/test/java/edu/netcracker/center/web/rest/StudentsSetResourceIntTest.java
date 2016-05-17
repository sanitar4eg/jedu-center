package edu.netcracker.center.web.rest;

import edu.netcracker.center.Application;
import edu.netcracker.center.domain.StudentsSet;
import edu.netcracker.center.repository.StudentsSetRepository;
import edu.netcracker.center.service.StudentsSetService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.querydsl.QuerydslPredicateArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the StudentsSetResource REST controller.
 *
 * @see StudentsSetResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class StudentsSetResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Inject
    private StudentsSetRepository studentsSetRepository;

    @Inject
    private StudentsSetService studentsSetService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private QuerydslPredicateArgumentResolver querydslPredicateArgumentResolver;

    private MockMvc restStudentsSetMockMvc;

    private StudentsSet studentsSet;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StudentsSetResource studentsSetResource = new StudentsSetResource();
        ReflectionTestUtils.setField(studentsSetResource, "studentsSetService", studentsSetService);
        this.restStudentsSetMockMvc = MockMvcBuilders.standaloneSetup(studentsSetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver, querydslPredicateArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        studentsSet = new StudentsSet();
        studentsSet.setName(DEFAULT_NAME);
        studentsSet.setDescription(DEFAULT_DESCRIPTION);
        studentsSet.setIsActive(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createStudentsSet() throws Exception {
        int databaseSizeBeforeCreate = studentsSetRepository.findAll().size();

        // Create the StudentsSet

        restStudentsSetMockMvc.perform(post("/api/studentsSets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(studentsSet)))
                .andExpect(status().isCreated());

        // Validate the StudentsSet in the database
        List<StudentsSet> studentsSets = studentsSetRepository.findAll();
        assertThat(studentsSets).hasSize(databaseSizeBeforeCreate + 1);
        StudentsSet testStudentsSet = studentsSets.get(studentsSets.size() - 1);
        assertThat(testStudentsSet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStudentsSet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStudentsSet.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentsSetRepository.findAll().size();
        // set the field null
        studentsSet.setName(null);

        // Create the StudentsSet, which fails.

        restStudentsSetMockMvc.perform(post("/api/studentsSets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(studentsSet)))
                .andExpect(status().isBadRequest());

        List<StudentsSet> studentsSets = studentsSetRepository.findAll();
        assertThat(studentsSets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentsSetRepository.findAll().size();
        // set the field null
        studentsSet.setIsActive(null);

        // Create the StudentsSet, which fails.

        restStudentsSetMockMvc.perform(post("/api/studentsSets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(studentsSet)))
                .andExpect(status().isBadRequest());

        List<StudentsSet> studentsSets = studentsSetRepository.findAll();
        assertThat(studentsSets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudentsSets() throws Exception {
        // Initialize the database
        studentsSetRepository.saveAndFlush(studentsSet);

        // Get all the studentsSets
        restStudentsSetMockMvc.perform(get("/api/studentsSets?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(studentsSet.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getStudentsSet() throws Exception {
        // Initialize the database
        studentsSetRepository.saveAndFlush(studentsSet);

        // Get the studentsSet
        restStudentsSetMockMvc.perform(get("/api/studentsSets/{id}", studentsSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(studentsSet.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentsSet() throws Exception {
        // Get the studentsSet
        restStudentsSetMockMvc.perform(get("/api/studentsSets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentsSet() throws Exception {
        // Initialize the database
        studentsSetRepository.saveAndFlush(studentsSet);

		int databaseSizeBeforeUpdate = studentsSetRepository.findAll().size();

        // Update the studentsSet
        studentsSet.setName(UPDATED_NAME);
        studentsSet.setDescription(UPDATED_DESCRIPTION);
        studentsSet.setIsActive(UPDATED_IS_ACTIVE);

        restStudentsSetMockMvc.perform(put("/api/studentsSets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(studentsSet)))
                .andExpect(status().isOk());

        // Validate the StudentsSet in the database
        List<StudentsSet> studentsSets = studentsSetRepository.findAll();
        assertThat(studentsSets).hasSize(databaseSizeBeforeUpdate);
        StudentsSet testStudentsSet = studentsSets.get(studentsSets.size() - 1);
        assertThat(testStudentsSet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStudentsSet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStudentsSet.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void deleteStudentsSet() throws Exception {
        // Initialize the database
        studentsSetRepository.saveAndFlush(studentsSet);

		int databaseSizeBeforeDelete = studentsSetRepository.findAll().size();

        // Get the studentsSet
        restStudentsSetMockMvc.perform(delete("/api/studentsSets/{id}", studentsSet.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentsSet> studentsSets = studentsSetRepository.findAll();
        assertThat(studentsSets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
