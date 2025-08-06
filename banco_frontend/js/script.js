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
      const cpf = document.getElementById("cpf").value.trim();
      const conta = document.getElementById("accountNumber").value.trim();
      const password = document.getElementById("password").value.trim();

      if (!cpf || cpf.length !== 11 || isNaN(cpf)) {
        alert("CPF inválido. Digite os 11 números.");
        return;
      }
      if (!conta || isNaN(conta)) {
        alert("Número da conta inválido.");
        return;
      }
      if (!password || password.length < 6) {
        alert("Senha inválida. Mínimo de 6 caracteres.");
        return;
      }

      const payload = { cpf, conta, senha: password };
      console.log("Login payload:", payload);

      fetch(API_LOGIN_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
      })
        .then(async (res) => {
          if (!res.ok) {
            const errorText = await res.text();
            console.error("Erro no login:", errorText);
            throw new Error(errorText || "CPF, conta ou senha inválidos.");
          }
          return res.json();
        })
        .then((data) => {
          window.location.href = "pages/home.html?account=" + data.conta;
        })
        .catch((err) => alert("Erro ao entrar:\n" + err.message));
    });
  }

  if (cadastroForm) {
    cadastroForm.addEventListener("submit", (e) => {
      e.preventDefault();

      const nome = document.getElementById("name").value.trim();
      const cpf = document.getElementById("cpf").value.replace(/\D/g, "");
      const senha = document.getElementById("newPassword").value;
      const confirmarSenha = document.getElementById("confirmPassword").value;
      const email = document.getElementById("email").value.trim();
      const telefone = document.getElementById("telefone").value.replace(/\D/g, "");
      const cep = document.getElementById("cep").value.replace(/\D/g, "");
      const logradouro = document.getElementById("logradouro").value.trim();
      const bairro = document.getElementById("bairro").value.trim();
      const cidade = document.getElementById("cidade").value.trim();
      const uf = document.getElementById("uf").value.trim();
      const numero = document.getElementById("numero").value.trim();
      const complemento = document.getElementById("complemento").value.trim();

      if (!nome) return alert("Preencha o nome.");
      if (cpf.length !== 11) return alert("CPF inválido.");
      if (senha.length < 6) return alert("Senha muito curta.");
      if (senha !== confirmarSenha) return alert("As senhas não coincidem.");
      if (!email.includes("@")) return alert("E-mail inválido.");
      if (telefone.length < 10) return alert("Telefone incompleto.");
      if (cep.length !== 8) return alert("CEP inválido.");
      if (!logradouro || !bairro || !cidade || !uf || !numero) {
        return alert("Preencha todos os campos de endereço.");
      }

      const conta = Math.floor(100000 + Math.random() * 900000);

      const payload = {
        nome,
        cpf,
        senha,
        email,
        telefone,
        conta,
        endereco: {
          cep,
          logradouro,
          bairro,
          cidade,
          uf,
          numero,
          complemento
        }
      };

      console.log("Cadastro payload:", payload);

      fetch(API_CADASTRO_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
      })
        .then(async (res) => {
          if (!res.ok) {
            const errorText = await res.text();
            console.error("Erro no cadastro:", errorText);
            throw new Error(errorText || "Erro desconhecido ao cadastrar.");
          }
          return res.json();
        })
        .then((data) => {
          alert("Cadastro realizado com sucesso! Sua conta é: " + data.conta);
          window.location.href = "../index.html";
        })
        .catch((err) => alert("Erro ao cadastrar usuário:\n" + err.message));
    });
  }

  if (transferForm) {
    transferForm.addEventListener("submit", (e) => {
      e.preventDefault();
      const origem = new URLSearchParams(window.location.search).get("account");
      const destino = document.getElementById("destAccount").value.trim();
      const valor = parseFloat(document.getElementById("transferValue").value);

      if (!destino || isNaN(valor) || valor <= 0) {
        return alert("Preencha uma conta válida e valor positivo.");
      }

      const payload = { origem, destino, valor };
      console.log("Transferência payload:", payload);

      fetch(`${API_SERVICOS_URL}/transferir`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
      })
        .then(async (res) => {
          if (!res.ok) {
            const errorText = await res.text();
            console.error("Erro na transferência:", errorText);
            throw new Error(errorText || "Erro desconhecido ao transferir.");
          }
          return res.json();
        })
        .then(() => {
          alert("Transferência realizada com sucesso!");
          location.reload();
        })
        .catch((err) => alert("Erro ao transferir:\n" + err.message));
    });
  }

  if (depositForm) {
    depositForm.addEventListener("submit", (e) => {
      e.preventDefault();
      const conta = new URLSearchParams(window.location.search).get("account");
      const valor = parseFloat(document.getElementById("depositValue").value);

      if (isNaN(valor) || valor <= 0) {
        return alert("Digite um valor de depósito válido.");
      }

      const payload = { conta, valor };
      console.log("Depósito payload:", payload);

      fetch(`${API_SERVICOS_URL}/depositar`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
      })
        .then(async (res) => {
          if (!res.ok) {
            const errorText = await res.text();
            console.error("Erro no depósito:", errorText);
            throw new Error(errorText || "Erro desconhecido ao depositar.");
          }
          return res.json();
        })
        .then(() => {
          alert("Depósito realizado com sucesso!");
          location.reload();
        })
        .catch((err) => alert("Erro ao depositar:\n" + err.message));
    });
  }

  const accountParam = new URLSearchParams(window.location.search).get("account");
  if (accountParam) {
    fetch(`${API_SERVICOS_URL}/usuario?conta=${accountParam}`)
      .then(res => res.json())
      .then(user => {
        console.log("Dados da conta carregados:", user);
        document.getElementById("accountDisplay").innerText = user.conta;
        document.getElementById("balanceDisplay").innerText = user.saldo.toFixed(2);
      })
      .catch((err) => {
        console.error("Erro ao carregar dados da conta:", err);
        alert("Erro ao carregar informações da conta");
      });
  }
});
