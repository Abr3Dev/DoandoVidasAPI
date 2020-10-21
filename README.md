# DoandoVidasAPI
API para o app DoandoVidas da Abr3dev

ENDPOINTS:

Cadastro Geral(Usuario e Conteúdo): 
https://localhost:8081/api/doandovidas/register

Request:/n 
![CadastroGeral1](https://user-images.githubusercontent.com/47859622/96776845-f41aa400-13bf-11eb-9e1a-5813bd129c44.png)

Response:/n
![CadastroGeralRetorno](https://user-images.githubusercontent.com/47859622/96776879-ff6dcf80-13bf-11eb-8155-ffdbf5cbee77.png)

Login(Busca do usuario pelo email e senha): 
https://localhost:8081/api/doandovidas/user/{email}/{password}

Request:/n
![Login](https://user-images.githubusercontent.com/47859622/96776986-1ad8da80-13c0-11eb-9a09-5b012b3b29dc.png)

Response:/n
![LoginRetorno](https://user-images.githubusercontent.com/47859622/96777024-26c49c80-13c0-11eb-982a-6b2a3252ccfb.png)

Atualização Geral: 
https://localhost:8081/api/doandovidas/user/{id}

Request:/n
![Atualização1](https://user-images.githubusercontent.com/47859622/96776645-ad2cae80-13bf-11eb-9d7f-141028af4d77.png)

Response:/n
![AtualizaçãoRetorno](https://user-images.githubusercontent.com/47859622/96776710-c5043280-13bf-11eb-94c7-733c74b870dd.png)

Exclusão Geral: 
https://localhost:8081/api/doandovidas/user/delete{id}

Request e Response:/n
![DeleteUsuario](https://user-images.githubusercontent.com/47859622/96776920-0c8abe80-13c0-11eb-8eae-883cfe8fbf96.png)

Cadastro Conteúdo:
https://localhost:8081/api/doandovidas/content

Request e Response:/n
![CadastroConteudo](https://user-images.githubusercontent.com/47859622/96776802-e533f180-13bf-11eb-8783-9deeca5d1f89.png)

Busca do Conteúdo pelo id: 
https://localhost:8081/api/doandovidas/content/{id}

Request e Response:/n
![BuscaConteudo](https://user-images.githubusercontent.com/47859622/96776736-cfbec780-13bf-11eb-86f5-b616431ae6cc.png)

Atualização do Conteúdo pelo id: 
https://localhost:8081/api/doandovidas/content/update/{id}

Request e Response:/n
![AtualizaçãoConteudo](https://user-images.githubusercontent.com/47859622/96776677-ba499d80-13bf-11eb-9d11-e1bae403ce02.png) 

Remoção do Conteúdo pelo id:
https://localhost:8081/api/doandovidas/content/delete/{id}

Request e Response:/n
![RemoçãoConteudo](https://user-images.githubusercontent.com/47859622/96777082-393ed600-13c0-11eb-9353-b95d875d2d17.png)
