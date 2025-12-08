

document.addEventListener('DOMContentLoaded', () => {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(buscarRestaurantes, erroLocalizacao);
    } else {
        console.log("Geolocalização não é suportada neste navegador.");
    }
});

function buscarRestaurantes(posicao) {
    const latitude = posicao.coords.latitude;
    const longitude = posicao.coords.longitude;

    // 2. Chamar nossa API Django com as coordenadas
    fetch('/api/restaurantes-proximos/', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            // Importante: Django requer isso para segurança em POSTs
            'X-CSRFToken': getCookie('csrftoken') 
        },
        body: JSON.stringify({ latitude: latitude, longitude: longitude })
    })
    .then(response => response.json())
    .then(data => {
        // 3. Montar o carrossel com os dados recebidos
        const carrosselDiv = document.getElementById('carrossel-proximos');
        carrosselDiv.innerHTML = ''; // Limpa qualquer conteúdo anterior

        if (data.length === 0) {
            carrosselDiv.innerHTML = '<p>Nenhum restaurante encontrado perto de você.</p>';
            return;
        }

        data.forEach(restaurante => {
            const card = document.createElement('div');
            card.className = 'restaurante-card'; // Dê um estilo para este card no seu CSS
            card.innerHTML = `
                <img src="/caminho/para/imagem/${restaurante.id}.jpg" alt="${restaurante.nome}">
                <h3>${restaurante.nome}</h3>
                <p>${restaurante.distancia_km} km de distância</p>
            `;
            carrosselDiv.appendChild(card);
        });
    })
    .catch(error => console.error('Erro ao buscar restaurantes:', error));
}

function erroLocalizacao() {
    console.log("Não foi possível obter a localização.");
    // Informe o usuário sobre o erro
}

// Função auxiliar para pegar o CSRF token do Django
function getCookie(name) {
    let cookieValue = null;
    if (document.cookie && document.cookie !== '') {
        const cookies = document.cookie.split(';');
        for (let i = 0; i < cookies.length; i++) {
            const cookie = cookies[i].trim();
            if (cookie.substring(0, name.length + 1) === (name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;

    alert("Codigo rodou!");
    console.log("rodou");
}