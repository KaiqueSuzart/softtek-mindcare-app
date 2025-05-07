package br.com.fiap.esg.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerta")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;

    @Column(name = "tipo_alerta", nullable = false, length = 50)
    private String tipoAlerta;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(name = "nivel_severidade", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private NivelSeveridade nivelSeveridade;

    @Column(name = "data_alerta", nullable = false)
    private LocalDateTime dataAlerta = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean resolvido = false;

    @Column(name = "data_resolucao")
    private LocalDateTime dataResolucao;

    public enum NivelSeveridade {
        BAIXO, MEDIO, ALTO, CRITICO
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
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