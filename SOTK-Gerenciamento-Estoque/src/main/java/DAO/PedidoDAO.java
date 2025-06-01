package DAO;

import MODELO.Pedido;
import MODELO.Produto;
import MODELO.Sede;
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

    public static void realizarPedido(Pedido pedido) throws SQLException {
        if (!sedeExiste(pedido.getSede_Id())) {
            throw new SQLException("Sede com ID " + pedido.getSede_Id() + " não existe");
        }

        String sql = "INSERT INTO tb_pedido (pedido_quant, Prod_id, Sede_id, Pedido_Cidade, Pedido_Rua, Pedido_numero) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoMySQL.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getPedido_Quant());
            stmt.setInt(2, pedido.getProduto_Id());
            stmt.setInt(3, pedido.getSede_Id());
            stmt.setString(4, pedido.getPedido_Cidade());
            stmt.setString(5, pedido.getPedido_Rua());
            stmt.setInt(6, pedido.getPedido_numeracao());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar produto no banco de dados.");
            e.printStackTrace();
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

