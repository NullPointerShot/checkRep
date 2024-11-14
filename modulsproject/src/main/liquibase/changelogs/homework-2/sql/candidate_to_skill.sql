CREATE TABLE Candidate_to_skill (
                                    candidate_id UUID REFERENCES Candidate(id),
                                    skill_id UUID REFERENCES Skill(id),
                                    PRIMARY KEY (candidate_id, skill_id)
);