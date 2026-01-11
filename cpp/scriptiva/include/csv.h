#ifndef CSV_H
#define CSV_H

#include "table.h"

#include <exception>
#include <fstream>

namespace utl
{
    /**
     * @brief Reads CSV files and converts them to Table objects.
     *
     * Handles separator and line break configuration. Provides method to read a file into a Table.
     *
     * - sep_: Field separator (default: ';').
     * - brkln_: Line break string.
     */
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