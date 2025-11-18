document.getElementById("formCadastro").addEventListener("submit", function(event) {
    event.preventDefault();

    const nome = document.getElementById("nome").value.trim();
    const email = document.getElementById("email").value.trim();
    const telefone = document.getElementById("telefone").value.trim();
    const senha = document.getElementById("senha").value.trim();
    const confirmSenha = document.getElementById("confirmSenha").value.trim();
    const mensagem = document.getElementById("mensagem");

    if (!nome || !email || !telefone || !senha || !confirmSenha) {
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
        mensagem.textContent = "A senha deve ter pelo menos 6 caracteres!";
        mensagem.style.color = "red";
        return;
    }

    if (senha !== confirmSenha) {
        mensagem.textContent = "As senhas não coincidem!";
        mensagem.style.color = "red";
        return;
    }

    mensagem.textContent = "Cadastro realizado com sucesso!";
    mensagem.style.color = "green";

    // Exemplo: redirecionar para login após sucesso
    // setTimeout(() => window.location.href = "../templates/login.html", 1500);
});