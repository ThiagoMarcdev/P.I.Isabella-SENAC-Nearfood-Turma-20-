from django.urls import path # função responsavel apra criar urls
from . import views # impostar arquivos do views de restaurantes para ca.. esse "." serve para indicar que eh nesta pasta


urlpatterns = [
    path('', views.acessar_home, name='homePage'),
    path('buscar_restaurantes/', views.buscar_restaurantes),
    path('api/restaurantes-proximos/', views.api_restaurantes_proximos, name='api_restaurantes_proximos'),    
]