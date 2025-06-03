package MODELO;

import java.util.List;

public class Venda {
    private int id;
    private int idSede;
    private List<ItemVenda> itens;

    public static class ItemVenda {
        private int idProduto;
        private int quantidade;

        public ItemVenda(int idProduto, int quantidade) {
            this.idProduto = idProduto;
            this.quantidade = quantidade;
        }

        public int getIdProduto() {
            return idProduto;
        }

        public int getQuantidade() {
            return quantidade;
        }
    }
}
