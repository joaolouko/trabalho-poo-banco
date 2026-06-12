USE sistema_ninja;

CREATE OR REPLACE VIEW vw_ninja_missoes AS
SELECT 
    n.nome AS nome_ninja,
    n.vila,
    n.cla,
    n.rank_ninja,
    m.titulo AS titulo_missao,
    m.rank_missao,
    nm.funcao,
    m.status AS status_missao
FROM 
    tb_ninja_missao nm
JOIN tb_ninja n ON nm.id_ninja = n.id
JOIN tb_missao m ON nm.id_missao = m.id;

CREATE OR REPLACE VIEW vw_total_ninjas_por_vila AS
SELECT 
    vila,
    COUNT(*) AS quantidade_ninjas
FROM 
    tb_ninja
GROUP BY 
    vila;

CREATE OR REPLACE VIEW vw_total_missoes_por_rank AS
SELECT 
    rank_missao,
    COUNT(*) AS quantidade_missoes
FROM 
    tb_missao
GROUP BY 
    rank_missao;

CREATE OR REPLACE VIEW vw_missoes_sem_ninjas AS
SELECT 
    m.id AS id_missao,
    m.titulo,
    m.rank_missao,
    m.vila_origem,
    m.status
FROM 
    tb_missao m
LEFT JOIN tb_ninja_missao nm ON m.id = nm.id_missao
WHERE 
    nm.id IS NULL;
