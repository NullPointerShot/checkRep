CREATE TABLE Skill (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       name VARCHAR(255) NOT NULL,
                       parent_id UUID REFERENCES Skill(id)
);