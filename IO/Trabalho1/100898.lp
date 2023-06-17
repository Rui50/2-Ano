/* Função objectivo: minimizar o número de contentores a usar */

min: 11 y11 + 10 y10 + 7 y7;

/* min: 11 x1 + 11 x2 + 11 x3 + 11 x4 + 11 x5 + 11 x6 + 11 x7 + 11 x8 + 11 x9 + 11 x10 + 11 x11 + 11 x12 + 11 x13 + 11 x14
     + 10 x15 + 10 x16 + 10 x17 + 10 x18 + 10 x19 + 10 x20 + 10 x21 + 10 x22 + 10 x23 + 10 x24 + 10 x25 + 10 x26
     + 7 x27 + 7 x28 + 7 x29 + 7 x30 + 7 x31 +7 x32; */
     
/* Tabelas */

//Métodos de empacotamento no contentor de comprimento 11:

ITEM11.5: 2 x1 + 1 x2 + 1 x3 + 1 x4 + 1 x5                                                                     <= 5;
ITEM11.4:        1 x2                      + 2 x6 + 2 x7 + 1 x8 + 1 x9 + 1 x10                                 <= 10;
ITEM11.3:               2 x3 + 1 x4        + 1 x6        + 2 x8 + 1 x9         + 3 x11 + 2 x12 + 1 x13         <= 10;
ITEM11.2:        1 x2        + 1 x4 + 3 x5        + 1 x7        + 2 x9 + 3 x10 + 1 x11 + 2 x12 + 4 x13 + 5 x14 <= 10;

//Métodos de empacotamento no contentor de comprimento 10:

ITEM10.5: 2 x15 + 1 x16 + 1 x17 + 1 x18                                                                 <=5;
ITEM10.4:         1 x16                 + 2 x19 + 1 x20 + 1 x21 + 1 x22                                 <=10;
ITEM10.3:                 1 x17                 + 2 x20 + 1 x21         + 3 x23 + 2 x24 + 1 x25         <=10;
ITEM10.2:                 1 x17 + 2 x18 + 1 x19         + 1 x21 + 3 x22         + 2 x24 + 3 x25 + 5 x26 <=10;

//Métodos de empacotamento no contentor de comprimento 7;

ITEM7.5: 1 x27                                         <=5;
ITEM7.4:         1 x28 + 1 x29                         <=10;
ITEM7.3:         1 x28         + 2 x30 + 1 x31         <=10;
ITEM7.2: 1 x27         + 1 x29         + 2 x31 + 3 x32 <=10;

/* Restrições */

//Garantir o uso de todos os items
2 x1 + 1 x2 + 1 x3 + 1 x4 + 1 x5 + 2 x15 + 1 x16 + 1 x17 + 1 x18 + 1 x27 = 5;
1 x2 + 2 x6 + 2 x7 + 1 x8 + 1 x9 + 1 x10 + 1 x16 + 2 x19 + 1 x20 + 1 x21 + 1 x22 = 10;
2 x3 + 1 x4 + 1 x6 + 2 x8 + 1 x9 + 3 x11 + 2 x12 + 1 x13 + 1 x17 + 2 x20 + 1 x21 + 3 x23 + 2 x24 + 1 x25 + 1 x28 + 2 x30 + 1 x31 = 10;
1 x2 + 1 x4 + 3 x5 + 1 x7 + 2 x9 + 3 x10 + 1 x11 + 2 x12 + 4 x13 + 5 x14 + 1 x17 + 2 x18 + 1 x19 + 1 x21 + 3 x22 + 2 x24 + 3 x25 + 5 x26 +1 x27 + 1 x29 + 2 x31 + 3 x32 = 10;

/* Definir a quantidade de contentores */

//Contentores de 11
y11 = 1 x1 + 1 x2 + 1 x3 + 1 x4 + 1 x5 + 1 x6 + 1 x7 + 1 x8 + 1 x9 + 1 x10 + 1 x11 + 1 x12 + 1 x13 + 1 x14 ;
y11 >= 0;

//Contentores de 10
y10 = 1 x15 + 1 x16 + 1 x17 + 1 x18 + 1 x19 + 1 x20  + 1 x21  + 1 x22 + 1 x23 + 1 x24 + 1 x25 + 1 x26;
y10 <= 1;
              
//Contentores de 7

y7 = 1 x27 + 1 x28 + 1 x29 + 1 x30 + 1 x31 + 1 x32;
y7 <= 10;


/* Definição de variáveis */
int y11 y10 y7 x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16 x17 x18 x19 x20 x21 x22 x23 x25 x26 x27 x28 x29 x30 x31 x32;