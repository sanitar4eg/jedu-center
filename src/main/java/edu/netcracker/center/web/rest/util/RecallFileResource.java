package edu.netcracker.center.web.rest.util;

import com.codahale.metrics.annotation.Timed;
import edu.netcracker.center.domain.Recall;
import edu.netcracker.center.repository.RecallRepository;
import edu.netcracker.center.service.FileServerService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * REST controller for managing files of Recall.
 */
@RestController
@RequestMapping("/api")
public class RecallFileResource {

    private final Logger log = LoggerFactory.getLogger(RecallFileResource.class);
    private final static String RECALL_PATH = "/recalls/";

    @Inject
    RecallRepository recallRepository;

    @Inject
    FileServerService fileServerService;

    /**
     * GET  /recalls/file/:id -> get recalls file by "id".
     */
    @RequestMapping(value = "recalls/file/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public void downloadFile(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get file of Recall by id: {}", id);
        Recall recall = recallRepository.getOne(id);
        String filePath = fileServerService.getPath() + RECALL_PATH + recall.getId() + "/" + recall.getFile();
        log.debug("File path: {}", filePath);
        try (InputStream is = new FileInputStream(filePath)) {
            response.setContentType("application/msword");
            response.setHeader("Content-Disposition", "attachment; filename=" + recall.getFile());
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
    @Transactional
    public void saveFile(@RequestParam("file") MultipartFile file, @PathVariable Long id) {
        Recall recall = recallRepository.getOne(id);
        String fileName = file.getOriginalFilename();
        String dirPath = fileServerService.getPath() + RECALL_PATH + id;
        log.debug("REST request to save file of Recall by id: {}, to path: {}, fileName: {}", id, dirPath,
            fileName);
        try {
            File directory = new File(dirPath);
            boolean result = directory.mkdirs();
            log.debug("Dirs Created: {}", result);
            file.transferTo(new File(dirPath + "/" + fileName));
        } catch (IOException e) {
            log.error("Error saving file for recall: {}, file:{}", id, fileName, e);
            throw new RuntimeException(e);
        }
        recall.setFile(fileName);
        recallRepository.save(recall);
    }


}
