from django.contrib import admin
from .models import Restaurant

# Register your models here.
class RestaurantAdmin(admin.ModelAdmin):
    list_display = ('name', 'description', 'estado', 'endereco', 'hora_funcionamento', 'delivery', 'telefone', 'website')
    search_fields = ('name', 'estado', 'endereco')