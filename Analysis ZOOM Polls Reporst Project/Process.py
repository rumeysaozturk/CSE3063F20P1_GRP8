import pandas as pd
import path
import Student
import csv
import xlsxwriter
import Answer
import Question
import Poll
import xlwt
import xlrd
import os.path
from os import path


from xlwt import Workbook
import matplotlib.pyplot as plt

answerkeys=['answerkey1', 'answerkey2']
studentsLength=196
students = []
studentsFullname = []
answers = []
Polls = []
assignQuestion = 0
differentPoll = 0
countoflesson = 0

inputStudent =Student.Student("", "", "")

#read all students
inputStudent.readStudents(students,studentsLength, studentsFullname)

inputQuestion = Question.Question("")


def findStudentIndex(pollFullname, studentsFullname):
    pollFullname = pollFullname.split(" ")
    s = 0
    while s < len(pollFullname):
        if pollFullname[s].isalpha() == False:
            pollFullname.remove(pollFullname[s])
        s += 1

    studentIndex = 0
    k = 0
    while k < len(studentsFullname):

        isNameIncludeAll = 0
        w = 0
        while w < len(pollFullname):
            if pollFullname[w] in studentsFullname[k]:
                isNameIncludeAll += 1
                if isNameIncludeAll == len(pollFullname):
                    studentIndex = k
                    break
            w += 1
        if isNameIncludeAll == len(pollFullname):
            break
        k += 1
    return isNameIncludeAll, studentIndex


def createNewPoll(assignQuestion, differentPoll, question_students, Polls, students ):
    differentPoll += 1
    assignQuestion += 1
    if assignQuestion == 1:
        assignQuestion == 0
        numOfQuestion = 0
        inputAnswer.answerPoll(Polls, str(answerkeys[differentPoll - 1]))
        assign_1 = 0
        length_polls = len(Polls)
        while assign_1 < Polls[length_polls - 1].answerLength:
            question_students.append(-1)
            assign_1 += 1

        k = 0
        while k < studentsLength:
            students[k].question.append(question_students)
            students[k].totalpointforThispoll.append(0)
            k += 1
        question_students = []
        totalPointThisPoll = 0
        iter = len(Polls) - 1
        while numOfQuestion < Polls[iter].answerLength:
            questiontxt = column[numOfQuestion * 2 + 4]
            Polls[iter].questionText.append(questiontxt)
            question = Question.Question(questiontxt)
            Polls[iter].questionsInPoll.append(question)
            numOfQuestion += 1
    return iter, differentPoll, assignQuestion, question_students,totalPointThisPoll


if(path.exists("attendance.xls")):
     loc = ("attendance.xls")
     wb = xlrd.open_workbook(loc)
     sheet = wb.sheet_by_index(0)
     k=0
     f=1
     countoflesson = sheet.cell_value(1, 4)
     while k < 169:
         students[k].attendance=int(sheet.cell_value(f, 3))
         k=k+1
         f=f+1


#------------------for answer method----------------------------
inputAnswer =Answer.Answer("", "")

inputPoll =Poll.Poll("", "")

line_count = 0

readPath="CSE3063_20201123_Mon_zoom_PollReport"
contain = readPath.find("Mon")
if (contain != -1):
    countoflesson = countoflesson + 2
else:
    countoflesson = countoflesson + 1

with open(str(readPath) + ".csv", encoding='utf-8') as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    for column in csv_reader:
        if line_count == 0:
            print(f'Column names are {", ".join(column)}')
            line_count += 1
        else:
            pollFullname = inputStudent.changeName(column[1])

            isNameIncludeAll, studentIndex = findStudentIndex(pollFullname,studentsFullname)

            if isNameIncludeAll == 0:
                continue

            students[studentIndex].attendance += 1

            if column[4] != 'Are you attending this lecture?':
                question_students = []
                totalPointThisPoll = 0
                #assign questions in the question list
                first_question_text = column[4]
                length_polls = len(Polls)
                iter = 0
                isInside = 0


                while iter < length_polls:
                    if Polls[iter].answers[0].questionText == first_question_text:
                        isInside = 1
                        break;
                    iter += 1
                assignQuestion = 0

                if isInside == 0:  # FOR NEW POLL
                     iter, differentPoll, assignQuestion, question_students,totalPointThisPoll = createNewPoll(assignQuestion, differentPoll, question_students, Polls, students)


                count_question = len(Polls[iter].answers)
                numOfQuestion = 0

                Polls, numOfQuestion, count_question, students, question_students, totalPointThisPoll, studentIndex = inputQuestion.fill_questionInPoll(Polls, numOfQuestion, column, count_question, students, question_students, totalPointThisPoll, studentIndex)

                students[studentIndex].question[iter] = question_students
                students[studentIndex].totalpointforThispoll[iter] = totalPointThisPoll

            line_count += 1

d=0
while d < len(Polls):
    a =0
    while a < len(Polls[d].questionText):
        print(str(a+1)+"Q) "+Polls[d].questionText[a])
        print(Polls[d].questionsInPoll[a].answers)
        print(Polls[d].questionsInPoll[a].countOfStudent)
        a+=1
    d += 1
inputPoll.writePollInformation(students, readPath, studentsLength, Polls)
inputPoll.writeinGlobal(students,readPath,Polls)

wb = xlwt.Workbook('attendance.xls')

# add_sheet is used to create sheet.
sheet1 = wb.add_sheet('Sheet 1')

sheet1.write(0, 0, "Öğrenci No")
sheet1.write(0, 1, "Öğrenci Adı")
sheet1.write(0, 2, "Öğrenci Soyadı")
sheet1.write(0, 3, "Attendance")
sheet1.write(0, 4, "Total Lesson")
sheet1.write(0, 5, "RATE")
d = 0
i = 1
while d < 196:
    sheet1.write(i, 0, str(students[d].studentid))
    sheet1.write(i, 1, students[d].firstName)
    sheet1.write(i, 2, students[d].lastName)
    sheet1.write(i, 3, students[d].attendance)
    sheet1.write(i, 4, countoflesson)
    sheet1.write(i, 5,str(students[d].attendance)+" of "+str(countoflesson))
    d = d + 1
    i = i + 1

wb.save("attendance.xls")
