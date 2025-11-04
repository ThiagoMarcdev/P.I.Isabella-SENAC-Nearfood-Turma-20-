from django.shortcuts import render
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from .serializers import UsuarioSerializer, UsuarioRetornoSerializer
from rest_framework.views import APIView
from django.contrib.auth import authenticate

def login(request):
    return render(request, 'login.html')

@api_view(['POST'])
def cadastrar_usuario(request):
    serializer = UsuarioSerializer(data=request.data)
    if serializer.is_valid():
        serializer.save()
        return Response(
            {"mensagem": "Usuário cadastrado com sucesso!"},
            status=status.HTTP_201_CREATED
        )
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class LoginView(APIView):
    authentication_classes = []  # permitir sem autenticação para login
    permission_classes = []      # permitir qualquer um acessar

    def post(self, request, *args, **kwargs):
        username = request.data.get('username')
        password = request.data.get('password')

        if not username or not password:
            return Response({"error": "username and password required"}, status=status.HTTP_400_BAD_REQUEST)

        user = authenticate(username=username, password=password)
        if user is None:
            return Response({"error": "Usuário ou senha inválidos"}, status=status.HTTP_401_UNAUTHORIZED)

        # opcional: usar serializer para formatar a resposta
        serializer = UsuarioRetornoSerializer(user)
        return Response({"message": "Login OK", "user": serializer.data}, status=status.HTTP_200_OK)
