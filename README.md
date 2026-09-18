# 🚪 Gateway Service

O **Gateway Service** é o ponto central de entrada para todos os serviços da aplicação.
Ele utiliza o [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway) e o
[Netflix Eureka](https://spring.io/projects/spring-cloud-netflix) para **roteamento inteligente**,
**descoberta dinâmica** e **balanceamento de carga** entre serviços.

---

## 🚀 Funcionalidades

* Atua como **porta de entrada** para todos os serviços;
* Faz o **roteamento dinâmico** das requisições via **Eureka**;
* Suporta **balanceamento de carga** automático com `lb://`;
* Permite aplicar **filtros globais**, autenticação e log de requisições;
* Facilita a integração com serviços como `security`, `user`, `order`, etc.

---

## 🧠 Como Funciona

1. O **Gateway** se registra automaticamente no **Discovery Service** (Eureka);
2. Ele consulta o Eureka para descobrir a localização dos demais serviços;
3. Requisições externas chegam ao Gateway, que as encaminha para o serviço correto (ex: `/users/**`, `/orders/**`);
4. Caso o serviço de destino esteja indisponível, o Gateway pode aplicar políticas de fallback e resiliência.

---

## 🧩 Tecnologias Utilizadas

* [Java 25](https://www.oracle.com/java/) – Linguagem principal;
* [Spring Boot](https://spring.io/projects/spring-boot) – Framework para inicialização e gestão da aplicação;
* [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway) – Roteamento Inteligente;
* [Gradle 9.4.0](https://gradle.org/) – Sistema de build;
* [Maven](https://maven.apache.org/) – Sistema de build;
* [Docker](https://www.docker.com/) – Empacotamento e deploy containerizado.

---

## ⚙️ Variáveis de Ambiente

A aplicação utiliza variáveis de ambiente para configuração, com suporte a valores padrões definidos no `application.yml`.

Formato utilizado:

```
${NOME_VARIAVEL:valor_padrao}
```

### 📋 Configurações disponíveis

| Variável               | Descrição                                                              | Default                                       |
|------------------------|------------------------------------------------------------------------|-----------------------------------------------|
| `EUREKA_URL`           | URL do servidor Eureka utilizado para registro e descoberta            | `http://localhost:8761/eureka/`               |
| `CORS_ALLOWED_ORIGINS` | Lista de origens autorizadas a consumir o Gateway via navegador (CORS) | `http://localhost:4200,http://localhost:3000` |

> Caso a variável não seja definida, o valor padrão especificado será utilizado automaticamente pela aplicação.

---

## 🛠️ Build Local

### 📦 Pré-requisitos

Antes de realizar o Build da aplicação, certifique-se de que as seguintes dependências estão instaladas e configuradas
em seu ambiente:

* [Java 25](https://www.oracle.com/java/)
* [Gradle 9.4.0](https://gradle.org/) *(ou utilize o wrapper incluído no projeto)*
* [Maven](https://maven.apache.org/)

### 🚀 Executando o Build

No diretório raiz do projeto, execute um dos comandos abaixo:

```bash
gradle clean build
```

Ou utilizando o wrapper do Gradle (**recomendado**):

```bash
./gradlew clean build
```

### 📁 Artefato Gerado

Após a execução do build, o arquivo `.jar` será gerado no seguinte diretório:

```
/build/libs/gateway.jar
```

---

## 🐳 Build com Docker

Para gerar a imagem Docker da aplicação, utilize a task customizada do Gradle:

``` bash
./gradlew clean dockerBuild -Penv=dev
```

### ⚙️ Parâmetros

Parâmetro obrigatório que define o ambiente da imagem.

### 🔹 -Penv
**Valores permitidos:** [ dev, test, homolog, prod ]

**Exemplo:**

``` bash
./gradlew clean dockerBuild -Penv=test
```

Ao executar a task `docker`, o Gradle realiza automaticamente:
-   Executa a task `bootJar`
-   Gera o `.jar` da aplicação em:
```
    /build/libs/gateway.jar
```

### 🐳 Build da imagem Docker

-   Executa o `docker build`
-   Utiliza o `Dockerfile` do projeto

### 🏷️ Geração de tags da imagem

-   Sempre gera a tag com a versão:

        gateway:${VERSION}

-   Gera também a tag do ambiente informado:

        gateway:${ENV}

## 🏷️ Exemplos de saída

### 🔹 Exemplo com dev

``` bash
./gradlew clean dockerBuild -Penv=dev
```

**Gera as imagens:** - gateway:1.0.0 - gateway:dev

### 🔹 Exemplo com test

``` bash
./gradlew clean dockerBuild -Penv=test
```

**Gera:** - gateway:1.0.0 - gateway:test

## ❗ Validações aplicadas

A task possui validações para garantir consistência:

-   Caso o parâmetro `env` não seja informado, o Build será interrompido
-   Caso seja informado um valor inválido, o Build será interrompido

Isso evita erros em pipelines e padroniza os ambientes.

## 📦 Dockerfile

A imagem é construída a partir do Dockerfile do projeto, que:

-   Utiliza uma imagem base leve (`eclipse-temurin:25-jre-alpine`)
-   Copia o `.jar` gerado pelo Gradle
-   Configura timezone (`America/Sao_Paulo`)
-   Expõe a porta `8080`
-   Define um `HEALTHCHECK` para o endpoint `/actuator/health`

## 🔄 Integração com CI/CD

Essa task foi projetada para ser utilizada em pipelines.

**Exemplo:**

``` bash
./gradlew clean dockerBuild -Penv=test
```

Após isso, a pipeline pode:

-   Realizar login no registry
-   Fazer push das imagens
-   Executar o deploy no ambiente correspondente

## 💡 Boas práticas

-   Utilize `clean` em pipelines para garantir builds consistentes
-   Utilize `-Penv` para padronizar ambientes (`dev`, `test`, `homolog`, `prod`)
-   Não inclua etapas de deploy dentro do Gradle (responsabilidade dá pipeline)

## 🔄 Executar no docker como Dev

``` bash
docker run -d --name gateway-service --restart always -p 8080:8080 -e EUREKA_URL=http://eureka-service:8761/eureka/ --network houpper-network gateway-service:dev
```

---

## 🚀 Build CI/CD

*Em construção.*

---