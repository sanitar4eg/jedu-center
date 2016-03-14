package edu.netcracker.center.web.rest;

import edu.netcracker.center.Application;
import edu.netcracker.center.domain.Curator;
import edu.netcracker.center.repository.CuratorRepository;

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
 * Test class for the CuratorResource REST controller.
 *
 * @see CuratorResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CuratorResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBB";
    private static final String DEFAULT_LAST_NAME = "AAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_DEPARTMENT = "AAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Inject
    private CuratorRepository curatorRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCuratorMockMvc;

    private Curator curator;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CuratorResource curatorResource = new CuratorResource();
        ReflectionTestUtils.setField(curatorResource, "curatorRepository", curatorRepository);
        this.restCuratorMockMvc = MockMvcBuilders.standaloneSetup(curatorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        curator = new Curator();
        curator.setFirstName(DEFAULT_FIRST_NAME);
        curator.setLastName(DEFAULT_LAST_NAME);
        curator.setEmail(DEFAULT_EMAIL);
        curator.setDepartment(DEFAULT_DEPARTMENT);
        curator.setIsActive(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createCurator() throws Exception {
        int databaseSizeBeforeCreate = curatorRepository.findAll().size();

        // Create the Curator

        restCuratorMockMvc.perform(post("/api/curators")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(curator)))
                .andExpect(status().isCreated());

        // Validate the Curator in the database
        List<Curator> curators = curatorRepository.findAll();
        assertThat(curators).hasSize(databaseSizeBeforeCreate + 1);
        Curator testCurator = curators.get(curators.size() - 1);
        assertThat(testCurator.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCurator.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCurator.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCurator.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testCurator.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = curatorRepository.findAll().size();
        // set the field null
        curator.setLastName(null);

        // Create the Curator, which fails.

        restCuratorMockMvc.perform(post("/api/curators")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(curator)))
                .andExpect(status().isBadRequest());

        List<Curator> curators = curatorRepository.findAll();
        assertThat(curators).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = curatorRepository.findAll().size();
        // set the field null
        curator.setEmail(null);

        // Create the Curator, which fails.

        restCuratorMockMvc.perform(post("/api/curators")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(curator)))
                .andExpect(status().isBadRequest());

        List<Curator> curators = curatorRepository.findAll();
        assertThat(curators).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = curatorRepository.findAll().size();
        // set the field null
        curator.setIsActive(null);

        // Create the Curator, which fails.

        restCuratorMockMvc.perform(post("/api/curators")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(curator)))
                .andExpect(status().isBadRequest());

        List<Curator> curators = curatorRepository.findAll();
        assertThat(curators).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCurators() throws Exception {
        // Initialize the database
        curatorRepository.saveAndFlush(curator);

        // Get all the curators
        restCuratorMockMvc.perform(get("/api/curators?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(curator.getId().intValue())))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT.toString())))
                .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getCurator() throws Exception {
        // Initialize the database
        curatorRepository.saveAndFlush(curator);

        // Get the curator
        restCuratorMockMvc.perform(get("/api/curators/{id}", curator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(curator.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCurator() throws Exception {
        // Get the curator
        restCuratorMockMvc.perform(get("/api/curators/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurator() throws Exception {
        // Initialize the database
        curatorRepository.saveAndFlush(curator);

		int databaseSizeBeforeUpdate = curatorRepository.findAll().size();

        // Update the curator
        curator.setFirstName(UPDATED_FIRST_NAME);
        curator.setLastName(UPDATED_LAST_NAME);
        curator.setEmail(UPDATED_EMAIL);
        curator.setDepartment(UPDATED_DEPARTMENT);
        curator.setIsActive(UPDATED_IS_ACTIVE);

        restCuratorMockMvc.perform(put("/api/curators")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(curator)))
                .andExpect(status().isOk());

        // Validate the Curator in the database
        List<Curator> curators = curatorRepository.findAll();
        assertThat(curators).hasSize(databaseSizeBeforeUpdate);
        Curator testCurator = curators.get(curators.size() - 1);
        assertThat(testCurator.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCurator.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCurator.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCurator.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testCurator.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void deleteCurator() throws Exception {
        // Initialize the database
        curatorRepository.saveAndFlush(curator);

		int databaseSizeBeforeDelete = curatorRepository.findAll().size();

        // Get the curator
        restCuratorMockMvc.perform(delete("/api/curators/{id}", curator.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Curator> curators = curatorRepository.findAll();
        assertThat(curators).hasSize(databaseSizeBeforeDelete - 1);
    }
}
