# Sistema de Monitoramento de Eficiência Energética (ESG)

Este projeto é um sistema de monitoramento de eficiência energética desenvolvido com Spring Boot, utilizando Oracle como banco de dados. O sistema permite o gerenciamento de sensores, monitoramento de consumo energético e geração de alertas.

## Requisitos

- Java 17
- Docker
- Docker Compose

## Tecnologias Utilizadas

- Spring Boot 3.1.5
- Spring Security
- Spring Data JPA
- Oracle Database 19c
- Flyway para migrations
- SpringDoc OpenAPI (Swagger)
- Spring Actuator

## Setup do Projeto

### Configuração do Oracle

O projeto utiliza Oracle Database 19c através do Docker. As credenciais padrão são:

- Sistema:
  - Usuário: system
  - Senha: oracle

- Aplicação:
  - Usuário: esg_user
  - Senha: esg_password
  - SID: ORCLPDB1

### Build e Execução

1. Clone o repositório:
   ```bash
   git clone <repository-url>
   cd esg-energy
   ```

2. Build do projeto:
   ```bash
   ./mvnw clean package -DskipTests
   ```

3. Inicie os containers com Docker Compose:
   ```bash
   docker-compose up --build
   ```

   > Nota: Na primeira execução, o Oracle Database pode levar alguns minutos para inicializar completamente.

## Verificação da Aplicação

### Endpoints de Monitoramento

- Health Check: http://localhost:8080/actuator/health
- Métricas: http://localhost:8080/actuator/metrics
- Informações: http://localhost:8080/actuator/info

### Documentação da API

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI Docs: http://localhost:8080/api-docs

## Estrutura do Banco de Dados

### Tabelas Principais

1. **Sensor**
   - Armazena informações dos sensores de energia
   - Campos: id, nome, localização, tipo, status, data de instalação

2. **Consumo**
   - Registra medições de consumo energético
   - Campos: id, sensor_id, data_medição, consumo_kwh, pico_demanda

3. **Alerta**
   - Gerencia alertas de consumo anormal ou problemas
   - Campos: id, sensor_id, tipo_alerta, descrição, severidade

## Desenvolvimento

### Migrations

As migrations do banco são gerenciadas pelo Flyway e estão localizadas em:
```
src/main/resources/db/migration/
```

### Configurações

As configurações da aplicação podem ser ajustadas em:
```
src/main/resources/application.yml
```

## Suporte

Para questões e suporte, por favor abra uma issue no repositório do projeto.