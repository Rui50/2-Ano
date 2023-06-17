import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class View implements Serializable {
    private Controller c;
    private Scanner sc;

    public View(){
        this.c = new Controller();
        this.sc = new Scanner(System.in);
    }

    public void run() {

        System.out.println("Escolha o modo de utilização:");
        Menu menu = new Menu(new String[] {
      
            " Utilizador",
            " Admin"
        });
    
        menu.setHandler(1, () -> menuInicialUtil());
        menu.setHandler(2, () -> menuAdmin());
    
        menu.run();
    }
  
    /**
    * MENU UTILIZADOR-------------------------------------------------------------------------------------------------------------------------
    */

    private void menuInicialUtil() {
     
        Menu menuInicialUtil = new Menu(new String[] {"Iniciar sessão",
                            "Registar nova conta"});
    
        menuInicialUtil.setHandler(1, () -> loginUtil());
        menuInicialUtil.setHandler(2, () -> RegNovaConta());

        menuInicialUtil.run();
    }

    private void loginUtil() {
        System.out.println("E-mail:");
        String email = sc.nextLine();
        System.out.println("Password:");
        String password = sc.nextLine();
        int a = c.fazerLogin(email,password);
        if (a==1) menuUtilizador();
        else if (a==2) System.out.println("Esta password está errada");
        else if (a == 3) System.out.println("Este e-mail não está associado a uma conta");
    }

    private void RegNovaConta() {
        System.out.println("Nome:");
        String nome = sc.nextLine();
        System.out.println("E-mail:");
        String email = sc.nextLine();
        System.out.println("Password:");
        String password = sc.nextLine();
        System.out.println("Morada:");
        String morada = sc.nextLine();
        System.out.println("NIF:");
        int nif = sc.nextInt();
        sc.nextLine();
        c.registarNovaConta(nome,email,password,morada,nif);
        System.out.println("A sua conta foi criada.");
    }
    private void menuUtilizador() {
        System.out.println("Escolha o que pretende fazer:");
        Menu menuUtil = new Menu(new String[] {"Fazer uma encomenda",
                                "Colocar artigo à venda",
                                "Remover artigo de venda" ,
                                "Verificar encomendas",
                                "Histórico de compras",
                                "Histórico de vendas",
                                "Artigos à venda"});


        menuUtil.setPreCondition(3, () -> c.existeArtigosAvender());

        menuUtil.setHandler(1, () -> criarEncomenda());
        menuUtil.setHandler(2, () -> addArtigo());
        menuUtil.setHandler(3, () -> remArtigo());
        menuUtil.setHandler(4, () -> verifEnc());
        menuUtil.setHandler(5, () -> listaCompras());
        menuUtil.setHandler(6, () -> listaVendas());
        menuUtil.setHandler(7, () -> artigosAvenda());

        menuUtil.run();
    }

    private void criarEncomenda() {

        System.out.println("Que tipo de artigo quer comprar ?");
        Menu menu = new Menu(new String[] {"Sapatilha",
                            "T-shirt",
                            "Mala",
                            "Remover Artigo",
                            "Concluir Encomenda" });

        menu.setPreCondition(4, () -> c.existeArtigosnaEncomenda());
        menu.setPreCondition(5, () -> c.existeArtigosnaEncomenda());

        menu.setHandler(1, () -> comprarSapatilha());
        menu.setHandler(2, () -> comprarTshirt());
        menu.setHandler(3, () -> comprarMala());
        menu.setHandler(4, () -> removerArtigoEnc());
        menu.setHandler(5, () -> concluirEncomenda());
        menu.run();
    }


    private void comprarSapatilha(){
        System.out.println("Aqui segue a lista de sapatilhas disponiveis:");
        c.getListaSapatilhasDisponiveis();
        System.out.println("Deseja comprar algum artigo? (true|false)");
        boolean b = sc.nextBoolean();
        sc.nextLine();
        if(b) {
            System.out.println("Digite o código da sapatilha que pretende adquirir:");
            String codigo = sc.nextLine();
            c.compraArtigo(codigo);
            System.out.println("O artigo foi adicionado à encomenda com sucesso!\n");
        }
    }

    private void comprarTshirt(){
        System.out.println("Aqui segue a lista de T-shirts disponiveis:");
        c.getListaTshirtsDisponiveis();
        System.out.println("Deseja comprar algum artigo? (true|false)");
        boolean b = sc.nextBoolean();
        sc.nextLine();
        if (b) {
            System.out.println("Digite o código da T-shirt que pretende adquirir:");
            String codigo = sc.nextLine();
            c.compraArtigo(codigo);
            System.out.println("O artigo foi adicionado à encomenda com sucesso!\n");
        }
    }

    private void comprarMala(){
        System.out.println("Aqui segue a lista de Malas disponiveis:");
        c.getListaMalasDisponiveis();
        System.out.println("Deseja comprar algum artigo? (true|false)");
        boolean b = sc.nextBoolean();
        sc.nextLine();
        if(b) {
            System.out.println("Digite o código da Mala que pretende adquirir:");
            String codigo = sc.nextLine();
            c.compraArtigo(codigo);
            System.out.println("O artigo foi adicionado à encomenda com sucesso!\n");
        }
    }
    private void removerArtigoEnc() {
        System.out.println("Aqui segue a lista de artigos nesta encomenda:");
        c.getListaArtigosnaEncomenda();
        System.out.println("Deseja remover algum artigo? (true|false)");
        boolean b = sc.nextBoolean();
        sc.nextLine();
        if(b) {System.out.println("Digite o código do artigo que pretende remover:");
            String codigo = sc.nextLine();
            c.remArtigoEncomenda(codigo);
            System.out.println("Artigo removido com sucesso!");
        }
    }
    private void concluirEncomenda() {
        c.concluiEncomenda();
        System.out.println("Encomenda concluida com sucesso!");
        menuUtilizador();
    }
  
    private void addArtigo() {
        System.out.println("Que tipo de artigo quer vender ?");
        Menu menuArtigo = new Menu(new String[] {"Sapatilha",
                                "T-shirt",
                                "Mala"});

        menuArtigo.setHandler(1, () -> criarSapatilha());
        menuArtigo.setHandler(2, () -> criarTshirt());
        menuArtigo.setHandler(3, () -> criarMala());
        menuArtigo.run();
    }
  
    private void criarSapatilha() {
        System.out.println("Preencha a informação que se segue:");
        System.out.println("Descrição: ");
        String descricao = sc.nextLine();
        System.out.println("Indica o seu estado de 1-5:");
        int estado = sc.nextInt();
        sc.nextLine();
        System.out.println("Marca:");
        String marca = sc.nextLine();
        System.out.println("Preço-base de venda (este preço não é definitivo):");
        double precobase = sc.nextDouble();
        System.out.println("Número prévio de donos:");
        int ndonos = sc.nextInt();
        sc.nextLine();
        System.out.println("Indica o nome da transportadora que pertence utilizar:");
        String transportadora = sc.nextLine();
        System.out.println("Tamanho :");
        int tamanho = sc.nextInt();
        sc.nextLine();
        System.out.println("Atacadores :");
        boolean atacadores = sc.nextBoolean();
        sc.nextLine();
        System.out.println("Cor :");
        String cor = sc.nextLine();
        System.out.println("Ano de Lançamento :");
        int anolancamento = sc.nextInt();
        sc.nextLine();
        System.out.println("Premium:");
        boolean premium = sc.nextBoolean();
        sc.nextLine();
        c.vendaSapatilha(descricao, estado, marca, precobase, ndonos, transportadora, tamanho, atacadores, cor, anolancamento, premium);
        System.out.println("O seu artigo foi adicionado com sucesso!");
    }
  
    private void criarTshirt() {
        System.out.println("Preencha a informação que se segue:");
        System.out.println("Descrição: ");
        String descricao = sc.nextLine();
        System.out.println("Indica o seu estado de 1-5 :");
        int estado = sc.nextInt();
        sc.nextLine();
        System.out.println("Marca:");
        String marca = sc.nextLine();
        System.out.println("Preço-base de venda (este preço não é definitivo):");
        double precobase = sc.nextDouble();
        System.out.println("Número prévio de donos:");
        int ndonos = sc.nextInt();
        sc.nextLine();
        System.out.println("Indica o nome da transportadora que pertence utilizar:");
        String transportadora = sc.nextLine();
        System.out.println("Tamanho :");
        System.out.println("1- S");
        System.out.println("2- M");
        System.out.println("3- L");
        System.out.println("4- XL");
        int tamanho = sc.nextInt();
        sc.nextLine();
        System.out.println("Padrão :");
        System.out.println("1- Liso");
        System.out.println("2- Riscas");
        System.out.println("3- Palmeiras");
        int padrao = sc.nextInt();
        sc.nextLine();
        c.vendaTshirt(descricao, estado, marca, precobase, ndonos, transportadora, tamanho, padrao);
        System.out.println("O seu artigo foi adicionado com sucesso!");
    }

    private void criarMala() {
        System.out.println("Preencha a informação que se segue:");
        System.out.println("Descrição: ");
        String descricao = sc.nextLine();
        System.out.println("Indica o seu estado de 1-5 :");
        int estado = sc.nextInt();
        sc.nextLine();
        System.out.println("Marca:");
        String marca = sc.nextLine();
        System.out.println("Preço-base de venda (este preço não é definitivo):");
        double precobase = sc.nextDouble();
        System.out.println("Número prévio de donos:");
        int ndonos = sc.nextInt();
        sc.nextLine();
        System.out.println("Indica o nome da transportadora que pertence utilizar:");
        String transportadora = sc.nextLine();
        System.out.println("Dimensao :");
        System.out.println("1- Pequena");
        System.out.println("2- Media");
        System.out.println("3- Grande");
        int dimensao = sc.nextInt();
        sc.nextLine();
        System.out.println("Material :");
        System.out.println("1- Tecido");
        System.out.println("2- PoliC");
        System.out.println("3- PoliP");
        System.out.println("4- ABS");
        int material = sc.nextInt();
        System.out.println("Ano da coleção:");
        int anocolecao = sc.nextInt();
        sc.nextLine();
        System.out.println("Premium:");
        boolean premium = sc.nextBoolean();
        sc.nextLine();
        c.vendaMala(descricao, estado, marca, precobase, ndonos, transportadora, dimensao, material, anocolecao, premium);
        System.out.println("O seu artigo foi adicionado com sucesso!");
    }

    public void remArtigo() {
        System.out.println("Aqui segue a lista com os artigos que tẽm à venda:");
        c.getListaArtigosAvender();
        System.out.println("Deseja remover algum artigo? (true|false)");
        boolean b = sc.nextBoolean();
        sc.nextLine();
        if(b) {
            System.out.println("Indica o código do artigo que pretende remover:");
            String codigo = sc.nextLine();
            c.remArtigoVenda(codigo);
            System.out.println("O seu artigo foi removido com sucesso!");
        }

    }
    public void verifEnc() {
        System.out.println("Aqui segue a lista de encomendas que ainda podem ser devolvidas:");
        c.getListaEncomendasDev();
        System.out.println("Quer devolver alguma encomenda?");
        System.out.println("SIM (escreva true) || NÃO (escreva false)");
        Boolean b = sc.nextBoolean();
        sc.nextLine();
        if(b) {
            System.out.println("Código da encomenda:");
            String codigo = sc.nextLine();
            boolean a = c.devolverEncomenda(codigo);
            if (a) System.out.println("Encomenda devolvida com sucesso!");
            else {
                System.out.println("Encomenda inválida para devolução!");
                verifEnc();
            }
        }
    }
    
    public void listaCompras() {
        System.out.println("Aqui segue a lista com os artigos que já comprou:");
        c.getListaArtigosComprados();
    }
    public void listaVendas() {
        System.out.println("Aqui segue a lista com os artigos que já vendeu,assim como o valor total adquirido:");
        c.getListaArtigosVendidos();
    }
    public void artigosAvenda() {
        System.out.println("Aqui segue a lista com os artigos que tẽm à venda:");
        c.getListaArtigosAvender();
    }
   //------------------------------MENU ADMIN---------------------------------------------------------------------------------------------------------------
  
    private void menuAdmin() {

        Menu menuAdmin = new Menu(new String[] {"Mudar nome da loja",
                                "Criar Artigo",
                                "Criar Utilizador",
                                "Criar Transportadora" ,
                                "Mover data",
                                "Lista de artigos",
                                "Lista de Utilizadores",
                                "Lista de Encomendas",
                                "Lista de Transportadoras",
                                "Valor ganho pela loja",
                                "Guardar Estado",
                                "Carregar Estado",
                                "Utilizador que mais faturou"
                                });
                                
        menuAdmin.setPreCondition(2, () -> c.existeTransportadora());

        menuAdmin.setHandler(1, () -> mudarNome());
        menuAdmin.setHandler(2, () -> criarArtigo());
        menuAdmin.setHandler(3, () -> criarUtilizador());
        menuAdmin.setHandler(4, () -> criarTransp());
        menuAdmin.setHandler(5, () -> movData());
        menuAdmin.setHandler(6, () -> listaArtigos());
        menuAdmin.setHandler(7, () -> listaUtilizadores());
        menuAdmin.setHandler(8, () -> listaEncomendas());
        menuAdmin.setHandler(9, () -> listaTransportadoras());
        menuAdmin.setHandler(10, () -> valorGanho());
        menuAdmin.setHandler(11, () -> guardaEstado());
        menuAdmin.setHandler(12, () -> carregaEstado());
        menuAdmin.setHandler(13, () -> maisFaturou());

        menuAdmin.run();
    }

    public void mudarNome(){
        System.out.println("Novo nome da Loja: ");
        String nome = sc.nextLine();
        c.setNomeLoja(nome);
    }

    private void criarArtigo() {
        System.out.println("Que tipo de artigo quer criar ?");
        Menu menuArtigo = new Menu(new String[] {"Sapatilha",
                                  "T-shirt",
                                  "Mala"});
                                  
        menuArtigo.setHandler(1, () -> criarSapatilhaAdmin());
        menuArtigo.setHandler(2, () -> criarTshirtAdmin());
        menuArtigo.setHandler(3, () -> criarMalaAdmin());
        menuArtigo.run();
    }

    private void criarSapatilhaAdmin() {
        System.out.println("Preencha a informação que se segue:");
        System.out.println("Descrição: ");
        String descricao = sc.nextLine();
        System.out.println("Marca:");
        String marca = sc.nextLine();
        System.out.println("Preço-base de venda (este preço não é definitivo):");
        double precobase = sc.nextDouble();
        sc.nextLine();
        System.out.println("Indica o nome da transportadora que pertence utilizar:");
        String transportadora = sc.nextLine();
        System.out.println("Tamanho :");
        int tamanho = sc.nextInt();
        sc.nextLine();
        System.out.println("Atacadores (true|false):");
        boolean atacadores = sc.nextBoolean();
        sc.nextLine();
        System.out.println("Cor :");
        String cor = sc.nextLine();
        System.out.println("Ano de Lançamento :");
        int anolancamento = sc.nextInt();
        sc.nextLine();
        System.out.println("Premium (true|false):");
        boolean premium = sc.nextBoolean();
        sc.nextLine();
        c.criaSapatilhaAdmin(descricao, marca, precobase, transportadora, tamanho, atacadores, cor, anolancamento, premium);
        System.out.println("O artigo foi criado com sucesso!");
        menuAdmin();
    }
      
    private void criarTshirtAdmin() {
        System.out.println("Preencha a informação que se segue:");
        System.out.println("Descrição: ");
        String descricao = sc.nextLine();
        System.out.println("Marca:");
        String marca = sc.nextLine();
        System.out.println("Preço-base de venda (este preço não é definitivo):");
        double precobase = sc.nextDouble();
        sc.nextLine();
        System.out.println("Indica o nome da transportadora que pertence utilizar:");
        String transportadora = sc.nextLine();
        System.out.println("Tamanho :");
        System.out.println("1- S");
        System.out.println("2- M");
        System.out.println("3- L");
        System.out.println("4- XL");
        int tamanho = sc.nextInt();
        sc.nextLine();
        System.out.println("Padrão :");
        System.out.println("1- Liso");
        System.out.println("2- Riscas");
        System.out.println("3- Palmeiras");
        int padrao = sc.nextInt();
        sc.nextLine();
        c.criaTshirtAdmin(descricao, marca, precobase,transportadora, tamanho, padrao);
        System.out.println("O artigo foi criado com sucesso!");
        menuAdmin();
    }
    private void criarMalaAdmin() {
        System.out.println("Preencha a informação que se segue:");
        System.out.println("Descrição: ");
        String descricao = sc.nextLine();
        sc.nextLine();
        System.out.println("Marca:");
        String marca = sc.nextLine();
        System.out.println("Preço-base de venda (este preço não é definitivo):");
        double precobase = sc.nextDouble();
        sc.nextLine();
        System.out.println("Indica o nome da transportadora que pertence utilizar:");
        String transportadora = sc.nextLine();
        System.out.println("Dimensao :");
        System.out.println("1- Pequena");
        System.out.println("2- Média");
        System.out.println("3- Grande");
        int dimensao = sc.nextInt();
        sc.nextLine();
        System.out.println("Material :");
        System.out.println("1- Tecido");
        System.out.println("2- PoliC");
        System.out.println("3- PoliP");
        System.out.println("4- ABS");
        int material = sc.nextInt();
        System.out.println("Ano da coleção:");
        int anocolecao = sc.nextInt();
        sc.nextLine();
        System.out.println("Premium:");
        boolean premium = sc.nextBoolean();
        sc.nextLine();
        c.criaMalaAdmin(descricao, marca, precobase, transportadora, dimensao, material, anocolecao, premium);
        System.out.println("O artigo foi criado com sucesso!");
        menuAdmin();
    }

    private void criarUtilizador() {
        System.out.println("Nome:");
        String nome = sc.nextLine();
        System.out.println("E-mail:");
        String email = sc.nextLine();
        System.out.println("Password:");
        String password = sc.nextLine();
        System.out.println("Morada:");
        String morada = sc.nextLine();
        System.out.println("NIF:");
        int nif = sc.nextInt();
        c.criaUtilizador(email, password, nome, morada, nif);
    }

    public void criarTransp(){
        System.out.println("Nome da Transportadora:");
        String nome = sc.nextLine();
        System.out.println("Margem de lucro(entre 0 e 1):");
        double margemLucro = sc.nextDouble();
        sc.nextLine();
        c.criaTransportadora(nome,margemLucro);
        System.out.println("Transportadora criada com sucesso!");
    }

    public void movData() {
        System.out.println("Quantos dias pretende avançar?");
        int dias = sc.nextInt();
        c.avancaTempo(dias);
    }

    public void listaArtigos(){
        System.out.println("Lista de Artigos:");
        c.getListaArtigosLoja();
    }

    public void listaUtilizadores(){
        System.out.println("Lista de Utilizadores:");
        c.getListaUtilizadoresLoja();
    }

    public void listaEncomendas(){
        System.out.println("Lista de Encomendas:");
        c.getListaEncomendasLoja();
    }
    
    public void listaTransportadoras(){
        System.out.println("Lista de Transportadoras:");
        c.getListaTransportadorassLoja();
    }

    public void valorGanho() {
    c.valorGanhoLoja();
  }

    public void maisFaturou() {c.userMaisFaturou();}

    public void guardaEstado() {
        try {
            c.guardaEstado();
            System.out.println("Gravação feita com sucesso!");
        }
        catch (java.io.FileNotFoundException f) {
            System.out.println("Ficheiro não encontrado!");
            f.printStackTrace();
        }
        catch (java.io.IOException f) {
            System.out.println("Erro a aceder ao ficheiro!");
            f.printStackTrace();
        }
    }
    public void carregaEstado() {
        try {
            c.carregaEstado();
            System.out.println("Gravação adquirida com sucesso!");
        }
        catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado!");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("Erro a aceder ao ficheiro!");
            e.printStackTrace();}
        catch (ClassNotFoundException e) {e.printStackTrace();
        }
    }
}
