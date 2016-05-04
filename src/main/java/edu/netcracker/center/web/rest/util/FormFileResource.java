package edu.netcracker.center.web.rest.util;

import com.codahale.metrics.annotation.Timed;
import edu.netcracker.center.domain.Form;
import edu.netcracker.center.service.FileServerService;
import edu.netcracker.center.service.FormService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
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
public class FormFileResource {

    private final Logger log = LoggerFactory.getLogger(FormFileResource.class);

    private static final String FORMS_PATH = "/forms/";
    private static final String APPLY_FORM_NAME = "Lastname_Firstname.docx";
    private static final String APPLY_FORM_PATH = "documents/apply-form/" + APPLY_FORM_NAME;
    private static final Boolean IS_ACTIVE = true;

    @Inject
    private FormService formService;

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
        log.debug("REST request to save Apply with id: {}, to path: {}, fileName: {}", form.getId(), dirPath,
            fileName);
        try {
            File directory = new File(dirPath);
            boolean result = directory.mkdirs();
            log.debug("Dirs Created: {}", result);
            file.transferTo(new File(dirPath + "/" + fileName));
        } catch (IOException e) {
            log.error("Error saving file for form: {}, file:{}", form.getId(), fileName, e);
            formService.delete(form);
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
        Resource resource = new ClassPathResource(APPLY_FORM_PATH);
        log.debug("File name: {}", resource.getFilename());
        try (InputStream is = resource.getInputStream()) {
            response.setContentType("application/msword");
            response.setHeader("Content-Disposition", "attachment; filename=" + APPLY_FORM_NAME);
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            log.info("Error getting form to fill:{}", APPLY_FORM_NAME);
            throw new RuntimeException(e);
        }
    }

    /**
     * GET  /forms/file/:id -> get forms file by "id".
     */
    @RequestMapping(value = "forms/file/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void downloadFile(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get file of Form by id: {}", id);
        Form form = formService.findOne(id);
        String filePath = fileServerService.getPath() + FORMS_PATH + form.getId() + "/" + form.getFile();
        log.debug("File path: {}", filePath);
        try (InputStream is = new FileInputStream(filePath)) {
            String fileName = java.net.URLEncoder.encode(form.getFile(),"UTF-8");
            response.setContentType("application/msword");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            log.info("Error getting file for form: {}, file:{}", form.getId(), form.getFile());
            throw new RuntimeException(e);
        }
    }

    /**
     * POST  /froms/file/{id} -> upload file for form with "id".
     */
    @RequestMapping(value = "/forms/file/{id}",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void saveFile(@RequestParam("file") MultipartFile file, @PathVariable Long id) {
        Form form = formService.findOne(id);
        String fileName = file.getOriginalFilename();
        String dirPath = fileServerService.getPath() + FORMS_PATH + id;
        log.debug("REST request to save file of Form by id: {}, to path: {}, fileName: {}", id, dirPath,
            fileName);
        try {
            File directory = new File(dirPath);
            boolean result = directory.mkdirs();
            log.debug("Dirs Created: {}", result);
            file.transferTo(new File(dirPath + "/" + fileName));
        } catch (IOException e) {
            log.error("Error saving file for form: {}, file:{}", id, fileName, e);
            throw new RuntimeException(e);
        }
        form.setFile(fileName);
        formService.save(form);
    }

    private Form createForm(String fileName) {
        Form form = new Form();
        form.setFile(fileName);
        form.setCreationTime(ZonedDateTime.now());
        form.setIsActive(IS_ACTIVE);
        formService.save(form);
        return form;
    }

}

