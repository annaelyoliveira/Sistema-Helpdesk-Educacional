## 🚀 Guia de Execução: Sistema Helpdesk Educacional

Siga os passos abaixo para configurar o ambiente e rodar a aplicação em sua máquina.

### 1. Pré-requisitos
Certifique-se de ter instalado:
* **Java JDK 21**
* **Maven 3.9+**
* **PostgreSQL 15+**
* **Postman** (para testes da API)

---

### 2. Preparando o Banco de Dados (PostgreSQL)
A aplicação utiliza o **Hibernate** para criar as tabelas automaticamente, mas o "container" do banco precisa existir primeiro.

1. Abra o **pgAdmin 4**.
2. Clique com o botão direito em *Databases* > *Create* > **Database...**
3. No campo "Database", digite exatamente: `helpdesk`
4. Clique em **Save**.
5. **Importante:** Verifique se as credenciais no arquivo `src/main/resources/META-INF/persistence.xml` batem com as suas (usuário `postgres` e sua senha).

---

### 3. Clonando e Rodando o Projeto
Abra o terminal na pasta onde deseja salvar o projeto:

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git
   cd Sistema-Helpdesk-Educacional
   ```
2. **Limpe e compile o projeto:**
   ```bash
   mvn clean install
   ```
3. **Inicie a aplicação:**
   ```bash
   mvn spring-boot:run
   ```

---

### 4. Configurando o Postman (Importando a Coleção)
Para facilitar, eu exportei todas as rotas configuradas. Siga estes passos para importar:

1. No Postman, clique no botão **Import** (canto superior esquerdo).
2. Arraste o arquivo `Helpdesk.postman_collection.json` (que está na raiz do projeto) para dentro do Postman.
3. Clique em **Import**.
4. Agora, todas as pastas (Usuários, Técnicos, Chamados) aparecerão na sua barra lateral.

---

### 5. Fluxo de Teste (O que colocar em cada requisição)

Se você preferir criar as rotas manualmente, aqui estão os exemplos de **Body (JSON)** que você deve usar (sempre selecione `Body` > `raw` > `JSON`):

| Ação | Método | URL | Body (Exemplo) |
| :--- | :--- | :--- | :--- |
| **Cadastrar Usuário** | `POST` | `/usuarios` | `{"email": "aluno@email.com", "senha": "123", "role": "ALUNO"}` |
| **Cadastrar Técnico** | `POST` | `/tecnicos` | `{"email": "tech@ti.com", "senha": "123", "espcialidade": "Redes"}` |
| **Abrir Chamado** | `POST` | `/chamados` | `{"descricao": "PC não liga", "status": "ABERTO", "usuario": {"id": 1}}` |
| **Atribuir Técnico** | `PUT` | `/chamados/1/atribuir/1`| *(Sem Body)* |
| **Atualizar Chamado**| `PUT` | `/chamados/1` | `{"descricao": "Resolvido", "status": "FINALIZADO", "usuario": {"id": 1}, "tecnico": {"id": 1}}` |

> **Nota importante:** Ao atualizar um chamado via `PUT`, lembre-se de enviar os IDs do usuário e do técnico no JSON (como no exemplo acima) para manter os vínculos ativos no banco de dados.

---

### 🛠️ Solução de Problemas Comuns
* **Erro "Port 8080 already in use":** Outra aplicação está rodando na mesma porta. Feche outras IDEs ou mude a porta no arquivo `application.properties`.
* **Tabelas não aparecem no pgAdmin:** Clique com o botão direito na pasta *Tables* dentro do banco `helpdesk` e selecione **Refresh**.

---

**Dica para você:** Se você ainda não exportou sua coleção do Postman, basta clicar nos `...` da coleção no Postman > **Export** > Escolha **v2.1** e salve na pasta do seu projeto antes de dar o `git push` final!