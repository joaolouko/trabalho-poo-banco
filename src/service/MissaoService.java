package service;

import dao.MissaoDAO;
import model.Missao;

import java.util.List;

public class MissaoService {

    private MissaoDAO missaoDAO;

    public MissaoService() {
        this.missaoDAO = new MissaoDAO();
    }

    public void inserir(Missao missao) {
        if (missao.getTitulo() == null || missao.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("O título da missão é obrigatório.");
        }
        missaoDAO.inserir(missao);
    }

    public void atualizar(Missao missao) {
        if (missao.getId() == null) {
            throw new IllegalArgumentException("ID da missão é obrigatório para atualização.");
        }
        missaoDAO.atualizar(missao);
    }

    public void excluir(int id) {
        missaoDAO.excluir(id);
    }

    public List<Missao> listarTodas() {
        return missaoDAO.listarTodas();
    }

    public Missao buscarPorId(int id) {
        return missaoDAO.buscarPorId(id);
    }
}
