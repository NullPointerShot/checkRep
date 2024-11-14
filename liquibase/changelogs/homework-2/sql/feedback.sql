CREATE TABLE Feedback (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          candidate_id UUID REFERENCES Candidate(id),
                          recruiter_id UUID REFERENCES Recruiter(id),
                          vacancy_id UUID REFERENCES Vacancy(id),
                          date DATE,
                          feedback TEXT,
                          status VARCHAR(20)
);