import java.io.Serializable;

public class Transportadora implements Serializable{
    private String nome;
    private double margemlucro;
    private double imposto;

    public Transportadora() {
        this.nome = "";
        this.margemlucro = 0;
        this.imposto = 0;
    }

    public Transportadora (String nome, double margemlucro, double imposto){
        this.nome = nome;
        this.margemlucro = margemlucro;
        this.imposto = imposto;
    }

    public Transportadora (Transportadora transportadora){
        this.nome = transportadora.getNome();
        this.margemlucro = transportadora.getMargemlucro();
        this.imposto = transportadora.getImposto();
    }

    public String getNome(){
        return this.nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public double getMargemlucro(){
        return this.margemlucro;
    }
    public void setMargemlucro(double margemlucro){
        this.margemlucro = margemlucro;
    }
    public double getImposto(){
        return this.imposto;
    }
    public void setImposto(double imposto){
        this.imposto = imposto;
    }
    public double calculaCustoexpedicao(double valorbase,int tamanho) {
        return (valorbase *this.getMargemlucro()*(1+this.getImposto())*0.9)/tamanho;
    }
    public Transportadora clone() {
        return new Transportadora(this);
    }
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Transportadora that = (Transportadora) o;
        return this.nome.equals(that.getNome()) &&
                this.imposto == that.getImposto() &&
                this.margemlucro == that.getMargemlucro() ;

    }

    public String toString() {
        StringBuilder sb = new StringBuilder(" ");
        sb.append("Nome: ").append(this.nome).append("\n");
        sb.append("Margem lucro: ").append(this.margemlucro).append("\n");
        sb.append("Imposto: ").append(this.imposto).append("\n");
        return sb.toString();
    }
}
