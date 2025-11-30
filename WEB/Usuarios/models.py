from django.db import models
from django.contrib.auth.models import AbstractUser, Group, Permission

class Usuario(AbstractUser):
    TIPOS_USUARIO = [
        ('cliente', 'Cliente'),
        ('admin', 'Administrador'),
        ('root', 'Root'),
    ]

    email = models.EmailField(unique=True)
    USERNAME_FIELD = "email"
    REQUIRED_FIELDS = []
    tipo = models.CharField(max_length=20, choices=TIPOS_USUARIO, default='cliente')
    telefone = models.CharField(max_length=20, blank=True) 

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
        db_table = 'tbl_Usuarios'

    def __str__(self):
        return f"{self.username} ({self.get_tipo_display()})"


class Cliente(models.Model):
    usuario = models.OneToOneField(Usuario, on_delete=models.CASCADE)
    telefone = models.CharField(max_length=20, blank=True)

    class Meta:
        db_table = 'tbl_Clientes'

    def __str__(self):
        return self.usuario.username


class Administrador(models.Model):
    usuario = models.OneToOneField(Usuario, on_delete=models.CASCADE)
    telefone = models.CharField(max_length=20, blank=True)
    cargo = models.CharField(max_length=50, blank=True)
    cnpj = models.CharField(max_length=30, blank=True)

    class Meta:
        db_table = 'tbl_Administradores'

    def __str__(self):
        return f"Administrador: {self.usuario.username}"
