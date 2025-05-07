package br.com.fiap.esg.controller;

import br.com.fiap.esg.dto.SensorDTO;
import br.com.fiap.esg.model.Sensor;
import br.com.fiap.esg.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sensores")
@Tag(name = "Sensores", description = "API para gerenciamento de sensores de energia")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os sensores cadastrados")
    public ResponseEntity<List<SensorDTO>> listarTodos() {
        List<SensorDTO> sensores = sensorService.listarTodos().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sensores);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Lista sensores por status")
    public ResponseEntity<List<SensorDTO>> listarPorStatus(@PathVariable Sensor.SensorStatus status) {
        List<SensorDTO> sensores = sensorService.listarPorStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sensores);
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Lista sensores por tipo")
    public ResponseEntity<List<SensorDTO>> listarPorTipo(@PathVariable String tipo) {
        List<SensorDTO> sensores = sensorService.listarPorTipo(tipo).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sensores);
    }

    @GetMapping("/localizacao/{localizacao}")
    @Operation(summary = "Busca sensores por localização")
    public ResponseEntity<List<SensorDTO>> buscarPorLocalizacao(@PathVariable String localizacao) {
        List<SensorDTO> sensores = sensorService.buscarPorLocalizacao(localizacao).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sensores);
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo sensor")
    public ResponseEntity<SensorDTO> cadastrar(@RequestBody SensorDTO sensorDTO) {
        Sensor sensor = convertToEntity(sensorDTO);
        Sensor sensorSalvo = sensorService.cadastrar(sensor);
        return ResponseEntity.ok(convertToDTO(sensorSalvo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um sensor existente")
    public ResponseEntity<SensorDTO> atualizar(@PathVariable Long id, @RequestBody SensorDTO sensorDTO) {
        Sensor sensor = convertToEntity(sensorDTO);
        Sensor sensorAtualizado = sensorService.atualizar(id, sensor);
        return ResponseEntity.ok(convertToDTO(sensorAtualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um sensor")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        sensorService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/manutencao")
    @Operation(summary = "Registra manutenção em um sensor")
    public ResponseEntity<SensorDTO> registrarManutencao(@PathVariable Long id) {
        Sensor sensor = sensorService.registrarManutencao(id);
        return ResponseEntity.ok(convertToDTO(sensor));
    }

    @PostMapping("/{id}/finalizar-manutencao")
    @Operation(summary = "Finaliza manutenção de um sensor")
    public ResponseEntity<SensorDTO> finalizarManutencao(@PathVariable Long id) {
        Sensor sensor = sensorService.finalizarManutencao(id);
        return ResponseEntity.ok(convertToDTO(sensor));
    }

    private SensorDTO convertToDTO(Sensor sensor) {
        SensorDTO dto = new SensorDTO();
        dto.setId(sensor.getId());
        dto.setNome(sensor.getNome());
        dto.setLocalizacao(sensor.getLocalizacao());
        dto.setTipo(sensor.getTipo());
        dto.setStatus(sensor.getStatus());
        dto.setDataInstalacao(sensor.getDataInstalacao());
        dto.setUltimaManutencao(sensor.getUltimaManutencao());
        return dto;
    }

    private Sensor convertToEntity(SensorDTO dto) {
        Sensor sensor = new Sensor();
        sensor.setId(dto.getId());
        sensor.setNome(dto.getNome());
        sensor.setLocalizacao(dto.getLocalizacao());
        sensor.setTipo(dto.getTipo());
        sensor.setStatus(dto.getStatus());
        sensor.setDataInstalacao(dto.getDataInstalacao());
        sensor.setUltimaManutencao(dto.getUltimaManutencao());
        return sensor;
    }
}