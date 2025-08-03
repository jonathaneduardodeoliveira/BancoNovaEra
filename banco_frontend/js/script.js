const API_URL = "https://sua-api-aqui.com";

let usuarios = JSON.parse(localStorage.getItem("usuarios")) || [];

document.addEventListener("DOMContentLoaded", () => {
  const loginForm = document.getElementById("loginForm");
  const cadastroForm = document.getElementById("cadastroForm");
  const transferForm = document.getElementById("transferForm");
  const depositForm = document.getElementById("depositForm");

  if (loginForm) {
    loginForm.addEventListener("submit", (e) => {
      e.preventDefault();
      const account = document.getElementById("accountNumber").value.trim();
      const password = document.getElementById("password").value.trim();

      const user = usuarios.find(
        (u) => u.conta === account && u.senha === password
      );
      if (user) {
        window.location.href = "pages/home.html?account=" + account;
      } else {
        alert("Conta ou senha inválidos!");
      }
    });
  }

  if (cadastroForm) {
    cadastroForm.addEventListener("submit", (e) => {
      e.preventDefault();
      const nome = document.getElementById("name").value;
      const endereco = document.getElementById("address").value;
      const cpf = document.getElementById("cpf").value;
      const senha = document.getElementById("newPassword").value;

      let conta;
      do {
        conta = Math.floor(100 + Math.random() * 900).toString();
      } while (usuarios.some((u) => u.conta === conta));

      usuarios.push({ nome, endereco, cpf, senha, conta, saldo: 1000 });
      localStorage.setItem("usuarios", JSON.stringify(usuarios));

      alert("Cadastro realizado com sucesso! Seu número de conta é: " + conta);
      window.location.href = "../index.html";
    });
  }

  if (transferForm) {
    transferForm.addEventListener("submit", (e) => {
      e.preventDefault();
      const origem = new URLSearchParams(window.location.search).get("account");
      const destino = document.getElementById("destAccount").value.trim();
      const valor = parseFloat(document.getElementById("transferValue").value);

      let remetente = usuarios.find((u) => u.conta === origem);
      let recebedor = usuarios.find((u) => u.conta === destino);

      if (!recebedor) return alert("Conta de destino inexistente!");
      if (valor <= 0) return alert("Valor inválido!");
      if (remetente.saldo < valor) return alert("Saldo insuficiente!");

      remetente.saldo -= valor;
      recebedor.saldo += valor;

      localStorage.setItem("usuarios", JSON.stringify(usuarios));
      alert("Transferência realizada com sucesso!");
      location.reload();
    });
  }

  if (depositForm) {
    depositForm.addEventListener("submit", (e) => {
      e.preventDefault();
      const conta = new URLSearchParams(window.location.search).get("account");
      const valor = parseFloat(document.getElementById("depositValue").value);

      let user = usuarios.find((u) => u.conta === conta);
      if (!user) return alert("Conta não encontrada!");
      if (valor <= 0) return alert("Valor inválido!");

      user.saldo += valor;
      localStorage.setItem("usuarios", JSON.stringify(usuarios));
      alert("Depósito realizado com sucesso!");
      location.reload();
    });
  }

  const accountParam = new URLSearchParams(window.location.search).get(
    "account"
  );
  if (accountParam) {
    const user = usuarios.find((u) => u.conta === accountParam);
    if (user) {
      document.getElementById("accountDisplay").innerText = user.conta;
      document.getElementById("balanceDisplay").innerText =
        user.saldo.toFixed(2);
    }
  }
});
