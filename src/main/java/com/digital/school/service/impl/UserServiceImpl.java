package com.digital.school.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.digital.school.model.User;
import com.digital.school.model.Role;
import com.digital.school.model.enumerated.RoleName;
import com.digital.school.repository.UserRepository;
import com.digital.school.repository.RoleRepository;
import com.digital.school.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<User> findByRole(RoleName roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        if (role.isPresent()) {
            return userRepository.findAll().stream()
                .filter(user -> user.getRoles().contains(role.get()))
                .collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    public Map<String, Object> getAdminDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        // TODO: Implement admin dashboard statistics
        return stats;
    }

    @Override
    public Map<String, Object> getProfessorDashboardStats(User professor) {
        Map<String, Object> stats = new HashMap<>();
        // TODO: Implement professor dashboard statistics
        return stats;
    }

    @Override
    public Map<String, Object> getStudentDashboardStats(User student) {
        Map<String, Object> stats = new HashMap<>();
        // TODO: Implement student dashboard statistics
        return stats;
    }
}