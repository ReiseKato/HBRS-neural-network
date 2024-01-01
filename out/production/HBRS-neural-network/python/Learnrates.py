import numpy as np
import pickle
from matplotlib import pyplot as plt
import csv
import os

dirpath = "learn"

files = sorted(os.listdir(dirpath))
dataV = []
dataR = []

length = 221

for file in files:
    if file.endswith(".csv"):
        file_path = os.path.join(dirpath, file)
        print(file_path)
        with open(file_path) as csv_file:
            reader = csv.reader(csv_file)
            content = next(reader)
        if file.startswith("totalErrV_"):
            dataV.append(content)
        else:
            dataR.append(content)

meanErrorR = []
meanErrorV = []
for i in range(0, len(dataV)):
    temp1 = []
    temp2 = []
    #print(i)
    for j in range(0, len(dataV[i]) - 2, length):
        temp1.append(sum(map(float, filter(None, dataV[i][j:j + length]))) / length)
        temp2.append(sum(map(float, filter(None, dataR[i][j:j + length]))) / length)

    meanErrorV.append(temp1)
    meanErrorR.append(temp2)

colors = ['red', 'green', 'blue', 'orange', 'purple', 'pink', 'cyan', 'magenta']

# Zufällige Farbe auswählen
random_color = np.random.choice(colors)
x = np.arange(10000)
x2 = np.arange(20000)

LRs = [
    0.001,
    0.01,
    0.1,
    0.25,
    0.333,
    0.5,
    1.0,
    0.00001,
    1.5,
    2.5
]

for i in range(0, len(LRs)):
    print(i)
    # if i == 1:
    #     plt.plot(x2, meanErrorV[i], label='Netz Victor', color=np.random.choice(colors))
    #     plt.plot(x2, meanErrorR[i], label='Netz Reise', color=np.random.choice(colors))
    # else:
    plt.plot(x, meanErrorR[i], label='Netz Reise', color=np.random.choice(colors))
    plt.plot(x, meanErrorV[i], label='Netz Victor', color=np.random.choice(colors))

    plt.legend()
    plt.grid()  # Gitterlinien
    plt.xlabel('Iterationen')  # Achsenbeschriftung
    plt.ylabel('Durchschnittlicher Fehler pro Iteration')  # Achsenbeschriftung
    plt.title(f"Mean Total Error for Dataset: V9_known With LR {LRs[i]}f")  # Titel
    # plt.suptitle('Fig 1.1')
    plt.savefig(f"plots/plotLR{LRs[i]}new.png")
    plt.clf()
