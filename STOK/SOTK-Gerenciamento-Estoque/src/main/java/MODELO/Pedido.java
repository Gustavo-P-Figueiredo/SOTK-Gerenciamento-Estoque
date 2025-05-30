package MODELO;

public class Pedido {
    int Pedido_Id;
    int Pedido_Quant;
    int Pedido_Numero;
    String Pedido_Rua;
    String Pedido_Cidade;
    int Sede_Id;
    int Produto_Id;

    public int getPedido_Id() {return Pedido_Id;}

    public int setPedido_Id(int id) {
        return id;
    }


    public int getPedido_Quant() {return Pedido_Quant;}

    public int setPedido_Quant(int quant) {
        return quant;
    }


    public int getPedido_Numero() {
            return Pedido_Numero;
        }

        public static void setPedido_Numero(int PedidoNumero) {
        }


        public String getPedido_Rua() {
            return Pedido_Rua;
        }

        public void setPedido_Rua(String rua) {
            this.Pedido_Rua = rua;
        }


        public String getPedido_Cidade() {
            return Pedido_Cidade;
        }

        public void setPedido_Cidade(String cidade) {
            this.Pedido_Cidade = cidade;
        }


    public int getProduto_Id(){
        return Produto_Id; }

    public void setProduto_Id(int id) {
    }


    public int getSede_Id() {
        return Sede_Id;
    }

    public static void setSede_Id(int sedeId) {
    }
}