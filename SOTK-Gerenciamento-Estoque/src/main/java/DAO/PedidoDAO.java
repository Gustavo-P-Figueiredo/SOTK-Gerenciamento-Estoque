package DAO;

import MODELO.Estoque;
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

    public static void realizarPedido(Pedido pedido) {
        // 1) SQL para inserir o pedido em tb_pedido
        String insertPedidoSQL = """
        INSERT INTO tb_pedido
          (pedido_quant, sede_id, prod_id, Pedido_Cidade, Pedido_Rua, Pedido_numero)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

        // 2) SQL para checar se já existe linha em tb_estoque para (sede_id, prod_id)
        String checkEstoqueSQL = """
        SELECT quantidade
        FROM tb_estoque
        WHERE Sede_id = ? AND Prod_id = ?
        """;

        // 3) SQL para atualizar a quantidade
        String updateEstoqueSQL = """
        UPDATE tb_estoque
        SET quantidade = quantidade + ?
        WHERE Sede_id = ? AND Prod_id = ?
        """;

        // 4) SQL para inserir nova linha no estoque (caso não exista)
        String insertEstoqueSQL = """
        INSERT INTO tb_estoque (Sede_id, Prod_id, quantidade)
        VALUES (?, ?, ?)
        """;

        try (Connection conn = ConexaoMySQL.getConexaoMySQL()) {
            conn.setAutoCommit(false);

            // —— 1. Inserir o pedido em tb_pedido ——
            try (PreparedStatement stmtPedido = conn.prepareStatement(insertPedidoSQL)) {
                // Para depurar, mostre exatamente o que está chegando no objeto "pedido":
                System.out.printf("[DEBUG] Antes de INSERT tb_pedido → quant=%d, sede=%d, prod=%d, cidade=%s, rua=%s, numero=%d%n",
                        pedido.getPedido_Quant(),
                        pedido.getSede_Id(),
                        pedido.getProduto_Id(),
                        pedido.getPedido_Cidade(),
                        pedido.getPedido_Rua(),
                        pedido.getPedido_numeracao()
                );

                stmtPedido.setInt(1, pedido.getPedido_Quant());   // quantidade do pedido
                stmtPedido.setInt(2, pedido.getSede_Id());       // ID da sede
                stmtPedido.setInt(3, pedido.getProduto_Id());    // ID do produto
                stmtPedido.setString(4, pedido.getPedido_Cidade());  // cidade de entrega
                stmtPedido.setString(5, pedido.getPedido_Rua());     // rua de entrega
                stmtPedido.setInt(6, pedido.getPedido_numeracao());  // número da residência
                int linhasPedido = stmtPedido.executeUpdate();
                System.out.println("[LOG] Pedido inserido em tb_pedido, linhas afetadas: " + linhasPedido);
            }

            // —— 2. Verificar se existe estoque para (sede_id, prod_id) ——
            boolean estoqueExiste = false;
            try (PreparedStatement stmtCheck = conn.prepareStatement(checkEstoqueSQL)) {
                stmtCheck.setInt(1, pedido.getSede_Id());
                stmtCheck.setInt(2, pedido.getProduto_Id());
                try (ResultSet rs = stmtCheck.executeQuery()) {
                    if (rs.next()) {
                        estoqueExiste = true;
                        int qtdAtual = rs.getInt("quantidade");
                        System.out.println("[LOG] Encontrou estoque existente → quantidade atual = " + qtdAtual);
                    } else {
                        System.out.println("[LOG] Não encontrou nenhuma linha em tb_estoque para (sede_id="
                                + pedido.getSede_Id() + ", prod_id=" + pedido.getProduto_Id() + ")");
                    }
                }
            }

            // —— 3. Se já existe → UPDATE, senão → INSERT ——
            if (estoqueExiste) {
                try (PreparedStatement stmtUpdate = conn.prepareStatement(updateEstoqueSQL)) {
                    stmtUpdate.setInt(1, pedido.getPedido_Quant());   // + pedido_quant
                    stmtUpdate.setInt(2, pedido.getSede_Id());       // WHERE Sede_id = ?
                    stmtUpdate.setInt(3, pedido.getProduto_Id());    //   AND Prod_id = ?
                    int linhasUpd = stmtUpdate.executeUpdate();
                    System.out.println("[LOG] Executou UPDATE em tb_estoque, linhas afetadas = " + linhasUpd);
                }
            } else {
                try (PreparedStatement stmtInsert = conn.prepareStatement(insertEstoqueSQL)) {
                    stmtInsert.setInt(1, pedido.getSede_Id());       // Sede_id
                    stmtInsert.setInt(2, pedido.getProduto_Id());    // Prod_id
                    stmtInsert.setInt(3, pedido.getPedido_Quant());  // quantidade inicial
                    int linhasIns = stmtInsert.executeUpdate();
                    System.out.println("[LOG] Executou INSERT em tb_estoque, linhas criadas = " + linhasIns);
                }
            }

            conn.commit();
            System.out.println("[LOG] Transação commitada com sucesso.");

        } catch (SQLException e) {
            e.printStackTrace();
            // Se der erro, tente fazer rollback (lembrando de usar a mesma Connection)
            System.out.println("[ERRO] Falha no realizarPedido → rolando o rollback...");
            try {
                Connection conParaRollback = ConexaoMySQL.getConexaoMySQL();
                if (!conParaRollback.getAutoCommit()) {
                    conParaRollback.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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

