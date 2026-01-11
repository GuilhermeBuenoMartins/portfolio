#ifndef CHAT_H
#define CHAT_H

#include "dialog.h"

#include <regex>
#include <stdexcept>
#include <vector>

namespace ctr
{
    /**
     * @brief Manages the conversation flow between dialogs.
     *
     * Stores the dialogs, history, and current dialog pointer. Provides methods to ask questions,
     * receive answers, check for conclusion, and retrieve the final message.
     *
     * - dlgs_: List of all dialogs in the flow.
     * - hist_: History of dialogs traversed.
     * - curr_: Pointer to the current dialog.
     */
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