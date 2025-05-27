package MenuInicial;

import java.util.Scanner;

public class Menu {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Sistema iniciado, escolha uma opção");
         System.out.println("1. Cadastrar Produto");
            System.out.println("2. Cadastrar Sede");
            System.out.println("3. Realizar Pedido");
            System.out.println("4. Consultar Estoque");
            System.out.println("5. Listar Produtos");
            System.out.println("6. Listar Sedes");
            System.out.println("7. Sair");
            int opcao = scanner.nextInt(); 

            switch (opcao) {
                case 1 -> System.out.println("1. Cadastrar Produto");
                    //ir para classe cadastrar produto

                case 2 -> System.out.println("2. Cadastrar Sede");
                    //ir para classe cadastrar Sede

                case 3 -> System.out.println("3. Realizar Pedido");
                    //ir para Realizar pedido

                case 4 -> System.out.println("4. Consultar Estoque");
                    //ir para Consultar Estoque

                case 5 -> System.out.println("5. Listar Produtos");
                    //ir para Listar Produtos

                case 6 -> System.out.println("6. Listar Sedes");
                    //ir para listar sedes


                case 7 -> System.out.println("7. Sair");
                    //Saindo

                default -> System.out.println("Opção Invalida, por favor selecione um numero de operação valido");
            }
    }
}
