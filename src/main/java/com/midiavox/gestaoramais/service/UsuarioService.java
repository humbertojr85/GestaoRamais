<body>
  <h1>Gestão de Ramais</h1>

  <div class="container">
    <!-- Coluna Esquerda: Seções 1, 2 e 3 -->
    <div class="coluna-esquerda">
      <section>
        <h2>1. Cadastrar Usuário</h2>
        <form id="usuarioForm">
          <input type="text" id="nome" placeholder="Nome" required />
          <input type="email" id="email" placeholder="Email" required />
          <button type="submit">Cadastrar</button>
        </form>
      </section>

      <section>
        <h2>2. Gerar Ramais Disponíveis</h2>
        <form id="gerarRamaisForm">
          <input type="number" id="inicioRamal" placeholder="Ramal Inicial" required />
          <input type="number" id="fimRamal" placeholder="Ramal Final" required />
          <button type="submit">Gerar Ramais</button>
        </form>
      </section>

      <section>
        <h2>3. Logar em Ramal</h2>
        <form id="logarForm">
          <select id="usuarioSelect" required></select>
          <select id="ramalSelect" required></select>
          <button type="submit">Logar</button>
        </form>
      </section>
    </div>

    <!-- Coluna Direita: Seções 4 e 5 -->
    <div class="coluna-direita">
      <section>
        <h2>4. Usuários Não Logados</h2>
        <ul id="usuariosNaoLogados"></ul>
      </section>

      <section>
        <h2>5. Ramais Não Utilizados</h2>
        <ul id="ramaisNaoUtilizados"></ul>
      </section>
    </div>
  </div>

  <!-- Seção 6: Tabela abaixo -->
  <section>
    <h2>6. Usuários Logados</h2>
    <table id="usuariosLogadosTable">
      <thead>
        <tr>
          <th>Nome</th>
          <th>Email</th>
          <th>Ramal</th>
          <th>Status</th>
          <th>Ação</th>
        </tr>
      </thead>
      <tbody></tbody>
    </table>
  </section>

  <script src="script.js"></script>
</body>
