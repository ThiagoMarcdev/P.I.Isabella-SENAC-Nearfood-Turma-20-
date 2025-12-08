from django.conf import settings
from django.shortcuts import redirect, render
from django.contrib.auth import authenticate, login
from Usuarios.models import TokenResetSenha, Usuario
from django.contrib.auth import get_user_model
import uuid
from django.contrib.auth.hashers import make_password
from django.core.mail import send_mail

def login_view(request):
    if request.method == 'POST':
        user = authenticate(request, username=request.POST['username'], password=request.POST['password'])
        if user:
            login(request, user)
            return redirect('index')
    return render(request, 'login.html')


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

def reset_senha(request):
  pass

