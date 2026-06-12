USE sistema_ninja;

INSERT INTO tb_ninja (nome, vila, cla, rank_ninja, natureza_chakra, status) VALUES 
('Naruto Uzumaki', 'Folha', 'Uzumaki', 'Genin', 'Vento', 'Ativo'),
('Sasuke Uchiha', 'Folha', 'Uchiha', 'Genin', 'Fogo, Raio', 'Inativo'),
('Kakashi Hatake', 'Folha', 'Hatake', 'Jounin', 'Raio, Terra, Água, Fogo', 'Ativo'),
('Gaara', 'Areia', 'Kazekage', 'Kage', 'Vento, Terra', 'Ativo'),
('Shikamaru Nara', 'Folha', 'Nara', 'Chunin', 'Sombra (Inton)', 'Ativo');

INSERT INTO tb_missao (titulo, descricao, rank_missao, vila_origem, status) VALUES 
('Resgatar Gato de Madame Shijimi', 'Encontrar e devolver o gato fugitivo Tora.', 'D', 'Folha', 'Concluida'),
('Escolta do Construtor de Pontes', 'Escoltar Tazuna com segurança até o País das Ondas.', 'C', 'Folha', 'Concluida'),
('Proteger o Pergaminho Sagrado', 'Impedir o roubo do pergaminho contendo jutsus proibidos.', 'B', 'Folha', 'Aberta'),
('Investigação da Akatsuki', 'Reunir informações sobre a organização criminosa.', 'S', 'Areia', 'Aberta'),
('Defesa da Fronteira', 'Repelir invasores na fronteira oeste.', 'A', 'Folha', 'Cancelada');

INSERT INTO tb_ninja_missao (id_ninja, id_missao, funcao, data_participacao) VALUES 
(1, 1, 'Ataque', '2023-01-10'),
(2, 1, 'Ataque', '2023-01-10'),
(3, 2, 'Líder', '2023-02-15'),
(1, 2, 'Ataque', '2023-02-15'),
(2, 2, 'Defesa', '2023-02-15');
