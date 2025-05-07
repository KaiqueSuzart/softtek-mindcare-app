package br.com.fiap.esg.repository;

import br.com.fiap.esg.model.Consumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsumoRepository extends JpaRepository<Consumo, Long> {
    List<Consumo> findBySensorId(Long sensorId);
    List<Consumo> findByDataMedicaoBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Consumo> findBySensorIdAndDataMedicaoBetween(Long sensorId, LocalDateTime inicio, LocalDateTime fim);
}