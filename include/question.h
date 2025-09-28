#ifndef QUESTION_H
#define QUESTION_H

#include "response.h"

#include <string>

namespace mdl
{
    class Question
    {
    private:
        std::string id_;
        std::string text_;
        bool ansd_;

    public:
        Question();
        Question(const std::string &id, const std::string &text);
        ~Question();

        std::string id() const;
        void id(const std::string id);
        std::string text() const;
        void text(const std::string text);
        bool ansd() const;
        void ansd(const bool &ansd);
        std::string to_string() const;
    };
} // namespace mdl

#endif