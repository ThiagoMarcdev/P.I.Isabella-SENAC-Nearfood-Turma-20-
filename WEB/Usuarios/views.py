from django.shortcuts import render
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from .serializers import UsuarioSerializer, UsuarioRetornoSerializer
from rest_framework.views import APIView
from django.contrib.auth import authenticate


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
    authentication_classes = []
    permission_classes = []

    def post(self, request, *args, **kwargs):
        email = request.data.get('email')
        password = request.data.get('password')

        if not email or not password:
            return Response(
                {"error": "email and password required"},
                status=status.HTTP_400_BAD_REQUEST
            )

        # IMPORTANTE: usar username=email, pois USERNAME_FIELD=email
        user = authenticate(username=email, password=password)

        if user is None:
            return Response(
                {"error": "Email ou senha inválidos"},
                status=status.HTTP_401_UNAUTHORIZED
            )

        serializer = UsuarioRetornoSerializer(user)
        return Response(
            {"message": "Login OK", "user": serializer.data},
            status=status.HTTP_200_OK
        )
