-- Insert 1
INSERT INTO person (name, rg, cpf, birth_date, phone_number, mother_name, father_name, created_at, updated_at)
VALUES ('Alex Silva', '123456789', '84934669051', '1991-01-01', '65999999999', 'Maria Clementina', 'Joao da Silva', TIMESTAMP WITH TIME ZONE '2024-03-09T12:00:00.123456Z', null);

INSERT INTO address (public_place, neighborhood, number, city, state, postal_code, person_id)
VALUES ('Avenida Afonso Camargo', 'Cristo Rei', '777', 'Curitiba', 'PR', '80050-000', 1);

-- Insert 2
INSERT INTO person (name, rg, cpf, birth_date, phone_number, mother_name, father_name, created_at, updated_at)
VALUES ('Bruno Oliveira', '123456780', '06292395090', '1990-02-02', '65999999998', 'Ana Carolina', 'Jose Oliveira', TIMESTAMP WITH TIME ZONE '2024-03-09T12:00:00.123456Z', null);

INSERT INTO address (public_place, neighborhood, number, city, state, postal_code, person_id)
VALUES ('Rua das Flores', 'Centro', '123', 'Sao Paulo', 'SP', '01010-000', 2);

-- Insert 3
INSERT INTO person (name, rg, cpf, birth_date, phone_number, mother_name, father_name, created_at, updated_at)
VALUES ('Carla Santos', '123456781', '00519436032', '1985-03-03', '65999999997', 'Rita de Cassia', 'Marcos Santos', TIMESTAMP WITH TIME ZONE '2024-03-09T12:00:00.123456Z', null);

INSERT INTO address (public_place, neighborhood, number, city, state, postal_code, person_id)
VALUES ('Rua das Palmeiras', 'Jardim Botanico', '456', 'Rio de Janeiro', 'RJ', '22471-000', 3);

-- Insert 4
INSERT INTO person (name, rg, cpf, birth_date, phone_number, mother_name, father_name, created_at, updated_at)
VALUES ('Daniel Ferreira', '123456782', '87740769061', '1982-04-04', '65999999996', 'Amanda Silva', 'Fernando Ferreira', TIMESTAMP WITH TIME ZONE '2024-03-09T12:00:00.123456Z', null);

INSERT INTO address (public_place, neighborhood, number, city, state, postal_code, person_id)
VALUES ('Rua dos Girassois', 'Jardim das Flores', '789', 'Porto Alegre', 'RS', '90220-000', 4);

-- Insert 5
INSERT INTO person (name, rg, cpf, birth_date, phone_number, mother_name, father_name, created_at, updated_at)
VALUES ('Eduarda Lima', '123456783', '93999887087', '1978-05-05', '65999999995', 'Patricia Lima', 'Carlos Silva', TIMESTAMP WITH TIME ZONE '2024-03-09T12:00:00.123456Z', null);

INSERT INTO address (public_place, neighborhood, number, city, state, postal_code, person_id)
VALUES ('Avenida das Acacias', 'Centro', '101', 'Belo Horizonte', 'MG', '30190-000', 5);