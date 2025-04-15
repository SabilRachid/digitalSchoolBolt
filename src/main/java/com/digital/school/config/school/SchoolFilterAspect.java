package com.digital.school.config.school;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SchoolFilterAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolFilterAspect.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SchoolContext schoolContext;

    // Pointcut ciblant toutes les méthodes des classes de service.
    @Pointcut("within(com.digital.school.service..*)")
    public void serviceMethods() { }

    // Avant toute exécution de méthode dans le package des services,
    // active le filtre "schoolFilter" avec l'ID d'école courant.
    @Before("serviceMethods()")
    public void applySchoolFilter(JoinPoint joinPoint) {
        Long schoolId = schoolContext.getSchoolId();
        if (schoolId == null) {
            // Valeur par défaut si aucun ID d'école n'est défini dans le contexte.
            schoolId = 261L;
        }
        Session session = entityManager.unwrap(Session.class);
        if (session.getEnabledFilter("schoolFilter") == null) {
            Filter filter = session.enableFilter("schoolFilter");
            filter.setParameter("schoolId", schoolId);
            LOGGER.info("SchoolFilter activé avec schoolId: {} dans la méthode: {}",
                    schoolId, joinPoint.getSignature().toShortString());
        }
    }

    // Après l'exécution d'une méthode dans le package des services,
    // désactive le filtre et nettoie le contexte pour éviter toute fuite d'information.
    @After("serviceMethods()")
    public void disableSchoolFilter(JoinPoint joinPoint) {
        Session session = entityManager.unwrap(Session.class);
        if (session.getEnabledFilter("schoolFilter") != null) {
            session.disableFilter("schoolFilter");
            LOGGER.info("SchoolFilter désactivé après la méthode: {}",
                    joinPoint.getSignature().toShortString());
        }
        // Nettoyage du contexte de l'école pour éviter toute contamination entre les requêtes.
        schoolContext.clear();
    }
}
