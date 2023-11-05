import matplotlib.pyplot as plt
import csv
import pandas as pd

path = 'S:\\HBRS\\neural network\\git repo\\HBRS-neural-network\\totalError.csv'


arr = []

# with open(path, newline='') as inputfile:
#     for row in csv.reader(inputfile):
#         arr.append(row[0])

# df = pd.read_csv(path)


plt.plot(arr)
plt.ylabel('total Error')
plt.xlabel('Iterations')
plt.axis((0, len(arr), 0, 0.5))
plt.show()
# print(df)