
// // Função para alternar tema
function alternarTema() {
  const html = document.documentElement
  const temaAtual = html.getAttribute("data-theme")
  const novoTema = temaAtual === "dark" ? "light" : "dark"

  html.setAttribute("data-theme", novoTema)
  localStorage.setItem("tema", novoTema)

  //   // Atualizar ícone do botão
  const btn = document.getElementById("themeToggle")
  if (btn) {
    btn.textContent = novoTema === "dark" ? "🌙" : "☀️"
  }
}


// // Variável para armazenar a categoria selecionada
// let categoriaAtual = null

// // Função para filtrar por categoria
// function filtrarPorCategoria(categoria) {
//   categoriaAtual = categoria

//   const resultados = categoriaAtual ? restaurantes.filter((rest) => rest.categoria === categoriaAtual) : restaurantes

//   const grid = document.getElementById("restaurantesGrid")
//   if (!grid) return

//   document.querySelectorAll(".category-btn").forEach((btn) => {
//     if (btn.textContent.includes(categoria)) {
//       btn.classList.add("active")
//     } else {
//       btn.classList.remove("active")
//     }
//   })

//   if (resultados.length === 0) {
//     grid.innerHTML =
//       '<p style="grid-column: 1/-1; text-align: center; color: var(--text-secondary);">Nenhum restaurante encontrado nesta categoria</p>'
//     return
//   }

//   grid.innerHTML = resultados
//     .map(
//       (rest) => `
//         <div class="restaurant-card" onclick="abrirModal(${rest.id})">
//             <div class="restaurant-logo">${rest.emoji}</div>
//             <h4>${rest.nome}</h4>
//             <p>${rest.tipo}</p>
//         </div>
//     `,
//     )
//     .join("")
// }

// function buscarRestaurantes() {
//   const input = document.getElementById("searchInput")
//   if (!input) return

//   const termo = input.value.toLowerCase()
//   const listaFiltrada = categoriaAtual ? restaurantes.filter((rest) => rest.categoria === categoriaAtual) : restaurantes
//   const resultados = termo
//     ? listaFiltrada.filter((rest) => rest.nome.toLowerCase().includes(termo) || rest.tipo.toLowerCase().includes(termo))
//     : listaFiltrada

//   const grid = document.getElementById("restaurantesGrid")
//   if (!grid) return

//   if (resultados.length === 0) {
//     grid.innerHTML =
//       '<p style="grid-column: 1/-1; text-align: center; color: var(--text-secondary);">Nenhum restaurante encontrado</p>'
//     return
//   }

//   grid.innerHTML = resultados
//     .map(
//       (rest) => `
//         <div class="restaurant-card" onclick="abrirModal(${rest.id})">
//             <div class="restaurant-logo">${rest.emoji}</div>
//             <h4>${rest.nome}</h4>
//             <p>${rest.tipo}</p>
//         </div>
//     `,
//     )
//     .join("")
// }

// // Carregar tema salvo
function carregarTema() {
  const temaSalvo = localStorage.getItem("tema") || "light"
  document.documentElement.setAttribute("data-theme", temaSalvo)

  const btn = document.getElementById("themeToggle")
  if (btn) {
    btn.textContent = temaSalvo === "dark" ? "🌙" : "☀️"
  }
}

// // Event Listeners
// document.addEventListener("DOMContentLoaded", () => {
//   console.log("[v0] DOM carregado, iniciando aplicação")
//   carregarTema()
//   renderizarRestaurantes()
//   renderizarRecomendacoes()

//   // Botão de tema
//   const themeBtn = document.getElementById("themeToggle")
//   if (themeBtn) {
//     themeBtn.addEventListener("click", alternarTema)
//   }

//   // Busca
//   const searchInput = document.getElementById("searchInput")
//   if (searchInput) {
//     searchInput.addEventListener("input", buscarRestaurantes)
//   }

//   // Event listeners para botões de categoria
//   document.querySelectorAll(".category-btn").forEach((btn) => {
//     btn.addEventListener("click", (e) => {
//       const texto = e.target.textContent.trim()
//       const categoria = texto.split(" ")[1] // Pega o texto depois do emoji

//       // Se clicar na categoria já ativa, remove o filtro
//       if (btn.classList.contains("active")) {
//         categoriaAtual = null
//         btn.classList.remove("active")
//         renderizarRestaurantes()
//       } else {
//         filtrarPorCategoria(categoria)
//       }
//     })
//   })

//   const modalClose = document.getElementById("modalClose")
//   const modalOverlay = document.getElementById("modalOverlay")
//   const btnVerRota = document.getElementById("btnVerRota")
//   const btnFazerReserva = document.getElementById("btnFazerReserva")
//   const btnVerMais = document.getElementById("btnVerMais")

//   if (modalClose) {
//     modalClose.addEventListener("click", fecharModal)
//   }

//   if (modalOverlay) {
//     modalOverlay.addEventListener("click", (e) => {
//       if (e.target === modalOverlay) {
//         fecharModal()
//       }
//     })
//   }

//   if (btnVerRota) {
//     btnVerRota.addEventListener("click", verRota)
//   }

//   if (btnFazerReserva) {
//     btnFazerReserva.addEventListener("click", fazerReserva)
//   }

//   if (btnVerMais) {
//     btnVerMais.addEventListener("click", verMaisDetalhes)
//   }
// })

// // Exportar dados para uso em outras páginas
// // window.restaurantesData = restaurantes
// // window.recomendacoesData = recomendacoes

document.addEventListener("DOMContentLoaded", function () {
  // Verifica se o navegador suporta geolocalização
  if (navigator.geolocation) {

    // Verifica se JÁ temos as coordenadas na URL para evitar loop infinito de recarregamento
    const urlParams = new URLSearchParams(window.location.search);
    if (!urlParams.has('lat') || !urlParams.has('lon')) {

      // Solicita a posição
      navigator.geolocation.getCurrentPosition(function (position) {
        const lat = position.coords.latitude;
        const lon = position.coords.longitude;

        // Recarrega a página passando latitude e longitude na URL
        // O Django vai capturar isso no request.GET
        window.location.search = `?lat=${lat}&lon=${lon}`;
      }, function (error) {
        console.warn("Usuário negou a localização ou erro ocorreu:", error.message);
        // Opcional: Mostrar alerta pedindo para ativar localização
      });
    }
  } else {
    console.log("Geolocalização não é suportada por este navegador.");
  }
});
