# -*- coding: utf-8 -*-
"""
Created on Mon Apr 12 18:52:01 2021

@author: guan
"""

import copy, random, datetime
import matplotlib.pyplot as plt
import xlrd
import pandas as pd
from dateutil import parser  # 将datetime.time格式转换为datetime.datetime

# 读取订单
filePath = 'Orders1.xls'
data = xlrd.open_workbook(filePath)
sheetNames = data.sheet_names()
orders1 = pd.read_excel(filePath, sheet_name=sheetNames[0])

depot = [2099.93, 798.70]  # 车辆出发的位置
K = 15  # 车辆数目
Q = 5  # 承载能力
Q_max = 10
time_max = 8  # 最大延误时间
idle_time = 1  # 再次回到场站的休整时间
Maxi_distance = 20  # 车辆最大行驶距离km
c1 = 0.003  # 车辆每m的耗电费用
t_s = 25 / 60  # 车辆上下车所耗的时间
P1 = 5  # 对应订单的车辆准时性超出乘客期望服务单位的时间的 惩罚的费用元/min
P2 = 5  # 对应订单的车辆快捷性超出乘客期望服务单位的时间的惩罚的费用
P3 = 2  # 对应订单的车辆舒适性超出乘客期望服务的惩罚的费用

now = datetime.datetime.today()
now = now.replace(hour=0, minute=0, second=0, microsecond=0)
for i in range(len(orders1)):
    au = str(orders1.loc[i, '期望上车时间窗au'])
    bu = str(orders1.loc[i, '期望上车时间窗bu'])
    DTu = str(orders1.loc[i, '期望到达时间DTu'])
    au = (parser.parse(au) - now).seconds / 60
    bu = (parser.parse(bu) - now).seconds / 60
    DTu = (parser.parse(DTu) - now).seconds / 60
    orders1.loc[i, 'au'] = au
    orders1.loc[i, 'bu'] = bu
    orders1.loc[i, 'DTu'] = DTu
# 将订单按照时间排序
orders = orders1.sort_values(by=['au'], ascending=True)
orders = orders.reset_index(drop=True)

indexNumber = [i for i in range(1, len(orders) + 1)]
orderNumber = list(orders.loc[:, '订单'])

# 系数
c_ou = list(orders.loc[:, '准时性系数Ou'])
c_du = list(orders.loc[:, '快捷度系数Du'])
c_u = list(orders.loc[:, '舒适度系数nu'])
DTu = list(orders.loc[:, 'DTu'])  # 期望到达时间


# 计算行驶距离和时间矩阵
def time_distance(orders):
    df_distance = pd.DataFrame(columns=[i for i in range(len(orders) + 1)])
    df_time = pd.DataFrame(columns=[i for i in range(len(orders) + 1)])
    for i in range(len(orders) + 1):
        for j in range(len(orders) + 1):
            if i == 0 and j != 0:
                dis = ((depot[0] - orders.loc[j - 1, 'LX']) ** 2 +
                       (depot[1] - orders.loc[j - 1, 'LY']) ** 2) ** 0.5
            elif i != 0 and j == 0:
                dis = ((depot[0] - orders.loc[i - 1, 'LX']) ** 2 +
                       (depot[1] - orders.loc[i - 1, 'LY']) ** 2) ** 0.5
            elif i == j:
                dis = 0
            else:
                dis = ((orders.loc[i - 1, 'LX'] - orders.loc[j - 1, 'LX']) ** 2 +
                       (orders.loc[i - 1, 'LY'] - orders.loc[j - 1, 'LY']) ** 2) ** 0.5
            t = ((dis / 1000) / 25) * 60  # 分钟
            df_distance.loc[i, j] = dis / 1000
            df_time.loc[i, j] = t
    return df_distance, df_time

# 参数设置
candidate_count = 50  # 候选集合长度
taboo_list_length = 20  # 禁忌表长度
iteration_count = 3  # 迭代次数
# 计算距离、时间
df_distance, df_time = time_distance(orders)
# 随机生成初始线路
def random_first_full_road(indexNumber):
    X = copy.deepcopy(indexNumber)
    random.shuffle(X)
    # 随机选择K-1个位置插入两个0，分割车辆
    indexs = sorted(random.sample([i for i in range(1, len(X))], K - 1))

    # 将index里的元素按顺序排列，有利于产生可行解
    for i in range(len(indexs)):
        if i == 0:
            X[0:indexs[i]] = sorted(X[0:indexs[i]])
        else:
            X[indexs[i - 1]:indexs[i]] = sorted(X[indexs[i - 1]:indexs[i]])

    for i in range(len(indexs)):
        X.insert(indexs[i] + 2 * i, 0)  # (index, value)在index索引值前插入value
        X.insert(indexs[i] + 2 * i, 0)
    return X


# 解的拆分
def X_sol(X):
    indexs = []
    num = 0
    route = []
    while num < len(X) - 1:
        if X[num] == 0 and X[num + 1] == 0:
            indexs.append([num, num + 1])
        num += 1
    if len(indexs) != 0:
        indexList = []
        for i in range(len(indexs) + 1):
            if i == 0:
                l = [0, indexs[i][0]]
            elif i > 0 and i < len(indexs):
                l = [indexs[i - 1][-1], indexs[i][0]]
            else:
                l = [indexs[i - 1][-1], len(X) - 1]

            if l[0] != l[1]:
                indexList.append(l)

        for i in range(len(indexList)):
            r = X[indexList[i][0]: indexList[i][1] + 1]
            if r[0] != 0:
                r = [0] + r
            if r[-1] != 0:
                r = r + [0]
            route.append(r)
    else:
        route.append(X)
    # 每个订单的人数
    order_q = {}
    for i in X:
        if i != 0:
            order_q[i] = orders.loc[i - 1, '人数qu']
    return route, order_q


# 初始解验证可行性
def Feasible(X):
    route, order_q = X_sol(X)
    for r in range(len(route)):
        line_route = route[r]
        # 验证车辆是否满足行驶距离约束
        dis = 0
        for i in range(len(line_route) - 1):
            distance = df_distance.loc[line_route[i], line_route[i + 1]]
            dis += distance
        if dis > Maxi_distance:
            #            print('f1')
            return False

            ##是否满足载客人数限制
        qsum = 0
        for key, value in order_q.items():
            if key in line_route:
                qsum += value
        if qsum > Q_max:
            #            print('f2')
            return False

        # 每个订单是否满足最大延误时间限制
        departure_time = 0
        arrival_time = 0
        num = 0
        qsum = 0
        while num < len(line_route) - 1:
            l1, l2 = line_route[num], line_route[num + 1]
            trip_time = df_time.loc[l1, l2]
            arrival_time = departure_time + trip_time  # 车辆到达订单位置的时间
            if l2 != 0:
                qsum += orders.loc[l2 - 1, '人数qu']
                if arrival_time <= orders.loc[l2 - 1, 'au']:  # 如果车辆提前到达
                    departure_time = orders.loc[l2 - 1, 'au'] + orders.loc[l2 - 1, '人数qu'] * t_s
                elif arrival_time > orders.loc[l2 - 1, 'au'] and arrival_time <= orders.loc[
                    l2 - 1, 'bu'] + time_max:  # 如果车辆在时间窗内到达或者可允许延误的时间窗内到达
                    departure_time = arrival_time + orders.loc[l2 - 1, '人数qu'] * t_s
                else:
                    #                    print('f3')
                    return False
            else:  # 接到最后一个订单
                departure_time = arrival_time + qsum * t_s + idle_time
            num += 1
    return True


def X_cost(X):
    route, order_q = X_sol(X)
    z2 = 0  # 准时成本
    z3 = 0  # 快捷成本
    z4 = 0  # 舒适成本
    Z = 0  # 总成本
    arrival_time = 0
    departure_time = 0
    qsum = 0
    # 能耗成本
    dis = 0
    for r in range(len(route)):
        line_route = route[r]
        for i in range(len(line_route) - 1):
            distance = df_distance.loc[line_route[i], line_route[i + 1]]
            dis += distance
    z1 = dis * c1 * 1000

    # 准时性成本-车辆在时间窗内开始服务则不产生成本，否则产生成本
    for i in range(len(route)):
        line_route = route[i]
        departure_time = 0
        num = 0
        qsum = 0
        one_v = []
        while num < len(line_route) - 1:

            l1, l2 = line_route[num], line_route[num + 1]
            trip_time = df_time.loc[l1, l2]
            arrival_time = departure_time + trip_time  # 车辆到达订单位置的时间
            if l2 != 0:
                one_v.append(l2)
                q = orders.loc[l2 - 1, '人数qu']
                qsum += q
                if arrival_time <= orders.loc[l2 - 1, 'au']:  # 如果车辆提前到达
                    departure_time = orders.loc[l2 - 1, 'au'] + orders.loc[l2 - 1, '人数qu'] * t_s
                elif arrival_time <= orders.loc[l2 - 1, 'bu'] + time_max:  # 如果车辆在时间窗内到达或者可允许延误的时间窗内到达
                    departure_time = arrival_time + orders.loc[l2 - 1, '人数qu'] * t_s
                    if arrival_time > orders.loc[l2 - 1, 'bu']:  # 超过容忍时间窗，给与惩罚
                        z2 += (arrival_time - orders.loc[l2 - 1, 'bu']) * c_ou[l2 - 1] * q * P1
            else:  # 接到最后一个订单
                final_qtime = departure_time
                # 计算快捷性成本
                for v in one_v:
                    if DTu[v - 1] < arrival_time:
                        z3 += (arrival_time - DTu[v - 1]) * order_q[v] * c_du[v - 1] * P2
                # 计算舒适性成本
                if qsum > Q:  # 大于荷载人数，舒适性得到惩罚
                    for v in one_v:
                        z4 += (order_q[v] / qsum) * (arrival_time - final_qtime) * order_q[v] * c_u[v - 1] * P3
                departure_time = arrival_time + qsum * t_s + idle_time
                qsum = 0  # 下一趟旅程重置
                departure_time = 0
                one_v = 0
            num += 1
    Z = z1 + z2 + z3 + z4
    cost_list = [z1, z2, z3, z4]
    return Z, cost_list


# 2-opt算子--随机选择一个序列的两个位置进行翻转
def Two_opt(X):
    ssroute, order_q = X_sol(X)
    indexs = []  # 序列元素大于2的索引
    for i in range(len(ssroute)):
        if len(ssroute[i]) > 3:
            indexs.append(i)
    ss = False
    while ss == False:
        route = copy.deepcopy(ssroute)
        s1 = random.choice(indexs)
        s2 = sorted(random.sample([i for i in range(1, len(route[s1]) - 1)], 2))
        indexV = route[s1][s2[0]:s2[1] + 1]
        indexV.reverse()
        route[s1][s2[0]:s2[1] + 1] = indexV
        v = [i for ii in route for i in ii]  # 降维
        ss = Feasible(v)
    #    print('UU1', len([i for i in v if i != 0]))

    return v, [s1] + s2  # 返回解，产生翻转的序列标号，翻转标号


# 2-exchange算子 ----随机选择一个序列的两个位置进行交换
def Two_exchange(X):
    ssroute, order_q = X_sol(X)
    indexs = []  # 序列元素大于3的索引
    for i in range(len(ssroute)):
        if len(ssroute[i]) > 3:
            indexs.append(i)
    ss = False
    while ss == False:
        route = copy.deepcopy(ssroute)
        s1 = random.choice(indexs)
        s2 = sorted(random.sample([i for i in range(1, len(route[s1]) - 1)], 2))
        route[s1][s2[0]], route[s1][s2[1]] = route[s1][s2[1]], route[s1][s2[0]]
        v = [i for ii in route for i in ii]  # 降维
        ss = Feasible(v)
    #    print('UU2', len([i for i in v if i != 0]))
    return v, [s1] + s2  # 返回解，产生翻转的是站点or订单标号，翻转标号


def loc_shift(X):  # 随机选择一个序列中的任意一个节点，将其插入到当前或其他序列中随机指定的位置
    ssroute, order_q = X_sol(X)
    indexs = [i for i in range(1, len(ssroute) - 1)]

    ss = False
    while ss == False:
        route = copy.deepcopy(ssroute)
        s1 = random.sample(indexs, 2)
        s2_1 = random.choice([i for i in range(1, len(route[s1[0]]) - 1)])
        s2_2 = random.choice([i for i in range(1, len(route[s1[1]]) - 1)])
        y1 = route[s1[0]][s2_1]
        route[s1[0]].remove(y1)
        route[s1[1]].insert(s2_2, y1)
        v = [i for ii in route for i in ii]  # 降维
        ss = Feasible(v)
    #    print('UU3', len([i for i in v if i != 0]))
    return v, s1 + [s2_1, s2_2]  # 返回解，产生翻转的是站点or订单标号，翻转标号


# GES算子-降低车辆使用数目，删减一个序列，并将此序列所有配送点从原序列移除，放入到资源池中，
# 随机选择资源池中的节点插入，对于剩余不可插入的节点重新组成一条序列
def GES_shift(X):
    ssroute, order_q = X_sol(X)
    indexs = [i for i in range(1, len(ssroute) - 1)]
    ss = False
    while ss == False:
        route = copy.deepcopy(ssroute)
        s1 = random.choice(indexs)
        l1 = [i for i in route[s1] if i != 0]
        route.remove(route[s1])
        curindexs = [i for i in range(len(route))]
        l2 = copy.deepcopy(l1)
        for l in l1:
            ss = False
            while ss == False:
                cur_route = copy.deepcopy(route)
                s2 = random.choice(curindexs)
                s3 = random.choice([i for i in range(1, len(route[s2]))])
                cur_route[s2].insert(s3, l)
                ss = Feasible(cur_route[s2])
            route = cur_route
            l2.remove(l)
        if len(l2) != 0:
            l = [0] + l2 + [0]
            cur_route.append(l)
            ss = Feasible(l)
        else:
            ss = True
    v = [i for ii in cur_route for i in ii]  # 降维
    #    print('UU4', len([i for i in v if i != 0]))
    return v, [s1, s2, s3]


# 获取下一条线路
def single_search(min_X, min_cost):
    XX = copy.deepcopy(min_X)
    # 生成候选集合列表和其对应的移动列表
    candidate_list = []
    candidate_move_list = []
    taboo_list = []

    while len(candidate_list) < candidate_count:
        #        print(len(candidate_list))
        # 随机选择一个算子
        choice = random.randint(0, 3)
        if choice == 0:
            tmp_X, tmp_move = Two_opt(XX)
        elif choice == 1:
            tmp_X, tmp_move = Two_exchange(XX)
        elif choice == 2:
            tmp_X, tmp_move = loc_shift(XX)
        else:
            tmp_X, tmp_move = GES_shift(XX)
        # print("tmp_route:",tmp_route)
        if tmp_X not in candidate_list:
            candidate_list.append(tmp_X)
            candidate_move_list.append([choice] + tmp_move)

    # 计算候选集合各路径的成本
    candidate_cost_list = []
    candidate_Partcosts_list = []
    for candidate in candidate_list:
        Cost, Partcosts = X_cost(candidate)
        candidate_cost_list.append(Cost)
        candidate_Partcosts_list.append(Partcosts)

    # print(candidate_list)

    min_candidate_cost = min(candidate_cost_list)  # 候选集合中最短路径
    min_candidate_index = candidate_cost_list.index(min_candidate_cost)
    min_candidate = candidate_list[min_candidate_index]  # 候选集合中最短路径对应的线路
    min_candidate_Partcosts = candidate_Partcosts_list[min_candidate_index]
    move = candidate_move_list[min_candidate_index]

    if min_candidate_cost < min_cost:
        min_cost = min_candidate_cost
        min_Partcosts = min_candidate_Partcosts
        min_X = min_candidate

        if move in taboo_list:  # 藐视法则，当此移动导致的值更优，则无视该禁忌列表
            taboo_list.remove(move)
        if len(taboo_list) >= taboo_list_length:  # 判断该禁忌列表长度是否以达到限制，是的话移除最初始的move
            taboo_list.remove(taboo_list[0])
        taboo_list.append(move)  # 将该move加入到禁忌列表
        return min_X, min_cost, min_Partcosts

    else:
        # 当未找到更优路径时，选择次优路线，如果该次优路线在禁忌表里，则更次一层，依次类推，找到一条次优路线
        if move in taboo_list:
            tmp_min_candidate = min_candidate
            tmp_min_candidate_cost = min_candidate_cost
            tmp_min_candidate_Partcosts = min_candidate_Partcosts
            tmp_move = move

            while move in taboo_list:
                candidate_list.remove(min_candidate)
                candidate_cost_list.remove(min_candidate_cost)
                candidate_Partcosts_list.remove(min_candidate_Partcosts)
                candidate_move_list.remove(move)

                min_candidate_cost = max(candidate_cost_list)  # 候选集合中成本最大
                min_candidate_index = candidate_cost_list.index(min_candidate_cost)
                min_candidate = candidate_list[min_candidate_index]  # 候选集合中最短路径对应的线路
                min_candidate_Partcosts = candidate_Partcosts_list[min_candidate_index]
                move = candidate_move_list[min_candidate_index]
                if len(candidate_list) < 10:  # 防止陷入死循环，在候选集个数小于10的时候跳出
                    min_candidate = tmp_min_candidate
                    move = tmp_move
                    min_candidate_cost = tmp_min_candidate_cost
                    min_candidate_Partcosts = tmp_min_candidate_Partcosts

        if len(taboo_list) >= taboo_list_length:  # 判断该禁忌列表长度是否以达到限制，是的话移除最初始的move
            taboo_list.remove(taboo_list[0])
        taboo_list.append(move)
        return min_candidate, min_candidate_cost, min_candidate_Partcosts


# 进行taboo_search直到达到终止条件:循环100次
def taboo_search(min_X, min_cost):
    X_list = []
    costList = []
    PartcostsList = []
    for i in range(iteration_count):
        min_X, min_cost, min_Partcosts = single_search(min_X, min_cost)
        X_list.append(min_X)
        costList.append(min_cost)
        PartcostsList.append(min_Partcosts)
        print("迭代次数" + str(i) + ":" + str(min_cost))
    return X_list, costList, PartcostsList


# 画线路图
def draw_line_pic(route, cost, duration, desc):
    x = []
    y = []
    for item in route:
        x.append(orders.loc[item, 'LX'])
        y.append(orders.loc[item, 'LY'])
    x0 = [x[0], ]
    y0 = [y[0], ]
    plt.plot(x, y)
    plt.scatter(x0, y0, marker="o", c="r")
    for a, b in zip(x0, y0):
        plt.text(a, b, (a, b), ha='center', va='bottom', fontsize=10)
    plt.title("Taboo_Search(" + desc + ": " + str(cost) + ")")
    plt.show()

def getRoute():
    # 生成初始可行解
    ss = False
    while ss == False:
        X = random_first_full_road(indexNumber)
        ss = Feasible(X)
    # 计算成本
    Cost, Partcosts_list = X_cost(X)
    min_X, min_cost = X, Cost
    t1 = datetime.datetime.now()
    X_list, costList, PartcostsList = taboo_search(min_X, min_cost)
    t2 = datetime.datetime.now()
    # 选择迭代过程中最小成本
    min_cost = min(costList)
    index = costList.index(min_cost)
    # 选择成本最小解
    new_X = X_list[index]
    # 订单解析
    min_X = copy.deepcopy(new_X)
    for i in range(len(new_X)):
        if new_X[i] != 0:
            min_X[i] = orderNumber[new_X[i] - 1]
    route, qq = X_sol(min_X)
    print("最小成本：", min_cost)
    print("运行时间：", (t2 - t1).seconds / 60)
    return route

    # draw_line_pic(route,min_cost,duration,"random")


if __name__ == '__main__':

    route = getRoute()
    routee= [[] for i in range(len(route))]
    print(route)
    i = 0
    for routing in route:
        for routiing in routing:
            if routiing <= 18:
                routee[i].append(routiing)

        i = i + 1
    print(routee)
    print(len(routee))

