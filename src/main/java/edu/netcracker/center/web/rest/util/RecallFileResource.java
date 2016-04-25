package edu.netcracker.center.web.rest.util;

import com.codahale.metrics.annotation.Timed;
import edu.netcracker.center.domain.Recall;
import edu.netcracker.center.repository.RecallRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * REST controller for managing files of Recall.
 */
@RestController
@RequestMapping("/api")
public class RecallFileResource {

    private final Logger log = LoggerFactory.getLogger(RecallFileResource.class);
    private final static String RECALL_PATH = "recall/students/";

    @Inject
    RecallRepository recallRepository;

    /**
     * GET  /recalls/file/:id -> get recalls file by "id".
     */
    @RequestMapping(value = "recalls/file/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void getHistoryOfStudents(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get file of Recall by id: {}", id);
        Recall recall = recallRepository.getOne(id);
        try (InputStream is = new FileInputStream(RECALL_PATH + recall.getFile())) {
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            log.info("Error getting file for recall: {}, file:{}", recall.getId(), recall.getFile());
            throw new RuntimeException(e);
        }
    }

    /**
     * POST  /recalls/file/{id} -> upload file for recall with "id".
     */
    @RequestMapping(value = "/recalls/file/{id}",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void handleImport(@RequestParam("file") MultipartFile file, @PathVariable Long id,
                             HttpServletRequest request) {
        Recall recall = recallRepository.getOne(id);
        String fileName = file.getOriginalFilename();
        recall.setFile(fileName);
        recallRepository.save(recall);
        String dirPath = request.getServletContext().getRealPath("/") + RECALL_PATH + id;
        log.debug("REST request to save file of Recall by id: {}, to path: {}, fileName: {}", id, dirPath,
            fileName);
        try {
            File directory = new File(dirPath);
            boolean result = directory.mkdirs();
            log.debug("Dirs Created: {}", result);
            file.transferTo(new File(dirPath  + "/" + fileName));
        } catch (IOException e) {
            log.error("Error saving file for recall: {}, file:{}", id, fileName, e);
            throw new RuntimeException(e);
        }
    }


}
