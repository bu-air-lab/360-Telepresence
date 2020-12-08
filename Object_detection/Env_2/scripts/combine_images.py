import sys
import os
from PIL import Image

path_1 = '/home/xiaohan/Desktop/objectDetection/YOLOv3/output_1/'
path_2 = '/home/xiaohan/Desktop/objectDetection/YOLOv3/output_2/'
path_3 = '/home/xiaohan/Desktop/objectDetection/YOLOv3/output_3/'
path_4 = '/home/xiaohan/Desktop/objectDetection/YOLOv3/output_4/'
path_out = '/home/xiaohan/Desktop/objectDetection/YOLOv3/output_1234/'
for filename in os.listdir(path_1):
  fileroot = '_'.join(filename.split('_')[:-1])
  im_1 = Image.open(path_1 + filename)
  im_2 = Image.open(path_2 + fileroot + '_2' + '.png')
  im_3 = Image.open(path_3 + fileroot + '_3' + '.png')
  im_4 = Image.open(path_4 + fileroot + '_4' + '.png')
  images = [im_1, im_2, im_3, im_4]
  widths, heights = zip(*(i.size for i in images))
  total_width = sum(widths)
  max_height = max(heights)

  new_im = Image.new('RGB', (total_width, max_height))

  x_offset = 0
  for im in images:
    new_im.paste(im, (x_offset,0))
    x_offset += im.size[0]

  new_im.save(path_out + fileroot + '.jpg')
