public class Tshirt extends Artigo{
    public enum Tamanho {
        S,
        M,
        L,
        XL
    }
    private Tamanho tamanho;
    public enum Padrao {
        Liso,
        Riscas,
        Palmeiras
    }
    //liso nao tem desconto
    //restantes tem 50 % se forem usados
    private Padrao padrao;
    
    public Tshirt() {
        super();
        this.tamanho = null;
        this.padrao = null;
    }


    public Tshirt(String codigo, String descricao, boolean enovo, Estado estado, String marca, double precobase, double precocorrigido, 
                int numeroDonos, Transportadora transportadora, Tamanho tamanho, Padrao padrao) {
        super(codigo, descricao, enovo, estado,marca,precobase,precocorrigido,numeroDonos,transportadora);
        this.tamanho = tamanho;
        this.padrao = padrao;
    }

    public Tshirt(Tshirt tshirt) {
        super(tshirt);
        this.tamanho = tshirt.getTamanho();
        this.padrao = tshirt.getPadrao();
    }

    public Tamanho getTamanho(){
        return this.tamanho;
    }
    public void setTamanho(Tamanho tamanho){
        this.tamanho = tamanho;
    }

    public Padrao getPadrao(){
        return this.padrao;
    }
    public void setPadrao(Padrao padrao){
        this.padrao = padrao;
    }
    public void verificaTamanho(int tamanho) {
        if(tamanho == 1) setTamanho(Tamanho.S); 
        else if(tamanho == 2) setTamanho(Tamanho.M);
        else if(tamanho == 3) setTamanho(Tamanho.L);
        else if(tamanho == 4) setTamanho(Tamanho.XL);
    }
    public void verificaPadrao(int padrao) {
        if(padrao == 1) setPadrao(Padrao.Liso);
        else if(padrao == 2) setPadrao(Padrao.Riscas);
        else if(padrao == 3) setPadrao(Padrao.Palmeiras);
    }

    @Override
    public void valorCorrigido() {
        if(getPadrao()!= Padrao.Liso && getEnovo() == false) {
            setPrecocorrigido(getPrecobase()*0.5);
        }
        else setPrecocorrigido(getPrecobase());
    }
    
    @Override
    public Artigo clone() {
    return new Tshirt(this);
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Tshirt tshirt = (Tshirt) o;
        return getTamanho() == tshirt.getTamanho() 
               && getPadrao() == tshirt.getPadrao();
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append (super.toString());
        sb.append("Tamanho: ").append(this.tamanho).append("\n");
        sb.append("Padrão: ").append(this.padrao).append("\n");
        return sb.toString();
    }
}
