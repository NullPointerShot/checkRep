CREATE TABLE Role(
                      id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                      name VARCHAR(50) NOT NULL
);