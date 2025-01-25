package com.digital.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.digital.school.model.Exam;
import com.digital.school.model.Classe;
import com.digital.school.model.Subject;
import com.digital.school.model.enumerated.ExamStatus;
import java.time.LocalDateTime;
import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByClasse(Classe classe);
    List<Exam> findBySubject(Subject subject);
    List<Exam> findByStatus(ExamStatus status);
    List<Exam> findByExamDateBetween(LocalDateTime start, LocalDateTime end);
    List<Exam> findByClasseAndExamDateBetween(Classe classe, LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT e FROM Exam e WHERE e.classe = :classe AND e.examDate > CURRENT_TIMESTAMP ORDER BY e.examDate")
    List<Exam> findUpcomingExamsByClasse(Classe classe);
}