package br.com.fiap.esg.service;

import br.com.fiap.esg.model.Sensor;
import br.com.fiap.esg.repository.SensorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Transactional(readOnly = true)
    public List<Sensor> listarTodos() {
        return sensorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Sensor> listarPorStatus(Sensor.SensorStatus status) {
        return sensorRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Sensor> listarPorTipo(String tipo) {
        return sensorRepository.findByTipo(tipo);
    }

    @Transactional(readOnly = true)
    public List<Sensor> buscarPorLocalizacao(String localizacao) {
        return sensorRepository.findByLocalizacaoContainingIgnoreCase(localizacao);
    }

    @Transactional
    public Sensor cadastrar(Sensor sensor) {
        sensor.setDataInstalacao(LocalDateTime.now());
        sensor.setStatus(Sensor.SensorStatus.ATIVO);
        return sensorRepository.save(sensor);
    }

    @Transactional
    public Sensor atualizar(Long id, Sensor sensor) {
        return sensorRepository.findById(id)
                .map(sensorExistente -> {
                    sensorExistente.setNome(sensor.getNome());
                    sensorExistente.setLocalizacao(sensor.getLocalizacao());
                    sensorExistente.setTipo(sensor.getTipo());
                    sensorExistente.setStatus(sensor.getStatus());
                    if (sensor.getUltimaManutencao() != null) {
                        sensorExistente.setUltimaManutencao(sensor.getUltimaManutencao());
                    }
                    return sensorRepository.save(sensorExistente);
                })
                .orElseThrow(() -> new RuntimeException("Sensor não encontrado"));
    }

    @Transactional
    public void excluir(Long id) {
        sensorRepository.deleteById(id);
    }

    @Transactional
    public Sensor registrarManutencao(Long id) {
        return sensorRepository.findById(id)
                .map(sensor -> {
                    sensor.setStatus(Sensor.SensorStatus.MANUTENCAO);
                    sensor.setUltimaManutencao(LocalDateTime.now());
                    return sensorRepository.save(sensor);
                })
                .orElseThrow(() -> new RuntimeException("Sensor não encontrado"));
    }

    @Transactional
    public Sensor finalizarManutencao(Long id) {
        return sensorRepository.findById(id)
                .map(sensor -> {
                    sensor.setStatus(Sensor.SensorStatus.ATIVO);
                    return sensorRepository.save(sensor);
                })
                .orElseThrow(() -> new RuntimeException("Sensor não encontrado"));
    }
}