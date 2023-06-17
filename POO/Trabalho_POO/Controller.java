import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Random;

public class Controller implements Serializable{
    
    private Vintage modelo;
    private Utilizador utilizador;
    private Encomenda encomenda;

    public Controller() {
        this.modelo = new Vintage();
        this.utilizador = new Utilizador();
        this.encomenda = new Encomenda();
    }

    public int fazerLogin(String email, String password){
        if(modelo.getUtilizadores().containsKey(email)) {
            utilizador = new Utilizador(modelo.getUtilizadores().get(email).clone());
            if(utilizador.getPassword().equals(password)) return 1;
            else return 2;
        }
        else return 3;
    }

    public void registarNovaConta(String nome, String email,String password, String morada, int nif){
        this.utilizador = new Utilizador(gerarCodigo(), email, password, nome, morada, nif, new HashMap<>(), new HashMap<>(), new HashMap<>(), 0);
        this.modelo.addUtilizador(utilizador.clone());
        utilizador = new Utilizador();
    }

    public boolean existeArtigosAvender(){
         return utilizador.getAvender().size()>0;
    }
    public boolean existeArtigosnaEncomenda(){
        return this.encomenda.getArtigos().size()>0;
    }
    public void getListaSapatilhasDisponiveis() {
        System.out.println(this.modelo.getSapatilhas(this.utilizador.clone()).values().toString());
    }
     public void getListaTshirtsDisponiveis() {
        System.out.println(this.modelo.getTshirts(this.utilizador.clone()).values().toString());
     }
     public void getListaMalasDisponiveis() {
        System.out.println(this.modelo.getMalas(this.utilizador.clone()).values().toString());
     }
     public void compraArtigo(String codigo) {
        encomenda.addArtigo(this.modelo.getArtigos().get(codigo).clone());
        if (modelo.getArtigos().get(codigo).getEnovo() == false) {
            modelo.mudarArtigoListaCompra(this.modelo.getArtigos().get(codigo).clone());modelo.addComprasUtil(this.utilizador.getEmail(), this.modelo.getArtigos().get(codigo).clone());
        }
        modelo.remArtigo(codigo);
    }

    public void getListaArtigosnaEncomenda() {
        System.out.println(this.encomenda.getArtigos().toString());
     }

    public void remArtigoEncomenda(String codigo) {
        this.modelo.addArtigo(this.encomenda.getArtigos().get(codigo).clone());
        this.modelo.mudarArtigoListaRem(this.encomenda.getArtigos().get(codigo).clone());
        this.modelo.remComprasUtil(this.utilizador.getEmail(), this.modelo.getArtigos().get(codigo).clone());
        this.encomenda.remArtigo(codigo);
    }

    public void concluiEncomenda() {
        this.encomenda.tamanhoEncomenda();
        this.encomenda.calculaPrecofinal();
        this.encomenda.setDatacriacao(modelo.getData());
        this.encomenda.setCodigo(gerarCodigo());
        this.encomenda.setUtilizador(utilizador.clone());
        this.encomenda.setEstado(Encomenda.Estado.Finalizada);
        this.encomenda.setDataEntrega(ChronoUnit.DAYS.addTo(this.modelo.getData(), 5+encomenda.getArtigos().size()));
        this.modelo.addEncomenda(encomenda.clone());
        this.encomenda = new Encomenda();
    }
    public void vendaSapatilha(String descricao, int estado, String marca, double precobase, int ndonos, String transportadora,
                               int tamanho, boolean atacadores, String cor, int anolancamento, boolean premium) {
        Sapatilha sapatilha = new Sapatilha(gerarCodigo(), descricao, false, null, marca, precobase, 0, ndonos,
        this.modelo.getTransportadoras().get(transportadora).clone(), tamanho, atacadores, cor, anolancamento, premium);
        sapatilha.avaliarEstado(estado);
        sapatilha.valorCorrigido();
        this.utilizador.addArtigoUtil( sapatilha.clone());
        this.modelo.addArtigoListaUtil(utilizador.getEmail(), sapatilha.clone());
        this.modelo.addArtigo( sapatilha.clone());
    }
    public void vendaTshirt(String descricao, int estado, String marca, double precobase, int ndonos, String transportadora , int tamanho, int padrao) {
        Tshirt tshirt = new Tshirt(gerarCodigo(), descricao, false, null, marca, precobase, 0, ndonos,
                modelo.getTransportadoras().get(transportadora).clone(), null, null);
        tshirt.valorCorrigido();
        tshirt.avaliarEstado(estado);
        tshirt.verificaTamanho(tamanho);
        tshirt.verificaPadrao(padrao);
        this.utilizador.addArtigoUtil( tshirt.clone());
        this.modelo.addArtigoListaUtil(utilizador.getEmail(), tshirt.clone());
        this.modelo.addArtigo(tshirt.clone());
    }
    public void vendaMala(String descricao, int estado, String marca, double precobase, int ndonos, String transportadora, 
    int dimensao, int material, int anocolecao, boolean premium) {
        Mala mala = new Mala(gerarCodigo(), descricao, false, null, marca, precobase, 0, ndonos, 
        modelo.getTransportadoras().get(transportadora).clone(), null, null,anocolecao,premium);
        mala.valorCorrigido();
        mala.avaliarEstado(estado);
        mala.verificaDimensao(dimensao);
        mala.verificaMaterial(material);
        this.utilizador.addArtigoUtil( mala.clone());
        this.modelo.addArtigoListaUtil(utilizador.getEmail(), mala.clone());  
        modelo.addArtigo(mala.clone());
    }
    
    public void remArtigoVenda(String codigo) {
        this.utilizador.remArtigoUtil(modelo.getArtigos().get(codigo));
        this.modelo.remArtigo(codigo);
    }
    public void getListaEncomendasDev() {
        System.out.println(modelo.getEncomendasPorUtil(this.utilizador.getEmail()).values().toString());
    }

    public boolean devolverEncomenda(String codigo){
        if (modelo.getEncomendasPorUtil(this.utilizador.getEmail()).containsKey(modelo.getEncomendas().get(codigo).getCodigo())) {
            modelo.mudarArtigosLista(modelo.getEncomendas().get(codigo).getArtigos());
            modelo.remListaArtigosUtil(utilizador.getEmail(),modelo.getEncomendas().get(codigo));
            modelo.getEncomendas().remove(codigo);
            modelo.calculaValorGanhoTotal();
            return true;
        }
        return false;
    }
    public void getListaArtigosComprados() {
        System.out.println(utilizador.getCompras().values().toString());
    }
    public void getListaArtigosVendidos() {
        System.out.println(utilizador.getVendidos().values().toString());
        System.out.print(utilizador.getValorTotalVendido()+"\n");
    }
    public void getListaArtigosAvender() {
        System.out.println(utilizador.getAvender().values().toString());  
    }

    public boolean existeTransportadora() {
       return this.modelo.getTransportadoras().size()>0;
    } 
    public void setNomeLoja(String nome) {
        this.modelo.setNome(nome);
    }

    public void criaSapatilhaAdmin(String descricao,  String marca, double precobase, String transportadora, 
                                    int tamanho, boolean atacadores, String cor, int anolancamento, boolean premium) {
        Sapatilha sapatilha = new Sapatilha(gerarCodigo(), descricao, true, Artigo.Estado.NOVO, marca, precobase, 0, 0, 
        this.modelo.getTransportadoras().get(transportadora).clone(), tamanho, atacadores, cor, anolancamento, premium);
        sapatilha.valorCorrigido();
        this.modelo.addArtigo(sapatilha.clone());
    }
    public void criaTshirtAdmin(String descricao, String marca, double precobase, String transportadora , int tamanho, int padrao) {
        Tshirt tshirt = new Tshirt(gerarCodigo(), descricao, true, Artigo.Estado.NOVO, marca, precobase, 0, 0,
                                    this.modelo.getTransportadoras().get(transportadora).clone(), null,null);
        tshirt.valorCorrigido();
        tshirt.verificaPadrao(padrao);
        tshirt.verificaTamanho(tamanho);
        this.modelo.addArtigo(tshirt.clone());
    }
    public void criaMalaAdmin(String descricao, String marca, double precobase, String transportadora, int dimensao, int material, 
                                int anocolecao, boolean premium) {
        Mala mala = new Mala(gerarCodigo(), descricao, true, Artigo.Estado.NOVO, marca, precobase, 0, 0,
                                this.modelo.getTransportadoras().get(transportadora).clone(), null, null,anocolecao,premium);
        mala.valorCorrigido();
        mala.verificaDimensao(dimensao);
        mala.verificaMaterial(material);
        this.modelo.addArtigo( mala.clone());
    }

    public void criaUtilizador(String email, String password, String nome, String morada, int nif) {
        this.utilizador = new Utilizador(gerarCodigo(), email, password, nome, morada, nif, new HashMap<>(), new HashMap<>(), new HashMap<>(), 0);
                            this.modelo.addUtilizador( utilizador.clone());
        this.utilizador = new Utilizador();
        System.out.println("O utilizador " + utilizador.getNome() + " foi criado com sucesso!");
    }
    public void criaTransportadora(String nome, double margemLucro) {
        Transportadora transp = new Transportadora(nome, margemLucro, 0.1);
        this.modelo.addTransportadora(transp.clone());
    }
    public void avancaTempo(int dias) {
        this.modelo.alteraData(dias);
        this.modelo.verificarEncomendas();
        System.out.println("A nova data é " + this.modelo.getData().toString()+ "\n");
    }
    public void getListaArtigosLoja() {
        for(Artigo artigo : modelo.getArtigos().values()){
            System.out.println(artigo.toString());
        }
    }
    public void getListaUtilizadoresLoja() {
        for(Utilizador util : modelo.getUtilizadores().values()){
            System.out.println(util.toString());
        }
    }
    public void getListaEncomendasLoja() {
        for(Encomenda enc : modelo.getEncomendas().values()){
            System.out.println(enc.toString());
        }
    }
    public void getListaTransportadorassLoja() {
        for(Transportadora transp : modelo.getTransportadoras().values()){
            System.out.println(transp.toString());
        }
    }

    public void guardaEstado() throws FileNotFoundException, IOException {
        this.modelo.guardaEstado("Vintage.obj");
    }
    public void carregaEstado() throws FileNotFoundException, IOException, ClassNotFoundException {
        this.modelo.carregaEstado("Vintage.obj");
    }

    public void valorGanhoLoja() {
        System.out.println("O valor ganho até ao momento pela loja "+ modelo.getNome() + " é de " + modelo.getValorGanhoTotal() + ".\n");
    }

    public void userMaisFaturou() {
        System.out.println("O utilizador que mais faturou foi: " + modelo.maisFaturou().getNome() + "\n" + modelo.maisFaturou() + ".\n");
    }
    public  String gerarCodigo() {
        int esq = 48; //  '0'
        int dir = 122; // 'z'
        int len = 8;
        Random random = new Random();
    
        String codigo = random.ints(esq, dir + 1)
          .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
          .limit(len)
          .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
          .toString();
    
        if (modelo.getArtigos().containsKey(codigo) || modelo.getEncomendas().containsKey(codigo) || modelo.getUtilizadores().containsKey(codigo)) gerarCodigo();
        return codigo;
    }
}
