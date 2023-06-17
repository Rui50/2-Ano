import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Encomenda implements Serializable {
    private String codigo;
    private Map<String, Artigo> artigos;
    public enum Dimensao {
        Pequeno,
        Medio,
        Grande
    }
    private Dimensao dimensao;
    private double precofinal;
    public enum Estado {
        Pendente,
        Finalizada,
        Expedida
    }
    private Estado estado;
    private LocalDate datacriacao;
    private Utilizador utilizador;
    private LocalDate dataEntrega;

    public Encomenda(){
        this.codigo = "";
        this.artigos = new HashMap<>();
        this.dimensao = null;
        this.precofinal = 0;
        this.estado = Estado.Pendente;
        this.datacriacao = null;
        this.utilizador = null;
        this.dataEntrega= null;
    }
    public Encomenda(String codigo, Map<String, Artigo> artigos,Dimensao dimensao, double precofinal, Estado estado, LocalDate datacriacao, 
                    Utilizador utilizador, LocalDate dataEntrega){
        this.codigo = codigo;
        this.artigos = artigos;
        this.dimensao = dimensao;
        this.precofinal = precofinal;
        this.estado = estado;
        this.datacriacao = datacriacao;
        this.utilizador = utilizador;
        this.dataEntrega = dataEntrega;
        
    }
    public Encomenda(Encomenda encomenda){
        this.codigo = encomenda.getCodigo();
        this.artigos = encomenda.getArtigos();
        this.dimensao = encomenda.getDimensao();
        this.precofinal = encomenda.getPrecofinal();
        this.estado = encomenda.getEstado();
        this.datacriacao = encomenda.getDatacriacao();
        this.utilizador = encomenda.getUtilizador();
        this.dataEntrega = encomenda.getDataEntrega();
    }

    public String getCodigo(){
        return this.codigo;
    }
    public void setCodigo(String codigo){
        this.codigo = codigo;
    }
    
    public Map<String, Artigo> getArtigos() {
        return this.artigos.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public void setArtigos(Map<String, Artigo> artigos) {
        this.artigos =  artigos.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public Dimensao getDimensao(){
        return this.dimensao;
    }
    public void setDimensao(Dimensao dimensao){
        this.dimensao = dimensao;
    }

    public double getPrecofinal(){
        return this.precofinal;
    }
    public void setPrecofinal(Double precofinal){
        this.precofinal = precofinal;
    }

    public Estado getEstado(){
        return this.estado;
    }
    public void setEstado(Estado estado){
        this.estado = estado;
    }
    public LocalDate getDatacriacao() {
        return this.datacriacao;
    }
    public void setDatacriacao(LocalDate datacriacao){
        this.datacriacao = datacriacao;
    }

    public Utilizador getUtilizador(){
        return this.utilizador;
    }
    public void setUtilizador(Utilizador utilizador){
        this.utilizador = utilizador;
    }
    public LocalDate getDataEntrega() {
        return this.dataEntrega;
    }
    public void setDataEntrega(LocalDate dataEntrega){
        this.dataEntrega = dataEntrega;
    }

    public void addArtigo (Artigo a) {
        this.artigos.put(a.getCodigo(), a.clone());
    }

    public void remArtigo (String codigo){
        this.artigos.remove(codigo);
    }
    public void tamanhoEncomenda(){
        if (artigos.values().size()==1) setDimensao(Dimensao.Pequeno);
        else if (artigos.values().size()>1 && artigos.values().size()<= 5) setDimensao(Dimensao.Medio);
        else if (artigos.values().size()> 5) setDimensao(Dimensao.Grande);
        
    }
    public  void calculaPrecofinal () {
        double total = 0;
        double valorbase = 0;

        if(this.getDimensao() == Encomenda.Dimensao.Pequeno) valorbase = 2;
        else if(this.getDimensao().equals(Encomenda.Dimensao.Medio)) valorbase = 5;
        else if(this.getDimensao().equals(Encomenda.Dimensao.Grande)) valorbase = 10;

        for(Artigo art : this.getArtigos().values()){
            if(art.getEnovo()== true) total += art.getPrecocorrigido() + 0.5 + art.getTransportadora().calculaCustoexpedicao(valorbase,artigos.values().size());
            else total += art.getPrecocorrigido() + 0.25 + art.getTransportadora().calculaCustoexpedicao(valorbase,artigos.values().size());  
        }
        this.setPrecofinal(total);
    }

    public boolean devolveEncomenda(LocalDate data) {
        if (ChronoUnit.DAYS.between(data, this.getDatacriacao()) > 2) return true;
        return false;
    }
    
    public Encomenda clone() {
        return new Encomenda(this);
    }

    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Encomenda that = (Encomenda) o;
        return this.codigo.equals(that.getCodigo()) &&
                this.datacriacao.isEqual(that.getDatacriacao()) &&
                this.utilizador.equals(that.getUtilizador()) &&
                this.estado.equals(that.getEstado()) &&
                this.dataEntrega.isEqual(that.getDataEntrega()) &&
                this.dimensao.equals(that.getDimensao())&&
                this.artigos.equals(that.getArtigos()) &&
                this.precofinal == that.getPrecofinal() ;

    }
    public String toString() {
        StringBuilder sb = new StringBuilder(" ");
        sb.append("Código: ").append(this.codigo).append("\n");
        sb.append("Data de Criação: ").append(this.datacriacao.toString()).append("\n");
        sb.append("Utilizador: ").append(this.utilizador.getNome()).append("\n");
        sb.append("Estado: ").append(this.estado).append("\n");
        sb.append("Data de Entrega: ").append(this.dataEntrega.toString()).append("\n");
        sb.append("Dimensao: ").append(this.dimensao).append("\n");
        sb.append("Produtos: ").append(this.artigos.values().toString()).append("\n");
        sb.append("Preço final: ").append(this.precofinal).append("\n");
        return sb.toString();
    }
}
