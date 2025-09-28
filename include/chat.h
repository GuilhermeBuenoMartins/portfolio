#ifndef CHAT_H
#define CHAT_H

#include "dialog.h"

#include <regex>
#include <stdexcept>
#include <vector>

namespace ctr
{
    class Chat
    {
    private:
        std::vector<mdl::Dialog> dlgs_;
        std::vector<mdl::Dialog> hist_;
        mdl::Dialog* curr_;
        
        mdl::Dialog &next_dlg();
        std::vector<mdl::Dialog> dlgs_by_qstn_id(const std::string &qstn_id);
    public:
        Chat(std::vector<mdl::Dialog> &dlgs);
        ~Chat();

        std::string ask();
        bool receive(const std::string &answer);
        bool has_conclusion();
        std::string conclusion();
    };
} // namespace ctr

#endif