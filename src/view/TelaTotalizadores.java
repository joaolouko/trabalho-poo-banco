package view;

import model.TotalizadorNinja;
import service.TotalizadorService;
import util.JTableUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaTotalizadores extends JFrame {

    private JTable tabela;
    private DefaultTableModel tableModel;
    private TotalizadorService totalizadorService;

    public TelaTotalizadores() {
        totalizadorService = new TotalizadorService();

        setTitle("Totalizadores");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelTopo = new JPanel();
        JButton btnGerar = new JButton("Gerar Totalizadores");
        painelTopo.add(btnGerar);
        add(painelTopo, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Descrição", "Quantidade", "Data Geração"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnGerar.addActionListener(e -> gerarTotalizadores());

        carregarTabela();
    }

    private void gerarTotalizadores() {
        try {
            totalizadorService.gerarTotalizadores();
            JOptionPane.showMessageDialog(this, "Totalizadores gerados com sucesso!");
            carregarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar totalizadores: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarTabela() {
        JTableUtils.clearTable(tableModel);
        try {
            List<TotalizadorNinja> totais = totalizadorService.listarTodos();
            for (TotalizadorNinja tn : totais) {
                tableModel.addRow(new Object[]{
                        tn.getId(), tn.getDescricao(), tn.getQuantidade(), tn.getDataGeracao()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tabela: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
