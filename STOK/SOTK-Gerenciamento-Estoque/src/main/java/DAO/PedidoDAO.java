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

    public static void pedir(Pedido pedido) {
        String sql = "Insert into tb_pedido (Pedido_quant, Pedido_numero, Pedido_Rua, Pedido_Cidade, Prod_id, Sede_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoMySQL.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getPedido_Quant());
            stmt.setInt(2, pedido.getPedido_Numero());
            stmt.setString(3, pedido.getPedido_Rua());
            stmt.setString(4, pedido.getPedido_Cidade());
            stmt.setInt(5, pedido.getProduto_Id());
            stmt.setInt(6, pedido.getSede_Id());

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar produto no banco de dados.");
            e.printStackTrace();
        }


    }
}
