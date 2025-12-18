package br.com.nearfood.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MinhaContaFrame extends JFrame {

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

    // Campos Editar Perfil
    private JTextField edtNome;
    private JTextField edtEmail;
    private JTextField edtTelefone;
    private JPasswordField edtSenha;
    private JPasswordField edtConfirmar;

    // Valores originais (para descartar)
    private final String NOME_ORIGINAL = "João da Silva";
    private final String EMAIL_ORIGINAL = "joao@email.com";
    private final String TELEFONE_ORIGINAL = "(11) 99999-9999";

    public MinhaContaFrame() {
        setTitle("Minha Conta • NearFood");
        setSize(1200, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        criarTopo();
        criarLayoutPrincipal();

        setVisible(true);
    }

    // =========================
    // TOPO
    // =========================
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

    // =========================
    // LAYOUT PRINCIPAL
    // =========================
    private void criarLayoutPrincipal() {

        JPanel base = new JPanel(new BorderLayout());
        base.setBackground(CINZA_BG);
        base.setBorder(new EmptyBorder(20, 20, 20, 20));

        menuLateral = criarMenuLateral();
        base.add(menuLateral, BorderLayout.WEST);
        base.add(criarConteudoCentral(), BorderLayout.CENTER);

        add(base, BorderLayout.CENTER);
    }

    // =========================
    // MENU LATERAL
    // =========================
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

    int opcao = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja sair da sua conta?",
            "Encerrar sessão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
    );

    if (opcao == JOptionPane.YES_OPTION) {
        this.dispose(); // fecha a tela atual

        SwingUtilities.invokeLater(() -> {
            TelaLoginNew login = new TelaLoginNew();
            login.setVisible(true);
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

    // =========================
    // CONTEÚDO CENTRAL
    // =========================
    private JPanel criarConteudoCentral() {

        cardLayout = new CardLayout();
        painelCards = new JPanel(cardLayout);

        painelCards.add(cardMeusDados(), "DADOS");
        painelCards.add(cardEditarPerfil(), "EDITAR");
        painelCards.add(cardAjuda(), "AJUDA");
        painelCards.add(new TelaBackup(), "BACKUP");

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(CINZA_CARD);
        wrapper.setBorder(new EmptyBorder(30, 40, 30, 40));
        wrapper.add(painelCards, BorderLayout.CENTER);

        return wrapper;
    }

    // =========================
    // CARDS
    // =========================
    private JPanel cardMeusDados() {

        JPanel p = baseCard("Meus Dados");

        p.add(campoRead("Nome", NOME_ORIGINAL));
        p.add(campoRead("Email", EMAIL_ORIGINAL));
        p.add(campoRead("Telefone", TELEFONE_ORIGINAL));
        p.add(campoRead("Plano", "Premium"));
        p.add(campoRead("Status", "Conta ativa"));

        return p;
    }

    private JPanel cardEditarPerfil() {

        JPanel p = baseCard("Editar Perfil");

        edtNome = new JTextField(NOME_ORIGINAL);
        edtEmail = new JTextField(EMAIL_ORIGINAL);
        edtTelefone = new JTextField(TELEFONE_ORIGINAL);
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
        enviar.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "Mensagem enviada com sucesso para o suporte.",
                        "Suporte",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );

        p.add(Box.createVerticalStrut(15));
        p.add(enviar);

        return p;
    }

    // =========================
    // LÓGICA EDITAR PERFIL
    // =========================
    private void confirmarAlteracoes() {

    String senha = new String(edtSenha.getPassword());
    String confirmar = new String(edtConfirmar.getPassword());

    // Se algum campo de senha foi preenchido
    if (!senha.isEmpty() || !confirmar.isEmpty()) {

        // Verifica se as senhas coincidem
        if (!senha.equals(confirmar)) {
            JOptionPane.showMessageDialog(
                    this,
                    "As senhas não coincidem.\nPor favor, verifique e tente novamente.",
                    "Erro de validação",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Verifica tamanho mínimo da senha
        if (senha.length() < 6) {
            JOptionPane.showMessageDialog(
                    this,
                    "A senha deve conter no mínimo 6 caracteres.",
                    "Senha inválida",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
    }

    String[] opcoes = {"Salvar", "Descartar alterações", "Cancelar"};

    int escolha = JOptionPane.showOptionDialog(
            this,
            "Deseja salvar as alterações realizadas?",
            "Confirmar alterações",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opcoes,
            opcoes[0]
    );

    if (escolha == 0) { // Salvar
        JOptionPane.showMessageDialog(
                this,
                "Alterações salvas com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE
        );
        edtSenha.setText("");
        edtConfirmar.setText("");
    } else if (escolha == 1) { // Descartar
        edtNome.setText(NOME_ORIGINAL);
        edtEmail.setText(EMAIL_ORIGINAL);
        edtTelefone.setText(TELEFONE_ORIGINAL);
        edtSenha.setText("");
        edtConfirmar.setText("");
    }
}



    // =========================
    // COMPONENTES BASE
    // =========================
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

    // =========================
    // MENU HAMBURGUER
    // =========================
    private void alternarMenu() {
        menuVisivel = !menuVisivel;
        menuLateral.setVisible(menuVisivel);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MinhaContaFrame::new);
    }
}
