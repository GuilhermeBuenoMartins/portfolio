#ifndef CSV_H
#define CSV_H

#include "table.h"

#include <exception>
#include <fstream>

namespace utl
{
    class Csv
    {
    private:
        std::string sep_;
        std::string brkln_;
    public:
        Csv();
        Csv(const std::string &sep);
        Csv(const std::string &sep, const std::string &brkln_);

        ~Csv();

        Table read(const std::string &fn) const;
    };
} // namespace utl
#endif