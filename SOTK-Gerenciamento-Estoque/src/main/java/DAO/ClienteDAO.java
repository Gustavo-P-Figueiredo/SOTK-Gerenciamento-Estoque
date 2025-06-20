package DAO;

import MODELO.Cliente;
import Util.ConexaoMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public static boolean CadastrarCliente(Cliente cliente) {
        String sql = "INSERT INTO tb_cliente (cliente_nome, cliente_CPF, cliete_tel, cliente_cidade, cliente_rua, cliente_numero) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoMySQL.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getCliente_Nome());
            stmt.setString(2, cliente.getCliente_Cpf());
            stmt.setString(3, cliente.getCliente_Tel());
            stmt.setString(4, cliente.getCliente_Cidade());
            stmt.setString(5, cliente.getCliente_Rua());
            stmt.setInt(6, cliente.getCliente_Numeracao());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar cliente no banco de dados.");
            e.printStackTrace();
            return false;
        }
    }


    public static List<Cliente> listarCliente() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "Select * from tb_cliente";

        try (Connection conn = ConexaoMySQL.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
               Cliente cliente = new Cliente();
                cliente.setCliente_Id(rs.getInt("cliente_id"));
                cliente.setCliente_Nome(rs.getString("cliente_nome"));
                cliente.setCliente_Cpf(rs.getString("cliente_CPF"));
                cliente.setCliente_Tel(rs.getString("cliete_tel"));
                cliente.setCliente_Cidade(rs.getString("cliente_cidade"));
                cliente.setCliente_Rua(rs.getString("cliente_rua"));
                cliente.setCliente_Numeracao(rs.getInt("cliente_numero"));

                clientes.add(cliente);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes no banco de dados.");
            e.printStackTrace();
        }

        return clientes;

    }





}
