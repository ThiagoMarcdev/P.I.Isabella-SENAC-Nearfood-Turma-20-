// Verificar se há token na URL para mostrar formulário correto
document.addEventListener("DOMContentLoaded", () => {
  const urlParams = new URLSearchParams(window.location.search)
  const token = urlParams.get("token")

  if (token) {
    document.getElementById("requestResetForm").parentElement.style.display = "none"
    document.getElementById("newPasswordForm").style.display = "block"
    document.getElementById("resetToken").value = token
  }

  const form = document.getElementById("resetForm")
  const emailInput = document.getElementById("email")
  const submitBtn = document.getElementById("submitBtn")
  const errorAlert = document.getElementById("errorAlert")
  const successAlert = document.getElementById("successAlert")

  form.addEventListener("submit", async (e) => {
    e.preventDefault() // Prevenir submit tradicional do formulário

    const email = emailInput.value.trim()

    // Validar email
    if (!validateEmail(email)) {
      showError("Por favor, insira um email válido.")
      return
    }

    // Mostrar loading
    submitBtn.disabled = true
    document.querySelector(".btn-text").style.display = "none"
    document.querySelector(".btn-loader").style.display = "inline-block"

    try {
      // Pegar CSRF token do Django
      const csrfToken = getCookie("csrftoken")

      // Fazer requisição AJAX para o Django
      const response = await fetch("/api/reset-senha/solicitar/", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "X-CSRFToken": csrfToken,
          "X-Requested-With": "XMLHttpRequest",
        },
        body: JSON.stringify({
          email: email,
        }),
      })

      const data = await response.json()

      if (response.ok && data.success) {
        showSuccess("Link de recuperação enviado! Verifique seu email.")
        emailInput.value = ""
      } else {
        showError(data.message || "Erro ao enviar email. Verifique se o email está cadastrado.")
      }
    } catch (error) {
      console.error("[v0] Erro na requisição:", error)
      showError("Erro de conexão. Verifique sua internet e tente novamente.")
    } finally {
      // Restaurar botão
      submitBtn.disabled = false
      document.querySelector(".btn-text").style.display = "inline"
      document.querySelector(".btn-loader").style.display = "none"
    }
  })

  // Validar email em tempo real
  emailInput.addEventListener("blur", function () {
    if (this.value && !validateEmail(this.value)) {
      showError("Email inválido")
    }
  })

  emailInput.addEventListener("focus", () => {
    hideAlerts()
  })

  function validateEmail(email) {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    return regex.test(email)
  }

  function showError(message) {
    errorAlert.textContent = message
    errorAlert.style.display = "block"
    successAlert.style.display = "none"
    setTimeout(() => {
      errorAlert.style.display = "none"
    }, 5000)
  }

  function showSuccess(message) {
    successAlert.textContent = message
    successAlert.style.display = "block"
    errorAlert.style.display = "none"
  }

  function hideAlerts() {
    errorAlert.style.display = "none"
    successAlert.style.display = "none"
  }
})

// Formulário de solicitação de reset
const requestForm = document.getElementById("requestResetForm")
if (requestForm) {
  requestForm.addEventListener("submit", async function (e) {
    e.preventDefault()

    const emailInput = document.getElementById("email")
    const emailError = document.getElementById("emailError")
    const submitBtn = this.querySelector('button[type="submit"]')
    const btnText = submitBtn.querySelector(".btn-text")
    const btnLoader = submitBtn.querySelector(".btn-loader")
    const successMessage = document.getElementById("successMessage")

    // Limpar mensagens anteriores
    emailError.textContent = ""
    successMessage.style.display = "none"

    // Validar email
    if (!window.validateEmail(emailInput.value)) {
      // Using window to access the validateEmail function
      emailError.textContent = "Por favor, insira um email válido"
      return
    }

    // Mostrar loading
    submitBtn.disabled = true
    btnText.style.display = "none"
    btnLoader.style.display = "inline-block"

    try {
      // Aqui você fará a requisição para seu backend Django
      // Exemplo com fetch:
      /*
            const response = await fetch('/api/password-reset/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRFToken': getCookie('csrftoken')
                },
                body: JSON.stringify({
                    email: emailInput.value
                })
            });
            
            const data = await response.json();
            
            if (response.ok) {
                successMessage.style.display = 'flex';
                emailInput.value = '';
            } else {
                emailError.textContent = data.error || 'Erro ao enviar email';
            }
            */

      // Simulação para teste (remover em produção)
      await new Promise((resolve) => setTimeout(resolve, 2000))
      successMessage.style.display = "flex"
      emailInput.value = ""
    } catch (error) {
      console.error("[v0] Error:", error)
      emailError.textContent = "Erro ao processar solicitação. Tente novamente."
    } finally {
      submitBtn.disabled = false
      btnText.style.display = "inline"
      btnLoader.style.display = "none"
    }
  })
}

// Formulário de confirmação de nova senha
const confirmForm = document.getElementById("confirmResetForm")
if (confirmForm) {
  const passwordInput = document.getElementById("newPassword")
  const confirmPasswordInput = document.getElementById("confirmPassword")

  // Validação em tempo real da senha
  passwordInput.addEventListener("input", function () {
    window.validatePasswordRequirements(this.value) // Using window to access the validatePasswordRequirements function
  })

  // Toggle de visualização de senha
  document.querySelectorAll(".toggle-password").forEach((button) => {
    button.addEventListener("click", function () {
      const targetId = this.dataset.target
      const input = document.getElementById(targetId)

      if (input.type === "password") {
        input.type = "text"
        this.classList.add("active")
      } else {
        input.type = "password"
        this.classList.remove("active")
      }
    })
  })

  confirmForm.addEventListener("submit", async function (e) {
    e.preventDefault()

    const passwordError = document.getElementById("passwordError")
    const confirmPasswordError = document.getElementById("confirmPasswordError")
    const submitBtn = this.querySelector('button[type="submit"]')
    const btnText = submitBtn.querySelector(".btn-text")
    const btnLoader = submitBtn.querySelector(".btn-loader")
    const successMessage = document.getElementById("successMessageConfirm")

    // Limpar mensagens anteriores
    passwordError.textContent = ""
    confirmPasswordError.textContent = ""
    successMessage.style.display = "none"

    // Validações
    const password = passwordInput.value
    const confirmPassword = confirmPasswordInput.value

    if (!window.validatePassword(password)) {
      // Using window to access the validatePassword function
      passwordError.textContent = "A senha não atende aos requisitos mínimos"
      return
    }

    if (password !== confirmPassword) {
      confirmPasswordError.textContent = "As senhas não coincidem"
      return
    }

    // Mostrar loading
    submitBtn.disabled = true
    btnText.style.display = "none"
    btnLoader.style.display = "inline-block"

    try {
      // Aqui você fará a requisição para seu backend Django
      // Exemplo com fetch:
      /*
            const token = document.getElementById('resetToken').value;
            const response = await fetch('/api/password-reset-confirm/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRFToken': getCookie('csrftoken')
                },
                body: JSON.stringify({
                    token: token,
                    password: password
                })
            });
            
            const data = await response.json();
            
            if (response.ok) {
                successMessage.style.display = 'flex';
                setTimeout(() => {
                    window.location.href = '/login/';
                }, 2000);
            } else {
                passwordError.textContent = data.error || 'Erro ao redefinir senha';
            }
            */

      // Simulação para teste (remover em produção)
      await new Promise((resolve) => setTimeout(resolve, 2000))
      successMessage.style.display = "flex"
      setTimeout(() => {
        window.location.href = "login.html"
      }, 2000)
    } catch (error) {
      console.error("[v0] Error:", error)
      passwordError.textContent = "Erro ao processar solicitação. Tente novamente."
    } finally {
      submitBtn.disabled = false
      btnText.style.display = "inline"
      btnLoader.style.display = "none"
    }
  })
}

// Função para validar senha
window.validatePassword = (password) => {
  const hasMinLength = password.length >= 8
  const hasUppercase = /[A-Z]/.test(password)
  const hasNumber = /\d/.test(password)

  return hasMinLength && hasUppercase && hasNumber
}

// Função para validar requisitos da senha em tempo real
window.validatePasswordRequirements = (password) => {
  const lengthReq = document.getElementById("req-length")
  const uppercaseReq = document.getElementById("req-uppercase")
  const numberReq = document.getElementById("req-number")

  // Validar comprimento
  if (password.length >= 8) {
    lengthReq.classList.add("valid")
  } else {
    lengthReq.classList.remove("valid")
  }

  // Validar letra maiúscula
  if (/[A-Z]/.test(password)) {
    uppercaseReq.classList.add("valid")
  } else {
    uppercaseReq.classList.remove("valid")
  }

  // Validar número
  if (/\d/.test(password)) {
    numberReq.classList.add("valid")
  } else {
    numberReq.classList.remove("valid")
  }
}

// Função para pegar CSRF token (necessário para Django)
function getCookie(name) {
  let cookieValue = null
  if (document.cookie && document.cookie !== "") {
    const cookies = document.cookie.split(";")
    for (let i = 0; i < cookies.length; i++) {
      const cookie = cookies[i].trim()
      if (cookie.substring(0, name.length + 1) === name + "=") {
        cookieValue = decodeURIComponent(cookie.substring(name.length + 1))
        break
      }
    }
  }
  return cookieValue
}
