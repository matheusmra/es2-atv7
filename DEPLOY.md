# Documentação de Deploy (Dokploy / Docker Compose)

Este projeto utiliza **Infrastructure as Code (IaC)** através do arquivo `docker-compose.yml` para definir e orquestrar de forma explícita todos os serviços necessários para rodar a aplicação em produção.

## Arquitetura de Deploy

O ambiente é orquestrado por 3 containers principais:

1. **Banco de Dados (`db`)**
   - **Imagem:** `postgres:16-alpine`
   - **Função:** Persistência de dados (Consultas, Exames, Profissionais).
   - **Volume:** `pgdata` (Garante que os dados não sejam perdidos ao reiniciar o container).

2. **Backend (`app`)**
   - **Imagem:** Construída localmente via `backend/Dockerfile` (Multi-stage build com Maven e Eclipse Temurin 17 JRE).
   - **Função:** API RESTful construída com Spring Boot.
   - **Integração:** Conecta-se automaticamente ao container do PostgreSQL através da variável de ambiente `SPRING_DATASOURCE_URL`.

3. **Frontend (`frontend`)**
   - **Imagem:** Construída localmente via `frontend/Dockerfile` (Multi-stage build com Node.js e Nginx).
   - **Função:** Interface do usuário (React + Vite).
   - **Integração:** O Nginx foi explicitamente configurado para redirecionar requisições não encontradas para o `index.html` (comportamento de SPA/React Router). Ele recebe a variável de ambiente `VITE_API_URL` no momento do build.

## Como rodar localmente ou na VPS (via Dokploy)

O **Dokploy** possui suporte nativo para aplicações Compose. Para fazer o deploy:

1. No Dokploy, crie um novo projeto do tipo **Compose**.
2. Conecte ao repositório Git.
3. Defina o path para este `docker-compose.yml`.
4. Defina as variáveis de ambiente diretamente no painel do Dokploy (se quiser sobrescrever as que estão no compose, como senhas de banco ou domínios da API).
5. Faça o Deploy.

### Rodando via CLI (Local)
Para levantar a infraestrutura inteira no seu próprio computador via terminal:
\`\`\`bash
docker-compose up --build -d
\`\`\`
