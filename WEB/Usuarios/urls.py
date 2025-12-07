from django.urls import path
from .views import cadastrar_usuario, LoginView

urlpatterns = [
    path('login-interno/', views.login, name='login'),
    path('api/usuarios/', views.cadastrar_usuario, name='cadastrar_usuario'),
    path('login/', views.LoginView.as_view(), name='LoginView'),
]
