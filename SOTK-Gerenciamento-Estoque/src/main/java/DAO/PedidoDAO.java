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
    public static int getPedidoID;

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

    public static boolean cadastrarPedido(Pedido pedido) {
        String sqlEstoque = "SELECT Quant_CD FROM tb_produto WHERE Prod_id = ?";
        String sqlUpdateEstoque = "UPDATE tb_produto SET Quant_CD = Quant_CD - ? WHERE Prod_id = ?";
        String sqlInserirPedido = "INSERT INTO tb_pedido " +
                "(prod_id, pedido_quant, sede_id, pedido_data, Pedido_Cidade, Pedido_rua, Pedido_numero, Pedido_Status) " +
                "VALUES (?, ?, ?, NOW(), ?, ?, ?, 'Integrado')";

        try (Connection conn = ConexaoMySQL.getConexaoMySQL()) {
            conn.setAutoCommit(false);

            try (
                    PreparedStatement psEstoque = conn.prepareStatement(sqlEstoque);
                    PreparedStatement psUpdate = conn.prepareStatement(sqlUpdateEstoque);
                    PreparedStatement psPedido = conn.prepareStatement(sqlInserirPedido);
            ) {
                // Verificar estoque
                psEstoque.setInt(1, pedido.getProduto_Id());
                ResultSet rs = psEstoque.executeQuery();

                if (!rs.next()) {
                    System.out.println("Produto com ID " + pedido.getProduto_Id() + " não encontrado.");
                    conn.rollback();
                    return false;
                }

                int estoqueAtual = rs.getInt("Quant_CD");
                System.out.println("Estoque atual: " + estoqueAtual);

                if (estoqueAtual < pedido.getPedido_Quant()) {
                    System.out.println("Estoque insuficiente: disponível " + estoqueAtual +
                            ", solicitado " + pedido.getPedido_Quant() + " Acione o time de compras");
                    conn.rollback();
                    return false;
                }

                // Atualizar estoque
                psUpdate.setInt(1, pedido.getPedido_Quant());
                psUpdate.setInt(2, pedido.getProduto_Id());
                System.out.println("Executando: " + sqlUpdateEstoque.replace("?", pedido.getPedido_Quant() + "")
                        .replace("?", pedido.getProduto_Id() + ""));

                int afetadas = psUpdate.executeUpdate();
                System.out.println("Linhas afetadas na atualização: " + afetadas);

                if (afetadas == 0) {
                    System.out.println("Falha ao atualizar o estoque.");
                    conn.rollback();
                    return false;
                }

                // Inserir pedido
                psPedido.setInt(1, pedido.getProduto_Id());
                psPedido.setInt(2, pedido.getPedido_Quant());
                psPedido.setInt(3, pedido.getSede_Id());
                psPedido.setString(4, pedido.getPedido_Cidade());
                psPedido.setString(5, pedido.getPedido_Rua());
                psPedido.setInt(6, pedido.getPedido_numeracao());

                psPedido.executeUpdate();

                conn.commit();
                System.out.println("Pedido cadastrado com sucesso e estoque atualizado!");
                return true;

            } catch (SQLException e) {
                System.out.println("Erro na transação: " + e.getMessage());
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erro na conexão: " + e.getMessage());
            return false;
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












