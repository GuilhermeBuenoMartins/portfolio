#include "../include/table.h"

utl::Table::Table(const std::vector<std::vector<std::string>> &cells)
{
    hlen_ = 0;
    for (int i = 0; i < cells.size(); i++)
    {
        hlen_ = i == 0 || cells.size() < hlen_? cells.size() : hlen_;
    }
    cells_ = cells;
}

utl::Table::~Table() {}

size_t utl::Table::vlength() const { return cells_.size(); }

size_t utl::Table::hlength() const { return hlen_; }

std::vector<std::string> utl::Table::row(const int &i) const { return cells_[i]; }

std::vector<std::string> utl::Table::col(const int &j) const
{
    std::vector<std::string> col;
    for (std::vector<std::string> row: cells_)
    {
        col.push_back(row[j]);
    }
    return col;
}

std::string utl::Table::get(const int &i, const int &j) const { return cells_[i][j]; }

void utl::Table::set(const int &i, const int &j, const std::string &s) { cells_[i][j] = s; }