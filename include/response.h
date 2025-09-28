#ifndef RESPONSE_H
#define RESPONSE_H

#include <string>

namespace mdl
{
    class Response
    {
    private:
        std::string id_;
        std::string ptrn_;
        std::string match_;
    public:
        Response();
        Response(const std::string &id, const std::string &ptrn);
        ~Response();

        std::string id() const;
        void id(const std::string &id);
        std::string ptrn() const;
        void ptrn(const std::string &ptrn);
        std::string match() const;
        void match(const std::string &match);
        std::string to_string() const;
    };
} // namespace mdl
#endif