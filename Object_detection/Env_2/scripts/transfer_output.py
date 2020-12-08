from PIL import Image
import os
path = '/home/xiaohan/Desktop/objectDetection/360-Dataset/images/'
path_in = '/home/xiaohan/Desktop/objectDetection/YOLOv3/(3)out/'
path_out = '/home/xiaohan/Desktop/objectDetection/YOLOv3/output/'
for filename in os.listdir(path):
    im = Image.open(path + filename)
    width, height = im.size

    file_1 = open(path_in + os.path.splitext(filename)[0] + '_1' + '.txt')
    file_2 = open(path_in + os.path.splitext(filename)[0] + '_2' + '.txt')
    file_3 = open(path_in + os.path.splitext(filename)[0] + '_3' + '.txt')
    file_4 = open(path_in + os.path.splitext(filename)[0] + '_4' + '.txt')
    outfile = open(path_out + os.path.splitext(filename)[0] + '.txt', 'w+')
    for line in file_1:
        split_line = line.split(' ')
        split_line[3] = str(int(int(split_line[3]) + height/4))
        split_line[5] = str(int(int(split_line[5]) + height/4))
        new_line = ' '.join(split_line)
        outfile.write(new_line + '\n')
    for line in file_2:
        split_line = line.split(' ')
        split_line[2] = str(int(int(split_line[2]) + width/4))
        split_line[4] = str(int(int(split_line[4]) + width/4))
        split_line[3] = str(int(int(split_line[3]) + height/4))
        split_line[5] = str(int(int(split_line[5]) + height/4))
        new_line = ' '.join(split_line)
        outfile.write(new_line + '\n')
    for line in file_3:
        split_line = line.split(' ')
        split_line[2] = str(int(int(split_line[2]) + width/2))
        split_line[4] = str(int(int(split_line[4]) + width/2))
        split_line[3] = str(int(int(split_line[3]) + height/4))
        split_line[5] = str(int(int(split_line[5]) + height/4))
        new_line = ' '.join(split_line)
        outfile.write(new_line + '\n')
    for line in file_4:
        split_line = line.split(' ')
        split_line[2] = str(int(int(split_line[2]) + width/4 * 3))
        split_line[4] = str(int(int(split_line[4]) + width/4 * 3))
        split_line[3] = str(int(int(split_line[3]) + height/4))
        split_line[5] = str(int(int(split_line[5]) + height/4))
        new_line = ' '.join(split_line)
        outfile.write(new_line + '\n')
    file_1.close()
    file_2.close()
    file_3.close()
    file_4.close()
    outfile.close()
