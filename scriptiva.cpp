#include <string>
#include <iostream>
#include "scriptiva.h"

using namespace std;

int main(int argc, char const *argv[])
{
    const string brline = "\n\r";
    const string content = scpvaM::readFile("sample.txt");
    const scpvaM::Table table = scpvaM::Table::create(content, ";");
    cout << table.get(table.getHeader(2), 1) << endl;
    return 0;
}
