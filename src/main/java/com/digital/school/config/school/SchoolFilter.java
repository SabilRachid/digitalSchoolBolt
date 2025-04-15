package com.digital.school.config.school;

import com.digital.school.controller.rest.admin.AdminClasseRestController;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SchoolFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolFilter.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SchoolContext schoolContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Récupère le schoolId depuis le contexte de sécurité ou un autre mécanisme
        Long schoolId = extractSchoolIdFromUser(request); // à implémenter selon ta logique
        schoolContext.setSchoolId(schoolId);

        // Activer le filtre Hibernate pour la session courante
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("schoolFilter");
        filter.setParameter("schoolId", schoolId);
        LOGGER.info("SchoolFilter activated with schoolId: {} | Session hash: {}", schoolId, session.hashCode());


        try {
            filterChain.doFilter(request, response);
        } finally {
            // Nettoyer le contexte pour éviter toute fuite entre requêtes
            schoolContext.clear();
            session.disableFilter("schoolFilter");
        }
    }

    private Long extractSchoolIdFromUser(HttpServletRequest request) {
        // Exemple : extraire l'ID d'école de l'utilisateur authentifié
        // Ceci dépend de ton implémentation de la sécurité (ex : via SecurityContextHolder)
        // Retourne ici l'ID de l'école ou une valeur par défaut.
        Long extractedId = 261L;
        LOGGER.info("Extracting schoolId from request, returning: {}", extractedId);
        return extractedId; // Exemple simplifié
    }
}
