CREATE TABLE Job_offer (
                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                           candidate_id UUID REFERENCES Candidate(id),
                           vacancy_id UUID REFERENCES Vacancy(id),
                           salary INTEGER,
                           status VARCHAR(20)
);