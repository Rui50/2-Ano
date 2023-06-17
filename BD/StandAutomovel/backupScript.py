import pymysql

# Configurações de conexão com o banco de dados
host = 'localhost'
port = 3306
user = 'root'
password = '123'
database = 'stand'
output_file = 'C:\\Users\\ruipe\\Desktop\\StandAutomovel\\Backup.sql'

# Definir a ordem desejada das tabelas
table_order = ['Cliente', 'Telefone', 'Funcionario', 'Carro', 'Compra', 'VendaInclui', 'Aluguer', 'AluguerInclui', 'EfetuaPedido']

# Conectando ao banco de dados
conn = pymysql.connect(
    host=host,
    port=port,
    user=user,
    password=password,
    database=database
)

# Criando o arquivo de backup
with open(output_file, 'w') as f:
    cursor = conn.cursor()
    
    # Iterar sobre as tabelas na ordem desejada
    for table_name in table_order:
        cursor.execute(f'SELECT * FROM {table_name}')
        rows = cursor.fetchall()
        
        # Obter as informações das colunas
        cursor.description
        column_names = [col[0] for col in cursor.description]
        
        # Escrever o conteúdo da tabela no arquivo
        f.write(f'-- Tabela: {table_name}\n')
        for row in rows:
            row_values = []
            for value in row:
                if value is None:
                    row_values.append('NULL')
                else:
                    row_values.append(f"'{str(value)}'")
            values = ', '.join(row_values)
            insert_statement = f'INSERT INTO {table_name} ({", ".join(column_names)}) VALUES ({values});\n'
            f.write(insert_statement)
        f.write('\n')
        
print("Backup feito com sucesso")

# Fechando a conexão com o banco de dados
conn.close()