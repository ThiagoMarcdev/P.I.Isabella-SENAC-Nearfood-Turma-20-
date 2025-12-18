from django.contrib import admin
from .models import ItemCardapio, Restaurant, Categoria, Promocao

class ItemCardapioInline(admin.TabularInline):
    model = ItemCardapio
    extra = 1 

class RestaurantAdmin(admin.ModelAdmin):
    list_display = ('nome', 'cep', 'cidade_estado', 'latitude', 'longitude') 
   
    list_filter = ('estado', 'categorias')
    
    # Barra de pesquisa
    search_fields = ('nome', 'cep')
    
    # Adiciona o cardápio dentro da edição do restaurante
    inlines = [ItemCardapioInline]
    class Media:
        
        js = ('js/admin_cep.js',) 

    
    def cidade_estado(self, obj):
        return f"{obj.endereco} - {obj.estado}"
    cidade_estado.short_description = "Localização"

# --- REGISTROS ---
admin.site.register(Restaurant, RestaurantAdmin)
admin.site.register(Categoria)
admin.site.register(Promocao)
