#include "../include/question.h"

mdl::Question::Question() : id_(""), text_(""), ansd_(false) {}

mdl::Question::Question(const std::string &id, const std::string &text) : id_(id), text_(text), ansd_(false) {}

mdl::Question::~Question() {}

std::string mdl::Question::id() const { return id_; }

void mdl::Question::id(const std::string id) { id_ = id; }

std::string mdl::Question::text() const { return text_; }

void mdl::Question::text(const std::string text) { text_ = text; }

bool mdl::Question::ansd() const { return ansd_; }

void mdl::Question::ansd(const bool &ansd) { ansd_ = ansd; }

std::string mdl::Question::to_string() const
{
    std::string ansd = ansd_? "true" : "false";
    std::string s = "Question: {id=\"" + id_ + "\", text=\"" + text_ + "\", ansd=" + ansd + "}";
    return s;
}