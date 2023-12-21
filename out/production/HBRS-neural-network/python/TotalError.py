import numpy as np
import pickle
from matplotlib import pyplot as plt
import csv

#csv.field_size_limit(7 * 1024 * 1024)  # 1 Megabyte = 1024 Kilobytes = 1024*1024 Bytes

fileR = 'totalErrorReise.csv'
fileV = 'totalErrorVic.csv'

with open(fileR) as csv_file_R:
    reader = csv.reader(csv_file_R)
    content1 = next(reader)

with open(fileV) as csv_file_V:
    reader2 = csv.reader(csv_file_V)
    content2 = next(reader2)

print(len(content1))
print(len(content2))

# berechne den durchschnittlichen total Error pro iteration
meanErrorR = []
meanErrorV = []
for i in range(0, len(content1)-2, 16):
    meanErrorR.append((sum(map(float, content1[i:i+16]))/16))
    meanErrorV.append((sum(map(float, content2[i:i+16]))/16))



x = np.arange(10000)

plt.plot(x, meanErrorR, label='Error Reise', color='blue')
plt.plot(x, meanErrorV, label='Error Victor', color='red')
plt.legend()

plt.grid()  # Gitterlinien
plt.xlabel('Iteration')  # Achsenbeschriftung
plt.ylabel('Mean total Error')  # Achsenbeschriftung
plt.title('Mean total Error')  # Titel
plt.show()  # Plot Anzeigen
