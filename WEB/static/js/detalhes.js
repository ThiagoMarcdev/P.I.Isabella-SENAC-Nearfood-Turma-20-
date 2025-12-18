document.addEventListener("DOMContentLoaded", function () {
    console.log("--- Página de Detalhes Iniciada ---");

    //Inicializa o Tema (Dark/Light)
    carregarTema();
    const themeBtn = document.getElementById("themeToggle");
    if (themeBtn) {
        themeBtn.addEventListener("click", alternarTema);
    }

    initMap();
    const reservaBtn = document.querySelector(".reserve-detail-btn");
    if (reservaBtn) {
        reservaBtn.addEventListener("click", fazerReserva);
    }
});

/*
   LÓGICA DO MAPA (Leaflet + OpenStreetMap)
*/
function initMap() {
    // A variável RESTAURANTE_DATA foi definida no HTML pelo Django
    if (typeof RESTAURANTE_DATA === 'undefined') {
        console.error("Erro: Dados do restaurante não encontrados.");
        return;
    }

    const mapContainer = document.getElementById('map');

    // Verifica se a div do mapa existe
    if (!mapContainer) return;

    // Verifica se temos as coordenadas
    if (!RESTAURANTE_DATA.lat || !RESTAURANTE_DATA.lon) {
        console.warn("Restaurante sem coordenadas. Mapa ocultado.");
        mapContainer.innerHTML = `
            <div style="height:100%; display:flex; flex-direction:column; align-items:center; justify-content:center; background:#f0f0f0; color:#666; border-radius:12px;">
                <span style="font-size: 2rem; margin-bottom: 10px;">🗺️</span>
                <p>Localização não cadastrada no sistema.</p>
            </div>
        `;
        return;
    }

    console.log(` Renderizando mapa para: ${RESTAURANTE_DATA.nome} [${RESTAURANTE_DATA.lat}, ${RESTAURANTE_DATA.lon}]`);

    try {
        //Cria o mapa
        var map = L.map('map').setView([RESTAURANTE_DATA.lat, RESTAURANTE_DATA.lon], 15);

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '© OpenStreetMap'
        }).addTo(map);

      
        var marker = L.marker([RESTAURANTE_DATA.lat, RESTAURANTE_DATA.lon]).addTo(map);

        
        marker.bindPopup(`<b>${RESTAURANTE_DATA.nome}</b><br>📍 ${RESTAURANTE_DATA.nome}`).openPopup();

    } catch (error) {
        console.error("Erro ao inicializar o Leaflet:", error);
        mapContainer.innerHTML = "<p style='text-align:center; padding:20px;'>Erro ao carregar o mapa.</p>";
    }
}

// temas
function carregarTema() {
    const temaSalvo = localStorage.getItem("tema") || "light";
    document.documentElement.setAttribute("data-theme", temaSalvo);
    
    const btn = document.getElementById("themeToggle");
    if (btn) btn.textContent = temaSalvo === "dark" ? "🌙" : "☀️";
}

function alternarTema() {
    const html = document.documentElement;
    const temaAtual = html.getAttribute("data-theme");
    const novoTema = temaAtual === "dark" ? "light" : "dark";

    html.setAttribute("data-theme", novoTema);
    localStorage.setItem("tema", novoTema);

    const btn = document.getElementById("themeToggle");
    if (btn) btn.textContent = novoTema === "dark" ? "🌙" : "☀️";
}

function fazerReserva() {
    alert("Funcionalidade de Reserva será implementada no backend em breve!");
}