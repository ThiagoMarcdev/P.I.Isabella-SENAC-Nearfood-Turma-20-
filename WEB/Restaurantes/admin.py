from django.contrib import admin
from .models import Avaliacao, ItemCardapio, Restaurant, Categoria, Promocao

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


@admin.register(Avaliacao)
class AvaliacaoAdmin(admin.ModelAdmin):
    list_display = ('restaurante', 'usuario', 'nota', 'data_formatada')
    list_filter = ('nota', 'restaurante')
    search_fields = ('restaurante__nome', 'usuario__username', 'comentario')
    readonly_fields = ('data',)

    def data_formatada(self, obj):
        return obj.data.strftime("%d/%m/%Y %H:%M")
    data_formatada.short_description = 'Data'