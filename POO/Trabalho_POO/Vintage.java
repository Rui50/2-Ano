import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Vintage implements Serializable{
    private String nome;
    private LocalDate data ;
    private Map<String,Transportadora> transportadoras;
    private Map<String,Utilizador> utilizadores;
    private Map<String,Artigo> artigos;
    private Map<String,Encomenda> encomendas;
    private double valorGanhoTotal;

    public Vintage(){
        this.nome = "";
        this.data = LocalDate.now();
        this.transportadoras = new HashMap<>();
        this.utilizadores = new HashMap<>();
        this.artigos = new HashMap<>();
        this.encomendas = new HashMap<>();
        this.valorGanhoTotal = 0;

    }
    public Vintage(String nome, LocalDate data, Map<String,Transportadora> transportadoras, Map<String,Utilizador> utilizadores, Map<String,Artigo> artigos, Map<String, Encomenda> encomendas, double valorGanhoTotal){
        this.nome = nome;
        this.data = data;
        this.transportadoras = transportadoras;
        this.utilizadores = utilizadores;
        this.artigos = artigos;
        this.encomendas = encomendas; 
        this.valorGanhoTotal = valorGanhoTotal;
    }
    public Vintage(Vintage vintage){
        this.nome = vintage.getNome();
        this.data = vintage.getData();
        this.transportadoras = vintage.getTransportadoras();
        this.utilizadores = vintage.getUtilizadores();
        this.artigos = vintage.getArtigos();
        this.encomendas = vintage.getEncomendas(); 
        this.valorGanhoTotal = vintage.getValorGanhoTotal();
    }
    public String getNome(){
        return this.nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public LocalDate getData() {
        return this.data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setCompras(LocalDate data) {
        this.data =  data;
    }
    public Map<String, Transportadora> getTransportadoras() {
        return this.transportadoras.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public void setTransportadoras(Map<String, Transportadora> transportadoras) {
        this.transportadoras =  transportadoras.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }
    public Map<String, Utilizador> getUtilizadores() {
        return this.utilizadores.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public void setUtilizadores(Map<String, Utilizador> utilizadores) {
        this.utilizadores =  utilizadores.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }
    public Map<String, Artigo> getArtigos() {
        return this.artigos.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public void setArtigos(Map<String, Artigo> artigos) {
        this.artigos =  artigos.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }
    public Map<String, Encomenda> getEncomendas() {
        return this.encomendas.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public void setEncomendas(Map<String, Encomenda> encomendas) {
        this.encomendas =  encomendas.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }
    public double getValorGanhoTotal(){
        return this.valorGanhoTotal;
    }
    public void setValorGanhoTotal(double valorGanhoTotal){
        this.valorGanhoTotal = valorGanhoTotal;
    }



     public void calculaValorGanhoTotal () {
         double total = 0;
         for(Encomenda enc : this.encomendas.values()) {
             for (Artigo art : enc.getArtigos().values()){
                 if (art.getEnovo()) total += 0.5;
                 else total += 0.25;
             }
         }
         setValorGanhoTotal(total);
     }
        
    public void mudarArtigoListaCompra(Artigo a) {
        for(Utilizador util : utilizadores.values()){
            if(util.getAvender().containsKey(a.getCodigo())){
                util.remArtigoUtil(a);
                util.addArtigovendidos(a);
                util.setValorTotalVendido(util.getValorTotalVendido() + a.getPrecocorrigido());
            }
        }
    }
    public void mudarArtigoListaRem(Artigo a) {
        for(Utilizador util : utilizadores.values()){
            if(util.getVendidos().containsKey(a.getCodigo())){
                util.remArtigovendidos(a.clone());
                util.addArtigoUtil(a.clone());
                util.setValorTotalVendido(util.getValorTotalVendido()-a.getPrecocorrigido());
            }
        }
    }
    public void mudarArtigosLista(Map<String,Artigo> artigosLista) {
        for(Artigo a : artigosLista.values()){
            for(Utilizador util : utilizadores.values()){
                if(util.getVendidos().containsKey(a.getCodigo())){
                    util.remArtigovendidos(a);
                    util.addArtigoUtil(a);
                    util.setValorTotalVendido(util.getValorTotalVendido()-a.getPrecocorrigido());
                }
            }
            this.artigos.put(a.getCodigo(), a.clone());
        }
    }

    public Map<String,Encomenda> getEncomendasPorUtil(String codigo){
        Map<String,Encomenda> utilEnc = new HashMap<>();
        for(Encomenda enc : this.encomendas.values()){
            if (enc.getUtilizador().equals(this.utilizadores.get(codigo)) && enc.getEstado().equals(Encomenda.Estado.Finalizada)) 
                utilEnc.put(enc.getCodigo(), enc.clone());
            else if (enc.getUtilizador().equals(this.utilizadores.get(codigo)) && enc.getEstado().equals(Encomenda.Estado.Expedida) && ChronoUnit.DAYS.between(data, enc.getDataEntrega())<=2)
                utilEnc.put(enc.getCodigo(), enc.clone());
        }
        return utilEnc;
    }

    public void alteraData(int n) {
        LocalDate newdata =  ChronoUnit.DAYS.addTo(this.data, n);
        this.setData(newdata);
    }
    public void addArtigoListaUtil( String codigoUtil, Artigo a) {//adiciona artigo num utilizador
        this.utilizadores.get(codigoUtil).addArtigoUtil(a);
    }
    public void addArtigo(Artigo art) {//adiciona artigo na lista de artigos
        this.artigos.put(art.getCodigo(), art.clone());
    }
    public void remArtigo(String codigo) {
        this.artigos.remove(codigo);
    }

    public void addUtilizador(Utilizador utilizador) {
        this.utilizadores.put(utilizador.getEmail(),utilizador.clone());
    }
    public void addTransportadora(Transportadora transp) {
        this.transportadoras.put(transp.getNome(),transp.clone());
    }
    public void addComprasUtil(String emailUtil,Artigo art) {
        this.utilizadores.get(emailUtil).addArtigocompras(this.getArtigos().get(art.getCodigo()).clone());
    }
    public void remComprasUtil(String emailUtil,Artigo art) {
        this.utilizadores.get(emailUtil).remArtigocompras(this.getArtigos().get(art.getCodigo()).clone());
    }
    public void remListaArtigosUtil(String emailUtil,Encomenda enc) {
        this.utilizadores.get(emailUtil).removeListaArtigos(enc.clone());
        
    }

    public void addEncomenda( Encomenda enc) {
        this.encomendas.put(enc.getCodigo(), enc.clone());
    }

    public Map<String, Sapatilha> getSapatilhas(Utilizador util) {
        Map<String, Sapatilha> sapatilhas = new HashMap<>();
    
        for (Map.Entry<String, Artigo> entry : artigos.entrySet()) {
            String chave = entry.getKey();
            Artigo artigo = entry.getValue();
            
            if ((artigo instanceof Sapatilha) && util.getAvender().containsKey(chave) == false) {
                Sapatilha sapatilha = (Sapatilha) artigo;
                sapatilhas.put(chave, sapatilha);
            }
        }
        return sapatilhas;
    }
    public Map<String, Tshirt> getTshirts(Utilizador util) {
        Map<String, Tshirt> tshirts = new HashMap<>();
    
        for (Map.Entry<String, Artigo> entry : artigos.entrySet()) {
            String chave = entry.getKey();
            Artigo artigo = entry.getValue();

            if ((artigo instanceof Tshirt) && util.getAvender().containsKey(chave) == false) {
                Tshirt tshirt = (Tshirt) artigo;
                tshirts.put(chave, tshirt);
            }
        }
        return tshirts;
    }
        
    public Map<String, Mala> getMalas(Utilizador util) {
        Map<String, Mala> malas = new HashMap<>();

        for (Map.Entry<String, Artigo> entry : artigos.entrySet()) {
            String chave = entry.getKey();
            Artigo artigo = entry.getValue();

            if ((artigo instanceof Mala) && util.getAvender().containsKey(chave) == false) {
                Mala mala = (Mala) artigo;
                malas.put(chave, mala);
            }
        }
        return malas;
    }

    public void carregaEstado(String nomeficheiro) throws IOException, ClassNotFoundException {
        Vintage e = aux(nomeficheiro);
        this.artigos = e.getArtigos();
        this.utilizadores = e.getUtilizadores();
        this.encomendas = e.getEncomendas();
        this.transportadoras = e.getTransportadoras();
        this.data = e.getData();
        this.nome = e.getNome();
        this.valorGanhoTotal = e.getValorGanhoTotal();
    }

    public Vintage aux (String nomeficheiro) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeficheiro));
        Vintage e = (Vintage) ois.readObject();
        ois.close();
        return e;

    }
    public void guardaEstado(String nomeFicheiro) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(nomeFicheiro);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    public void removerArtigos(Map<String,Artigo> artigosAremover){
        for(Artigo art : artigosAremover.values()){
           this.artigos.remove(art.getCodigo());
        }
    }
  
    public void verificarEncomendas(){
        for(Encomenda enc : this.encomendas.values()) {
            if(enc.getDataEntrega().isBefore(this.data) && enc.getEstado().equals(Encomenda.Estado.Finalizada)) {
                enc.setEstado(Encomenda.Estado.Expedida);
                this.calculaValorGanhoTotal();
                System.out.println("A encomenda feita por " + enc.getUtilizador().getNome() + " no valor de " + enc.getPrecofinal() + " foi entregue no dia " + enc.getDataEntrega());
                System.out.println("Artigos:");
                for(Artigo art : enc.getArtigos().values()) {
                    System.out.println(art.toString());
                }
            }
        }
    }
//---------------------------..------------FUNCOES PEDIDAS NO GUIAO ----------------------------------------------------------------------


    public Utilizador maisFaturou() {
        Utilizador maior = new Utilizador();
        for(Utilizador util : utilizadores.values()){
            if(util.getValorTotalVendido() > maior.getValorTotalVendido()) maior = util.clone();
        }
        return maior;
    }
}
