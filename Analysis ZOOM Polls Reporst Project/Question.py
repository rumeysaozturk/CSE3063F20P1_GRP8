import matplotlib.pyplot as plt

class Question ():
    def __init__(self,question):
        self.question = question
        self.answers = []
        self.countOfStudent = []

    def histogram(self, a, Polls, previousPlace, worksheet, iter, picname ):
        while a < Polls[iter].answerLength:
            x_bar = []
            y_bar = []
            lengthOfAnswer = len(Polls[iter].questionsInPoll[a].answers)
            i = 0
            trueAnswerIndex = 0
            while i < lengthOfAnswer:
                x_bar.append(str(i + 1))
                y_bar.append(Polls[iter].questionsInPoll[a].countOfStudent[i])
                if Polls[iter].questionsInPoll[a].answers[i] == Polls[iter].answers[a].answer:
                    trueAnswerIndex = i
                i += 1
            plt.bar(x_bar, y_bar, color='b').patches[trueAnswerIndex].set_color('r')
            nameOfPng = str(1 + a) + "Pic_" + str(iter + 1) + "Poll_" + picname + ".png"
            plt.savefig(nameOfPng, dpi=150)
            worksheet.write('A' + str(previousPlace), str(a + 1) + "Q) " + Polls[iter].questionsInPoll[a].question)
            previousPlace += 1
            worksheet.insert_image('B' + str(previousPlace), nameOfPng, {'x_scale': 0.5, 'y_scale': 0.5})

            i = 0
            previousPlace += 12
            while i < lengthOfAnswer:
                previousPlace += 1
                worksheet.write('A' + str(previousPlace),
                                str(i + 1) + "- " + Polls[iter].questionsInPoll[a].answers[i] + " (" + str(
                                    Polls[iter].questionsInPoll[a].countOfStudent[i]) + " students select)")

                i += 1
            previousPlace += 3
            plt.close()

            a += 1
        return a, previousPlace, worksheet, iter, Polls





