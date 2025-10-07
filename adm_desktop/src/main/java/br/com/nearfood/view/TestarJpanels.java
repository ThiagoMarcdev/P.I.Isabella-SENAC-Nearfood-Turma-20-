/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.nearfood.view;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author firmodev
 */
public class TestarJpanels {
    
    public static void main (String [] args){
        // Garante que a interface use o tema do sistema operacional
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Cria a janela
        JFrame frame = new JFrame("Painel de Edição");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Adiciona seu painel aqui
        frame.add(new PainelEditar());

        // Define tamanho e exibe
        frame.pack(); // ajusta o tamanho automaticamente
        frame.setLocationRelativeTo(null); // centraliza
        frame.setVisible(true);
        
    }
    
}
