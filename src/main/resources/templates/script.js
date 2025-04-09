const baseUrl = "http://localhost:8080";

async function cadastrarUsuario() {
  const nome = document.getElementById("nome").value;
  const email = document.getElementById("email").value;

  const response = await fetch(`${baseUrl}/usuarios`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ nome, email }),
  });

  if (response.ok) {
    alert("Usuário cadastrado com sucesso!");
    carregarUsuarios();
  } else {
    alert("Erro ao cadastrar usuário.");
  }
}

async function gerarRamais() {
  const inicio = parseInt(document.getElementById("inicio").value);
  const fim = parseInt(document.getElementById("fim").value);

  await fetch(`${baseUrl}/ramais/gerar`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ inicio, fim }),
  });

  alert("Ramais gerados com sucesso!");
  carregarRamaisDisponiveis();
}

async function carregarRamaisDisponiveis() {
  const response = await fetch(`${baseUrl}/ramais/disponiveis`);
  const ramais = await response.json();

  const select = document.getElementById("ramaisDisponiveis");
  select.innerHTML = "";

  ramais.forEach((ramal) => {
    const option = document.createElement("option");
    option.value = ramal.id;
    option.textContent = ramal.numero;
    select.appendChild(option);
  });
}

async function carregarUsuarios() {
  const response = await fetch(`${baseUrl}/usuarios`);
  const usuarios = await response.json();

  const selectUsuarios = document.getElementById("usuarios");
  const selectLogados = document.getElementById("usuariosLogados");

  selectUsuarios.innerHTML = "";
  selectLogados.innerHTML = "";

  usuarios.forEach((usuario) => {
    const option = document.createElement("option");
    option.value = usuario.id;
    option.textContent = usuario.nome;
    selectUsuarios.appendChild(option);
  });

  // Atualiza lista de usuários logados
  const logados = await fetch(`${baseUrl}/ramais/usuarios-logados`);
  const usuariosLogados = await logados.json();

  usuariosLogados.forEach((usuario) => {
    const option = document.createElement("option");
    option.value = usuario.ramal.id;
    option.textContent = `${usuario.nome} - Ramal: ${usuario.ramal.numero}`;
    selectLogados.appendChild(option);
  });
}

async function logarRamal() {
  const idUsuario = document.getElementById("usuarios").value;
  const idRamal = document.getElementById("ramaisDisponiveis").value;

  await fetch(`${baseUrl}/ramais/logar`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ idUsuario, idRamal }),
  });

  alert("Usuário logado no ramal!");
  carregarUsuarios();
  carregarRamaisDisponiveis();
}

async function logoffRamal() {
  const idRamal = document.getElementById("usuariosLogados").value;

  await fetch(`${baseUrl}/ramais/logoff`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ idRamal }),
  });

  alert("Ramal liberado!");
  carregarUsuarios();
  carregarRamaisDisponiveis();
}

// Inicializa tudo ao carregar a página
window.onload = () => {
  carregarUsuarios();
  carregarRamaisDisponiveis();
};
