CREATE TABLE Candidate (
                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                           profile_id UUID UNIQUE REFERENCES Profile(id),
                           experience VARCHAR(255)
);