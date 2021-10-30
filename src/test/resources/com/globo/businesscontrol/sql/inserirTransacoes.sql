INSERT INTO TRANSACAO(ID, DATA_TRANSACAO, VALOR, DESCRICAO, ID_RESPONSAVEL, ID_CONTA, TIPO_TRANSACAO, CATEGORIA_DESPESA, DATA_CRIACAO, DATA_ATUALIZACAO, DATA_EXCLUSAO) VALUES
    (1, '2020-11-09 10:15:00', 100, 'Comprei algo', 1, 1, 'DESPESA', 'ALIMENTACAO', '2020-11-09 10:30:00', '2020-11-09 10:30:00', NULL);

INSERT INTO TRANSACAO(ID, DATA_TRANSACAO, VALOR, DESCRICAO, ID_RESPONSAVEL, ID_CONTA, TIPO_TRANSACAO, CATEGORIA_RECEITA, DATA_CRIACAO, DATA_ATUALIZACAO, DATA_EXCLUSAO) VALUES
(2, '2020-11-09 10:15:00', 100, 'Salario', 1, 1, 'RECEITA', 'SALARIO', '2020-11-09 10:30:00', '2020-11-09 10:30:00', NULL);

INSERT INTO TRANSACAO(ID, DATA_TRANSACAO, VALOR, DESCRICAO, ID_RESPONSAVEL, ID_CONTA, TIPO_TRANSACAO, ID_CONTA_DESTINO, DATA_CRIACAO, DATA_ATUALIZACAO, DATA_EXCLUSAO) VALUES
(3, '2020-11-09 10:15:00', 100, 'Poupanca', 1, 1, 'TRANSFERENCIA', 2, '2020-11-09 10:30:00', '2020-11-09 10:30:00', NULL);