import pandas as pd
import Student
import re

class Student() :



    def __init__(self, studentid, firstName, lastName):
        self.studentid = studentid
        self.firstName = firstName
        self.lastName = lastName
        self.attendance = 0
        self.question = []
        self.totalpointforThispoll = []
        self.totalPoint = 0;


    def changeName(self, sFullname):
        sFullname = re.sub(r"İ", "i", sFullname)
        sFullname = re.sub(r"I", "i", sFullname)
        sFullname = re.sub(r"Ç", "c", sFullname)
        sFullname = re.sub(r"Ş", "s", sFullname)
        sFullname = re.sub(r"Ü", "u", sFullname)
        sFullname = re.sub(r"Ğ", "g", sFullname)
        sFullname = re.sub(r"i", "i", sFullname)
        sFullname = re.sub(r"ı", "i", sFullname)
        sFullname = re.sub(r"ç", "c", sFullname)
        sFullname = re.sub(r"ş", "s", sFullname)
        sFullname = re.sub(r"ü", "u", sFullname)
        sFullname = re.sub(r"ğ", "g", sFullname)
        sFullname = re.sub(r"O", "o", sFullname)
        sFullname = re.sub(r"Ö", "o", sFullname)
        sFullname = re.sub(r"o", "o", sFullname)
        sFullname = re.sub(r"ö", "o", sFullname)
        sFullname = sFullname.lower()  # for the rest use default lower
        return sFullname

    # READ ALL STUDENTS
    def readStudents(self,students, studentsLength, studentsFullname):
        i = 0
        inputStudent = Student("", "", "")
        xls = pd.ExcelFile(r"StudentList.XLS", engine='openpyxl')
        sheetX = xls.parse(0)
        studentId = sheetX['Öğrenci No']
        firstName = sheetX['Adı']
        lastName = sheetX['Soyadı']
        while i < studentsLength:
            sFullName = firstName[i] + " " + lastName[i]
            sFullName = inputStudent.changeName(sFullName)
            studentsFullname.append(sFullName)
            Student1 = Student(studentId[i], firstName[i], lastName[i])
            students.append(Student1)
            i = i + 1
