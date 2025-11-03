from django.urls import path
from . import views
from .views import cadastrar_usuario

urlpatterns = [
    path('login/', views.login, name='login'),
    path('api/usuarios/', views.cadastrar_usuario, name='cadastrar_usuario'),
]