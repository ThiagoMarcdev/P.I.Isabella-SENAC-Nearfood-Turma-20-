from rest_framework import serializers
from .models import Usuario # Importando seu modelo correto

class UsuarioSerializer(serializers.ModelSerializer):
    class Meta:
        model = Usuario
        # Listamos os campos exatos que o Java envia/recebe
        fields = ['id', 'username', 'first_name', 'last_name', 'email', 'telefone', 'password']
        
        # Configuramos a senha para não ser devolvida no GET (segurança)
        # e não ser obrigatória no PUT (caso o usuário edite só o telefone)
        extra_kwargs = {
            'password': {'write_only': True, 'required': False},
            'username': {'read_only': True}, # Geralmente não deixamos mudar o username
        }

    def update(self, instance, validated_data):
        # Capturamos a senha separadamente para criptografar
        password = validated_data.pop('password', None)
        
        # Se o Java enviou uma nova senha, criptografamos e salvamos
        if password:
            instance.set_password(password)

        # Atualiza os outros campos (telefone, nome, email) automaticamente
        return super().update(instance, validated_data)