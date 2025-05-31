package DAO;

import MODELO.Produto;
import Util.ConexaoMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void cadastrar(Produto produto) {
        String sql = "INSERT INTO tb_produto (Prod_nome, Prod_valor) VALUES (?, ?)";

        try (Connection conn = ConexaoMySQL.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getProduto_Nome());
            stmt.setDouble(2, produto.getProduto_Valor());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar produto no banco de dados.");
            e.printStackTrace();
        }
    }

    public List<Produto> listar() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM tb_produto";

        try (Connection conn = ConexaoMySQL.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setProduto_Id(rs.getInt("Prod_id"));
                produto.setProduto_Nome(rs.getString("Prod_nome"));
                produto.setProduto_Valor(rs.getDouble("Prod_valor"));

                produtos.add(produto);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos no banco de dados.");
            e.printStackTrace();
        }

        return produtos;
    }




}
