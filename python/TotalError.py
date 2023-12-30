import numpy as np
import pickle
from matplotlib import pyplot as plt
import csv
import os

# csv.field_size_limit(7 * 1024 * 1024)  # 1 Megabyte = 1024 Kilobytes = 1024*1024 Bytes

# fileR = 'totalErrorReise.csv'
# fileV = 'totalErrorVic.csv'
#
# # lese die CSV Dateien ein
# with open(fileR) as csv_file_R:
#     reader = csv.reader(csv_file_R)
#     content1 = next(reader)
#
# with open(fileV) as csv_file_V:
#     reader2 = csv.reader(csv_file_V)
#     content2 = next(reader2)
#
# #print(len(content1))
# #print(len(content2))
#
# # berechne den durchschnittlichen total Error pro iteration
#
# meanErrorR = []
# meanErrorV = []
# for i in range(0, len(content1) - 2, 32):
#     meanErrorR.append((sum(map(float, content1[i:i + 32])) / 32))
#     meanErrorV.append((sum(map(float, content2[i:i + 32])) / 32))
#
# x = np.arange(10000)
#
# #print(len(meanErrorV))
#
# # plotte die Iterationen
#
# plt.plot(x, meanErrorR, label='Error Reise', color='green')
# plt.plot(x, meanErrorV, label='Error Victor', color='red')
# plt.legend()
#
# plt.grid()  # Gitterlinien
# plt.xlabel('Iteration')  # Achsenbeschriftung
# plt.ylabel('Durchschnittlicher Fehler pro Iteration')  # Achsenbeschriftung
# plt.title('Mean total Error')  # Titel
# #plt.suptitle('Fig 1.1')
# plt.savefig("plots/vic_plot1.png")
# plt.show()  # Plot Anzeigen

dirpath = "errors"

files = sorted(os.listdir(dirpath))
dataV = []
dataR = []

length = []

with open("length.csv") as csv_file:
    reader = csv.reader(csv_file)
    temp = next(reader)
length = temp

length = list(map(int, length))

print(length)

print(type(length[0]))

for file in files:
    if file.endswith(".csv"):
        file_path = os.path.join(dirpath, file)
        print(file_path)
        with open(file_path) as csv_file:
            reader = csv.reader(csv_file)
            content = next(reader)
        if file.startswith("totalErrorVic"):
            dataV.append(content)
        else:
            dataR.append(content)

# print(len(dataV[1]))
# print(len(dataR[1]))
meanErrorR = []
meanErrorV = []
for i in range(0, len(length)):
    temp1 = []
    temp2 = []
    print(i)
    for j in range(0, len(dataV[i]) - 2, length[i]):
        temp1.append(sum(map(float, filter(None, dataV[i][j:j + length[i]]))) / length[i])
        temp2.append(sum(map(float, filter(None, dataR[i][j:j + length[i]]))) / length[i])
        # temp1 = [float(x) for x in dataV[i][j:j + length[i]] if x]
        # temp2 = [float(x) for x in dataR[i][j:j + length[i]] if x]
        #
        # if temp1:
        #     mean = sum(temp1) / len(temp1)
        #     meanErrorV.append(mean)
        #
        # if temp2:
        #     mean = sum(temp2) / len(temp2)
        #     meanErrorR.append(mean)

    meanErrorV.append(temp1)
    meanErrorR.append(temp2)

# print(len(meanErrorR))
# print(len(meanErrorR[1]))
# for i in range(0, len(length)):
#     if len(meanErrorR[i]) == 100000:
#         print("R:yes",i)
#
#     else:
#         print("R:no" ,i)
#         print("R", i, len(meanErrorR[i]))
#
#     if len(meanErrorV[i]) == 100000:
#         print("V:yes", i)
#     else:
#         print("V:no" ,i)
#         print("V", i, len(meanErrorR[i]))

colors = ['red', 'green', 'blue', 'orange', 'purple', 'pink', 'cyan', 'magenta']

# Zufällige Farbe auswählen
random_color = np.random.choice(colors)
x = np.arange(200000)

#
# fig, axs = plt.subplots(3, 3)
# for i in range(2):
#     for j in range(3):
#         if i == 0:
#             axs[i, j].plot(x, meanErrorV[i], label="Netz Victor", color=np.random.choice(colors))
#             axs[i, j].plot(x, meanErrorR[i], label="Netz Reise", color=np.random.choice(colors))
#             axs[i, j].set_title(f"Mean Total Error, Dataset: V{i}")  # Titel
#         else:
#             axs[i, j].plot(x, meanErrorV[i+3], label="Netz Victor", color=np.random.choice(colors))
#             axs[i, j].plot(x, meanErrorR[i+3], label="Netz Reise", color=np.random.choice(colors))
#             axs[i, j].set_title(f"Mean Total Error, Dataset: V{i+3}")  # Titel
#         axs[i, j].legend()
#         axs[i, j].grid()
#         axs[i, j].set_xlabel('Iterationen')  # Achsenbeschriftung
#         axs[i, j].set_ylabel('Durchschnittlicher Fehler pro Iteration')  # Achsenbeschriftung

for i in range(1, 2):
    plt.plot(x, meanErrorR[i], label='Netz Reise', color=np.random.choice(colors))
    plt.plot(x, meanErrorV[i], label='Netz Victor', color=np.random.choice(colors))
    plt.legend()
    plt.grid()  # Gitterlinien
    plt.xlabel('Iterationen')  # Achsenbeschriftung
    plt.ylabel('Durchschnittlicher Fehler pro Iteration')  # Achsenbeschriftung
    plt.title(f"Mean Total Error for Dataset: V{i}")  # Titel
    #plt.suptitle('Fig 1.1')
    plt.savefig(f"plots/plot{i}t.png")
    plt.clf()
