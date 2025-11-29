from django.db import models
from django.contrib.auth.models import User
import uuid
from django.utils import timezone
from datetime import timedelta

# Create your models here.

class Categoria(models.Model):
    nome = models.CharField(max_length=50)
    # Para o ícone, guardamos a classe do FontAwesome, por exemplo: "fa-solid fa-burger"
    icone = models.CharField(max_length=50, blank=True)
    
    class Meta:
        db_table='tbl_Categoria'

    def __str__(self):
        return self.nome

class Restaurant(models.Model):
    #id = models.AutoField (primary_key=True) # O Django cria automaticamente
    nome = models.CharField(max_length=100)
    descricao = models.CharField(max_length=255, blank=True, null=True) # Mudado para CharField
    estado = models.CharField(max_length=100)
    endereco = models.CharField(max_length=100)
    hora_funcionamento = models.CharField(max_length=50)
    delivery = models.BooleanField(default=False)
    telefone = models.CharField(max_length=30)
    website = models.URLField(max_length=255, blank=True, null=True)
    avaliacao = models.DecimalField(max_digits=3, decimal_places=1, default=0.0) # Mudado para DecimalField
    latitude = models.DecimalField(max_digits=9, decimal_places=6, blank=True, null=True) # Mudado para DecimalField
    longitude = models.DecimalField(max_digits=9, decimal_places=6, blank=True, null=True) # Mudado para DecimalField
    categorias = models.ManyToManyField('Categoria') # Perfeito usando a string!
    #recomendado = models.BooleanField(default=False)
    
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)
    
    class Meta:
        db_table='tbl_Restaurantes'

    def __str__(self):
        return self.nome

class Promocao(models.Model):
    titulo = models.CharField(max_length=100) # Ex: "NA SUA PRÓXIMA RESERVA"
    desconto_percentual = models.IntegerField() # Ex: 50
    # Relaciona a promoção a um restaurante específico
    restaurante = models.ForeignKey('Restaurant', on_delete=models.CASCADE)
    ativo = models.BooleanField(default=True)
    
    
    class Meta:
        db_table='tbl_Promocoes'

    def __str__(self):
        return f"{self.desconto_percentual}% OFF em {self.restaurante.nome}"
    

    class PasswordResetToken(models.Model):
        user = models.ForeignKey(User, on_delete=models.CASCADE)
        token = models.UUIDField(default=uuid.uuid4, unique=True)
        created_at = models.DateTimeField(auto_now_add=True)
        used = models.BooleanField(default=False)
    
    def is_valid(self):
        # Token expira em 1 hora
        expiration_time = self.created_at + timedelta(hours=1)
        return not self.used and timezone.now() < expiration_time
    
    def __str__(self):
        return f"Token para {self.user.email}"