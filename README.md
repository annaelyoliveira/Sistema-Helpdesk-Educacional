# 🖥️ Sistema de Helpdesk Educacional (API REST)
## 📖 Sobre o Projeto
Este projeto é uma API REST desenvolvida para gerenciar chamados de suporte de TI em uma instituição de ensino. A aplicação permite o cadastro de usuários (alunos/professores), técnicos e a gestão completa do ciclo de vida de um chamado (abertura, atribuição e finalização).

O diferencial deste projeto é a demonstração do domínio de **Mapeamento Objeto-Relacional (ORM)** utilizando **Hibernate e JPA (Jakarta Persistence API)** de forma nativa dentro do ecossistema Spring Boot, **sem o uso de Spring Data JPA**. Isso garante o controle total sobre as consultas JPQL, o ciclo de vida das entidades e o gerenciamento transacional manual via `EntityManager`.

## 🚀 Tecnologias Utilizadas
* **Java 21:** Versão estável e moderna da linguagem.
* **Spring Boot 3.x:** Framework para agilizar o setup da aplicação REST.
* **Hibernate ORM:** Motor de persistência principal.
* **JPA (Jakarta Persistence API):** Especificação para manipulação de dados.
* **PostgreSQL:** Banco de dados relacional robusto.
* **Maven:** Gerenciamento de dependências.
* **Postman:** Ferramenta para validação e testes dos endpoints.

## ⚙️ Arquitetura e Padrões
A aplicação segue o padrão de **Camadas (Layered Architecture)**, garantindo a separação de responsabilidades:
* **Model (Entidades):** Mapeamento das tabelas do banco de dados com anotações JPA.
* **Repository:** Camada de acesso aos dados utilizando `@PersistenceContext` e `EntityManager` para execução de comandos SQL/JPQL.
* **Service:** Camada de lógica de negócio e controle transacional (`@Transactional`).
* **Controller:** Exposição dos endpoints REST e tratamento de requisições HTTP.

## 🗂️ Modelagem de Entidades
O sistema gerencia três domínios principais:
1. **`Usuario`**: O cliente que solicita o suporte (Aluno/Professor).
2. **`Tecnico`**: O profissional especializado que resolve os chamados.
3. **`Chamado`**: O ticket de suporte que vincula usuários e técnicos.

**Relacionamentos:**
* `Chamado` ➡️ `Usuario` (**Many-to-One**): Um usuário pode abrir múltiplos chamados.
* `Chamado` ➡️ `Tecnico` (**Many-to-One**): Um técnico pode ser responsável por vários chamados.

## 🛠️ Como Executar o Projeto

### 1. Pré-requisitos
* Java JDK 21 instalado.
* PostgreSQL instalado e rodando.
* Postman instalado (opcional, para testes).

### 2. Configuração do Banco de Dados
1. No seu PostgreSQL (pgAdmin), crie um banco de dados chamado `helpdesk`.
2. Verifique as credenciais no arquivo `src/main/resources/META-INF/persistence.xml` (usuário e senha do Postgres).

### 3. Rodando a Aplicação
1. Clone o repositório:
   ```bash
   git clone https://github.com/SEU_USUARIO/Sistema-Helpdesk-Educacional.git
   ```
2. Na pasta raiz do projeto, execute:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

### 📥 Testando a API
Importe o arquivo **`Helpdesk.postman_collection.json`** (disponível na raiz do projeto) para o seu Postman. Ele contém todas as rotas prontas, incluindo:
* Cadastro e listagem de usuários/técnicos.
* Abertura de chamados vinculados ao usuário.
* Filtros de chamados (Abertos, Em Andamento, Finalizados).
* Atualização de status e exclusão de registros.

---

### 💡 Observação para Avaliação
Esta aplicação **não utiliza Spring Data JPA (`JpaRepository`)**. Toda a persistência foi implementada manualmente nos repositórios utilizando o `EntityManager`, conforme solicitado, para demonstrar o conhecimento profundo na especificação JPA e no framework Hibernate.