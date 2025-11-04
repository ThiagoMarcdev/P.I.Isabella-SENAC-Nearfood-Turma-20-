from django.urls import path
from . import views
from .views import cadastrar_usuario
from .views import LoginView

urlpatterns = [
    path('login-interno/', views.login, name='login'),
    path('api/usuarios/', views.cadastrar_usuario, name='cadastrar_usuario'),
    path('/login/', views.LoginView.as_view(), name='LoginView'),
]