# Sistema Helpdesk

Projeto de exemplo usando Hibernate (JPA) com Maven — aplicação simples de helpdesk.

## Objetivo

Demonstrar o uso de Hibernate + JPA em uma aplicação Java (sem Spring Data) para gerenciar chamados de suporte, técnicos e usuários. O projeto serve como base didática para: modelagem de entidades, configuração do ORM, exemplos de CRUD e execução com Maven/IDE.

## Tecnologias

- Java 24
- Maven
- Hibernate ORM 6 (com JPA - Jakarta Persistence)
- PostgreSQL (driver `org.postgresql:postgresql`)

## Estrutura do projeto

- `src/main/java/com/helpdesk/model` — entidades: `Usuario`, `Tecnico`, `Chamado`
- `src/main/java/com/helpdesk/dao` — DAOs (ex.: `UsuarioDAO`)
- `src/main/java/com/helpdesk/util` — utilitários (ex.: `HibernateUtil`)
- `src/main/resources/hibernate.cfg.xml` — configuração do Hibernate
- `pom.xml` — dependências e build

## Objetivo da aplicação

Gerenciar chamados de suporte associados a usuários e técnicos. Cada `Chamado` referencia um `Usuario` (quem abriu) e um `Tecnico` (responsável), exemplificando relações `@ManyToOne`.

## Configuração do ORM e do banco

A configuração principal está em `src/main/resources/hibernate.cfg.xml`. Ajuste as propriedades de conexão conforme seu ambiente PostgreSQL (URL, usuário, senha).

Propriedade importante para desenvolvimento:

- `hibernate.hbm2ddl.auto=update` — gera/atualiza tabelas automaticamente (não recomendado em produção).

Exemplo mínimo de banco (Postgres):

1. Criar database:

```sql
CREATE DATABASE helpdesk;
```
2. Ajustar `hibernate.cfg.xml` caso usuário/senha ou porta sejam diferentes.

## Modelagem das entidades

Resumo das entidades implementadas:

- `Usuario`:
  - `id` (PK, auto)
  - `nome`
  - `email`

- `Tecnico`:
  - `id` (PK, auto)
  - `nome`
  - `especialidade`

- `Chamado`:
  - `id` (PK, auto)
  - `descricao`
  - `status`
  - `usuario` (`@ManyToOne` para `Usuario`)
  - `tecnico` (`@ManyToOne` para `Tecnico`)

As anotações JPA estão em `src/main/java/com/helpdesk/model`.

## Explicação sucinta dos demais códigos

- `HibernateUtil` (`com.helpdesk.util.HibernateUtil`) — inicializa e fornece o `SessionFactory` via `Configuration().configure("hibernate.cfg.xml")`.
- `UsuarioDAO` (`com.helpdesk.dao.UsuarioDAO`) — exemplo simples de persistência com `Session` e `Transaction`. Atualmente possui método `salvar(Usuario)`; recomenda-se adicionar métodos `buscarPorId`, `listarTodos`, `atualizar`, `deletar` para CRUD completo.
- `Main` (`com.helpdesk.Main`) — pequeno runner que cria um `Usuario` e chama `UsuarioDAO.salvar(...)`.

## Exemplo de CRUD (exemplos de uso)

- Criar usuário (existe no projeto):

```java
Usuario usuario = new Usuario();
usuario.setNome("Anna");
usuario.setEmail("anna@email.com");

UsuarioDAO dao = new UsuarioDAO();
dao.salvar(usuario);
```

- Exemplo de leitura com Hibernate `Session` (consultar por id):

```java
try (Session session = HibernateUtil.getSessionFactory().openSession()) {
    Usuario u = session.find(Usuario.class, 1L);
    System.out.println(u.getNome());
}
```

- Exemplo de atualizar:

```java
try (Session session = HibernateUtil.getSessionFactory().openSession()) {
    Transaction tx = session.beginTransaction();
    Usuario u = session.find(Usuario.class, 1L);
    u.setEmail("novo@email.com");
    session.merge(u);
    tx.commit();
}
```

- Exemplo de deletar:

```java
try (Session session = HibernateUtil.getSessionFactory().openSession()) {
    Transaction tx = session.beginTransaction();
    Usuario u = session.find(Usuario.class, 1L);
    if (u != null) session.remove(u);
    tx.commit();
}
```

- Criar `Chamado` e associar `Usuario` e `Tecnico`:

```java
// carregar ou criar usuario e tecnico
Usuario usuario = /* carregar */;
Tecnico tecnico = /* carregar */;

Chamado chamado = new Chamado();
chamado.setDescricao("Problema no login");
chamado.setStatus("ABERTO");
chamado.setUsuario(usuario);
chamado.setTecnico(tecnico);

try (Session session = HibernateUtil.getSessionFactory().openSession()) {
    Transaction tx = session.beginTransaction();
    session.persist(chamado);
    tx.commit();
}
```

Observação: os exemplos acima usam APIs padrão do Hibernate/JPA; ajuste para usar os DAOs se preferir encapsular acesso.

## Como compilar e executar

Recomenda-se executar via sua IDE (IntelliJ, Eclipse) que carregue `target/classes` e as dependências do Maven.

Pelo Maven (se tiver o plugin `exec` configurado, ou adicione temporariamente):

```bash
mvn compile exec:java -Dexec.mainClass="com.helpdesk.Main"
```

Ou apenas compilar e executar via IDE:

```bash
mvn package
# executar Main pela IDE
```

## Boas práticas e notas finais

- `hibernate.hbm2ddl.auto=update` é conveniente em desenvolvimento; em produção use `validate` ou gerencie migrações (Flyway/Liquibase).
- Trate transações com `try/catch` para garantir `rollback()` em falhas.
- Não use Spring Data neste projeto conforme requisito; o acesso é feito via Hibernate puro/JPA.
- Considere adicionar testes unitários e DAOs completos para todas as entidades (`UsuarioDAO`, `TecnicoDAO`, `ChamadoDAO`).

## Licença

Coloque aqui a licença desejada (ex.: MIT) antes de publicar no Git.

---

Arquivo criado automaticamente para facilitar envio ao GitHub.
