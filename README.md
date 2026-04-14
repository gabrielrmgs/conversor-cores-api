# Cromatick — Conversor de Cores API

API REST para conversão entre diferentes modelos de cores: **RGB, CMYK, HSV e HSL**, com foco em precisão, consistência e facilidade de integração.

---

## Sobre o projeto

O **Cromatick** é uma API desenvolvida para realizar conversões entre modelos de cores de forma confiável, evitando inconsistências comuns em processos de reconversão.

A aplicação também inclui uma interface web integrada para uso direto no navegador.

Principais características:

* Conversões consistentes entre modelos de cores
* Preservação do modelo de entrada (source of truth)
* API leve e performática
* Interface web embutida
* Documentação automática via OpenAPI

---

## Modelos suportados

* RGB (Red, Green, Blue)
* CMYK (Cyan, Magenta, Yellow, Black)
* HSV (Hue, Saturation, Value)
* HSL (Hue, Saturation, Lightness)

---

## Endpoints

### RGB → outros modelos

```
POST /cores/rgb
```

Exemplo:

```json
{
  "r": 28,
  "g": 132,
  "b": 116
}
```

---

### CMYK → outros modelos

```
POST /cores/cmyk
```

Exemplo:

```json
{
  "c": 0.82,
  "m": 0.16,
  "y": 0.26,
  "k": 0.38
}
```

---

### HSV → outros modelos

```
POST /cores/hsv
```

Exemplo:

```json
{
  "h": 170.77,
  "s": 0.79,
  "v": 0.52
}
```

---

### HSL → outros modelos

```
POST /cores/hsl
```

Exemplo:

```json
{
  "h": 170.77,
  "s": 0.65,
  "l": 0.31
}
```

---

## Exemplo de resposta

```json
{
  "rgb": { "r": 28, "g": 132, "b": 116 },
  "cmyk": { "c": 0.82, "m": 0.16, "y": 0.26, "k": 0.38 },
  "hsv": { "h": 170.77, "s": 0.79, "v": 0.52 },
  "hsl": { "h": 170.77, "s": 0.65, "l": 0.31 },
  "hex": "#1C8474"
}
```

---

## Decisão de arquitetura

A API segue a regra:

**O modelo de entrada é preservado na resposta.**

Exemplo:

* Entrada em CMYK → saída mantém o CMYK original
* Não há reconversão CMYK → RGB → CMYK

Motivação:

* CMYK não é reversível com precisão via RGB
* Evita perda de informação
* Garante consistência para o consumidor da API

---

## Interface web

O projeto possui uma interface web integrada para uso direto no navegador.

Local do arquivo:

```
src/main/resources/META-INF/resources/cromatick.html
```

Acesso:

```
http://localhost:7000/cromatick.html
```

---

## Documentação (OpenAPI)

A API já possui documentação automática via OpenAPI.

Após subir o projeto, acesse:

```
http://localhost:7000/q/swagger-ui
```

---

## Tecnologias

* Java 25
* Quarkus 3.34
* REST (JAX-RS)
* Maven

---

## Como executar

### Pré-requisitos

* Java 25
* Maven 3.9+

---

### Modo desenvolvimento

```bash
./mvnw quarkus:dev
```

---

### Build

```bash
./mvnw clean package
```

---

### Execução do jar

```bash
java -jar target/*-runner.jar
```

---
## Docker

O projeto já está preparado para execução com Docker, incluindo `Dockerfile` e `docker-compose.yml`.

### Pré-requisito

* Docker instalado na máquina

---

### Passos para execução

Clone o repositório:

```bash
git clone https://github.com/seu-usuario/conversor-cores-api.git
cd conversor-cores-api
```

Gere o build da aplicação:

```bash
mvn clean package
```

Suba o container:

```bash
docker compose up --build -d
```

---

### Acesso

Após subir o container, a aplicação estará disponível em:

```bash
http://localhost:7000
```

Interface web:

```bash
http://localhost:7000/cromatick.html
```

---

### Configuração utilizada

```yaml
services:
  web-api:
    build: .
    container_name: conversor-cores-api
    ports:
      - "7000:7000"
    restart: always
```

## Testes

A API pode ser testada via:

* Swagger UI
* Postman
* Insomnia
* cURL

---

## Precisão numérica

* RGB: intervalo `[0–255]`
* CMYK, HSV, HSL: intervalo `[0–1]`
* Hue: `[0–360]`

Os valores são arredondados para manter equilíbrio entre precisão e legibilidade.

---

## Melhorias futuras

* Suporte a modelos LAB e XYZ
* Perfis de cor (ICC)
* Cache de conversões
* Rate limiting
* Versionamento da API

---

## Licença

Este projeto está sob a licença MIT.

---

## Autor

Gabriel Sá

---
