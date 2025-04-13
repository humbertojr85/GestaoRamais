const api = "http://localhost:8080";

document.addEventListener("DOMContentLoaded", () => {
  carregarUsuarios();
  carregarRamaisDisponiveis();
});


// ====== [1] Cadastrar novo usuário ======
document.getElementById("usuarioForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const nome = document.getElementById("nome").value;
  const email = document.getElementById("email").value;

  const res = await fetch(`${api}/usuarios`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ nome, email }),
  });

  if (res.ok) {
    alert("Usuário cadastrado com sucesso!");
    carregarUsuarios();
    document.getElementById("usuarioForm").reset();
  } else {
    alert("Erro ao cadastrar usuário.");
  }
});


// ====== [2] Gerar faixa de ramais ======
document.getElementById("gerarRamaisForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const inicio = document.getElementById("inicioRamal").value;
  const fim = document.getElementById("fimRamal").value;

  const res = await fetch(`${api}/ramais/gerar`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ inicio, fim }),
  });

  if (res.ok) {
    const mensagem = await res.text(); // Captura a mensagem do back-end
    alert(mensagem);                   // Exibe a mensagem real
    carregarRamaisDisponiveis();       // Recarrega a lista
  } else {
    alert("Erro ao gerar ramais.");
  }
});


// ====== [3] Excluir faixa de ramais ======
document.getElementById("btnExcluirRamais").addEventListener("click", async () => {
  const inicio = document.getElementById("inicioRamal").value;
  const fim = document.getElementById("fimRamal").value;

  if (!inicio || !fim) {
    alert("Preencha o intervalo de ramais para excluir.");
    return;
  }

  if (confirm(`Deseja realmente excluir os ramais de ${inicio} até ${fim}?`)) {
    const res = await fetch(`${api}/ramais/excluir-faixa`, {
      method: "DELETE",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ inicio, fim }),
    });

    if (res.ok) {
      alert("Ramais excluídos com sucesso!");
      carregarRamaisDisponiveis();
    } else {
      alert("Erro ao excluir ramais.");
    }
  }
});


// ====== [4] Logar usuário em ramal ======
document.getElementById("logarForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const idUsuario = document.getElementById("usuarioSelect").value;
  const idRamal = document.getElementById("ramalSelect").value;

  const res = await fetch(`${api}/ramais/logar`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ idUsuario, idRamal }),
  });

  if (res.ok) {
    alert("Usuário logado com sucesso!");
    carregarUsuarios();
    carregarRamaisDisponiveis();
  } else {
    alert("Erro ao logar usuário.");
  }
});


// ====== [5] Deslogar usuário de ramal ======
async function fazerLogoff(idRamal) {
  const res = await fetch(`${api}/ramais/logoff`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ idRamal }),
  });

  if (res.ok) {
    alert("Logoff realizado!");
    carregarUsuarios();
    carregarRamaisDisponiveis();
  } else {
    alert("Erro ao fazer logoff.");
  }
}


// ====== [6] Carregar usuários e preencher tabela + select ======
async function carregarUsuarios() {
  const res = await fetch(`${api}/usuarios`);
  const usuarios = await res.json();

  const select = document.getElementById("usuarioSelect");
  const table = document.querySelector("#usuariosLogadosTable tbody");

  select.innerHTML = "";
  table.innerHTML = "";

  usuarios.forEach((usuario) => {
    if (!usuario.ramal) {
      const option = document.createElement("option");
      option.value = usuario.id;
      option.text = usuario.nome;
      select.appendChild(option);
    }

    if (usuario.ramal) {
      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td>${usuario.nome}</td>
        <td>${usuario.email}</td>
        <td>${usuario.ramal.numero}</td>
        <td><button class="btn-logado" disabled>Logado</button></td>
        <td><button class="btn-logoff" onclick="fazerLogoff('${usuario.ramal.id}')">Logoff</button></td>
      `;
      table.appendChild(tr);
    }
  });

  listarUsuariosNaoLogados(usuarios);
}


// ====== [7] Carregar ramais disponíveis e preencher select + lista ======
async function carregarRamaisDisponiveis() {
  const res = await fetch(`${api}/ramais/disponiveis`);
  const ramais = await res.json();

  const select = document.getElementById("ramalSelect");
  select.innerHTML = "";

  ramais.forEach((ramal) => {
    const option = document.createElement("option");
    option.value = ramal.id;
    option.text = ramal.numero;
    select.appendChild(option);
  });

  listarRamaisDisponiveisParaExcluir(ramais);
}


// ====== [8] Listar usuários não logados (com botão excluir) ======
function listarUsuariosNaoLogados(usuarios) {
  const lista = document.getElementById("usuariosNaoLogados");
  lista.innerHTML = "";

  usuarios.forEach((usuario) => {
    if (!usuario.ramal) {
      const li = document.createElement("li");
      li.innerHTML = `
        ${usuario.nome} (${usuario.email})
        <button onclick="excluirUsuario('${usuario.id}')">Excluir</button>
      `;
      lista.appendChild(li);
    }
  });
}


// ====== [9] Listar ramais disponíveis (com botão excluir) ======
function listarRamaisDisponiveisParaExcluir(ramais) {
  const lista = document.getElementById("listaRamaisDisponiveis");
  lista.innerHTML = "";

  ramais.forEach((ramal) => {
    const li = document.createElement("li");
    li.innerHTML = `
      Ramal ${ramal.numero}
      <button onclick="excluirRamal('${ramal.id}')">Excluir</button>
    `;
    lista.appendChild(li);
  });
}


// ====== [10] Excluir usuário ======
async function excluirUsuario(id) {
  if (confirm("Deseja realmente excluir este usuário?")) {
    const res = await fetch(`${api}/usuarios/${id}`, { method: "DELETE" });
    if (res.ok) {
      alert("Usuário excluído com sucesso!");
      carregarUsuarios();
    } else {
      alert("Erro ao excluir usuário.");
    }
  }
}


// ====== [11] Excluir ramal ======
async function excluirRamal(id) {
  if (confirm("Deseja realmente excluir este ramal?")) {
    const res = await fetch(`${api}/ramais/${id}`, { method: "DELETE" });
    if (res.ok) {
      alert("Ramal excluído com sucesso!");
      carregarRamaisDisponiveis();
    } else {
      alert("Erro ao excluir ramal.");
    }
  }
}

// ====== [12] Filtro Avançado ======
document.getElementById("pesquisa").addEventListener("input", filtrarResultados);

function filtrarResultados() {
  const termo = document.getElementById("pesquisa").value.toLowerCase().trim();

  const listaUsuarios = document.querySelectorAll("#usuariosNaoLogados li");
  const listaRamais = document.querySelectorAll("#listaRamaisDisponiveis li");
  const linhasTabela = document.querySelectorAll("#usuariosLogadosTable tbody tr");

  const faixaRegex = /^(\d+)\s*(?:-|a)\s*(\d+)$/i;
  const faixaMatch = termo.match(faixaRegex);

  let numerosPermitidos = [];

  if (faixaMatch) {
    const inicio = parseInt(faixaMatch[1]);
    const fim = parseInt(faixaMatch[2]);

    if (!isNaN(inicio) && !isNaN(fim) && fim >= inicio) {
      for (let i = inicio; i <= fim; i++) {
        numerosPermitidos.push(i.toString());
      }
    }
  }

  const filtro = (texto) => {
    texto = texto.toLowerCase();
    if (faixaMatch) {
      return numerosPermitidos.some(num => texto.includes(num));
    } else {
      return texto.includes(termo);
    }
  };

  // Usuários não logados
  listaUsuarios.forEach(li => {
    const texto = li.textContent.toLowerCase();
    li.style.display = filtro(texto) ? "flex" : "none";
  });

  // Ramais disponíveis
  listaRamais.forEach(li => {
    const texto = li.textContent.toLowerCase();
    li.style.display = filtro(texto) ? "flex" : "none";
  });

  // Tabela de usuários logados
  linhasTabela.forEach(tr => {
    const texto = tr.textContent.toLowerCase();
    tr.style.display = filtro(texto) ? "" : "none";
  });
}
