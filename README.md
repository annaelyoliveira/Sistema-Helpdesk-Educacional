# 🖥️ Sistema de Gerenciamento de Chamados de TI (Helpdesk Educacional)

## 📖 Sobre o Projeto
Este projeto é uma aplicação Console (Java SE) desenvolvida para gerenciar problemas de infraestrutura e TI relatados por funcionários de uma instituição de ensino. 

O objetivo principal desta aplicação é demonstrar a implementação prática e o domínio de mapeamento objeto-relacional (ORM) utilizando **Hibernate em conjunto com a especificação JPA (Jakarta Persistence API)**, sem o uso de abstrações adicionais como o Spring Data. O projeto aborda a configuração manual do provedor de persistência, controle transacional explícito e mapeamento de entidades com relacionamentos.

## 🚀 Tecnologias Utilizadas
* **Java:** Linguagem de programação principal (Versão 17+).
* **Hibernate ORM:** Provedor de persistência e motor do JPA.
* **JPA (Jakarta Persistence API):** Padrão e especificação de persistência do Java.
* **PostgreSQL:** Banco de dados relacional.
* **Maven:** Gerenciamento de dependências e build.
* **Git:** Controle de versão.

## ⚙️ Arquitetura e Padrões
* **Padrão DAO (Data Access Object):** Isolamento total das operações de banco de dados (CRUD) em classes específicas.
* **Singleton:** Utilizado na classe utilitária `JPAUtil` para garantir uma única instância do `EntityManagerFactory` durante o ciclo de vida da aplicação.
* **Gerenciamento de Transações:** Operações de escrita (`persist`, `merge`, `remove`) são controladas manualmente através do `EntityManager`, garantindo a integridade dos dados com `commit` e `rollback`.

## 🗂️ Modelagem de Entidades e Relacionamentos
O domínio do sistema é composto por três entidades principais:
1. **`Funcionario`**: Representa o colaborador da escola que solicita o suporte.
2. **`Tecnico`**: Representa o profissional de TI responsável por resolver o problema.
3. **`Chamado`**: A ocorrência em si (com título, descrição, data e status).

**Relacionamentos mapeados:**
* Vários Chamados podem ser abertos por um único Funcionário (`@ManyToOne`).
* Vários Chamados podem ser atribuídos a um único Técnico (`@ManyToOne`).

## 🛠️ Como Executar o Projeto

### Pré-requisitos
* Java JDK 17 ou superior instalado.
* PostgreSQL instalado e rodando na porta padrão (5432).
* Maven instalado (ou o wrapper embutido na sua IDE).

### Passo a Passo
1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/SEU_USUARIO/sistema-chamados.git](https://github.com/SEU_USUARIO/sistema-chamados.git)