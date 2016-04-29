package edu.netcracker.center.web.rest;

import edu.netcracker.center.Application;
import edu.netcracker.center.domain.Lesson;
import edu.netcracker.center.repository.LessonRepository;
import edu.netcracker.center.service.LessonService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the LessonResource REST controller.
 *
 * @see LessonResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LessonResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_TOPIC = "AAAAA";
    private static final String UPDATED_TOPIC = "BBBBB";

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_TIME_STR = dateTimeFormatter.format(DEFAULT_TIME);

    @Inject
    private LessonRepository lessonRepository;

    @Inject
    private LessonService lessonService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLessonMockMvc;

    private Lesson lesson;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LessonResource lessonResource = new LessonResource();
        ReflectionTestUtils.setField(lessonResource, "lessonService", lessonService);
        this.restLessonMockMvc = MockMvcBuilders.standaloneSetup(lessonResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lesson = new Lesson();
        lesson.setTopic(DEFAULT_TOPIC);
        lesson.setTime(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createLesson() throws Exception {
        int databaseSizeBeforeCreate = lessonRepository.findAll().size();

        // Create the Lesson

        restLessonMockMvc.perform(post("/api/lessons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lesson)))
                .andExpect(status().isCreated());

        // Validate the Lesson in the database
        List<Lesson> lessons = lessonRepository.findAll();
        assertThat(lessons).hasSize(databaseSizeBeforeCreate + 1);
        Lesson testLesson = lessons.get(lessons.size() - 1);
        assertThat(testLesson.getTopic()).isEqualTo(DEFAULT_TOPIC);
        assertThat(testLesson.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void checkTopicIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonRepository.findAll().size();
        // set the field null
        lesson.setTopic(null);

        // Create the Lesson, which fails.

        restLessonMockMvc.perform(post("/api/lessons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lesson)))
                .andExpect(status().isBadRequest());

        List<Lesson> lessons = lessonRepository.findAll();
        assertThat(lessons).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonRepository.findAll().size();
        // set the field null
        lesson.setTime(null);

        // Create the Lesson, which fails.

        restLessonMockMvc.perform(post("/api/lessons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lesson)))
                .andExpect(status().isBadRequest());

        List<Lesson> lessons = lessonRepository.findAll();
        assertThat(lessons).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLessons() throws Exception {
        // Initialize the database
        lessonRepository.saveAndFlush(lesson);

        // Get all the lessons
        restLessonMockMvc.perform(get("/api/lessons?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lesson.getId().intValue())))
                .andExpect(jsonPath("$.[*].topic").value(hasItem(DEFAULT_TOPIC.toString())))
                .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME_STR)));
    }

    @Test
    @Transactional
    public void getLesson() throws Exception {
        // Initialize the database
        lessonRepository.saveAndFlush(lesson);

        // Get the lesson
        restLessonMockMvc.perform(get("/api/lessons/{id}", lesson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lesson.getId().intValue()))
            .andExpect(jsonPath("$.topic").value(DEFAULT_TOPIC.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingLesson() throws Exception {
        // Get the lesson
        restLessonMockMvc.perform(get("/api/lessons/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLesson() throws Exception {
        // Initialize the database
        lessonRepository.saveAndFlush(lesson);

		int databaseSizeBeforeUpdate = lessonRepository.findAll().size();

        // Update the lesson
        lesson.setTopic(UPDATED_TOPIC);
        lesson.setTime(UPDATED_TIME);

        restLessonMockMvc.perform(put("/api/lessons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lesson)))
                .andExpect(status().isOk());

        // Validate the Lesson in the database
        List<Lesson> lessons = lessonRepository.findAll();
        assertThat(lessons).hasSize(databaseSizeBeforeUpdate);
        Lesson testLesson = lessons.get(lessons.size() - 1);
        assertThat(testLesson.getTopic()).isEqualTo(UPDATED_TOPIC);
        assertThat(testLesson.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void deleteLesson() throws Exception {
        // Initialize the database
        lessonRepository.saveAndFlush(lesson);

		int databaseSizeBeforeDelete = lessonRepository.findAll().size();

        // Get the lesson
        restLessonMockMvc.perform(delete("/api/lessons/{id}", lesson.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Lesson> lessons = lessonRepository.findAll();
        assertThat(lessons).hasSize(databaseSizeBeforeDelete - 1);
    }
}
