package br.com.fiap.esg.service;

import br.com.fiap.esg.model.Alerta;
import br.com.fiap.esg.model.Sensor;
import br.com.fiap.esg.repository.AlertaRepository;
import br.com.fiap.esg.repository.SensorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertaService {

    private final AlertaRepository alertaRepository;
    private final SensorRepository sensorRepository;

    public AlertaService(AlertaRepository alertaRepository, SensorRepository sensorRepository) {
        this.alertaRepository = alertaRepository;
        this.sensorRepository = sensorRepository;
    }

    @Transactional(readOnly = true)
    public List<Alerta> listarTodos() {
        return alertaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Alerta> listarPorSensor(Long sensorId) {
        return alertaRepository.findBySensorId(sensorId);
    }

    @Transactional(readOnly = true)
    public List<Alerta> listarPorStatus(Boolean resolvido) {
        return alertaRepository.findByResolvido(resolvido);
    }

    @Transactional(readOnly = true)
    public List<Alerta> listarPorSeveridade(Alerta.NivelSeveridade nivelSeveridade) {
        return alertaRepository.findByNivelSeveridade(nivelSeveridade);
    }

    @Transactional(readOnly = true)
    public List<Alerta> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return alertaRepository.findByDataAlertaBetween(inicio, fim);
    }

    @Transactional
    public Alerta criarAlerta(Long sensorId, Alerta alerta) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new RuntimeException("Sensor não encontrado"));

        alerta.setSensor(sensor);
        alerta.setDataAlerta(LocalDateTime.now());
        alerta.setResolvido(false);
        return alertaRepository.save(alerta);
    }

    @Transactional
    public Alerta resolverAlerta(Long id) {
        return alertaRepository.findById(id)
                .map(alerta -> {
                    alerta.setResolvido(true);
                    alerta.setDataResolucao(LocalDateTime.now());
                    return alertaRepository.save(alerta);
                })
                .orElseThrow(() -> new RuntimeException("Alerta não encontrado"));
    }

    @Transactional
    public void excluirAlerta(Long id) {
        alertaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Alerta> buscarAlertasCriticos(Long sensorId) {
        return alertaRepository.findBySensorIdAndResolvidoAndNivelSeveridade(
                sensorId, false, Alerta.NivelSeveridade.CRITICO);
    }
}