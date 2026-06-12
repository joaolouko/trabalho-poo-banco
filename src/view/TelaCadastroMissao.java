package view;

import model.Missao;
import service.MissaoService;
import util.JTableUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TelaCadastroMissao extends JFrame {

    private JTextField txtId, txtTitulo, txtVilaOrigem;
    private JTextArea txtDescricao;
    private JComboBox<String> cbRank, cbStatus;
    private JTable tabela;
    private DefaultTableModel tableModel;
    private MissaoService missaoService;

    public TelaCadastroMissao() {
        missaoService = new MissaoService();

        setTitle("Cadastro de Missões");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelForm = new JPanel(new BorderLayout(5, 5));
        painelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelCampos = new JPanel(new GridLayout(5, 2, 5, 5));
        
        painelCampos.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        painelCampos.add(txtId);

        painelCampos.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        painelCampos.add(txtTitulo);

        painelCampos.add(new JLabel("Rank:"));
        cbRank = new JComboBox<>(new String[]{"D", "C", "B", "A", "S"});
        painelCampos.add(cbRank);

        painelCampos.add(new JLabel("Vila de Origem:"));
        txtVilaOrigem = new JTextField();
        painelCampos.add(txtVilaOrigem);

        painelCampos.add(new JLabel("Status:"));
        cbStatus = new JComboBox<>(new String[]{"Aberta", "Concluida", "Cancelada"});
        painelCampos.add(cbStatus);

        painelForm.add(painelCampos, BorderLayout.NORTH);

        JPanel painelDescricao = new JPanel(new BorderLayout());
        painelDescricao.add(new JLabel("Descrição:"), BorderLayout.NORTH);
        txtDescricao = new JTextArea(3, 20);
        painelDescricao.add(new JScrollPane(txtDescricao), BorderLayout.CENTER);

        painelForm.add(painelDescricao, BorderLayout.CENTER);
        add(painelForm, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel();
        JButton btnInserir = new JButton("Cadastrar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnLimpar = new JButton("Limpar");

        painelBotoes.add(btnInserir);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);
        add(painelBotoes, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Título", "Descrição", "Rank", "Vila", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnInserir.addActionListener(e -> inserir());
        btnAtualizar.addActionListener(e -> atualizar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limparCampos());

        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tabela.getSelectedRow();
                if (linha >= 0) {
                    txtId.setText(tableModel.getValueAt(linha, 0).toString());
                    txtTitulo.setText(tableModel.getValueAt(linha, 1).toString());
                    txtDescricao.setText(tableModel.getValueAt(linha, 2) != null ? tableModel.getValueAt(linha, 2).toString() : "");
                    cbRank.setSelectedItem(tableModel.getValueAt(linha, 3).toString());
                    txtVilaOrigem.setText(tableModel.getValueAt(linha, 4).toString());
                    cbStatus.setSelectedItem(tableModel.getValueAt(linha, 5).toString());
                }
            }
        });

        carregarTabela();
    }

    private void inserir() {
        try {
            Missao m = new Missao();
            m.setTitulo(txtTitulo.getText());
            m.setDescricao(txtDescricao.getText());
            m.setRankMissao(cbRank.getSelectedItem().toString());
            m.setVilaOrigem(txtVilaOrigem.getText());
            m.setStatus(cbStatus.getSelectedItem().toString());

            missaoService.inserir(m);
            JOptionPane.showMessageDialog(this, "Missão cadastrada com sucesso!");
            limparCampos();
            carregarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizar() {
        try {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione uma missão para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Missao m = new Missao();
            m.setId(Integer.parseInt(txtId.getText()));
            m.setTitulo(txtTitulo.getText());
            m.setDescricao(txtDescricao.getText());
            m.setRankMissao(cbRank.getSelectedItem().toString());
            m.setVilaOrigem(txtVilaOrigem.getText());
            m.setStatus(cbStatus.getSelectedItem().toString());

            missaoService.atualizar(m);
            JOptionPane.showMessageDialog(this, "Missão atualizada com sucesso!");
            limparCampos();
            carregarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluir() {
        try {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione uma missão para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                missaoService.excluir(Integer.parseInt(txtId.getText()));
                JOptionPane.showMessageDialog(this, "Missão excluída com sucesso!");
                limparCampos();
                carregarTabela();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtTitulo.setText("");
        txtDescricao.setText("");
        cbRank.setSelectedIndex(0);
        txtVilaOrigem.setText("");
        cbStatus.setSelectedIndex(0);
    }

    private void carregarTabela() {
        JTableUtils.clearTable(tableModel);
        try {
            List<Missao> missoes = missaoService.listarTodas();
            for (Missao m : missoes) {
                tableModel.addRow(new Object[]{
                        m.getId(), m.getTitulo(), m.getDescricao(), m.getRankMissao(), m.getVilaOrigem(), m.getStatus()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tabela: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
