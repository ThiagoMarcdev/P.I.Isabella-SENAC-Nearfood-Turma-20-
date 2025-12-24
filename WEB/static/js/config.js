console.log("--> O arquivo config.js foi carregado com sucesso!");
document.addEventListener("DOMContentLoaded", () => {
    carregarTema();
    carregarPreferenciasLocais(); // Notificações e Tema (coisas que não precisam ir pro banco agora)
  
    // Listeners de Tema e Notificação
    configurarListenersInterface();
    const accountForm = document.getElementById("accountForm");
    if (accountForm) {
        console.log("--> Formulário de conta encontrado pelo JS!"); // Debug
        
        accountForm.addEventListener("submit", function(e) {
            console.log("--> Botão SALVAR clicado! JS interceptou."); // Debug
            salvarConfiguracoesConta(e);
        });
    } else {
        console.error("--> ERRO: O JS não encontrou o form com id='accountForm'");
    }
});

// FUNÇÕES DE BACKEND 

async function salvarConfiguracoesConta(event) {
  event.preventDefault(); 
    console.log("Botão salvar clicado! Iniciando script...");
    const email = document.getElementById("email").value;
    const senhaAtual = document.getElementById("senhaAtual").value;
    const novaSenha = document.getElementById("novaSenha").value;
    const confirmarSenha = document.getElementById("confirmarSenha").value;
    
    
    const csrfToken = document.querySelector('[name=csrfmiddlewaretoken]').value;

    
    if (novaSenha) {
        if (novaSenha.length < 6) {
            mostrarMensagem("A senha deve ter no mínimo 6 caracteres.", "error");
            return;
        }
        if (novaSenha !== confirmarSenha) {
            mostrarMensagem("As novas senhas não conferem.", "error");
            return;
        }
        if (!senhaAtual) {
            mostrarMensagem("Por favor, digite sua senha atual para confirmar a alteração.", "error");
            return;
        }
    }

    
    const dados = {
        email: email,
        senha_atual: senhaAtual,
        nova_senha: novaSenha,
        confirmar_senha: confirmarSenha
    };

    try {
        const response = await fetch('/config/', { 
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRFToken': csrfToken
            },
            body: JSON.stringify(dados)
        });

        const data = await response.json();

        if (response.ok) {
            mostrarMensagem("Dados atualizados com sucesso!", "success");
            // Limpa campos de senha
            document.getElementById("senhaAtual").value = "";
            document.getElementById("novaSenha").value = "";
            document.getElementById("confirmarSenha").value = "";
        } else {
            mostrarMensagem(data.message || "Erro ao atualizar.", "error");
        }

    } catch (error) {
        console.error("Erro na requisição:", error);
        mostrarMensagem("Erro de conexão com o servidor.", "error");
    }
}



function carregarPreferenciasLocais() {
    // Notificações
    const notifPromocoes = localStorage.getItem("notifPromocoes") !== "false";
    const notifReservas = localStorage.getItem("notifReservas") !== "false";
    const notifNovos = localStorage.getItem("notifNovos") === "true";
  
    if(document.getElementById("notifPromocoes")) document.getElementById("notifPromocoes").checked = notifPromocoes;
    if(document.getElementById("notifReservas")) document.getElementById("notifReservas").checked = notifReservas;
    if(document.getElementById("notifNovos")) document.getElementById("notifNovos").checked = notifNovos;

    // Tema (Radio Button)
    const temaSalvo = localStorage.getItem("tema") || "light";
    const radio = document.querySelector(`input[name="tema"][value="${temaSalvo}"]`);
    if (radio) radio.checked = true;
}

function configurarListenersInterface() {
    // Tema
    const temaRadios = document.querySelectorAll('input[name="tema"]');
    temaRadios.forEach((radio) => {
        radio.addEventListener("change", (e) => alterarTema(e.target.value));
    });

    // Botão Header
    const themeBtn = document.getElementById("themeToggle");
    if (themeBtn) themeBtn.addEventListener("click", alternarTemaHeader);

    // Notificações
    const ids = ["notifPromocoes", "notifReservas", "notifNovos"];
    ids.forEach(id => {
        const el = document.getElementById(id);
        if (el) {
            el.addEventListener("change", (e) => {
                localStorage.setItem(id, e.target.checked);
                mostrarMensagem("Preferência salva no dispositivo.", "success");
            });
        }
    });
}

function carregarTema() {
    const tema = localStorage.getItem("tema") || "light";
    aplicarTema(tema);
}

function alterarTema(tema) {
    aplicarTema(tema);
    localStorage.setItem("tema", tema);
    mostrarMensagem("Tema atualizado!", "success");
}

function alternarTemaHeader() {
    const atual = localStorage.getItem("tema") || "light";
    const novo = atual === "dark" ? "light" : "dark";
    
    // Atualiza radio
    const radio = document.querySelector(`input[name="tema"][value="${novo}"]`);
    if(radio) radio.checked = true;
    
    alterarTema(novo);
}

function aplicarTema(tema) {
    let temaFinal = tema;
    if (tema === "auto") {
        temaFinal = window.matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : "light";
    }
    document.documentElement.setAttribute("data-theme", temaFinal);
    
    const btn = document.getElementById("themeToggle");
    if (btn) btn.textContent = temaFinal === "dark" ? "🌙" : "☀️";
}

// UI HELPERS

function mostrarMensagem(texto, tipo) {
    const msgDiv = document.getElementById("successMessage");
    if (!msgDiv) return;

    msgDiv.textContent = (tipo === "success" ? "✓ " : "⚠️ ") + texto;
    msgDiv.style.backgroundColor = tipo === "success" ? "#d4edda" : "#f8d7da";
    msgDiv.style.color = tipo === "success" ? "#155724" : "#721c24";
    msgDiv.style.borderColor = tipo === "success" ? "#c3e6cb" : "#f5c6cb";
    msgDiv.style.display = "block";

    setTimeout(() => {
        msgDiv.style.display = "none";
    }, 4000);
}