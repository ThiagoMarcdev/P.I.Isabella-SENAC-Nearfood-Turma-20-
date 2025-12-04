from django.urls import path
from .views import cadastrar_usuario, LoginView

urlpatterns = [
    # Rotas HTML

    # Rotas da API
    path('cadastro/', cadastrar_usuario, name="cadastro_usuario"),
    path('login-api/', LoginView.as_view(), name="login_api"),
]
