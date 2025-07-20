#include <fstream>
#include <iostream>
#include <regex>
#include <string>
#include <vector>

using namespace std;

inline const string brl = "\n\r";

// Scriptiva Model
namespace scpvaM
{

#pragma region Classes
    class Table
    {
    private:
        vector<vector<string>> cells;
        static vector<string> split(const string s, const string p);

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
        int getNumberOfRows();
        int getNumberOfColumns(int r);
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

        ~Question() {}

        string getId();
        void setId(string i);
        string getText();
        void setText(string txt);
        vector<Answer> getAnswers();
        void setAnswers(vector<Answer> &aswrs);
        string getMatched();
        void setMatched(string mtchd);
    };

    class Conclusion
    {
    private:
        string id;
        string text;

    public:
        Conclusion() {}

        ///@brief Constructor of conclusion.
        ///@param i Identifier (ID).
        ///@param txt Text of conclusion
        Conclusion(string i, string txt) : id(i), text(txt) {}

        ~Conclusion() {}

        string getId();
        void setId(string i);
        string getText();
        void setText(string txt);
    };

    class Flow
    {
    private:
        string id;
        Question question;
        string answer_id;
        string next_flow;
        string conclusion_id;
        bool is_first;

    public:
        Flow() {}

        /// @brief Representation of a flow between a question and next question in other flow.
        /// @param i Identifier (ID).
        /// @param q Question.
        /// @param aswr_i Answer identifier.
        /// @param concl_i Conclusion's identifier.
        /// @param nxt_flw Next flow's identifier.
        /// @param is_frst First flow or not.
        Flow(string i, Question &q, string aswr_i, string concl_i, string nxt_flw, bool is_frst)
            : id(i), question(q), answer_id(aswr_i), conclusion_id(concl_i), next_flow(nxt_flw), is_first(is_frst) {}

        ~Flow() {}

        string getId();
        void setId(string i);
        Question getQuestion();
        void setQuestion(Question &q);
        string getAnswerId();
        void setAnswerId(string aswr_id);
        string getNextFlow();
        void setNextFlow(string nxt_flw);
        string getConclusionId();
        void setConclusionId(string concl_i);
        bool isFirst();
        bool setFirst(bool is_frst);
    };
#pragma endregion Classes
#pragma region Implementations
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

    /// @brief  Split a string text from a string pattern.
    /// @param s String text.
    /// @param p String pattern.
    /// @return String vector containing result.
    inline vector<string> Table::split(const string s, const string p)
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

    inline string Table::get(int i, int j) { return cells[i][j]; }

    inline string Table::getHeader(int i) { return get(0, i); }

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

    /// @brief Count the number of rows in table.
    /// @return Number of rows in Table.
    int Table::getNumberOfRows() { return cells.size(); }

    /// @brief Count the number of columns in a row.
    /// @param r Row.
    /// @return Number of columns in the row.
    int Table::getNumberOfColumns(int r) { return cells[r].size(); }
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
#pragma endregion Implementations : Question
#pragma region Implementations: Conclusion
    string Conclusion::getId() { return id; }

    void Conclusion::setId(string i) { id = i; }

    string Conclusion::getText() { return text; }

    void Conclusion::setText(string txt) { text = txt; }
#pragma endregion Implementations : Conclusion
#pragma region Implementations: Flow
    string Flow::getId() { return id; }

    void Flow::setId(string i) { id = i; }

    Question Flow::getQuestion() { return question; }

    void Flow::setQuestion(Question &q) { question = q; }

    string Flow::getAnswerId() { return; }

    void Flow::setAnswerId(string aswr_id) { answer_id = aswr_id; }

    string Flow::getNextFlow() { return next_flow; }

    void Flow::setNextFlow(string nxt_flw) { next_flow = nxt_flw; }

    string Flow::getConclusionId() { return conclusion_id; }

    void Flow::setConclusionId(string concl_i) { conclusion_id = concl_i; }

    bool Flow::isFirst() { return; }

    bool Flow::setFirst(bool is_frst) { is_first = is_frst; }
#pragma endregion Implementations : Flow
#pragma endregion Implementaions
} // namespace scpvaM

// Scriptiva Control
namespace scpvaC
{
#pragma region Classes
    class Error
    {
    private:
        vector<string> error_messages;
        vector<string> warning_messages;

    public:
        Error() {}
        ~Error() {}

        vector<string> getErrors();
        void addError(string msg);
        vector<string> getWarning();
        void addWarning(string msg);
        bool hasErrors();
        bool hasWarnings();
        void clear();
    };

    class File
    {
    private:
        Error error;

    public:
        File() {}
        ~File() {}
        string read(string filename);
        Error getError();
    };

    class Checker
    {
    private:
        scpvaM::Table table;

    public:
        /// @brief Constructor of Checher
        /// @param tb Table.
        Checker(scpvaM::Table &tb) : table(tb) {}

        ~Checker() {}

        Error validadeNumberOfRows(int min);
        Error validadeHeader(vector<string> header);
        Error validadeNumberOfColumns(int min);
        Error validadeUniqueValue(string header);
    };
    
    class FlowManager
    {
    private:
        Error error;
        vector<scpvaM::Flow> flows;
        vector<scpvaM::Conclusion> conclusions;
        string current_flow;

        scpvaM::Flow getFlow(string flw_i);

    public:
        /// @brief Manages conversation flow during the execution.
        /// @param flw A set of flow
        /// @param concls A set of conclusion
        FlowManager(vector<scpvaM::Flow> flws, vector<scpvaM::Conclusion> concls)
            : flows(flws), conclusions(concls) {}

        ~FlowManager() {}

        scpvaM::Question getFirstQuestion();
        scpvaM::Question getNextQuestion();
        scpvaM::Conclusion getConclusion();
        bool hasConclusion();
        bool hasNextFlow();
        bool answerQuestion(string txt);
        Error getError();
    };
#pragma endregion Classes
#pragma region Implementations
#pragma region Implementations: Error
    vector<string> Error::getErrors() { return error_messages; }

    void Error::addError(string msg) { error_messages.push_back(msg); }

    vector<string> Error::getWarning() { return warning_messages; }

    void Error::addWarning(string msg) { warning_messages.push_back(msg); }

    bool Error::hasErrors() { return error_messages.size() > 0; }

    bool Error::hasWarnings() { return warning_messages.size() > 0; }

    void Error::clear()
    {
        error_messages.clear();
        warning_messages.clear();
    }
#pragma endregion Implementations : Error
#pragma region Implemetations: File
    /// @brief Read a file from the filename.
    /// @param filename Path with file name.
    /// @return Content of file.
    string File::read(std::string filename)
    {
        std::ifstream inputFile(filename);
        if (!inputFile.is_open())
        {
            error.addError("Error opening file " + filename + ".");
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
#pragma endregion Implementations : File
#pragma region Implementations: Checker
    Error Checker::validadeNumberOfRows(int min)
    {
        Error error;
        if (table.getNumberOfRows() < min)
        {
            string msg = "Expected at least " + to_string(min) + " rows, but found only" + to_string(table.getNumberOfRows()) + ".";
            error.addError(msg);
        }
        if (table.getNumberOfRows() > min)
        {
            string msg = "Expected " + to_string(min) + " rows, but was found " + to_string(table.getNumberOfRows()) + ".";
            error.addWarning(msg);
        }
        return error;
    }

    Error Checker::validadeHeader(vector<string> header)
    {
        Error error;
        for (string &name : header)
        {
            if (table.getHeader(name) == -1)
            {
                error.addError("Header " + name + " not found.");
            }
        }
        return error;
    }

    Error Checker::validadeNumberOfColumns(int min)
    {
        Error error;
        for (int r = 0; r < table.getNumberOfRows(); r++)
        {
            int numOfCols = table.getNumberOfColumns(r);
            if (numOfCols < min)
            {
                string msg = "Expected at least " + to_string(min) + " columns, but found only";
                msg += to_string(numOfCols) + " in row " + to_string(r) + ".";
                error.addError(msg);
            }
            if (numOfCols > min)
            {
                string msg = "Expected " + to_string(min) + " columns, but was found ";
                msg += to_string(numOfCols) + " in row " + to_string(r) + ".";
                error.addWarning(msg);
            }
        }
        return error;
    }

    Error Checker::validadeUniqueValue(string header)
    {
        Error error;
        for (int i = 1; i < table.getNumberOfRows() - 1; i++)
        {
            string value = table.get(header, i);
            for (int r = i + 1; r < table.getNumberOfRows(); r++)
            {
                if (value == table.get(header, r))
                {
                    string msg = "The value '" + value = "' in row " + to_string(i);
                    msg += " is duplicated at row " + to_string(r) + ".";
                    error.addError(msg);
                    break;
                }
            }
        }
        return error;
    }
#pragma endregion Implementations : Checker
#pragma region Implementations: FlowManager
    /// @brief Get flow from flow's identifier.
    /// @param flw_i Flow's identifier (ID).
    /// @return Flow.
    scpvaM::Flow FlowManager::getFlow(string flw_i)
    {
        for (scpvaM::Flow &flw : flows)
        {
            if (flw.getId() == flw_i)
            {
                return flw;
            }
        }
        error.addError("Flows identifier '" + flw_i + "' not found.");
        return flows[0];
    }

    /// @brief Search the initial flow and return its question. If it not found, return the first flow.
    /// @return A flow.
    scpvaM::Question FlowManager::getFirstQuestion()
    {
        for (scpvaM::Flow &flw : flows)
        {
            if (flw.isFirst())
            {
                current_flow = flw.getId();
                return flw.getQuestion();
            }
        }
        error.addWarning("Not found first flow. Please, check the file.");
        current_flow = flows[0].getId();
        return flows[0].getQuestion();
    }

    scpvaM::Question FlowManager::getNextQuestion() {
        if (getFlow(current_flow).getQuestion().getMatched().size() > 0) {
            current_flow = getFlow(current_flow).getNextFlow();
        }
        return getFlow(current_flow).getQuestion();
    }

    /// @brief Get the conclusion at current flow. If it is not exist, return any conclusion.
    /// @return A conclusion.
    scpvaM::Conclusion FlowManager::getConclusion()
    {
        for (scpvaM::Conclusion &concl : conclusions)
        {
            if (concl.getId() == getFlow(current_flow).getConclusionId())
            {
                return concl;
            }
        }
        return conclusions[0];
    }

    /// @return True if there is a conclusion, otherwise false. But it is necessary to check existing errors.
    bool FlowManager::hasConclusion() { return getFlow(current_flow).getConclusionId().size() > 0; }

    /// @return False if there is not a next question, otherwise false. But it is necessary to check existing errors.
    bool FlowManager::hasNextFlow() { return getFlow(current_flow).getNextFlow().size() > 0; }

    bool FlowManager::answerQuestion(string txt) {}

    Error FlowManager::getError() { return error; }
#pragma endregion Implementations : FlowManager
#pragma endregion Implementations

} // namespace scpvaC

// Scriptiva View
namespace scpvaV
{

} // namespace scpvaV
