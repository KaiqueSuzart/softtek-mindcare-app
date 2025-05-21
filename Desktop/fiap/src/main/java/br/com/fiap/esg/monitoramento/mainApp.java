package com.suaempresa.monitoramento;

mport java.util.*;

public class MainApp {

    public static void main(String[] args) {
        System.out.println("🚀 Aplicação iniciando...");

        // Inicializar dependências
        DataRepository repository = new InMemoryCacheDataRepository(); // Com cache
        ApiService apiService = new ApiServiceImpl();
        SyncService syncService = new SyncService(apiService, repository);
        UseCases useCases = new UseCases(repository, syncService);

        // Simular fluxo
        useCases.syncIfOnline();
        useCases.getData();

        // Gerar documentação simulada
        System.out.println("\n📄 Documentação: UseCases disponíveis:");
        System.out.println(" - syncIfOnline()");
        System.out.println(" - getData()");
    }

    // ===== Interface e implementação do repositório (cache + simulação DB) =====

    interface DataRepository {
        List<String> getAllData();
        void saveData(List<String> data);
    }

    static class InMemoryCacheDataRepository implements DataRepository {
        private List<String> cache = new ArrayList<>();

        @Override
        public List<String> getAllData() {
            System.out.println("🔁 Lendo do cache...");
            return cache;
        }

        @Override
        public void saveData(List<String> data) {
            System.out.println("💾 Salvando no cache/DB simulado...");
            this.cache = new ArrayList<>(data);
        }
    }

    // ===== Serviço externo simulado =====

    interface ApiService {
        boolean isOnline();
        List<String> fetchRemoteData();
    }

    static class ApiServiceImpl implements ApiService {
        @Override
        public boolean isOnline() {
            boolean online = new Random().nextBoolean();
            System.out.println("🌐 Status da conexão: " + (online ? "ONLINE" : "OFFLINE"));
            return online;
        }

        @Override
        public List<String> fetchRemoteData() {
            return Arrays.asList("Item 1", "Item 2", "Item 3");
        }
    }

    // ===== Sincronização =====

    static class SyncService {
        private final ApiService apiService;
        private final DataRepository repository;

        public SyncService(ApiService apiService, DataRepository repository) {
            this.apiService = apiService;
            this.repository = repository;
        }

        public void sync() {
            if (apiService.isOnline()) {
                List<String> data = apiService.fetchRemoteData();
                repository.saveData(data);
                System.out.println("🔄 Dados sincronizados com sucesso.");
            } else {
                System.out.println("⚠️ Sem conexão. Sincronização adiada.");
            }
        }
    }

    // ===== UseCases =====

    static class UseCases {
        private final DataRepository repository;
        private final SyncService syncService;

        public UseCases(DataRepository repository, SyncService syncService) {
            this.repository = repository;
            this.syncService = syncService;
        }

        public void syncIfOnline() {
            syncService.sync();
        }

        public void getData() {
            List<String> data = repository.getAllData();
            System.out.println("📦 Dados disponíveis: " + data);
        }
    }
}