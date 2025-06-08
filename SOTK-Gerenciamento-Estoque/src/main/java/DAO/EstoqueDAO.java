package DAO;

import MODELO.Estoque;
import Util.ConexaoMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstoqueDAO {

    public static List<Estoque> consultarEstoque(int sedeId) {
        List<Estoque> lista = new ArrayList<>();

        String sql = """
                SELECT p.Prod_nome, SUM(pe.pedido_quant) AS quantidade
                FROM tb_pedido pe
                JOIN tb_produto p ON pe.prod_id = p.Prod_id
                WHERE pe.sede_id = ?
                GROUP BY p.Prod_nome;
                """;

        try (Connection conn = ConexaoMySQL.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sedeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Estoque estoque = new Estoque();
                estoque.setProduto_Nome(rs.getString("Prod_nome"));
                estoque.setQuantidade(rs.getInt("quantidade"));
                lista.add(estoque);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


}
