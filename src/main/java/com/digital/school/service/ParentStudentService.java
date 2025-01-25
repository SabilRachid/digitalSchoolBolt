```java
package com.digital.school.service;

import com.digital.school.model.ParentStudent;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ParentStudentService {
    List<Map<String, Object>> findAllAsMap();
    Optional<ParentStudent> findById(Long id);
    ParentStudent save(ParentStudent association);
    void deleteById(Long id);
    ParentStudent validate(Long id);
    List<ParentStudent> findByParent(Long parentId);
    List<ParentStudent> findByStudent(Long studentId);
    List<ParentStudent> findByValidationStatus(boolean validated);
}
```