#include <string>
#include <iostream>
#include "scriptiva.h"

using namespace std;

int main(int argc, char const *argv[])
{
    const string brline = "\n\r";
    const string content = scpvaM::readFile("sample.txt");
    const vector<string> vec = scpvaM::split(content, "\n\r");
    for (string s: vec)
    {
        std::cout << s <<std::endl;
    }
    return 0;
}
