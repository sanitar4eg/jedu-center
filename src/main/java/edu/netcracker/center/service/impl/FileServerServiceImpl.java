package edu.netcracker.center.service.impl;

import edu.netcracker.center.config.JHipsterProperties;
import edu.netcracker.center.service.FileServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.File;

@Service
public class FileServerServiceImpl implements FileServerService {

    private final Logger log = LoggerFactory.getLogger(FileServerServiceImpl.class);

    @Inject
    private JHipsterProperties jHipsterProperties;

    private String path = "";

    @PostConstruct
    public void init() {
        path = jHipsterProperties.getWorkdir().getPath();
        File dir = new File(path);
        log.debug("Check working directory: {}", dir.getAbsolutePath());
        if (!dir.exists()) {
            log.info("Created working directory: {}, path {}", dir.mkdir(), dir.getAbsolutePath());
        }
    }

    public String getPath() {
        return path;
    }
}
