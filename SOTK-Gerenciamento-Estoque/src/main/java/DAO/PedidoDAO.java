package DAO;

import MODELO.Pedido;
import MODELO.Produto;
import Util.ConexaoMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PedidoDAO {
    public static boolean sedeExiste(int sedeId) throws SQLException {
        String sql = "SELECT 1 FROM tb_sede WHERE Sede_id = ?";
        try (Connection conn = ConexaoMySQL.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sedeId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Método para cadastrar um pedido e atualizar o estoque do produto no centro de distribuição (CD)
    public static boolean cadastrarPedido(Pedido pedido) {
        // SQL para consultar o estoque atual no CD
        String sqlEstoque = "SELECT Quant_CD FROM tb_produto WHERE Prod_id = ?";
        // SQL para atualizar o estoque no CD (diminuir a quantidade)
        String sqlUpdateEstoque = "UPDATE tb_produto SET Quant_CD = Quant_CD - ? WHERE Prod_id = ?";
        // SQL para inserir o pedido na tabela de pedidos
        String sqlInserirPedido = "INSERT INTO tb_pedido (prod_id, quantidade, sede_id, data_pedido) VALUES (?, ?, ?, NOW())";

        // Abre a conexão com o banco
        try (Connection conn = ConexaoMySQL.getConexaoMySQL()) {
            conn.setAutoCommit(false); // Inicia a transação manualmente

            try (
                    PreparedStatement psEstoque = conn.prepareStatement(sqlEstoque);
                    PreparedStatement psUpdate = conn.prepareStatement(sqlUpdateEstoque);
                    PreparedStatement psPedido = conn.prepareStatement(sqlInserirPedido);
            ) {
                // 1. Verifica o estoque atual do produto no CD
                psEstoque.setInt(1, pedido.getProduto_Id());
                ResultSet rs = psEstoque.executeQuery();

                if (!rs.next()) {
                    System.out.println("Produto com ID " + pedido.getProduto_Id() + " não encontrado no estoque do CD. Fale com o time de compras.");
                    conn.rollback(); // Desfaz qualquer operação
                    return false;
                }

                int estoqueAtual = rs.getInt("Quant_CD");

                if (estoqueAtual < pedido.getPedido_Quant()) {
                    System.out.println("Estoque insuficiente no CD para o produto ID: " + pedido.getProduto_Id() +
                            ". Disponível: " + estoqueAtual + ", Solicitado: " + pedido.getPedido_Quant());
                    conn.rollback(); // Desfaz qualquer operação
                    return false;
                }

                // 2. Atualiza o estoque no CD (retira a quantidade do pedido)
                psUpdate.setInt(1, pedido.getPedido_Quant());
                psUpdate.setInt(2, pedido.getProduto_Id());
                psUpdate.executeUpdate();

                // 3. Insere o pedido na tabela de pedidos
                psPedido.setInt(1, pedido.getProduto_Id());
                psPedido.setInt(2, pedido.getPedido_Quant());
                psPedido.setInt(3, pedido.getSede_Id());
                psPedido.executeUpdate();

                conn.commit(); // Confirma todas as operações no banco
                System.out.println("Pedido cadastrado com sucesso!");
                return true;

            } catch (SQLException e) {
                conn.rollback(); // Se der erro, desfaz tudo
                System.out.println("Erro ao cadastrar pedido. Transação revertida.");
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

    public List<Pedido> pedidoListar() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM tb_pedido";

        try (Connection conn = ConexaoMySQL.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setPedido_Id(rs.getInt("pedido_id"));
                pedido.setPedido_Data(rs.getDate("pedido_data"));
                pedido.setPedido_Quant(rs.getInt("pedido_quant"));
                pedido.setProduto_Id(rs.getInt("Prod_id"));
                pedido.setSede_Id(rs.getInt("Sede_id"));
                pedido.setPedido_Cidade(rs.getString("pedido_Cidade"));
                pedido.setPedido_Rua(rs.getString("pedido_Rua"));
                pedido.setPedido_numeracao(rs.getInt("pedido_numero"));

                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar pedidos realizados.");
            e.printStackTrace();
        }

        return pedidos;
    }

}










