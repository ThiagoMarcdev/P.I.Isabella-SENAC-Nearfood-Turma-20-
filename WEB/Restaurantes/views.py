
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
    
    from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.views.decorators.http import require_http_methods
from django.core.mail import send_mail
from django.contrib.auth.models import User
from django.contrib.auth.hashers import make_password
from .models import PasswordResetToken
import json

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
from django.views.decorators.csrf import csrf_exempt
from django.views.decorators.http import require_http_methods
from django.core.mail import send_mail
from django.contrib.auth.models import User
from django.contrib.auth.hashers import make_password
from .models import PasswordResetToken
import json

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


    @require_http_methods(["POST"])
    def definir_nova_senha(request):
        try:
            data = json.loads(request.body)
            token = data.get('token')
            password = data.get('password')
            
            # Verificar token
            try:
                reset_token = PasswordResetToken.objects.get(token=token)
            except PasswordResetToken.DoesNotExist:
                return JsonResponse({
                    'success': False,
                    'message': 'Token inválido.'
                }, status=400)
            
            # Verificar se token está válido
            if not reset_token.is_valid():
                return JsonResponse({
                    'success': False,
                    'message': 'Token expirado ou já utilizado.'
                }, status=400)
            
            # Atualizar senha
            user = reset_token.user
            user.password = make_password(password)
            user.save()
            
            # Marcar token como usado
            reset_token.used = True
            reset_token.save()
            
            return JsonResponse({
                'success': True,
                'message': 'Senha redefinida com sucesso!'
            })
            
        except Exception as e:
            return JsonResponse({
                'success': False,
                'message': f'Erro ao redefinir senha: {str(e)}'
            }, status=500)