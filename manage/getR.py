# !/usr/bin/env python
import os,sys
from socket import *
from time import ctime
from openpyxl import Workbook
from xlutils.copy import copy
import xlrd
import datetime
import math
from TS_orders3 import getRoute
distanceTime = [[0, 0.9744, 1.368, 0.792, 1.9104, 2.5176, 2.2008, 4.0296, 3.8256, 3.408, 5.1744, 4.3272, 1.2912, 1.1664, 2.2872, 2.9328, 1.4112, 3.1392, 2.2824],
                [0.9744, 0,0.3936, 1.0584, 2.2824, 1.5432, 2.1624, 3.8568, 3.3216, 2.4336, 4.5096, 3.6624, 1.0368, 2.1408, 2.8656, 3.5112, 1.1568, 2.8848, 3.2568],
                [1.368, 0.3936, 0, 0.6648, 1.8888, 1.1496, 1.7688, 3.4632, 2.928, 2.04, 4.116, 3.2688, 1.4304, 2.5344, 3.2592, 3.9048, 1.5504, 3.2784, 3.6504],
                [0.792, 1.0584, 0.6648, 0, 1.224, 1.8144, 1.5144, 3.3432, 3.1392, 2.7048, 4.488, 3.6408, 2.0832, 1.9584, 3.0792, 3.7248, 2.2032, 3.9312, 3.0744],
                [1.9104, 2.2824, 1.8888, 1.224, 0, 1.9536, 1.596, 2.364, 2.8992, 2.844, 4.248, 3.4008, 3.2016, 3.0504, 4.1496, 4.788, 3.3216, 5.0496, 2.9136],
[2.5176, 1.5432, 1.1496, 1.8144, 1.9536, 0, 0.6192, 2.3136, 1.7784, 0.8904, 2.9664, 2.1192, 2.58, 3.684, 4.4088, 5.0544, 2.7, 4.428, 4.8],
[2.2008, 2.1624, 1.7688, 1.5144, 1.596, 0.6192, 0, 1.8288, 1.6248, 1.5096, 2.9736, 2.1264, 3.1992, 3.3672, 4.488, 5.1336, 3.3192, 5.0472, 4.4832],
[4.0296, 3.8568, 3.4632, 3.3432, 2.364, 2.3136, 1.8288, 0, 0.5352, 2.2656, 1.884, 1.0368, 4.8936, 5.196, 6.3168, 6.9624, 4.3344, 6.7416, 5.2776],
[3.8256, 3.3216, 2.928, 3.1392, 2.8992, 1.7784, 1.6248, 0.5352, 0, 1.7304, 1.3488, 0.5016, 4.3584, 4.992, 6.1128, 6.7584, 3.7992, 6.2064, 5.8128],
[3.408, 2.4336, 2.04, 2.7048, 2.844, 0.8904, 1.5096, 2.2656, 1.7304, 0, 2.076, 1.2288, 3.4704, 4.5744, 4.8384, 5.484, 2.388, 4.8576, 5.6904],
[5.1744, 4.5096, 4.116, 4.488, 4.248, 2.9664, 2.9736, 1.884, 1.3488, 2.076, 0, 0.8472, 5.5464, 6.3408, 6.9144, 7.56, 4.464, 6.9336, 7.1616],
[4.3272, 3.6624, 3.2688, 3.6408, 3.4008, 2.1192, 2.1264, 1.0368, 0.5016, 1.2288, 0.8472, 0, 4.6992, 5.4936, 6.0672, 6.7128, 3.6168, 6.0864, 6.3144],
[1.2912, 1.0368, 1.4304, 2.0832, 3.2016, 2.58, 3.1992, 4.8936, 4.3584, 3.4704, 5.5464, 4.6992, 0, 1.1256, 1.8288, 2.4744, 1.4544, 1.848, 2.2416],
[1.1664, 2.1408, 2.5344, 1.9584, 3.0504, 3.684, 3.3672, 5.196, 4.992, 4.5744, 6.3408, 5.4936, 1.1256, 0, 1.1208, 1.7664, 2.5392, 2.7624, 1.116],
[2.2872, 2.8656, 3.2592, 3.0792, 4.1496, 4.4088, 4.488, 6.3168, 6.1128, 4.8384, 6.9144, 6.0672, 1.8288, 1.1208, 0, 0.6456, 2.4504, 1.6752, 1.236],
[2.9328, 3.5112, 3.9048, 3.7248, 4.78, 	5.0544, 5.1336, 6.9624, 6.7584, 5.484, 7.56, 6.7128, 2.4744, 1.7664, 0.6456, 0, 3.096, 2.3208, 1.8744],
[1.4112, 1.1568, 1.5504, 2.2032, 3.3216, 2.7, 3.3192, 4.3344, 3.7992, 2.388, 4.464, 3.6168, 1.4544, 2.5392, 2.4504, 3.096, 0, 2.4696, 3.6552],
[3.1392, 2.8848, 3.2784, 3.9312, 5.0496, 4.428, 5.0472, 6.7416, 6.2064, 4.8576, 6.9336, 6.0864, 1.848, 2.7624, 1.6752, 2.3208, 2.4696, 0, 2.9112],
[2.2824	, 3.2568, 3.6504, 3.0744, 2.9136, 4.8, 4.4832, 5.2776, 5.8128, 5.6904, 	7.1616, 6.3144, 2.2416, 1.116, 1.236, 1.8744, 3.6552, 2.9112, 0]

                ]
def base_dir(filename=None):
    return os.path.join(os.path.dirname(__file__),filename)

HOST = ''
PORT = 21567
BUFSIZ = 1024
ADDR = (HOST, PORT)
startD = ""

tcpSerSock = socket(AF_INET, SOCK_STREAM)
tcpSerSock.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
tcpSerSock.bind(ADDR)
tcpSerSock.listen(5)
# 向sheet中写入一行数据
def insertOne(value, sheet):
    row = [value] * 3
    sheet.append(row)

def case2():
    global startD
    startD = "1871.38888889924 516.937516500242"

def case3():
    global startD
    startD = "1623.24326739833 640.984159659594"

def case4():
    global startD
    startD = "1499.17045659944 901.522037330084"

def case5():
    global startD
    startD = "1499.17045659944 1065.32883718982"

def case6():
    global startD
    startD = "1065.32883718982 549.180830719881"

def case7():
    global startD
    startD = "926.920814301819 901.522037330084"

def case8():
    global startD
    startD = "190.83714620024 1010.07008159999"

def case9():
    global startD
    startD = "368.593165900558 638.924191489816"

def case10():
    global startD
    startD = "988.957219600677 203.097328250296"

def case11():
    global startD
    startD = "236.612662598491 236.612662598491"

def case12():
    global startD
    startD = "216.56499119848 309.771819310263"

def case13():
    global startD
    startD = "2649.79541879892 466.064526960254"

def case14():
    global startD
    startD = "3186.07062859833 890.416397459805"

def case15():
    global startD
    startD = "3210.01645829901 523.117492870428"

def case16():
    global startD
    startD = "3186.07062859833 671.61494234018"

def case17():
    global startD
    startD = "3810.33284059912 830.857721660286"

def case18():
    global startD
    startD = "3606.29209620133 431.313115259632"

def case19():
    global startD
    startD = "116.313606 39.826740"

def default():  # 默认情况下执行的函数
    print('No such case')

while True:
    print("waiting for connection...")
    tcpCliSock, addr = tcpSerSock.accept()
    print("...connected from:", addr)


    while True:
        data = tcpCliSock.recv(BUFSIZ)
        if not data:
            break

        data1 ="get your data:%s\n[%s]" % (data, ctime())
        print("data is ", data)
        trace =data.decode('utf-8')

        print(trace.split('_', -1)[4])
        switch = {'2': case2,
                  '3': case3,
                  '4': case4,
                  '5': case5,
                  '6': case6,
                  '7': case7,
                  '8': case8,
                  '9': case9,
                  '10': case10,
                  '11': case11,
                  '12': case12,
                  '13': case13,
                  '14': case14,
                  '15': case15,
                  '16': case16,
                  '17': case17,
                  '18': case18,
                  '19': case19,
                  }


        switch.get(trace.split('_', -1)[5], default)()

        print(startD)


        """对excel进行操作"""
        work = xlrd.open_workbook(base_dir("static/Orders1.xls"),formatting_info=True)
        sheet = work.sheet_by_index(0)
        rb = xlrd.open_workbook('static/Orders1.xls',formatting_info=True)
        wb = copy(rb)
        ws = wb.get_sheet(0)
        counter = sheet.nrows
        print(counter)
        print(startD)
        sendDate = trace.split('_', -1)[1]+":00"
        sendDate = datetime.datetime.strptime(sendDate, "%H:%M:%S")
        print(sendDate, type(sendDate))
        ws.write(sheet.nrows, 0, label=counter)
        ws.write(sheet.nrows, 1, label=trace.split('_', -1)[5])
        ws.write(sheet.nrows, 2, label=trace.split('_', -1)[4]+":00")
        ws.write(sheet.nrows, 3, label=trace.split('_', -1)[1]+":00")
        #ws.write(sheet.nrows, 3, label=sendDate)

        ws.write(sheet.nrows, 4, label=trace.split('_', -1)[3]+":00")
        ws.write(sheet.nrows, 5, label=trace.split('_', -1)[7])
        ws.write(sheet.nrows, 6, label=trace.split('_', -1)[8])
        ws.write(sheet.nrows, 7, label=trace.split('_', -1)[6])
        ws.write(sheet.nrows, 8, label=trace.split('_', -1)[10])
        ws.write(sheet.nrows, 9, label=trace.split('_', -1)[9])
        ws.write(sheet.nrows, 10, label=startD.split(' ', -1)[0])
        ws.write(sheet.nrows, 11, label=startD.split(' ', -1)[1])
        wb.save('Orders.xls')  # 保存文件
        route=getRoute()
        print(route)
        count=0
        countall=0
        routee=[[]]
        for routing in route:
            for routiing in routing:
                if routiing == (int(trace.split('_', -1)[5])-1):
                    countall = count + 1

                    i = 0
                    Intimcount = 0
                    Outtimcount = 0
                    route_list = []
                    while i < len(routing):
                        if routing[i]<=18 :
                            route_list.append(routing[i])
                        i+=1

                    print(routing)
                    print(route_list)
                    i=0
                    flag=0
                    while i < (len(route_list)-1):
                        if routiing == route_list[i] :
                            flag = 1

                        if flag == 0:
                            Intimcount += distanceTime[route_list[i]][route_list[i+1]]
                            print(distanceTime[route_list[i]][route_list[i+1]])
                        if flag == 1:
                            Outtimcount += distanceTime[route_list[i]][route_list[i+1]]
                            print(distanceTime[route_list[i]][route_list[i+1]])
                        i += 1
            count += 1



        str1 = ""
        for i in route_list:
            str1 += str(i)+"-"
        print(str1)
        print(Intimcount)
        print(Outtimcount)
        # DataForUser = "您好，车辆预计在"+str(math.ceil(Intimcount))+"分钟左右到达上车地点，请您尽快前往。\n本车预计"+str(math.ceil(Outtimcount))+"分钟到达目的地。"
        DataForUser = str(math.ceil(Intimcount))+"_"+str(math.ceil(Outtimcount))+"_"+str1+"_"+str(countall)
        tcpCliSock.send(DataForUser.encode())
        tcpCliSock.close

tcpSerSock.close