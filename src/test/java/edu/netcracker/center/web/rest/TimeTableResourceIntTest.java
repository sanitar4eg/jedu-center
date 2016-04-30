package edu.netcracker.center.web.rest;

import edu.netcracker.center.Application;
import edu.netcracker.center.domain.TimeTable;
import edu.netcracker.center.repository.TimeTableRepository;
import edu.netcracker.center.service.TimeTableService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
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
 * Test class for the TimeTableResource REST controller.
 *
 * @see TimeTableResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TimeTableResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private TimeTableRepository timeTableRepository;

    @Inject
    private TimeTableService timeTableService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTimeTableMockMvc;

    private TimeTable timeTable;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TimeTableResource timeTableResource = new TimeTableResource();
        ReflectionTestUtils.setField(timeTableResource, "timeTableService", timeTableService);
        this.restTimeTableMockMvc = MockMvcBuilders.standaloneSetup(timeTableResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        timeTable = new TimeTable();
        timeTable.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTimeTable() throws Exception {
        int databaseSizeBeforeCreate = timeTableRepository.findAll().size();

        // Create the TimeTable

        restTimeTableMockMvc.perform(post("/api/timeTables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timeTable)))
                .andExpect(status().isCreated());

        // Validate the TimeTable in the database
        List<TimeTable> timeTables = timeTableRepository.findAll();
        assertThat(timeTables).hasSize(databaseSizeBeforeCreate + 1);
        TimeTable testTimeTable = timeTables.get(timeTables.size() - 1);
        assertThat(testTimeTable.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeTableRepository.findAll().size();
        // set the field null
        timeTable.setName(null);

        // Create the TimeTable, which fails.

        restTimeTableMockMvc.perform(post("/api/timeTables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timeTable)))
                .andExpect(status().isBadRequest());

        List<TimeTable> timeTables = timeTableRepository.findAll();
        assertThat(timeTables).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTimeTables() throws Exception {
        // Initialize the database
        timeTableRepository.saveAndFlush(timeTable);

        // Get all the timeTables
        restTimeTableMockMvc.perform(get("/api/timeTables?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(timeTable.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTimeTable() throws Exception {
        // Initialize the database
        timeTableRepository.saveAndFlush(timeTable);

        // Get the timeTable
        restTimeTableMockMvc.perform(get("/api/timeTables/{id}", timeTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(timeTable.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTimeTable() throws Exception {
        // Get the timeTable
        restTimeTableMockMvc.perform(get("/api/timeTables/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeTable() throws Exception {
        // Initialize the database
        timeTableRepository.saveAndFlush(timeTable);

		int databaseSizeBeforeUpdate = timeTableRepository.findAll().size();

        // Update the timeTable
        timeTable.setName(UPDATED_NAME);

        restTimeTableMockMvc.perform(put("/api/timeTables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timeTable)))
                .andExpect(status().isOk());

        // Validate the TimeTable in the database
        List<TimeTable> timeTables = timeTableRepository.findAll();
        assertThat(timeTables).hasSize(databaseSizeBeforeUpdate);
        TimeTable testTimeTable = timeTables.get(timeTables.size() - 1);
        assertThat(testTimeTable.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteTimeTable() throws Exception {
        // Initialize the database
        timeTableRepository.saveAndFlush(timeTable);

		int databaseSizeBeforeDelete = timeTableRepository.findAll().size();

        // Get the timeTable
        restTimeTableMockMvc.perform(delete("/api/timeTables/{id}", timeTable.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TimeTable> timeTables = timeTableRepository.findAll();
        assertThat(timeTables).hasSize(databaseSizeBeforeDelete - 1);
    }
}
