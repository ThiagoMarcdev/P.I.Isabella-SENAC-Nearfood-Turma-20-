package br.com.nearfood.views;

import br.com.nearfood.models.Usuario;
import br.com.nearfood.service.UsuarioService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MinhaContaFrame extends JFrame {

    private UsuarioService service;
    private Usuario usuarioAtual;
    private Long idUsuarioLogado;

    // =========================
    // CORES
    // =========================
    private final Color LARANJA = new Color(255, 122, 0);
    private final Color BRANCO = Color.WHITE;
    private final Color CINZA_BG = new Color(245, 245, 245);
    private final Color CINZA_CARD = new Color(230, 230, 230);
    private final Color TEXTO = new Color(50, 50, 50);

    // =========================
    // CONTROLE
    // =========================
    private JPanel menuLateral;
    private JPanel painelCards;
    private CardLayout cardLayout;
    private boolean menuVisivel = true;

    // Campos de Visualização (Meus Dados)
    private JTextField viewNome;
    private JTextField viewEmail;
    private JTextField viewTelefone;

    // Campos Editar Perfil
    private JTextField edtNome;
    private JTextField edtEmail;
    private JTextField edtTelefone;
    private JPasswordField edtSenha;
    private JPasswordField edtConfirmar;

    // Strings para Placeholder (caso a API falhe)
    private final String CARREGANDO = "Carregando...";

    public MinhaContaFrame(Long idDoUsuario) {
        this.idUsuarioLogado = idDoUsuario;
        this.service = new UsuarioService();

        setTitle("Minha Conta • NearFood");
        setSize(1200, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        criarTopo();
        criarLayoutPrincipal();

        // Inicia o carregamento
        carregarDadosDaApi();

        setVisible(true);
    }

    public void carregarDadosDaApi() {
        // Define texto de carregamento enquanto busca
        if(viewNome != null) viewNome.setText(CARREGANDO);

        service.buscarUsuario(this.idUsuarioLogado)
            .thenAccept(usuario -> {
                this.usuarioAtual = usuario;
                // Atualiza a UI na Thread correta
                SwingUtilities.invokeLater(this::atualizarCamposUI);
            })
            .exceptionally(ex -> {
                SwingUtilities.invokeLater(() -> 
                    JOptionPane.showMessageDialog(this, "Erro ao conectar com API: " + ex.getMessage())
                );
                return null;
            });
    }

    private void atualizarCamposUI() {
        if (usuarioAtual != null) {
            // Tratamento simples para evitar "null" na tela
            String nome = (usuarioAtual.getFirst_name() != null ? usuarioAtual.getFirst_name() : "") + " " + 
                          (usuarioAtual.getLast_name() != null ? usuarioAtual.getLast_name() : "");
            
            String email = usuarioAtual.getEmail() != null ? usuarioAtual.getEmail() : "";
            String fone = usuarioAtual.getTelefone() != null ? usuarioAtual.getTelefone() : "";

            // Atualiza aba "Meus Dados"
            viewNome.setText(nome.trim());
            viewEmail.setText(email);
            viewTelefone.setText(fone);

            // Atualiza aba "Editar" com os valores para edição
            // Nota: No editar, geralmente separamos Nome e Sobrenome, mas aqui vamos por tudo no nome
            // ou pegar apenas o primeiro nome, dependendo da sua regra de negócio.
            // Vou manter a lógica de colocar o nome completo:
            edtNome.setText(nome.trim()); 
            edtEmail.setText(email);
            edtTelefone.setText(fone);
        }
    }

    // =========================
    // CARDS (CORRIGIDOS)
    // =========================
    private JPanel cardMeusDados() {
        JPanel p = baseCard("Meus Dados");

        // --- CORREÇÃO AQUI ---
        // Antes você criava um campo novo com texto fixo e não salvava na variável.
        // Agora instanciamos as variáveis da classe e adicionamos ELAS ao painel.
        
        viewNome = new JTextField(CARREGANDO);
        viewNome.setEnabled(false);

        viewEmail = new JTextField(CARREGANDO);
        viewEmail.setEnabled(false);

        viewTelefone = new JTextField(CARREGANDO);
        viewTelefone.setEnabled(false);

        p.add(campoBase("Nome", viewNome));
        p.add(campoBase("Email", viewEmail));
        p.add(campoBase("Telefone", viewTelefone));
        p.add(campoRead("Plano", "Premium")); 
        p.add(campoRead("Status", "Conta ativa"));

        return p;
    }

    private JPanel cardEditarPerfil() {
        JPanel p = baseCard("Editar Perfil");

        edtNome = new JTextField();
        edtEmail = new JTextField();
        edtTelefone = new JTextField();
        edtSenha = new JPasswordField();
        edtConfirmar = new JPasswordField();

        p.add(campoBase("Nome", edtNome));
        p.add(campoBase("Email", edtEmail));
        p.add(campoBase("Telefone", edtTelefone));
        p.add(campoBase("Nova Senha", edtSenha));
        p.add(campoBase("Confirmar Senha", edtConfirmar));

        JButton salvar = new JButton("Salvar Alterações");
        salvar.addActionListener(e -> confirmarAlteracoes());

        p.add(Box.createVerticalStrut(20));
        p.add(salvar);

        return p;
    }

    // =========================
    // LÓGICA EDITAR PERFIL
    // =========================
    private void confirmarAlteracoes() {
        if (usuarioAtual == null) {
            JOptionPane.showMessageDialog(this, "Usuário não carregado. Verifique sua conexão.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String senha = new String(edtSenha.getPassword());
        String confirmar = new String(edtConfirmar.getPassword());

        // Validações
        if (!senha.isEmpty()) {
            if (!senha.equals(confirmar)) {
                JOptionPane.showMessageDialog(this, "As senhas não coincidem.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (senha.length() < 6) {
                JOptionPane.showMessageDialog(this, "Senha muito curta.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        int escolha = JOptionPane.showConfirmDialog(this, "Deseja salvar as alterações na nuvem?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (escolha == JOptionPane.YES_OPTION) {
            // Atualiza o objeto local
            // ATENÇÃO: Se sua API espera First e Last name separados, você precisará dividir a string edtNome.getText()
            usuarioAtual.setFirst_name(edtNome.getText()); 
            // usuarioAtual.setLast_name(""); // Se for necessário limpar ou tratar sobrenome
            
            usuarioAtual.setEmail(edtEmail.getText());
            usuarioAtual.setTelefone(edtTelefone.getText());
            
            if (!senha.isEmpty()) {
                usuarioAtual.setPassword(senha);
            }

            // Envia para API
            service.atualizarUsuario(usuarioAtual)
                .thenAccept(sucesso -> {
                    SwingUtilities.invokeLater(() -> {
                        if (sucesso) {
                            JOptionPane.showMessageDialog(this, "Dados atualizados com sucesso!");
                            atualizarCamposUI(); // Reflete as mudanças
                            edtSenha.setText("");
                            edtConfirmar.setText("");
                            cardLayout.show(painelCards, "DADOS");
                        } else {
                            JOptionPane.showMessageDialog(this, "Erro ao salvar na API.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                })
                .exceptionally(ex -> {
                    SwingUtilities.invokeLater(() -> 
                        JOptionPane.showMessageDialog(this, "Falha na comunicação: " + ex.getMessage())
                    );
                    return null;
                });
        }
    }

    // =========================
    // MÉTODOS AUXILIARES E LAYOUT (Mantidos)
    // =========================

    private JPanel cardAjuda() {
        JPanel p = baseCard("Ajuda e Suporte");
        JTextField assunto = new JTextField();
        JTextField email = new JTextField("user@email.com");
        JTextArea msg = new JTextArea(5, 20);
        msg.setLineWrap(true);
        msg.setWrapStyleWord(true);

        p.add(campoBase("Assunto", assunto));
        p.add(campoBase("Seu Email", email));
        p.add(new JLabel("Mensagem"));
        p.add(new JScrollPane(msg));

        JButton enviar = new JButton("Enviar para o suporte");
        enviar.addActionListener(e -> JOptionPane.showMessageDialog(this, "Mensagem enviada com sucesso!", "Suporte", JOptionPane.INFORMATION_MESSAGE));
        p.add(Box.createVerticalStrut(15));
        p.add(enviar);
        return p;
    }

    private void criarTopo() {
        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(LARANJA);
        topo.setPreferredSize(new Dimension(0, 60));
        topo.setBorder(new EmptyBorder(0, 20, 0, 20));

        JLabel titulo = new JLabel("Minha Conta");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JPanel direita = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 12));
        direita.setOpaque(false);

        JLabel usuario = new JLabel("👤");
        usuario.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        usuario.setForeground(Color.WHITE);

        JButton menu = new JButton("☰");
        menu.setFont(new Font("Segoe UI", Font.BOLD, 22));
        menu.setForeground(Color.WHITE);
        menu.setContentAreaFilled(false);
        menu.setBorderPainted(false);
        menu.setFocusPainted(false);
        menu.addActionListener(e -> alternarMenu());

        direita.add(usuario);
        direita.add(menu);
        topo.add(titulo, BorderLayout.WEST);
        topo.add(direita, BorderLayout.EAST);
        add(topo, BorderLayout.NORTH);
    }

    private void criarLayoutPrincipal() {
        JPanel base = new JPanel(new BorderLayout());
        base.setBackground(CINZA_BG);
        base.setBorder(new EmptyBorder(20, 20, 20, 20));
        menuLateral = criarMenuLateral();
        base.add(menuLateral, BorderLayout.WEST);
        base.add(criarConteudoCentral(), BorderLayout.CENTER);
        add(base, BorderLayout.CENTER);
    }

    private JPanel criarMenuLateral() {
        JPanel menu = new JPanel();
        menu.setPreferredSize(new Dimension(240, 0));
        menu.setBackground(BRANCO);
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(30, 20, 30, 20));

        menu.add(botaoMenu("Meus Dados", "DADOS"));
        menu.add(Box.createVerticalStrut(12));
        menu.add(botaoMenu("Editar Perfil", "EDITAR"));
        menu.add(Box.createVerticalStrut(12));
        menu.add(botaoMenu("Backup do Sistema", "BACKUP"));
        menu.add(Box.createVerticalGlue());
        menu.add(botaoMenu("Ajuda e Suporte", "AJUDA"));
        menu.add(Box.createVerticalStrut(15));

        JButton sair = new JButton("Sair / Encerrar Sessão");
        sair.setAlignmentX(Component.CENTER_ALIGNMENT);
        sair.addActionListener(e -> {
            int opcao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja sair?", "Encerrar sessão", JOptionPane.YES_NO_OPTION);
            if (opcao == JOptionPane.YES_OPTION) {
                this.dispose();
                SwingUtilities.invokeLater(() -> {
                    // Verifique se TelaLoginNew existe no seu projeto
                    new TelaLoginNew().setVisible(true); 
                });
            }
        });
        menu.add(sair);
        return menu;
    }

    private JButton botaoMenu(String texto, String card) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(200, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.addActionListener(e -> cardLayout.show(painelCards, card));
        return btn;
    }

    private JPanel criarConteudoCentral() {
        cardLayout = new CardLayout();
        painelCards = new JPanel(cardLayout);

        painelCards.add(cardMeusDados(), "DADOS");
        painelCards.add(cardEditarPerfil(), "EDITAR");
        painelCards.add(cardAjuda(), "AJUDA");
        // Verifique se TelaBackup existe no seu projeto
        painelCards.add(new TelaBackup(), "BACKUP"); 

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(CINZA_CARD);
        wrapper.setBorder(new EmptyBorder(30, 40, 30, 40));
        wrapper.add(painelCards, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel baseCard(String titulo) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        JLabel t = new JLabel(titulo);
        t.setFont(new Font("Segoe UI", Font.BOLD, 22));
        t.setForeground(TEXTO);
        p.add(t);
        p.add(Box.createVerticalStrut(25));
        return p;
    }

    private JPanel campoRead(String label, String valor) {
        JTextField campo = new JTextField(valor);
        campo.setEnabled(false);
        return campoBase(label, campo);
    }

    private JPanel campoBase(String label, JComponent campo) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        p.add(new JLabel(label), BorderLayout.NORTH);
        p.add(campo, BorderLayout.CENTER);
        return p;
    }

    private void alternarMenu() {
        menuVisivel = !menuVisivel;
        menuLateral.setVisible(menuVisivel);
        revalidate();
        repaint();
    }

}