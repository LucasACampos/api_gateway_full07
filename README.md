# api_gateway_full07

# Premissas
Kong rodando nas portas 8000(gateway) 8001(api de configuracao)<br/>
Java 17 + Maven para execução do projeto.<br/>
Porta 8080 disponivel para execução do projeto<br/>

# Execução
O projeto se cadastra no kong ao ser iniciado utilizando workspace default (tanto workspace como url da api de configuração do kong podem ser alterados no .properties) <br/>

Para iniciar o projeto spring executar raiz do projeto clonado:<br/>
<code>mvn spring-boot:run -f "./ApiGatewayExercicio/pom.xml"</code><br/>

O arquivo de configuração do kong utilizando a collection do postman é um backup(KongConfig.postman_collection.json), caso o sistema não consiga se cadastrar, os endpoints da collection devem ser executados de cima para baixo<br/><br/>

A collection do postman Sistema.postman_collection.json possui tanto os end-points do kong quando do projeto spring direto<br/>

O projeto utiliza o banco de dados h2 no modo de salvando em arquivo
<img src="https://raw.githubusercontent.com/LucasACampos/api_gateway_full07/main/gifFuncionamento.gif"/>

# Swagger
http://localhost:8080/swagger-ui/index.html#/
# Database/h2
http://localhost:8080/h2 <br/>
login:sa<br/> 
senha:[vazio]<br/>
jdbc-url: jdbc:h2:file:./data/db
