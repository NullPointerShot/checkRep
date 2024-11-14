package com.senla.main.repository;

import com.senla.main.dao.AbstractDao;
import com.senla.main.model.JobOffer;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JobOfferRepository extends AbstractDao<JobOffer, UUID> {

    public JobOfferRepository() {
        super(JobOffer.class);
    }


}
