from django.db import models

# Create your models here.
class Restaurant(models.Model):
    #id = models.AutoField (primary_key=True) # o django cria automaticamente um campo id como uma pk autofield
    nome = models.CharField(max_length=100)
    description = models.TextField(max_length=200, blank=True, null=True)
    estado = models.CharField(max_length=100)
    endereco = models.CharField(max_length=100)
    hora_funcionamento = models.CharField(max_length=50)
    delivery = models.BooleanField(default=False)
    telefone = models.IntegerField()
    website = models.URLField(blank=True, null=True)
    avaliacao = models.FloatField(default=0.0) #precisa ser float porque pode ser decimal o numero

    created_at = models.DateTimeField(auto_now_add=True)  # Data de criação do registro
    updated_at = models.DateTimeField(auto_now=True)  # Data da última atualização

    def __str__(self):
        return self.name #isso mostrara no site o nome do restaurante e não um padrao do django
    ##############################fim da tabela restaurante##################################################

