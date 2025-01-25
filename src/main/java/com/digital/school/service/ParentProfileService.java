```java
package com.digital.school.service;

import com.digital.school.model.ParentProfile;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ParentProfileService {
    List<Map<String, Object>> findAllAsMap();
    Optional<ParentProfile> findById(Long id);
    ParentProfile save(ParentProfile profile);
    void deleteById(Long id);
    List<ParentProfile> findByContactMethod(String contactMethod);
}
```