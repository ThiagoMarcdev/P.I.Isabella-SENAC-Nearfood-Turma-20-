document.addEventListener("DOMContentLoaded", () => {
  // Pegar token da URL
  const urlParams = new URLSearchParams(window.location.search)
  const token = urlParams.get("token")

  if (!token) {
    showError("Token inválido ou expirado. Solicite um novo link de redefinição.")
    document.getElementById("submitBtn").disabled = true
  } else {
    document.getElementById("tokenField").value = token
  }

  const form = document.getElementById("newPasswordForm")
  const passwordInput = document.getElementById("password")
  const confirmPasswordInput = document.getElementById("confirm_password")
  const submitBtn = document.getElementById("submitBtn")

  // Toggle visualização de senha
  document.querySelectorAll(".toggle-password").forEach((button) => {
    button.addEventListener("click", function () {
      const targetId = this.getAttribute("data-target")
      const input = document.getElementById(targetId)
      const type = input.getAttribute("type") === "password" ? "text" : "password"
      input.setAttribute("type", type)
      this.classList.toggle("active")
    })
  })

  // Validação de senha em tempo real
  passwordInput.addEventListener("input", function () {
    validatePasswordRequirements(this.value)
  })

  // Validação de confirmação de senha
  confirmPasswordInput.addEventListener("input", () => {
    validatePasswordMatch()
  })

  // Validar requisitos da senha
  function validatePasswordRequirements(password) {
    const requirements = {
      length: password.length >= 8,
      uppercase: /[A-Z]/.test(password),
      number: /[0-9]/.test(password),
      special: /[!@#$%^&*(),.?":{}|<>]/.test(password),
    }

    document.getElementById("req-length").classList.toggle("valid", requirements.length)
    document.getElementById("req-uppercase").classList.toggle("valid", requirements.uppercase)
    document.getElementById("req-number").classList.toggle("valid", requirements.number)
    document.getElementById("req-special").classList.toggle("valid", requirements.special)

    return Object.values(requirements).every((req) => req)
  }

  // Validar se as senhas coincidem
  function validatePasswordMatch() {
    const password = passwordInput.value
    const confirmPassword = confirmPasswordInput.value
    const errorElement = document.getElementById("password-match-error")

    if (confirmPassword && password !== confirmPassword) {
      errorElement.textContent = "As senhas não coincidem"
      errorElement.style.display = "block"
      return false
    } else {
      errorElement.textContent = ""
      errorElement.style.display = "none"
      return true
    }
  }

  // Submit do formulário
  form.addEventListener("submit", async (e) => {
    e.preventDefault()

    const password = passwordInput.value
    const confirmPassword = confirmPasswordInput.value

    // Validações
    if (!validatePasswordRequirements(password)) {
      showError("A senha não atende aos requisitos mínimos de segurança.")
      return
    }

    if (password !== confirmPassword) {
      showError("As senhas não coincidem.")
      return
    }

    // Mostrar loading
    submitBtn.disabled = true
    document.querySelector(".btn-text").style.display = "none"
    document.querySelector(".btn-loader").style.display = "inline-block"

    try {
      const formData = new FormData(form)

      // Enviar para o Django
      const response = await fetch("/api/reset-senha/definir-nova/", {
        method: "POST",
        body: formData,
        headers: {
          "X-Requested-With": "XMLHttpRequest",
        },
      })

      const data = await response.json()

      if (response.ok && data.success) {
        showSuccess("Senha redefinida com sucesso! Redirecionando...")
        setTimeout(() => {
          window.location.href = "/login/"
        }, 2000)
      } else {
        showError(data.message || "Erro ao redefinir senha. Tente novamente.")
        submitBtn.disabled = false
        document.querySelector(".btn-text").style.display = "inline"
        document.querySelector(".btn-loader").style.display = "none"
      }
    } catch (error) {
      console.error("[v0] Erro:", error)
      showError("Erro de conexão. Verifique sua internet e tente novamente.")
      submitBtn.disabled = false
      document.querySelector(".btn-text").style.display = "inline"
      document.querySelector(".btn-loader").style.display = "none"
    }
  })

  function showError(message) {
    const errorAlert = document.getElementById("errorAlert")
    const successAlert = document.getElementById("successAlert")
    errorAlert.textContent = message
    errorAlert.style.display = "block"
    successAlert.style.display = "none"
    setTimeout(() => {
      errorAlert.style.display = "none"
    }, 5000)
  }

  function showSuccess(message) {
    const successAlert = document.getElementById("successAlert")
    const errorAlert = document.getElementById("errorAlert")
    successAlert.textContent = message
    successAlert.style.display = "block"
    errorAlert.style.display = "none"
  }
})
