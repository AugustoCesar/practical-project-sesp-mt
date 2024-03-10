INSERT INTO person (name, rg, cpf, birth_date, phone_number, mother_name, father_name, created_at, updated_at)
VALUES ('Alex Silva', '123456789', '84934669051', '1991-01-01', '65999999999', 'Maria Clementina', 'Joao da Silva', TIMESTAMP WITH TIME ZONE '2024-03-09T12:00:00.123456Z', null);

INSERT INTO address (public_place, neighborhood, number, city, state, postal_code, person_id)
VALUES ('Avenida Afonso Camargo', 'Cristo Rei', '777', 'Curitiba', 'PR', '80050-000', 1);
