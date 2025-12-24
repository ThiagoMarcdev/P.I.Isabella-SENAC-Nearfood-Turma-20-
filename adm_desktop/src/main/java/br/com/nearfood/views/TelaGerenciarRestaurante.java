package br.com.nearfood.views;

import br.com.nearfood.models.Restaurante;
import br.com.nearfood.service.RestauranteService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class TelaGerenciarRestaurante extends JFrame {
    
    private Long idUsuarioLogado;

    // ===== SERVICE =====
    private final RestauranteService restauranteService = new RestauranteService();

    // ===== COMPONENTES DE ESTADO =====
    private JTextField txtNome;
    private JTextArea txtDescricao;
    private JLabel lblImagem;
    private File imagemSelecionada;

    // ===== CONSTRUTOR =====
    public TelaGerenciarRestaurante(Long idUsuario) {
        this.idUsuarioLogado = idUsuario;
        configurarTela();
    }

    // ===== CONFIGURAÇÃO PRINCIPAL =====
    private void configurarTela() {

        setTitle("NearFood - Gerenciar Restaurante");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Color.WHITE);

        container.add(criarTopo(), BorderLayout.NORTH);
        container.add(criarConteudo(), BorderLayout.CENTER);
        container.add(criarRodape(), BorderLayout.SOUTH);

        setContentPane(container);

        // 🔹 PASSO H
        carregarDadosRestaurante();
    }

    // ================= TOPO =================
    private JPanel criarTopo() {

        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(Color.WHITE);
        topo.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblNomeRestaurante = new JLabel("Nome do Restaurante");
        lblNomeRestaurante.setFont(new Font("Poppins", Font.BOLD, 20));

        JButton btnMinhaConta = new JButton("Minha Conta");
        btnMinhaConta.setFocusPainted(false);

        topo.add(lblNomeRestaurante, BorderLayout.WEST);
        topo.add(btnMinhaConta, BorderLayout.EAST);
        
        btnMinhaConta.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // Passamos o ID salvo para a próxima tela
                MinhaContaFrame telaConta = new MinhaContaFrame(idUsuarioLogado);
                telaConta.setVisible(true);
                dispose();
            }
        });

        return topo;
    }

    // ================= CONTEÚDO =================
    private JPanel criarConteudo() {

        JPanel conteudo = new JPanel(new BorderLayout());
        conteudo.setBackground(new Color(245, 245, 245));
        conteudo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        conteudo.add(criarFormulario(), BorderLayout.CENTER);
        conteudo.add(criarPainelImagem(), BorderLayout.EAST);

        return conteudo;
    }

    // ================= FORMULÁRIO =================
    private JPanel criarFormulario() {

        JPanel formulario = new JPanel();
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));
        formulario.setBackground(Color.WHITE);
        formulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Dados do Restaurante");
        lblTitulo.setFont(new Font("Poppins", Font.BOLD, 18));

        txtNome = new JTextField();
        txtNome.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtNome.setBorder(BorderFactory.createTitledBorder("Nome"));

        txtDescricao = new JTextArea(6, 20);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        txtDescricao.setBorder(BorderFactory.createTitledBorder("Descrição"));

        formulario.add(lblTitulo);
        formulario.add(Box.createVerticalStrut(20));
        formulario.add(txtNome);
        formulario.add(Box.createVerticalStrut(15));
        formulario.add(new JScrollPane(txtDescricao));

        return formulario;
    }

    // ================= IMAGEM =================
    private JPanel criarPainelImagem() {

        JPanel painelImagem = new JPanel(new BorderLayout());
        painelImagem.setPreferredSize(new Dimension(300, 0));
        painelImagem.setBackground(Color.WHITE);
        painelImagem.setBorder(BorderFactory.createTitledBorder("Imagem do Restaurante"));

        lblImagem = new JLabel("Clique para selecionar imagem", SwingConstants.CENTER);
        lblImagem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        lblImagem.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                selecionarImagem();
            }
        });

        painelImagem.add(lblImagem, BorderLayout.CENTER);

        return painelImagem;
    }

    // ================= RODAPÉ =================
    private JPanel criarRodape() {

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rodape.setBackground(Color.WHITE);
        rodape.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setFocusPainted(false);

        btnSalvar.addActionListener(e -> salvarRestaurante());

        rodape.add(btnSalvar);

        return rodape;
    }

    // ================= AÇÕES =================
    private void selecionarImagem() {

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            imagemSelecionada = chooser.getSelectedFile();
            atualizarPreviewImagem(imagemSelecionada);
        }
    }

    private void atualizarPreviewImagem(File imagem) {

        if (imagem == null || !imagem.exists()) {
            lblImagem.setText("Sem imagem");
            lblImagem.setIcon(null);
            return;
        }

        ImageIcon icon = new ImageIcon(imagem.getAbsolutePath());
        Image img = icon.getImage().getScaledInstance(
                lblImagem.getWidth(),
                lblImagem.getHeight(),
                Image.SCALE_SMOOTH
        );

        lblImagem.setText("");
        lblImagem.setIcon(new ImageIcon(img));
    }

    private void salvarRestaurante() {

        String nome = txtNome.getText().trim();
        String descricao = txtDescricao.getText().trim();

        if (nome.isEmpty() || descricao.isEmpty() || imagemSelecionada == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Preencha todos os campos e selecione uma imagem.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        Restaurante restaurante = new Restaurante(nome, descricao, imagemSelecionada);
        restauranteService.salvar(restaurante);

        JOptionPane.showMessageDialog(
                this,
                "✅ Restaurante salvo com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ================= PASSO H =================
    private void carregarDadosRestaurante() {

        Restaurante restaurante = restauranteService.buscar();

        if (restaurante == null) {
            return;
        }

        txtNome.setText(restaurante.getNome());
        txtDescricao.setText(restaurante.getDescricao());

        imagemSelecionada = restaurante.getImagem();
        atualizarPreviewImagem(imagemSelecionada);
    }
    
    
   
    
    
    
}
