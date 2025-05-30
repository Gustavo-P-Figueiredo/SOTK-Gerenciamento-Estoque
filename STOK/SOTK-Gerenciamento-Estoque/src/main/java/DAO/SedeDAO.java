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

public class SedeDAO {
    public static void cadastrar(Sede sede) {
        String sql = "INSERT INTO tb_sede (Sede_name, Sede_lider, Sede_endereço) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoMySQL.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sede.getSede_Nome());
            stmt.setString(2, sede.getSede_Lider());
            stmt.setString(3, sede.getSede_Endereço());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar a sede no banco de dados.");
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
                sede.setSede_Endereço(rs.getString("Sede_endereço"));


                sedes.add(sede);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos no banco de dados.");
            e.printStackTrace();
        }

        return sedes;
    }

}

