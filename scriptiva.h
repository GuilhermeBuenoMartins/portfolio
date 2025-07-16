#include <fstream>
#include <iostream>
#include <regex>
#include <string>
#include <vector>

using namespace std;
// Scriptiva Model
namespace scpvaM
{
    std::string readFile(std::string filename);

#pragma region Classes

    class Table
    {
    private:
        vector<vector<string>> cells;
        static vector<vector<string>> build(string content);

    public:
        Table(string content);
        ~Table();
    };
#pragma endregion

#pragma region Implementations

    /// @brief Read a file from the filename.
    /// @param filename Path with file name.
    /// @return Content of file.
    string readFile(std::string filename)
    {
        std::ifstream inputFile(filename);
        if (!inputFile.is_open())
        {
            std::cerr << "Error opening file " << filename << std::endl;
            return nullptr;
        }
        std::string content = "";
        std::string line;
        while (std::getline(inputFile, line))
        {
            content += line + "\n\r";
        }
        return content;
    }

    /// @brief  Split a string text from a string pattern.
    /// @param s String text.
    /// @param p String pattern.
    /// @return String vector containing result.
    vector<string> split(const string s, const string p)
    {
        vector<string> vec;
        int i = 0;
        int j = 0;
        while (s.length() >= i + p.length())
        {
            if (s.compare(i, p.length(), p) == 0)
            {
                vec.push_back(s.substr(j, i - j));
                i += p.length();
                j = i;
            }
            i++;
        }
        return vec;
    }

#pragma region Implementations: Table
    Table::Table(std::string filename)
    {
    }

    Table::~Table()
    {
    }

    inline vector<vector<string>> Table::build(string content)
    {
        vector<vector<string>> cells;
        // TODO: Implement the function code.
        return cells;
    }
#pragma endregion Implmentations : Table
#pragma endregion Implementaions

} // namespace scpvaM

// Scriptiva Control
namespace scpvaC
{

} // namespace scpvaC

// Scriptiva View
namespace scpvaV
{

} // namespace scpvaV
