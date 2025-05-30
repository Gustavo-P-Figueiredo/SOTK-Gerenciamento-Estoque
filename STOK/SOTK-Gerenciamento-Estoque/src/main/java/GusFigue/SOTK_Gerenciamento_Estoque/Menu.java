package GusFigue.SOTK_Gerenciamento_Estoque;

import DAO.PedidoDAO;
import DAO.ProdutoDAO;
import DAO.SedeDAO;
import MODELO.Pedido;
import MODELO.Produto;
import MODELO.Sede;

import java.util.Scanner;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ProdutoDAO produtoDAO = new ProdutoDAO();

    public static void main(String[] args) {
        int opcao;

        do {
            System.out.println("===== MENU WMS =====");
            System.out.println("1. Cadastrar Produto"); //DONE
            System.out.println("2. Cadastrar Sede"); // DONE
            System.out.println("3. Realizar Pedido"); // DOTO
            System.out.println("4. Consultar Estoque"); //DOTO
            System.out.println("5. Listar Produtos"); //DONE
            System.out.println("6. Listar Sedes"); //DONE
            System.out.println("7. Listar Pedidos"); //DOTO
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
                    //cadastrarProduto();
                    break;
                case 5:
                    produtoLista();
                    break;
                case 6:
                    sedeLista();
                break;
                case 7:
                    //cadastrarProduto();
                    break;
                case 8:
                    //cadastrarProduto();
                    break;
                case 9:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 9);
    }



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
    }


    public static void cadastrarSede() {
        System.out.println("=== Cadastro de Sede ===");
        System.out.print("Nome da sede: ");
        String nome = scanner.nextLine();

        System.out.println("=== Cadastro de Sede ===");
        System.out.print("Nome do lider: ");
        String lider = scanner.nextLine();

        System.out.println("=== Cadastro de Sede ===");
        System.out.print("endereço da sede: ");
        String endereco = scanner.nextLine();

        Sede sede = new Sede();
        sede.setSede_Nome(nome);
        sede.setSede_Lider(lider);
        sede.setSede_Endereço(endereco);


        SedeDAO.cadastrar(sede);
        System.out.println("Sede cadastrado com sucesso!");
    }


    private static void produtoLista() {
        System.out.println("=== Lista de Produtos Cadastrados ===");
        for (Produto produto : produtoDAO.listar()) {
            System.out.println("ID: " + produto.getProduto_Id());
            System.out.println("Nome: " + produto.getProduto_Nome());
            System.out.println("Valor: R$ " + produto.getProduto_Valor());
            System.out.println("-------------------------------");
        }
    }


    private static void sedeLista() {
        System.out.println("=== Lista de Sedes Cadastrados ===");
        for (Sede Sede : SedeDAO.listar()) {
            System.out.println("ID: " + Sede.getSede_Id());
            System.out.println("Nome: " + Sede.getSede_Nome());
            System.out.println("Lider:  " + Sede.getSede_Lider());
            System.out.println("Endereço:  " + Sede.getSede_Endereço());
            System.out.println("-------------------------------");
        }
    }


    public static void realizarPedido() {
        System.out.println("=== realizar Pedido ===");
        System.out.print("Quantidade do pedido ");
        int quant = scanner.nextInt();

        System.out.println("=== Endereço do pedido ===");
        System.out.print("Endereço da entrega: ");
        String endereco = scanner.next();

        System.out.println("=== realizar Pedido ===");
        System.out.print("Id do produto solicitado");
        int prod_ID = scanner.nextInt();

        System.out.println("=== realizar Pedido ===");
        System.out.print("Id da sede solicitante");
        int Sede_ID = scanner.nextInt();

        Pedido pedido = new Pedido();
        int setPedido_Quant;
        int Pedido_Numero;
        String Pedido_Rua;
        String Pedido_Cidade;
        int setProduto_Id;
        int setSede_Id;


        PedidoDAO.pedir(pedido);
        System.out.println("Pedido solicitado com sucesso!");
    }



}
