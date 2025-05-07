package br.com.fiap.esg.dto;

import br.com.fiap.esg.model.Sensor.SensorStatus;
import java.time.LocalDateTime;

public class SensorDTO {
    private Long id;
    private String nome;
    private String localizacao;
    private String tipo;
    private SensorStatus status;
    private LocalDateTime dataInstalacao;
    private LocalDateTime ultimaManutencao;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public SensorStatus getStatus() {
        return status;
    }

    public void setStatus(SensorStatus status) {
        this.status = status;
    }

    public LocalDateTime getDataInstalacao() {
        return dataInstalacao;
    }

    public void setDataInstalacao(LocalDateTime dataInstalacao) {
        this.dataInstalacao = dataInstalacao;
    }

    public LocalDateTime getUltimaManutencao() {
        return ultimaManutencao;
    }

    public void setUltimaManutencao(LocalDateTime ultimaManutencao) {
        this.ultimaManutencao = ultimaManutencao;
    }
}