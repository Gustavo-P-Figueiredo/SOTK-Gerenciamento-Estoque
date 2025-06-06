package MODELO;

public class Produto {
    static int Produto_Id;
    String Produto_Nome;
    double Produto_Valor;
    int Quant_CD;

    public static int getProduto_Id(){
        return Produto_Id;
    }

    public void setProduto_Id(int produto_Id) {
        this.Produto_Id = produto_Id;
    }

    public String getProduto_Nome() {
        return Produto_Nome;
    }

    public void setProduto_Nome(String produto_Nome) {
        this.Produto_Nome = produto_Nome;

    }

    public double getProduto_Valor() {
        return Produto_Valor;
    }

    public void setProduto_Valor(double produto_Valor) {
        this.Produto_Valor = produto_Valor;
    }

    public int getQuant_CD() {
        return Quant_CD;
    }

    public void setQuant_CD(int quant_CD) {
        this.Quant_CD = quant_CD;
    }
}