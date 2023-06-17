USE Stand;
-- -----------------------
-- Efetuar uma compra
-- -----------------------
DELIMITER $$
-- DROP PROCEDURE efetuarCompra
CREATE PROCEDURE efetuarCompra (
    IN pedidoData DATE,
    IN Carro INT,
    IN funcionarioID INT,
    IN clienteID INT,
    IN PlanoPagamento VARCHAR(50)
)
BEGIN
    DECLARE contribuinteF VARCHAR(50);
    DECLARE ValorCompra DECIMAL(9,2);
    DECLARE ID_COMPRA INT;
	SELECT Contribuinte 
		INTO contribuinteF FROM Cliente WHERE ID = clienteID;
	SELECT Preco
		INTO ValorCompra FROM Carro WHERE ID = Carro;

	-- Inserir os campos relacionados na tabela Compra
    INSERT INTO Compra
		(Cliente_ID, Funcionario_ID, PlanoPagamento, DataEmissao, Contribuinte, DataPagamento, Valor)
		VALUES
		(clienteID, funcionarioID, PlanoPagamento, pedidoData, contribuinteF, pedidoData, ValorCompra);
	
	-- SELECT * FROM efetuaPedido
    -- Inserir o pedido de compra na tabela efetuaPedido
    
    -- Obter o ID da Compra
	SELECT Numero
		INTO ID_COMPRA FROM Compra WHERE Numero = (SELECT MAX(Numero) FROM Compra);
        
    INSERT INTO efetuaPedido 
		(Data_pedido, Compra_Numero, Funcionario_ID, Cliente_ID)
		VALUES 
		(pedidoData, ID_COMPRA, funcionarioID, clienteID);
        
	-- Inserir os campos relacionados na tabela vendaInclui
    INSERT INTO vendainclui
		(Carro_ID, Compra_Numero, Preco, Imposto)
        VALUES
        (Carro, ID_COMPRA, valorCompra, '0');
	
    -- Atualizar o numero de vendas do funcionario
	UPDATE funcionario  
		SET NVendas = Nvendas + 1
		WHERE ID = funcionarioID;
	
    -- Atualizar o valor de compras do cliente
	UPDATE cliente
		SET ValorCompras = ValorCompras + ValorCompra
        WHERE ID = clienteID;
	
    -- Atualizar o estado do carro
	UPDATE carro
		SET Estado = 'NAODISPONIVEL'
		WHERE ID = Carro;
        
END $$
-- --------------------
-- Efetuar um Aluguer
-- --------------------
DELIMITER $$
-- DROP PROCEDURE efetuarAluguer
CREATE PROCEDURE efetuarAluguer (
    IN pedidoData DATE,
    IN Carro INT,
    IN funcionarioID INT,
    IN clienteID INT,
    IN PlanoPagamento VARCHAR(50),
    IN FotoEstado LONG,
    IN TempoAluguer INT,
    IN DadosSeguro VARCHAR(45)
)
BEGIN
    DECLARE contribuinteF VARCHAR(50);
    DECLARE ValorAluguer DECIMAL(9,2);
    DECLARE ID_ALUGUER INT;
	SELECT Contribuinte 
		INTO contribuinteF FROM Cliente WHERE ID = clienteID;
	SELECT Preco
		INTO ValorAluguer FROM Carro WHERE ID = Carro;
	SET ValorAluguer = ValorAluguer * TempoAluguer;

	-- Inserir os campos relacionados na tabela Compra
    SELECT * FROM Aluguer;
    INSERT INTO Aluguer
		(Funcionario_ID, Cliente_ID, PlanoPagamento, DataEmissao, Contribuinte, DataPagamento, Valor, FotoEstado, DadosSeguro, PeriodoAluguer)
		VALUES
		(funcionarioID, clienteID, PlanoPagamento, pedidoData, contribuinteF, pedidoData, ValorAluguer, FotoEstado, DadosSeguro, TempoAluguer);
	
	-- SELECT * FROM efetuaPedido
    -- Inserir o pedido de compra na tabela efetuaPedido
    
    -- Obter o ID do Aluguer
	SELECT Numero
		INTO ID_ALUGUER FROM Aluguer WHERE Numero = (SELECT MAX(Numero) FROM Aluguer);
        
    INSERT INTO efetuaPedido 
		(Data_pedido, Aluguer_Numero, Funcionario_ID, Cliente_ID)
		VALUES 
		(pedidoData, ID_ALUGUER, funcionarioID, clienteID);
        
	-- Inserir os campos relacionados na tabela vendaInclui
    INSERT INTO aluguerinclui
		(Carro_ID, Aluguer_Numero, Preco, Imposto)
        VALUES
        (Carro, ID_ALUGUER, ValorAluguer, '0');
	
    -- Atualizar o numero de vendas do funcionario
	UPDATE funcionario  
		SET NAlugueres = NAlugueres + 1
		WHERE ID = funcionarioID;
	
    -- Atualizar o valor de compras do cliente
	UPDATE cliente
		SET ValorCompras = ValorCompras + ValorAluguer
        WHERE ID = clienteID;
        
END $$

-- ----------------------------
-- Adicionar um Cliente
-- ---------------------------
DELIMITER $$
-- DROP PROCEDURE criarUtilizador
CREATE PROCEDURE criarUtilizador (
	IN Nome VARCHAR(45),
    IN Sexo VARCHAR(15),
    IN DataNascimento DATE,
    IN Rua VARCHAR(45),
    IN Distrito VARCHAR(20),
    IN Localidade VARCHAR(20),
    IN Codigo_Postal VARCHAR(8),
    IN Email VARCHAR(45),
    IN Contribuinte INT,
    IN ValoCompras DECIMAL(10,2),
    IN Dívida DECIMAL(8,2),
    IN NumeroTelefone VARCHAR(45)
    
)
BEGIN
		DECLARE ID_CLIENTE INT;
		-- INSERIR O CLIENTE
		INSERT INTO Cliente 
			(Nome, Sexo, DataNascimento, Rua, Distrito, Localidade, Codigo_Postal, Email, Contribuinte, ValorCompras, Dívida)
			VALUES
			(Nome, Sexo, DataNascimento, Rua, Distrito, Localidade, Codigo_Postal, Email, Contribuinte, ValorCompras, Dívida);
		
        -- Obter id do cliente adicionado
       -- SELECT ID
		-- INTO ID_CLIENTE FROM Cliente WHERE ID = ID_CLIENTE;
        
		SELECT ID
		INTO ID_Cliente FROM Cliente WHERE ID = (SELECT MAX(ID) FROM Cliente);
        
        -- INSERIR O NUMERO DE TELEFONE
        INSERT INTO Telefone
			(Cliente_ID, Telefone)
            VALUES
            (ID_CLIENTE, NumeroTelefone);
        
END $$

-- ----------------------------
-- Adicionar um Funcionario
-- ---------------------------
DELIMITER $$
-- DROP PROCEDURE criarFuncionario
CREATE PROCEDURE criarFuncionario (
	IN Nome VARCHAR(45),
    IN DataNascimento DATE,
    IN Funcao VARCHAR(30),
    IN Sexo VARCHAR(15),
    IN Salário DECIMAL(8,2),
    IN Telefone INT,
    IN DataContratacao DATE,
    IN Nvendas INT,
    IN Nalugueres INT
)
BEGIN
	
    -- INSERIR FUNCIONARIO
    INSERT INTO Funcionario
		(ID, Nome, DataNascimento, Funcao, Sexo, Salário, Telefone, DataContratacao, NVendas, NAlugueres)
        VALUES
        (ID, Nome, DataNascimento, Funcao, Sexo, Salário, Telefone, DataContratacao, NVendas, NAlugueres);
        
END $$

-- ----------------------------
-- Adicionar um Carro
-- ---------------------------
DELIMITER $$
-- DROP PROCEDURE addCarro
CREATE PROCEDURE addCarro (
	IN Destinacao VARCHAR(10),
    IN Marca VARCHAR(15),
    IN Modelo VARCHAR(25),
    IN Kilometragem INT,
    IN AnoFabrico VARCHAR(5),
    IN Preco DECIMAL(10,2),
    IN Integridade VARCHAR(45),
    IN Estado VARCHAR(20)

)
BEGIN
	
    INSERT INTO Carro
		(ID, Destinacao, Marca, Modelo, Kilometragem, AnoFabrico, Preco, Integridade, Estado)
        VALUES
        (ID, Destinacao, Marca, Modelo, Kilometragem, AnoFabrico, Preco, Integridade, Estado);
        
END $$



