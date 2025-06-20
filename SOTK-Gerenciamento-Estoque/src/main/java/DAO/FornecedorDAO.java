package DAO;

import MODELO.Cliente;
import MODELO.Fornecedor;
import Util.ConexaoMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDAO {

    public static boolean CadastrarFornecedor (Fornecedor fornecedor){
        String sql =  "INSERT INTO tb_fornecedor (fornecedor_nome, fornecedor_cnpj, fornecedor_produtos)"
                + "VALUES (?, ?, ?)";

        try (Connection conn = ConexaoMySQL.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fornecedor.getFornecedor_Nome());
            stmt.setString(2, fornecedor.getFornecedor_Cnpj());
            stmt.setString(3, fornecedor.getFornecedor_Catalogo());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar produto no banco de dados.");
            e.printStackTrace();
            return false;
        }


    }

    public static List<Fornecedor> listarFornecedor() {
        List<Fornecedor> fornecedores = new ArrayList<>();
        String sql = "Select * from tb_fornecedor";

        try (Connection conn = ConexaoMySQL.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setFornecedor_Id(rs.getInt("fornecedor_id"));
                fornecedor.setFornecedor_Nome(rs.getString("fornecedor_nome"));
                fornecedor.setFornecedor_Cnpj(rs.getString("fornecedor_cnpj"));
                fornecedor.setFornecedor_Catalogo(rs.getString("fornecedor_produtos"));

                fornecedores.add(fornecedor);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes no banco de dados.");
            e.printStackTrace();
        }

        return fornecedores;
    }
}
