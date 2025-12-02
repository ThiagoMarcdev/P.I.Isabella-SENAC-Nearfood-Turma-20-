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
    console.log("[v0] Grid de restaurantes não encontrado")
    return
  }

  console.log("[v0] Renderizando", restaurantes.length, "restaurantes")

  grid.innerHTML = restaurantes
    .map(
      (rest) => `
        <div class="restaurant-card" onclick="irParaDetalhes(${rest.id})">
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
    console.log("[v0] Grid de recomendações não encontrado")
    return
  }

  console.log("[v0] Renderizando", recomendacoes.length, "recomendações")

  grid.innerHTML = recomendacoes
    .map(
      (rest) => `
        <div class="recommendation-card" onclick="irParaDetalhes(${rest.id})">
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

// Função para ir para detalhes do restaurante
function irParaDetalhes(id) {
  console.log("[v0] Navegando para detalhes do restaurante:", id)
  // Salvar ID no localStorage para usar na página de detalhes
  localStorage.setItem("restauranteAtual", id)
  window.location.href = "/detalhes"
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

  // Atualizar botões de categoria para mostrar o ativo
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
        <div class="restaurant-card" onclick="irParaDetalhes(${rest.id})">
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

  // Filtrar primeiro por categoria se houver uma selecionada
  const listaFiltrada = categoriaAtual ? restaurantes.filter((rest) => rest.categoria === categoriaAtual) : restaurantes

  // Depois aplicar a busca por texto
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
        <div class="restaurant-card" onclick="irParaDetalhes(${rest.id})">
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
})

// Exportar dados para uso em outras páginas
window.restaurantesData = restaurantes
window.recomendacoesData = recomendacoes
