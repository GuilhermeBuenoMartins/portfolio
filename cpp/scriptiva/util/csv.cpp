#include "../include/csv.h"

utl::Csv::Csv() : sep_(";"), brkln_("\r\n") {}

utl::Csv::Csv(const std::string &sep) : sep_(sep), brkln_("\r\n") {}

utl::Csv::Csv(const std::string &sep, const std::string &brkln) : sep_(sep), brkln_(brkln) {}

utl::Csv::~Csv() {}

utl::Table utl::Csv::read(const std::string &fn) const
{
    std::ifstream file(fn);
    if (!file.is_open())
    {
        throw std::runtime_error("It could not open the file " + fn + ".");
    }
    std::vector<std::vector<std::string>> cells;
    std::string line;
    while (std::getline(file, line))
    {
        size_t begin = 0;
        size_t end = line.find(sep_, begin);
        std::vector<std::string> row;
        while (end != std::string::npos)
        {
            row.push_back(line.substr(begin, end - begin));
            begin = end + sep_.length();
            end = line.find(sep_, begin);
        }
        cells.push_back(row);
    }
    return Table(cells);
}