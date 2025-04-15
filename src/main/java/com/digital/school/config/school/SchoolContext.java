package com.digital.school.config.school;

import org.springframework.stereotype.Component;

@Component
public class SchoolContext {
    private static final ThreadLocal<Long> currentSchoolId = new ThreadLocal<>();

    public void setSchoolId(Long id) {
        currentSchoolId.set(id);
    }

    public Long getSchoolId() {
        return currentSchoolId.get();
    }

    public void clear() {
        currentSchoolId.remove();
    }
}

