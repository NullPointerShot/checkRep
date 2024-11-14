CREATE TABLE Profile(
                      id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                      name VARCHAR(255) NOT NULL,
                      password VARCHAR(255) NOT NULL,
                      role_id UUID REFERENCES Role(id)
);