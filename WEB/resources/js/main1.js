// Dados de exemplo dos restaurantes
const restaurantes = [
  { id: 1, nome: "Bella Cucina", tipo: "Italiano", categoria: "Pizza", emoji: "🍝", avaliacao: 4.5 },
  { id: 2, nome: "Zen Bites", tipo: "Chinês", categoria: "Japonês", emoji: "🥢", avaliacao: 4.7 },
  { id: 3, nome: "The Grill House", tipo: "Churrascaria", categoria: "Lanches", emoji: "🥩", avaliacao: 4.6 },
  { id: 4, nome: "Ocean Harvest", tipo: "Frutos do mar", categoria: "Japonês", emoji: "🦐", avaliacao: 4.8 },
  { id: 5, nome: "Green Plate", tipo: "Vegano", categoria: "Sanduíche", emoji: "🥗", avaliacao: 4.4 },
  { id: 6, nome: "Fiesta Cantina", tipo: "Mexicano", categoria: "Lanches", emoji: "🌮", avaliacao: 4.5 },
]

const recomendacoes = [
  { id: 7, nome: "Golden Harvest", tipo: "Comida caseira", categoria: "Lanches", emoji: "🍲" },
  { id: 8, nome: "Urban Bites", tipo: "Comida de rua", categoria: "Lanches", emoji: "🍔" },
]

console.log("[v0] main.js carregado, total de restaurantes:", restaurantes.length)

// Função para renderizar restaurantes
function renderizarRestaurantes() {
  const grid = document.getElementById("restaurantesGrid")
  if (!grid) {
    return
  }

  grid.innerHTML = restaurantes
    .map(
      (rest) => `
        <div class="restaurant-card" onclick="abrirModal(${rest.id})">
            <div class="restaurant-logo">${rest.emoji}</div>
            <h4>${rest.nome}</h4>
            <p>${rest.tipo}</p>
        </div>
    `,
    )
    .join("")
}

// Função para renderizar recomendações
function renderizarRecomendacoes() {
  const grid = document.getElementById("recomendacoesGrid")
  if (!grid) {
    return
  }

  grid.innerHTML = recomendacoes
    .map(
      (rest) => `
        <div class="recommendation-card" onclick="abrirModal(${rest.id})">
            <div class="recommendation-image">${rest.emoji}</div>
            <div class="recommendation-info">
                <h4>${rest.nome}</h4>
                <p>${rest.tipo}</p>
            </div>
        </div>
    `,
    )
    .join("")
}

// Função para abrir modal com detalhes do restaurante
function abrirModal(id) {
  const restaurante = [...restaurantes, ...recomendacoes].find((r) => r.id === id)
  if (!restaurante) return

  // Preencher dados do modal
  document.getElementById("modalLogo").textContent = restaurante.emoji
  document.getElementById("modalNome").textContent = restaurante.nome
  document.getElementById("modalTipo").textContent = restaurante.tipo
  document.getElementById("modalAvaliacao").textContent = restaurante.avaliacao || "4.5"
  document.getElementById("modalTempo").textContent =
    `${Math.floor(Math.random() * 15) + 10}-${Math.floor(Math.random() * 15) + 20} min`
  document.getElementById("modalDistancia").textContent = `${(Math.random() * 3 + 0.5).toFixed(1)} km`
  document.getElementById("modalDescricao").textContent =
    `Restaurante especializado em ${restaurante.tipo.toLowerCase()} com os melhores pratos da região.`

  // Salvar ID atual
  window.restauranteAtualId = id

  // Mostrar modal
  document.getElementById("modalOverlay").classList.add("active")
  document.body.style.overflow = "hidden"
}

function fecharModal() {
  document.getElementById("modalOverlay").classList.remove("active")
  document.body.style.overflow = "auto"
}

function verRota() {
  const restaurante = [...restaurantes, ...recomendacoes].find((r) => r.id === window.restauranteAtualId)
  if (restaurante) {
    // Simulando abertura do Google Maps
    alert(
      `🗺️ Abrindo rota para ${restaurante.nome}...\n\nEndereço: Avenida João Celos, 123\n\nIsso abriria o Google Maps em uma aplicação real.`,
    )
  }
}

function fazerReserva() {
  const restaurante = [...restaurantes, ...recomendacoes].find((r) => r.id === window.restauranteAtualId)
  if (restaurante) {
    alert(`📅 Reserva solicitada para ${restaurante.nome}!\n\nEm breve você receberá uma confirmação.`)
    fecharModal()
  }
}

function verMaisDetalhes() {
  localStorage.setItem("restauranteAtual", window.restauranteAtualId)
  window.location.href = "detalhe.html"
}

// Função para alternar tema
function alternarTema() {
  const html = document.documentElement
  const temaAtual = html.getAttribute("data-theme")
  const novoTema = temaAtual === "dark" ? "light" : "dark"

  html.setAttribute("data-theme", novoTema)
  localStorage.setItem("tema", novoTema)

  // Atualizar ícone do botão
  const btn = document.getElementById("themeToggle")
  if (btn) {
    btn.textContent = novoTema === "dark" ? "🌙" : "☀️"
  }
}

// Variável para armazenar a categoria selecionada
let categoriaAtual = null

// Função para filtrar por categoria
function filtrarPorCategoria(categoria) {
  categoriaAtual = categoria

  const resultados = categoriaAtual ? restaurantes.filter((rest) => rest.categoria === categoriaAtual) : restaurantes

  const grid = document.getElementById("restaurantesGrid")
  if (!grid) return

  document.querySelectorAll(".category-btn").forEach((btn) => {
    if (btn.textContent.includes(categoria)) {
      btn.classList.add("active")
    } else {
      btn.classList.remove("active")
    }
  })

  if (resultados.length === 0) {
    grid.innerHTML =
      '<p style="grid-column: 1/-1; text-align: center; color: var(--text-secondary);">Nenhum restaurante encontrado nesta categoria</p>'
    return
  }

  grid.innerHTML = resultados
    .map(
      (rest) => `
        <div class="restaurant-card" onclick="abrirModal(${rest.id})">
            <div class="restaurant-logo">${rest.emoji}</div>
            <h4>${rest.nome}</h4>
            <p>${rest.tipo}</p>
        </div>
    `,
    )
    .join("")
}

function buscarRestaurantes() {
  const input = document.getElementById("searchInput")
  if (!input) return

  const termo = input.value.toLowerCase()
  const listaFiltrada = categoriaAtual ? restaurantes.filter((rest) => rest.categoria === categoriaAtual) : restaurantes
  const resultados = termo
    ? listaFiltrada.filter((rest) => rest.nome.toLowerCase().includes(termo) || rest.tipo.toLowerCase().includes(termo))
    : listaFiltrada

  const grid = document.getElementById("restaurantesGrid")
  if (!grid) return

  if (resultados.length === 0) {
    grid.innerHTML =
      '<p style="grid-column: 1/-1; text-align: center; color: var(--text-secondary);">Nenhum restaurante encontrado</p>'
    return
  }

  grid.innerHTML = resultados
    .map(
      (rest) => `
        <div class="restaurant-card" onclick="abrirModal(${rest.id})">
            <div class="restaurant-logo">${rest.emoji}</div>
            <h4>${rest.nome}</h4>
            <p>${rest.tipo}</p>
        </div>
    `,
    )
    .join("")
}

// Carregar tema salvo
function carregarTema() {
  const temaSalvo = localStorage.getItem("tema") || "light"
  document.documentElement.setAttribute("data-theme", temaSalvo)

  const btn = document.getElementById("themeToggle")
  if (btn) {
    btn.textContent = temaSalvo === "dark" ? "🌙" : "☀️"
  }
}

// Event Listeners
document.addEventListener("DOMContentLoaded", () => {
  console.log("[v0] DOM carregado, iniciando aplicação")
  carregarTema()
  renderizarRestaurantes()
  renderizarRecomendacoes()

  // Botão de tema
  const themeBtn = document.getElementById("themeToggle")
  if (themeBtn) {
    themeBtn.addEventListener("click", alternarTema)
  }

  // Busca
  const searchInput = document.getElementById("searchInput")
  if (searchInput) {
    searchInput.addEventListener("input", buscarRestaurantes)
  }

  // Event listeners para botões de categoria
  document.querySelectorAll(".category-btn").forEach((btn) => {
    btn.addEventListener("click", (e) => {
      const texto = e.target.textContent.trim()
      const categoria = texto.split(" ")[1] // Pega o texto depois do emoji

      // Se clicar na categoria já ativa, remove o filtro
      if (btn.classList.contains("active")) {
        categoriaAtual = null
        btn.classList.remove("active")
        renderizarRestaurantes()
      } else {
        filtrarPorCategoria(categoria)
      }
    })
  })

  const modalClose = document.getElementById("modalClose")
  const modalOverlay = document.getElementById("modalOverlay")
  const btnVerRota = document.getElementById("btnVerRota")
  const btnFazerReserva = document.getElementById("btnFazerReserva")
  const btnVerMais = document.getElementById("btnVerMais")

  if (modalClose) {
    modalClose.addEventListener("click", fecharModal)
  }

  if (modalOverlay) {
    modalOverlay.addEventListener("click", (e) => {
      if (e.target === modalOverlay) {
        fecharModal()
      }
    })
  }

  if (btnVerRota) {
    btnVerRota.addEventListener("click", verRota)
  }

  if (btnFazerReserva) {
    btnFazerReserva.addEventListener("click", fazerReserva)
  }

  if (btnVerMais) {
    btnVerMais.addEventListener("click", verMaisDetalhes)
  }
})

// Exportar dados para uso em outras páginas
window.restaurantesData = restaurantes
window.recomendacoesData = recomendacoes
