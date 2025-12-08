// Carregar configurações salvas
function carregarConfiguracoes() {
  // Carregar e-mail salvo
  const emailSalvo = localStorage.getItem("userEmail")
  if (emailSalvo) {
    document.getElementById("email").value = emailSalvo
  }

  // Carregar tema salvo (light, dark, ou auto)
  const temaSalvo = localStorage.getItem("tema") || "light"
  const radioButton = document.querySelector(`input[name="tema"][value="${temaSalvo}"]`)
  if (radioButton) {
    radioButton.checked = true
  }

  // Carregar notificações salvas
  const notifPromocoes = localStorage.getItem("notifPromocoes") !== "false"
  const notifReservas = localStorage.getItem("notifReservas") !== "false"
  const notifNovos = localStorage.getItem("notifNovos") === "true"

  document.getElementById("notifPromocoes").checked = notifPromocoes
  document.getElementById("notifReservas").checked = notifReservas
  document.getElementById("notifNovos").checked = notifNovos
}

// Salvar configurações da conta
function salvarConfiguracoesGerais(event) {
  event.preventDefault()

  const email = document.getElementById("email").value
  const senhaAtual = document.getElementById("senhaAtual").value
  const novaSenha = document.getElementById("novaSenha").value
  const confirmarSenha = document.getElementById("confirmarSenha").value

  // Validação de senha
  if (novaSenha && novaSenha !== confirmarSenha) {
    alert("As senhas não coincidem!")
    return
  }

  if (novaSenha && novaSenha.length < 6) {
    alert("A senha deve ter pelo menos 6 caracteres!")
    return
  }

  // Salvar e-mail
  localStorage.setItem("userEmail", email)

  // Simular salvamento de senha (em produção, isso seria feito no backend)
  if (novaSenha) {
    localStorage.setItem("userPassword", novaSenha)
  }

  mostrarMensagemSucesso()

  // Limpar campos de senha
  document.getElementById("senhaAtual").value = ""
  document.getElementById("novaSenha").value = ""
  document.getElementById("confirmarSenha").value = ""
}

// Alterar tema
function alterarTema(tema) {
  const html = document.documentElement

  let temaFinal = tema

  // Se modo automático, detectar preferência do sistema
  if (tema === "auto") {
    const prefereDark = window.matchMedia("(prefers-color-scheme: dark)").matches
    temaFinal = prefereDark ? "dark" : "light"
  }

  html.setAttribute("data-theme", temaFinal)
  localStorage.setItem("tema", tema)

  // Atualizar botão do header
  const btn = document.getElementById("themeToggle")
  if (btn) {
    btn.textContent = temaFinal === "dark" ? "🌙" : "☀️"
  }

  mostrarMensagemSucesso()
}

// Salvar notificações
function salvarNotificacoes(tipo, valor) {
  localStorage.setItem(tipo, valor)
  mostrarMensagemSucesso()
}

// Mostrar mensagem de sucesso
function mostrarMensagemSucesso() {
  const mensagem = document.getElementById("successMessage")
  if (!mensagem) return

  mensagem.style.display = "block"

  setTimeout(() => {
    mensagem.style.display = "none"
  }, 3000)
}

// Carregar tema atual
function carregarTema() {
  const temaSalvo = localStorage.getItem("tema") || "light"

  let temaAtual = temaSalvo

  // Se modo automático, detectar preferência do sistema
  if (temaSalvo === "auto") {
    const prefereDark = window.matchMedia("(prefers-color-scheme: dark)").matches
    temaAtual = prefereDark ? "dark" : "light"
  }

  document.documentElement.setAttribute("data-theme", temaAtual)

  const btn = document.getElementById("themeToggle")
  if (btn) {
    btn.textContent = temaAtual === "dark" ? "🌙" : "☀️"
  }
}

// Alternar tema do header
function alternarTema() {
  const html = document.documentElement
  const temaAtual = html.getAttribute("data-theme")
  const novoTema = temaAtual === "dark" ? "light" : "dark"

  html.setAttribute("data-theme", novoTema)
  localStorage.setItem("tema", novoTema)

  const radioButton = document.querySelector(`input[name="tema"][value="${novoTema}"]`)
  if (radioButton) {
    radioButton.checked = true
  }

  const btn = document.getElementById("themeToggle")
  if (btn) {
    btn.textContent = novoTema === "dark" ? "🌙" : "☀️"
  }
}

// Event Listeners
document.addEventListener("DOMContentLoaded", () => {
  carregarTema()
  carregarConfiguracoes()

  // Form de conta
  const accountForm = document.getElementById("accountForm")
  if (accountForm) {
    accountForm.addEventListener("submit", salvarConfiguracoesGerais)
  }

  // Radio buttons de tema
  const temaRadios = document.querySelectorAll('input[name="tema"]')
  temaRadios.forEach((radio) => {
    radio.addEventListener("change", (e) => alterarTema(e.target.value))
  })

  // Toggles de notificação
  document.getElementById("notifPromocoes")?.addEventListener("change", (e) => {
    salvarNotificacoes("notifPromocoes", e.target.checked)
  })

  document.getElementById("notifReservas")?.addEventListener("change", (e) => {
    salvarNotificacoes("notifReservas", e.target.checked)
  })

  document.getElementById("notifNovos")?.addEventListener("change", (e) => {
    salvarNotificacoes("notifNovos", e.target.checked)
  })

  // Botão de tema do header
  const themeBtn = document.getElementById("themeToggle")
  if (themeBtn) {
    themeBtn.addEventListener("click", alternarTema)
  }
})
