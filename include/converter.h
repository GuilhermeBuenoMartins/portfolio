#ifndef CONVERTER_H
#define CONVERTER_H

#include "dialog.h"
#include "question.h"
#include "response.h"
#include "table.h"

#include <stdexcept>

/**
 * @brief Utility functions for converting between table rows and model objects.
 *
 * Includes conversion for Response, Question, Dialog and search functions by id.
 */
namespace utl
{
    mdl::Response to_response(const std::vector<std::string> &row);

    std::vector<mdl::Response> to_responses(const utl::Table &table);

    mdl::Question to_question(const std::vector<std::string> &row);

    std::vector<mdl::Question> to_questions(const utl::Table &table);

    size_t find(const std::vector<mdl::Response> &resps, const std::string &id);

    size_t find(const std::vector<mdl::Question> &qstns, const std::string &id);

    mdl::Dialog to_dialog(const std::vector<std::string> &row, std::vector<mdl::Question> &qstns, std::vector<mdl::Response> &resps);

    std::vector<mdl::Dialog> to_dialogs(const utl::Table &table, std::vector<mdl::Question> &qstns,
                                        std::vector<mdl::Response> &resps);
} // namespace utl

#endif