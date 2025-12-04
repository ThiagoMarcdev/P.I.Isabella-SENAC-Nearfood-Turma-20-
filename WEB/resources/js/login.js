document.getElementById("formLogin").addEventListener("submit", async function (event) {
    event.preventDefault();

    const email = document.getElementById("email").value.trim();
    const senha = document.getElementById("password").value.trim();
    const mensagem = document.getElementById("mensagem");

    if (!email || !senha) {
        mensagem.textContent = "Preencha todos os campos!";
        mensagem.style.color = "red";
        return;
    }

    const dados = {
        email: email,
        password: senha
    };

    try {
        const response = await fetch("http://localhost:8000/api/usuarios/login-api/", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(dados)
        });

        const data = await response.json();

        if (!response.ok) {
            mensagem.textContent = data.detail || "E-mail ou senha inválidos!";
            mensagem.style.color = "red";
            return;
        }

        mensagem.textContent = "Login realizado com sucesso!";
        mensagem.style.color = "green";

        setTimeout(() => {
            window.location.href = "../../Restaurantes/templates/index.html";
        }, 1500);

    } catch (error) {
        mensagem.textContent = "Erro ao se comunicar com o servidor!";
        mensagem.style.color = "red";
        console.error(error);
    }
});
