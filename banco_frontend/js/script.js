document.addEventListener("DOMContentLoaded", () => {
  const loginForm = document.getElementById("loginForm");
  const messageDiv = document.getElementById("loginMessage");

  loginForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const cpf = document.getElementById("cpf").value.trim();
    const senha = document.getElementById("password").value.trim();

    try {
      const response = await fetch(`http://localhost:8080/login?cpf=${cpf}&senha=${senha}`, {
        method: "POST"
      });

      const text = await response.text();
      if (response.ok) {
        messageDiv.innerHTML = `<span class='text-success'>${text}</span>`;
        setTimeout(() => {
          window.location.href = "pages/home.html?cpf=" + cpf;
        }, 1000);
      } else {
        messageDiv.innerHTML = `<span class='text-danger'>${text}</span>`;
      }
    } catch (error) {
      messageDiv.innerHTML = `<span class='text-danger'>Erro ao conectar com o servidor.</span>`;
    }
  });
});
