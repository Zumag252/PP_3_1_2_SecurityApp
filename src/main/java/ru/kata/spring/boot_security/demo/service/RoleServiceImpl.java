package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.Collection;
import java.util.List;
import java.util.Set;



@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {
   private final RoleDao roleDao;
   @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional
    public void saveRole(List<Role> role) {
        roleDao.saveRole(role);
    }

    public List<Role> getRoles() {
        return roleDao.getRoles();
    }

    public void deleteRole(Long id) {
       roleDao.deleteRole(id);
    }

    @Override
    public Role findByName(String roleName) {
        return roleDao.findByName(roleName);
    }
}
