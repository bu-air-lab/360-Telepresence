import matplotlib.pyplot as plt
import numpy as np
import seaborn as sns

baseline_2m_mean = 1343.70
baseline_4m_mean = 1651.94
baseline_6m_mean = 1892.11
baseline_8m_mean = 1968.36
baseline_10m_mean = 1895.46
baseline_12m_mean = 2024.953
baseline_14m_mean = 2118.65
baseline_16m_mean = 2364.01
baseline_18m_mean = 2241.10


scene_analyser_2m_mean = 186.96
scene_analyser_4m_mean = 421.34
scene_analyser_6m_mean = 596.76
scene_analyser_8m_mean = 706.76
scene_analyser_10m_mean = 852.46
scene_analyser_12m_mean = 914.4
scene_analyser_14m_mean = 953.73
scene_analyser_16m_mean = 1027.2
scene_analyser_18m_mean = 1084.76

baseline_2m_error = 1687.98
baseline_4m_error = 1902.71
baseline_6m_error = 1920.88
baseline_8m_error = 2177.16
baseline_10m_error = 1773.25
baseline_12m_error = 1865.94
baseline_14m_error = 1845.18
baseline_16m_error = 1900.92
baseline_18m_error = 1845.08

scene_analyser_2m_error = 446.42
scene_analyser_4m_error = 767.65
scene_analyser_6m_error = 690.17
scene_analyser_8m_error = 783.25
scene_analyser_10m_error = 890.64
scene_analyser_12m_error = 854.27
scene_analyser_14m_error = 808.60
scene_analyser_16m_error = 847.21
scene_analyser_18m_error = 882.07

q_a_error = (baseline_2m_error, baseline_4m_error, baseline_6m_error, baseline_8m_error, baseline_10m_error, baseline_12m_error, baseline_14m_error, baseline_16m_error, baseline_18m_error)
q_b_error = (scene_analyser_2m_error, scene_analyser_4m_error, scene_analyser_6m_error, scene_analyser_8m_error, scene_analyser_10m_error, scene_analyser_12m_error, scene_analyser_14m_error, scene_analyser_16m_error, scene_analyser_18m_error)



baseline_means = [baseline_2m_mean,baseline_4m_mean,baseline_6m_mean,baseline_8m_mean,baseline_10m_mean,baseline_12m_mean,baseline_14m_mean,baseline_16m_mean,baseline_18m_mean]
scene_analysis_means = [scene_analyser_2m_mean, scene_analyser_4m_mean, scene_analyser_6m_mean, scene_analyser_8m_mean, scene_analyser_10m_mean, scene_analyser_12m_mean, scene_analyser_14m_mean, scene_analyser_16m_mean, scene_analyser_18m_mean]
n_groups = 9

# fig, ax = plt.subplots(1)
# x_axis =  [0,1]
# x_axis_2 =  [1,2]
sns.set_style("darkgrid")
plt.plot(baseline_means)
# ax.fill_between(x_axis, baseline_2m_mean + baseline_2m_error, baseline_2m_mean - baseline_2m_error)
# ax.fill_between(x_axis_2, baseline_4m_mean + baseline_4m_error, baseline_4m_mean - baseline_4m_error)
plt.plot(scene_analysis_means)
# ax.grid()
plt.show()
