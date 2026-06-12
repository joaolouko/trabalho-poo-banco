package dao;

import connection.Conexao;
import model.Missao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MissaoDAO {

    public void inserir(Missao missao) {
        String sql = "INSERT INTO tb_missao (titulo, descricao, rank_missao, vila_origem, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, missao.getTitulo());
            stmt.setString(2, missao.getDescricao());
            stmt.setString(3, missao.getRankMissao());
            stmt.setString(4, missao.getVilaOrigem());
            stmt.setString(5, missao.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir missão: " + e.getMessage(), e);
        }
    }

    public void atualizar(Missao missao) {
        String sql = "UPDATE tb_missao SET titulo = ?, descricao = ?, rank_missao = ?, vila_origem = ?, status = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, missao.getTitulo());
            stmt.setString(2, missao.getDescricao());
            stmt.setString(3, missao.getRankMissao());
            stmt.setString(4, missao.getVilaOrigem());
            stmt.setString(5, missao.getStatus());
            stmt.setInt(6, missao.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar missão: " + e.getMessage(), e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM tb_missao WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir missão: " + e.getMessage(), e);
        }
    }

    public List<Missao> listarTodas() {
        String sql = "SELECT * FROM tb_missao";
        List<Missao> missoes = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Missao m = new Missao();
                m.setId(rs.getInt("id"));
                m.setTitulo(rs.getString("titulo"));
                m.setDescricao(rs.getString("descricao"));
                m.setRankMissao(rs.getString("rank_missao"));
                m.setVilaOrigem(rs.getString("vila_origem"));
                m.setStatus(rs.getString("status"));
                missoes.add(m);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar missões: " + e.getMessage(), e);
        }
        return missoes;
    }

    public Missao buscarPorId(int id) {
        String sql = "SELECT * FROM tb_missao WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Missao m = new Missao();
                    m.setId(rs.getInt("id"));
                    m.setTitulo(rs.getString("titulo"));
                    m.setDescricao(rs.getString("descricao"));
                    m.setRankMissao(rs.getString("rank_missao"));
                    m.setVilaOrigem(rs.getString("vila_origem"));
                    m.setStatus(rs.getString("status"));
                    return m;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar missão: " + e.getMessage(), e);
        }
        return null;
    }
}
