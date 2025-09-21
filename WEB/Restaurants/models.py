from django.db import models

# Create your models here.
class Restaurant(models.Model):
    #id = models.AutoField (primary_key=True) # o django cria automaticamente um campo id como uma pk autofield
    nome = models.CharField(max_length=100)
    descricao = models.TextField(max_length=200, blank=True, null=True)
    estado = models.CharField(max_length=100)
    endereco = models.CharField(max_length=100)
    hora_funcionamento = models.CharField(max_length=50)
    delivery = models.BooleanField(default=False)
    telefone = models.CharField(max_length=20)
    website = models.URLField(blank=True, null=True)
    avaliacao = models.FloatField(default=0.0) #precisa ser float porque pode ser decimal o numero
    latitude = models.FloatField(blank=True, null=True)
    longitude = models.FloatField(blank=True, null=True)
    categorias = models.ManyToManyField(Categoria)
    recomendado = models.BooleanField(default=False)
    

    created_at = models.DateTimeField(auto_now_add=True)  # Data de criação do registro
    updated_at = models.DateTimeField(auto_now=True)  # Data da última atualização

    def __str__(self):
        return self.nome #isso mostrara no site o nome do restaurante e não um padrao do django
    ##############################fim da tabela restaurante##################################################
    
    
    class Categoria(models.Model):
        nome = models.CharField(max_length=50)
        # Para o ícone, guardamos a classe do FontAwesome, por exemplo: "fa-solid fa-burger"
        icone = models.CharField(max_length=50, blank=True)

    def __str__(self):
        return self.nome
    
    class Promocao(models.Model):
        titulo = models.CharField(max_length=100) # Ex: "NA SUA PRÓXIMA RESERVA"
        desconto_percentual = models.IntegerField() # Ex: 50
        # Relaciona a promoção a um restaurante específico
        restaurante = models.ForeignKey(Restaurante, on_delete=models.CASCADE)
        ativo = models.BooleanField(default=True)

    def __str__(self):
        return f"{self.desconto_percentual}% OFF em {self.restaurante.nome}"

