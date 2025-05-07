package br.com.fiap.esg.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sensor")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 200)
    private String localizacao;

    @Column(nullable = false, length = 50)
    private String tipo;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private SensorStatus status = SensorStatus.ATIVO;

    @Column(name = "data_instalacao", nullable = false)
    private LocalDateTime dataInstalacao = LocalDateTime.now();

    @Column(name = "ultima_manutencao")
    private LocalDateTime ultimaManutencao;

    public enum SensorStatus {
        ATIVO, INATIVO, MANUTENCAO
    }

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