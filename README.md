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
