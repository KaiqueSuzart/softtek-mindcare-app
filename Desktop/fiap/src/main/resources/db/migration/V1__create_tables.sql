-- Tabela de Sensores
CREATE TABLE sensor (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR2(100) NOT NULL,
    localizacao VARCHAR2(200) NOT NULL,
    tipo VARCHAR2(50) NOT NULL,
    status VARCHAR2(20) DEFAULT 'ATIVO' NOT NULL,
    data_instalacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    ultima_manutencao TIMESTAMP,
    CONSTRAINT chk_status CHECK (status IN ('ATIVO', 'INATIVO', 'MANUTENCAO'))
);

-- Tabela de Consumo Energético
CREATE TABLE consumo (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    sensor_id NUMBER NOT NULL,
    data_medicao TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    consumo_kwh NUMBER(10,2) NOT NULL,
    pico_demanda NUMBER(10,2),
    temperatura NUMBER(5,2),
    CONSTRAINT fk_consumo_sensor FOREIGN KEY (sensor_id) REFERENCES sensor(id)
);

-- Tabela de Alertas
CREATE TABLE alerta (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    sensor_id NUMBER NOT NULL,
    tipo_alerta VARCHAR2(50) NOT NULL,
    descricao VARCHAR2(500) NOT NULL,
    nivel_severidade VARCHAR2(20) NOT NULL,
    data_alerta TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    resolvido NUMBER(1) DEFAULT 0 NOT NULL,
    data_resolucao TIMESTAMP,
    CONSTRAINT fk_alerta_sensor FOREIGN KEY (sensor_id) REFERENCES sensor(id),
    CONSTRAINT chk_severidade CHECK (nivel_severidade IN ('BAIXO', 'MEDIO', 'ALTO', 'CRITICO')),
    CONSTRAINT chk_resolvido CHECK (resolvido IN (0, 1))
);

-- Índices para melhorar performance
CREATE INDEX idx_consumo_sensor_data ON consumo(sensor_id, data_medicao);
CREATE INDEX idx_alerta_sensor_data ON alerta(sensor_id, data_alerta);
CREATE INDEX idx_sensor_status ON sensor(status);