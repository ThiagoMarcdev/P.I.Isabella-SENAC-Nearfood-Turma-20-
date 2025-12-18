from django.contrib import admin

# Register your models here.
from django.contrib import admin
from .models import ItemCardapio, Restaurant, Categoria, Promocao

# Configuração Personalizada do Admin para Restaurante
class RestaurantAdmin(admin.ModelAdmin):
    # Campos que aparecem na lista
    list_display = ('nome', 'cep', 'latitude', 'longitude') 
    
    # Adicionando o JavaScript personalizado
    class Media:
        js = ('js/admin_cep.js',) # Caminho dentro da pasta static

# Registra o model com a configuração personalizada
admin.site.register(Restaurant, RestaurantAdmin)
admin.site.register(Categoria)
admin.site.register(Promocao)

admin.site.register(ItemCardapio) # cadastrar cadarpio do restaurante