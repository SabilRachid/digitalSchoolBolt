```java
package com.digital.school.service;

import com.digital.school.model.StudentProfile;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StudentProfileService {
    List<Map<String, Object>> findAllAsMap();
    Optional<StudentProfile> findById(Long id);
    StudentProfile save(StudentProfile profile);
    void deleteById(Long id);
    List<StudentProfile> findBySpecialNeeds(boolean hasSpecialNeeds);
    List<StudentProfile> findByClass(Long classId);
}
```