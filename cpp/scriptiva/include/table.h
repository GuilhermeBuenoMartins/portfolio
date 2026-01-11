#ifndef TABLE_H
#define TABLE_H

#include <string>
#include <vector>

namespace utl
{
    /**
     * @brief Represents a table of string data, used for CSV and tabular manipulation.
     *
     * Stores the cells and header length. Provides methods to access rows, columns, and individual cells.
     *
     * - cells_: 2D vector of table cells.
     * - hlen_: Horizontal length.
     */
    class Table
    {
    private:
        std::vector<std::vector<std::string>> cells_;
        size_t hlen_;
    public:
        Table(const std::vector<std::vector<std::string>> &cells);

        ~Table();

        size_t vlength() const;
        size_t hlength() const;
        std::vector<std::string> row(const int &i) const;
        std::vector<std::string> col(const int &j) const;
        std::string get(const int &i, const int &j) const;
        void set(const int &i, const int &j, const std::string &s);
    };
} // namespace utl
#endif