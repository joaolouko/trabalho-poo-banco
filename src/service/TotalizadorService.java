package service;

import connection.Conexao;
import dao.TotalizadorDAO;
import model.TotalizadorNinja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TotalizadorService {

    private TotalizadorDAO totalizadorDAO;

    public TotalizadorService() {
        this.totalizadorDAO = new TotalizadorDAO();
    }

    public void gerarTotalizadores() {
        // Limpar dados anteriores
        totalizadorDAO.limparTotalizadores();

        try (Connection conn = Conexao.getConnection()) {
            
            // Quantidade de ninjas por vila
            executarEGravar(conn, "SELECT vila, COUNT(*) as qtd FROM tb_ninja GROUP BY vila", "Ninjas por vila - ");
            
            // Quantidade de ninjas por rank
            executarEGravar(conn, "SELECT rank_ninja, COUNT(*) as qtd FROM tb_ninja GROUP BY rank_ninja", "Ninjas por rank - ");
            
            // Quantidade de ninjas por natureza de chakra
            executarEGravar(conn, "SELECT natureza_chakra, COUNT(*) as qtd FROM tb_ninja GROUP BY natureza_chakra", "Ninjas por natureza - ");
            
            // Quantidade de missões por rank
            executarEGravar(conn, "SELECT rank_missao, COUNT(*) as qtd FROM tb_missao GROUP BY rank_missao", "Missões por rank - ");
            
            // Quantidade de missões por status
            executarEGravar(conn, "SELECT status, COUNT(*) as qtd FROM tb_missao GROUP BY status", "Missões por status - ");
            
            // Quantidade de ninjas vinculados em missões
            String sqlNinjasVinculados = "SELECT 'Total', COUNT(DISTINCT id_ninja) as qtd FROM tb_ninja_missao";
            executarEGravar(conn, sqlNinjasVinculados, "Ninjas vinculados em missões - ");
            
            // Quantidade de missões sem ninja
            String sqlMissoesSemNinja = "SELECT 'Total', COUNT(m.id) as qtd FROM tb_missao m LEFT JOIN tb_ninja_missao nm ON m.id = nm.id_missao WHERE nm.id IS NULL";
            executarEGravar(conn, sqlMissoesSemNinja, "Missões sem ninja - ");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerar totalizadores: " + e.getMessage(), e);
        }
    }

    private void executarEGravar(Connection conn, String sql, String prefixoDescricao) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String desc = rs.getString(1);
                int qtd = rs.getInt("qtd");
                
                TotalizadorNinja tn = new TotalizadorNinja();
                tn.setDescricao(prefixoDescricao + desc);
                tn.setQuantidade(qtd);
                
                totalizadorDAO.inserir(tn);
            }
        }
    }

    public List<TotalizadorNinja> listarTodos() {
        return totalizadorDAO.listarTodos();
    }
}
