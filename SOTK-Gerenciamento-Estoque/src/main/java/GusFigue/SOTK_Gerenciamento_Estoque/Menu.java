package GusFigue.SOTK_Gerenciamento_Estoque;

import DAO.*;
import MODELO.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Menu {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ProdutoDAO produtoDAO = new ProdutoDAO();
    private static final PedidoDAO pedidoDAO = new PedidoDAO();


    public static void main(String[] args) throws SQLException {
        int opcao;

        do {
            System.out.println("===== MENU SOTK =====");
            System.out.println("1. Cadastrar Produto"); //DONE CHECK
            System.out.println("2. Cadastrar Sede"); // DONE CHECK
            System.out.println("3. Realizar Pedido"); // DONE CHECK
            System.out.println("4. Registrar Venda"); // DONE CHECK
            System.out.println("5. Consultar Estoque"); //DONE CHECK
            System.out.println("6. Listar Produtos"); //DONE CHECK
            System.out.println("7. Listar Sedes"); //DONE CHECK
            System.out.println("8. Listar Pedidos"); //DONE CHECK
            System.out.println("9. Consultar Status de pedido"); //DONE CHECK
            System.out.println("10. Alterar Status de pedido"); //DONE CHECK
            System.out.println("11. Abastecer Centro de Distribuição");
            System.out.println("12. Cadastrar Clientes");
            System.out.println("13. Listar Clientes");
            System.out.println("14. Cadastrar Fornecedores");
            System.out.println("15. Listar Fornecedores");
            System.out.println("16. Sair ");

            System.out.print("Escolha uma opção: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Digite um número válido.");
                scanner.next();
            }
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarProduto();
                    break;
                case 2:
                    cadastrarSede();
                    break;
                case 3:
                    cadastrarPedido();
                    break;
                case 4:
                    registrarVenda();
                    break;
                case 5:
                    consultarEstoque();
                    break;
                case 6:
                    produtoLista();
                    break;
                case 7:
                    sedeLista();
                    break;
                case 8:
                    pedidoLista();
                    break;
                case 9:
                    consultarStatus();
                    break;
                case 10:
                    alterarStatus();
                    break;
                case 11:
                    abastecerCD();
                    break;
                case 12:
                    CadastrarCliente();
                    break;
                case 13:
                    listarCliente();
                    break;
                case 14:
                    CadastrarFornecedor();
                    break;
                case 15:
                    listarFornecedor();
                    break;
                case 16:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 16);
    }


    // CASE 1
    public static void cadastrarProduto() {
        System.out.println("=== Cadastrar Produto ===");
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();

        double valor = 0.0;
        boolean valorValido = false;

        while (!valorValido) {
            System.out.print("Valor do produto: ");
            try {
                valor = Double.parseDouble(scanner.nextLine().replace(",", "."));
                valorValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número.");
            }
        }

        System.out.println("Insira a quantidade total de unidades no centro de distribuição");
        int quant_CD = scanner.nextInt();

        Produto produto = new Produto();
        produto.setProduto_Nome(nome);
        produto.setProduto_Valor(valor);
        produto.setQuant_CD(quant_CD);

        produtoDAO.cadastrar(produto);
        System.out.println("Produto cadastrado com sucesso!");
        System.out.println("-------------------------------");
    }

    // CASE 2
    public static void cadastrarSede() {
        System.out.println("=== Cadastrar Sede ===");
        System.out.println("Nome da Sede: ");
        String nome = scanner.nextLine();

        System.out.println("Lider da Sede: ");
        String lider = scanner.nextLine();

        System.out.println("Cidade da Sede: ");
        String cidade = scanner.nextLine();

        System.out.println("Rua da Sede: ");
        String rua = scanner.nextLine();

        System.out.println("Numero de residencia da Sede: ");
        int numeracao = scanner.nextInt();

        Sede sede = new Sede();
        sede.setSede_Nome(nome);
        sede.setSede_Lider(lider);
        sede.setSede_Cidade(cidade);
        sede.setSede_Rua(rua);
        sede.setSede_Numeracao(numeracao);

        SedeDAO.cadastrarSede(sede);
        System.out.println("Sede cadastrada com sucesso!");
        System.out.println("-------------------------------");
    }

    // CASE 3
    public static void cadastrarPedido() throws SQLException {
        System.out.println("=== Realizar Pedido ===");
        System.out.print("Quantidade pedida: ");
        int quantidade = scanner.nextInt();

        System.out.print("ID do produto desejado: ");
        int prod_id = scanner.nextInt();

        System.out.print("ID da Sede que o pedido será registrado: ");
        int sede_id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Cidade de entrega: ");
        String cidade = scanner.nextLine();

        System.out.print("Rua de entrega: ");
        String rua = scanner.nextLine();

        System.out.print("Numero de residência para entrega: ");
        int numeracao = scanner.nextInt();

        Pedido pedido = new Pedido();
        pedido.setPedido_Quant(quantidade);
        pedido.setProduto_Id(prod_id);
        pedido.setSede_Id(sede_id);
        pedido.setPedido_Cidade(cidade);
        pedido.setPedido_Rua(rua);
        pedido.setPedido_numeracao(numeracao);

        boolean sucesso = PedidoDAO.cadastrarPedido(pedido);
        if (sucesso) {
            System.out.println("Pedido realizado com sucesso!");
        } else {
            System.out.println("Falha ao realizar o pedido.");
        }
    }


    // CASE 4
    public static void registrarVenda() {
        VendaDAO vendaDAO = new VendaDAO();
        List<Venda.ItemVenda> itens = new ArrayList<>();

        System.out.print("ID da sede onde a venda está sendo feita: ");
        int idSede = scanner.nextInt();

        while (true) {
            System.out.print("ID do produto (ou 0 para finalizar): ");
            int idProduto = scanner.nextInt();
            if (idProduto == 0) break;

            System.out.print("Quantidade vendida: ");
            int qtd = scanner.nextInt();

            itens.add(new Venda.ItemVenda(idProduto, qtd));
        }

        if (!itens.isEmpty()) {
            vendaDAO.registrarVenda(idSede, itens);
        } else {
            System.out.println("Nenhum item foi adicionado. Venda cancelada.");
        }
    }

    // CASE 5
    public static void consultarEstoque() {
        System.out.println("=== Consultar Estoque por Sede ===");
        System.out.print("Digite o ID da sede: ");
        int sedeId = scanner.nextInt();
        scanner.nextLine();

        List<Estoque> estoqueList = EstoqueDAO.consultarEstoque(sedeId);

        if (estoqueList.isEmpty()) {
            System.out.println("Nenhum produto encontrado para esta sede.");
        } else {
            for (Estoque estoque : estoqueList) {
                System.out.println("Produto: " + estoque.getProduto_Nome());
                System.out.println("Quantidade em estoque: " + estoque.getQuantidade());
                System.out.println("-------------------------------");
            }
        }
    }

    // CASE 6
    public static void produtoLista() {
        System.out.println("=== Lista de Produtos Cadastrados ===");
        for (Produto produto : produtoDAO.listar()) {
            System.out.println("ID: " + produto.getProduto_Id());
            System.out.println("Nome: " + produto.getProduto_Nome());
            System.out.println("Valor: R$ " + produto.getProduto_Valor());
            System.out.println("Quantidade disponivel para pedido: " + produto.getQuant_CD());
            System.out.println("-------------------------------");
        }
    }

    // CASE 7
    public static void sedeLista() {
        System.out.println("=== Lista de Sedes Cadastradas ===");
        for (Sede sede : SedeDAO.listar()) {
            System.out.println("ID: " + sede.getSede_Id());
            System.out.println("Nome: " + sede.getSede_Nome());
            System.out.println("Lider: " + sede.getSede_Lider());
            System.out.println("Cidade: " + sede.getSede_Cidade());
            System.out.println("Rua: " + sede.getSede_Rua());
            System.out.println("Numero de residencia: " + sede.getSede_Numeracao());
            System.out.println("-------------------------------");
        }
    }

    // CASE 8
    public static void pedidoLista() {
        System.out.println("=== Lista de Pedidos solicitados ===");
        for (Pedido pedido : pedidoDAO.pedidoListar()) {
            System.out.println("ID do pedido: " + pedido.getPedido_Id());
            System.out.println("Data do pedido: " + pedido.getPedido_Data());
            System.out.println("Quantidade: " + pedido.getPedido_Quant());
            System.out.println("ID do produto solicitado: " + pedido.getProduto_Id());
            System.out.println("ID da sede solititante: " + pedido.getSede_Id());
            System.out.println("Cidade para entrega: " + pedido.getPedido_Cidade());
            System.out.println("Rua para entrega: " + pedido.getPedido_Rua());
            System.out.println("Numero de residencia para entrega: " + pedido.getPedido_numeracao());
            System.out.println("-------------------------------");
        }
    }

    // CASE 9
    public static void consultarStatus() {
        List<Pedido> pedidos = StatusDAO.listarStatus();

        System.out.println("=== Lista de Pedidos e seus Status ===");
        for (Pedido pedido : pedidos) {
            System.out.println("Pedido ID: " + pedido.getPedido_Id());
            System.out.println("Status: " + pedido.getPedido_Status());
            System.out.println("--------------------------");
        }
    }


    // CASE 10
    public static void alterarStatus() {
        System.out.println("Digite o ID do pedido para atualizar o status:");
        int Pedido_id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Insira o novo status do pedido:");
        String Pedido_Status = scanner.nextLine();

        Pedido pedido = new Pedido();
        pedido.setPedido_Id(Pedido_id);
        pedido.setPedido_Status(Pedido_Status);

        boolean sucesso = StatusDAO.alterarStatus(pedido);

        if (sucesso) {
            System.out.println("Status do pedido atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar o status.");
        }
    }

    // CASE 11
    public static void abastecerCD() {
        System.out.println("Informe o ID do produto que deve ser reabastecido: ");
        int Prod_Id = scanner.nextInt();

        System.out.println("Informe a quantidade para o reabastecimento: ");
        int quant_CD = scanner.nextInt();

        Produto produto = new Produto();
        produto.setQuant_CD(quant_CD);
        produto.setProduto_Id(Prod_Id);

        produtoDAO.abastecerCD(produto);
        System.out.println("Produto cadastrado com sucesso!");
        System.out.println("-------------------------------");
    }

    // CASE 12
    public static void CadastrarCliente() {
        System.out.println("=== Cadastrar Cliente ===");

        System.out.print("Nome do Cliente: ");
        String nome = scanner.nextLine();

        System.out.print("CPF do cliente: ");  //22222222238
        String cpf = scanner.nextLine();

        System.out.print("Telefone do cliente: "); //2298201418
        String tel = scanner.nextLine();

        System.out.print("Cidade do cliente: ");
        String cidade = scanner.nextLine();

        System.out.print("Rua do cliente: ");
        String rua = scanner.nextLine();

        System.out.print("Numero de residência do cliente: ");
        int numeracao = scanner.nextInt();

        Cliente cliente = new Cliente();
        cliente.setCliente_Nome(nome);
        cliente.setCliente_Cpf(cpf);
        cliente.setCliente_Tel(tel);
        cliente.setCliente_Cidade(cidade);
        cliente.setCliente_Rua(rua);
        cliente.setCliente_Numeracao(numeracao);

        boolean cadastrado = ClienteDAO.CadastrarCliente(cliente);
        if(cadastrado) {
            System.out.println("Cliente cadastrado com sucesso!");
        } else {
            System.out.println("Falha ao cadastrar cliente.");
        }
    }

    //CASE 13
    public static void listarCliente() {
        List<Cliente> clientes = ClienteDAO.listarCliente();
        System.out.println("=== Lista de Clientes ===");
        for (Cliente cliente : clientes) {
            System.out.println("ID: " + cliente.getCliente_Id());
            System.out.println("Nome: " + cliente.getCliente_Nome());
            System.out.println("CPF: " + cliente.getCliente_Cpf());
            System.out.println("Telefone: " + cliente.getCliente_Tel());
            System.out.println("Cidade: " + cliente.getCliente_Cidade());
            System.out.println("Rua: " + cliente.getCliente_Rua());
            System.out.println("Numeração: " + cliente.getCliente_Numeracao());

            System.out.println("--------------------------");
        }
    }

    //CASE 14
    public static void CadastrarFornecedor() {
        System.out.println("=== Cadastrar Fornecedor ===");
        System.out.print("Nome do Fornecedor: ");
        String nome = scanner.nextLine();

        System.out.print("CNPJ do Fornecedor: "); // Exemplo: 111111111134
        String cnpj = scanner.nextLine();

        System.out.print("Catálogo do Fornecedor: ");
        String catalogo = scanner.nextLine();

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setFornecedor_Nome(nome);
        fornecedor.setFornecedor_Cnpj(cnpj);
        fornecedor.setFornecedor_Catalogo(catalogo);

        boolean cadastrado = FornecedorDAO.CadastrarFornecedor(fornecedor);
        if (cadastrado) {
            System.out.println("Fornecedor cadastrado com sucesso!");
        } else {
            System.out.println("Falha ao cadastrar fornecedor.");
        }
    }


    //CASE 15
    public static void listarFornecedor() {
        System.out.println("=== Lista de Fornecedores ===");
        List<Fornecedor> fornecedores = FornecedorDAO.listarFornecedor();

        for (Fornecedor fornecedor : fornecedores) {
            System.out.println("ID: " + (fornecedor.getFornecedor_Id()));
            System.out.println("Nome: " + (fornecedor.getFornecedor_Nome()));
            System.out.println("CNPJ: " + (fornecedor.getFornecedor_Cnpj()));
            System.out.println("Catalogo: " + (fornecedor.getFornecedor_Catalogo()));

            System.out.println("--------------------------");
        }
    }
}


















