# Projeto de Cadastro de Pessoas e Endereços
Este é um projeto Spring Boot para um sistema de cadastro de pessoas e seus endereços. 
O sistema permite o gerenciamento de informações básicas sobre pessoas, como nome, data de nascimento e nome da mãe, além de seus endereços associados.

## Tecnologias Utilizadas
- Java 17
- Spring Boot
- Spring Data JPA / Hibernate
- Maven
- H2 Database

## Configuração do Projeto

**Pré-requisitos:** Java 17  

**Configuração do Banco de Dados:** O projeto utiliza o banco de dados H2 em memória por padrão.
O H2 será inicializado automaticamente quando a aplicação for executada.  
Obs.: Há um arquivo .sql que popula o banco de dados H2 com alguns registros automaticamente ao executar o projeto.

**Execução do Projeto:** Navegue até o diretório raiz do projeto e execute o seguinte comando para iniciar a aplicação:
```bash
# run project
./mvnw spring-boot:run
```

## Endpoints

Com a aplicação executando, acesse a url abaixo para ver as especificações de cada endpoint

**Swagger:** http://localhost:8080/swagger-ui/index.html

**Print swagger endpoints**

![img.png](print_adresses_swagger.png)
![img_1.png](print_persons_swagger.png)