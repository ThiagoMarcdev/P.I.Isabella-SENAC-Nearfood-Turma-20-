from django.db import models


class Cliente(models.Model):
    username = models.CharField(max_length=255, blank=False, null=False, unique=True), # não pode se repetir no banco de dados
    nome = models.CharField(max_length=255, blank=True, null=True),
    email = models.EmailField(max_length=100, blank=True, null=True),
    telefone = models.CharField(max_length=20, blank=True, null=True),
    is_admin = models.BooleanField(default=False, blank=False, null=False),
    senha = models.CharField(max_length=128, blank=False, null=False),
    class Meta:
        db_table = 'tbl_Clientes'

    def __str__(self):
        return self.username
    
class Dono(models.Model):
    username = models.CharField(max_length=255, blank=False, null=False, unique=True), # não pode se repetir no banco de dados
    nome = models.CharField(max_length=255, blank=True, null=True),
    email = models.EmailField(max_length=100, blank=True, null=True),
    telefone = models.CharField(max_length=20, blank=True, null=True),
    is_admin = models.BooleanField(default=True, blank=False, null=False), # usuario admin dono do restaurante
    senha = models.CharField(max_length=128, blank=False, null=False),
    cnpj = models.CharField(max_length=128, blank=False, null=False, unique=True), # não pode se repetir no banco de dados
    
    class Meta:
        db_table = 'tbl_Donos'

    def __str__(self):
        return self.username