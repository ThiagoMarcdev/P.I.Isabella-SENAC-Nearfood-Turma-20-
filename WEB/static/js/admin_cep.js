document.addEventListener("DOMContentLoaded", function() {
    // O Django Admin dá IDs padrão aos campos: id_nome_do_campo
    const cepInput = document.getElementById("id_cep");
    
    if (cepInput) {
        cepInput.addEventListener("blur", function() {
            // Remove tudo que não é número
            let cep = this.value.replace(/\D/g, '');

            if (cep.length === 8) {
                // Feedback visual que está buscando
                document.getElementById("id_endereco").value = "Buscando...";

                // Consulta a API do ViaCEP
                fetch(`https://viacep.com.br/ws/${cep}/json/`)
                    .then(response => response.json())
                    .then(data => {
                        if (!data.erro) {
                            // Preenche os campos do Django Admin
                            // Nota: Verifique se os nomes dos seus campos no Model batem com esses IDs
                            document.getElementById("id_endereco").value = data.logradouro; 
                            // O ViaCEP retorna a sigla (SP), mas o Nominatim prefere o nome.
                            // Mas geralmente ele entende a sigla. Vamos testar com a sigla.
                            document.getElementById("id_estado").value = data.uf; 
                            
                            // Foco no campo número (se você tiver um campo numero separado, 
                            // se não, o usuário completa no endereço)
                            // Sugestão: Crie um alert ou foco para lembrar do número
                            alert("Endereço encontrado! Por favor, adicione o NÚMERO ao endereço.");
                        } else {
                            alert("CEP não encontrado.");
                        }
                    })
                    .catch(error => {
                        console.error("Erro na requisição:", error);
                        alert("Erro ao buscar CEP.");
                    });
            }
        });
    }
});