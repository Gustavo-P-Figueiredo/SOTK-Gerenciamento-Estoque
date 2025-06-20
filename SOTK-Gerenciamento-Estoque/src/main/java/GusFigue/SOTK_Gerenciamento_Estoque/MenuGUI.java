
package GusFigue.SOTK_Gerenciamento_Estoque;

import DAO.*;
import MODELO.*;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static GusFigue.SOTK_Gerenciamento_Estoque.Menu.*;

public class MenuGUI {

    private static final ProdutoDAO produtoDAO = new ProdutoDAO();
    private static final PedidoDAO pedidoDAO = new PedidoDAO();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuGUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Menu SOTK - Gerenciamento de Estoque");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(135, 206, 250));
        frame.setLayout(new GridLayout(8, 2, 17, 17));

        // Criando botões para cada opção do menu
        String[] options = {
                "1. Cadastrar Produto",
                "2. Cadastrar Sede",
                "3. Realizar Pedido",
                "4. Registrar Venda",
                "5. Consultar Estoque",
                "6. Listar Produtos",
                "7. Listar Sedes",
                "8. Listar Pedidos",
                "9. Consultar Status de Pedido",
                "10. Alterar Status de Pedido",
                "11. Abastecer CD",
                "12. Cadastrar Cliente",
                "13. Listar Clientes",
                "14. Cadastrar Fornecedor",
                "15. Listar Fornecedores",
                "16. Sair "
        };

        for (String option : options) {
            JButton button = new JButton(option);
            button.addActionListener(e -> handleOption(option));
            frame.add(button);
        }

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static void handleOption(String option) {
        try {
            switch (option) {
                case "1. Cadastrar Produto":
                    cadastrarProduto();
                    break;
                case "2. Cadastrar Sede":
                    cadastrarSede();
                    break;
                case "3. Realizar Pedido":
                    cadastrarPedido();
                    break;
                case "4. Registrar Venda":
                    registrarVenda();
                    break;
                case "5. Consultar Estoque":
                    consultarEstoque();
                    break;
                case "6. Listar Produtos":
                    listarProdutos();
                    break;
                case "7. Listar Sedes":
                    listarSedes();
                    break;
                case "8. Listar Pedidos":
                    listarPedidos();
                    break;
                case "9. Consultar Status de Pedido":
                    consultarStatus();
                    break;
                case "10. Alterar Status de Pedido":
                    alterarStatus();
                    break;
                case "11. Abastecer CD":
                    abastecerCD();
                    break;
                case "12. Cadastrar Cliente":
                    CadastrarCliente();
                    break;
                case "13. Listar Clientes":
                    listarCliente();
                    break;
                case "14. Cadastrar Fornecedor":
                    CadastrarFornecedor();
                    break;
                case "15. Listar Fornecedores":
                    listarFornecedor();
                    break;
                case "16. Sair":
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    // Exemplo de um dos métodos GUI (ex: cadastrarProduto)
    private static void cadastrarProduto() {
        String nome = JOptionPane.showInputDialog("Nome do Produto:");
        if (nome == null) return;

        String valorStr = JOptionPane.showInputDialog("Valor do Produto:");
        if (valorStr == null) return;

        String quantStr = JOptionPane.showInputDialog("Quantidade no CD:");
        if (quantStr == null) return;

        try {
            double valor = Double.parseDouble(valorStr.replace(",", "."));
            int quant = Integer.parseInt(quantStr);

            Produto produto = new Produto();
            produto.setProduto_Nome(nome);
            produto.setProduto_Valor(valor);
            produto.setQuant_CD(quant);

            produtoDAO.cadastrar(produto);
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Dados inválidos. Tente novamente.");
        }
    }

    private static void cadastrarSede() {
        try {
            String nome = JOptionPane.showInputDialog("Nome da Sede:");
            String lider = JOptionPane.showInputDialog("Líder da Sede:");
            String cidade = JOptionPane.showInputDialog("Cidade:");
            String rua = JOptionPane.showInputDialog("Rua:");
            String numStr = JOptionPane.showInputDialog("Número da residência:");

            if (nome == null || lider == null || cidade == null || rua == null || numStr == null) return;

            int numeracao = Integer.parseInt(numStr);

            Sede sede = new Sede();
            sede.setSede_Nome(nome);
            sede.setSede_Lider(lider);
            sede.setSede_Cidade(cidade);
            sede.setSede_Rua(rua);
            sede.setSede_Numeracao(numeracao);

            SedeDAO.cadastrarSede(sede);
            JOptionPane.showMessageDialog(null, "Sede cadastrada com sucesso!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Número inválido.");
        }
    }

    private static void cadastrarPedido() throws SQLException {
        try {
            int quantidade = Integer.parseInt(JOptionPane.showInputDialog("Quantidade:"));
            int prod_id = Integer.parseInt(JOptionPane.showInputDialog("ID do Produto:"));
            int sede_id = Integer.parseInt(JOptionPane.showInputDialog("ID da Sede:"));
            String cidade = JOptionPane.showInputDialog("Cidade de Entrega:");
            String rua = JOptionPane.showInputDialog("Rua de Entrega:");
            int numero = Integer.parseInt(JOptionPane.showInputDialog("Número da residência:"));

            Pedido pedido = new Pedido();
            pedido.setPedido_Quant(quantidade);
            pedido.setProduto_Id(prod_id);
            pedido.setSede_Id(sede_id);
            pedido.setPedido_Cidade(cidade);
            pedido.setPedido_Rua(rua);
            pedido.setPedido_numeracao(numero);

            boolean sucesso = PedidoDAO.cadastrarPedido(pedido);

            if (sucesso) {
                JOptionPane.showMessageDialog(null, "Pedido cadastrado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao cadastrar pedido.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Dados inválidos.");
        }
    }

    private static void registrarVenda() {
        try {
            int idSede = Integer.parseInt(JOptionPane.showInputDialog("ID da Sede:"));
            List<Venda.ItemVenda> itens = new ArrayList<>();

            while (true) {
                String idProdutoStr = JOptionPane.showInputDialog("ID do Produto (ou 0 para finalizar):");
                if (idProdutoStr == null) break;

                int idProduto = Integer.parseInt(idProdutoStr);
                if (idProduto == 0) break;

                int qtd = Integer.parseInt(JOptionPane.showInputDialog("Quantidade:"));
                itens.add(new Venda.ItemVenda(idProduto, qtd));
            }

            if (!itens.isEmpty()) {
                new VendaDAO().registrarVenda(idSede, itens);
                JOptionPane.showMessageDialog(null, "Venda registrada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum item adicionado.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Dados inválidos.");
        }
    }

    private static void consultarEstoque() {
        try {
            int sedeId = Integer.parseInt(JOptionPane.showInputDialog("ID da Sede:"));
            List<Estoque> estoqueList = EstoqueDAO.consultarEstoque(sedeId);

            StringBuilder sb = new StringBuilder("Estoque da Sede " + sedeId + ":\n");

            for (Estoque estoque : estoqueList) {
                sb.append("Produto: ").append(estoque.getProduto_Nome())
                        .append(" | Quantidade: ").append(estoque.getQuantidade()).append("\n");
            }

            JOptionPane.showMessageDialog(null, sb.length() > 0 ? sb.toString() : "Nenhum produto encontrado.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido.");
        }
    }

    private static void listarProdutos() {
        StringBuilder sb = new StringBuilder("Produtos Cadastrados:\n");
        for (Produto produto : produtoDAO.listar()) {
            sb.append("ID: ").append(produto.getProduto_Id())
                    .append(", Nome: ").append(produto.getProduto_Nome())
                    .append(", Valor: R$").append(produto.getProduto_Valor())
                    .append(", Quantidade no CD: ").append(produto.getQuant_CD()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void listarSedes() {
        StringBuilder sb = new StringBuilder("Sedes Cadastradas:\n");
        for (Sede sede : SedeDAO.listar()) {
            sb.append("ID: ").append(sede.getSede_Id())
                    .append(", Nome: ").append(sede.getSede_Nome())
                    .append(", Cidade: ").append(sede.getSede_Cidade()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void listarPedidos() {
        StringBuilder sb = new StringBuilder("Pedidos:\n");
        for (Pedido pedido : pedidoDAO.pedidoListar()) {
            sb.append("ID: ").append(pedido.getPedido_Id())
                    .append(", Produto: ").append(pedido.getProduto_Id())
                    .append(", Quantidade: ").append(pedido.getPedido_Quant())
                    .append(", Status: ").append(pedido.getPedido_Status()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void consultarStatus() {
        StringBuilder sb = new StringBuilder("Status dos Pedidos:\n");
        for (Pedido pedido : StatusDAO.listarStatus()) {
            sb.append("Pedido ID: ").append(pedido.getPedido_Id())
                    .append(", Status: ").append(pedido.getPedido_Status()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void alterarStatus() {
        try {
            int pedidoId = Integer.parseInt(JOptionPane.showInputDialog("ID do Pedido:"));
            String novoStatus = JOptionPane.showInputDialog("Novo Status:");

            Pedido pedido = new Pedido();
            pedido.setPedido_Id(pedidoId);
            pedido.setPedido_Status(novoStatus);

            boolean sucesso = StatusDAO.alterarStatus(pedido);

            if (sucesso) {
                JOptionPane.showMessageDialog(null, "Status atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao atualizar status.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido.");
        }
    }

    private static void abastecerCD() {
        try {
            int prodId = Integer.parseInt(JOptionPane.showInputDialog("ID do Produto:"));
            int quantidade = Integer.parseInt(JOptionPane.showInputDialog("Quantidade a Abastecer:"));

            Produto produto = new Produto();
            produto.setProduto_Id(prodId);
            produto.setQuant_CD(quantidade);

            produtoDAO.abastecerCD(produto);
            JOptionPane.showMessageDialog(null, "Produto abastecido com sucesso!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Dados inválidos.");
        }
    }

    private static void CadastrarCliente() {
        String nome = JOptionPane.showInputDialog("Nome do Cliente:");
        String cpf = JOptionPane.showInputDialog("CPF do Cliente:");
        String telefone = JOptionPane.showInputDialog("Telefone do Cliente:");
        String cidade = JOptionPane.showInputDialog("Cidade:");
        String rua = JOptionPane.showInputDialog("Rua:");
        String numStr = JOptionPane.showInputDialog("Número da residência:");

        if (nome == null || cpf == null || telefone == null || cidade == null || rua == null || numStr == null) {
            JOptionPane.showMessageDialog(null, "Cadastro cancelado.");
            return;
        }

        try {
            int numero = Integer.parseInt(numStr);

            Cliente cliente = new Cliente();
            cliente.setCliente_Nome(nome);
            cliente.setCliente_Cpf(cpf);
            cliente.setCliente_Tel(telefone);
            cliente.setCliente_Cidade(cidade);
            cliente.setCliente_Rua(rua);
            cliente.setCliente_Numeracao(numero);

            boolean sucesso = ClienteDAO.CadastrarCliente(cliente);

            if (sucesso) {
                JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar cliente.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Número inválido.");
        }
    }

    private static void listarCliente() {
        List<Cliente> clientes = ClienteDAO.listarCliente();

        StringBuilder sb = new StringBuilder("Clientes Cadastrados:\n");
        for (Cliente cliente : clientes) {
            sb.append("ID: ").append(cliente.getCliente_Id())
                    .append(", Nome: ").append(cliente.getCliente_Nome())
                    .append(", CPF: ").append(cliente.getCliente_Cpf())
                    .append(", Telefone: ").append(cliente.getCliente_Tel())
                    .append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.length() > 0 ? sb.toString() : "Nenhum cliente encontrado.");
    }

    private static void CadastrarFornecedor() {
        String nome = JOptionPane.showInputDialog("Nome do Fornecedor:");
        String cnpj = JOptionPane.showInputDialog("CNPJ do Fornecedor:");
        String catalogo = JOptionPane.showInputDialog("Catalogo do Fornecedor:");

        if (nome == null || cnpj == null || catalogo == null) {
            JOptionPane.showMessageDialog(null, "Cadastro cancelado.");
            return;
        }

        try {
            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setFornecedor_Nome(nome);
            fornecedor.setFornecedor_Cnpj(cnpj);
            fornecedor.setFornecedor_Catalogo(catalogo);

            boolean sucesso = FornecedorDAO.CadastrarFornecedor(fornecedor);

            if (sucesso) {
                JOptionPane.showMessageDialog(null, "Fornecedor cadastrado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao cadastrar fornecedor.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Número inválido.");
        }
    }


    private static void listarFornecedor() {
        List<Fornecedor> fornecedores = FornecedorDAO.listarFornecedor();

        StringBuilder sb = new StringBuilder("Fornecedores Cadastrados:\n");
        for (Fornecedor fornecedor : fornecedores) {
            sb.append("ID: ").append(fornecedor.getFornecedor_Id())
                    .append(", Nome: ").append(fornecedor.getFornecedor_Nome())
                    .append(", CNPJ: ").append(fornecedor.getFornecedor_Cnpj())
                    .append(", Catalogo: ").append(fornecedor.getFornecedor_Catalogo())
                    .append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.length() > 0 ? sb.toString() : "Nenhum fornecedor encontrado.");
    }


}
