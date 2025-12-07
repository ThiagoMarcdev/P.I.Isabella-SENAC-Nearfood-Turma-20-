document.getElementById("formCadastro").addEventListener("submit", async function(event) {
    event.preventDefault();

    const nomeCompleto = document.getElementById("nome").value.trim();
    const email = document.getElementById("email").value.trim();
    const telefone = document.getElementById("telefone").value.trim();
    const senha = document.getElementById("senha").value.trim();
    const confirmSenha = document.getElementById("confirmSenha").value.trim();
    const mensagem = document.getElementById("mensagem");

    // ===== VALIDAÇÕES =====
    if (!nomeCompleto || !email || !telefone || !senha || !confirmSenha) {
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

    // ===== TRATAR NOME =====
    const partesNome = nomeCompleto.split(" ");
    const first_name = partesNome.shift();           
    const last_name = partesNome.join(" ") || "";    

    // username automático (pode trocar depois)
    const username = email.split("@")[0];

    // ===== MONTAR JSON PARA O BACKEND =====
    const dados = {
        username: username,
        first_name: first_name,
        last_name: last_name,
        password: senha,
        email: email,
        tipo: "cliente",
        telefone: telefone
    };

    try {
        const response = await fetch("http://localhost:8000/api/usuarios/cadastro/", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(dados)
        });

        const data = await response.json();

        if (!response.ok) {
            mensagem.textContent = data.detail || "Erro ao cadastrar!";
            mensagem.style.color = "red";
            return;
        }

        mensagem.textContent = "Cadastro realizado com sucesso!";
        mensagem.style.color = "green";

        setTimeout(() => {
            window.location.href = "../templates/login.html";
        }, 1500);

    } catch (error) {
        mensagem.textContent = "Erro ao conectar com o servidor!";
        mensagem.style.color = "red";
        console.error(error);
    }
});
