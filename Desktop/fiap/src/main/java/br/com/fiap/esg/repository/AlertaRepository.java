package br.com.fiap.esg.repository;

import br.com.fiap.esg.model.Alerta;
import br.com.fiap.esg.model.Alerta.NivelSeveridade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    List<Alerta> findBySensorId(Long sensorId);
    List<Alerta> findByResolvido(Boolean resolvido);
    List<Alerta> findByNivelSeveridade(NivelSeveridade nivelSeveridade);
    List<Alerta> findByDataAlertaBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Alerta> findBySensorIdAndResolvidoAndNivelSeveridade(Long sensorId, Boolean resolvido, NivelSeveridade nivelSeveridade);
}