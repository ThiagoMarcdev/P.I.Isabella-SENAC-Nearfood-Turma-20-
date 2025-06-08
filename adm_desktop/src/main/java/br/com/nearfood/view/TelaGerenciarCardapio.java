/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package br.com.nearfood.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.Vector;

/**
 *
 * @author cauan.rsilva
 */
public class TelaGerenciarCardapio extends javax.swing.JPanel {
    private JTable tableProdutos;
    private DefaultTableModel tableModel;
    private JPanel panelFiltros;
    private JPanel panelLista;
    private JPanel panelPaginacao;
    private JButton btnAcaoMassa;
    private JComboBox<String> cmbTodosItens;
    private JComboBox<String> cmbFiltrarStatus;
    private JTextField txtBusca;
    private JLabel lblPaginaAtual;
    private JButton btnAnterior;
    private JButton btnProximo;

    /**
     * Creates new form TelaGerenciarCardapio
     */
    public TelaGerenciarCardapio() {
        initComponents();
        setSize(1170, 717);
        setupMenuLateral();
        setupPainelPrincipal();
    }

    private void setupMenuLateral() {
        jPanel2.setBackground(new Color(255, 102, 0));
        jPanel2.setLayout(new BoxLayout(jPanel2, BoxLayout.Y_AXIS));
        jPanel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] menuItems = {
            "Meus pedidos", "Pedidos locais", "Gestor de cardápio",
            "Edição em massa", "Pedidos balcão", "Robô",
            "QR Code", "Recuperador de vendas"
        };

        for (String item : menuItems) {
            JToggleButton btn = new JToggleButton(item);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(190, 40));
            btn.setBackground(new Color(255, 102, 0));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            
            if (item.equals("Edição em massa")) {
                btn.setSelected(true);
                btn.setBackground(new Color(255, 82, 0));
            }
            
            jPanel2.add(btn);
            jPanel2.add(Box.createVerticalStrut(10));
        }
    }

    private void setupPainelPrincipal() {
        // Configuração do painel principal
        fundoListaCardapio.setBackground(Color.WHITE);
        fundoListaCardapio.setLayout(new BorderLayout(10, 10));
        fundoListaCardapio.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel lblTitulo = new JLabel("Edição em massa");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        fundoListaCardapio.add(lblTitulo, BorderLayout.NORTH);

        // Painel de filtros
        panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.setBackground(Color.WHITE);

        cmbTodosItens = new JComboBox<>(new String[]{"Todos itens"});
        cmbFiltrarStatus = new JComboBox<>(new String[]{"Filtrar por status"});
        txtBusca = new JTextField(20);
        btnAcaoMassa = new JButton("Ação em massa");
        
        // Estilização dos componentes
        cmbTodosItens.setPreferredSize(new Dimension(150, 35));
        cmbFiltrarStatus.setPreferredSize(new Dimension(150, 35));
        txtBusca.setPreferredSize(new Dimension(300, 35));
        btnAcaoMassa.setPreferredSize(new Dimension(120, 35));
        btnAcaoMassa.setBackground(new Color(0, 123, 255));
        btnAcaoMassa.setForeground(Color.WHITE);
        btnAcaoMassa.setFocusPainted(false);

        panelFiltros.add(cmbTodosItens);
        panelFiltros.add(Box.createHorizontalStrut(10));
        panelFiltros.add(cmbFiltrarStatus);
        panelFiltros.add(Box.createHorizontalStrut(10));
        panelFiltros.add(txtBusca);
        panelFiltros.add(Box.createHorizontalStrut(10));
        panelFiltros.add(btnAcaoMassa);

        fundoListaCardapio.add(panelFiltros, BorderLayout.CENTER);

        // Configuração da tabela
        String[] colunas = {"", "Item", "Categoria", "Disponibilidade", "Status"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 0 ? Boolean.class : String.class;
            }
        };

        tableProdutos = new JTable(tableModel);
        tableProdutos.setRowHeight(60);
        tableProdutos.getColumnModel().getColumn(0).setPreferredWidth(30);
        tableProdutos.getColumnModel().getColumn(1).setPreferredWidth(300);
        tableProdutos.getColumnModel().getColumn(2).setPreferredWidth(150);
        tableProdutos.getColumnModel().getColumn(3).setPreferredWidth(200);
        tableProdutos.getColumnModel().getColumn(4).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(tableProdutos);
        fundoListaCardapio.add(scrollPane, BorderLayout.CENTER);

        // Painel de paginação
        panelPaginacao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelPaginacao.setBackground(Color.WHITE);
        
        btnAnterior = new JButton("Anterior");
        lblPaginaAtual = new JLabel("Página 1 de 10");
        btnProximo = new JButton("Próximo");
        
        panelPaginacao.add(btnAnterior);
        panelPaginacao.add(Box.createHorizontalStrut(10));
        panelPaginacao.add(lblPaginaAtual);
        panelPaginacao.add(Box.createHorizontalStrut(10));
        panelPaginacao.add(btnProximo);
        
        fundoListaCardapio.add(panelPaginacao, BorderLayout.SOUTH);

        // Adicionar alguns dados de exemplo
        adicionarDadosExemplo();
    }

    private void adicionarDadosExemplo() {
        // Adicionar alguns itens de exemplo à tabela
        Object[] row1 = {false, "Baurus por R$10,00", "Lanches", "D S T Q Q S S", "Ativo"};
        Object[] row2 = {false, "X-Burger por R$15,00", "Lanches", "D S T Q Q S S", "Ativo"};
        Object[] row3 = {false, "Batata Frita por R$8,00", "Acompanhamentos", "D S T Q Q S S", "Inativo"};
        
        tableModel.addRow(row1);
        tableModel.addRow(row2);
        tableModel.addRow(row3);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnBotaoSair = new javax.swing.JButton();
        iconeLogo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnInicio = new javax.swing.JToggleButton();
        btnSair = new javax.swing.JToggleButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnPedidos1 = new javax.swing.JToggleButton();
        btnGerenciarCardapio1 = new javax.swing.JToggleButton();
        fundoListaCardapio = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        txtCampoDeBuscaProdutos = new javax.swing.JTextField();
        conteinerCardapio = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 153, 51));
        jPanel1.setLayout(null);

        jLabel2.setText("Olá Nearfood!");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(1070, 10, 80, 17);

        btnBotaoSair.setText("Sair");
        jPanel1.add(btnBotaoSair);
        btnBotaoSair.setBounds(1070, 40, 70, 23);

        iconeLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/traced-logo-nearfood.png.png"))); // NOI18N
        iconeLogo.setText("NearFood");
        jPanel1.add(iconeLogo);
        iconeLogo.setBounds(0, 10, 80, 80);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Near Food");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(80, 30, 60, 17);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel3.setText("Gerenciamento de cardápios");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(560, 20, 200, 20);

        add(jPanel1);
        jPanel1.setBounds(0, 0, 1280, 80);

        jPanel2.setBackground(new java.awt.Color(255, 102, 0));

        btnInicio.setFont(new java.awt.Font("Liberation Sans", 0, 16)); // NOI18N
        btnInicio.setText("Inicio");

        btnSair.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        btnSair.setText("Sair");

        jButton2.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        jButton2.setText("Configurações da Conta");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        jButton3.setText("Relatórios");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        jButton4.setText("Avaliações");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnPedidos1.setFont(new java.awt.Font("Liberation Sans", 0, 16)); // NOI18N
        btnPedidos1.setText("Pedidos");

        btnGerenciarCardapio1.setFont(new java.awt.Font("Liberation Sans", 0, 16)); // NOI18N
        btnGerenciarCardapio1.setText("Gerenciar Cardápio");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGerenciarCardapio1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnPedidos1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnInicio)
                .addGap(18, 18, 18)
                .addComponent(btnGerenciarCardapio1)
                .addGap(18, 18, 18)
                .addComponent(btnPedidos1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(412, 412, 412)
                .addComponent(btnSair)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel2);
        jPanel2.setBounds(0, 80, 210, 640);

        fundoListaCardapio.setBackground(new java.awt.Color(255, 255, 255));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Total de itens" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Filtrar por status" }));

        txtCampoDeBuscaProdutos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));
        jPanel3.add(jLabel4);

        conteinerCardapio.setViewportView(jPanel3);

        javax.swing.GroupLayout fundoListaCardapioLayout = new javax.swing.GroupLayout(fundoListaCardapio);
        fundoListaCardapio.setLayout(fundoListaCardapioLayout);
        fundoListaCardapioLayout.setHorizontalGroup(
            fundoListaCardapioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fundoListaCardapioLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(fundoListaCardapioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(conteinerCardapio, javax.swing.GroupLayout.PREFERRED_SIZE, 854, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(fundoListaCardapioLayout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(txtCampoDeBuscaProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        fundoListaCardapioLayout.setVerticalGroup(
            fundoListaCardapioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fundoListaCardapioLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(fundoListaCardapioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCampoDeBuscaProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(conteinerCardapio, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        add(fundoListaCardapio);
        fundoListaCardapio.setBounds(240, 110, 900, 590);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBotaoSair;
    private javax.swing.JToggleButton btnGerenciarCardapio1;
    private javax.swing.JToggleButton btnInicio;
    private javax.swing.JToggleButton btnPedidos1;
    private javax.swing.JToggleButton btnSair;
    private javax.swing.JScrollPane conteinerCardapio;
    private javax.swing.JPanel fundoListaCardapio;
    private javax.swing.JLabel iconeLogo;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txtCampoDeBuscaProdutos;
    // End of variables declaration//GEN-END:variables

    public static void main(String[] args) {
        TelaGerenciarCardapio tela = new TelaGerenciarCardapio();
        tela.setVisible(true);
    }
}
