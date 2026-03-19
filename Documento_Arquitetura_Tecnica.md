# Documento de Arquitetura Técnica

## Sistema de Helpdesk Educacional

---

## 1. Informações Gerais

* **Projeto:** Sistema de Gerenciamento de Chamados de TI
* **Plataforma:** Java SE (Aplicação Console)
* **Persistência:** Hibernate ORM (Puro) + PostgreSQL

---

## 2. Visão Geral e Objetivo

O objetivo da aplicação é gerenciar e rastrear problemas de infraestrutura e TI relatados por funcionários de uma instituição de ensino.

O sistema deve permitir:

* Abertura de chamados
* Atribuição de técnicos
* Atualização de status
* Registro completo do ciclo de vida dos chamados no banco de dados

---

## 3. Stack Tecnológica

| Tecnologia    | Descrição                    |
| ------------- | ---------------------------- |
| Java 17+      | Linguagem principal          |
| Maven         | Gerenciador de dependências  |
| PostgreSQL    | Banco de dados relacional    |
| Hibernate ORM | Mapeamento objeto-relacional |
| Git           | Controle de versão           |

### Controle de versão

Os commits devem seguir o padrão semântico:

```bash
feat: criacao da entidade Chamado  
fix: correcao do update no DAO  
```

---

## 4. Configuração do ORM e Banco de Dados

A comunicação com o banco será feita exclusivamente via Hibernate, sem o uso de frameworks adicionais.

### Arquivo de configuração

```
src/main/resources/META-INF/persistence.xml
```

### Propriedades obrigatórias

```xml
javax.persistence.jdbc.url
javax.persistence.jdbc.user
javax.persistence.jdbc.password

hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
hibernate.hbm2ddl.auto=update
hibernate.show_sql=true
```

---

## 5. Modelagem das Entidades

### 5.1 Funcionario

Responsável pela abertura do chamado.

* `id` (Long, @Id, @GeneratedValue)
* `nome` (String)
* `setor` (String)

---

### 5.2 Tecnico

Responsável pela resolução do chamado.

* `id` (Long, @Id, @GeneratedValue)
* `nome` (String)
* `especialidade` (String)

---

### 5.3 Chamado

Entidade principal do sistema.

* `id` (Long, @Id, @GeneratedValue)
* `titulo` (String)
* `descricao` (String)
* `status` (String: "ABERTO", "EM_ANDAMENTO", "RESOLVIDO")

### Relacionamentos

* `@ManyToOne` com Funcionario
* `@ManyToOne` com Tecnico

---

## 6. Arquitetura e Padrão de Projeto

O sistema utiliza o padrão DAO (Data Access Object) para isolar a lógica de acesso ao banco de dados.

### Estrutura de camadas

```
Main → Service → DAO → Banco de Dados
```

### Componentes

#### JPA Util

Classe responsável por:

* Inicializar o EntityManagerFactory (padrão Singleton)
* Fornecer instâncias de EntityManager

#### Classes DAO

* ChamadoDAO
* FuncionarioDAO
* TecnicoDAO

#### Controle de Transações

Cada operação deve seguir:

1. begin()
2. operação
3. commit()
4. rollback() em caso de erro

---

## 7. Fluxo CRUD da Aplicação

A aplicação será executada via menu no console (Scanner), permitindo as seguintes operações:

### Create

* Cadastrar um Funcionario
* Cadastrar um Tecnico
* Abrir um Chamado vinculado ao Funcionario

### Read

* Listar chamados com status "ABERTO"
* Exibir o nome do funcionário solicitante

### Update

* Buscar chamado por ID
* Atribuir um Tecnico
* Atualizar status para "EM_ANDAMENTO" ou "RESOLVIDO"

### Delete

* Excluir um chamado cadastrado incorretamente

---

## 8. Considerações Finais

Este projeto demonstra:

* Uso do Hibernate sem frameworks adicionais
* Aplicação do padrão DAO
* Manipulação de banco relacional com JPA
* Organização em camadas seguindo boas práticas
