# 📊 API de Simulação e Adesão de Empréstimos

## 📌 Visão Geral
API RESTful para simulação de condições de empréstimo e processamento de adesões, com validações de regras de negócio, cálculo de parcelas e geração de contratos.

## 🧱 Arquitetura

![Arquitetura do Sistema](https://github.com/matheusmedeirosn/credit-simulator-api/blob/9e24529b27ddd6113a38640414a9a1cfa6aeef63/Sistema%20de%20Simulac%CC%A7a%CC%83o%20e%20Proposta%20de%20Empre%CC%81stimos.png?raw=true)

## ✨ Funcionalidades Principais

### 🔍 Simulação de Empréstimos
- Cálculo personalizado de condições
- Simulação de diferentes cenários
- Validação de elegibilidade do cliente

### 📝 Adesão Digital
- Processo completo de contratação
- Validação documental
- Análise automatizada de crédito
- Geração de contrato digital

### ⚙️ Funcionalidades Técnicas
- Cache de simulações
- Comunicação com mensageria para escrita das adesões

## 🛠 Tecnologias Utilizadas

### Backend
- Java 17 (LTS)
- Spring Boot 3.5.3
- OpenAPI 3 (Documentação)

### Mensageria
- RabbitMQ

### Cache
- Redis

## 📋 Pré-requisitos

- JDK 17+
- Maven 3.8+

## 🚀 Instalação e Execução

### Ambiente Local
```bash
# Clonar repositório
git clone https://github.com/matheusmedeirosn/credit-simulator-api.git

# Configurar variáveis de ambiente de redis e rabbitMQ (arquivo application.properties)

# Build e execução
mvn clean install
mvn spring-boot:run
