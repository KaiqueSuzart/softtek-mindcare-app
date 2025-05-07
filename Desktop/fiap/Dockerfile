# Stage 1: Build with Maven, leveraging layer cache for dependencies
FROM maven:3.9.4-eclipse-temurin-17 AS builder

WORKDIR /app

# 1) Copy only pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 2) Copy source and package
COPY src ./src
RUN mvn clean package -DskipTests -B

# Stage 2: Runtime on a lean JRE with hardened non-root user
FROM eclipse-temurin:17-jre

LABEL maintainer="seu-email@exemplo.com"

# 1) Create dedicated group and user with no login shell
RUN addgroup --system appgroup \
 && adduser --system --ingroup appgroup --no-create-home --home /app --shell /usr/sbin/nologin appuser

WORKDIR /app

# 2) Copy the fat JAR and set ownership
COPY --from=builder /app/target/*.jar app.jar
RUN chown appuser:appgroup app.jar

# 3) Expose application port
EXPOSE 8080

# 4) Switch to non-root user
USER appuser

# 5) JVM tuning: G1GC, RAM limits and heap dumps on OOM
ENV JAVA_TOOL_OPTIONS="-XX:+UseG1GC \
  -XX:MaxRAMPercentage=75.0 \
  -XX:+HeapDumpOnOutOfMemoryError \
  -XX:HeapDumpPath=/app/heapdump.hprof"

# 6) Configure healthcheck
HEALTHCHECK --interval=30s --timeout=5s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# 7) Launch the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]