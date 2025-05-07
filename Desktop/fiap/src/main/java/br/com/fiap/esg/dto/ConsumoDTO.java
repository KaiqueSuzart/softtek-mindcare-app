package br.com.fiap.esg.dto;

import java.time.LocalDateTime;

public class ConsumoDTO {
    private Long id;
    private Long sensorId;
    private LocalDateTime dataMedicao;
    private Double consumoKwh;
    private Double picoDemanda;
    private Double temperatura;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public LocalDateTime getDataMedicao() {
        return dataMedicao;
    }

    public void setDataMedicao(LocalDateTime dataMedicao) {
        this.dataMedicao = dataMedicao;
    }

    public Double getConsumoKwh() {
        return consumoKwh;
    }

    public void setConsumoKwh(Double consumoKwh) {
        this.consumoKwh = consumoKwh;
    }

    public Double getPicoDemanda() {
        return picoDemanda;
    }

    public void setPicoDemanda(Double picoDemanda) {
        this.picoDemanda = picoDemanda;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }
}