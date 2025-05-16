# Instruções para Implementação do Backend

## Endpoints Necessários

### 1. Carregar Perguntas
```http
GET /api/questions
```

**Resposta:**
```json
[
  {
    "id": "string",
    "text": "string",
    "options": ["string"]
  }
]
```

### 2. Enviar Respostas
```http
POST /api/answers
```

**Request Body:**
```json
[
  {
    "questionId": "string",
    "questionText": "string",
    "selectedOption": "string"
  }
]
```

**Resposta:**
```json
{
  "success": true,
  "message": "string"
}
```

### 3. Enviar Resumo
```http
POST /api/summary
```

**Request Body:**
```json
[
  {
    "questionId": "string",
    "questionText": "string",
    "selectedOption": "string"
  }
]
```

**Resposta:**
```json
{
  "success": true,
  "message": "string"
}
```

## Implementação no Frontend

### 1. QuestionnaireViewModel
```kotlin
// Adicionar dependência do Retrofit
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

// Criar interface da API
interface MindCareApi {
    @GET("questions")
    suspend fun getQuestions(): List<Question>

    @POST("answers")
    suspend fun sendAnswers(@Body answers: List<Answer>): Response<ApiResponse>

    @POST("summary")
    suspend fun submitSummary(@Body answers: List<Answer>): Response<ApiResponse>
}

// Implementar no ViewModel
class QuestionnaireViewModel : ViewModel() {
    private val api = // Configurar Retrofit

    fun loadQuestions() = viewModelScope.launch {
        try {
            _uiState.value = UiState.Loading
            val questions = api.getQuestions()
            _uiState.value = UiState.Success(questions)
        } catch (e: Exception) {
            _uiState.value = UiState.Error("Erro ao carregar perguntas: ${e.message}")
        }
    }

    fun sendAnswers() = viewModelScope.launch {
        try {
            val response = api.sendAnswers(answers)
            if (response.isSuccessful) {
                // Navegar para tela de resumo
            } else {
                // Tratar erro
            }
        } catch (e: Exception) {
            // Tratar erro
        }
    }
}
```

### 2. SummaryViewModel
```kotlin
class SummaryViewModel : ViewModel() {
    private val api = // Configurar Retrofit

    fun submitSummary(answers: List<Answer>) = viewModelScope.launch {
        _submitState.value = UiState.Loading
        try {
            val response = api.submitSummary(answers)
            if (response.isSuccessful) {
                _submitState.value = UiState.Success(Unit)
            } else {
                _submitState.value = UiState.Error("Erro ao enviar resumo")
            }
        } catch (e: Exception) {
            _submitState.value = UiState.Error("Erro ao enviar resumo: ${e.message}")
        }
    }
}
```

## Considerações

1. **Tratamento de Erros**
   - Implementar tratamento adequado para erros de rede
   - Mostrar mensagens amigáveis para o usuário
   - Implementar retry em caso de falha

2. **Cache**
   - Considerar implementar cache local para as perguntas
   - Usar Room para persistência local

3. **Segurança**
   - Implementar autenticação se necessário
   - Usar HTTPS
   - Validar dados no servidor

4. **Performance**
   - Implementar paginação se houver muitas perguntas
   - Otimizar tamanho das requisições
   - Usar compressão

5. **Testes**
   - Implementar testes unitários para os ViewModels
   - Implementar testes de integração
   - Testar diferentes cenários de erro

## Exemplo de Configuração do Retrofit

```kotlin
object RetrofitClient {
    private const val BASE_URL = "https://api.mindcare.com/" // Substituir pela URL real

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: MindCareApi = retrofit.create(MindCareApi::class.java)
}
```

## Próximos Passos

1. Implementar os endpoints no backend
2. Configurar o Retrofit no frontend
3. Implementar tratamento de erros
4. Adicionar testes
5. Implementar cache local 