package edu.netcracker.center.web.rest;

import edu.netcracker.center.Application;
import edu.netcracker.center.domain.LearningResult;
import edu.netcracker.center.domain.enumeration.TypeOfResult;
import edu.netcracker.center.repository.LearningResultRepository;
import edu.netcracker.center.service.LearningResultService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
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
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LearningResultResource REST controller.
 *
 * @see LearningResultResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LearningResultResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final TypeOfResult DEFAULT_TYPE = TypeOfResult.Dismissed;
    private static final TypeOfResult UPDATED_TYPE = TypeOfResult.GotJob;
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATION_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATION_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATION_TIME_STR = dateTimeFormatter.format(DEFAULT_CREATION_TIME);

    @Inject
    private LearningResultRepository learningResultRepository;

    @Inject
    private LearningResultService learningResultService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLearningResultMockMvc;

    private LearningResult learningResult;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LearningResultResource learningResultResource = new LearningResultResource();
        ReflectionTestUtils.setField(learningResultResource, "learningResultService", learningResultService);
        this.restLearningResultMockMvc = MockMvcBuilders.standaloneSetup(learningResultResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        learningResult = new LearningResult();
        learningResult.setType(DEFAULT_TYPE);
        learningResult.setDescription(DEFAULT_DESCRIPTION);
        learningResult.setCreationTime(DEFAULT_CREATION_TIME);
    }

    @Test
    @Transactional
    public void createLearningResult() throws Exception {
        int databaseSizeBeforeCreate = learningResultRepository.findAll().size();

        // Create the LearningResult

        restLearningResultMockMvc.perform(post("/api/learningResults")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(learningResult)))
            .andExpect(status().isCreated());

        // Validate the LearningResult in the database
        List<LearningResult> learningResults = learningResultRepository.findAll();
        assertThat(learningResults).hasSize(databaseSizeBeforeCreate + 1);
        LearningResult testLearningResult = learningResults.get(learningResults.size() - 1);
        assertThat(testLearningResult.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testLearningResult.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLearningResult.getCreationTime()).isEqualTo(DEFAULT_CREATION_TIME);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = learningResultRepository.findAll().size();
        // set the field null
        learningResult.setType(null);

        // Create the LearningResult, which fails.

        restLearningResultMockMvc.perform(post("/api/learningResults")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(learningResult)))
            .andExpect(status().isBadRequest());

        List<LearningResult> learningResults = learningResultRepository.findAll();
        assertThat(learningResults).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = learningResultRepository.findAll().size();
        // set the field null
        learningResult.setCreationTime(null);

        // Create the LearningResult, which fails.

        restLearningResultMockMvc.perform(post("/api/learningResults")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(learningResult)))
            .andExpect(status().isBadRequest());

        List<LearningResult> learningResults = learningResultRepository.findAll();
        assertThat(learningResults).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLearningResults() throws Exception {
        // Initialize the database
        learningResultRepository.saveAndFlush(learningResult);

        // Get all the learningResults
        restLearningResultMockMvc.perform(get("/api/learningResults?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(learningResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].creationTime").value(hasItem(DEFAULT_CREATION_TIME_STR)));
    }

    @Test
    @Transactional
    public void getLearningResult() throws Exception {
        // Initialize the database
        learningResultRepository.saveAndFlush(learningResult);

        // Get the learningResult
        restLearningResultMockMvc.perform(get("/api/learningResults/{id}", learningResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(learningResult.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.creationTime").value(DEFAULT_CREATION_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingLearningResult() throws Exception {
        // Get the learningResult
        restLearningResultMockMvc.perform(get("/api/learningResults/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLearningResult() throws Exception {
        // Initialize the database
        learningResultRepository.saveAndFlush(learningResult);

        int databaseSizeBeforeUpdate = learningResultRepository.findAll().size();

        // Update the learningResult
        learningResult.setType(UPDATED_TYPE);
        learningResult.setDescription(UPDATED_DESCRIPTION);
        learningResult.setCreationTime(UPDATED_CREATION_TIME);

        restLearningResultMockMvc.perform(put("/api/learningResults")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(learningResult)))
            .andExpect(status().isOk());

        // Validate the LearningResult in the database
        List<LearningResult> learningResults = learningResultRepository.findAll();
        assertThat(learningResults).hasSize(databaseSizeBeforeUpdate);
        LearningResult testLearningResult = learningResults.get(learningResults.size() - 1);
        assertThat(testLearningResult.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testLearningResult.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLearningResult.getCreationTime()).isEqualTo(UPDATED_CREATION_TIME);
    }

    @Test
    @Transactional
    public void deleteLearningResult() throws Exception {
        // Initialize the database
        learningResultRepository.saveAndFlush(learningResult);

        int databaseSizeBeforeDelete = learningResultRepository.findAll().size();

        // Get the learningResult
        restLearningResultMockMvc.perform(delete("/api/learningResults/{id}", learningResult.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LearningResult> learningResults = learningResultRepository.findAll();
        assertThat(learningResults).hasSize(databaseSizeBeforeDelete - 1);
    }
}
