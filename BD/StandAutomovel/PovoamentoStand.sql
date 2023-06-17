-- Base de dados a ser povoada
USE stand;
SET SQL_SAFE_UPDATES = 1;
-- Povoamento da tabela Cliente
-- DESC Cliente;
-- DELETE FROM Cliente;
-- SELECT * FROM Cliente;
INSERT INTO Cliente
	(ID, Nome, Sexo, DataNascimento, Rua, Distrito, Localidade, Codigo_Postal, Email, Contribuinte, ValorCompras, Dívida)
    VALUES
    ('1', 'Joao Antunes', 'Masculino', '2000-05-01' , 'Rua do Adado', 'Viana', 'Lanheses', '4990-444', 'Joaoa46@gmail.com', '914923592', '1200000', '0'),
    ('2', 'Tomas Antunes', 'Masculino', '2001-11-25', 'Atarao Santo', 'Lisboa', 'Belem', '4250-525', 'tomassa@gmail.com', '93869195', '180000', '0'),
    ('3', 'Joana Matraz', 'Feminino', '1998-08-12', 'Rua Toral', 'Braga', 'Braga', '4520-550', 'joMatraz@gmail.com', '934863933', '60000', '0'),
    ('4', 'Afonso Goter', 'Masculino', '1990-01-20', 'Rua da Mata', 'Aveiro', 'Aveiro', '4555-222', 'GotAfonso@sapo.pt', '999888666', '0','0');

-- Povoamento da tabela Telefone
-- DESC Telefone;
-- DELETE FROM Telefone;
-- SELECT * FROM Telefone;
INSERT INTO Telefone
    (Cliente_ID, Telefone)
	VALUES 
    ('1', '+351 111 222 333'),
    ('2', '+351 444 555 666'),
    ('3', '+351 777 888 999'),
    ('4', '+351 000 111 222');
    
-- Povoamento da tabela Funcionario
-- DESC Funcionario
-- DELETE FROM Funcionario
-- SELECT * FROM Funcionario;
INSERT INTO Funcionario
	(ID, Nome, DataNascimento, Funcao, Sexo, Salário, Telefone, DataContratacao, NVendas, NAlugueres)
    VALUES
    ('1', 'Jose Benifacio', '1960-05-01', 'Patrao', 'Masculino', '5000', '919955995', '1990-04-01', '0', '0'),
    ('2', 'Tomé Adante', '1990-03-02', 'Tecnico de Venda', 'Masculino', '2500', '915000444', '2020-06-01', '1', '0'),
    ('3', 'Andrade Tuco', '1988-06-01', 'Tecnico de Venda', 'Masculino', '2600', '953868175', '2019-02-28', '1', '2'),
    ('4', 'Marta Luisa', '1985-11-20', 'Gestor Financeiro', 'Feminino', '3000', '968395929', '2017-09-15', '0', '0'),
    ('5', 'Ana Catarina', '1992-01-20', 'Recepcionista', 'Feminino', '1200', '89394995', '2020-11-04', '0', '0'),
    ('6', 'Afonso Lopes', '1990-05-20', 'Tecnico de Venda', 'Masculino', '2400', '919944339', '2021-04-20', '0', '0');

-- Povoamento da tabela carro
-- DESC Carro;
-- DELETE FROM Carro;
-- SELECT * FROM Carro;
INSERT INTO Carro
	(ID, Destinacao, Marca, Modelo, Kilometragem, AnoFabrico, Preco, Integridade, Estado)
    VALUES
    ('1', 'Venda', 'Mercedes', 'GLS', '0', '2010', '80000', 'NOVO', 'DISPONIVEL'),
    ('2', 'Venda', 'Audi', 'A5', '100000', '2015', '50000', 'USADO', 'DISPONIVEL'),
    ('3', 'Aluguer', 'Ferrari', 'Spider', '32500', '2012', '1000', 'USADO', 'DISPONIVEL'),
    ('4', 'Aluguer', 'Mercedes', 'Classe G', '90000', '2012', '800', 'USADO', 'DISPONIVEL'),
    ('5', 'Venda', 'Lamborghini', 'Huracan', '0', '2018', '1200000', 'NOVO', 'NAODISPONIVEL'),
    ('6', 'Venda', 'Nissan', 'GTR', '54000', '2011', '54000', 'USADO', 'DISPONIVEL'),
    ('7', 'Venda', 'Lamborghini', 'Urus', '49000', '2020', '270000', 'USADO', 'DISPONIVEL'),
    ('8', 'Aluguer', 'MC Laren', 'MP4', '40000', '2012', '140000', 'USADO', 'DISPONIVEL'),
    ('9', 'Venda', 'Wiesmann', 'GT MF4', '65000', '2008', '180000', 'USADO', 'NAODISPONIVEL'),
    ('10', 'Venda', 'MC Laren', '720s', '0', '2017', '300000', 'NOVO', 'DISPONIVEL');
    

-- Povoamento da tabela Compra
-- DESC Compra;
-- DELETE FROM Compra;
-- SELECT * FROM Compra;
INSERT INTO Compra
	(Numero, Cliente_ID, Funcionario_ID, PlanoPagamento, DataEmissao, Contribuinte, DataPagamento, Valor)
    VALUES
    ('1', '1', '2', 'A pronto', '2023-01-10', '914923592', '2022-01-10', '1200000'),
    ('2', '2', '3', 'A pronto', '2022-10-23', '959595959', '2023-10-23', '180000');

-- Povoamento da tabela VendaInclui
-- DESC VendaInclui;
-- DELETE FROM VendaInclui;
-- SELECT * FROM VendaInclui;
INSERT INTO VendaInclui
	(Carro_ID, Compra_Numero, Preco, Imposto)
    VALUES
	('5', '1', '1200000', '0'),
    ('9', '2', '180000', '0');
    
-- Povoamento da tabela Aluguer
-- DESC Aluguer;
-- DELETE FROM Aluguer;
-- SELECT * FROM Aluguer;
INSERT INTO Aluguer
	(Numero, Funcionario_ID, Cliente_ID, PlanoPagamento, DataEmissao, Contribuinte, DataPagamento, Valor, FotoEstado, DadosSeguro, PeriodoAluguer)
    VALUES
    ('1', '3', '3', 'A pronto', '2023-04-10', '934863933', '2023-04-10', '3000', 'BOMESTADO', '1010101010100', '3'),
    ('2', '3', '3', 'A pronto', '2023-04-10', '934863933', '2023-04-10', '3000', 'BOMESTADO', '1100101001010', '3'); 

-- Povoamento da tabela AluguerInclui
-- DESC AluguerInclui;
-- DELETE FROM AluguerInclui;
-- SELECT * FROM AluguerInclui;
INSERT INTO AluguerInclui
	(Aluguer_Numero, Carro_ID, Preco, Imposto)
    VALUES
	('1', '3', '3000', '0'),
    ('2', '8', '3000', '0');
    
-- Povoamento da tabela EfetuaPedido
-- DESC EfetuaPedido;
-- DELETE FROM EfetuaPedido;
-- SELECT * FROM EfetuaPedido;
INSERT INTO EfetuaPedido
	(ID, Data_pedido, Compra_Numero, Aluguer_Numero, Funcionario_ID, Cliente_ID)
    VALUES
    ('1', '2022-01-10', '1', null, '2', '1'),
    ('2', '2023-04-10', null, '1', '3', '3'),
    ('3', '2023-04-10', null, '2', '3', '3'),
    ('4', '2023-10-23', '2', null, '2', '3');
    
    