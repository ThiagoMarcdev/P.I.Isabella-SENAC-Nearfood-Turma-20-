from django.db import models

# Create your models here.
from django.contrib.auth.models import AbstractUser
from django.db import models
from django.core.validators import RegexValidator

class Usuario(AbstractUser):
    TIPOS_USUARIO = [
        ('cliente', 'Cliente'),
        ('fornecedor', 'Fornecedor'),
        ('admin', 'Administrador'),
    ]

    tipo = models.CharField(max_length=20, choices=TIPOS_USUARIO)

    # Campos já herdados: username, email, password, first_name, last_name, is_active, etc.

    def __str__(self):
        return f"{self.username} ({self.get_tipo_display()})"


class Cliente(models.Model):
    usuario = models.OneToOneField(Usuario, on_delete=models.CASCADE)
    
    cpf_validator = RegexValidator(
        regex=r'^\d{3}\.\d{3}\.\d{3}-\d{2}$',
        message='CPF deve estar no formato XXX.XXX.XXX-XX'
    )
    cpf = models.CharField(max_length=14, unique=True, validators=[cpf_validator])
    telefone = models.CharField(max_length=20, blank=True)
    endereco = models.TextField(blank=True)

    def __str__(self):
        return self.usuario.username


class Fornecedor(models.Model):
    usuario = models.OneToOneField(Usuario, on_delete=models.CASCADE)
    
    cnpj_validator = RegexValidator(
        regex=r'^\d{2}\.\d{3}\.\d{3}/\d{4}-\d{2}$',
        message='CNPJ deve estar no formato XX.XXX.XXX/XXXX-XX'
    )
    cnpj = models.CharField(max_length=18, unique=True, validators=[cnpj_validator])
    telefone = models.CharField(max_length=20, blank=True)
    empresa = models.CharField(max_length=100)
    endereco = models.TextField(blank=True)

    def __str__(self):
        return self.empresa


class Administrador(models.Model):
    usuario = models.OneToOneField(Usuario, on_delete=models.CASCADE)
    
    telefone = models.CharField(max_length=20, blank=True)
    cargo = models.CharField(max_length=50, blank=True)

    def __str__(self):
        return f"Admin: {self.usuario.username}"
