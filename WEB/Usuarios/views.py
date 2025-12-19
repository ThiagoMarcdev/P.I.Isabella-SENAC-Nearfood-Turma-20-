import json
from django.conf import settings
from django.http import JsonResponse
from django.shortcuts import redirect, render
from django.contrib.auth import authenticate, login
from Usuarios.models import Dono, TokenResetSenha, Usuario
from django.contrib.auth import get_user_model
import uuid
from django.contrib.auth.hashers import make_password
from django.core.mail import send_mail
from django.contrib.auth import logout
from django.views.decorators.csrf import csrf_exempt
from django.views.decorators.http import require_http_methods
from django.contrib.auth.decorators import login_required
from django.contrib.auth import update_session_auth_hash
from rest_framework.permissions import AllowAny, IsAuthenticated
from rest_framework import generics

from Usuarios.serializers import UsuarioSerializer

def login_view(request):
    if request.method == 'POST':
        user = authenticate(request, username=request.POST['username'], password=request.POST['password'])
        if user:
            login(request, user)
            return redirect('index')
    return render(request, 'login.html')

def fazerLogout(request): # desloga o usuario do site
    logout(request) # limpa a sessão
    return redirect('login') # redireciona para tela login


def cadastro_view(request):
    if request.method == 'POST':
        nome = request.POST['firstName']
        username = request.POST['username']
        email = request.POST['email']
        telefone = request.POST['telefone']
        senha = request.POST['senha']
        confirmSenha = request.POST['confirmSenha']

        if senha != confirmSenha:
            return render(request, 'cadastro.html', {"erro": "As senhas não coincidem"})

        user = Usuario.objects.create_user(
            username=username,
            email=email,
            password=senha,
        )

        user.first_name = nome
        user.telefone = telefone
        user.tipo = "cliente"
        user.save()

        login(request, user)
        return redirect('login')

    return render(request, 'cadastro.html')




def exibir_receber_token(request):
    return render(request, 'recebeToken.html')

    # chamar função para enviar link de redefinição de senha
    
    
User = get_user_model()

def enviar_token(request):
    if request.method == "POST":
        email = request.POST.get('email')

        try:
            usuario = User.objects.get(email=email)
        except User.DoesNotExist:
            return render(request, 'recebeToken.html', {
                "erro": "Email não encontrado."
            })

        # gerar token único
        token = str(uuid.uuid4())

        TokenResetSenha.objects.create(usuario=usuario, token=token)

        link = request.build_absolute_uri(f"/usuarios/reset/{token}/")

        # enviar email — precisa configurar EMAIL_BACKEND
        send_mail(
            subject="Redefinir sua senha",
            message=f"Clique no link para redefinir sua senha:\n{link}",
            from_email=settings.DEFAULT_FROM_EMAIL,
            recipient_list=[email],
        )

        return render(request, 'recebeToken.html', {
            "sucesso": "Enviamos o link de redefinição para seu email."
        })
    
def exibir_reset_senha(request, token):
    from .models import TokenResetSenha

    try:
        token_obj = TokenResetSenha.objects.get(token=token)
    except TokenResetSenha.DoesNotExist:
        return render(request, 'resetSenhaNova.html', {"erro": "Link inválido."})

    if token_obj.expirado():
        return render(request, 'resetSenhaNova.html', {"erro": "Link expirado."})

    return render(request, 'resetSenhaNova.html', {"token": token})

def salvar_nova_senha(request, token):
    if request.method == "POST":
        senha1 = request.POST.get("password")
        senha2 = request.POST.get("confirm_password")

        if senha1 != senha2:
            return render(request, 'resetSenhaNova.html', {
                "erro": "As senhas não coincidem.",
                "token": token
            })

        try:
            token_obj = TokenResetSenha.objects.get(token=token)
        except TokenResetSenha.DoesNotExist:
            return render(request, 'resetSenhaNova.html', {"erro": "Token inválido."})

        usuario = token_obj.usuario

        # alterar senha corretamente
        usuario.set_password(senha1)
        usuario.save()

        # apagar token usado
        token_obj.delete()

        # redirecionar para login
        return redirect("login")

    return redirect("esqueci")

@csrf_exempt  
@require_http_methods(["POST"])
def api_login(request):
    try:
        data = json.loads(request.body)
        
        username_java = data.get('username') 
        password_java = data.get('password')
        
        if not username_java or not password_java:
            return JsonResponse({
                'authenticated': False, 
                'message': 'Usuário e senha são obrigatórios'
            }, status=400)

        # verifica credenciais (Username e Senha)
        user = authenticate(request, username=username_java, password=password_java)

        if user is not None:
            # O usuário é do tipo 'dono'?
            if user.tipo == 'dono':
                # SUCESSO: É dono e a senha está certa
                return JsonResponse({
                    'authenticated': True,
                    'message': 'Login de Dono realizado com sucesso',
                    'user_id': user.id,
                    'nome': user.first_name or user.username
                }, status=200)
            
            else:
                # BLOQUEIO: A senha está certa, mas é um Cliente tentando entrar no App de Dono
                return JsonResponse({
                    'authenticated': False,
                    'message': 'Acesso restrito. Este aplicativo é apenas para parceiros/donos.'
                }, status=403) # 403 = Proibido
        
        else:
            # ERRO: Usuário não existe ou senha errada
            return JsonResponse({
                'authenticated': False,
                'message': 'Credenciais inválidas'
            }, status=401)

    except json.JSONDecodeError:
        return JsonResponse({'error': 'JSON inválido'}, status=400)
    except Exception as e:
        return JsonResponse({'error': str(e)}, status=500)


@csrf_exempt  
@require_http_methods(["POST"])
def api_cadastro(request):
    try:
        data = json.loads(request.body)
        
        #  Extração dos dados (Tentando pegar tanto camelCase do Java quanto snake_case)
        # O Java manda 'username' já concatenado conforme sua lógica
        username_java = data.get('username') or data.get('user') 
        password_java = data.get('password') or data.get('senha')
        email = data.get('email')
        telefone = data.get('telefone')
        
        # Pega firstName (Java) ou first_name (Python)
        first_name = data.get('firstName') or data.get('first_name')
        # Pega lastName (Java) ou last_name (Python)
        last_name = data.get('lastName') or data.get('last_name')
        
        #  Validação de campos obrigatórios
        # CNPJ e Restaurante não são validados aqui pois o banco aceita NULL agora
        if not all([first_name, last_name, email, telefone, username_java, password_java]):
            return JsonResponse({
                'success': False, 
                'message': 'Todos os campos (Nome, Sobrenome, Email, Tel, User, Senha) são obrigatórios.'
            }, status=400)

        #Verificações de duplicidade
        if Usuario.objects.filter(username=username_java).exists():
            return JsonResponse({'success': False, 'message': 'Nome de usuário já está em uso.'}, status=400)
        
        if Usuario.objects.filter(email=email).exists():
            return JsonResponse({'success': False, 'message': 'Email já cadastrado.'}, status=400)

        # Criação do Usuário DONO
        
        novo_dono = Dono.objects.create_user(
            username=username_java,
            email=email,
            password=password_java,
            tipo='dono',            
            first_name=first_name,
            last_name=last_name,
            cnpj=None,              
            restaurante=None        
        )
        
        # Salva o telefone e confirma a gravação
        novo_dono.telefone = telefone
        novo_dono.save()

        return JsonResponse({
            'success': True,
            'message': 'Cadastro realizado com sucesso!',
            'user_id': novo_dono.id
        }, status=201)

    except json.JSONDecodeError:
        return JsonResponse({'success': False, 'message': 'JSON inválido enviado pelo Java.'}, status=400)
    except Exception as e:
        return JsonResponse({'success': False, 'message': f'Erro interno no servidor: {str(e)}'}, status=500)
    
    
@login_required
def config(request):
    
    if request.method == 'POST':
        try:
            data = json.loads(request.body)
            user = request.user
           
            novo_email = data.get('email')
            if novo_email and novo_email != user.email:
               
                user.email = novo_email
            
            nova_senha = data.get('nova_senha')
            senha_atual = data.get('senha_atual')
            
            if nova_senha:
                if not senha_atual:
                    return JsonResponse({'status': 'error', 'message': 'Para mudar a senha, informe a senha atual.'}, status=400)
                
                if not user.check_password(senha_atual):
                    return JsonResponse({'status': 'error', 'message': 'A senha atual está incorreta.'}, status=400)
                
                if nova_senha != data.get('confirmar_senha'):
                    return JsonResponse({'status': 'error', 'message': 'As novas senhas não coincidem.'}, status=400)

               
                user.set_password(nova_senha)
                
                update_session_auth_hash(request, user)
            
            user.save()
            return JsonResponse({'status': 'success', 'message': 'Dados atualizados com sucesso!'})

        except Exception as e:
            return JsonResponse({'status': 'error', 'message': f'Erro interno: {str(e)}'}, status=500)

    
    return render(request, 'config.html')


# logica de alteração de dados na aplicação java
class UsuarioDetailView(generics.RetrieveUpdateAPIView):
    # Aponta para o seu modelo customizado
    queryset = Usuario.objects.all()
    serializer_class = UsuarioSerializer
    
    # Para facilitar os testes iniciais com o Java, deixe AllowAny
    # Depois mude para IsAuthenticated quando implementar o Login com Token
    permission_classes = [AllowAny]