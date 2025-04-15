package com.digital.school.config.school;

import com.digital.school.model.School;
import com.digital.school.service.SchoolService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SchoolInjectionAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolInjectionAspect.class);

    @Autowired
    private SchoolService schoolService;

    // Pointcut pour intercepter toutes les méthodes du package de service,
    // en excluant celles de SchoolServiceImpl pour éviter les appels récursifs
    @Pointcut("execution(* com.digital.school.service..*(..)) && !within(com.digital.school.service.impl.SchoolServiceImpl)")
    public void serviceMethodsExceptSchoolService() { }

    @Before("serviceMethodsExceptSchoolService()")
    public void injectSchoolIntoEntities(JoinPoint joinPoint) {
        School currentSchool = schoolService.getCurrentSchool(); // Ceci n'est plus intercepté en boucle grâce à l'exclusion
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof SchoolAware) {
                SchoolAware entity = (SchoolAware) arg;
                if (entity.getSchool() == null) {
                    entity.setSchool(currentSchool);
                    LOGGER.info("Injection de l'école (id={}) dans l'entité {}",
                            currentSchool.getId(), entity.getClass().getSimpleName());
                }
            }
        }
    }
}
