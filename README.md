# SkyNet – Plataforma de Monitoramento Inteligente

**SkyNet** é uma plataforma de vigilância inteligente que integra visão computacional e inteligência artificial para monitoramento de ambientes urbanos. Desenvolvida com foco em segurança pública, a aplicação permite a detecção de comportamentos suspeitos e o registro de ocorrências em tempo real.

## 🚀 Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3**
* **Spring Security**
* **JWT (JSON Web Token)**
* **JPA/Hibernate**
* **PostgreSQL**
* **Gradle**

## 🔐 Funcionalidades do Backend

* Autenticação e autorização com JWT e Spring Security.
* Registro de usuários e login seguro.
* CRUD de ocorrências com associação ao usuário responsável.
* Armazenamento de dados em banco PostgreSQL.
* API RESTful com endpoints protegidos.
* Integração com filtros de segurança personalizados.

## 📦 Estrutura do Projeto

```
skyNet/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/skyNet/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── security/
│   │   └── resources/
│   │       ├── application.properties
│   │       └── ...
├── build.gradle
└── settings.gradle
```

## 🧪 Como Executar o Projeto

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/viMoraes10/skyNet.git
   cd skyNet
   ```

2. **Configure o banco de dados:**

   Certifique-se de que o PostgreSQL está instalado e crie um banco de dados para o projeto. Atualize as credenciais no arquivo `application.properties`.

3. **Execute a aplicação:**

   ```bash
   ./gradlew bootRun
   ```

   A aplicação estará disponível em `http://localhost:8080`.

## 📌 Endpoints Principais

* `POST /auth/login`: Autenticação de usuários.
* `POST /auth/register`: Registro de novos usuários.
* `GET /occurrence`: Lista todas as ocorrências.
* `POST /occurrence`: Cria uma nova ocorrência.
* `PUT /occurrence/{id}`: Atualiza uma ocorrência existente.
* `DELETE /occurrence/{id}`: Remove uma ocorrência.

*Nota: Todos os endpoints de ocorrência requerem autenticação via JWT.*

## 📈 Futuras Implementações

* Detecção automática de furtos e roubos utilizando IA.
* Integração com sistemas de reconhecimento facial em ambientes como restaurantes, mercados e estádios de futebol.
* Geração de alertas em tempo real para autoridades competentes.

## 🤝 Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou pull requests.
