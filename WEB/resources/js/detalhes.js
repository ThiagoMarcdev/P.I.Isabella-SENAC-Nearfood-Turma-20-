// Dados completos dos restaurantes (incluindo informações extras)
const restaurantesCompletos = [
  {
    id: 1,
    nome: "Bella Cucina",
    tipo: "Italiano",
    emoji: "🍝",
    avaliacao: 4.5,
    endereco: "Avenida João Celos, 123",
    descricao:
      "Autêntica culinária italiana com receitas tradicionais passadas de geração em geração. Nossos pratos são preparados com ingredientes frescos importados diretamente da Itália. Desfrute de uma experiência gastronômica única em um ambiente acolhedor e sofisticado.",
    avaliacoes: [
      { nome: "Ana Maria Souza", data: "02/05/2023", texto: "Comida excelente. Volto sempre aqui!", avatar: "👩" },
      { nome: "Pedro Álvarez", data: "24/07/2023", texto: "Comida boa e barata. Ótimo atendimento.", avatar: "👨" },
    ],
  },
  {
    id: 2,
    nome: "Zen Bites",
    tipo: "Chinês",
    emoji: "🥢",
    avaliacao: 4.7,
    endereco: "Rua das Flores, 456",
    descricao:
      "Culinária chinesa autêntica em um ambiente zen e tranquilo. Nossos chefs especializados preparam pratos tradicionais com técnicas milenares. Experimente sabores únicos que transportam você para o coração da China.",
    avaliacoes: [
      {
        nome: "Guilherme Ferreira",
        data: "18/06/2024",
        texto: "Lugar incrível! Ótima vibe e atendimento.",
        avatar: "👦",
      },
    ],
  },
  {
    id: 3,
    nome: "The Grill House",
    tipo: "Churrascaria",
    emoji: "🥩",
    avaliacao: 4.6,
    endereco: "Avenida Premium Steak, 789",
    descricao:
      "A melhor churrascaria da cidade com carnes nobres e cortes especiais. Nosso rodízio oferece variedade incomparável de carnes grelhadas no ponto perfeito. Ambiente elegante ideal para celebrações especiais.",
    avaliacoes: [{ nome: "Carlos Silva", data: "15/08/2023", texto: "Melhor churrasco que já comi!", avatar: "👨" }],
  },
  {
    id: 4,
    nome: "Ocean Harvest",
    tipo: "Frutos do mar",
    emoji: "🦐",
    avaliacao: 4.8,
    endereco: "Rua da Praia, 321",
    descricao:
      "Frutos do mar frescos todos os dias direto do mar para sua mesa. Especialistas em peixes e crustáceos preparados com técnicas contemporâneas. Viva uma experiência oceânica única.",
    avaliacoes: [{ nome: "Marina Costa", data: "10/09/2023", texto: "Frutos do mar fresquíssimos!", avatar: "👩" }],
  },
  {
    id: 5,
    nome: "Green Plate",
    tipo: "Vegano",
    emoji: "🥗",
    avaliacao: 4.4,
    endereco: "Avenida Verde, 654",
    descricao:
      "Culinária vegana criativa e saborosa que surpreende até os não-veganos. Ingredientes orgânicos e locais em pratos coloridos e nutritivos. Prove que comida saudável pode ser deliciosa.",
    avaliacoes: [{ nome: "Julia Santos", data: "05/10/2023", texto: "Opções veganas incríveis!", avatar: "👩" }],
  },
  {
    id: 6,
    nome: "Fiesta Cantina",
    tipo: "Mexicano",
    emoji: "🌮",
    avaliacao: 4.5,
    endereco: "Rua México, 987",
    descricao:
      "Sabores autênticos do México em um ambiente festivo e colorido. Tacos, burritos e margaritas preparados com receitas tradicionais. Sinta-se em uma verdadeira fiesta mexicana.",
    avaliacoes: [{ nome: "Diego Martinez", data: "20/11/2023", texto: "Tacos autênticos e deliciosos!", avatar: "👨" }],
  },
]

// Função para carregar detalhes do restaurante
function carregarDetalhes() {
  const restauranteId = Number.parseInt(localStorage.getItem("restauranteAtual")) || 1
  const restaurante = restaurantesCompletos.find((r) => r.id === restauranteId)

  if (!restaurante) return

  // Atualizar informações do restaurante
  document.getElementById("restauranteName").textContent = restaurante.nome
  document.getElementById("avaliacaoNumero").textContent = restaurante.avaliacao
  document.getElementById("restauranteEndereco").textContent = restaurante.endereco
  document.getElementById("restauranteDescricao").innerHTML = `<p>${restaurante.descricao}</p>`

  // Atualizar botão de favorito
  const favoritoBtn = document.getElementById("adicionarFavoritoBtn")
  const favoritoIcone = document.getElementById("favoritoIcone")
  if (favoritoBtn) {
    favoritoBtn.setAttribute("data-restaurante-id", restaurante.id)

    // Verificar se já está nos favoritos
    const favoritos = JSON.parse(localStorage.getItem("favoritos") || "[]")
    const isFavorito = favoritos.includes(restaurante.id)

    favoritoIcone.textContent = isFavorito ? "❤️" : "🤍"
    favoritoBtn.innerHTML = `<span id="favoritoIcone">${isFavorito ? "❤️" : "🤍"}</span> ${isFavorito ? "REMOVER DOS FAVORITOS" : "ADICIONAR AOS FAVORITOS"}`
  }

  // Renderizar avaliações
  renderizarAvaliacoes(restaurante.avaliacoes)
}

// Função para renderizar avaliações
function renderizarAvaliacoes(avaliacoes) {
  const lista = document.getElementById("avaliacoesList")
  if (!lista) return

  lista.innerHTML = avaliacoes
    .map(
      (av) => `
        <div class="review-card">
            <div class="review-avatar">${av.avatar}</div>
            <div class="review-content">
                <h4>${av.nome}</h4>
                <p class="review-date">${av.data}</p>
                <p class="review-text">${av.texto}</p>
            </div>
        </div>
    `,
    )
    .join("")
}

// Função para adicionar/remover dos favoritos
function toggleFavorito() {
  const restauranteId = Number.parseInt(localStorage.getItem("restauranteAtual"))
  const favoritos = JSON.parse(localStorage.getItem("favoritos") || "[]")

  const index = favoritos.indexOf(restauranteId)
  const favoritoBtn = document.getElementById("adicionarFavoritoBtn")
  const favoritoIcone = document.getElementById("favoritoIcone")

  if (index === -1) {
    // Adicionar aos favoritos
    favoritos.push(restauranteId)
    favoritoIcone.textContent = "❤️"
    favoritoBtn.innerHTML = '<span id="favoritoIcone">❤️</span> REMOVER DOS FAVORITOS'
    alert("Restaurante adicionado aos favoritos!")
  } else {
    // Remover dos favoritos
    favoritos.splice(index, 1)
    favoritoIcone.textContent = "🤍"
    favoritoBtn.innerHTML = '<span id="favoritoIcone">🤍</span> ADICIONAR AOS FAVORITOS'
    alert("Restaurante removido dos favoritos!")
  }

  localStorage.setItem("favoritos", JSON.stringify(favoritos))
}

// Função para fazer reserva
function fazerReserva() {
  const restauranteId = Number.parseInt(localStorage.getItem("restauranteAtual"))
  const restaurante = restaurantesCompletos.find((r) => r.id === restauranteId)

  alert(
    `Reserva marcada no ${restaurante.nome}!\n\nEndereço: ${restaurante.endereco}\n\nEm breve você receberá um e-mail de confirmação.`,
  )
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
  carregarDetalhes()

  // Botão de favorito
  const favoritoBtn = document.getElementById("adicionarFavoritoBtn")
  if (favoritoBtn) {
    favoritoBtn.addEventListener("click", toggleFavorito)
  }

  // Botão de reserva
  const reservaBtn = document.querySelector(".reserve-detail-btn")
  if (reservaBtn) {
    reservaBtn.addEventListener("click", fazerReserva)
  }

  // Botão de tema
  const themeBtn = document.getElementById("themeToggle")
  if (themeBtn) {
    themeBtn.addEventListener("click", alternarTema)
  }
})
