from django.db import models
from Users.models import Usuario, Fornecedor

class Review(models.Model):
    usuario = models.ForeignKey(Usuario, on_delete=models.CASCADE)
    fornecedor = models.ForeignKey(Fornecedor, on_delete=models.CASCADE)
    nota = models.PositiveSmallIntegerField(choices=[(i, i) for i in range(1, 6)])  # 1 a 5 estrelas
    comentario = models.TextField(blank=True)
    data = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return f"{self.usuario.username} avaliou {self.fornecedor.empresa} ({self.nota} estrelas)"

class Feedback(models.Model):
    STATUS_CHOICES = [
        ('pendente', 'Pendente'),
        ('lido', 'Lido'),
        ('respondido', 'Respondido'),
    ]

    usuario = models.ForeignKey(
        Usuario,
        on_delete=models.SET_NULL,
        null=True,
        blank=True,
        help_text="Usuário que enviou o feedback (opcional)"
    )
    assunto = models.CharField(max_length=150, help_text="Assunto do feedback")
    mensagem = models.TextField(help_text="Mensagem escrita pelo usuário")
    data_envio = models.DateTimeField(auto_now_add=True)
    status = models.CharField(
        max_length=10,
        choices=STATUS_CHOICES,
        default='pendente',
        help_text="Status do feedback"
    )
    resposta = models.TextField(
        blank=True,
        help_text="Resposta da equipe ao feedback"
    )

    def __str__(self):
        if self.usuario:
            return f"Feedback de {self.usuario.username} - {self.assunto}"
        