const api = "http://localhost:8080";

document.addEventListener("DOMContentLoaded", () => {
  carregarUsuarios();
  carregarRamaisDisponiveis();
});

// Cadastrar usuário
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

// Marcar usuários logados
function carregarUsuarios() {
fetch('http://localhost:8080/usuarios')
    .then(response => response.json())
    .then(data => {
        const select = document.getElementById('usuarios');
        select.innerHTML = ''; // limpa a lista

        data.forEach(usuario => {
            const option = document.createElement('option');
            option.value = usuario.id;

            // Verifica se o usuário está logado (tem ramal associado)
            if (usuario.ramal) {
                option.innerHTML = '🟢 ' + usuario.nome;
            } else {
                option.innerHTML = usuario.nome;
            }

            select.appendChild(option);
        });
    })
    .catch(error => {
        console.error('Erro ao carregar usuários:', error);
    });
}


// Gerar ramais
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
    alert("Ramais gerados com sucesso!");
    carregarRamaisDisponiveis();
  } else {
    alert("Erro ao gerar ramais.");
  }
});

// Excluir ramais por faixa
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

// Logar usuário em ramal
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
