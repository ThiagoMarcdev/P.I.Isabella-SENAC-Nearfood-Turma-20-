package br.com.nearfood.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

public class TelaBackup extends JPanel {

    private JLabel lblUltimoBackup;
    private JLabel lblStatus;
    private JLabel lblEspaco;
    private JButton btnFazerBackup;
    private JButton btnRestaurar;
    private JButton btnAgendar;
    private JTable tabelaHistorico;
    private DefaultTableModel modeloTabela;

    private final Color COR_LARANJA = new Color(251, 157, 25);
    private final Color COR_VERDE = new Color(46, 230, 65);
    private final Color COR_VERMELHO = new Color(247, 75, 56);
    private final Color COR_DOURADO = new Color(184, 134, 11);
    private final Color COR_CINZA = new Color(120, 120, 120);

    public TelaBackup() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(criarCabecalho(), BorderLayout.NORTH);
        add(criarConteudo(), BorderLayout.CENTER);

        carregarHistorico();
    }

    // ================= CABEÇALHO =================
    private JPanel criarCabecalho() {
        JPanel painel = new JPanel();
        painel.setBackground(COR_LARANJA);
        painel.setPreferredSize(new Dimension(0, 70));

        JLabel titulo = new JLabel("Backup e Restauração");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);

        painel.add(titulo);
        return painel;
    }

    // ================= CONTEÚDO =================
    private JPanel criarConteudo() {

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(Color.WHITE);
        painel.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Informações
        lblUltimoBackup = new JLabel("Último backup: --/--/----");
        lblUltimoBackup.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        lblStatus = new JLabel("Status: Aguardando");
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblStatus.setForeground(COR_CINZA);

        lblEspaco = new JLabel("Espaço utilizado: 145MB");
        lblEspaco.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        painel.add(lblUltimoBackup);
        painel.add(Box.createVerticalStrut(8));
        painel.add(lblStatus);
        painel.add(Box.createVerticalStrut(8));
        painel.add(lblEspaco);
        painel.add(Box.createVerticalStrut(20));

        painel.add(criarPainelBotoes());
        painel.add(Box.createVerticalStrut(25));
        painel.add(criarHistorico());

        return painel;
    }

    // ================= BOTÕES =================
    private JPanel criarPainelBotoes() {

        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        painel.setBackground(Color.WHITE);

        btnFazerBackup = criarBotao("Fazer Backup Agora", COR_VERDE);
        btnFazerBackup.addActionListener(e -> fazerBackup());

        btnRestaurar = criarBotao("Restaurar Backup", COR_VERMELHO);
        btnRestaurar.addActionListener(e -> restaurarBackup());

        btnAgendar = criarBotao("Agendar Backup", COR_DOURADO);
        btnAgendar.addActionListener(e -> agendarBackup());

        painel.add(btnFazerBackup);
        painel.add(btnRestaurar);
        painel.add(btnAgendar);

        return painel;
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(190, 42));
        btn.setBackground(cor);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return btn;
    }

    // ================= HISTÓRICO =================
    private JPanel criarHistorico() {

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Histórico de Backups");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        painel.add(titulo, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(
            new String[]{"Data e Hora", "Status"}, 0
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tabelaHistorico = new JTable(modeloTabela);
        tabelaHistorico.setRowHeight(28);

        painel.add(new JScrollPane(tabelaHistorico), BorderLayout.CENTER);
        return painel;
    }

    private void carregarHistorico() {
        modeloTabela.addRow(new Object[]{"20/08/2025 - 18:30", "Concluído ✓"});
        modeloTabela.addRow(new Object[]{"15/08/2025 - 14:44", "Interrompido ⚠"});
    }

    // ================= FUNCIONALIDADES =================
    private void fazerBackup() {

        lblStatus.setText("Status: Em andamento...");
        lblStatus.setForeground(COR_DOURADO);

        btnFazerBackup.setEnabled(false);
        btnRestaurar.setEnabled(false);
        btnAgendar.setEnabled(false);

        new Thread(() -> {
            try {
                Thread.sleep(2500);
                SwingUtilities.invokeLater(() -> {

                    String data = new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(new Date());

                    lblUltimoBackup.setText("Último backup: " + data);
                    lblStatus.setText("Status: Concluído ✓");
                    lblStatus.setForeground(COR_VERDE);

                    modeloTabela.insertRow(0, new Object[]{data, "Concluído ✓"});

                    btnFazerBackup.setEnabled(true);
                    btnRestaurar.setEnabled(true);
                    btnAgendar.setEnabled(true);

                    JOptionPane.showMessageDialog(
                        this,
                        "Backup realizado com sucesso!",
                        "Backup",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                });
            } catch (InterruptedException ignored) {}
        }).start();
    }

    private void restaurarBackup() {
        JOptionPane.showMessageDialog(
            this,
            "Backup restaurado com sucesso!",
            "Restauração",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void agendarBackup() {

        String[] opcoes = {"Diariamente", "Semanalmente", "Mensalmente"};

        String escolha = (String) JOptionPane.showInputDialog(
            this,
            "Escolha a frequência do backup automático:",
            "Agendar Backup",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opcoes,
            opcoes[0]
        );

        if (escolha != null) {
            JOptionPane.showMessageDialog(
                this,
                "Backup automático agendado: " + escolha,
                "Agendamento",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // ================= SUPORTE =================
    public static void enviarSuporte(Component parent) {
        JOptionPane.showMessageDialog(
            parent,
            "Suporte enviado com sucesso!\nNossa equipe entrará em contato.",
            "Suporte",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
