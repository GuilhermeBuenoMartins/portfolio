#include "../include/chat.h"

ctr::Chat::Chat(std::vector<mdl::Dialog> &dlgs) : dlgs_(dlgs), curr_(nullptr) {}

ctr::Chat::~Chat() {}

mdl::Dialog &ctr::Chat::next_dlg()
{
    for (size_t i = 0; curr_ == nullptr && i < dlgs_.size(); i++)
    {
        if (dlgs_[i].first())
        {
            return dlgs_[i];
        }
    }
    for (mdl::Dialog &dlg : dlgs_)
    {
        if (dlg.id() == curr_->next())
        {
            return dlg;
        }
    }
    throw std::runtime_error("It was not found initial dialog. At least row in column \"First\" must be true.");
}

std::vector<mdl::Dialog> ctr::Chat::dlgs_by_qstn_id(const std::string &qstn_id)
{
    std::vector<mdl::Dialog> dlgs;
    for (mdl::Dialog &dlg : dlgs_)
    {
        if (dlg.qstn().id() == qstn_id)
        {
            dlgs.push_back(dlg);
        }
    }
    return dlgs;
}

std::string ctr::Chat::ask()
{
    if (curr_ == nullptr)
    { curr_ = &next_dlg(); }
    if (!has_conclusion() && curr_->qstn().ansd())
    { curr_ = &next_dlg(); }
    return curr_->qstn().text();
}

bool ctr::Chat::receive(const std::string &answer)
{
    std::vector<mdl::Dialog> dlgs = dlgs_by_qstn_id(curr_->qstn().id());
    for (mdl::Dialog &dlg: dlgs)
    {
        std::regex ptrn(dlg.resp().ptrn());
        std::smatch matches;
        if (std::regex_search(answer, matches, ptrn))
        {
            *curr_ = dlg;
            dlg.resp().match(matches[0]);
            dlg.qstn().ansd(true);
            hist_.push_back(dlg);
            return true;
        }
    }
    return false;
}

bool ctr::Chat::has_conclusion()
{ 
    if (curr_ == nullptr) { return false; }
    return !curr_->conc().empty();
}

std::string ctr::Chat::conclusion()
{
    std::string conc = has_conclusion()? curr_->conc() : "";
    for (mdl::Dialog &dlg: hist_)
    {
        std::string var = "${" + dlg.resp().id() + "}";
        size_t begin = 0;
        size_t end = conc.find(var, begin);
        while (end != std::string::npos)
        {
            conc = conc.replace(end, var.size(), dlg.resp().match());
            begin = end + var.size();
            end = conc.find(var, begin);
        }
    }
    return conc;
}
