import java.io.Serializable;

public abstract class Artigo implements Serializable {
    private String codigo;
    private String descricao;
    private boolean enovo;//pode se tirar isto porque sd o n donos for 0 ja indica que é novo
    public enum Estado {
        NOVO,
        GASTO,
        MEDIO,
        OTIMO
    }
    private Estado estado;
    private String marca;
    private double precobase;
    private double precocorrigido;
    private int numeroDonos;
    private Transportadora transportadora;

    public Artigo(){
        this.codigo = "";
        this.descricao = "";
        this.enovo = true;
        this.estado = null;
        this.marca = "";
        this.precobase = 0;
        this.precocorrigido = 0;
        this.numeroDonos = 0;
        this.transportadora = null;
    }

    public Artigo(String codigo, String descricao, boolean enovo, Estado estado, String marca,
     double precobase, double precocorrigido, int numeroDonos, Transportadora transportadora){
        this.codigo = codigo;
        this.descricao = descricao;
        this.enovo = enovo;
        this.estado = estado;
        this.marca = marca;
        this.precobase = precobase;
        this.precocorrigido = precocorrigido;
        this.numeroDonos = numeroDonos;
        this.transportadora = transportadora;
    }

    public Artigo(Artigo artigo){
        this.codigo = artigo.getCodigo();
        this.descricao = artigo.getDescricao();
        this.enovo = artigo.getEnovo();
        this.estado = artigo.getEstado();
        this.marca = artigo.getMarca();
        this.precobase = artigo.getPrecobase();
        this.precocorrigido = artigo.getPrecocorrigido();
        this.numeroDonos = artigo.getNumeroDonos();
        this.transportadora = artigo.getTransportadora();
    }

    public String getCodigo(){
        return this.codigo;
    }
    public void setCodigo(String codigo){
        this.codigo = codigo;
    }

    public String getDescricao(){
        return this.descricao;
    }
    public void setDescricao (String descricao){
        this.descricao = descricao;
    }

    public boolean getEnovo(){
        return this.enovo;
    }
    public void setEnovo(boolean enovo){
        this.enovo = enovo;
    }

    public Estado getEstado(){
        return this.estado;
    }
    public void setEstado(Estado estado){
        this.estado = estado;
    }

    public String getMarca(){
        return this.marca;
    }
    public void setMarca(String marca){
        this.marca = marca;
    }

    public double getPrecobase(){
        return this.precobase;
    }
    public void setPrecobase(double precobase){
        this.precobase = precobase;
    }

    public double getPrecocorrigido(){
        return this.precocorrigido;
    }
    public void setPrecocorrigido(double precocorrigido){
        this.precocorrigido = precocorrigido;
    }

    public int getNumeroDonos(){
        return this.numeroDonos;
    }
    public void setNumeroDonos(int numeroDonos){
        this.numeroDonos = numeroDonos;
    }
    public Transportadora getTransportadora(){
        return this.transportadora;
    }
    public void setTransportadora(Transportadora transportadora){
        this.transportadora = transportadora;
    }
    public void avaliarEstado(int estado) {
        if(1<=estado && estado <=2) setEstado(Estado.GASTO); 
        else if(3<=estado && estado <=4) setEstado(Estado.MEDIO);
        else if(estado == 5) setEstado(Estado.OTIMO);
      }

    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Artigo that = (Artigo) o;
        return this.codigo.equals(that.getCodigo()) &&
                this.enovo == that.getEnovo() &&
                this.estado.equals(that.getEstado()) &&
                this.marca.equals(that.getMarca()) &&
                this.precobase == that.getPrecobase() &&
                this.precocorrigido == that.getPrecocorrigido() &&
                this.transportadora.equals(that.getTransportadora());

    }
    public String toString() {
        StringBuilder sb = new StringBuilder(" ");
        sb.append("Codigo: ").append(this.codigo).append("\n");
        sb.append("Descrição: ").append(this.descricao).append("\n");
        sb.append("É novo: ").append(this.enovo).append("\n");
        sb.append("Estado: ").append(this.estado).append("\n");
        sb.append("Marca: ").append(this.marca).append("\n");
        sb.append("Preço base: ").append(this.precobase).append("\n");
        sb.append("Preço corrigido: ").append(this.precocorrigido).append("\n");
        sb.append("Número de donos: ").append(this.numeroDonos).append("\n");
        sb.append("Tranpostadora: ").append(this.getTransportadora().getNome()).append("\n");
        return sb.toString();
    }
    public double valorEstado(Estado estado){
        if(estado == Estado.OTIMO) return 2;
        else if(estado == Estado.MEDIO) return 3;
        else if(estado == Estado.GASTO) return 4;
        return 1;
    }
    public abstract void valorCorrigido ();  
    public abstract Artigo clone();

}
