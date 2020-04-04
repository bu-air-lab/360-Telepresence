# Key components of the 360 Interface
The following files form the key parts of the Telepresence interface and the variables that can possibly be changed (but not limited to these variables) are listed as well. 

## 360video.php
The file is the main interface file which shows the map and the 360 video. 

The following items can be modified in the 360video.php to run the experiments:
```
<a-sky src="frames/300_frame.jpg" id="360_image_sky" rotation="0 -1 0"></a-sky>
```
In the above line, the starting frame number can be changed to anything between 300 and 340 for randomization. Also, the rotation of y-axis can be changed from -1 to anything between 0 to 359 for randomization during experiments.

## 360image.js
The file works with changing the frames for the 360 based on the simulated human's behaviors. 
To stop the random behavior for debugging, set the following variable to true, currently it is false and the robot will randomly move around and change orientation which is required for the experiments.
```
var stop_randomization = true;
```

## obj_det_coco.js
The file takes care of the object detection behaviors of the robot and also terminates the trial whenever the tv is in the center of the frame. The trial complete variable is set to true, once the tv is at the center. For the experiments, you can use this variable to run multiple trials but do not forget to reset this variable at the beginning of the trial and once the value of this variable is set to true, you can stop the timer for the current trial.
```
trial_complete = true
```

To run the baseline experiments, you can change the disable_arrows to true, this will not show any arrows. Currently the variable is set to false and hence it will show the arrows to guide the virtual human.
```
var disable_arrows = true;
```

## simulated_human.js
The file contains code for the simulated human behavior. For baseline experiments change the following variable to true.
```
var baseline = true;
```
