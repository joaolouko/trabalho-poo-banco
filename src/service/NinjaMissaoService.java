package service;

import dao.MissaoDAO;
import dao.NinjaDAO;
import dao.NinjaMissaoDAO;
import model.Missao;
import model.Ninja;
import model.NinjaMissao;

import java.util.List;

public class NinjaMissaoService {

    private NinjaMissaoDAO ninjaMissaoDAO;
    private NinjaDAO ninjaDAO;
    private MissaoDAO missaoDAO;

    public NinjaMissaoService() {
        this.ninjaMissaoDAO = new NinjaMissaoDAO();
        this.ninjaDAO = new NinjaDAO();
        this.missaoDAO = new MissaoDAO();
    }

    public void vincularNinjaMissao(NinjaMissao ninjaMissao) {
        Ninja ninja = ninjaDAO.buscarPorId(ninjaMissao.getIdNinja());
        Missao missao = missaoDAO.buscarPorId(ninjaMissao.getIdMissao());

        if (ninja == null) {
            throw new IllegalArgumentException("Ninja não encontrado.");
        }
        if (missao == null) {
            throw new IllegalArgumentException("Missão não encontrada.");
        }

        validarRank(ninja.getRankNinja(), missao.getRankMissao());

        ninjaMissaoDAO.inserir(ninjaMissao);
    }

    private void validarRank(String rankNinja, String rankMissao) {
        boolean valido = false;
        switch (rankNinja) {
            case "Genin":
                if (rankMissao.equals("D") || rankMissao.equals("C")) valido = true;
                break;
            case "Chunin":
                if (rankMissao.equals("D") || rankMissao.equals("C") || rankMissao.equals("B")) valido = true;
                break;
            case "Jounin":
                if (rankMissao.equals("D") || rankMissao.equals("C") || rankMissao.equals("B") || rankMissao.equals("A") || rankMissao.equals("S")) valido = true;
                break;
            case "Kage":
                if (rankMissao.equals("A") || rankMissao.equals("S")) valido = true;
                break;
        }

        if (!valido) {
            throw new IllegalArgumentException("Rank do Ninja (" + rankNinja + ") insuficiente ou incompatível para a Missão (Rank " + rankMissao + ").");
        }
    }

    public List<NinjaMissao> listarTodos() {
        return ninjaMissaoDAO.listarTodos();
    }
}
