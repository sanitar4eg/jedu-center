package edu.netcracker.center.web.rest;

import edu.netcracker.center.Application;
import edu.netcracker.center.domain.ReasonForLeaving;
import edu.netcracker.center.repository.ReasonForLeavingRepository;
import edu.netcracker.center.service.ReasonForLeavingService;

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

import edu.netcracker.center.domain.enumeration.TypeOfReason;

/**
 * Test class for the ReasonForLeavingResource REST controller.
 *
 * @see ReasonForLeavingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ReasonForLeavingResourceIntTest {

    
    private static final TypeOfReason DEFAULT_TYPE = TypeOfReason.Dismissed;
    private static final TypeOfReason UPDATED_TYPE = TypeOfReason.GotJob;
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private ReasonForLeavingRepository reasonForLeavingRepository;

    @Inject
    private ReasonForLeavingService reasonForLeavingService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restReasonForLeavingMockMvc;

    private ReasonForLeaving reasonForLeaving;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReasonForLeavingResource reasonForLeavingResource = new ReasonForLeavingResource();
        ReflectionTestUtils.setField(reasonForLeavingResource, "reasonForLeavingService", reasonForLeavingService);
        this.restReasonForLeavingMockMvc = MockMvcBuilders.standaloneSetup(reasonForLeavingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        reasonForLeaving = new ReasonForLeaving();
        reasonForLeaving.setType(DEFAULT_TYPE);
        reasonForLeaving.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createReasonForLeaving() throws Exception {
        int databaseSizeBeforeCreate = reasonForLeavingRepository.findAll().size();

        // Create the ReasonForLeaving

        restReasonForLeavingMockMvc.perform(post("/api/reasonForLeavings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(reasonForLeaving)))
                .andExpect(status().isCreated());

        // Validate the ReasonForLeaving in the database
        List<ReasonForLeaving> reasonForLeavings = reasonForLeavingRepository.findAll();
        assertThat(reasonForLeavings).hasSize(databaseSizeBeforeCreate + 1);
        ReasonForLeaving testReasonForLeaving = reasonForLeavings.get(reasonForLeavings.size() - 1);
        assertThat(testReasonForLeaving.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testReasonForLeaving.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = reasonForLeavingRepository.findAll().size();
        // set the field null
        reasonForLeaving.setType(null);

        // Create the ReasonForLeaving, which fails.

        restReasonForLeavingMockMvc.perform(post("/api/reasonForLeavings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(reasonForLeaving)))
                .andExpect(status().isBadRequest());

        List<ReasonForLeaving> reasonForLeavings = reasonForLeavingRepository.findAll();
        assertThat(reasonForLeavings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReasonForLeavings() throws Exception {
        // Initialize the database
        reasonForLeavingRepository.saveAndFlush(reasonForLeaving);

        // Get all the reasonForLeavings
        restReasonForLeavingMockMvc.perform(get("/api/reasonForLeavings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(reasonForLeaving.getId().intValue())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getReasonForLeaving() throws Exception {
        // Initialize the database
        reasonForLeavingRepository.saveAndFlush(reasonForLeaving);

        // Get the reasonForLeaving
        restReasonForLeavingMockMvc.perform(get("/api/reasonForLeavings/{id}", reasonForLeaving.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(reasonForLeaving.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReasonForLeaving() throws Exception {
        // Get the reasonForLeaving
        restReasonForLeavingMockMvc.perform(get("/api/reasonForLeavings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReasonForLeaving() throws Exception {
        // Initialize the database
        reasonForLeavingRepository.saveAndFlush(reasonForLeaving);

		int databaseSizeBeforeUpdate = reasonForLeavingRepository.findAll().size();

        // Update the reasonForLeaving
        reasonForLeaving.setType(UPDATED_TYPE);
        reasonForLeaving.setDescription(UPDATED_DESCRIPTION);

        restReasonForLeavingMockMvc.perform(put("/api/reasonForLeavings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(reasonForLeaving)))
                .andExpect(status().isOk());

        // Validate the ReasonForLeaving in the database
        List<ReasonForLeaving> reasonForLeavings = reasonForLeavingRepository.findAll();
        assertThat(reasonForLeavings).hasSize(databaseSizeBeforeUpdate);
        ReasonForLeaving testReasonForLeaving = reasonForLeavings.get(reasonForLeavings.size() - 1);
        assertThat(testReasonForLeaving.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testReasonForLeaving.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteReasonForLeaving() throws Exception {
        // Initialize the database
        reasonForLeavingRepository.saveAndFlush(reasonForLeaving);

		int databaseSizeBeforeDelete = reasonForLeavingRepository.findAll().size();

        // Get the reasonForLeaving
        restReasonForLeavingMockMvc.perform(delete("/api/reasonForLeavings/{id}", reasonForLeaving.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ReasonForLeaving> reasonForLeavings = reasonForLeavingRepository.findAll();
        assertThat(reasonForLeavings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
