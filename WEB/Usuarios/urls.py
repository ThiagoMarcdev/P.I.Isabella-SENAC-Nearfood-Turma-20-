from django.urls import path
from . import views

urlpatterns = [
    path('login-interno/', views.login_view, name='login'),
    path('cadastro/', views.cadastro_view, name='cadastrar_usuario'),
    
    path('  /', views.exibir_receber_token, name='esqueci'),
    path('enviar-token/', views.enviar_token, name='enviar_token'),
    path('reset/<str:token>/', views.exibir_reset_senha, name='reset_senha'),
    path('reset-confirmar/<str:token>/', views.salvar_nova_senha, name='reset_confirmar'),
]
