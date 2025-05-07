package br.com.fiap.esg.dto;

import br.com.fiap.esg.model.Alerta.NivelSeveridade;
import java.time.LocalDateTime;

public class AlertaDTO {
    private Long id;
    private Long sensorId;
    private String tipoAlerta;
    private String descricao;
    private NivelSeveridade nivelSeveridade;
    private LocalDateTime dataAlerta;
    private Boolean resolvido;
    private LocalDateTime dataResolucao;

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

    public String getTipoAlerta() {
        return tipoAlerta;
    }

    public void setTipoAlerta(String tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public NivelSeveridade getNivelSeveridade() {
        return nivelSeveridade;
    }

    public void setNivelSeveridade(NivelSeveridade nivelSeveridade) {
        this.nivelSeveridade = nivelSeveridade;
    }

    public LocalDateTime getDataAlerta() {
        return dataAlerta;
    }

    public void setDataAlerta(LocalDateTime dataAlerta) {
        this.dataAlerta = dataAlerta;
    }

    public Boolean getResolvido() {
        return resolvido;
    }

    public void setResolvido(Boolean resolvido) {
        this.resolvido = resolvido;
    }

    public LocalDateTime getDataResolucao() {
        return dataResolucao;
    }

    public void setDataResolucao(LocalDateTime dataResolucao) {
        this.dataResolucao = dataResolucao;
    }
}