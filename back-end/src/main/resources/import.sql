-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-1');
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-2');
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-3');

INSERT INTO marca (nome, cnpj) VALUES ('Pedigree', '31.634.674/0001-71');
INSERT INTO marca (nome, cnpj) VALUES ('GoldeN', '07.284.054/0001-75');
INSERT INTO marca (nome, cnpj) VALUES ('Whiskas', '12.286.796/0001-70');
INSERT INTO marca (nome, cnpj) VALUES ('GranPlus', '38.850.092/0001-71');
INSERT INTO marca (nome, cnpj) VALUES ('PetLovers', '12.345.678/0001-90');
INSERT INTO marca (nome, cnpj) VALUES ('AnimalNutrition', '98.765.432/0001-21');
INSERT INTO marca (nome, cnpj) VALUES ('HappyPaws', '11.222.333/0001-44');
INSERT INTO marca (nome, cnpj) VALUES ('AquaTech', '55.666.777/0001-88');
INSERT INTO marca (nome, cnpj) VALUES ('HealthPets', '77.888.999/0001-55');
INSERT INTO marca (nome, cnpj) VALUES ('FurJoy', '12.345.678/0001-12');
INSERT INTO marca (nome, cnpj) VALUES ('FinnedFriends', '11.111.111/0001-11');
INSERT INTO marca (nome, cnpj) VALUES ('BeautyTails', '99.999.999/0001-99');
INSERT INTO marca (nome, cnpj) VALUES ('FeatheredBeauties', '22.333.444/0001-22');

INSERT INTO categoria (nome, descricao) VALUES ('Racao', 'Rações para todos os tipos de Pets');
INSERT INTO categoria (nome, descricao) VALUES ('Brinquedos para Gatos', 'Diversos brinquedos para entretenimento de gatos');
INSERT INTO categoria (nome, descricao) VALUES ('Acessórios para Cães', 'Acessórios variados para cães, como coleiras e guias');
INSERT INTO categoria (nome, descricao) VALUES ('Produtos de Higiene', 'Itens para manter a higiene dos animais');
INSERT INTO categoria (nome, descricao) VALUES ('Aquários', 'Aquários e acessórios para peixes');
INSERT INTO categoria (nome, descricao) VALUES ('Medicamentos para Animais', 'Medicamentos para tratar diversas condições de saúde');
INSERT INTO categoria (nome, descricao) VALUES ('Gaiolas para Pássaros', 'Gaiolas e acessórios para pássaros domésticos');
INSERT INTO categoria (nome, descricao) VALUES ('Camas para Pets', 'Camas confortáveis para cães e gatos');
INSERT INTO categoria (nome, descricao) VALUES ('Alimentos para Peixes', 'Alimentos específicos para peixes de aquário');
INSERT INTO categoria (nome, descricao) VALUES ('Produtos de Beleza para Animais', 'Produtos para deixar os pets mais bonitos e bem cuidados');


INSERT INTO fornecedor (nome, email) VALUES ('Bontrato', 'bontrato@gmail.com');
INSERT INTO fornecedor (nome, email) VALUES ('PetVet', 'contato@petvet.com');
INSERT INTO fornecedor (nome, email) VALUES ('AnimalCare', 'animalcare@provedor.com');
INSERT INTO fornecedor (nome, email) VALUES ('PetFood', 'petfood@fornecedora.com');
INSERT INTO fornecedor (nome, email) VALUES ('PetToys', 'info@pettoys.com');
INSERT INTO fornecedor (nome, email) VALUES ('AquaWorld', 'aquaworld@fornecimento.com');
INSERT INTO fornecedor (nome, email) VALUES ('HealthPets', 'healthpets@providencia.com');
INSERT INTO fornecedor (nome, email) VALUES ('FurryFriends', 'furryfriends@contato.com');
INSERT INTO fornecedor (nome, email) VALUES ('FishWorld', 'fishworld@fornecimento.com');
INSERT INTO fornecedor (nome, email) VALUES ('BeautyPets', 'beautypets@provedora.com');

INSERT INTO produto (nome, descricao, preco, estoque, id_marca, id_fornecedor, id_categoria, nomeImagem) VALUES ('Ração Pedigree Nutrição Essencial', 'Para Cães Adultos', 140.00, 30, 1,1,1, 'pedigree.png');
INSERT INTO produto (nome, descricao, preco, estoque, id_marca, id_fornecedor, id_categoria, nomeImagem) VALUES ('Ração Golden Fórmula Mini Bits', 'Para Cães Adultos de Porte Pequeno', 110.00, 10, 2,1,2, 'golden.png');
INSERT INTO produto (nome, descricao, preco, estoque, id_marca, id_fornecedor, id_categoria, nomeImagem) VALUES ('Ração Whiskas', 'Para Gatos Adultos', 180.00, 15, 3,1,1, 'whiskas.png');
INSERT INTO produto (nome, descricao, preco, estoque, id_marca, id_fornecedor, id_categoria, nomeImagem) VALUES ('Ração GranPlus Choice', 'Para Gatos Filhotes', 150.00, 12, 4,1,2, 'granplus.png');

INSERT INTO estado (nome, sigla) VALUES ('Acre', 'AC');
INSERT INTO estado (nome, sigla) VALUES ('Alagoas', 'AL');
INSERT INTO estado (nome, sigla) VALUES ('Amapá', 'AP');
INSERT INTO estado (nome, sigla) VALUES ('Amazonas', 'AM');
INSERT INTO estado (nome, sigla) VALUES ('Bahia', 'BA');
INSERT INTO estado (nome, sigla) VALUES ('Ceará', 'CE');
INSERT INTO estado (nome, sigla) VALUES ('Distrito Federal', 'DF');
INSERT INTO estado (nome, sigla) VALUES ('Espírito Santo', 'ES');
INSERT INTO estado (nome, sigla) VALUES ('Goiás', 'GO');
INSERT INTO estado (nome, sigla) VALUES ('Maranhão', 'MA');
INSERT INTO estado (nome, sigla) VALUES ('Mato Grosso', 'MT');
INSERT INTO estado (nome, sigla) VALUES ('Mato Grosso do Sul', 'MS');
INSERT INTO estado (nome, sigla) VALUES ('Minas Gerais', 'MG');
INSERT INTO estado (nome, sigla) VALUES ('Pará', 'PA');
INSERT INTO estado (nome, sigla) VALUES ('Paraíba', 'PB');
INSERT INTO estado (nome, sigla) VALUES ('Paraná', 'PR');
INSERT INTO estado (nome, sigla) VALUES ('Pernambuco', 'PE');
INSERT INTO estado (nome, sigla) VALUES ('Piauí', 'PI');
INSERT INTO estado (nome, sigla) VALUES ('Rio de Janeiro', 'RJ');
INSERT INTO estado (nome, sigla) VALUES ('Rio Grande do Norte', 'RN');
INSERT INTO estado (nome, sigla) VALUES ('Rio Grande do Sul', 'RS');
INSERT INTO estado (nome, sigla) VALUES ('Rondônia', 'RO');
INSERT INTO estado (nome, sigla) VALUES ('Roraima', 'RR');
INSERT INTO estado (nome, sigla) VALUES ('Santa Catarina', 'SC');
INSERT INTO estado (nome, sigla) VALUES ('São Paulo', 'SP');
INSERT INTO estado (nome, sigla) VALUES ('Sergipe', 'SE');
INSERT INTO estado (nome, sigla) VALUES ('Tocantins', 'TO');

INSERT INTO cidade (nome, id_estado) VALUES ('Rio Branco', 1);
INSERT INTO cidade (nome, id_estado) VALUES ('Maceió', 2);
INSERT INTO cidade (nome, id_estado) VALUES ('Macapá', 3);
INSERT INTO cidade (nome, id_estado) VALUES ('Manaus', 4);
INSERT INTO cidade (nome, id_estado) VALUES ('Salvador', 5);
INSERT INTO cidade (nome, id_estado) VALUES ('Fortaleza', 6);
INSERT INTO cidade (nome, id_estado) VALUES ('Brasília', 7);
INSERT INTO cidade (nome, id_estado) VALUES ('Vitória', 8);
INSERT INTO cidade (nome, id_estado) VALUES ('Goiânia', 9);
INSERT INTO cidade (nome, id_estado) VALUES ('São Luís', 10);
INSERT INTO cidade (nome, id_estado) VALUES ('Cuiabá', 11);
INSERT INTO cidade (nome, id_estado) VALUES ('Campo Grande', 12);
INSERT INTO cidade (nome, id_estado) VALUES ('Belo Horizonte', 13);
INSERT INTO cidade (nome, id_estado) VALUES ('Belém', 14);
INSERT INTO cidade (nome, id_estado) VALUES ('João Pessoa', 15);
INSERT INTO cidade (nome, id_estado) VALUES ('Curitiba', 16);
INSERT INTO cidade (nome, id_estado) VALUES ('Recife', 17);
INSERT INTO cidade (nome, id_estado) VALUES ('Teresina', 18);
INSERT INTO cidade (nome, id_estado) VALUES ('Rio de Janeiro', 19);
INSERT INTO cidade (nome, id_estado) VALUES ('Natal', 20);
INSERT INTO cidade (nome, id_estado) VALUES ('Porto Alegre', 21);
INSERT INTO cidade (nome, id_estado) VALUES ('Porto Velho', 22);
INSERT INTO cidade (nome, id_estado) VALUES ('Boa Vista', 23);
INSERT INTO cidade (nome, id_estado) VALUES ('Florianópolis', 24);
INSERT INTO cidade (nome, id_estado) VALUES ('São Paulo', 25);
INSERT INTO cidade (nome, id_estado) VALUES ('Aracaju', 26);
INSERT INTO cidade (nome, id_estado) VALUES ('Palmas', 27);


INSERT INTO endereco (logradouro, bairro, numero, cep, id_cidade) VALUES ('alameda 12', 'Quadra 708 Sul', 'lote 10', '77082-012', 2);
INSERT INTO endereco (logradouro, bairro, numero, cep, id_cidade) VALUES ('avenida Bernado Sayão', 'Setor Aeroporto', 'número 3564', '77700-001', 3);
INSERT INTO endereco (logradouro, bairro, numero, cep, id_cidade) VALUES ('rua Piauí', 'Quadra 301 Norte', 'numero 102', '77030-030', 1);
INSERT INTO endereco (logradouro, bairro, numero, cep, id_cidade) VALUES ('alameda 08', 'Quadra 1200 Sul', 'numero 092', '77092-839', 3);
INSERT INTO endereco (logradouro, bairro, numero, cep, id_cidade) VALUES ('alameda 13', 'Setor Bueno', 'lote 18', '77903-029', 1);

INSERT INTO pessoa (nome) VALUES ('João Aguiar');
INSERT INTO pessoa (nome) VALUES ('Maria Fernanda');
INSERT INTO pessoa (nome) VALUES ('Paulo Vitor');
INSERT INTO pessoa (nome) VALUES ('Julia Ramos');
INSERT INTO pessoa (nome) VALUES ('Lucas Ferreira');
INSERT INTO pessoa (nome) VALUES ('João Jorilo');

INSERT INTO pessoaFisica (cpf, email, sexo, id) VALUES ('09112332145', 'joao_aguia@gmail.com', 1, 1);
INSERT INTO pessoaFisica (cpf, email, sexo, id) VALUES ('89114182345', 'mariaF@gmail.com', 2, 2);
INSERT INTO pessoaFisica (cpf, email, sexo, id) VALUES ('19429301284', 'paulo_gaymer@gmail.com', 1, 3);
INSERT INTO pessoaFisica (cpf, email, sexo, id) VALUES ('90819287304', 'julia.ra@gmail.com', 2, 4);
INSERT INTO pessoaFisica (cpf, email, sexo, id) VALUES ('92874291092', 'lucas_ferreira@gmail.com', 1, 5);
--INSERT INTO pessoaFisica (email, id) VALUES ('jubiscreisson@outlook.com', 6);

INSERT INTO usuario (id_pessoa_fisica, login, senha, id_endereco) VALUES (1, 'JoaoA', 'ZXChMgzI4VI5Jx+KKCL0AnuRaug9sWorJdV7iCDgWIDNVms7vyhaZeXP+5x26q6uDWKJmyQySZzE8hvoncjgCA==', 1);

INSERT INTO usuario (id_pessoa_fisica, login, senha, id_endereco)
            VALUES (2, 'maria123', 'x6JkviFo/CZc/dYoTsn+KjkyXu9rqbOwZ89vC1horO3B+ZT2N9nhquEvkFxm2WZahBpo5wgui91vSF00c1BYPA==', 3);

INSERT INTO usuario (id_pessoa_fisica, login, senha, id_endereco) VALUES (3, 'PauloVitor', 'EDCT26TOqyKJg1i5rpN/tOkmr8RSjKfPP1qdPhjlj+sA3Wd++oZFkG5YChaMMRndKipiyVxfL12CUYWybBk+aA==', 2);

INSERT INTO usuario (id_pessoa_fisica, login, senha, id_endereco) VALUES (4, 'Juh', '/t7jPylqAlsn/BU03MiTgXI3m7B49BDsA3B8wBgk6dSj2a3G+1hgWJ+TNanb3cm8/iyX/io6DErKm/HPiwP/SA==', 5);

INSERT INTO usuario (id_pessoa_fisica, login, senha, id_endereco)
            VALUES (5, 'LucasFerreira', 'Tv7l6PLjIMgRTG8n32VVbtgHZyUj3L9nbtTz77T96tP52xepJQ25AoczGr8MA89dZ8cOErN3WcuGyOurMPCaOA==', 4);

--INSERT INTO usuario (id_pessoa_fisica, login, senha)
           -- VALUES (6, 'Joao_dos_Isekai', '89ud9FUF967ZPp2GxHJ6ITVrXHnVfA0uf1AsYZ0V0SYuA0OCjSKXEgH72aTLeGBaQr3m7WuVsgWlx76WK/gWuA==');

INSERT INTO perfis (id_usuario, perfil) VALUES (1, 'Admin');
INSERT INTO perfis (id_usuario, perfil) VALUES (1, 'User');
INSERT INTO perfis (id_usuario, perfil) VALUES (2, 'User');
INSERT INTO perfis (id_usuario, perfil) VALUES (3, 'User');
INSERT INTO perfis (id_usuario, perfil) VALUES (4, 'User');
INSERT INTO perfis (id_usuario, perfil) VALUES (5, 'Admin');
--INSERT INTO perfis (id_usuario, perfil) VALUES (6, 'User_Basic');

INSERT INTO telefone (codigoarea, numero, id_usuario) VALUES ('071', '99283-8723',1);
INSERT INTO telefone (codigoarea, numero, id_usuario) VALUES ('012', '99928-0912',1);
INSERT INTO telefone (codigoarea, numero, id_usuario) VALUES ('011', '98456-7812',2);
INSERT INTO telefone (codigoarea, numero, id_usuario) VALUES ('061', '99901-5842',3);
INSERT INTO telefone (codigoarea, numero, id_usuario) VALUES ('061', '99933-0572',4);
INSERT INTO telefone (codigoarea, numero, id_usuario) VALUES ('063', '99933-0572',5);
INSERT INTO telefone (codigoarea, numero, id_usuario) VALUES ('078', '98203-3301',2);
INSERT INTO telefone (codigoarea, numero, id_usuario) VALUES ('092', '98382-0912',4);

INSERT INTO fornecedor (nome,email) VALUES ('ASASAS', 'sadasdasdasd');


INSERT INTO lista_desejo (id_usuario, id_produto) VALUES (1, 3);
INSERT INTO lista_desejo (id_usuario, id_produto) VALUES (2, 3);
INSERT INTO lista_desejo (id_usuario, id_produto) VALUES (1, 1);
INSERT INTO lista_desejo (id_usuario, id_produto) VALUES (1, 2);
INSERT INTO lista_desejo (id_usuario, id_produto) VALUES (4, 1);


INSERT INTO cupomDesconto (codigoCupom, quantidadeDisponivel, porcentagemDesconto)VALUES ('aadawd', 1, 1); 
INSERT INTO cupomDesconto (codigoCupom, quantidadeDisponivel, porcentagemDesconto)VALUES ('getas', 14, 121); 
INSERT INTO cupomDesconto (codigoCupom, quantidadeDisponivel, porcentagemDesconto)VALUES ('baba', 100, 122); 
--INSERT INTO avaliacao (data, estrela, id_produto, id_usuario) VALUES ('2023-01-22', 3, 1, 1);
--INSERT INTO avaliacao (comentario, data, estrela, id_produto, id_usuario)
  --              VALUES ('muito bão', '2022-11-09', 5, 3, 2);
--INSERT INTO avaliacao (data, estrela, id_produto, id_usuario) VALUES ('2023-02-08', 1, 2, 1);
--INSERT INTO avaliacao (data, estrela, id_produto, id_usuario) VALUES ('2023-03-14', 4, 1, 1);
--INSERT INTO avaliacao (data, estrela, id_produto, id_usuario) VALUES ('2022-10-28', 5, 1, 3);

--INSERT INTO pagamento (valor, confirmacaoPagamento, dataConfirmacaoPagamento) VALUES (511, true, '2023-06-10');
--INSERT INTO pagamento (valor, confirmacaoPagamento, dataConfirmacaoPagamento) VALUES (1228.25, true, '2023-06-15');

--INSERT INTO pix (nome, cpf, dataExpiracaoTokenPix, id) VALUES ('Maria Fernanda', '89114182345', '2023-06-11', 1);
--INSERT INTO boletoBancario (id, nome, cpf, dataGeracaoBoleto, dataVencimento)
    --        VALUES (2, 'Maria Fernanda', '89114182345', '2023-06-15', '2023-06-25');

--INSERT INTO compra (dataCompra, totalCompra, ifConcluida, id_endereco, id_pagamento, id_usuario)
   --         VALUES ('2023-06-10', 511, true, 3, 1, 2);

--INSERT INTO compra (dataCompra, totalCompra, ifConcluida, id_endereco, id_pagamento, id_usuario)
  --          VALUES ('2023-06-15', 1228.25, true, 3, 2, 2);

--INSERT INTO itemCompra (id_compra, quantidade, precoUnitario, id_produto) VALUES (1, 10, 34.95, 1);
--INSERT INTO itemCompra (id_compra, quantidade, precoUnitario, id_produto) VALUES (1, 5, 32.30, 2);
--INSERT INTO itemCompra (id_compra, quantidade, precoUnitario, id_produto) VALUES (2, 15, 34.95, 1);
--INSERT INTO itemCompra (id_compra, quantidade, precoUnitario, id_produto) VALUES (2, 2, 29.00, 3);
--INSERT INTO itemCompra (id_compra, quantidade, precoUnitario, id_produto) VALUES (2, 20, 32.30, 2);
