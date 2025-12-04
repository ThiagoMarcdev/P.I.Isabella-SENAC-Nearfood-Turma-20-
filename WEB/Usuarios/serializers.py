from rest_framework import serializers
from .models import Usuario, Cliente

class UsuarioSerializer(serializers.ModelSerializer):    
    telefone = serializers.CharField(write_only=True, required=False)
    #endereco = serializers.CharField(write_only=True, required=False)
    
    class Meta:
        model = Usuario
        fields = ['id', 'username', 'first_name', 'last_name', 'email', 'password', 'telefone', 'tipo']
        extra_kwargs = {
            'password': {'write_only': True}
        }

    def create(self, validated_data):
        telefone = validated_data.pop('telefone', '')
        #endereco = validated_data.pop('endereco', '')
        
        user = Usuario.objects.create_user(
            username=validated_data['username'],
            first_name=validated_data.get('first_name', ''),
            last_name=validated_data.get('last_name', ''),
            email=validated_data.get('email', ''),
            password=validated_data['password'],
            telefone=validated_data.get('telefone', ''),
            tipo=validated_data.get('tipo', 'cliente'),          
        )
        return user


class ClienteSerializer(serializers.ModelSerializer):
    usuario = UsuarioSerializer()

    class Meta:
        model = Cliente
        fields = ['usuario',  'telefone']

    def create(self, validated_data):
        usuario_data = validated_data.pop('usuario')
        user = Usuario.objects.create_user(**usuario_data)
        cliente = Cliente.objects.create(usuario=user, **validated_data)
        return cliente


class UsuarioRetornoSerializer(serializers.ModelSerializer):
    class Meta:
        model = Usuario
        fields = ['id', 'username', 'email', 'first_name', 'last_name']  # ajuste conforme seu model