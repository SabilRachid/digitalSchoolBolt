package com.digital.school.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.digital.school.model.Document;
import com.digital.school.model.User;
import com.digital.school.service.DocumentService;
import com.digital.school.service.StorageService;
import com.digital.school.service.UserService;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        return ResponseEntity.ok(documentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocument(@PathVariable Long id) {
        return documentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Document>> getStudentDocuments(@PathVariable Long studentId) {
        return userService.findById(studentId)
                .map(student -> ResponseEntity.ok(documentService.findByStudent(student)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<Document>> getParentDocuments(@PathVariable Long parentId) {
        return userService.findById(parentId)
                .map(parent -> ResponseEntity.ok(documentService.findByParent(parent)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Document>> getDocumentsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(documentService.findByCategory(category));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Document>> getDocumentsByType(@PathVariable String type) {
        return ResponseEntity.ok(documentService.findByType(type));
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type,
            @RequestParam("category") String category,
            @RequestParam(value = "studentId", required = false) Long studentId,
            @RequestParam(value = "parentId", required = false) Long parentId,
            @AuthenticationPrincipal User uploader) {
        try {
            User student = studentId != null ? 
                userService.findById(studentId).orElse(null) : null;
            User parent = parentId != null ? 
                userService.findById(parentId).orElse(null) : null;

            Document document = documentService.upload(file, type, category, uploader, student, parent);
            return ResponseEntity.ok(document);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Erreur lors de l'upload: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id) {
        return documentService.findById(id).map(document -> {
            try {
                Resource file = storageService.loadAsResource(document.getFilePath());
                return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(document.getMimeType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, 
                        "attachment; filename=\"" + document.getName() + "\"")
                    .body(file);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/validate")
    public ResponseEntity<?> validateDocument(
            @PathVariable Long id,
            @AuthenticationPrincipal User validator) {
        try {
            Document validatedDoc = documentService.validate(id, validator);
            return ResponseEntity.ok(validatedDoc);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Erreur lors de la validation: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable Long id) {
        try {
            documentService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Erreur lors de la suppression: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/check")
    public ResponseEntity<Map<String, Boolean>> checkDocumentExists(
            @RequestParam(value = "studentId", required = false) Long studentId,
            @RequestParam(value = "parentId", required = false) Long parentId,
            @RequestParam("type") String type) {
        
        Map<String, Boolean> result = new HashMap<>();
        
        if (studentId != null) {
            userService.findById(studentId).ifPresent(student -> 
                result.put("exists", documentService.existsByStudentAndType(student, type)));
        } else if (parentId != null) {
            userService.findById(parentId).ifPresent(parent -> 
                result.put("exists", documentService.existsByParentAndType(parent, type)));
        }
        
        return ResponseEntity.ok(result);
    }
}