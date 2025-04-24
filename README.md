# 📞 Sistema de Gestão de Ramais

Projeto desenvolvido para o desafio de estágio. Permite cadastrar usuários, gerar ramais, e associar cada usuário a um ramal, com controle de login e logoff.

## ⚙️ Tecnologias Utilizadas

- Java 17 + Spring Boot 3
- MySQL via Docker
- HTML5, CSS3, JavaScript (vanilla)
- REST API com JSON
- Lombok + JPA/Hibernate

## 🔧 Funcionalidades

- 📌 Cadastro de usuários e emails únicos
- 🔢 Geração automática de faixa de ramais
- 🔄 Login e logoff em ramais
- 🧾 Listagem de usuários logados e não logados
- 🧹 Exclusão de usuários e ramais
- 🔗 Integração entre back-end e front-end via API REST

## 🚀 Como executar

1. Suba o MySQL com Docker:
```bash
docker-compose up -d

# 📞 Gestão de Ramais - Aplicativo Desktop

Aplicação desktop desenvolvida em **JavaFX**, com consumo de API REST via **Retrofit2**, para a gestão de ramais de usuários em uma empresa. Projeto em evolução 🚀

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **JavaFX 17.0.2**
- **Retrofit2 (2.9.0)**
- **Gson Converter**
- **Maven**

---

## ⚙️ Funcionalidades

- 👤 **Cadastro de Usuários**
- ☎️ **Geração de Ramais** (faixa entre dois números)
- 🔗 **Login de usuário em ramal disponível**
- 🔓 **Logoff de ramal**
- ❌ **Exclusão de faixa de ramais**
- 🧾 **Listagem reativa de:**
  - Usuários logados
  - Usuários não logados
  - Ramais disponíveis

---

## 🖥️ Interface

A interface foi desenvolvida para ser intuitiva e semelhante à versão web:

- Três seções horizontais iniciais: Cadastro, Geração de Ramais e Login
- Listas laterais de usuários não logados e ramais disponíveis
- Tabela de usuários logados com botão de logoff
- Campo de busca para filtrar usuários e ramais

---

## 📦 Como Rodar o Projeto

1. Clone este repositório:
   ```bash
   git clone https://github.com/humbertojr85/GestaoRamais.git
   cd gestaoramaisdesktop
   ```

2. Certifique-se de que o **servidor da API REST (versão web)** esteja rodando (porta `8080` por padrão).

3. Execute o projeto via Maven:
   ```bash
   mvn javafx:run
   ```

---

## 🧪 Pré-requisitos

- JDK 17+
- Maven 3.8+
- API REST da Gestão de Ramais já rodando (projeto web)

---

## 👨‍💻 Desenvolvido por

**Humberto Alcântara**  
Liderança | Desenvolvimento | Integração Web & Desktop

---

## 📸 Captura de Tela

> _(Adicione alguns prints do sistema rodando)_

---
