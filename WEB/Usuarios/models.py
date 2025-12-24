from django.db import models
from django.contrib.auth.models import AbstractUser
from django.contrib.auth import get_user_model
import uuid
from datetime import timedelta
from django.utils import timezone

class Usuario(AbstractUser):
    tipo = models.CharField(max_length=10, choices=[('cliente', 'Cliente'), ('dono', 'Dono')])
    telefone = models.CharField(max_length=20, blank=True, null=True)
    favoritos = models.ManyToManyField('Restaurantes.Restaurant', related_name='favoritado_por', blank=True)

    class Meta:
        db_table = 'tbl_usuarios'

    def __str__(self):
        return self.username

class Dono(Usuario):
    cnpj = models.CharField(max_length=128, unique=True, blank=True, null=True)
    restaurante = models.CharField(max_length=255, blank=True, null=True)

    class Meta:
        db_table = 'tbl_donos'

    def __str__(self):
        return self.username

User = get_user_model()

class TokenResetSenha(models.Model):
    usuario = models.ForeignKey(User, on_delete=models.CASCADE)
    token = models.CharField(max_length=200, unique=True)
    criado_em = models.DateTimeField(auto_now_add=True)

    def expirado(self):
        return self.criado_em < timezone.now() - timedelta(hours=1)