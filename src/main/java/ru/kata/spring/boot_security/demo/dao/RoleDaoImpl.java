package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;
import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoleDaoImpl implements RoleDao {
    private final EntityManager entityManager;
    @Autowired
    public RoleDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void saveRole(List<Role> role) {
        entityManager.persist(role);
    }

    @Override
    public List<Role> getRoles() {
        return entityManager.createQuery("from Role", Role.class)
                .getResultStream().collect(Collectors.toList());
    }

    @Override
    public void deleteRole(Long id) {
        Role role = entityManager.find(Role.class, id);
        if (role == null) {
            throw new NullPointerException("Роль с таким id не найдена");
        }
        entityManager.remove(role);
    }

    @Override
    public Role findByName(String roleName) {
        try {
            return entityManager.createQuery("from Role where name=:roleName", Role.class).setParameter("roleName", roleName).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
