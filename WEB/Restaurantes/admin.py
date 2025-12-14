from django.contrib import admin

# Register your models here.
from django.contrib import admin
from .models import Restaurant, Categoria, Promocao

admin.site.register(Restaurant)
admin.site.register(Categoria)
admin.site.register(Promocao)