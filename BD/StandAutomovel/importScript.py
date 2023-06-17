import mysql.connector
from mysql.connector import Error
import csv

def insert_cliente(cursor, row):
    nome = row[1]
    sexo = row[2]
    data_nascimento = row[3]
    rua = row[4]
    distrito = row[5]
    localidade = row[6]
    codigo_postal = row[7]
    email = row[8]
    contribuinte = row[9]
    valor_compras = row[10]
    divida = row[11]

    sql = """INSERT INTO Cliente
        (Nome, Sexo, DataNascimento, Rua, Distrito, Localidade, Codigo_Postal, Email, Contribuinte, ValorCompras, Dívida)
        VALUES
        (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
        """

    values = (nome, sexo, data_nascimento, rua, distrito, localidade, codigo_postal, email, contribuinte, valor_compras, divida)

    cursor.execute(sql, values)
    print("Inserted data into Cliente table.")

def insert_funcionario(cursor, row):
    nome = row[1]
    data_nascimento = row[2]
    funcao = row[3]
    sexo = row[4]
    salario = row[5]
    telefone = row[6]
    data_contratacao = row[7]
    n_vendas = row[8]
    n_alugueres = row[9]

    sql = """INSERT INTO Funcionario
        (Nome, DataNascimento, Funcao, Sexo, Salário, Telefone, DataContratacao, NVendas, NAlugueres)
        VALUES
        (%s, %s, %s, %s, %s, %s, %s, %s, %s)
        """

    values = (nome, data_nascimento, funcao, sexo, salario, telefone, data_contratacao, n_vendas, n_alugueres)

    cursor.execute(sql, values)
    print("Inserted data into Funcionario table.")

def insert_telefone(cursor, row):
    id_cliente = row[1]
    telefone = row[2]

    sql = """INSERT INTO Telefone
        (Cliente_ID, Telefone)
        VALUES
        (%s, %s)
        """

    values = (id_cliente, telefone)

    cursor.execute(sql, values)
    print("Inserted data into Telefone table.")

def insert_carro(cursor, row):
    destinacao = row[1]
    marca = row[2]
    modelo = row[3]
    kilometragem = row[4]
    anoFabrico = row[5]
    preco = row[6]
    integridade = row[7]
    estado = row[8]

    sql = """INSERT INTO Carro
	    (Destinacao, Marca, Modelo, Kilometragem, AnoFabrico, Preco, Integridade, Estado)
        VALUES
        (%s, %s, %s, %s, %s, %s, %s, %s)
        """

    values = (destinacao, marca, modelo, kilometragem, anoFabrico, preco, integridade, estado)

    cursor.execute(sql, values)
    print("Inserted data into Carro table.")

def insert_compra(cursor, row):
    idCliente = row[1]
    idFuncionario = row[2]
    planoPagamento = row[3]
    dataEmissao = row[4]
    nContribuinte = row[5]
    dataDoPagamente = row[6]
    valor = row[7]

    sql = """INSERT INTO Compra
	    (Cliente_ID, Funcionario_ID, PlanoPagamento, DataEmissao, Contribuinte, DataPagamento, Valor)
        VALUES
        (%s, %s, %s, %s, %s, %s, %s)
        """

    values = (idCliente, idFuncionario, planoPagamento, dataEmissao, nContribuinte, dataDoPagamente, valor)

    cursor.execute(sql, values)
    print("Inserted data into Compra table.")

def insert_vendaInclui(cursor, row):
    idCarro = row[1]
    compra_num = row[2]
    preco = row[3]
    imposto = row[4]

    sql = """INSERT INTO VendaInclui
	    (Carro_ID, Compra_Numero, Preco, Imposto)
        VALUES
        (%s, %s, %s, %s)
        """

    values = (idCarro, compra_num, preco, imposto)

    cursor.execute(sql, values)
    print("Inserted data into VendaInclui table.")

def insert_aluguer(cursor, row):
    idFuncionario = row[1]
    idCliente = row[2]
    planoPagamento = row[3]
    dataEmissao = row[4]
    nContribuinte = row[5]
    dataDoPagamente = row[6]
    valor = row[7]
    FotoEstado = row[8]
    DadosDoSeguro = row[9]
    PeriodoAluguer = row[10]

    sql = """INSERT INTO Aluguer
	    (Funcionario_ID, Cliente_ID, PlanoPagamento, DataEmissao, Contribuinte, DataPagamento, Valor, FotoEstado, DadosSeguro, PeriodoAluguer)
        VALUES
        (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
        """

    values = (idFuncionario, idCliente, planoPagamento, dataEmissao, nContribuinte, dataDoPagamente, valor, FotoEstado, DadosDoSeguro, PeriodoAluguer)

    cursor.execute(sql, values)
    print("Inserted data into Aluguer table.")

def insert_aluguerInclui(cursor, row):
    num_aluguer = row[1]
    idCarro = row[2]
    preco = row[3]
    imposto = row[4]

    sql = """INSERT INTO AluguerInclui
	    (Aluguer_Numero, Carro_ID, Preco, Imposto)
        VALUES
        (%s, %s, %s, %s)
        """

    values = (num_aluguer, idCarro, preco, imposto)

    cursor.execute(sql, values)
    print("Inserted data into AluguerInclui table.")

def insert_efetuaPedido(cursor, row):
    dataPedido = row[1]
    numCompra = row[2] if row[2] != 'null' else None
    numAluguer = row[3] if row[3] != 'null' else None
    idFuncionario = row[4]
    idCliente = row[5]

    sql = """INSERT INTO EfetuaPedido
	    (Data_pedido, Compra_Numero, Aluguer_Numero, Funcionario_ID, Cliente_ID)
        VALUES
        (%s, %s, %s, %s, %s)
        """

    values = (dataPedido, numCompra, numAluguer, idFuncionario, idCliente)

    cursor.execute(sql, values)
    print("Inserted data into efetuaPedido table.")

try:
    db = mysql.connector.connect(host="localhost", user="root", passwd="123", database="stand")
    if db.is_connected():
        db_Info = db.get_server_info()
        print("Connected to MySQL Server version ", db_Info)
        mycursor = db.cursor()
        mycursor.execute("select database();")
        record = mycursor.fetchone()
        print("You're connected to the database: ", record)

except Error as e:
    print("Error while connecting to MySQL: ", e)

# Criar Cursos
cursor = db.cursor()

# Definir o dicionario table_insertion_functions
table_insertion_functions = {
    "Cliente": insert_cliente,
    "Funcionario": insert_funcionario,
    "Telefone": insert_telefone,
    "Carro": insert_carro,
    "Compra": insert_compra,
    "VendaInclui": insert_vendaInclui,
    "Aluguer": insert_aluguer,
    "AluguerInclui": insert_aluguerInclui,
    "EfetuaPedido": insert_efetuaPedido, 
    # Possivel adicionar mais tabelas se feita a sua definição
}

with open("povoar.csv", "r") as file:
    reader = csv.reader(file, delimiter=";")

    for row in reader:
        if not row:  # Saltar linhas vazias para melhor organização de tabelas
            continue
            
        table_name = row[0]
        if table_name in table_insertion_functions:
            insertion_function = table_insertion_functions[table_name]
            insertion_function(cursor, row)
            db.commit()

    db.commit()

cursor.close()