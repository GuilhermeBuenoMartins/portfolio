#include <fstream>
#include <iostream>
#include <regex>
#include <string>
#include <vector>

using namespace std;
// Scriptiva Model
namespace scpvaM
{
    inline const string brl = "\n\r";

    string readFile(string filename);
    vector<string> split(const string s, const string p);

#pragma region Classes

    class Table
    {
    private:
        vector<vector<string>> cells;

    public:
        Table();
        Table(vector<vector<string>> c);
        ~Table();
        static Table create(string content, string p);
        string get(int i, int j) const;
        string get(string header, int i) const;
        int getHeader(string header) const;
        string getHeader(int i) const;
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

    /// @brief Default constructor.
    Table::Table() {}

    /// @brief Alternative constructor.
    /// @param cells_ Cells.
    Table::Table(vector<vector<string>> cls) : cells(cls) {}

    Table::~Table() {}

    /// @brief Create an instance of Table.
    /// @param content String content of the CSV file.
    /// @param sep String that separates columns.
    /// @return An instance of Table.
    inline Table Table::create(string content, string sep)
    {
        vector<vector<string>> cells;
        vector<string> lines = split(content, brl);
        for (int i = 0; i < lines.size(); i++)
        {
            cells.push_back(split(lines[i], sep));
        }
        return Table(cells);
    }

    inline string Table::get(int i, int j) const {
        return cells[i][j];
    }

    inline string Table::getHeader(int i) const {
        return get(0, i);
    }

    inline int Table::getHeader(string header) const {
        for (int j = 0; j < cells[0].size(); j++)
        {
            if (cells[0][j] == header) { return j; }
        }
        return -1;
    }

    inline string Table::get(string header, int i) const {
        int j = getHeader(header);
        return get(i, j);
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
