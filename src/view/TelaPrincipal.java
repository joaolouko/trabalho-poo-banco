package view;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Sistema Ninja");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        JButton btnNinja = new JButton("Cadastro de Ninjas");
        JButton btnMissao = new JButton("Cadastro de Missões");
        JButton btnVinculo = new JButton("Vínculo Ninja-Missão");
        JButton btnTotalizadores = new JButton("Totalizadores");
        JButton btnViews = new JButton("Views");

        btnNinja.addActionListener(e -> new TelaCadastroNinja().setVisible(true));
        btnMissao.addActionListener(e -> new TelaCadastroMissao().setVisible(true));
        btnVinculo.addActionListener(e -> new TelaVinculoNinjaMissao().setVisible(true));
        btnTotalizadores.addActionListener(e -> new TelaTotalizadores().setVisible(true));
        btnViews.addActionListener(e -> new TelaViews().setVisible(true));

        add(btnNinja);
        add(btnMissao);
        add(btnVinculo);
        add(btnTotalizadores);
        add(btnViews);
    }
}
