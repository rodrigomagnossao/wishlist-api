<h1>Serviço de wishlist</h1>

<b>Swagger:</b></br>
http://localhost:8080/swagger-ui.html
</br>
</br>
<b>Banco NoSQL:</b></br>
MongoDB conectado no Atlas (cloud)
</br>
</br>
<b>Teste Unitário:</b></br>
MongdoDB Embedded

</br>
</br>

<b> Para rodar o projeto, fazer o clone do repositório e rodar local, executando os endpoints via swagger ou postman. Os dados de conexão do banco estão no application.properties </b>

-------------------------------------------------------------------------------------------------------------------------

<h1>Atualização Branch feature/remove_user_after_delete</h1>
Branch que remove o registro do usuário do banco de dados quando ele remove todos os produtos da lista.
</br>
Antes, ele mantinha o usuário com a lista vazia
