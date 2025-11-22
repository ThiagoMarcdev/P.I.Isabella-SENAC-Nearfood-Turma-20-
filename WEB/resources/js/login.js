document.getElementById("formLogin").addEventListener("submit", function(event) {
    event.preventDefault();

    const email = document.getElementById("email").value.trim();
    const senha = document.getElementById("senha").value.trim();
    const mensagem = document.getElementById("mensagem");

    if (!email || !senha) {
        mensagem.textContent = "Preencha todos os campos!";
        mensagem.style.color = "red";
        return;
    }

    if (!email.includes("@")) {
        mensagem.textContent = "E-mail inválido!";
        mensagem.style.color = "red";
        return;
    }

    if (senha.length < 6) {
        mensagem.textContent = "Senha inválida!";
        mensagem.style.color = "red";
        return;
    }

    // Aqui você pode validar a senha correta (exemplo simples)
    if (senha === "123456") { // Substitua por lógica real ou integração com backend
        alert("Login realizado com sucesso!");
        window.location.href = "../templates/home.html"; // Redireciona para a tela home
    } else {
        mensagem.textContent = "Senha incorreta!";
        mensagem.style.color = "red";
    }
});