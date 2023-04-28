-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-1');
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-2');
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-3');

INSERT INTO marca (nome, cnpj) VALUES ('Pedigree', '31.634.674/0001-71');
INSERT INTO marca (nome, cnpj) VALUES ('GoldeN', '07.284.054/0001-75');
INSERT INTO marca (nome, cnpj) VALUES ('Whiskas', '12.286.796/0001-70');
INSERT INTO marca (nome, cnpj) VALUES ('GranPlus', '38.850.092/0001-71');

INSERT INTO produto (nome, descricao, preco, estoque, id_marca) VALUES ('Ração Pedigree Nutrição Essencial', 'Para Cães Adultos', 140.00, 20, 1);

INSERT INTO produto (nome, descricao, preco, estoque, id_marca) VALUES ('Ração Golden Fórmula Mini Bits', 'Para Cães Adultos de Porte Pequeno', 110.00, 10, 2);

INSERT INTO produto (nome, descricao, preco, estoque, id_marca) VALUES ('Ração Whiskas', 'Para Gatos Adultos', 180.00, 15, 3);
INSERT INTO produto (nome, descricao, preco, estoque, id_marca) VALUES ('Ração GranPlus Choice', 'Para Gatos Filhotes', 150.00, 12, 4);

-- Cachorro
INSERT INTO racao (quantidadeQuilos, sabor, escolhaAnimal, id) VALUES (15, 'Carne', 2, 1);
INSERT INTO racao (quantidadeQuilos, sabor, escolhaAnimal, id) VALUES (10, 'Frango e Carne', 2, 2);

-- Gato
INSERT INTO racao (quantidadeQuilos, sabor, escolhaAnimal, id) VALUES (10, 'Carne', 1, 3);
INSERT INTO racao (quantidadeQuilos, sabor, escolhaAnimal, id) VALUES (12, 'Frango', 1, 4);

INSERT INTO estado (nome, sigla) VALUES ('Acre', 'AC');
INSERT INTO estado (nome, sigla) VALUES ('Amazonas', 'AM');
INSERT INTO estado (nome, sigla) VALUES ('Pará', 'PA');
INSERT INTO estado (nome, sigla) VALUES ('Goiás', 'GO');
INSERT INTO estado (nome, sigla) VALUES ('Tocantins', 'TO');

INSERT INTO municipio (nome, id_estado) VALUES ('Manaus', 2);
INSERT INTO municipio (nome, id_estado) VALUES ('Palmas', 5);
INSERT INTO municipio (nome, id_estado) VALUES ('Guaraí', 5);
INSERT INTO municipio (nome, id_estado) VALUES ('Belém', 3);

INSERT INTO endereco (logradouro, bairro, numero, cep, id_municipio) VALUES ('alameda 12', 'Quadra 708 Sul', 'lote 10', '77082-012', 2);
INSERT INTO endereco (logradouro, bairro, numero, cep, id_municipio) VALUES ('avenida Bernado Sayão', 'Setor Aeroporto', 'número 3564', '77700-001', 3);
INSERT INTO endereco (logradouro, bairro, numero, cep, id_municipio) VALUES ('rua Piauí', 'Quadra 301 Norte', 'numero 102', '77030-030', 1);

INSERT INTO telefone (codigoarea, numero) VALUES ('011', '98456-7812');
INSERT INTO telefone (codigoarea, numero) VALUES ('061', '99901-5842');
INSERT INTO telefone (codigoarea, numero) VALUES ('061', '99933-0572');
INSERT INTO telefone (codigoarea, numero) VALUES ('063', '99933-0572');
INSERT INTO telefone (codigoarea, numero) VALUES ('078', '98203-3301');

INSERT INTO usuario (nome, email, senha, cpf, id_endereco, id_telefone_principal, id_telefone_opcional)
            VALUES ('João Aguiar', 'joao_aguia@gmail.com', 'joao1234', '09112332145',
                    1, 2, 1);

INSERT INTO usuario (nome, email, senha, cpf, id_endereco, id_telefone_principal)
            VALUES ('Maria Fernanda', 'mariaF@gmail.com', 'senha1234', '89114182345',
                    3, 3);

INSERT INTO usuario (nome, email, senha, cpf, id_endereco, id_telefone_principal, id_telefone_opcional)
            VALUES ('Paulo Vitor', 'paulo_gaymer@gmail.com', 'pa1000ulo', '19429301284',
                    2, 4, 5);

INSERT INTO avaliacao (comentario, data, estrela, id_produto, id_usuario)
                VALUES ('Gostei demais', '2023-02-15', 5, 4, 2);
INSERT INTO avaliacao (comentario, data, estrela, id_produto, id_usuario)
                VALUES ('Muito bom', '2022-11-09', 5, 1, 2);
INSERT INTO avaliacao (data, estrela, id_produto, id_usuario) VALUES ('2023-02-08', 1, 2, 3);
INSERT INTO avaliacao (comentario, data, estrela, id_produto, id_usuario)
                VALUES ('Ruim', '2023-05-09', 1, 2, 1);