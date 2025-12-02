// Dados dos restaurantes
const restaurantesCompletos = [
  { id: 1, nome: "Bella Cucina", tipo: "Italiano", emoji: "🍝", endereco: "Avenida João Celos, 123" },
  { id: 2, nome: "Zen Bites", tipo: "Chinês", emoji: "🥢", endereco: "Rua das Flores, 456" },
  { id: 3, nome: "The Grill House", tipo: "Churrascaria", emoji: "🥩", endereco: "Avenida Premium Steak, 789" },
  { id: 4, nome: "Ocean Harvest", tipo: "Frutos do mar", emoji: "🦐", endereco: "Rua da Praia, 321" },
  { id: 5, nome: "Green Plate", tipo: "Vegano", emoji: "🥗", endereco: "Avenida Verde, 654" },
  { id: 6, nome: "Fiesta Cantina", tipo: "Mexicano", emoji: "🌮", endereco: "Rua México, 987" },
]

// Carregar e renderizar favoritos
function carregarFavoritos() {
  const favoritos = JSON.parse(localStorage.getItem("favoritos") || "[]")
  const grid = document.getElementById("favoritosGrid")
  const emptyState = document.getElementById("emptyState")

  if (favoritos.length === 0) {
    grid.style.display = "none"
    emptyState.style.display = "block"
    return
  }

  grid.style.display = "grid"
  emptyState.style.display = "none"

  const restaurantesFavoritos = favoritos
    .map((id) => restaurantesCompletos.find((r) => r.id === id))
    .filter((r) => r !== undefined)

  grid.innerHTML = restaurantesFavoritos
    .map(
      (rest) => `
        <div class="favorite-card">
            <div class="favorite-image" onclick="verRestaurante(${rest.id})">
                ${rest.emoji}
            </div>
            <div class="favorite-info">
                <h3>${rest.nome}</h3>
                <p>${rest.tipo}</p>
                <p style="font-size: 14px; color: var(--text-secondary);">📍 ${rest.endereco}</p>
                <div class="favorite-actions">
                    <button class="remove-favorite-btn" onclick="removerFavorito(${rest.id})">
                        ❌ Remover
                    </button>
                    <button class="view-restaurant-btn" onclick="verRestaurante(${rest.id})">
                        👁️ Ver Detalhes
                    </button>
                </div>
            </div>
        </div>
    `,
    )
    .join("")
}

// Remover restaurante dos favoritos
function removerFavorito(id) {
  let favoritos = JSON.parse(localStorage.getItem("favoritos") || "[]")
  favoritos = favoritos.filter((favId) => favId !== id)
  localStorage.setItem("favoritos", JSON.stringify(favoritos))

  carregarFavoritos()
}

// Ver detalhes do restaurante
function verRestaurante(id) {
  localStorage.setItem("restauranteAtual", id)
  window.location.href = "/detalhes"
}

// Carregar tema
function carregarTema() {
  const temaSalvo = localStorage.getItem("tema") || "light"
  document.documentElement.setAttribute("data-theme", temaSalvo)

  const btn = document.getElementById("themeToggle")
  if (btn) {
    btn.textContent = temaSalvo === "dark" ? "🌙" : "☀️"
  }
}

// Alternar tema
function alternarTema() {
  const html = document.documentElement
  const temaAtual = html.getAttribute("data-theme")
  const novoTema = temaAtual === "dark" ? "light" : "dark"

  html.setAttribute("data-theme", novoTema)
  localStorage.setItem("tema", novoTema)

  const btn = document.getElementById("themeToggle")
  if (btn) {
    btn.textContent = novoTema === "dark" ? "🌙" : "☀️"
  }
}

// Event Listeners
document.addEventListener("DOMContentLoaded", () => {
  carregarTema()
  carregarFavoritos()

  // Botão de tema
  const themeBtn = document.getElementById("themeToggle")
  if (themeBtn) {
    themeBtn.addEventListener("click", alternarTema)
  }
})
