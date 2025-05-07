package br.com.fiap.esg.repository;

import br.com.fiap.esg.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    List<Sensor> findByStatus(Sensor.SensorStatus status);
    List<Sensor> findByTipo(String tipo);
    List<Sensor> findByLocalizacaoContainingIgnoreCase(String localizacao);
}