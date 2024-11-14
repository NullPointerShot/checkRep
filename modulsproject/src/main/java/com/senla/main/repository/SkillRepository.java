package com.senla.main.repository;

import com.senla.main.dao.AbstractDao;
import com.senla.main.model.Skill;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SkillRepository extends AbstractDao<Skill, UUID> {

    public SkillRepository() {
        super(Skill.class);
    }


    public List<Skill> findByName(String name) {
        String jpql = "SELECT s FROM Skill s WHERE s.name = :name";
        return entityManager.createQuery(jpql, Skill.class)
                .setParameter("name", name)
                .getResultList();
    }
}
