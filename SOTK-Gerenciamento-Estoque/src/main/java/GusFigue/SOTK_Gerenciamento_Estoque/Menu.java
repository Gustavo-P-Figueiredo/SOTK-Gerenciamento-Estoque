package GusFigue.SOTK_Gerenciamento_Estoque;

import DAO.EstoqueDAO;
import DAO.PedidoDAO;
import DAO.ProdutoDAO;
import DAO.SedeDAO;
import MODELO.Estoque;
import MODELO.Pedido;
import MODELO.Produto;
import MODELO.Sede;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ProdutoDAO produtoDAO = new ProdutoDAO();
    private static final PedidoDAO pedidoDAO = new PedidoDAO();
    private static Estoque estoque;


    public static void main(String[] args) throws SQLException {
        int opcao;

        do {
            System.out.println("===== MENU WMS =====");
            System.out.println("1. Cadastrar Produto"); //DONE CHECK
            System.out.println("2. Cadastrar Sede"); // DONE CHECK
            System.out.println("3. Realizar Pedido"); // DONE CHECK
            System.out.println("4. Consultar Estoque"); //DONE CHECK
            System.out.println("5. Listar Produtos"); //DONE CHECK
            System.out.println("6. Listar Sedes"); //DONE CHECK
            System.out.println("7. Listar Pedidos"); //DONE CHECK
            System.out.println("8. Sair"); //DONE
            System.out.print("Escolha uma opção: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Digite um número válido.");
                scanner.next(); // limpar entrada inválida
            }
            opcao = scanner.nextInt();
            scanner.nextLine(); // consumir a quebra de linha

            switch (opcao) {
                case 1:
                    cadastrarProduto();
                    break;
                case 2:
                    cadastrarSede();
                    break;
                case 3:
                    realizarPedido();
                    break;
                case 4:
                    consultarEstoque();
                    break;
                case 5:
                    produtoLista();
                    break;
                case 6:
                    sedeLista();
                    break;
                case 7:
                    pedidoLista();
                    break;
                case 8:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 8);
    }


    //CASE 1
    public static void cadastrarProduto() {
        System.out.println("=== Cadastro de Produto ===");
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

        Produto produto = new Produto();
        produto.setProduto_Nome(nome);
        produto.setProduto_Valor(valor);

        produtoDAO.cadastrar(produto);
        System.out.println("Produto cadastrado com sucesso!");
        System.out.println("-------------------------------");

    }

    //CASE 5
    private static void produtoLista() {
        System.out.println("=== Lista de Produtos Cadastrados ===");
        for (Produto produto : produtoDAO.listar()) {
            System.out.println("ID: " + produto.getProduto_Id());
            System.out.println("Nome: " + produto.getProduto_Nome());
            System.out.println("Valor: R$ " + produto.getProduto_Valor());
            System.out.println("-------------------------------");
        }
    }

    //CASE 2
    public static void cadastrarSede() {
        System.out.println("=== Cadastrar Sede ===");
        System.out.println("Nome da Sede: ");
        String nome = scanner.nextLine();

        System.out.println("=== Cadastrar Sede ===");
        System.out.println("Lider da Sede: ");
        String lider = scanner.nextLine();

        System.out.println("=== Cadastrar Sede ===");
        System.out.println("Cidade da Sede: ");
        String cidade = scanner.nextLine();

        System.out.println("=== Cadastrar Sede ===");
        System.out.println("Rua da Sede: ");
        String rua = scanner.nextLine();

        System.out.println("=== Cadastrar Sede ===");
        System.out.println("Numeração da Sede: ");
        int numeracao = scanner.nextInt();

        Sede sede = new Sede();
        sede.setSede_Nome(nome);
        sede.setSede_Lider(lider);
        sede.setSede_Cidade(cidade);
        sede.setSede_Rua(rua);
        sede.setSede_Numeracao(numeracao);

        SedeDAO.cadastrarSede(sede);
        System.out.println("Produto cadastrado com sucesso!");
        System.out.println("-------------------------------");

    }

    //CASE 6
    public static void sedeLista() {
        System.out.println("=== Lista de Sedes Cadastradas ===");
        for (Sede sede : SedeDAO.listar()) {
            System.out.println("ID: " + sede.getSede_Id());
            System.out.println("Nome: " + sede.getSede_Nome());
            System.out.println("Lider: " + sede.getSede_Lider());
            System.out.println("Cidade: " + sede.getSede_Cidade());
            System.out.println("Rua: " + sede.getSede_Rua());
            System.out.println("Numeração: " + sede.getSede_Numeracao());
            System.out.println("-------------------------------");

        }
    }

    //CASE 3
    public static void realizarPedido() throws SQLException {
        System.out.println("=== Realizar Pedido ===");
        System.out.println("Quantidade pedida: ");
        int quantidade = scanner.nextInt();

        System.out.println("=== Cadastrar Sede ===");
        System.out.println("ID do produto desejado: ");
        int prod_id = scanner.nextInt();

        System.out.println("=== Realizar Pedido ===");
        System.out.println("ID da Sede que o pedido será registrado: ");
        int sede_id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("=== Realizar Pedido ===");
        System.out.println("Cidade de entrega: ");
        String cidade = scanner.nextLine();

        System.out.println("=== Realizar Pedido ===");
        System.out.println("Rua de entrega: ");
        String rua = scanner.nextLine();

        System.out.println("=== Realizar Pedido ===");
        System.out.println("Numeração de casa para entrega : ");
        int numeracao = scanner.nextInt();

        Pedido pedido = new Pedido();
        pedido.setPedido_Quant(quantidade);
        pedido.setProduto_Id(prod_id);
        pedido.setSede_Id(sede_id);
        pedido.setPedido_Cidade(cidade);
        pedido.setPedido_Rua(rua);
        pedido.setPedido_numeracao(numeracao);


        PedidoDAO.realizarPedido(pedido);
        System.out.println("Pedido realizado com sucesso!");
        System.out.println("-------------------------------");
    }

    //CASE 7
    private static void pedidoLista() {
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


    //CASE 4
    private static void consultarEstoque() {
        System.out.println("=== Consultar Estoque por Sede ===");
        System.out.print("Digite o ID da sede: ");
        int sedeId = scanner.nextInt();
        scanner.nextLine(); // Limpar quebra de linha

        List<Estoque> estoqueList = EstoqueDAO.consultarEstoquePorSede(sedeId);

        if (estoqueList.isEmpty()) {
            System.out.println("Nenhum produto encontrado para esta sede.");
        } else {
            for (Estoque estoque : estoqueList) {
                System.out.println("Produto: " + estoque.getProduto_Nome());
                System.out.println("Quantidade em estoque: " + Estoque.getQuantidade());
                System.out.println("-------------------------------");
            }
        }
    }
}











