package com.digital.school.config.school;

import com.digital.school.model.School;
import com.digital.school.repository.SchoolRepository;
import com.digital.school.service.SchoolService;
import com.digital.school.service.impl.LevelServiceImpl;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.hibernate.Filter;
import org.hibernate.Session;

@ControllerAdvice
public class GlobalModelAttributeAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalModelAttributeAdvice.class);

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SchoolRepository schoolRepository;

    @ModelAttribute("school")
    public School addSchoolToModel() {
        // La méthode getCurrentSchool() doit appliquer le filtre global et retourner l'école correspondant à l'ID de la session
        return getCurrentSchool();
    }


       // Active le filtre global pour limiter la requête à l'école courante
       private School getCurrentSchool() {
        enableSchoolFilter();
        Long currentSchoolId = getCurrentSchoolId();
        return schoolRepository.findById(currentSchoolId)
                .orElseThrow(() -> new RuntimeException("Aucune école trouvée pour l'ID : " + currentSchoolId));
    }

    private void enableSchoolFilter() {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("schoolFilter").setParameter("schoolId", getCurrentSchoolId());
        LOGGER.info("schoolFilter activated with schoolId: {}", getCurrentSchoolId());
    }

    private Long getCurrentSchoolId() {
        // Vous pouvez adapter cette méthode pour obtenir dynamiquement l'ID de l'école (ex : via le contexte de sécurité)
        return 261L;
    }
}

