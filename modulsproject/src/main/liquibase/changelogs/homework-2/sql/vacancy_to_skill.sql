CREATE TABLE Vacancy_to_skill (
                                  vacancy_id UUID REFERENCES Vacancy(id),
                                  skill_id UUID REFERENCES Skill(id),
                                  PRIMARY KEY (vacancy_id, skill_id)
);