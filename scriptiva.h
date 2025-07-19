#include <fstream>
#include <iostream>
#include <regex>
#include <string>
#include <vector>

using namespace std;
// Scriptiva Model
namespace scpvaM
{
    inline const string brl = "\n\r";

    string readFile(string filename);
    vector<string> split(const string s, const string p);

#pragma region Classes

    class Table {
    private :
        vector<vector<string>> cells;

    public:
        Table() {}

        /// @brief Representation of a table.
        /// @param cls Cells.
        Table::Table(vector<vector<string>> cls) : cells(cls) {}

        ~Table() {}

        static Table create(string content, string p);
        string get(int i, int j);
        string get(string header, int i);
        int getHeader(string header);
        string getHeader(int i);
    };

    class Answer
    {
    private:
        string id;
        string regex;

    public:
        Answer() {}

        /// @brief Representation of possible answers given by the user.
        /// @param i Identifier (ID).
        /// @param rgx Regular Expression (regex) of the answer.
        Answer(string i, string rgx) : id(i), regex(rgx) {}
        ~Answer() {}

        string getId();
        void setId(int i);
        string getRegex();
        void setRegex(string rgx);
    };

    class Question
    {
    private:
        string id;
        string text;
        vector<Answer> answers;
        string matched;
    public:
        Question() {}

        /// @brief Representation of questo
        /// @param i Identifier (ID).
        /// @param txt Text of the question.
        /// @param aswrs Vector of acceptable answers.
        Question(string i, string txt, vector<Answer> &aswrs) : id(i), text(txt), answers(aswrs) {}

        ~Question() { }

        string getId();
        void setId(string i);
        string getText();
        void setText(string txt);
        vector<Answer> getAnswers();
        void setAnswers(vector<Answer> &aswrs);
        string getMatched();
        void setMatched(string mtchd);
    };
    
    class Flow 
    {
    private:
        string id;
        Question question;
        string answer_id;
        string next_flow;
        bool is_first;
    public:
        Flow() {}

        /// @brief Representation of a flow between a question and next question in other flow.
        /// @param i Identifier (ID).
        /// @param q Question.
        /// @param aswr_i Answer identifier.
        /// @param nxt_flw Next flow's identifier.
        /// @param is_frst First flow or not.
        Flow(string i, Question &q, string aswr_i, string nxt_flw, bool is_frst) 
            : id(i), question(q), answer_id(aswr_i), next_flow(nxt_flw), is_first(is_frst) {}

        ~Flow() {}

        string getId();
        void setId(string i);
        Question getQuestion();
        void setQuestion(Question &q);
        string getAnswerId();
        void setAnswerId(string aswr_id);
        string getNextFlow();
        void setNextFlow(string nxt_flw);
        bool isFirst();
        bool setFirst(bool is_frst);        
    };
#pragma endregion region Classes

#pragma region Implementations

    /// @brief Read a file from the filename.
    /// @param filename Path with file name.
    /// @return Content of file.
    string readFile(std::string filename)
    {
        std::ifstream inputFile(filename);
        if (!inputFile.is_open())
        {
            std::cerr << "Error opening file " << filename << std::endl;
            return nullptr;
        }
        std::string content = "";
        std::string line;
        while (std::getline(inputFile, line))
        {
            content += line + "\n\r";
        }
        return content;
    }

    /// @brief  Split a string text from a string pattern.
    /// @param s String text.
    /// @param p String pattern.
    /// @return String vector containing result.
    vector<string> split(const string s, const string p)
    {
        vector<string> vec;
        int i = 0;
        int j = 0;
        while (s.length() >= i + p.length())
        {
            if (s.compare(i, p.length(), p) == 0)
            {
                vec.push_back(s.substr(j, i - j));
                i += p.length();
                j = i;
            }
            i++;
        }
        return vec;
    }

#pragma region Implementations: Table

    /// @brief Create an instance of Table.
    /// @param content String content of the CSV file.
    /// @param sep String that separates columns.
    /// @return An instance of Table.
    inline Table Table::create(string content, string sep)
    {
        vector<vector<string>> cells;
        vector<string> lines = split(content, brl);
        for (int i = 0; i < lines.size(); i++)
        {
            cells.push_back(split(lines[i], sep));
        }
        return Table(cells);
    }

    inline string Table::get(int i, int j) { return cells[i][j]; }

    inline string Table::getHeader(int i)  { return get(0, i); }

    int Table::getHeader(string header) 
    {
        for (int j = 0; j < cells[0].size(); j++)
        {
            if (cells[0][j] == header)
            {
                return j;
            }
        }
        return -1;
    }

    inline string Table::get(string header, int i)
    {
        int j = getHeader(header);
        return get(i, j);
    }

#pragma endregion Implmentations : Table
#pragma region Implementations: Answer

    string Answer::getId() { return id; }

    void Answer::setId(int i) { id = i; }

    string Answer::getRegex() { return regex; }

    void Answer::setRegex(string rgx) { regex = rgx; }

#pragma endregion Implementations : Answer
#pragma region Implementations: Question

    string Question::getId() { return id; }

    void Question::setId(string i) { id = i; }

    string Question::getText() { return text; }

    void Question::setText(string txt) { text = txt; }

    vector<Answer> Question::getAnswers() { return answers; }

    void Question::setAnswers(vector<Answer> &aswrs) { answers = aswrs; }

    string Question::getMatched() { return matched; }

    void Question::setMatched(string mtchd) { matched = mtchd; }
#pragma endregion Implementations: Question
#pragma region Implementations: Flow
    string Flow::getId() { return id;}

    void Flow::setId(string i) { id = i; }

    Question Flow::getQuestion() { return question;}

    void Flow::setQuestion(Question &q) { question = q; }

    string Flow::getAnswerId() { return ;}

    void Flow::setAnswerId(string aswr_id) { answer_id = aswr_id; }

    string Flow::getNextFlow() { return ;}

    void Flow::setNextFlow(string nxt_flw) { next_flow = nxt_flw; }

    bool Flow::isFirst() { return ;}

    bool Flow::setFirst(bool is_frst) { is_first = is_frst; }
#pragma endregion Implementations: Flow
#pragma endregion Implementaions

} // namespace scpvaM

// Scriptiva Control
namespace scpvaC
{

} // namespace scpvaC

// Scriptiva View
namespace scpvaV
{

} // namespace scpvaV
