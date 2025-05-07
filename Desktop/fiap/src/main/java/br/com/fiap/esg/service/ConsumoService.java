package br.com.fiap.esg.service;

import br.com.fiap.esg.model.Consumo;
import br.com.fiap.esg.model.Sensor;
import br.com.fiap.esg.repository.ConsumoRepository;
import br.com.fiap.esg.repository.SensorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsumoService {

    private final ConsumoRepository consumoRepository;
    private final SensorRepository sensorRepository;

    public ConsumoService(ConsumoRepository consumoRepository, SensorRepository sensorRepository) {
        this.consumoRepository = consumoRepository;
        this.sensorRepository = sensorRepository;
    }

    @Transactional(readOnly = true)
    public List<Consumo> listarTodos() {
        return consumoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Consumo> listarPorSensor(Long sensorId) {
        return consumoRepository.findBySensorId(sensorId);
    }

    @Transactional(readOnly = true)
    public List<Consumo> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return consumoRepository.findByDataMedicaoBetween(inicio, fim);
    }

    @Transactional(readOnly = true)
    public List<Consumo> buscarPorSensorEPeriodo(Long sensorId, LocalDateTime inicio, LocalDateTime fim) {
        return consumoRepository.findBySensorIdAndDataMedicaoBetween(sensorId, inicio, fim);
    }

    @Transactional
    public Consumo registrarMedicao(Long sensorId, Consumo consumo) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new RuntimeException("Sensor não encontrado"));

        if (sensor.getStatus() != Sensor.SensorStatus.ATIVO) {
            throw new RuntimeException("Sensor não está ativo para registrar medições");
        }

        consumo.setSensor(sensor);
        consumo.setDataMedicao(LocalDateTime.now());
        return consumoRepository.save(consumo);
    }

    @Transactional
    public void excluirMedicao(Long id) {
        consumoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Double calcularConsumoTotal(Long sensorId, LocalDateTime inicio, LocalDateTime fim) {
        List<Consumo> medicoes = buscarPorSensorEPeriodo(sensorId, inicio, fim);
        return medicoes.stream()
                .mapToDouble(Consumo::getConsumoKwh)
                .sum();
    }

    @Transactional(readOnly = true)
    public Double calcularPicoDemanda(Long sensorId, LocalDateTime inicio, LocalDateTime fim) {
        List<Consumo> medicoes = buscarPorSensorEPeriodo(sensorId, inicio, fim);
        return medicoes.stream()
                .mapToDouble(Consumo::getPicoDemanda)
                .max()
                .orElse(0.0);
    }
}