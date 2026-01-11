#include "include/chat.h"
#include "include/converter.h"
#include "include/csv.h"

#include <iostream>

const std::string RESPS_FN = "responses.csv";
const std::string QSTNS_FN = "questions.csv";
const std::string DLGS_FN = "dialogs.csv";

int main()
{
    std::cout << "======================================" << std::endl;
    std::cout << "            Scriptiva" << std::endl;
    std::cout << "--------------------------------------" << std::endl;
    std::cout << "A lightweight program focused on making writing tasks faster, generating lines for messages and reports." << std::endl;
    std::cout << "Terminal-based, customizable via external files. No coding required for new behaviors!" << std::endl;
    std::cout << "Conversation flows are controlled by regex keywords in answers." << std::endl;
    std::cout << "Conclusions are generated as final messages or paragraphs with captured data." << std::endl;
    std::cout << "======================================" << std::endl;

    utl::Csv csv;
    std::string answer = "";
    do
    {
        std::vector<mdl::Response> resps = utl::to_responses(csv.read(RESPS_FN));
        std::vector<mdl::Question> qstns = utl::to_questions(csv.read(QSTNS_FN));
        std::vector<mdl::Dialog> dlgs = utl::to_dialogs(csv.read(DLGS_FN), qstns, resps);
        ctr::Chat chat(dlgs);
        while (!chat.has_conclusion())
        {
            std::cout << chat.ask() << std::endl;
            std::getline(std::cin, answer);
            chat.receive(answer);
        }
        std::cout << "======================================" << std::endl;
        std::cout << "   Conclusion:" << std::endl;
        std::cout << "--------------------------------------" << std::endl;
        std::cout << chat.conclusion() << std::endl;
        std::cout << "======================================" << std::endl;
        std::cout << "Enter \"y\" to continue: ";
        std::getline(std::cin, answer);
        std::cout << "======================================" << std::endl;
    } while (answer == "Y" || answer == "y");
    return 0;
}
