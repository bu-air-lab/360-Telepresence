from PIL import Image
import os
path = '/home/xiaohan/Desktop/objectDetection/360-Dataset/images/'
path_out = '/home/xiaohan/Desktop/objectDetection/360-Dataset/split_to_4/'
for filename in os.listdir(path):
	im = Image.open(path + filename)
	width, height = im.size   # Get dimensions

	left = 0
	top = height/4
	right = width
	bottom = height/4 * 3

	# Crop the center of the image
	newIm = im.crop((left, top, right, bottom))
	#newIm.save(path_out + os.path.splitext(filename)[0] + '.jpg')
	newIm_1 = newIm.crop((0, 0, width/4, height/2))
	newIm_1.save(path_out + os.path.splitext(filename)[0] + '_1' + '.jpg')
	newIm_2 = newIm.crop((width/4, 0, width/2, height/2))
	newIm_2.save(path_out + os.path.splitext(filename)[0] + '_2' + '.jpg')
	newIm_3 = newIm.crop((width/2, 0, width/4 * 3, height/2))
	newIm_3.save(path_out + os.path.splitext(filename)[0] + '_3' + '.jpg')
	newIm_4 = newIm.crop((width/4 * 3, 0, width, height/2))
	newIm_4.save(path_out + os.path.splitext(filename)[0] + '_4' + '.jpg')	

#    	newIm.save(path_out + os.path.splitext(filename)[0] + '_l' + '.jpg')
	
