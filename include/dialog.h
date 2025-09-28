#ifndef DIALOG_H
#define DIALOG_H

#include "response.h"
#include "question.h"

#include <string>

namespace mdl
{
    /**
     * @brief Represents a dialog step in the conversation flow.
     *
     * Contains question, response, next dialog id, and conclusion text.
     *
     * - id_: Dialog identifier.
     * - first_: True if this is the first dialog.
     * - qstn_: Pointer to the associated question.
     * - resp_: Pointer to the associated response.
     * - next_: Id of the next dialog.
     * - conc_: Conclusion text for this dialog.
     */
    class Dialog
    {
    private:
        std::string id_;
        bool first_;
        mdl::Question *qstn_;
        mdl::Response *resp_;
        std::string next_;
        std::string conc_;
    public:
        Dialog();
        Dialog(const std::string &id, const bool &first, mdl::Question &qstn, mdl::Response &resp, const std::string &next, const std::string &conc);
        ~Dialog();

        std::string id() const;
        void id(const std::string id);
        bool first() const;
        void first(const bool &first);
        mdl::Question &qstn() const;
        void qstn(mdl::Question &qstn);
        mdl::Response &resp() const;
        void resp(mdl::Response &resp);
        std::string next() const;
        void next(const std::string &next);
        std::string conc() const;
        void conc(const std::string &conc);
        std::string to_string() const;
    };
} // namespace mdl

#endif