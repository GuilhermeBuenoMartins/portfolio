#include "../include/dialog.h"

mdl::Dialog::Dialog() : id_(""), first_(false), qstn_(nullptr), resp_(nullptr), next_(""), conc_("") {}

mdl::Dialog::Dialog(const std::string &id, const bool &first, mdl::Question &qstn, mdl::Response &resp,
                    const std::string &next, const std::string &conc) : id_(id), first_(first), qstn_(&qstn), resp_(&resp), 
                    next_(next), conc_(conc) {}

mdl::Dialog::~Dialog() {}                

std::string mdl::Dialog::id() const { return id_; }

void mdl::Dialog::id(const std::string id) { id_ = id; }

bool mdl::Dialog::first() const { return first_; }

void mdl::Dialog::first(const bool &first) { first_ = first; }

mdl::Question &mdl::Dialog::qstn() const { return *qstn_; }

void mdl::Dialog::qstn(mdl::Question &qstn) { qstn_ = &qstn; }

mdl::Response &mdl::Dialog::resp() const { return *resp_; }

void mdl::Dialog::resp(mdl::Response &resp) { resp_ = &resp; }

std::string mdl::Dialog::next() const { return next_; }

void mdl::Dialog::next(const std::string &next) { next_ = next; } 

std::string mdl::Dialog::conc() const { return conc_; }

void mdl::Dialog::conc(const std::string &conc) { conc_ = conc; }

std::string mdl::Dialog::to_string() const
{
    std::string qstn_s = qstn_ == nullptr ? "" : qstn_->to_string();
    std::string resp_s = resp_ == nullptr ? "" : resp_->to_string();
    std::string first = first_? "true" : "false";
    return "Dialog: {id=\"" + id_ + "\", first=" + first + ", qstn=" + qstn_s + ", resp=" + resp_s + "next=\"" + next_ + "\", conc=\"" + conc_ + "\"}";
}