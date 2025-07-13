# Scriptiva

A lightweight program focused on making writing tasks faster, generating lines for messages and reports.

## Overview

Scriptiva is a terminal-based program developed in C++, and it can be customized through external files. Thus, a user does not even need to code — simply changing a parameter is enough to get a new behavior.

Many conversation flows can be unleashed from an initial question. The regular expressions (regex) help identify keywords in the answers and control the conversation flows. A matched keyword is responsible for leading to the next question. When there is no next question, the conclusion is generated.

A conclusion can be a final message or a paragraph containing captured data from previous questions. As text in the terminal, it can be copied and pasted anywhere the user wants.