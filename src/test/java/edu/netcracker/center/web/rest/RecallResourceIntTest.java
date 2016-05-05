package edu.netcracker.center.web.rest;

import edu.netcracker.center.Application;
import edu.netcracker.center.domain.Recall;
import edu.netcracker.center.repository.RecallRepository;
import edu.netcracker.center.service.RecallService;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.netcracker.center.domain.enumeration.TypeRecallEnumeration;

/**
 * Test class for the RecallResource REST controller.
 *
 * @see RecallResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RecallResourceIntTest {


    private static final TypeRecallEnumeration DEFAULT_TYPE = TypeRecallEnumeration.Technical;
    private static final TypeRecallEnumeration UPDATED_TYPE = TypeRecallEnumeration.Interview;
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_FILE = "AAAAA";
    private static final String UPDATED_FILE = "BBBBB";

    @Inject
    private RecallRepository recallRepository;

    @Inject
    private RecallService recallService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private QuerydslPredicateArgumentResolver querydslPredicateArgumentResolver;

    private MockMvc restRecallMockMvc;

    private Recall recall;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RecallResource recallResource = new RecallResource();
        ReflectionTestUtils.setField(recallResource, "recallService", recallService);
        this.restRecallMockMvc = MockMvcBuilders.standaloneSetup(recallResource)
            .setCustomArgumentResolvers(pageableArgumentResolver, querydslPredicateArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        recall = new Recall();
        recall.setType(DEFAULT_TYPE);
        recall.setName(DEFAULT_NAME);
        recall.setDescription(DEFAULT_DESCRIPTION);
        recall.setFile(DEFAULT_FILE);
    }

    @Test
    @Transactional
    public void createRecall() throws Exception {
        int databaseSizeBeforeCreate = recallRepository.findAll().size();

        // Create the Recall

        restRecallMockMvc.perform(post("/api/recalls")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(recall)))
                .andExpect(status().isCreated());

        // Validate the Recall in the database
        List<Recall> recalls = recallRepository.findAll();
        assertThat(recalls).hasSize(databaseSizeBeforeCreate + 1);
        Recall testRecall = recalls.get(recalls.size() - 1);
        assertThat(testRecall.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testRecall.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRecall.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRecall.getFile()).isEqualTo(DEFAULT_FILE);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = recallRepository.findAll().size();
        // set the field null
        recall.setType(null);

        // Create the Recall, which fails.

        restRecallMockMvc.perform(post("/api/recalls")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(recall)))
                .andExpect(status().isBadRequest());

        List<Recall> recalls = recallRepository.findAll();
        assertThat(recalls).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecalls() throws Exception {
        // Initialize the database
        recallRepository.saveAndFlush(recall);

        // Get all the recalls
        restRecallMockMvc.perform(get("/api/recalls?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(recall.getId().intValue())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].file").value(hasItem(DEFAULT_FILE.toString())));
    }

    @Test
    @Transactional
    public void getRecall() throws Exception {
        // Initialize the database
        recallRepository.saveAndFlush(recall);

        // Get the recall
        restRecallMockMvc.perform(get("/api/recalls/{id}", recall.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(recall.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.file").value(DEFAULT_FILE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRecall() throws Exception {
        // Get the recall
        restRecallMockMvc.perform(get("/api/recalls/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecall() throws Exception {
        // Initialize the database
        recallRepository.saveAndFlush(recall);

		int databaseSizeBeforeUpdate = recallRepository.findAll().size();

        // Update the recall
        recall.setType(UPDATED_TYPE);
        recall.setName(UPDATED_NAME);
        recall.setDescription(UPDATED_DESCRIPTION);
        recall.setFile(UPDATED_FILE);

        restRecallMockMvc.perform(put("/api/recalls")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(recall)))
                .andExpect(status().isOk());

        // Validate the Recall in the database
        List<Recall> recalls = recallRepository.findAll();
        assertThat(recalls).hasSize(databaseSizeBeforeUpdate);
        Recall testRecall = recalls.get(recalls.size() - 1);
        assertThat(testRecall.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRecall.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRecall.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRecall.getFile()).isEqualTo(UPDATED_FILE);
    }

    @Test
    @Transactional
    public void deleteRecall() throws Exception {
        // Initialize the database
        recallRepository.saveAndFlush(recall);

		int databaseSizeBeforeDelete = recallRepository.findAll().size();

        // Get the recall
        restRecallMockMvc.perform(delete("/api/recalls/{id}", recall.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Recall> recalls = recallRepository.findAll();
        assertThat(recalls).hasSize(databaseSizeBeforeDelete - 1);
    }
}
