## Executando projeto local
Criar Imagem docker do projeto: docker build -t toy-store-product .

Criar uma rede docker: docker network create toystorerede

Visualizar a rede recem criada: docker network ls

Verificar o que está rodando na rede: docker network inspect toystorerede

Executar a Imagem docker localmente: docker run -p 8080:8080 toy-store-product -e SPRING_DATASOURCE_URL=postgres-toy-store

Executar postgres localmente: docker run --name postgres-toy-store -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=postgres -p 5432:5432 --network toystorerede -d postgres

Verificar container criado: http://localhost:8080/stock/swagger-ui/index.html#/