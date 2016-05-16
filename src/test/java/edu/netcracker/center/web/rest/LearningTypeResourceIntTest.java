package edu.netcracker.center.web.rest;

import edu.netcracker.center.Application;
import edu.netcracker.center.domain.LearningType;
import edu.netcracker.center.repository.LearningTypeRepository;
import edu.netcracker.center.service.LearningTypeService;

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
 * Test class for the LearningTypeResource REST controller.
 *
 * @see LearningTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LearningTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Inject
    private LearningTypeRepository learningTypeRepository;

    @Inject
    private LearningTypeService learningTypeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLearningTypeMockMvc;

    private LearningType learningType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LearningTypeResource learningTypeResource = new LearningTypeResource();
        ReflectionTestUtils.setField(learningTypeResource, "learningTypeService", learningTypeService);
        this.restLearningTypeMockMvc = MockMvcBuilders.standaloneSetup(learningTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        learningType = new LearningType();
        learningType.setName(DEFAULT_NAME);
        learningType.setIsActive(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createLearningType() throws Exception {
        int databaseSizeBeforeCreate = learningTypeRepository.findAll().size();

        // Create the LearningType

        restLearningTypeMockMvc.perform(post("/api/learningTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(learningType)))
                .andExpect(status().isCreated());

        // Validate the LearningType in the database
        List<LearningType> learningTypes = learningTypeRepository.findAll();
        assertThat(learningTypes).hasSize(databaseSizeBeforeCreate + 1);
        LearningType testLearningType = learningTypes.get(learningTypes.size() - 1);
        assertThat(testLearningType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLearningType.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = learningTypeRepository.findAll().size();
        // set the field null
        learningType.setName(null);

        // Create the LearningType, which fails.

        restLearningTypeMockMvc.perform(post("/api/learningTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(learningType)))
                .andExpect(status().isBadRequest());

        List<LearningType> learningTypes = learningTypeRepository.findAll();
        assertThat(learningTypes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = learningTypeRepository.findAll().size();
        // set the field null
        learningType.setIsActive(null);

        // Create the LearningType, which fails.

        restLearningTypeMockMvc.perform(post("/api/learningTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(learningType)))
                .andExpect(status().isBadRequest());

        List<LearningType> learningTypes = learningTypeRepository.findAll();
        assertThat(learningTypes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLearningTypes() throws Exception {
        // Initialize the database
        learningTypeRepository.saveAndFlush(learningType);

        // Get all the learningTypes
        restLearningTypeMockMvc.perform(get("/api/learningTypes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(learningType.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getLearningType() throws Exception {
        // Initialize the database
        learningTypeRepository.saveAndFlush(learningType);

        // Get the learningType
        restLearningTypeMockMvc.perform(get("/api/learningTypes/{id}", learningType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(learningType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLearningType() throws Exception {
        // Get the learningType
        restLearningTypeMockMvc.perform(get("/api/learningTypes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLearningType() throws Exception {
        // Initialize the database
        learningTypeRepository.saveAndFlush(learningType);

		int databaseSizeBeforeUpdate = learningTypeRepository.findAll().size();

        // Update the learningType
        learningType.setName(UPDATED_NAME);
        learningType.setIsActive(UPDATED_IS_ACTIVE);

        restLearningTypeMockMvc.perform(put("/api/learningTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(learningType)))
                .andExpect(status().isOk());

        // Validate the LearningType in the database
        List<LearningType> learningTypes = learningTypeRepository.findAll();
        assertThat(learningTypes).hasSize(databaseSizeBeforeUpdate);
        LearningType testLearningType = learningTypes.get(learningTypes.size() - 1);
        assertThat(testLearningType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLearningType.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void deleteLearningType() throws Exception {
        // Initialize the database
        learningTypeRepository.saveAndFlush(learningType);

		int databaseSizeBeforeDelete = learningTypeRepository.findAll().size();

        // Get the learningType
        restLearningTypeMockMvc.perform(delete("/api/learningTypes/{id}", learningType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LearningType> learningTypes = learningTypeRepository.findAll();
        assertThat(learningTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
