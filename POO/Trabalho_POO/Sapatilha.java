import java.time.LocalDate;

public  class Sapatilha extends Artigo{

    private int tamanho;
    private boolean atacadores; // 0 - não / 1 - sim
    private String cor;
    private int anoLancamento;
    //apenas tem desconto nas usadas(colecao/ano diferentes) ou novas com tamanho maior que 45
    private boolean premium;

    public Sapatilha() {
        super();
        this.tamanho = 0;
        this.atacadores = false;
        this.cor = "";
        this.anoLancamento = 0;
        this.premium = false;
    }


    public Sapatilha(String codigo, String descricao, boolean enovo, Estado estado, String marca, double precobase, double precocorrigido, 
                    int numeroDonos, Transportadora transportadora, int tamanho, boolean atacadores, String cor, int anolancamento, boolean premium) {
        super(codigo, descricao, enovo, estado,marca,precobase,precocorrigido,numeroDonos,transportadora);
        this.tamanho = tamanho;
        this.atacadores = atacadores;
        this.cor = cor;
        this.anoLancamento = anolancamento;
        this.premium = premium;
    }

    public Sapatilha(Sapatilha sapatilha) {
        super(sapatilha);
        this.tamanho = sapatilha.getTamanho();
        this.atacadores = sapatilha.getAtacadores();
        this.cor = sapatilha.getCor();
        this.anoLancamento = sapatilha.getanoLancamento();
        this.premium = sapatilha.getpremium();
    }

    public int getTamanho(){
        return this.tamanho;
    }
    public void setTamanho(int tamanho){
        this.tamanho = tamanho;
    }

    public boolean getAtacadores(){
        return this.atacadores;
    }
    public void setAtacadores(boolean atacadores){
        this.atacadores = atacadores;
    }

    public String getCor(){
        return this.cor;
    }
    public void setCor(String cor){
        this.cor = cor;
    }
    public int getanoLancamento() {
        return this.anoLancamento;
    }
    public void setanolancamento(int ano){
        this.anoLancamento = ano;
    }
    public boolean getpremium() {
        return this.premium;
    }
    public void setpremium(boolean premium){
        this.premium = premium;
    }

    @Override
    public void valorCorrigido() {
        if (premium == true) {
            double n = getPrecobase();
            double anos = LocalDate.now().getYear()-anoLancamento;//mudar data
            setPrecocorrigido(n + n*0.1*valorEstado(getEstado())*anos);
        }
        else if(getEnovo()== false || getTamanho() > 45 ) {
            double n = getPrecobase();
            double valor = n-n*0.1*(1+getNumeroDonos())/valorEstado(getEstado());
            setPrecocorrigido(valor);
        }
        else setPrecocorrigido(getPrecobase());
    }
    @Override
    public Artigo clone() {
    return new Sapatilha(this);
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Sapatilha sapatilha = (Sapatilha) o;
        return getTamanho() == sapatilha.getTamanho() 
               && Boolean.compare(sapatilha.getAtacadores(), getAtacadores()) == 0 
               && this.cor.equals(sapatilha.getCor())
               && this.anoLancamento == sapatilha.getanoLancamento()
               && Boolean.compare(sapatilha.getpremium(), getpremium()) == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append (super.toString());
        sb.append("Tamanho: ").append(this.tamanho).append("\n");
        sb.append("Atacadores: ").append(this.atacadores).append("\n");
        sb.append("Cor: ").append(this.cor).append("\n");
        sb.append("Ano de Lançamento: ").append(this.anoLancamento).append("\n");
        sb.append("Premium: ").append(this.premium).append("\n");
        return sb.toString();
    }
}
