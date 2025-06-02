package MODELO;


    public class Estoque {
        private String produto_Nome;
        private static int quantidade;

        public Estoque() { }

        // produto_Nome
        public String getProduto_Nome() {
            return produto_Nome;
        }
        public void setProduto_Nome(String produto_Nome) {
            this.produto_Nome = produto_Nome;
        }

        // quantidade
        public static int getQuantidade() {
            return quantidade;
        }
        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }
    }




