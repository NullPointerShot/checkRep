CREATE TABLE Recruiter (
                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                           profile_id UUID UNIQUE REFERENCES Profile(id),
                           company_id UUID REFERENCES Company(id)
);