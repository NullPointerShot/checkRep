package com.senla.main.repository;

import com.senla.main.dao.AbstractDao;
import com.senla.main.model.Profile;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ProfileRepository extends AbstractDao<Profile, UUID> {

    public ProfileRepository() {
        super(Profile.class);
    }

}
