// Dados completos dos restaurantes (incluindo informações extras)
const restaurantesCompletos = [
  {
    id: 1,
    nome: "Bella Cucina",
    tipo: "Italiano",
    emoji: "🍝",
    avaliacao: 4.5,
    endereco: "Avenida João Celos, 123",
    enderecoCompleto: "Avenida João Celos, 123 - Centro",
    cidadeEstado: "São Paulo, SP - CEP: 01234-567",
    descricao:
      "Autêntica culinária italiana com receitas tradicionais passadas de geração em geração. Nossos pratos são preparados com ingredientes frescos importados diretamente da Itália. Desfrute de uma experiência gastronômica única em um ambiente acolhedor e sofisticado.",
    cardapio: [
      { nome: "Pizza Margherita", preco: "R$ 45,00", emoji: "🍕" },
      { nome: "Lasanha Bolonhesa", preco: "R$ 52,00", emoji: "🍝" },
      { nome: "Risoto de Funghi", preco: "R$ 58,00", emoji: "🍚" },
      { nome: "Tiramisù", preco: "R$ 22,00", emoji: "🍰" },
    ],
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
    enderecoCompleto: "Rua das Flores, 456 - Jardim Oriental",
    cidadeEstado: "São Paulo, SP - CEP: 02345-678",
    descricao:
      "Culinária chinesa autêntica em um ambiente zen e tranquilo. Nossos chefs especializados preparam pratos tradicionais com técnicas milenares. Experimente sabores únicos que transportam você para o coração da China.",
    cardapio: [
      { nome: "Yakissoba", preco: "R$ 38,00", emoji: "🍜" },
      { nome: "Spring Roll", preco: "R$ 25,00", emoji: "🥟" },
      { nome: "Frango Xadrez", preco: "R$ 42,00", emoji: "🍗" },
      { nome: "Arroz Frito", preco: "R$ 28,00", emoji: "🍚" },
    ],
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
    enderecoCompleto: "Avenida Premium Steak, 789 - Vila Gourmet",
    cidadeEstado: "São Paulo, SP - CEP: 03456-789",
    descricao:
      "A melhor churrascaria da cidade com carnes nobres e cortes especiais. Nosso rodízio oferece variedade incomparável de carnes grelhadas no ponto perfeito. Ambiente elegante ideal para celebrações especiais.",
    cardapio: [
      { nome: "Picanha Premium", preco: "R$ 89,00", emoji: "🥩" },
      { nome: "Costela Barbecue", preco: "R$ 75,00", emoji: "🍖" },
      { nome: "Fraldinha", preco: "R$ 68,00", emoji: "🥓" },
      { nome: "Rodízio Completo", preco: "R$ 95,00", emoji: "🍽️" },
    ],
    avaliacoes: [{ nome: "Carlos Silva", data: "15/08/2023", texto: "Melhor churrasco que já comi!", avatar: "👨" }],
  },
  {
    id: 4,
    nome: "Ocean Harvest",
    tipo: "Frutos do mar",
    emoji: "🦐",
    avaliacao: 4.8,
    endereco: "Rua da Praia, 321",
    enderecoCompleto: "Rua da Praia, 321 - Beira Mar",
    cidadeEstado: "São Paulo, SP - CEP: 04567-890",
    descricao:
      "Frutos do mar frescos todos os dias direto do mar para sua mesa. Especialistas em peixes e crustáceos preparados com técnicas contemporâneas. Viva uma experiência oceânica única.",
    cardapio: [
      { nome: "Moqueca de Peixe", preco: "R$ 78,00", emoji: "🐟" },
      { nome: "Camarão ao Alho", preco: "R$ 85,00", emoji: "🦐" },
      { nome: "Risoto de Frutos do Mar", preco: "R$ 92,00", emoji: "🦞" },
      { nome: "Sushi Especial", preco: "R$ 68,00", emoji: "🍣" },
    ],
    avaliacoes: [{ nome: "Marina Costa", data: "10/09/2023", texto: "Frutos do mar fresquíssimos!", avatar: "👩" }],
  },
  {
    id: 5,
    nome: "Green Plate",
    tipo: "Vegano",
    emoji: "🥗",
    avaliacao: 4.4,
    endereco: "Avenida Verde, 654",
    enderecoCompleto: "Avenida Verde, 654 - Eco Park",
    cidadeEstado: "São Paulo, SP - CEP: 05678-901",
    descricao:
      "Culinária vegana criativa e saborosa que surpreende até os não-veganos. Ingredientes orgânicos e locais em pratos coloridos e nutritivos. Prove que comida saudável pode ser deliciosa.",
    cardapio: [
      { nome: "Bowl Tropical", preco: "R$ 35,00", emoji: "🥗" },
      { nome: "Hambúrguer Vegano", preco: "R$ 42,00", emoji: "🍔" },
      { nome: "Wrap de Falafel", preco: "R$ 38,00", emoji: "🌯" },
      { nome: "Smoothie Detox", preco: "R$ 18,00", emoji: "🥤" },
    ],
    avaliacoes: [{ nome: "Julia Santos", data: "05/10/2023", texto: "Opções veganas incríveis!", avatar: "👩" }],
  },
  {
    id: 6,
    nome: "Fiesta Cantina",
    tipo: "Mexicano",
    emoji: "🌮",
    avaliacao: 4.5,
    endereco: "Rua México, 987",
    enderecoCompleto: "Rua México, 987 - Vila Latina",
    cidadeEstado: "São Paulo, SP - CEP: 06789-012",
    descricao:
      "Sabores autênticos do México em um ambiente festivo e colorido. Tacos, burritos e margaritas preparados com receitas tradicionais. Sinta-se em uma verdadeira fiesta mexicana.",
    cardapio: [
      { nome: "Tacos al Pastor", preco: "R$ 32,00", emoji: "🌮" },
      { nome: "Burrito Supreme", preco: "R$ 45,00", emoji: "🌯" },
      { nome: "Quesadilla", preco: "R$ 38,00", emoji: "🫓" },
      { nome: "Nachos Especiais", preco: "R$ 28,00", emoji: "🧀" },
    ],
    avaliacoes: [{ nome: "Diego Martinez", data: "20/11/2023", texto: "Tacos autênticos e deliciosos!", avatar: "👨" }],
  },
]

function renderizarCardapio(cardapio) {
  const menuGrid = document.getElementById("menuGrid")
  if (!menuGrid) return

  menuGrid.innerHTML = cardapio
    .map(
      (item) => `
        <div class="menu-item">
            <span class="menu-emoji">${item.emoji}</span>
            <div class="menu-info">
                <h4>${item.nome}</h4>
                <p class="menu-price">${item.preco}</p>
            </div>
        </div>
    `,
    )
    .join("")
}

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

  const enderecoCompletoEl = document.getElementById("enderecoCompleto")
  const cidadeEstadoEl = document.getElementById("cidadeEstado")
  if (enderecoCompletoEl) enderecoCompletoEl.textContent = restaurante.enderecoCompleto
  if (cidadeEstadoEl) cidadeEstadoEl.textContent = restaurante.cidadeEstado

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

  renderizarCardapio(restaurante.cardapio)

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
    `📅 Reserva marcada no ${restaurante.nome}!\n\nEndereço: ${restaurante.endereco}\n\nEm breve você receberá um e-mail de confirmação.`,
  )
}

function abrirGoogleMaps() {
  const restauranteId = Number.parseInt(localStorage.getItem("restauranteAtual"))
  const restaurante = restaurantesCompletos.find((r) => r.id === restauranteId)

  if (restaurante) {
    alert(
      `🗺️ Abrindo rota para ${restaurante.nome} no Google Maps...\n\nEndereço: ${restaurante.enderecoCompleto}\n${restaurante.cidadeEstado}\n\nIsso abriria o Google Maps em uma aplicação real.`,
    )
  }
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

  const reservaBtn = document.getElementById("btnReservaDetalhes")
  if (reservaBtn) {
    reservaBtn.addEventListener("click", fazerReserva)
  }

  const mapsBtn = document.getElementById("btnAbrirMaps")
  if (mapsBtn) {
    mapsBtn.addEventListener("click", abrirGoogleMaps)
  }

  // Botão de tema
  const themeBtn = document.getElementById("themeToggle")
  if (themeBtn) {
    themeBtn.addEventListener("click", alternarTema)
  }
})

window.restaurantesCompletosData = restaurantesCompletos
