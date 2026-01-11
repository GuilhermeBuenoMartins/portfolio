
# Scriptiva

Um programa leve focado em tornar tarefas de escrita mais rápidas, gerando linhas para mensagens e relatórios.

## Visão Geral

Scriptiva é um programa baseado em terminal desenvolvido em C++, e pode ser personalizado através de arquivos externos. Assim, o usuário não precisa programar — basta alterar um parâmetro para obter um novo comportamento.

Muitos fluxos de conversa podem ser desencadeados a partir de uma pergunta inicial. Expressões regulares (regex) ajudam a identificar palavras-chave nas respostas e controlar os fluxos de conversa. Uma palavra-chave correspondente leva à próxima pergunta. Quando não há próxima pergunta, a conclusão é gerada.

Uma conclusão pode ser uma mensagem final ou um parágrafo contendo dados capturados de perguntas anteriores. Como texto no terminal, pode ser copiado e colado onde o usuário desejar.

## Como usar

1. **Compile a aplicação**
	- Execute o script de build no terminal:
	  ```bash
	  model=(dialog question response)
	  util=(converter csv table)
	  control=(chat)
	  output=()
	  mkdir output
	  g++ -c app.cpp -o output/app.o
	  output="output/app.o"
	  for file in ${control[@]}; do
		  g++ -c control/${file}.cpp -o output/${file}.o
		  output="${output} output/${file}.o"
	  done
	  for file in ${model[@]}; do
		  g++ -c model/${file}.cpp -o output/${file}.o
		  output="${output} output/${file}.o"
	  done
	  for file in ${util[@]}; do
		  g++ -c util/${file}.cpp -o output/${file}.o
		  output="${output} output/${file}.o"
	  done
	  g++ ${output} -o app
	  rm -drf output
	  ```
	- Isso irá compilar o código-fonte e gerar o executável [app](app).

2. **Prepare seus arquivos de dados**
	- Certifique-se de que os seguintes arquivos CSV estejam presentes no diretório do projeto:
	  - [questions.csv](questions.csv) — Contém as perguntas do fluxo.
	  - [responses.csv](responses.csv) — Contém as possíveis respostas.
	  - [dialogs.csv](dialogs.csv) — Define os fluxos de conversa.
	- Você pode personalizar esses arquivos para alterar o comportamento e o conteúdo da aplicação.

3. **Execute a aplicação**
	- Execute o programa no terminal:
	  ```bash
	  ./app
	  ```
	- Siga as instruções no terminal. Digite suas respostas conforme solicitado.
	- Para continuar após uma sessão, digite `y` quando solicitado. Para sair, digite qualquer outra tecla.

4. **Copie e utilize a conclusão gerada**
	- Ao final do fluxo, a aplicação exibirá uma mensagem ou parágrafo de conclusão.
	- Você pode copiar esse texto e utilizá-lo onde quiser.

> Para personalização avançada, edite os arquivos CSV para definir novas perguntas, respostas e fluxos de conversa. Não é necessário programar!
