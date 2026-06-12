CREATE DATABASE IF NOT EXISTS sistema_ninja;
USE sistema_ninja;

CREATE TABLE IF NOT EXISTS tb_ninja (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    vila VARCHAR(100) NOT NULL,
    cla VARCHAR(100),
    rank_ninja ENUM('Genin', 'Chunin', 'Jounin', 'Kage') NOT NULL,
    natureza_chakra VARCHAR(100),
    status ENUM('Ativo', 'Inativo') NOT NULL DEFAULT 'Ativo'
);

CREATE TABLE IF NOT EXISTS tb_missao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT,
    rank_missao ENUM('D', 'C', 'B', 'A', 'S') NOT NULL,
    vila_origem VARCHAR(100) NOT NULL,
    status ENUM('Aberta', 'Concluida', 'Cancelada') NOT NULL DEFAULT 'Aberta'
);

CREATE TABLE IF NOT EXISTS tb_ninja_missao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_ninja INT NOT NULL,
    id_missao INT NOT NULL,
    funcao VARCHAR(50) NOT NULL,
    data_participacao DATE NOT NULL,
    CONSTRAINT fk_ninja FOREIGN KEY (id_ninja) REFERENCES tb_ninja(id) ON DELETE CASCADE,
    CONSTRAINT fk_missao FOREIGN KEY (id_missao) REFERENCES tb_missao(id) ON DELETE CASCADE,
    UNIQUE(id_ninja, id_missao)
);

CREATE TABLE IF NOT EXISTS tb_totalizador_ninja (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    quantidade INT NOT NULL,
    data_geracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
