from django.db import models
from django.contrib.auth.models import AbstractUser
from django.core.validators import RegexValidator

from django.db import models
# Importe também Group e Permission
from django.contrib.auth.models import AbstractUser, Group, Permission
from django.core.validators import RegexValidator

class Usuario(AbstractUser):
    TIPOS_USUARIO = [
        ('cliente', 'Cliente'),
        ('admin', 'Administrador'),
        ('root', 'Root'),
    ]

    tipo = models.CharField(max_length=20, choices=TIPOS_USUARIO, default='cliente')
    # para cadastrar usuario é necessario usar especificar se é um usuario do tipo cliente ou administrador = dono
    
    groups = models.ManyToManyField(
        Group,
        verbose_name='groups',
        blank=True,
        help_text='Os grupos aos quais este usuário pertence.',
        related_name="usuario_set",
        related_query_name="user",
    )
    user_permissions = models.ManyToManyField(
        Permission,
        verbose_name='user permissions',
        blank=True,
        help_text='Permissões específicas deste usuário.',
        related_name="usuario_permission_set",
        related_query_name="user",
    )
    
    class Meta:
        db_table='tbl_Usuarios'

    def __str__(self):
        # Corrigi o f-string para usar self.username, que é o campo padrão de login
        return f"{self.username} ({self.get_tipo_display()})"

class Cliente(models.Model): # usando o abstract ja herda campos como username email e senha
    usuario = models.OneToOneField(Usuario, on_delete=models.CASCADE)
    
    validador_cpf = RegexValidator(
        regex=r'^\d{3}\.\d{3}\.\d{3}-\d{2}$',
        message='CPF deve estar no formato XXX.XXX.XXX-XX'
    )
    #cpf = models.CharField(max_length=14, unique=True, validators=[validador_cpf])
    telefone = models.CharField(max_length=20, blank=True)
    endereco = models.TextField(blank=True)
    
    
    class Meta:
        db_table='tbl_Clientes'
        # campos: id, username, email, password; herdado| custom; cpf, telefone, endereco

    def __str__(self):
        return self.usuario.username



class Administrador(models.Model):
    usuario = models.OneToOneField(Usuario, on_delete=models.CASCADE)
    telefone = models.CharField(max_length=20, blank=True)
    cargo = models.CharField(max_length=50, blank=True)
    cnpj = models.CharField(max_length=30, blank=True)
    
    
    class Meta:
        db_table='tbl_Administradores'

    def __str__(self):
        return f"Administrador: {self.usuario.username}"