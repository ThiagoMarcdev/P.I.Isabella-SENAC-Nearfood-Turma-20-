# core/views.py

from django.shortcuts import render
from .api_client import buscar_restaurantes_na_api
from .models import Restaurante

def buscar_restaurantes(request):
    resultados = []

    if request.method == 'POST':
        prato = request.POST.get('prato')
        api_resultados = buscar_restaurantes_na_api(prato)

        for r in api_resultados:
            # Cria no banco se ainda não existir
            Restaurante.objects.get_or_create(
                nome=r['nome'],
                endereco=r['endereco'],
                nota=r['nota']
            )
        
        resultados = Restaurante.objects.filter(nome__icontains=prato)

    return render(request, 'buscar.html', {'restaurantes': resultados})
