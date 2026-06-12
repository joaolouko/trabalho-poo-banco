package view;

import model.Missao;
import model.Ninja;
import model.NinjaMissao;
import service.MissaoService;
import service.NinjaMissaoService;
import service.NinjaService;
import util.JTableUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class TelaVinculoNinjaMissao extends JFrame {

    private JComboBox<Ninja> cbNinja;
    private JComboBox<Missao> cbMissao;
    private JComboBox<String> cbFuncao;
    private JTable tabela;
    private DefaultTableModel tableModel;
    private NinjaMissaoService ninjaMissaoService;
    private NinjaService ninjaService;
    private MissaoService missaoService;

    public TelaVinculoNinjaMissao() {
        ninjaMissaoService = new NinjaMissaoService();
        ninjaService = new NinjaService();
        missaoService = new MissaoService();

        setTitle("Vínculo Ninja-Missão");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelForm = new JPanel(new GridLayout(4, 2, 5, 5));
        painelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        painelForm.add(new JLabel("Ninja:"));
        cbNinja = new JComboBox<>();
        painelForm.add(cbNinja);

        painelForm.add(new JLabel("Missão:"));
        cbMissao = new JComboBox<>();
        painelForm.add(cbMissao);

        painelForm.add(new JLabel("Função:"));
        cbFuncao = new JComboBox<>(new String[]{"Líder", "Ataque", "Suporte", "Sensorial", "Médico", "Defesa"});
        painelForm.add(cbFuncao);

        JButton btnVincular = new JButton("Vincular");
        painelForm.add(new JLabel("")); // espaço vazio
        painelForm.add(btnVincular);

        add(painelForm, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID Vínculo", "ID Ninja", "ID Missão", "Função", "Data"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnVincular.addActionListener(e -> vincular());

        carregarCombos();
        carregarTabela();
    }

    private void carregarCombos() {
        try {
            List<Ninja> ninjas = ninjaService.listarTodos();
            for (Ninja n : ninjas) {
                cbNinja.addItem(n);
            }

            List<Missao> missoes = missaoService.listarTodas();
            for (Missao m : missoes) {
                cbMissao.addItem(m);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void vincular() {
        try {
            Ninja ninjaSelecionado = (Ninja) cbNinja.getSelectedItem();
            Missao missaoSelecionada = (Missao) cbMissao.getSelectedItem();

            if (ninjaSelecionado == null || missaoSelecionada == null) {
                JOptionPane.showMessageDialog(this, "Selecione um Ninja e uma Missão.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            NinjaMissao nm = new NinjaMissao();
            nm.setIdNinja(ninjaSelecionado.getId());
            nm.setIdMissao(missaoSelecionada.getId());
            nm.setFuncao(cbFuncao.getSelectedItem().toString());
            nm.setDataParticipacao(LocalDate.now());

            ninjaMissaoService.vincularNinjaMissao(nm);
            
            JOptionPane.showMessageDialog(this, "Vínculo realizado com sucesso!");
            carregarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao vincular: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarTabela() {
        JTableUtils.clearTable(tableModel);
        try {
            List<NinjaMissao> vinculos = ninjaMissaoService.listarTodos();
            for (NinjaMissao nm : vinculos) {
                tableModel.addRow(new Object[]{
                        nm.getId(), nm.getIdNinja(), nm.getIdMissao(), nm.getFuncao(), nm.getDataParticipacao()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tabela: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
