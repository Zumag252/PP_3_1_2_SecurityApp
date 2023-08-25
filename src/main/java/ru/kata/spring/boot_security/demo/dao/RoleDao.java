package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RoleDao {
    void saveRole(List<Role> role);
    List<Role> getRoles();
    void deleteRole(Long id);
    Role findByName(String roleName);
}
