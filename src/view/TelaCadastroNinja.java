package view;

import model.Ninja;
import service.NinjaService;
import util.JTableUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TelaCadastroNinja extends JFrame {

    private JTextField txtId, txtNome, txtVila, txtCla, txtNatureza;
    private JComboBox<String> cbRank, cbStatus;
    private JTable tabela;
    private DefaultTableModel tableModel;
    private NinjaService ninjaService;

    public TelaCadastroNinja() {
        ninjaService = new NinjaService();

        setTitle("Cadastro de Ninjas");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelForm = new JPanel(new GridLayout(7, 2, 5, 5));
        painelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        painelForm.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        painelForm.add(txtId);

        painelForm.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painelForm.add(txtNome);

        painelForm.add(new JLabel("Vila:"));
        txtVila = new JTextField();
        painelForm.add(txtVila);

        painelForm.add(new JLabel("Clã:"));
        txtCla = new JTextField();
        painelForm.add(txtCla);

        painelForm.add(new JLabel("Rank:"));
        cbRank = new JComboBox<>(new String[]{"Genin", "Chunin", "Jounin", "Kage"});
        painelForm.add(cbRank);

        painelForm.add(new JLabel("Natureza do Chakra:"));
        txtNatureza = new JTextField();
        painelForm.add(txtNatureza);

        painelForm.add(new JLabel("Status:"));
        cbStatus = new JComboBox<>(new String[]{"Ativo", "Inativo"});
        painelForm.add(cbStatus);

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

        tableModel = new DefaultTableModel(new String[]{"ID", "Nome", "Vila", "Clã", "Rank", "Natureza", "Status"}, 0) {
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
                    txtNome.setText(tableModel.getValueAt(linha, 1).toString());
                    txtVila.setText(tableModel.getValueAt(linha, 2).toString());
                    txtCla.setText(tableModel.getValueAt(linha, 3) != null ? tableModel.getValueAt(linha, 3).toString() : "");
                    cbRank.setSelectedItem(tableModel.getValueAt(linha, 4).toString());
                    txtNatureza.setText(tableModel.getValueAt(linha, 5) != null ? tableModel.getValueAt(linha, 5).toString() : "");
                    cbStatus.setSelectedItem(tableModel.getValueAt(linha, 6).toString());
                }
            }
        });

        carregarTabela();
    }

    private void inserir() {
        try {
            Ninja n = new Ninja();
            n.setNome(txtNome.getText());
            n.setVila(txtVila.getText());
            n.setCla(txtCla.getText());
            n.setRankNinja(cbRank.getSelectedItem().toString());
            n.setNaturezaChakra(txtNatureza.getText());
            n.setStatus(cbStatus.getSelectedItem().toString());

            ninjaService.inserir(n);
            JOptionPane.showMessageDialog(this, "Ninja cadastrado com sucesso!");
            limparCampos();
            carregarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizar() {
        try {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um ninja para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Ninja n = new Ninja();
            n.setId(Integer.parseInt(txtId.getText()));
            n.setNome(txtNome.getText());
            n.setVila(txtVila.getText());
            n.setCla(txtCla.getText());
            n.setRankNinja(cbRank.getSelectedItem().toString());
            n.setNaturezaChakra(txtNatureza.getText());
            n.setStatus(cbStatus.getSelectedItem().toString());

            ninjaService.atualizar(n);
            JOptionPane.showMessageDialog(this, "Ninja atualizado com sucesso!");
            limparCampos();
            carregarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluir() {
        try {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um ninja para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                ninjaService.excluir(Integer.parseInt(txtId.getText()));
                JOptionPane.showMessageDialog(this, "Ninja excluído com sucesso!");
                limparCampos();
                carregarTabela();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtVila.setText("");
        txtCla.setText("");
        cbRank.setSelectedIndex(0);
        txtNatureza.setText("");
        cbStatus.setSelectedIndex(0);
    }

    private void carregarTabela() {
        JTableUtils.clearTable(tableModel);
        try {
            List<Ninja> ninjas = ninjaService.listarTodos();
            for (Ninja n : ninjas) {
                tableModel.addRow(new Object[]{
                        n.getId(), n.getNome(), n.getVila(), n.getCla(), n.getRankNinja(), n.getNaturezaChakra(), n.getStatus()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tabela: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
