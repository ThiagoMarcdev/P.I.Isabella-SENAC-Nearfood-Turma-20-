from django.shortcuts import render #função para renderizar templates ou seja: renderizar interfaces (html, css...)
from django.http import HttpResponse #função para retornar mensagem em http
from .models import Restaurant #importando models de restaurantes


# Create your views here.
# aqui vai estar toda a "logica do site"

def acessar_home(request):
    return render(request, 'home_restaurant.html')

def buscar_restaurantes(request):
    return render(
        request,
        'busca2.html',
        {'Restaurant': {'nome': '*'}}

    
    )