import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Utilizador implements Serializable{
    private String codigo;
    private String email;
    private String password;
    private String nome;
    private String morada;
    private int NIF;
    private Map<String, Artigo> compras;
    private Map<String, Artigo> avender;
    private Map<String, Artigo> vendidos;
    private double valortotalVendido;

    public Utilizador(){
        this.codigo = "";
        this.email ="";
        this.password = "";
        this.nome = "";
        this.morada = "";
        this.NIF = 0;
        this.compras = new HashMap<>();
        this.avender = new HashMap<>();
        this.vendidos = new HashMap<>();
        this.valortotalVendido = 0;
    }

    public Utilizador(String codigo, String email, String password, String nome, String morada, int NIF,Map<String, Artigo> compras,Map<String,
                        Artigo> avender,Map<String, Artigo> vendidos, double valorTotalVendido) {
        this.codigo = codigo;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.morada = morada;
        this.NIF = NIF;
        this.valortotalVendido = valorTotalVendido;
        this.compras = compras;
        this.avender = avender;
        this.vendidos = vendidos;
    }

    public Utilizador(Utilizador utilizador){
        this.codigo = utilizador.getCodigo();
        this.email = utilizador.getEmail();
        this.password = utilizador.getPassword();
        this.nome = utilizador.getNome();
        this.morada = utilizador.getMorada();
        this.NIF = utilizador.getNIF();
        this.valortotalVendido = utilizador.getValorTotalVendido();
        this.compras = utilizador.getCompras();
        this.avender = utilizador.getAvender();
        this.vendidos = utilizador.getVendidos();
    }


    public String getCodigo(){
        return this.codigo;
    }
    public void setCodigo(String codigo){
        this.codigo = codigo;
    }

    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getNome(){
        return this.nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public String getMorada(){
        return this.morada;
    }
    public void setMorada(String morada){
        this.morada = morada;
    }

    public int getNIF(){
        return this.NIF;
    }
    public void setNIF(int NIF){
        this.NIF = NIF;
    }
    public Map<String, Artigo> getCompras() {
        return this.compras.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public void setCompras(Map<String, Artigo> compras) {
        this.compras =  compras.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }
    public Map<String, Artigo> getVendidos() {
        return this.vendidos.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public void setVendidos(Map<String, Artigo> vendidos) {
        this.vendidos =  vendidos.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public Map<String, Artigo> getAvender() {
        return this.avender.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public void setAvender(Map<String, Artigo> avender) {
        this.avender =  avender.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }
    
    public double getValorTotalVendido(){
        return this.valortotalVendido;
    }
    public void setValorTotalVendido(double valortotal){
        this.valortotalVendido = valortotal;
    }

    public void calculaValorTotal() {
        double total = 0;
        for(Artigo art : vendidos.values()){
            total += art.getPrecocorrigido();  
        }
        this.setValorTotalVendido(total);
    }
    public void removeListaArtigos(Encomenda e) {
        for(Artigo art : e.getArtigos().values()){
            this.compras.remove(art.getCodigo());
        }
    }
    public void addArtigoUtil( Artigo a) {
        this.avender.put(a.getCodigo(), a.clone());
    }
    public void remArtigoUtil( Artigo a) {
        this.avender.remove(a.getCodigo());
    }
    public void addArtigovendidos( Artigo a) {
        this.vendidos.put(a.getCodigo(), a.clone());
    }
    public void remArtigovendidos( Artigo a) {
        this.vendidos.remove(a.getCodigo());
    }
    public void addArtigocompras( Artigo a) {
        this.compras.put(a.getCodigo(), a.clone());
    }
    public void remArtigocompras( Artigo a) {
        this.compras.remove(a.getCodigo());
    }

    public  Utilizador clone(){
        return new Utilizador(this);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilizador that = (Utilizador) o;
        return Objects.equals(getCodigo(), that.getCodigo())
                && Objects.equals(getEmail(), that.getEmail())
                && Objects.equals(getPassword(), that.getPassword())
                && Objects.equals(getNome(), that.getNome())
                && Objects.equals(getMorada(), that.getMorada())
                && getNIF() == that.getNIF();
    }
    public String toString() {
        StringBuilder sb = new StringBuilder(" ");
        sb.append("Codigo: ").append(this.codigo).append("\n");
        sb.append("Nome: ").append(this.nome).append("\n");
        sb.append("Email: ").append(this.email).append("\n");
        sb.append("Morada: ").append(this.morada).append("\n");
        sb.append("NIF: ").append(this.NIF).append("\n");
        sb.append("Compras: ").append(this.compras.values()).append("\n");
        sb.append("A vender: ").append(this.avender.values()).append("\n");
        sb.append("Vendidos: ").append(this.vendidos.values()).append("\n");
        sb.append("Valor total vendido: ").append(this.valortotalVendido).append("\n");
        return sb.toString();
    }
}
