package DAO;

import MODELO.Produto;
import MODELO.Sede;
import Util.ConexaoMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SedeDAO {

    public static void cadastrarSede(Sede sede) {
    String sql = "INSERT INTO tb_sede (Sede_name, Sede_lider, Sede_Cidade, Sede_Rua, Sede_numero) " +
            "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoMySQL.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, sede.getSede_Nome());
            stmt.setString(2, sede.getSede_Lider());
            stmt.setString(3, sede.getSede_Cidade());
            stmt.setString(4, sede.getSede_Rua());
            stmt.setInt(5, sede.getSede_Numeracao());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idGerado = generatedKeys.getInt(1);
                    sede.setSede_Id(idGerado); // Atualiza o objeto sede com o ID gerado

                } else {
                    throw new SQLException("Falha ao obter o ID gerado.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar produto no banco de dados.");
            e.printStackTrace();
        }
    }


    public static List<Sede> listar() {
        List<Sede> sedes = new ArrayList<>();
        String sql = "SELECT * FROM tb_sede";

        try (Connection conn = ConexaoMySQL.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Sede sede = new Sede();
                sede.setSede_Id(rs.getInt("Sede_id"));
                sede.setSede_Nome(rs.getString("Sede_name"));
                sede.setSede_Lider(rs.getString("Sede_lider"));
                sede.setSede_Cidade(rs.getString("Sede_Cidade"));
                sede.setSede_Rua(rs.getString("Sede_Rua"));
                sede.setSede_Numeracao(rs.getInt("Sede_numero"));


                sedes.add(sede);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar sedes no banco de dados.");
            e.printStackTrace();
        }

        return sedes;
    }

}

