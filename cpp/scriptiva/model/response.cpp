#include "../include/response.h"

mdl::Response::Response() : id_(""), ptrn_(""), match_("") {}

mdl::Response::Response(const std::string &id, const std::string &ptrn) : id_(id), ptrn_(ptrn), match_("") {}

mdl::Response::~Response() {}

std::string mdl::Response::id() const { return id_; }

void mdl::Response::id(const std::string &id) { id_ = id; }

std::string mdl::Response::ptrn() const { return ptrn_; }

void mdl::Response::ptrn(const std::string &ptrn) { ptrn_ = ptrn; }

std::string mdl::Response::match() const { return match_;}

void mdl::Response::match(const std::string &match) { match_ = match; }

std::string mdl::Response::to_string() const 
{ 
    return "Response: {id=\"" + id_ + "\", ptrn=\"" + ptrn_ + "\", match=\"" + match_ + "\"}";
}