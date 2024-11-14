CREATE TABLE Vacancy (
                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                         title VARCHAR(255) NOT NULL,
                         company_id UUID REFERENCES Company(id),
                         salary INTEGER
);