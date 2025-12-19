from django.urls import path
from . import views

urlpatterns = [
    path('login-interno/', views.login_view, name='login'),
    path('cadastro/', views.cadastro_view, name='cadastrar_usuario'),
    path('logout/', views.fazerLogout, name='logout'),
    path('api/login/', views.api_login, name='api_login'),
    path('api/cadastro/', views.api_cadastro, name='api_cadastro'),
    path('api/usuarios/<int:pk>/', views.UsuarioDetailView.as_view(), name='usuario-detail'),
    
    path('  /', views.exibir_receber_token, name='esqueci'),
    path('enviar-token/', views.enviar_token, name='enviar_token'),
    path("reset/<str:token>/", views.exibir_reset_senha, name="reset"),
    path('reset-confirmar/<str:token>/', views.salvar_nova_senha, name='reset_confirmar'),
    
]
