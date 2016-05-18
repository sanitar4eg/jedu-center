package edu.netcracker.center.web.rest.util;

import com.codahale.metrics.annotation.Timed;
import edu.netcracker.center.domain.Recall;
import edu.netcracker.center.service.FileServerService;
import edu.netcracker.center.service.RecallService;
import org.apache.catalina.util.URLEncoder;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing files of Recall.
 */
@RestController
@RequestMapping("/api")
public class RecallFileResource {

    private final Logger log = LoggerFactory.getLogger(RecallFileResource.class);
    private final static String RECALL_PATH = "/recalls/";

    @Inject
    RecallService recallService;

    @Inject
    FileServerService fileServerService;

    /**
     * GET  /recalls/file/:id -> get recalls file by "id".
     */
    @RequestMapping(value = "recalls/file/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void downloadFile(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get file of Recall by id: {}", id);
        Recall recall = recallService.findOne(id);
        String filePath = fileServerService.getPath() + RECALL_PATH + recall.getId() + "/" + recall.getFile();
        log.debug("File path: {}", filePath);
        try (InputStream is = new FileInputStream(filePath)) {
            String fileName = java.net.URLEncoder.encode(recall.getFile(), "UTF-8");
            response.setContentType("application/msword");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
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
    public ResponseEntity<Recall> saveFile(@RequestParam("file") MultipartFile file, @PathVariable Long id)
        throws URISyntaxException {
        Recall recall = recallService.findOne(id);
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
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("recall",
                "fileUpload", "Не удалось загрузить файл")).body(recall);
        }
        recall.setFile(fileName);
        recall = recallService.save(recall);
        return ResponseEntity.created(new URI("/api/recalls/" + recall.getId()))
            .headers(HeaderUtil.createEntityUpdateAlert("recall", recall.getId().toString()))
            .body(recall);
    }


}
