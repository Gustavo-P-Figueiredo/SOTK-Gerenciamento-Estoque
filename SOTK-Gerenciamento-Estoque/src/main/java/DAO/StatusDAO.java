package DAO;

import MODELO.Pedido;
import Util.ConexaoMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusDAO {

    public static String consultarStatus(int idPedido) {
        String status = null;

        String sql = "SELECT status FROM pedido_status WHERE pedido_id = ?";

        try (Connection conn = ConexaoMySQL.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                status = rs.getString("status");
            } else {
                System.out.println("Pedido não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar status do pedido: " + e.getMessage());
        }

        return status;
    }

    public static boolean alterarStatus(Pedido pedido) {
        String sql = "UPDATE pedido_status SET status = ? WHERE pedido_id = ?";

        try (Connection conn = ConexaoMySQL.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("Atualizando pedido ID " + pedido.getPedido_Id() + " para status: " + pedido.getPedido_Status());

            stmt.setString(1, pedido.getPedido_Status());
            stmt.setInt(2, pedido.getPedido_Id());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar status do pedido: " + e.getMessage());
            return false;
        }
    }
}
