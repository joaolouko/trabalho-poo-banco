package model;

import java.time.LocalDateTime;

public class TotalizadorNinja {
    private Integer id;
    private String descricao;
    private Integer quantidade;
    private LocalDateTime dataGeracao;

    public TotalizadorNinja() {}

    public TotalizadorNinja(Integer id, String descricao, Integer quantidade, LocalDateTime dataGeracao) {
        this.id = id;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.dataGeracao = dataGeracao;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public LocalDateTime getDataGeracao() { return dataGeracao; }
    public void setDataGeracao(LocalDateTime dataGeracao) { this.dataGeracao = dataGeracao; }
}
