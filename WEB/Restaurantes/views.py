
from django.shortcuts import render
from django.http import JsonResponse
from .models import Restaurant, Promocao, Categoria 
from geopy.distance import geodesic
import json

def acessar_home(request):
    """
    Esta view agora busca TODAS as informações necessárias para a página inicial
    e as envia para o template.
    """
    promocao_ativa = Promocao.objects.filter(ativo=True).first()
    todas_as_categorias = Categoria.objects.all()
    restaurantes_recomendados = Restaurant.objects.filter(recomendado=True)[:2]
    restaurantes_gerais = Restaurant.objects.order_by('?')[:6]

    contexto = {
        'promocao': promocao_ativa,
        'categorias': todas_as_categorias,
        'recomendacoes': restaurantes_recomendados,
        'restaurantes_proximos': restaurantes_gerais,
    }
    return render(request, 'templates/restaurants/index.html', contexto)


def buscar_restaurantes(request):
    return render(
        request,
        'busca2.html',
        {'Restaurant': {'nome': '*'}}    
    )
    
    
# API DE LOCALIZAÇÃO 
def api_restaurantes_proximos(request):
    try:
        data = json.loads(request.body)
        user_lat = data.get('latitude')
        # CORREÇÃO: A variável agora é 'user_lon'
        user_lon = data.get('longitude')
        
        if user_lat is None or user_lon is None:
            return JsonResponse({'error': 'Coordenadas faltando'}, status=400)

        user_location = (user_lat, user_lon)
        restaurantes = Restaurant.objects.all()
        restaurantes_proximos = []

        for restaurante in restaurantes:
            if restaurante.latitude and restaurante.longitude:
                restaurante_location = (restaurante.latitude, restaurante.longitude)
                distancia = geodesic(user_location, restaurante_location).km

                if distancia < 10:
                    restaurantes_proximos.append({
                        'id': restaurante.id,
                        'nome': restaurante.nome,
                        'descricao': restaurante.descricao,
                        'distancia_km': round(distancia, 2)
                        # Adicione outros campos do seu modelo aqui se o JavaScript precisar
                    })
                        
        restaurantes_proximos.sort(key=lambda r: r['distancia_km'])

        return JsonResponse(restaurantes_proximos, safe=False)

    except Exception as e:
        return JsonResponse({'error': str(e)}, status=500)