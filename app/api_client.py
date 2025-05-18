# core/api_client.py

def buscar_restaurantes_na_api(termo):
    # Aqui estamos simulando uma resposta da API
    dados = [
        {'nome': 'Pizzaria do João', 'endereco': 'Rua A, 123', 'nota': 4.5},
        {'nome': 'Cantina da Mama', 'endereco': 'Av. B, 456', 'nota': 4.2},
    ]

    # Filtra se o nome do restaurante tiver o termo
    return [rest for rest in dados if termo.lower() in rest['nome'].lower()]
