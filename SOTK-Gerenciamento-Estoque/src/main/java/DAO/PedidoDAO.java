package DAO;

import MODELO.Pedido;
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

    public static void realizarPedido(Pedido pedido) {
        String insertPedidoSQL = """
        INSERT INTO tb_pedido
          (pedido_quant, sede_id, prod_id, Pedido_Cidade, Pedido_Rua, Pedido_numero)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

        String checkEstoqueSQL = """
        SELECT quantidade
        FROM tb_estoque
        WHERE Sede_id = ? AND Prod_id = ?
        """;

        String updateEstoqueSQL = """
        UPDATE tb_estoque
        SET quantidade = quantidade + ?
        WHERE Sede_id = ? AND Prod_id = ?
        """;

        String insertEstoqueSQL = """
        INSERT INTO tb_estoque (Sede_id, Prod_id, quantidade)
        VALUES (?, ?, ?)
        """;

        Connection conn = null;

        try {
            conn = ConexaoMySQL.getConexaoMySQL();
            conn.setAutoCommit(false);

            try (PreparedStatement stmtPedido = conn.prepareStatement(insertPedidoSQL)) {
                stmtPedido.setInt(1, pedido.getPedido_Quant());
                stmtPedido.setInt(2, pedido.getSede_Id());
                stmtPedido.setInt(3, pedido.getProduto_Id());
                stmtPedido.setString(4, pedido.getPedido_Cidade());
                stmtPedido.setString(5, pedido.getPedido_Rua());
                stmtPedido.setInt(6, pedido.getPedido_numeracao());
                stmtPedido.executeUpdate();
            }

            boolean estoqueExiste;
            try (PreparedStatement stmtCheck = conn.prepareStatement(checkEstoqueSQL)) {
                stmtCheck.setInt(1, pedido.getSede_Id());
                stmtCheck.setInt(2, pedido.getProduto_Id());
                try (ResultSet rs = stmtCheck.executeQuery()) {
                    estoqueExiste = rs.next();
                }
            }

            if (estoqueExiste) {
                try (PreparedStatement stmtUpdate = conn.prepareStatement(updateEstoqueSQL)) {
                    stmtUpdate.setInt(1, pedido.getPedido_Quant());
                    stmtUpdate.setInt(2, pedido.getSede_Id());
                    stmtUpdate.setInt(3, pedido.getProduto_Id());
                    stmtUpdate.executeUpdate();
                }
            } else {
                try (PreparedStatement stmtInsert = conn.prepareStatement(insertEstoqueSQL)) {
                    stmtInsert.setInt(1, pedido.getSede_Id());
                    stmtInsert.setInt(2, pedido.getProduto_Id());
                    stmtInsert.setInt(3, pedido.getPedido_Quant());
                    stmtInsert.executeUpdate();
                }
            }

            conn.commit();
            System.out.println("[LOG] Pedido realizado e estoque atualizado com sucesso.");

        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao realizar pedido: " + e.getMessage());
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.err.println("[ERRO] Falha ao fazer rollback: " + ex.getMessage());
            }
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("[ERRO] Falha ao fechar conexão: " + e.getMessage());
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