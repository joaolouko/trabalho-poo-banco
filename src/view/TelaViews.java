package view;

import connection.Conexao;
import util.JTableUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class TelaViews extends JFrame {

    private JComboBox<String> cbViews;
    private JTable tabela;
    private DefaultTableModel tableModel;

    public TelaViews() {
        setTitle("Consultar Views");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelTopo = new JPanel();
        painelTopo.add(new JLabel("Selecione a View:"));
        
        cbViews = new JComboBox<>(new String[]{
                "vw_ninja_missoes",
                "vw_total_ninjas_por_vila",
                "vw_total_missoes_por_rank",
                "vw_missoes_sem_ninjas"
        });
        painelTopo.add(cbViews);

        JButton btnConsultar = new JButton("Consultar");
        painelTopo.add(btnConsultar);
        
        add(painelTopo, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnConsultar.addActionListener(e -> consultarView());
    }

    private void consultarView() {
        String viewSelecionada = cbViews.getSelectedItem().toString();
        String sql = "SELECT * FROM " + viewSelecionada;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Limpar tabela
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);

            // Adicionar colunas
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(metaData.getColumnName(i));
            }

            // Adicionar linhas
            while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                tableModel.addRow(rowData);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao consultar view: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
