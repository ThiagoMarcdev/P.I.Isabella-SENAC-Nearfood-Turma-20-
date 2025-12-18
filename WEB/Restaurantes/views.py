
from math import asin, cos, radians, sin, sqrt
from django.contrib import messages as flash_messages # Damos um apelido para evitar conflito
from django.shortcuts import get_object_or_404, redirect, render
from django.http import JsonResponse
from .models import Avaliacao, ItemCardapio, Restaurant, Promocao, Categoria 
from geopy.distance import geodesic
import json
from django.views.decorators.csrf import csrf_exempt
from django.views.decorators.http import require_http_methods
from django.core.mail import send_mail
from django.contrib.auth.models import User
from django.contrib.auth.hashers import make_password
from .models import PasswordResetToken
from django.contrib.auth.decorators import login_required
from django.db.models import Avg

def haversine(lon1, lat1, lon2, lat2):
    # Converter graus decimais em radianos
    lon1, lat1, lon2, lat2 = map(radians, [lon1, lat1, lon2, lat2])

    # Fórmula de Haversine
    dlon = lon2 - lon1 
    dlat = lat2 - lat1 
    a = sin(dlat/2)**2 + cos(lat1) * cos(lat2) * sin(dlon/2)**2
    c = 2 * asin(sqrt(a)) 
    r = 6371 # Raio da Terra em quilômetros
    return c * r

@login_required # verifica se login esta feito
def acessar_home(request):
    lat_user = request.GET.get('lat')
    lon_user = request.GET.get('lon')
    
    restaurantes_proximos = []
    
    todos_restaurantes = Restaurant.objects.all()

    if lat_user and lon_user:
        try:
            lat_user = float(lat_user)
            lon_user = float(lon_user)
            
            for restaurante in todos_restaurantes:
                # Só calcula se o restaurante tiver coordenadas cadastradas
                if restaurante.latitude and restaurante.longitude:
                    distancia = haversine(
                        lon_user, lat_user,
                        float(restaurante.longitude), float(restaurante.latitude)
                    )
                    
                    # FILTRO: Raio de 10km
                    if distancia <= 10:
                        restaurante.distancia_temp = round(distancia, 1)
                        restaurantes_proximos.append(restaurante)
            
           
            restaurantes_proximos.sort(key=lambda x: x.distancia_temp)
            
        except ValueError:
            # Se vier lixo na URL, não quebra o site
            pass
    else:
        # Se o usuário negou localização, mostra tudo ou uma lista padrão
        restaurantes_proximos = todos_restaurantes  
    
    
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
        'restaurantes_gerais': restaurantes_gerais,
        'restaurantes_proximos': restaurantes_proximos,
    }
    return render(request, 'index1.html', contexto)

@login_required # login aqui é necessario para trazer informações especificas daquele usuario
def favoritos(request):
    meus_favoritos = request.user.favoritos.all()
    contexto = {
        'meus_favoritos': meus_favoritos
    }
    
    return render(request, 'favoritos1.html', contexto)

def buscar_restaurantes(request):
    return render(
        request,
        'busca.html',
        {'Restaurant': {'nome': '*'}}    
    )

def detalhes(request, id):
    restaurante = get_object_or_404(Restaurant, id=id)
    
    # Lógica de POST (Salvar/Atualizar Avaliação)
    if request.method == 'POST':
        if not request.user.is_authenticated:
            return redirect('login') # Segurança extra
            
        try:
            nova_nota = int(request.POST.get('nota'))
            novo_comentario = request.POST.get('comentario')
            
            # --- A MÁGICA DO UPSERT ---
            # Procura por (restaurante + usuario). 
            # Se achar, atualiza os campos em 'defaults'. Se não, cria.
            avaliacao, created = Avaliacao.objects.update_or_create(
                restaurante=restaurante,
                usuario=request.user,
                defaults={
                    'nota': nova_nota,
                    'comentario': novo_comentario
                }
            )
            
            # Recalcular média
            avaliacoes = restaurante.avaliacoes.all()
            media = avaliacoes.aggregate(Avg('nota'))['nota__avg']
            
            # Atualiza o restaurante
            restaurante.avaliacao = round(media, 1) if media else 0
            restaurante.save()
            
            # Feedback para o usuário
            if created:
                flash_messages.success(request, 'Sua avaliação foi publicada!') # <--- Mudou aqui
            else:
                flash_messages.info(request, 'Sua avaliação foi atualizada!') # <--- Mudou aqui
                
        except ValueError:
            flash_messages.error(request, 'Erro ao processar a nota.') # <--- Mudou aqui
                
        return redirect('detalhes', id=id)

    # --- GET (Exibir Página) ---
    avaliacoes = restaurante.avaliacoes.all().order_by('-data') # Mais recentes primeiro
    cardapio = ItemCardapio.objects.filter(restaurante=restaurante)
    
    is_favorito = False
    if request.user.is_authenticated:
        is_favorito = restaurante.favoritos.filter(id=request.user.id).exists()

    context = {
        'restaurante': restaurante,
        'cardapio': cardapio,
        'avaliacoes': avaliacoes,
        'is_favorito': is_favorito,
    }
    return render(request, 'detalhe.html', context)


    
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
    
    from django.http import JsonResponse


@require_http_methods(["POST"])
def solicitar_reset_senha(request):
    try:
        data = json.loads(request.body)
        email = data.get('email')
        
        # Verificar se email existe
        try:
            user = User.objects.get(email=email)
        except User.DoesNotExist:
            return JsonResponse({
                'success': False,
                'message': 'Email não encontrado em nossa base de dados.'
            }, status=404)
        
        # Criar token
        token = PasswordResetToken.objects.create(user=user)
        
        # Montar link de reset
        reset_link = f"http://seusite.com/reset-senha-nova.html?token={token.token}"
        
        # Enviar email
        send_mail(
            'Recuperação de Senha',
            f'Clique no link para redefinir sua senha: {reset_link}',
            'noreply@seusite.com',
            [email],
            fail_silently=False,
        )
        
        return JsonResponse({
            'success': True,
            'message': 'Email enviado com sucesso!'
        })
        
    except Exception as e:
        return JsonResponse({
            'success': False,
            'message': f'Erro ao processar solicitação: {str(e)}'
        }, status=500)


        from django.http import JsonResponse

@login_required
def toggle_favorito(request, id):
    restaurante = get_object_or_404(Restaurant, id=id)
    usuario = request.user
    
    if restaurante in usuario.favoritos.all():
        usuario.favoritos.remove(restaurante)
    else:
        usuario.favoritos.add(restaurante)
        
    return redirect('detalhes', id=id)