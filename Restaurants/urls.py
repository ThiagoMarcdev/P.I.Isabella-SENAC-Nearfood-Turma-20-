from django.urls import path # função responsavel apra criar urls
from . import views # impostar arquivos do views de restaurantes para ca.. esse "." serve para indicar que eh nesta pasta


urlpatterns = [
    path('acessar_restaurantes/', views.acessar_home),
    path('buscar_restaurantes/', views.buscar_restaurantes)
]