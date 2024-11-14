package com.senla.main.repository;

import com.senla.main.dao.AbstractDao;
import com.senla.main.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class RoleRepository extends AbstractDao<Role, UUID> {

    public RoleRepository() {
        super(Role.class);
    }


    public List<Role> findByName(String name) {
        String jpql = "SELECT r FROM Role r WHERE r.name LIKE :name";
        return entityManager.createQuery(jpql, Role.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }
}
