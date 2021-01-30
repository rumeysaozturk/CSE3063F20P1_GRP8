import pandas as pd
import Poll
class Answer ():
    def __init__(self, questionText, answer):
        self.questionText = questionText
        self.answer = answer

    # READ QUESTIONS AND ANSWERS OF THE QUESTION POLL
    def answerPoll(self, Polls, answerKeyName):
        xls = pd.ExcelFile(r"" + answerKeyName + ".XLS", engine='openpyxl')
        i = 1
        answers = []
        takeInput = xls.parse(0)
        questionXls = takeInput['Question Text']
        answerXls = takeInput['Answer']
        answerLength = answerXls[0]
        questionTexts = []
        while i < answerLength + 1:
            answer = Answer(questionXls[i], answerXls[i])
            answers.append(answer)
            print(answer.questionText, answer.answer)
            i = i + 1

        poll = Poll.Poll(answerLength, answers)
        Polls.append(poll)



