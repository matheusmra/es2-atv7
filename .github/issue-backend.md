## Contexto

Implementar o backend da aplicação de **clínica de saúde multidisciplinar** usando **Spring Boot**, cobrindo todos os requisitos obrigatórios levantados no diagrama de domínio da disciplina.

---

## Entidades e Modelo de Domínio

### `ProfissionalDeSaude`

| Campo       | Tipo     | Observação                                      |
|-------------|----------|-------------------------------------------------|
| `id`        | Long     | Gerado automaticamente (PK)                     |
| `nome`      | String   | Obrigatório                                     |
| `telefone`  | String   | Obrigatório                                     |
| `endereco`  | String   | Obrigatório                                     |
| `categoria` | Enum     | `MEDICO`, `FISIOTERAPEUTA`, `PSICOLOGO`         |

---

### `Atendimento`

| Campo           | Tipo          | Observação                                                   |
|-----------------|---------------|--------------------------------------------------------------|
| `id`            | Long          | Gerado automaticamente (PK)                                  |
| `data`          | LocalDate     | Data do atendimento                                          |
| `horario`       | LocalTime     | Horário do atendimento                                       |
| `problemaTexto` | String        | Descrição do problema relatado pelo paciente                 |
| `receitaSaude`  | String        | Preenchido conforme categoria do profissional (ver abaixo)  |
| `profissional`  | FK → `ProfissionalDeSaude` | Relacionamento N:1 (`*` Atendimento → `1,1` Profissional) |
| `exames`        | List\<ExameLab\> | Relacionamento **1:N** — um atendimento pode ter vários exames |

**Regras de negócio para `receitaSaude`:**
- `MEDICO` → campo destinado a **Remédio**
- `FISIOTERAPEUTA` → campo destinado a **Atividade Física**
- `PSICOLOGO` → campo destinado a **Atividades Mentais**

---

### `ExameLab`

| Campo         | Tipo          | Observação                                   |
|---------------|---------------|----------------------------------------------|
| `id`          | Long          | Gerado automaticamente (PK)                  |
| `descricao`   | String        | Descrição do exame                           |
| `atendimento` | FK → `Atendimento` | Lado N do relacionamento 1:N com Atendimento |

> Relacionamento: **1 Atendimento → N ExameLab** (`@OneToMany` / `@ManyToOne`)

---

## Operações Requeridas

### `ProfissionalDeSaude` — CRUD completo

| Operação              | Endpoint                                 | Método |
|-----------------------|------------------------------------------|--------|
| Inserir               | `POST /profissionais`                    | POST   |
| Alterar por ID        | `PUT /profissionais/{id}`                | PUT    |
| Consultar por Nome    | `GET /profissionais?nome={nome}`         | GET    |
| Consultar por ID      | `GET /profissionais/{id}`                | GET    |
| Consultar por Categoria | `GET /profissionais?categoria={cat}`   | GET    |
| Excluir por ID        | `DELETE /profissionais/{id}`             | DELETE |

### `Atendimento` — CRUD básico

| Operação      | Endpoint                    | Método |
|---------------|-----------------------------|--------|
| Criar         | `POST /atendimentos`        | POST   |
| Listar        | `GET /atendimentos`         | GET    |
| Buscar por ID | `GET /atendimentos/{id}`    | GET    |
| Atualizar     | `PUT /atendimentos/{id}`    | PUT    |
| Excluir       | `DELETE /atendimentos/{id}` | DELETE |

### `ExameLab` — CRUD básico

| Operação      | Endpoint             | Método |
|---------------|----------------------|--------|
| Criar         | `POST /exames`       | POST   |
| Listar        | `GET /exames`        | GET    |
| Buscar por ID | `GET /exames/{id}`   | GET    |
| Atualizar     | `PUT /exames/{id}`   | PUT    |
| Excluir       | `DELETE /exames/{id}`| DELETE |

---

## Tarefas de Implementação

- [ ] Configurar projeto Spring Boot (Spring Web, Spring Data JPA, Validation)
- [ ] Criar enum `CategoriaEnum` (`MEDICO`, `FISIOTERAPEUTA`, `PSICOLOGO`)
- [ ] Criar entidade `ProfissionalDeSaude` com JPA
- [ ] Criar entidade `Atendimento` com FK para `ProfissionalDeSaude` e `@OneToMany` para `ExameLab`
- [ ] Criar entidade `ExameLab` com `@ManyToOne` para `Atendimento`
- [ ] Implementar `ProfissionalRepository` com queries por nome e categoria
- [ ] Implementar `ProfissionalService` com regras de negócio
- [ ] Implementar `ProfissionalController` com os 6 endpoints obrigatórios
- [ ] Implementar `AtendimentoService` com validação da `receitaSaude` por categoria
- [ ] Implementar `AtendimentoController`
- [ ] Implementar `ExameLabController`
- [ ] Criar DTOs de request/response para cada entidade
- [ ] Tratar exceções com `@ControllerAdvice` (404, 400)
- [ ] **Testes unitários** para `ProfissionalService` e `AtendimentoService` com JUnit 5 + Mockito
- [ ] **Testes de integração** para os controllers com `@SpringBootTest` / `MockMvc`
- [ ] **Dockerfile** para containerização da aplicação
- [ ] `docker-compose.yml` com serviço da aplicação + PostgreSQL

---

## Testes Unitários

Cobertura mínima esperada:

```
ProfissionalServiceTest
  ✓ deve lançar exceção ao buscar profissional inexistente
  ✓ deve retornar lista de profissionais por categoria
  ✓ deve salvar profissional com dados válidos
  ✓ deve lançar exceção ao excluir profissional inexistente

AtendimentoServiceTest
  ✓ deve criar atendimento vinculado a profissional existente
  ✓ deve lançar exceção ao vincular profissional inexistente
  ✓ deve validar receitaSaude conforme categoria do profissional
```

---

## Dockerfile

```dockerfile
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## Stack

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA + Hibernate**
- **Spring Validation** (`@Valid`, `@NotBlank`, etc.)
- **JUnit 5 + Mockito** (testes unitários)
- **MockMvc** (testes de integração)
- **Banco de dados:** H2 (dev) / PostgreSQL (produção)
- **Build:** Maven
- **Container:** Docker


Implementar o backend da aplicação de **clínica de saúde multidisciplinar** usando **Spring Boot**, cobrindo todos os requisitos obrigatórios levantados no diagrama de domínio da disciplina.

---

## Entidades e Modelo de Domínio

### `ProfissionalDeSaude`

| Campo       | Tipo     | Observação                                      |
|-------------|----------|-------------------------------------------------|
| `id`        | Long     | Gerado automaticamente (PK)                     |
| `nome`      | String   | Obrigatório                                     |
| `telefone`  | String   | Obrigatório                                     |
| `endereco`  | String   | Obrigatório                                     |
| `categoria` | Enum     | `MEDICO`, `FISIOTERAPEUTA`, `PSICOLOGO`         |

> A categoria é um discriminador de subtipo. A Receita de Saúde gerada no atendimento varia conforme a categoria do profissional (ver `Atendimento`).

---

### `Atendimento`

| Campo           | Tipo          | Observação                                                   |
|-----------------|---------------|--------------------------------------------------------------|
| `id`            | Long          | Gerado automaticamente (PK)                                  |
| `data`          | LocalDate     | Data do atendimento                                          |
| `horario`       | LocalTime     | Horário do atendimento                                       |
| `problemaTéxto` | String        | Descrição do problema relatado pelo paciente                 |
| `receitaSaude`  | String        | Preenchido conforme categoria do profissional (ver abaixo)  |
| `profissional`  | FK → `ProfissionalDeSaude` | Relacionamento N:1 (`*` Atendimento → `1,1` Profissional) |

**Regras de negócio para `receitaSaude`:**
- `MEDICO` → campo destinado a **Remédio**
- `FISIOTERAPEUTA` → campo destinado a **Atividade Física**
- `PSICOLOGO` → campo destinado a **Atividades Mentais**

---

### `ExameLab`

| Campo      | Tipo   | Observação                  |
|------------|--------|-----------------------------|
| `id`       | Long   | Gerado automaticamente (PK) |
| `descricao`| String | Descrição do exame          |

> Relacionamento com `Atendimento` a ser detalhado (parcialmente visível no diagrama).

---

## Operações Requeridas

### `ProfissionalDeSaude` — CRUD completo

| Operação              | Endpoint sugerido                                  | Método HTTP |
|-----------------------|----------------------------------------------------|-------------|
| Inserir               | `POST /profissionais`                              | POST        |
| Alterar por ID        | `PUT /profissionais/{id}`                          | PUT         |
| Consultar por Nome    | `GET /profissionais?nome={nome}`                   | GET         |
| Consultar por ID      | `GET /profissionais/{id}`                          | GET         |
| Consultar por Categoria | `GET /profissionais?categoria={categoria}`       | GET         |
| Excluir por ID        | `DELETE /profissionais/{id}`                       | DELETE      |

### `Atendimento` — CRUD básico

| Operação        | Endpoint sugerido           | Método HTTP |
|-----------------|-----------------------------|-------------|
| Criar           | `POST /atendimentos`        | POST        |
| Listar          | `GET /atendimentos`         | GET         |
| Buscar por ID   | `GET /atendimentos/{id}`    | GET         |
| Atualizar       | `PUT /atendimentos/{id}`    | PUT         |
| Excluir         | `DELETE /atendimentos/{id}` | DELETE      |

---

## Tarefas de Implementação

- [ ] Configurar projeto Spring Boot (Spring Web, Spring Data JPA, banco H2 ou PostgreSQL)
- [ ] Criar enum `CategoriaEnum` (`MEDICO`, `FISIOTERAPEUTA`, `PSICOLOGO`)
- [ ] Criar entidade `ProfissionalDeSaude` com JPA
- [ ] Criar entidade `Atendimento` com FK para `ProfissionalDeSaude`
- [ ] Criar entidade `ExameLab` com relacionamento para `Atendimento`
- [ ] Implementar `ProfissionalRepository` com queries por nome e categoria
- [ ] Implementar `ProfissionalService` com regras de negócio
- [ ] Implementar `ProfissionalController` com os 6 endpoints obrigatórios
- [ ] Implementar `AtendimentoService` com validação da `receitaSaude` por categoria
- [ ] Implementar `AtendimentoController`
- [ ] Implementar `ExameLabController`
- [ ] Criar DTOs de request/response para cada entidade
- [ ] Tratar exceções com `@ControllerAdvice` (404, 400)
- [ ] Testes básicos dos endpoints

---

## Stack

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Banco de dados:** H2 (dev) / PostgreSQL (produção)
- **Build:** Maven ou Gradle
