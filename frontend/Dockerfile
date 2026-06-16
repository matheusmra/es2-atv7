# Estágio 1: Build da aplicação React/Vite
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
# Argumento para a URL da API (Dokploy/Docker injeta durante o build)
ARG VITE_API_URL
ENV VITE_API_URL=${VITE_API_URL}
RUN npm run build

# Estágio 2: Servidor Nginx para os arquivos estáticos
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
# Copia configuração do Nginx para suportar React Router e Proxy para API
RUN echo "server { \
    listen 80; \
    location /api/ { \
        proxy_pass http://app:8080/; \
    } \
    location / { \
        root /usr/share/nginx/html; \
        index index.html index.htm; \
        try_files \$uri \$uri/ /index.html; \
    } \
}" > /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
