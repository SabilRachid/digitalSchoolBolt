```java
package com.digital.school.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.digital.school.model.Document;
import com.digital.school.model.User;
import com.digital.school.repository.DocumentRepository;
import com.digital.school.service.DocumentService;
import com.digital.school.service.StorageService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private StorageService storageService;

    @Override
    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    @Override
    public Optional<Document> findById(Long id) {
        return documentRepository.findById(id);
    }

    @Override
    @Transactional
    public Document save(Document document) {
        return documentRepository.save(document);
    }

    @Override
    @Transactional
    public Document upload(MultipartFile file, String type, String category, 
                         User uploader, User student, User parent) {
        try {
            // Générer un nom de fichier unique
            String fileName = generateUniqueFileName(file.getOriginalFilename());
            
            // Sauvegarder le fichier
            String filePath = storageService.store(file, fileName);
            
            // Créer le document
            Document document = new Document();
            document.setName(file.getOriginalFilename());
            document.setType(type);
            document.setCategory(category);
            document.setFilePath(filePath);
            document.setMimeType(file.getContentType());
            document.setFileSize(file.getSize());
            document.setUploadedBy(uploader);
            document.setUploadedAt(LocalDateTime.now());
            document.setStudent(student);
            document.setParent(parent);
            
            return documentRepository.save(document);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'upload du document", e);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        documentRepository.findById(id).ifPresent(document -> {
            // Supprimer le fichier physique
            storageService.delete(document.getFilePath());
            // Supprimer l'enregistrement
            documentRepository.deleteById(id);
        });
    }

    @Override
    public List<Document> findByStudent(User student) {
        return documentRepository.findByStudent(student);
    }

    @Override
    public List<Document> findByParent(User parent) {
        return documentRepository.findByParent(parent);
    }

    @Override
    public List<Document> findByCategory(String category) {
        return documentRepository.findByCategory(category);
    }

    @Override
    public List<Document> findByType(String type) {
        return documentRepository.findByType(type);
    }

    @Override
    public List<Document> findByStudentAndType(User student, String type) {
        return documentRepository.findByStudentAndType(student, type);
    }

    @Override
    public List<Document> findByParentAndType(User parent, String type) {
        return documentRepository.findByParentAndType(parent, type);
    }

    @Override
    @Transactional
    public Document validate(Long id, User validator) {
        return documentRepository.findById(id).map(document -> {
            document.setValidated(true);
            document.setValidatedBy(validator);
            document.setValidatedAt(LocalDateTime.now());
            return documentRepository.save(document);
        }).orElseThrow(() -> new RuntimeException("Document non trouvé"));
    }

    @Override
    public boolean existsByStudentAndType(User student, String type) {
        return documentRepository.existsByStudentAndType(student, type);
    }

    @Override
    public boolean existsByParentAndType(User parent, String type) {
        return documentRepository.existsByParentAndType(parent, type);
    }

    private String generateUniqueFileName(String originalFilename) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return timestamp + extension;
    }
}
```