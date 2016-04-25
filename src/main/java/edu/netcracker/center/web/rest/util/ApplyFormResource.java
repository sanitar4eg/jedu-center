package edu.netcracker.center.web.rest.util;

import com.codahale.metrics.annotation.Timed;
import edu.netcracker.center.domain.Form;
import edu.netcracker.center.repository.FormRepository;
import edu.netcracker.center.service.FileServerService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;

/**
 * REST controller for managing files of form.
 */
@RestController
@RequestMapping("/api")
public class ApplyFormResource {

    private final Logger log = LoggerFactory.getLogger(ApplyFormResource.class);

    private static final String FORMS_PATH = "/forms/";
    private static final String APPLY_FORM_PATH = "/documents/apply-form/Lastname_Firstname.docx";
    private static final String APPLY_FORM_NAME = "Lastname_Firstname.docx";
    private static final Boolean IS_ACTIVE = true;

    @Inject
    private FormRepository formRepository;

    @Inject
    FileServerService fileServerService;

    /**
     * POST  /apply/forms -> Upload a new form.
     */
    @RequestMapping(value = "/apply/form",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void createForm(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Form form = createForm(fileName);
        String dirPath = fileServerService.getPath() + FORMS_PATH + form.getId();
        try {
            File directory = new File(dirPath);
            boolean result = directory.mkdirs();
            log.debug("Dirs Created: {}", result);
            file.transferTo(new File(dirPath + "/" + fileName));
        } catch (IOException e) {
            log.error("Error saving file for recall: {}, file:{}", form.getId(), fileName, e);
            formRepository.delete(form);
            throw new RuntimeException(e);
        }
    }

    /**
     * GET  /apply/form -> get the from to fill
     */
    @RequestMapping(value = "/apply/form",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void getForm(HttpServletResponse response) {
        log.debug("REST request to get Form to fill");
        String filePath = fileServerService.getPath() + APPLY_FORM_PATH;
        log.debug("File path: {}", filePath);
        try (InputStream is = new FileInputStream(filePath)) {
            response.setContentType("application/msword");
            response.setHeader("Content-Disposition", "attachment; filename=" + APPLY_FORM_NAME);
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            log.info("Error getting form to fill:{}", APPLY_FORM_NAME);
            throw new RuntimeException(e);
        }
    }

    private Form createForm(String fileName) {
        Form form = new Form();
        form.setFile(fileName);
        form.setCreationTime(ZonedDateTime.now());
        form.setIsActive(IS_ACTIVE);
        formRepository.save(form);
        formRepository.flush();
        return form;
    }

}

