```java
package com.digital.school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import com.digital.school.model.Document;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DocumentService {
    List<Map<String, Object>> findAllAsMap();
    Optional<Document> findById(Long id);
    Document save(Document document);
    Document upload(MultipartFile file, String type, String category, Long studentId, Long parentId);
    ResponseEntity<?> download(Long id);
    Document validate(Long id);
    void deleteById(Long id);
}
```