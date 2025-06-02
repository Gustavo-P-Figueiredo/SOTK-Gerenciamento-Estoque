package MODELO;

import java.sql.Date;

public class Pedido {
    public static int quant;
    int Pedido_Id;
    Date Pedido_Data;
    int Pedido_Quant;
    String Pedido_Cidade;
    String Pedido_Rua;
    int Pedido_numeracao;
    int Produto_Id;
    int Sede_Id; //Get Setter

    public int getPedido_Id() {
        return Pedido_Id;
    }

    public void setPedido_Id(int pedido_id) {
        this.Pedido_Id = pedido_id;
    }

    public Date getPedido_Data() {
        return Pedido_Data;
    }

    public void setPedido_Data(Date pedido_Data) {
        this.Pedido_Data = pedido_Data;
    }

    public int getPedido_Quant() {
        return Pedido_Quant;
    }

    public void setPedido_Quant(int pedido_Quant) {
        this.Pedido_Quant = pedido_Quant;
    }

    public String getPedido_Cidade() {
        return Pedido_Cidade;
    }

    public void setPedido_Cidade(String pedido_Cidade) {
        Pedido_Cidade = pedido_Cidade;
    }

    public String getPedido_Rua() {
        return Pedido_Rua;
    }

    public void setPedido_Rua(String pedido_Rua) {
        Pedido_Rua = pedido_Rua;
    }

    public int getPedido_numeracao() {
        return Pedido_numeracao;
    }

    public void setPedido_numeracao(int pedido_numeracao) {
        Pedido_numeracao = pedido_numeracao;
    }

    public int getProduto_Id(){
        return Produto_Id;
    }

    public void setProduto_Id(int produto_Id) {
        this.Produto_Id = produto_Id;
    }

    public int getSede_Id() {
        return Sede_Id;
    }

    public void setSede_Id(int sede_Id){
        this.Sede_Id = sede_Id;

    }

}