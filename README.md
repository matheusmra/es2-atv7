# Clínica Multidisciplinar — es2-atv7

Aplicação de gestão para clínica de saúde multidisciplinar desenvolvida como atividade da disciplina de Engenharia de Software 2.

🚀 **Link da Aplicação (Produção):** [https://clinica.apps.webtech.network/](https://clinica.apps.webtech.network/)

---

## Visão Geral

O sistema permite gerenciar **profissionais de saúde** (médicos, fisioterapeutas e psicólogos), **atendimentos** e **exames laboratoriais**, com regras de negócio específicas por categoria de profissional.

## Tecnologias

| Camada    | Tecnologia                             |
|-----------|----------------------------------------|
| Backend   | Java 17 + Spring Boot 3.x              |
| Frontend  | React 18 + Vite + React Router v6      |
| Banco     | H2 (dev) / PostgreSQL (produção)       |
| Build     | Maven (backend) / npm + Vite (frontend)|
| Ícones    | Google Material Symbols                |
| Testes    | JUnit 5 + Mockito + MockMvc            |
| Container | Docker                                 |

---

## Estrutura do Projeto

```
es2-atv7/
├── backend/          # Spring Boot (Java 17)
│   ├── src/
│   │   ├── main/java/com/clinica/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── model/
│   │   │   ├── dto/
│   │   │   └── exception/
│   │   └── resources/
│   ├── Dockerfile
│   └── pom.xml
└── frontend/         # React + Vite (Node 18+)
    ├── src/
    │   ├── api/
    │   ├── components/
    │   │   └── ui/   # Button, Input, Select, Textarea
    │   └── pages/
    └── vite.config.js
```

---

## Backend

### Como rodar

```bash
cd backend
./mvnw spring-boot:run
```

API disponível em `http://localhost:8080`.

### Endpoints principais

| Recurso       | Base URL          |
|---------------|-------------------|
| Profissionais | `/profissionais`  |
| Atendimentos  | `/atendimentos`   |
| Exames Lab    | `/exames`         |

### Testes

```bash
cd backend
./mvnw test
```

### Docker

```bash
cd backend
docker build -t clinica-backend .
docker run -p 8080:8080 clinica-backend
```

---

## Frontend

### Como rodar

```bash
cd frontend
npm install
npm run dev
```

Aplicação disponível em `http://localhost:3000`.

> O Vite está configurado com proxy: requisições para `/api` são redirecionadas automaticamente para `http://localhost:8080`.

---

## Autores e Contribuições

Projeto desenvolvido de forma colaborativa para a disciplina de Engenharia de Software 2.

| Aluno | Papel / Responsabilidades |
| :--- | :--- |
| **Arthur Carvalho Rodrigues** | **Desenvolvimento Backend**<br/>Testes Unitários, Arquitetura, Lógica de Negócios (Java/Spring) e Containerização (Docker) |
| **Matheus de Almeida Moreira** | **Desenvolvimento Fullstack (Frontend + Backend)**<br/>Infraestrutura, CI/CD e Deploy na VPS |
