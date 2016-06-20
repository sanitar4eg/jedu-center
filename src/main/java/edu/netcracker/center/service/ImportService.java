package edu.netcracker.center.service;

import edu.netcracker.center.domain.util.OperationResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface ImportService {

    Collection<OperationResult> handleImportOfStudents(MultipartFile file, Long id);
}
