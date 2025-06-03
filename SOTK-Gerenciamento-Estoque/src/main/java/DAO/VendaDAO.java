package DAO;

import MODELO.Venda;
import Util.ConexaoMySQL;

import java.sql.*;
import java.util.List;

public class VendaDAO {

    public void registrarVenda(int idSede, List<Venda.ItemVenda> itens) {
        Connection conn = null;
        PreparedStatement stmtVenda = null;
        PreparedStatement stmtItem = null;
        PreparedStatement stmtAtualizaEstoque = null;

        try {
            conn = ConexaoMySQL.getConexaoMySQL();
            conn.setAutoCommit(false);

            // Inserir venda
            String sqlVenda = "INSERT INTO tb_venda (id_sede, Venda_data) VALUES (?, CURDATE())";
            stmtVenda = conn.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS);
            stmtVenda.setInt(1, idSede);
            stmtVenda.executeUpdate();

            ResultSet rs = stmtVenda.getGeneratedKeys();
            if (!rs.next()) throw new SQLException("Falha ao obter ID da venda.");
            int idVenda = rs.getInt(1);

            // Inserir itens da venda
            String sqlItem = "INSERT INTO item_venda (id_venda, id_produto, quantidade) VALUES (?, ?, ?)";
            stmtItem = conn.prepareStatement(sqlItem);

            // Atualizar estoque
            String sqlAtualiza = "UPDATE tb_estoque SET quantidade = quantidade - ? WHERE Sede_id = ? AND Prod_id = ?";
            stmtAtualizaEstoque = conn.prepareStatement(sqlAtualiza);

            for (Venda.ItemVenda item : itens) {
                // Inserir item da venda
                stmtItem.setInt(1, idVenda);
                stmtItem.setInt(2, item.getIdProduto());
                stmtItem.setInt(3, item.getQuantidade());
                stmtItem.addBatch();

                // Atualizar estoque
                stmtAtualizaEstoque.setInt(1, item.getQuantidade());
                stmtAtualizaEstoque.setInt(2, idSede);
                stmtAtualizaEstoque.setInt(3, item.getIdProduto());
                stmtAtualizaEstoque.addBatch();
            }

            stmtItem.executeBatch();
            stmtAtualizaEstoque.executeBatch();
            conn.commit();
            System.out.println("✅ Venda registrada com sucesso!");

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Erro ao fazer rollback: " + ex.getMessage());
            }
            System.err.println("Erro ao registrar venda: " + e.getMessage());
        } finally {
            try {
                if (stmtVenda != null) stmtVenda.close();
                if (stmtItem != null) stmtItem.close();
                if (stmtAtualizaEstoque != null) stmtAtualizaEstoque.close();
                if (conn != null) conn.setAutoCommit(true);
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}
