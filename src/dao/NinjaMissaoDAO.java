package dao;

import connection.Conexao;
import model.NinjaMissao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NinjaMissaoDAO {

    public void inserir(NinjaMissao ninjaMissao) {
        String sql = "INSERT INTO tb_ninja_missao (id_ninja, id_missao, funcao, data_participacao) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ninjaMissao.getIdNinja());
            stmt.setInt(2, ninjaMissao.getIdMissao());
            stmt.setString(3, ninjaMissao.getFuncao());
            stmt.setDate(4, Date.valueOf(ninjaMissao.getDataParticipacao()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir vínculo ninja-missão: " + e.getMessage(), e);
        }
    }

    public List<NinjaMissao> listarTodos() {
        String sql = "SELECT * FROM tb_ninja_missao";
        List<NinjaMissao> vinculacoes = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                NinjaMissao nm = new NinjaMissao();
                nm.setId(rs.getInt("id"));
                nm.setIdNinja(rs.getInt("id_ninja"));
                nm.setIdMissao(rs.getInt("id_missao"));
                nm.setFuncao(rs.getString("funcao"));
                nm.setDataParticipacao(rs.getDate("data_participacao").toLocalDate());
                vinculacoes.add(nm);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar vinculações: " + e.getMessage(), e);
        }
        return vinculacoes;
    }
}
