USE Stand;
-- ----------------------------
-- EXECUÇÃO DE QUERIES
-- ----------------------------

-- INFORMAÇOES SOBRE OS CLIENTES
SELECT nome,id,telefone,email,valorcompras FROM Cliente c
	INNER JOIN telefone t ON t.Cliente_ID = c.id
	ORDER BY nome ASC;

-- 3 Melhores Clientes do stand (maior valorCompras)
SELECT id,nome,ValorCompras FROM Cliente
	ORDER BY ValorCompras DESC
    LIMIT 3;

-- Numero de Vendas e Aluguers dos Funcionarios
SELECT Nome,Nvendas,Nalugueres FROM Funcionario
	WHERE Funcao = 'Tecnico de Venda';
    
-- MELHOR VENDEDOR
SELECT Nome, SUM((coalesce(Nvendas,0)) + (coalesce(Nalugueres ,0))) as TotalVendasAluguers FROM Funcionario
	WHERE Funcao = 'Tecnico de Venda'
	GROUP BY Nome
    ORDER BY TotalVendasAluguers DESC
    LIMIT 1;

-- Stock de Carros
SELECT * FROM Carro
	WHERE Estado = 'DISPONIVEL';
    
-- Carros Vendidos
SELECT * FROM Carro
	WHERE ESTADO = 'NAODISPONIVEL';
    
-- Marcas com mais Procuras para compra
SELECT Marca, COUNT(Marca) as Vendas FROM Carro c
	INNER JOIN VendaInclui v ON v.Carro_ID = c.id
    GROUP BY Marca;

-- Marcas com mais Procuras para aluguer
SELECT Marca, COUNT(Marca) as Aluguers FROM Carro c
	INNER JOIN AluguerInclui v ON v.Carro_ID = c.id
    GROUP BY Marca;

-- Obter tamanho da base de dados
SELECT table_name AS 'Table', 
       CONCAT(ROUND((data_length + index_length) / (1024 * 1024), 2), ' MB') AS 'Size'
FROM information_schema.TABLES
WHERE table_schema = 'stand';

-- --------------------------------------------
-- CRIAÇÂO DE VIEWS
-- --------------------------------------------

-- DROP VIEW stockCarrosVenda
CREATE VIEW StockCarrosVenda AS
SELECT ID,Marca,Modelo,Kilometragem,AnoFabrico,Preco,Integridade FROM Carro
	WHERE ESTADO = 'DISPONIVEL' AND Destinacao = 'Venda';
    
CREATE VIEW stockCarrosAluguer AS
SELECT ID,Marca,Modelo,Kilometragem,AnoFabrico,Preco,Integridade FROM Carro
	WHERE ESTADO = 'DISPONIVEL' AND Destinacao = 'Aluguer';

CREATE VIEW carrosCompradosPorClientes AS
SELECT Nome as Cliente, vi.Carro_ID FROM Cliente c
	INNER JOIN Compra o ON o.Cliente_ID = c.ID
    INNER JOIN VendaInclui vi ON vi.Compra_Numero = o.Numero;
    
CREATE VIEW carrosAlugadosPorClientes AS
SELECT Nome as Cliente, ai.Carro_ID FROM Cliente c
	INNER JOIN Aluguer o ON o.Cliente_ID = c.ID
    INNER JOIN AluguerInclui ai ON ai.Aluguer_Numero = o.Numero;

-- --------------------------------------------
-- EXECUÇÃO DE PROCEDURES
-- --------------------------------------------

-- Efetuar um pedido de compra
-- data, IDCarro, IDFuncionario, IDCliente, PlanoPagamento (A pronto)
CALL efetuarCompra(CURDATE(), 1, 6, 4, 'A pronto');

-- Efetuar aluguer
-- data, IDCarro, IDFuncionario, IDCliente, PlanoPagamento (A pronto), FotoEstado, TempoAluguer, DadosSeguro 
CALL efetuarAluguer(CURDATE(), 4, 6, 4, 'A Pronto', '01101111010101000', '4', 'Seguro Automovel');

-- Criar Utilizador
-- Nome, Sexo, DataNascimento, Rua, Distrito, Codigo_Postal, Email, Contribuinte, ValorCompras, Dívida, NumeroTelemovel
CALL criarUtilizador('Tiago Moita','Masculino','2003-05-07','Rua José Maria','Braga','Gualtar', '4990-282','titimoita@gmail.com', '362845334','0','0','93163734');

-- Criar Funcionario
-- Nome, DataNascimento, Funcao, Sexo, Salário, Telefone, DataContratacao, Nvendas, Nalugueres
CALL criarFuncionario('Tomás Cunha','2001-05-23','Recepcionista','Masculino','1000','945689345','2022-03-03','0','0');

-- Criar Carro
-- Destinacao, Marca, Modelo, Kilometragem, AnoFabrico, Preco, Integridade, Estado
CALL addCarro('Aluguer','Lamborghini','Aventador','11000','2012','1500','USADO','DISPONIVEL');

-- ---------------
-- Permissoes
-- ----------------

-- Criação dos users
-- SELECT * FROM mysql.user;
CREATE USER 'dono'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'recepcionista'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'gestor'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'tecnico'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'cliente'@'localhost' IDENTIFIED BY 'password';

-- Permissões
GRANT ALL PRIVILEGES ON *.* TO 'dono'@'localhost';
GRANT SELECT, UPDATE ON Cliente TO 'recepcionista'@'localhost';
GRANT SELECT ON stockcarrosaluguer TO 'cliente'@'localhost';
GRANT SELECT ON stockcarrosvenda TO 'cliente'@'localhost';
GRANT SELECT, UPDATE ON aluguer TO 'tecnico'@'localhost';
GRANT SELECT, UPDATE ON aluguerinclui TO 'tecnico'@'localhost';
GRANT SELECT, UPDATE ON efetuapedido TO 'tecnico'@'localhost';
GRANT SELECT, UPDATE ON compra TO 'tecnico'@'localhost';
GRANT SELECT, UPDATE ON vendainclui TO 'tecnico'@'localhost';






