package edu.netcracker.center.web.rest;

import edu.netcracker.center.Application;
import edu.netcracker.center.domain.Evaluation;
import edu.netcracker.center.repository.EvaluationRepository;

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
 * Test class for the EvaluationResource REST controller.
 *
 * @see EvaluationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EvaluationResourceIntTest {


    private static final Float DEFAULT_VALUE = 0F;
    private static final Float UPDATED_VALUE = 1F;

    @Inject
    private EvaluationRepository evaluationRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEvaluationMockMvc;

    private Evaluation evaluation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EvaluationResource evaluationResource = new EvaluationResource();
        ReflectionTestUtils.setField(evaluationResource, "evaluationRepository", evaluationRepository);
        this.restEvaluationMockMvc = MockMvcBuilders.standaloneSetup(evaluationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        evaluation = new Evaluation();
        evaluation.setValue(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createEvaluation() throws Exception {
        int databaseSizeBeforeCreate = evaluationRepository.findAll().size();

        // Create the Evaluation

        restEvaluationMockMvc.perform(post("/api/evaluations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(evaluation)))
                .andExpect(status().isCreated());

        // Validate the Evaluation in the database
        List<Evaluation> evaluations = evaluationRepository.findAll();
        assertThat(evaluations).hasSize(databaseSizeBeforeCreate + 1);
        Evaluation testEvaluation = evaluations.get(evaluations.size() - 1);
        assertThat(testEvaluation.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = evaluationRepository.findAll().size();
        // set the field null
        evaluation.setValue(null);

        // Create the Evaluation, which fails.

        restEvaluationMockMvc.perform(post("/api/evaluations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(evaluation)))
                .andExpect(status().isBadRequest());

        List<Evaluation> evaluations = evaluationRepository.findAll();
        assertThat(evaluations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEvaluations() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluations
        restEvaluationMockMvc.perform(get("/api/evaluations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(evaluation.getId().intValue())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())));
    }

    @Test
    @Transactional
    public void getEvaluation() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get the evaluation
        restEvaluationMockMvc.perform(get("/api/evaluations/{id}", evaluation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(evaluation.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEvaluation() throws Exception {
        // Get the evaluation
        restEvaluationMockMvc.perform(get("/api/evaluations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvaluation() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

		int databaseSizeBeforeUpdate = evaluationRepository.findAll().size();

        // Update the evaluation
        evaluation.setValue(UPDATED_VALUE);

        restEvaluationMockMvc.perform(put("/api/evaluations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(evaluation)))
                .andExpect(status().isOk());

        // Validate the Evaluation in the database
        List<Evaluation> evaluations = evaluationRepository.findAll();
        assertThat(evaluations).hasSize(databaseSizeBeforeUpdate);
        Evaluation testEvaluation = evaluations.get(evaluations.size() - 1);
        assertThat(testEvaluation.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deleteEvaluation() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

		int databaseSizeBeforeDelete = evaluationRepository.findAll().size();

        // Get the evaluation
        restEvaluationMockMvc.perform(delete("/api/evaluations/{id}", evaluation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Evaluation> evaluations = evaluationRepository.findAll();
        assertThat(evaluations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
