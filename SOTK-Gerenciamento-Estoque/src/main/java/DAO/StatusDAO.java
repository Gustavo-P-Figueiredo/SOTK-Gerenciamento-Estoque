package DAO;

import MODELO.Pedido;
import Util.ConexaoMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatusDAO {

    public static List<Pedido> listarStatus() {
        List<Pedido> statusList = new ArrayList<>();

        String sql = "SELECT Pedido_Id, Pedido_Status FROM tb_pedido";

        try (Connection conn = ConexaoMySQL.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("Pedido_Id");
                String status = rs.getString("Pedido_Status");

                Pedido pedido = new Pedido();
                pedido.setPedido_Id(id);
                pedido.setPedido_Status(status);
                statusList.add(pedido);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar status dos pedidos: " + e.getMessage());
        }
        return statusList;
    }



    public static boolean alterarStatus(Pedido pedido) {
        String sql = "UPDATE tb_pedido SET Pedido_Status = ? WHERE pedido_id = ?";

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
