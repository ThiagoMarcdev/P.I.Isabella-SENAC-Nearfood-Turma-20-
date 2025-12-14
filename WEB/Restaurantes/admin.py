from django.contrib import admin

# Register your models here.
from django.contrib import admin
from .models import ItemCardapio, Restaurant, Categoria, Promocao

admin.site.register(Restaurant)
admin.site.register(Categoria)
admin.site.register(Promocao)

admin.site.register(ItemCardapio) # cadastrar cadarpio do restaurante