package model;

import java.time.LocalDate;

public class NinjaMissao {
    private Integer id;
    private Integer idNinja;
    private Integer idMissao;
    private String funcao;
    private LocalDate dataParticipacao;

    public NinjaMissao() {}

    public NinjaMissao(Integer id, Integer idNinja, Integer idMissao, String funcao, LocalDate dataParticipacao) {
        this.id = id;
        this.idNinja = idNinja;
        this.idMissao = idMissao;
        this.funcao = funcao;
        this.dataParticipacao = dataParticipacao;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdNinja() { return idNinja; }
    public void setIdNinja(Integer idNinja) { this.idNinja = idNinja; }

    public Integer getIdMissao() { return idMissao; }
    public void setIdMissao(Integer idMissao) { this.idMissao = idMissao; }

    public String getFuncao() { return funcao; }
    public void setFuncao(String funcao) { this.funcao = funcao; }

    public LocalDate getDataParticipacao() { return dataParticipacao; }
    public void setDataParticipacao(LocalDate dataParticipacao) { this.dataParticipacao = dataParticipacao; }
}
