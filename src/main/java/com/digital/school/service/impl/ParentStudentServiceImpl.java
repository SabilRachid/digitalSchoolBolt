package com.digital.school.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.digital.school.model.ParentStudent;
import com.digital.school.model.User;
import com.digital.school.repository.ParentStudentRepository;
import com.digital.school.service.ParentStudentService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ParentStudentServiceImpl implements ParentStudentService {

    @Autowired
    private ParentStudentRepository parentStudentRepository;

    @Override
    public List<ParentStudent> findAll() {
        return parentStudentRepository.findAll();
    }

    @Override
    public Optional<ParentStudent> findById(Long id) {
        return parentStudentRepository.findById(id);
    }

    @Override
    @Transactional
    public ParentStudent save(ParentStudent association) {
        return parentStudentRepository.save(association);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        parentStudentRepository.deleteById(id);
    }

    @Override
    public List<ParentStudent> findByParent(User parent) {
        return parentStudentRepository.findByParent(parent);
    }

    @Override
    public List<ParentStudent> findByStudent(User student) {
        return parentStudentRepository.findByStudent(student);
    }

    @Override
    public List<ParentStudent> findByRelationship(String relationship) {
        return parentStudentRepository.findByRelationship(relationship);
    }

    @Override
    public List<ParentStudent> findPrimaryContacts() {
        return parentStudentRepository.findByPrimaryContactTrue();
    }

    @Override
    public Optional<ParentStudent> findByParentAndStudent(User parent, User student) {
        return parentStudentRepository.findByParentAndStudent(parent, student);
    }

    @Override
    public List<ParentStudent> findEmergencyContactsByStudent(User student) {
        return parentStudentRepository.findEmergencyContactsByStudent(student);
    }

    @Override
    public List<ParentStudent> findValidatedAssociationsByParent(User parent) {
        return parentStudentRepository.findValidatedAssociationsByParent(parent);
    }

    @Override
    public boolean existsByParentAndStudent(User parent, User student) {
        return parentStudentRepository.existsByParentAndStudent(parent, student);
    }

    @Override
    @Transactional
    public ParentStudent validate(Long id, User validator) {
        return parentStudentRepository.findById(id).map(association -> {
            association.setValidated(true);
            association.setValidatedBy(validator);
            association.setValidatedAt(LocalDateTime.now());
            return parentStudentRepository.save(association);
        }).orElseThrow(() -> new RuntimeException("Association non trouvée"));
    }

    @Override
    @Transactional
    public ParentStudent setPrimaryContact(Long id, boolean isPrimary) {
        return parentStudentRepository.findById(id).map(association -> {
            association.setPrimaryContact(isPrimary);
            return parentStudentRepository.save(association);
        }).orElseThrow(() -> new RuntimeException("Association non trouvée"));
    }
}