package br.com.nearfood.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
 
public class TelaBackup extends JFrame {
    // Componentes da interface
    private JLabel lblUltimoBackup;
    private JLabel lblStatus;
    private JLabel lblEspaco;
    private JButton btnFazerBackup;
    private JButton btnRestaurar;
    private JButton btnAgendar;
    private JTable tabelaHistorico;
    private DefaultTableModel modeloTabela;
    // Cores do sistema
    private final Color COR_LARANJA = new Color(251, 157, 25); // #FB9D19
    private final Color COR_VERDE = new Color(46, 230, 65); // #2BE641
    private final Color COR_VERMELHO = new Color(247, 75, 56); // #F74B38
    private final Color COR_DOURADO = new Color(184, 134, 11);
    public TelaBackup() {
        inicializarComponentes();
        carregarHistorico();
    }
    private void inicializarComponentes() {
        setTitle("Tela Backup - NearFood");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        // Painel principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBackground(Color.WHITE);
        // Cabeçalho
        JPanel painelCabecalho = criarCabecalho();
        painelPrincipal.add(painelCabecalho);
        // Painel de informações
        JPanel painelInfo = criarPainelInformacoes();
        painelPrincipal.add(painelInfo);
        // Painel de botões
        JPanel painelBotoes = criarPainelBotoes();
        painelPrincipal.add(painelBotoes);
        // Painel de histórico
        JPanel painelHistorico = criarPainelHistorico();
        painelPrincipal.add(painelHistorico);
        add(painelPrincipal, BorderLayout.CENTER);
    }
    private JPanel criarCabecalho() {
        JPanel painel = new JPanel();
        painel.setBackground(COR_LARANJA);
        painel.setPreferredSize(new Dimension(800, 80));
        painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        JLabel lblTitulo = new JLabel("NearFood  Backup e Restauração");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        painel.add(lblTitulo);
        return painel;
    }
    private JPanel criarPainelInformacoes() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(Color.WHITE);
        painel.setBorder(new EmptyBorder(20, 40, 20, 40));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        String dataAtual = sdf.format(new Date());
        lblUltimoBackup = new JLabel("Último backup realizado:");
        lblUltimoBackup.setFont(new Font("Arial", Font.PLAIN, 16));
        painel.add(lblUltimoBackup);
        JLabel lblData = new JLabel(dataAtual);
        lblData.setFont(new Font("Arial", Font.PLAIN, 16));
        painel.add(lblData);
        painel.add(Box.createRigidArea(new Dimension(0, 10)));
        lblStatus = new JLabel("Status: ✓ Sucesso");
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 16));
        lblStatus.setForeground(COR_VERDE);
        painel.add(lblStatus);
        painel.add(Box.createRigidArea(new Dimension(0, 20)));
        lblEspaco = new JLabel("Espaço utilizado:");
        lblEspaco.setFont(new Font("Arial", Font.PLAIN, 16));
        painel.add(lblEspaco);
        JLabel lblTamanho = new JLabel("145MB");
        lblTamanho.setFont(new Font("Arial", Font.PLAIN, 16));
        painel.add(lblTamanho);
        return painel;
    }
    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel();
        painel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        painel.setBackground(Color.WHITE);
        // Botão Fazer Backup Agora
        btnFazerBackup = new JButton("Fazer Backup Agora");
        btnFazerBackup.setFont(new Font("Arial", Font.BOLD, 14));
        btnFazerBackup.setBackground(COR_VERDE);
        btnFazerBackup.setForeground(Color.BLACK);
        btnFazerBackup.setPreferredSize(new Dimension(200, 45));
        btnFazerBackup.setFocusPainted(false);
        btnFazerBackup.setBorderPainted(false);
        btnFazerBackup.addActionListener(e -> fazerBackup());
        // Botão Restaurar Backup
        btnRestaurar = new JButton("Restaurar Backup");
        btnRestaurar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRestaurar.setBackground(COR_VERMELHO);
        btnRestaurar.setForeground(Color.WHITE);
        btnRestaurar.setPreferredSize(new Dimension(200, 45));
        btnRestaurar.setFocusPainted(false);
        btnRestaurar.setBorderPainted(false);
        btnRestaurar.addActionListener(e -> restaurarBackup());
        // Botão Agendar Backup Automático
        btnAgendar = new JButton("Agendar Backup Automático");
        btnAgendar.setFont(new Font("Arial", Font.BOLD, 14));
        btnAgendar.setBackground(COR_DOURADO);
        btnAgendar.setForeground(Color.WHITE);
        btnAgendar.setPreferredSize(new Dimension(230, 45));
        btnAgendar.setFocusPainted(false);
        btnAgendar.setBorderPainted(false);
        btnAgendar.addActionListener(e -> agendarBackup());
        painel.add(btnFazerBackup);
        painel.add(btnRestaurar);
        painel.add(btnAgendar);
        return painel;
    }
    private JPanel criarPainelHistorico() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Color.WHITE);
        painel.setBorder(new EmptyBorder(20, 40, 40, 40));
        JLabel lblTitulo = new JLabel("Histórico de Backups");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(lblTitulo, BorderLayout.NORTH);
        // Criar tabela
        String[] colunas = {"Data e Hora", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaHistorico = new JTable(modeloTabela);
        tabelaHistorico.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelaHistorico.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(tabelaHistorico);
        scrollPane.setPreferredSize(new Dimension(700, 150));
        painel.add(scrollPane, BorderLayout.CENTER);
        return painel;
    }
    private void carregarHistorico() {
        // Dados de exemplo
        modeloTabela.addRow(new Object[]{"20/08/2025 - 18:30", "Concluído ✓"});
        modeloTabela.addRow(new Object[]{"15/08/2025 - 14:44", "Interrompido ⚠"});
        modeloTabela.addRow(new Object[]{"02/08/2025 - 15:00", "Concluído ✓"});
    }
    // Funcionalidades dos botões
    private void fazerBackup() {
        JOptionPane.showMessageDialog(this, 
            "Iniciando backup do sistema...", 
            "Backup", 
            JOptionPane.INFORMATION_MESSAGE);
        // Simular processo de backup
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                SwingUtilities.invokeLater(() -> {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
                    String dataAtual = sdf.format(new Date());
                    modeloTabela.insertRow(0, new Object[]{dataAtual, "Concluído ✓"});
                    JOptionPane.showMessageDialog(this, 
                        "Backup realizado com sucesso!", 
                        "Sucesso", 
                        JOptionPane.INFORMATION_MESSAGE);
                });
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
    private void restaurarBackup() {
        int resposta = JOptionPane.showConfirmDialog(this,
            "Deseja realmente restaurar o backup?\nEsta ação substituirá os dados atuais.",
            "Confirmar Restauração",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        if (resposta == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this,
                "Backup restaurado com sucesso!",
                "Restauração",
                JOptionPane.INFORMATION_MESSAGE);
        }
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
            opcoes[0]);
        if (escolha != null) {
            JOptionPane.showMessageDialog(this,
                "Backup automático agendado: " + escolha,
                "Agendamento",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaBackup tela = new TelaBackup();
            tela.setVisible(true);
        });
    }
}