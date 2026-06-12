package dao;

import connection.Conexao;
import model.Ninja;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NinjaDAO {

    public void inserir(Ninja ninja) {
        String sql = "INSERT INTO tb_ninja (nome, vila, cla, rank_ninja, natureza_chakra, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ninja.getNome());
            stmt.setString(2, ninja.getVila());
            stmt.setString(3, ninja.getCla());
            stmt.setString(4, ninja.getRankNinja());
            stmt.setString(5, ninja.getNaturezaChakra());
            stmt.setString(6, ninja.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir ninja: " + e.getMessage(), e);
        }
    }

    public void atualizar(Ninja ninja) {
        String sql = "UPDATE tb_ninja SET nome = ?, vila = ?, cla = ?, rank_ninja = ?, natureza_chakra = ?, status = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ninja.getNome());
            stmt.setString(2, ninja.getVila());
            stmt.setString(3, ninja.getCla());
            stmt.setString(4, ninja.getRankNinja());
            stmt.setString(5, ninja.getNaturezaChakra());
            stmt.setString(6, ninja.getStatus());
            stmt.setInt(7, ninja.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar ninja: " + e.getMessage(), e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM tb_ninja WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir ninja: " + e.getMessage(), e);
        }
    }

    public List<Ninja> listarTodos() {
        String sql = "SELECT * FROM tb_ninja";
        List<Ninja> ninjas = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Ninja n = new Ninja();
                n.setId(rs.getInt("id"));
                n.setNome(rs.getString("nome"));
                n.setVila(rs.getString("vila"));
                n.setCla(rs.getString("cla"));
                n.setRankNinja(rs.getString("rank_ninja"));
                n.setNaturezaChakra(rs.getString("natureza_chakra"));
                n.setStatus(rs.getString("status"));
                ninjas.add(n);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar ninjas: " + e.getMessage(), e);
        }
        return ninjas;
    }

    public Ninja buscarPorId(int id) {
        String sql = "SELECT * FROM tb_ninja WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Ninja n = new Ninja();
                    n.setId(rs.getInt("id"));
                    n.setNome(rs.getString("nome"));
                    n.setVila(rs.getString("vila"));
                    n.setCla(rs.getString("cla"));
                    n.setRankNinja(rs.getString("rank_ninja"));
                    n.setNaturezaChakra(rs.getString("natureza_chakra"));
                    n.setStatus(rs.getString("status"));
                    return n;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ninja: " + e.getMessage(), e);
        }
        return null;
    }
}
