# Scriptiva

A lightweight program focused on making writing tasks faster, generating lines for messages and reports.

## Overview

Scriptiva is a terminal-based program developed in C++, and it can be customized through external files. Thus, a user does not even need to code — simply changing a parameter is enough to get a new behavior.

Many conversation flows can be unleashed from an initial question. The regular expressions (regex) help identify keywords in the answers and control the conversation flows. A matched keyword is responsible for leading to the next question. When there is no next question, the conclusion is generated.

A conclusion can be a final message or a paragraph containing captured data from previous questions. As text in the terminal, it can be copied and pasted anywhere the user wants.

## How to use

1. **Build the application**
   - Run the build script in your terminal:
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
   - This will compile the source code and generate the executable [app](app).

2. **Prepare your data files**
   - Ensure the following CSV files are present in the project directory:
     - [questions.csv](questions.csv) — Contains the questions for the flow.
     - [responses.csv](responses.csv) — Contains possible responses.
     - [dialogs.csv](dialogs.csv) — Defines the conversation flows.
   - You can customize these files to change the behavior and content of the application.

3. **Run the application**
   - Execute the program in your terminal:
     ```bash
     ./app
     ```
   - Follow the prompts in the terminal. Type your answers as requested.
   - To continue after a session, enter `y` when prompted. To exit, enter any other key.

4. **Copy and use the generated conclusion**
   - At the end of the flow, the application will display a conclusion message or paragraph.
   - You can copy this text and use it wherever you need.

> For advanced customization, edit the CSV files to define new questions, responses, and conversation flows. No coding required!