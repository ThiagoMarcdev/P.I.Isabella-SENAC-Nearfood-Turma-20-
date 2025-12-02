from django.urls import path # função responsavel apra criar urls
from . import views # impostar arquivos do views de restaurantes para ca.. esse "." serve para indicar que eh nesta pasta


urlpatterns = [
    path('buscar_restaurantes/', views.buscar_restaurantes),
    path('' , views.acessar_home, name='home'),
    path('api/restaurantes-proximos/', views.api_restaurantes_proximos, name='api_restaurantes_proximos'),    
     path('', views.index, name='index'),
    path('detalhes/', views.detalhes, name='detalhes'),
    path('config/', views.config, name='config'),
    path('favoritos/', views.favoritos, name='favoritos'),
     path('api/restaurantes/', views.api_restaurantes, name='api_restaurantes'),
    path('api/restaurante/<int:restaurante_id>/', views.api_restaurante_detalhes, name='api_restaurante_detalhes'),
    path('api/config/salvar/', views.api_salvar_config, name='api_salvar_config'),
    path('api/favorito/toggle/', views.api_toggle_favorito, name='api_toggle_favorito'),
]