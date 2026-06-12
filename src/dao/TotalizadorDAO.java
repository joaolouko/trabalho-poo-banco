package dao;

import connection.Conexao;
import model.TotalizadorNinja;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TotalizadorDAO {

    public void limparTotalizadores() {
        String sql = "TRUNCATE TABLE tb_totalizador_ninja";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao limpar totalizadores: " + e.getMessage(), e);
        }
    }

    public void inserir(TotalizadorNinja totalizador) {
        String sql = "INSERT INTO tb_totalizador_ninja (descricao, quantidade) VALUES (?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, totalizador.getDescricao());
            stmt.setInt(2, totalizador.getQuantidade());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir totalizador: " + e.getMessage(), e);
        }
    }

    public List<TotalizadorNinja> listarTodos() {
        String sql = "SELECT * FROM tb_totalizador_ninja ORDER BY id DESC";
        List<TotalizadorNinja> totalizadores = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TotalizadorNinja tn = new TotalizadorNinja();
                tn.setId(rs.getInt("id"));
                tn.setDescricao(rs.getString("descricao"));
                tn.setQuantidade(rs.getInt("quantidade"));
                tn.setDataGeracao(rs.getTimestamp("data_geracao").toLocalDateTime());
                totalizadores.add(tn);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar totalizadores: " + e.getMessage(), e);
        }
        return totalizadores;
    }
}
