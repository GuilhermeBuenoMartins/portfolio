#include "../include/converter.h"

mdl::Response utl::to_response(const std::vector<std::string> &row) { return mdl::Response(row[0], row[1]); }

std::vector<mdl::Response> utl::to_responses(const utl::Table &table)
{
    std::vector<mdl::Response> responses;
    // Ignoring header in index 0.
    for (int i = 1; i < table.vlength(); i++)
    {
        responses.push_back(to_response(table.row(i)));
    }
    return responses;
}

mdl::Question utl::to_question(const std::vector<std::string> &row) { return mdl::Question(row[0], row[1]); }

std::vector<mdl::Question> utl::to_questions(const utl::Table &table)
{
    std::vector<mdl::Question> questions;
    // Ignoring header in index 0.
    for (int i = 1; i < table.vlength(); i++)
    {
        questions.push_back(to_question(table.row(i)));
    }
    return questions;
}

size_t utl::find(const std::vector<mdl::Response> &resps, const std::string &id)
{
    for (size_t i = 0; i < resps.size(); i++)
    {
        if (resps[i].id() == id)
        {
            return i;
        }
    }
    return std::string::npos;
}

size_t utl::find(const std::vector<mdl::Question> &qstns, const std::string &id)
{
    for (size_t i = 0; i < qstns.size(); i++)
    {
        if (qstns[i].id() == id)
        {
            return i;
        }
    }
    return std::string::npos;
}

mdl::Dialog utl::to_dialog(const std::vector<std::string> &row, std::vector<mdl::Question> &qstns, std::vector<mdl::Response> &resps)
{
    size_t qstn_i = find(qstns, row[2]);
    if (qstn_i == std::string::npos)
    {
        throw std::runtime_error("Question id \"" + row[2] + "\" was not found to convertion.");
    }
    mdl::Question &qstn = qstns[qstn_i];
    size_t resp_i = find(resps, row[3]);
    if (resp_i == std::string::npos) {
        throw std::runtime_error("Response id \"" + row[3] + "\" was not found to convertion.");
    }
    mdl::Response &resp = resps[resp_i];
    bool first = row[1] == "true";
    std::string next = row[4].empty()? "": row[4];
    std::string conc = row[5].empty()? "": row[5];
    return mdl::Dialog(row[0], first, qstn, resp, next, conc);
}

std::vector<mdl::Dialog> utl::to_dialogs(const utl::Table &table, std::vector<mdl::Question> &qstns,
                                        std::vector<mdl::Response> &resps)
{
    std::vector<mdl::Dialog> dialogs;
    // Ignoring header in index 0.
    for (int i = 1; i < table.vlength(); i++)
    {
        dialogs.push_back(to_dialog(table.row(i), qstns, resps));
    }
    return dialogs;
}