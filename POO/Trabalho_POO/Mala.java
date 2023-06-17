
public class Mala extends Artigo{
    public enum Dimensao {
        Pequena,
        Media,
        Grande
    }
    public enum Material {
        tecido,
        poliC,
        poliP,
        ABS
    }
    private Dimensao dimensao;
    private Material material;
    private int anocolecao;
    private boolean premium;

    public Mala() {
        super();
        this.dimensao = null;
        this.material = null;
        this.anocolecao = 0;
        this.premium = false;
    }


    public Mala(String codigo, String descricao, boolean enovo, Estado estado, String marca, double precobase, double precocorrigido, 
                int numeroDonos, Transportadora transportadora,Dimensao dimensao, Material material, int anocolecao, boolean premium) {
        super(codigo, descricao, enovo, estado,marca,precobase,precocorrigido,numeroDonos,transportadora);
        this.dimensao = dimensao;
        this.material = material;
        this.anocolecao = anocolecao;
        this.premium = premium;
    }

    public Mala(Mala mala) {
        super(mala);
        this.dimensao = mala.getDimensao();
        this.material = mala.getMaterial();
        this.anocolecao = mala.getAnocolecao();
        this.premium = mala.getPremium();
    }

    public Dimensao getDimensao(){
        return this.dimensao;
    }
    public void setDimensao(Dimensao dimensao){
        this.dimensao = dimensao;
    }

    public Material getMaterial(){
        return this.material;
    }
    public void setMaterial(Material material){
        this.material = material;
    }

    public int getAnocolecao(){
        return this.anocolecao;
    }
    public void setAnocolecao(int anocolecao){
        this.anocolecao = anocolecao;
    }
    public boolean getPremium() {
        return this.premium;
    }
    public void setpremium(boolean premium){
        this.premium = premium;
    }

    public double getValordimensao() {
        if(this.dimensao == Dimensao.Pequena) return 1;
        else if(this.dimensao == Dimensao.Media) return 2;
        else if(this.dimensao == Dimensao.Grande) return 3;
        return 0;
    }
    public double getValorMaterial() {
        if(this.material == Material.tecido) return 1;
        else if(this.material == Material.poliC) return 2;
        else if(this.material == Material.poliP) return 2;
        else if(this.material == Material.ABS) return 3;
        return 0;
    }
    public void verificaDimensao(int dimensao) {
        if(dimensao == 1) setDimensao(Dimensao.Pequena); 
        else if(dimensao == 2) setDimensao(Dimensao.Media);
        else if(dimensao == 3) setDimensao(Dimensao.Grande);
    }
    public void verificaMaterial(int material) {
        if(material == 1) setMaterial(Material.tecido);
        else if(material == 2) setMaterial(Material.poliC);
        else if(material == 3) setMaterial(Material.poliP);
        else if(material == 4) setMaterial(Material.ABS);
    }

    @Override
    public void valorCorrigido() {
        if(premium == true){
           double n = getPrecobase();
           double anos = 2023-getAnocolecao();//mudar quando se definir uma data
            setPrecocorrigido(n + n*0.1*valorEstado(getEstado())*anos);
        }
        else{
            double n = getPrecobase();
            double anos = 2023-getAnocolecao();//mudar quando se definir uma data
            double valor = n-n*0.01*(anos*getValordimensao()*getValorMaterial());
            setPrecocorrigido(valor);
        }

    }
    @Override
    public Artigo clone() {
    return new Mala(this);
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Mala mala = (Mala) o;
        return getDimensao() == mala.getDimensao() 
               && getMaterial() == mala.getMaterial() 
               && getAnocolecao() == mala.getAnocolecao()
               && Boolean.compare(mala.getPremium(), getPremium()) == 0;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append (super.toString());
        sb.append("Dimensao: ").append(this.dimensao).append("\n");
        sb.append("Material: ").append(this.material).append("\n");
        sb.append("Ano coleção: ").append(this.anocolecao).append("\n");
        sb.append("Premium: ").append(this.premium).append("\n");
        return sb.toString();
    }
}
