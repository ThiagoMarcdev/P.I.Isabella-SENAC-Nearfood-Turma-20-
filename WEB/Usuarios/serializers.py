from rest_framework import serializers
from .models import Cliente, Dono

class UsuarioSerializer(serializers.ModelSerializer):
    # Password deve ser write_only por segurança
    password = serializers.CharField(write_only=True)

    class Meta:
        model = Usuario
        fields = ['id', 'username', 'first_name', 'last_name', 'email', 'password', 'telefone', 'tipo']

    def create(self, validated_data):
        # 1. Cria o Usuário Base com segurança
        user = Usuario.objects.create_user(
            username=validated_data['username'],
            password=validated_data['password'],
            email=validated_data.get('email', ''),
            first_name=validated_data.get('first_name', ''),
            last_name=validated_data.get('last_name', ''),
            telefone=validated_data.get('telefone', ''),
            tipo=validated_data.get('tipo', 'cliente')
        )

        # 2. Cria o perfil correspondente
        if user.tipo == 'cliente':
            Cliente.objects.create(usuario=user)
        elif user.tipo == 'admin':
            Administrador.objects.create(usuario=user)
        
        return user

class UsuarioRetornoSerializer(serializers.ModelSerializer):
    class Meta:
        model = Usuario
        fields = ['id', 'username', 'email', 'first_name', 'last_name', 'telefone', 'tipo']