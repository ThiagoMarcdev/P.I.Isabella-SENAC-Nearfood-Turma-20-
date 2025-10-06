from django.contrib import admin
from .models import Restaurant, Categoria, Promocao

# Classe de customização para o model Restaurant
class RestaurantAdmin(admin.ModelAdmin):
    # Usa os nomes exatos dos campos do models.py
    list_display = ('nome', 'descricao', 'estado', 'endereco', 'hora_funcionamento', 'delivery', 'telefone', 'website')
    search_fields = ('nome', 'estado', 'endereco')

# Registra o model Categoria (de forma simples)
admin.site.register(Categoria)

# Registra o model Promocao (de forma simples)
admin.site.register(Promocao)

# Registra o model Restaurant USANDO a classe de customização
admin.site.register(Restaurant, RestaurantAdmin)