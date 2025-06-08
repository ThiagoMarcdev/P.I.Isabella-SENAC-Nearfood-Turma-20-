"""
URL configuration for app project.

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/5.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path, include
from Restaurants.views import buscar_restaurantes

urlpatterns = [
    path('admin/', admin.site.urls),
    path('buscar/', include ('Restaurants.urls'), name='buscar_restaurantes'),
    path('restaurantes/', include('Restaurants.urls')) # quando o usuario acessar o endereço restaurantes ele vai ser redirecionado para o app "Restaurants" e o arquivo que gerenciara isso é o urls do app restaurants
    
]
