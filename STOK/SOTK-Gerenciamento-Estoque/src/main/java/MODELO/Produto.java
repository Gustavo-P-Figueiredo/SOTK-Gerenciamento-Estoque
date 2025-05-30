package MODELO;

public class Produto {
     private String Produto_Nome;
     private double Produto_Valor;
    private int Produto_Id;

    public String getProduto_Nome() {
    return Produto_Nome;
}

    public void setProduto_Nome(String nome) {
    this.Produto_Nome = nome;
}


    public double getProduto_Valor() {
        return Produto_Valor;
    }

    public void setProduto_Valor(double valor) {
        this.Produto_Valor = valor;
    }

    public int getProduto_Id(){return Produto_Id; }

    public void setProduto_Id(int id) {
    }
}