import numpy as np
import pickle
from matplotlib import pyplot as plt
import csv
import os
from collections import deque
# lese die tatsächlichen Werte ein
y_actual = np.array([])
y_actual = np.empty((0, 6))

with open("y_actual.csv") as csv_file:
    reader = csv.reader(csv_file, delimiter=';')
    for i, row in enumerate(reader):
        temp = np.array(row, dtype=np.float32)
        y_actual = np.vstack((y_actual, temp))



path = "unLearn"


files = sorted(os.listdir(path))
linked_list = deque()
# lese die ausgabe Werte der Netze für die unbekannten daten ein
# speichere 2D npArrays in verketteter Liste
for file in files:
    file_path = os.path.join(path, file)
    cfile = np.empty((0, 6))
    with open(file_path) as csv_file:
        reader = csv.reader(csv_file, delimiter=';')
        for i, row in enumerate(reader):
            temp = np.array(row, dtype=np.float32)
            cfile = np.vstack((cfile, temp))

        linked_list.append(cfile)


LRs = [
    0.001,
    0.1,
    1.0,
    0.00001,
    1.5,
    2.5
]

print(linked_list)
x_val = np.arange(1, 7)

# plote die linked list indem Fall für victor
for i in range(len(linked_list)):
    temp = linked_list[i]
    for j in range(len(temp)):

        plt.plot(x_val, temp[j], label=f'pred. output {j}')
        plt.plot(x_val, y_actual[j], label=f'actual output {j}', linestyle='--')

    plt.xlabel('Index des Ausgabe Neurons')
    plt.ylabel('Ausgabewert')
    plt.suptitle(f"Überprüfung der Daten von V2_Unknown mit LR = {LRs[i]}")
    plt.title('Mit Netz Victor trainiert auf V2_known')


    # Legende hinzufügen
    plt.legend()
    plt.savefig(f"plots/plotLineVictor{i}.png")
    plt.clf()




# Anzeige des Plots
# plt.show()