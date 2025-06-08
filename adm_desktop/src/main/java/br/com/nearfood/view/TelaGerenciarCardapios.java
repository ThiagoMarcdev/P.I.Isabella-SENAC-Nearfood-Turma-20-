package br.com.nearfood.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaGerenciarCardapios extends JFrame {

    CardLayout cardLayout = new CardLayout();
    JPanel painelPrincipal = new JPanel(cardLayout);

    public TelaGerenciarCardapios() {
        setTitle("NearFood - Gerenciamento de Cardápios");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel Lateral (menu)
        JPanel menuLateral = new JPanel();
        menuLateral.setLayout(new BoxLayout(menuLateral, BoxLayout.Y_AXIS));
        menuLateral.setBackground(new Color(255, 102, 0));
        menuLateral.setPreferredSize(new Dimension(200, 0));

        // Botões principais do menu
        JButton btnInicio = new JButton("Início");
        JButton btnGerenciarCardapio = new JButton("Gerenciar Cardápio");
        JButton btnPedidos = new JButton("Pedidos");
        JButton btnAvaliacoes = new JButton("Avaliações");
        JButton btnRelatorios = new JButton("Relatórios");

        // Submenu de "Gerenciar Cardápio" (inicialmente oculto)
        JPanel subMenuCardapio = new JPanel();
        subMenuCardapio.setLayout(new BoxLayout(subMenuCardapio, BoxLayout.Y_AXIS));
        subMenuCardapio.setBackground(new Color(255, 153, 51));
        subMenuCardapio.setVisible(false);

        // Sub-abas
        JButton btnCadastroProdutos = new JButton("Cadastrar Produto");
        JButton btnVerProdutos = new JButton("Ver Produtos");

        subMenuCardapio.add(btnCadastroProdutos);
        subMenuCardapio.add(btnVerProdutos);

        // Adiciona os botões ao menu lateral
        menuLateral.add(btnInicio);
        menuLateral.add(btnGerenciarCardapio);
        menuLateral.add(subMenuCardapio); // Submenu que aparece/desaparece
        menuLateral.add(btnPedidos);
        
        menuLateral.add(btnAvaliacoes);
        menuLateral.add(btnRelatorios);

        // Telas principais
        JPanel telaInicio = new JPanel();
        telaInicio.add(new JLabel("Bem-vindo ao NearFood"));

        JPanel telaCadastroProdutos = criarTelaCadastro();

        painelPrincipal.add(telaInicio, "inicio");
        painelPrincipal.add(telaCadastroProdutos, "cadastro");

        // Ações dos botões
        btnInicio.addActionListener(e -> cardLayout.show(painelPrincipal, "inicio"));

        btnGerenciarCardapio.addActionListener(e -> {
            // Alterna a visibilidade do submenu
            subMenuCardapio.setVisible(!subMenuCardapio.isVisible());
            revalidate();
            repaint();
        });

        btnCadastroProdutos.addActionListener(e -> cardLayout.show(painelPrincipal, "cadastro"));

        // Layout final da tela
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(criarTopo(), BorderLayout.NORTH);
        getContentPane().add(menuLateral, BorderLayout.WEST);
        getContentPane().add(painelPrincipal, BorderLayout.CENTER);

    }

    private JPanel criarTopo() {
        JPanel topo = new JPanel(new BorderLayout());
        topo.setPreferredSize(new Dimension(0, 60));
        topo.setBackground(new Color(255, 240, 230));

        // Esquerda: logo
        //JLabel lblLogo = new JLabel(new ImageIcon("adm_desktop/src/main/java/resources/images/traced-logo-nearfood.png.png"));
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/traced-logo-nearfood.png.png"));
        Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(img);

        JLabel lblLogo = new JLabel(" NearFood", resizedIcon, JLabel.LEFT);
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblLogo.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        // Centro: nome da página
        JLabel lblTitulo = new JLabel("Gerenciamento de Cardápios", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));

        // Direita: nome do restaurante + ícone
        JPanel painelDireita = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelDireita.setOpaque(false);
        JLabel lblUsuario = new JLabel("Restaurante Exemplo  ");
        JLabel lblIcone = new JLabel(new ImageIcon("src/br/com/nearfood/assets/perfil.png")); // coloque sua imagem aqui
        painelDireita.add(lblUsuario);
        painelDireita.add(lblIcone);
        lblIcone.setIcon(UIManager.getIcon("OptionPane.informationIcon"));

        topo.add(lblLogo, BorderLayout.WEST);
        topo.add(lblTitulo, BorderLayout.CENTER);
        topo.add(painelDireita, BorderLayout.EAST);

        return topo;
    }

    // Painel da tela de cadastro de produtos
    private JPanel criarTelaCadastro() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblNome = new JLabel("Nome do Produto:");
        lblNome.setBounds(30, 20, 150, 25);
        panel.add(lblNome);

        JTextField txtNome = new JTextField();
        txtNome.setBounds(180, 20, 300, 25);
        panel.add(txtNome);

        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setBounds(30, 60, 150, 25);
        panel.add(lblDescricao);

        JTextField txtDescricao = new JTextField();
        txtDescricao.setBounds(180, 60, 300, 25);
        panel.add(txtDescricao);

        JLabel lblPreco = new JLabel("Preço:");
        lblPreco.setBounds(30, 100, 150, 25);
        panel.add(lblPreco);

        JTextField txtPreco = new JTextField();
        txtPreco.setBounds(180, 100, 100, 25);
        panel.add(txtPreco);

        JLabel lblCategoria = new JLabel("Categoria");
        lblCategoria.setBounds(30, 140, 150, 25);
        panel.add(lblCategoria);

        JComboBox<String> cbCategoria = new JComboBox<>(new String[]{"", "Pizza", "Bebida", "Sobremesa"});
        cbCategoria.setBounds(180, 140, 150, 25);
        panel.add(cbCategoria);

        JTextArea txtAreaDescricaoLonga = new JTextArea();
        JScrollPane scroll = new JScrollPane(txtAreaDescricaoLonga);
        scroll.setBounds(30, 180, 600, 200);
        panel.add(scroll);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(550, 400, 80, 30);
        btnSalvar.setBackground(Color.GREEN);
        panel.add(btnSalvar);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaGerenciarCardapios().setVisible(true));
    }
}
