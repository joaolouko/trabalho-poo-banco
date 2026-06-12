package service;

import dao.NinjaDAO;
import model.Ninja;

import java.util.List;

public class NinjaService {

    private NinjaDAO ninjaDAO;

    public NinjaService() {
        this.ninjaDAO = new NinjaDAO();
    }

    public void inserir(Ninja ninja) {
        if (ninja.getNome() == null || ninja.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do ninja é obrigatório.");
        }
        ninjaDAO.inserir(ninja);
    }

    public void atualizar(Ninja ninja) {
        if (ninja.getId() == null) {
            throw new IllegalArgumentException("ID do ninja é obrigatório para atualização.");
        }
        ninjaDAO.atualizar(ninja);
    }

    public void excluir(int id) {
        ninjaDAO.excluir(id);
    }

    public List<Ninja> listarTodos() {
        return ninjaDAO.listarTodos();
    }

    public Ninja buscarPorId(int id) {
        return ninjaDAO.buscarPorId(id);
    }
}
