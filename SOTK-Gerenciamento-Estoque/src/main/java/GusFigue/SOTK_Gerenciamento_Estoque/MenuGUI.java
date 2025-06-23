package GusFigue.SOTK_Gerenciamento_Estoque;

import DAO.*;
import MODELO.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MenuGUI {
    private JFrame frame;
    private JList<String> navList;
    private DefaultListModel<String> navModel;
    private JTabbedPane tabbedPane;

    // DAOs
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final SedeDAO sedeDAO = new SedeDAO();
    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final VendaDAO vendaDAO = new VendaDAO();
    private final EstoqueDAO estoqueDAO = new EstoqueDAO();
    private final StatusDAO statusDAO = new StatusDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final FornecedorDAO fornecedorDAO = new FornecedorDAO();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuGUI().init());
    }

    private void init() {
        frame = new JFrame("SOTK - Gerenciamento de Estoque");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        // Navegação lateral
        navModel = new DefaultListModel<>();

        String[] options = {
                "Cadastrar Produto", "Listar Produtos", "Cadastrar Sede", "Listar Sedes",
                "Realizar Pedido", "Listar Pedidos", "Consultar Estoque", "Abastecer CD",
                "Registrar Venda", "Consultar Status", "Alterar Status", "Cadastrar Cliente",
                "Listar Clientes", "Cadastrar Fornecedor", "Listar Fornecedores", "Sair"
        };
        for (String opt : options) navModel.addElement(opt);
        navList = new JList<>(navModel);
        navList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        navList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String sel = navList.getSelectedValue();
                if ("Sair".equals(sel)) System.exit(0);
                openTab(sel);
            }
        });

        // Painel de abas
        tabbedPane = new JTabbedPane();

        // Divisor
        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(navList),
                tabbedPane);
        split.setDividerLocation(200);

        frame.getContentPane().add(split, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void openTab(String option) {
        // Seleciona se já aberto
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (tabbedPane.getTitleAt(i).equals(option)) {
                tabbedPane.setSelectedIndex(i);
                return;
            }
        }

        JPanel panel;
        switch (option) {
            case "Cadastrar Produto":
                panel = buildCadastrarProdutoPanel();
                break;
            case "Listar Produtos":
                panel = buildListarProdutosPanel();
                break;
            case "Cadastrar Sede":
                panel = buildCadastrarSedePanel();
                break;
            case "Listar Sedes":
                panel = buildListarSedesPanel();
                break;
            case "Realizar Pedido":
                panel = buildRealizarPedidoPanel();
                break;
            case "Listar Pedidos":
                panel = buildListarPedidosPanel();
                break;
            case "Consultar Estoque":
                panel = buildConsultarEstoquePanel();
                break;
            case "Abastecer CD":
                panel = buildAbastecerCDPanel();
                break;
            case "Registrar Venda":
                panel = buildRegistrarVendaPanel();
                break;
            case "Consultar Status":
                panel = buildConsultarStatusPanel();
                break;
            case "Alterar Status":
                panel = buildAlterarStatusPanel();
                break;
            case "Cadastrar Cliente":
                panel = buildCadastrarClientePanel();
                break;
            case "Listar Clientes":
                panel = buildListarClientesPanel();
                break;
            case "Cadastrar Fornecedor":
                panel = buildCadastrarFornecedorPanel();
                break;
            case "Listar Fornecedores":
                panel = buildListarFornecedoresPanel();
                break;
            default:
                panel = new JPanel();
                panel.add(new JLabel("Opção não implementada."));
        }

        tabbedPane.addTab(option, panel);
        tabbedPane.setSelectedComponent(panel);
    }

    // Panels de cada funcionalidade
    private JPanel buildCadastrarProdutoPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = defaultGbc();
        JTextField nomeF = new JTextField(20);
        JTextField valorF = new JTextField(10);
        JTextField quantF = new JTextField(5);

        p.add(new JLabel("Nome:"), gbc);
        gbc.gridx++; p.add(nomeF, gbc);
        gbc.gridx = 0; gbc.gridy++;
        p.add(new JLabel("Valor:"), gbc);
        gbc.gridx++; p.add(valorF, gbc);
        gbc.gridx = 0; gbc.gridy++;
        p.add(new JLabel("Quantidade CD:"), gbc);
        gbc.gridx++; p.add(quantF, gbc);
        gbc.gridx = 0; gbc.gridy++;
        JButton salvar = new JButton("Salvar");
        salvar.addActionListener(e -> {
            try {
                Produto prod = new Produto();
                prod.setProduto_Nome(nomeF.getText());
                prod.setProduto_Valor(Double.parseDouble(valorF.getText().replace(",", ".")));
                prod.setQuant_CD(Integer.parseInt(quantF.getText()));
                produtoDAO.cadastrar(prod);
                JOptionPane.showMessageDialog(p, "Produto cadastrado!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(p, "Erro: " + ex.getMessage());
            }
        });
        p.add(salvar, gbc);
        return p;
    }

    private JPanel buildListarProdutosPanel() {
        JPanel p = new JPanel(new BorderLayout());
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Produto prod : produtoDAO.listar()) {
            listModel.addElement(
                    String.format("ID:%d - %s - R$%.2f - Qtd:%d",
                            prod.getProduto_Id(), prod.getProduto_Nome(),
                            prod.getProduto_Valor(), prod.getQuant_CD())
            );
        }
        JList<String> list = new JList<>(listModel);
        p.add(new JScrollPane(list), BorderLayout.CENTER);
        return p;
    }

    private JPanel buildCadastrarSedePanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = defaultGbc();
        JTextField nomeF = new JTextField(20);
        JTextField liderF = new JTextField(20);
        JTextField cidadeF = new JTextField(15);
        JTextField ruaF = new JTextField(15);
        JTextField numF = new JTextField(5);

        p.add(new JLabel("Nome:"), gbc);
        gbc.gridx++; p.add(nomeF, gbc);
        gbc.gridx=0; gbc.gridy++;
        p.add(new JLabel("Líder:"), gbc);
        gbc.gridx++; p.add(liderF, gbc);
        gbc.gridx=0; gbc.gridy++;
        p.add(new JLabel("Cidade:"), gbc);
        gbc.gridx++; p.add(cidadeF, gbc);
        gbc.gridx=0; gbc.gridy++;
        p.add(new JLabel("Rua:"), gbc);
        gbc.gridx++; p.add(ruaF, gbc);
        gbc.gridx=0; gbc.gridy++;
        p.add(new JLabel("Número:"), gbc);
        gbc.gridx++; p.add(numF, gbc);
        gbc.gridx=0; gbc.gridy++;
        JButton salvar = new JButton("Salvar");
        salvar.addActionListener(e -> {
            try {
                Sede sede = new Sede();
                sede.setSede_Nome(nomeF.getText());
                sede.setSede_Lider(liderF.getText());
                sede.setSede_Cidade(cidadeF.getText());
                sede.setSede_Rua(ruaF.getText());
                sede.setSede_Numeracao(Integer.parseInt(numF.getText()));
                sedeDAO.cadastrarSede(sede);
                JOptionPane.showMessageDialog(p, "Sede cadastrada!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(p, "Erro: " + ex.getMessage());
            }
        });
        p.add(salvar, gbc);
        return p;
    }

    private JPanel buildListarSedesPanel() {
        JPanel p = new JPanel(new BorderLayout());
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Sede s : sedeDAO.listar()) {
            listModel.addElement(
                    String.format("ID:%d - %s - %s",
                            s.getSede_Id(), s.getSede_Nome(), s.getSede_Cidade())
            );
        }
        p.add(new JList<>(listModel), BorderLayout.CENTER);
        return p;
    }

    private JPanel buildRealizarPedidoPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = defaultGbc();
        JTextField qtdF = new JTextField(5);
        JTextField prodIdF = new JTextField(5);
        JTextField sedeIdF = new JTextField(5);
        JTextField cidadeF = new JTextField(15);
        JTextField ruaF = new JTextField(15);
        JTextField numF = new JTextField(5);

        p.add(new JLabel("Quantidade:"), gbc);
        gbc.gridx++; p.add(qtdF, gbc);
        gbc.gridx=0; gbc.gridy++;
        p.add(new JLabel("ID Produto:"), gbc);
        gbc.gridx++; p.add(prodIdF, gbc);
        gbc.gridx=0; gbc.gridy++;
        p.add(new JLabel("ID Sede:"), gbc);
        gbc.gridx++; p.add(sedeIdF, gbc);
        gbc.gridx=0; gbc.gridy++;
        p.add(new JLabel("Cidade entrega:"), gbc);
        gbc.gridx++; p.add(cidadeF, gbc);
        gbc.gridx=0; gbc.gridy++;
        p.add(new JLabel("Rua entrega:"), gbc);
        gbc.gridx++; p.add(ruaF, gbc);
        gbc.gridx=0; gbc.gridy++;
        p.add(new JLabel("Número:"), gbc);
        gbc.gridx++; p.add(numF, gbc);
        gbc.gridx=0; gbc.gridy++;
        JButton salvar = new JButton("Salvar");
        salvar.addActionListener(e -> {
            try {
                Pedido pedido = new Pedido();
                pedido.setPedido_Quant(Integer.parseInt(qtdF.getText()));
                pedido.setProduto_Id(Integer.parseInt(prodIdF.getText()));
                pedido.setSede_Id(Integer.parseInt(sedeIdF.getText()));
                pedido.setPedido_Cidade(cidadeF.getText());
                pedido.setPedido_Rua(ruaF.getText());
                pedido.setPedido_numeracao(Integer.parseInt(numF.getText()));
                boolean ok = pedidoDAO.cadastrarPedido(pedido);
                JOptionPane.showMessageDialog(p, ok ? "Pedido criado!" : "Falha ao criar pedido.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(p, "Erro: " + ex.getMessage());
            }
        });
        p.add(salvar, gbc);
        return p;
    }

    private JPanel buildListarPedidosPanel() {
        JPanel p = new JPanel(new BorderLayout());
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Pedido pe : pedidoDAO.pedidoListar()) {
            model.addElement(String.format("ID:%d - Prod:%d - Qtd:%d - St:%s",
                    pe.getPedido_Id(), pe.getProduto_Id(), pe.getPedido_Quant(), pe.getPedido_Status()));
        }
        p.add(new JList<>(model), BorderLayout.CENTER);
        return p;
    }

    private JPanel buildConsultarEstoquePanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = defaultGbc();
        JTextField sedeIdF = new JTextField(5);
        p.add(new JLabel("ID Sede:"), gbc);
        gbc.gridx++; p.add(sedeIdF, gbc);
        gbc.gridx=0; gbc.gridy++;
        JButton buscar = new JButton("Consultar");
        buscar.addActionListener(e -> {
            try {
                List<Estoque> list = estoqueDAO.consultarEstoque(
                        Integer.parseInt(sedeIdF.getText()));
                StringBuilder sb = new StringBuilder();
                for (Estoque es : list) {
                    sb.append(es.getProduto_Nome()).append(" - ").append(es.getQuantidade()).append("\n");
                }
                JOptionPane.showMessageDialog(p, sb.length()>0?sb.toString():"Vazio");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(p, "Erro: " + ex.getMessage());
            }
        });
        p.add(buscar, gbc);
        return p;
    }

    private JPanel buildAbastecerCDPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = defaultGbc();
        JTextField prodIdF = new JTextField(5);
        JTextField qtdF = new JTextField(5);
        p.add(new JLabel("ID Produto:"), gbc);
        gbc.gridx++; p.add(prodIdF, gbc);
        gbc.gridx=0; gbc.gridy++;
        p.add(new JLabel("Quantidade:"), gbc);
        gbc.gridx++; p.add(qtdF, gbc);
        gbc.gridx=0; gbc.gridy++;
        JButton bot = new JButton("Abastecer");
        bot.addActionListener(e -> {
            try {
                Produto prod = new Produto();
                prod.setProduto_Id(Integer.parseInt(prodIdF.getText()));
                prod.setQuant_CD(Integer.parseInt(qtdF.getText()));
                produtoDAO.abastecerCD(prod);
                JOptionPane.showMessageDialog(p, "Abastecido!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(p, "Erro: " + ex.getMessage());
            }
        });
        p.add(bot, gbc);
        return p;
    }

    private JPanel buildRegistrarVendaPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = defaultGbc();
        JTextField sedeIdF = new JTextField(5);
        p.add(new JLabel("ID Sede:"), gbc);
        gbc.gridx++; p.add(sedeIdF, gbc);
        gbc.gridx=0; gbc.gridy++;
        JButton addItem = new JButton("Adicionar Itens");
        List<Venda.ItemVenda> itens = new ArrayList<>();
        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(JOptionPane.showInputDialog("ID Produto:"));
                    int q = Integer.parseInt(JOptionPane.showInputDialog("Qtd:"));
                    itens.add(new Venda.ItemVenda(id, q));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(p, "Inválido");
                }
            }
        });
        p.add(addItem, gbc);
        gbc.gridx=0; gbc.gridy++;
        JButton salvar = new JButton("Salvar Venda");
        salvar.addActionListener(e -> {
            vendaDAO.registrarVenda(
                    Integer.parseInt(sedeIdF.getText()), itens);
            JOptionPane.showMessageDialog(p, "Venda ok");
        });
        p.add(salvar, gbc);
        return p;
    }

    private JPanel buildConsultarStatusPanel() {
        JPanel p = new JPanel(new BorderLayout());
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Pedido pe : statusDAO.listarStatus()) {
            model.addElement(
                    String.format("ID:%d - %s", pe.getPedido_Id(), pe.getPedido_Status())
            );
        }
        p.add(new JList<>(model), BorderLayout.CENTER);
        return p;
    }

    private JPanel buildAlterarStatusPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = defaultGbc();
        JTextField idF = new JTextField(5);
        JTextField statusF = new JTextField(10);
        p.add(new JLabel("ID Pedido:"), gbc);
        gbc.gridx++; p.add(idF, gbc);
        gbc.gridx=0; gbc.gridy++;
        p.add(new JLabel("Novo Status:"), gbc);
        gbc.gridx++; p.add(statusF, gbc);
        gbc.gridx=0; gbc.gridy++;
        JButton bot = new JButton("Alterar");
        bot.addActionListener(e -> {
            try {
                Pedido pe = new Pedido();
                pe.setPedido_Id(Integer.parseInt(idF.getText()));
                pe.setPedido_Status(statusF.getText());
                statusDAO.alterarStatus(pe);
                JOptionPane.showMessageDialog(p, "Atualizado");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(p, "Erro: " + ex.getMessage());
            }
        });
        p.add(bot, gbc);
        return p;
    }

    private JPanel buildCadastrarClientePanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = defaultGbc();
        JTextField nomeF = new JTextField(15);
        JTextField cpfF = new JTextField(15);
        JTextField telF = new JTextField(15);
        JTextField cidadeF = new JTextField(15);
        JTextField ruaF = new JTextField(15);
        JTextField numF = new JTextField(5);

        p.add(new JLabel("Nome:"), gbc);
        gbc.gridx++; p.add(nomeF, gbc);
        gbc.gridx=0; gbc.gridy++;
        p.add(new JLabel("CPF:"), gbc); gbc.gridx++; p.add(cpfF, gbc);
        gbc.gridx=0; gbc.gridy++;
        p.add(new JLabel("Telefone:"), gbc); gbc.gridx++; p.add(telF, gbc);
        gbc.gridx=0; gbc.gridy++;
        p.add(new JLabel("Cidade:"), gbc); gbc.gridx++; p.add(cidadeF, gbc);
        gbc.gridx=0; gbc.gridy++;
        p.add(new JLabel("Rua:"), gbc); gbc.gridx++; p.add(ruaF, gbc);
        gbc.gridx=0; gbc.gridy++;
        p.add(new JLabel("Número:"), gbc); gbc.gridx++; p.add(numF, gbc);
        gbc.gridx=0; gbc.gridy++;
        JButton bot = new JButton("Salvar");
        bot.addActionListener(e -> {
            try {
                Cliente cl = new Cliente();
                cl.setCliente_Nome(nomeF.getText());
                cl.setCliente_Cpf(cpfF.getText());
                cl.setCliente_Tel(telF.getText());
                cl.setCliente_Cidade(cidadeF.getText());
                cl.setCliente_Rua(ruaF.getText());
                cl.setCliente_Numeracao(Integer.parseInt(numF.getText()));
                clienteDAO.CadastrarCliente(cl);
                JOptionPane.showMessageDialog(p, "Cliente ok");
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(p, "Erro: " + ex.getMessage());
            }
        });
        p.add(bot, gbc);
        return p;
    }

    private JPanel buildListarClientesPanel() {
        JPanel p = new JPanel(new BorderLayout());
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Cliente c : clienteDAO.listarCliente()) {
            model.addElement(
                    String.format("ID:%d - %s - %s",
                            c.getCliente_Id(), c.getCliente_Nome(), c.getCliente_Cpf())
            );
        }
        p.add(new JList<>(model), BorderLayout.CENTER);
        return p;
    }

    private JPanel buildCadastrarFornecedorPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = defaultGbc();
        JTextField nomeF = new JTextField(15);
        JTextField cnpjF = new JTextField(15);
        JTextField catF = new JTextField(20);
        p.add(new JLabel("Nome:"), gbc);
        gbc.gridx++; p.add(nomeF, gbc);
        gbc.gridx=0; gbc.gridy++;
        p.add(new JLabel("CNPJ:"), gbc); gbc.gridx++; p.add(cnpjF, gbc);
        gbc.gridx=0; gbc.gridy++;
        p.add(new JLabel("Catálogo:"), gbc); gbc.gridx++; p.add(catF, gbc);
        gbc.gridx=0; gbc.gridy++;
        JButton bot = new JButton("Salvar");
        bot.addActionListener(e -> {
            try {
                Fornecedor f = new Fornecedor();
                f.setFornecedor_Nome(nomeF.getText());
                f.setFornecedor_Cnpj(cnpjF.getText());
                f.setFornecedor_Catalogo(catF.getText());
                fornecedorDAO.CadastrarFornecedor(f);
                JOptionPane.showMessageDialog(p, "Fornecedor ok");
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(p, "Erro: " + ex.getMessage());
            }
        });
        p.add(bot, gbc);
        return p;
    }

    private JPanel buildListarFornecedoresPanel() {
        JPanel p = new JPanel(new BorderLayout());
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Fornecedor f : fornecedorDAO.listarFornecedor()) {
            model.addElement(
                    String.format("ID:%d - %s - %s",
                            f.getFornecedor_Id(), f.getFornecedor_Nome(), f.getFornecedor_Cnpj())
            );
        }
        p.add(new JList<>(model), BorderLayout.CENTER);
        return p;
    }

    private GridBagConstraints defaultGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = 0;
        return gbc;
    }
}
