const API_LOGIN_URL = "http://localhost:8080/login";
const API_CADASTRO_URL = "http://localhost:8080/cadastro";
const API_SERVICOS_URL = "http://localhost:8081";

document.addEventListener("DOMContentLoaded", () => {
  const loginForm = document.getElementById("loginForm");
  const cadastroForm = document.getElementById("cadastroForm");
  const transferForm = document.getElementById("transferForm");
  const depositForm = document.getElementById("depositForm");

  if (loginForm) {
    loginForm.addEventListener("submit", (e) => {
      e.preventDefault();
      const identificador = document.getElementById("accountNumber").value.trim();
      const password = document.getElementById("password").value.trim();

      fetch(API_LOGIN_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          identificador: identificador,
          senha: password
        })
      })
        .then((res) => {
          if (!res.ok) throw new Error("Erro no login");
          return res.json();
        })
        .then((data) => {
          window.location.href = "pages/home.html?account=" + data.conta;
        })
        .catch(() => alert("Conta ou senha inválidos!"));
    });
  }

  if (cadastroForm) {
    cadastroForm.addEventListener("submit", (e) => {
      e.preventDefault();
      const nome = document.getElementById("name").value;
      const endereco = document.getElementById("address").value;
      const cpf = document.getElementById("cpf").value;
      const senha = document.getElementById("newPassword").value;

      fetch(API_CADASTRO_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ nome, endereco, cpf, senha })
      })
        .then((res) => {
          if (!res.ok) throw new Error("Erro no cadastro");
          return res.json();
        })
        .then((data) => {
          alert("Cadastro realizado com sucesso! Sua conta é: " + data.conta);
          window.location.href = "../index.html";
        })
        .catch(() => alert("Erro ao cadastrar usuário!"));
    });
  }

  if (transferForm) {
    transferForm.addEventListener("submit", (e) => {
      e.preventDefault();
      const origem = new URLSearchParams(window.location.search).get("account");
      const destino = document.getElementById("destAccount").value.trim();
      const valor = parseFloat(document.getElementById("transferValue").value);

      fetch(`${API_SERVICOS_URL}/transferir`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ origem, destino, valor })
      })
        .then((res) => {
          if (!res.ok) throw new Error("Erro na transferência");
          return res.json();
        })
        .then(() => {
          alert("Transferência realizada com sucesso!");
          location.reload();
        })
        .catch(() => alert("Erro ao transferir!"));
    });
  }

  if (depositForm) {
    depositForm.addEventListener("submit", (e) => {
      e.preventDefault();
      const conta = new URLSearchParams(window.location.search).get("account");
      const valor = parseFloat(document.getElementById("depositValue").value);

      fetch(`${API_SERVICOS_URL}/depositar`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ conta, valor })
      })
        .then((res) => {
          if (!res.ok) throw new Error("Erro no depósito");
          return res.json();
        })
        .then(() => {
          alert("Depósito realizado com sucesso!");
          location.reload();
        })
        .catch(() => alert("Erro ao depositar!"));
    });
  }

  const accountParam = new URLSearchParams(window.location.search).get("account");
  if (accountParam) {
    fetch(`${API_SERVICOS_URL}/usuario?conta=${accountParam}`)
      .then(res => res.json())
      .then(user => {
        document.getElementById("accountDisplay").innerText = user.conta;
        document.getElementById("balanceDisplay").innerText = user.saldo.toFixed(2);
      })
      .catch(() => alert("Erro ao carregar informações da conta"));
  }
});
