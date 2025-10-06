from django.urls import path
from . import viewsUsuarios

urlpatterns = [
    path('login/', viewsUsuarios.login, name='login'),
]