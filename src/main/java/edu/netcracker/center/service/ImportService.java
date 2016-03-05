package edu.netcracker.center.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImportService {

    void handleImportOfStudents(MultipartFile file);
}
