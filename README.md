# 360-Telepresence

1. Install Xampp on your machine.
2. Follow the following tutorial to make a new directory named telepresence (Link: https://www.youtube.com/watch?v=YsmMVwz1rlo).
3. Download this video from google drive (Link to the video (https://drive.google.com/file/d/16dv9i_gsk9WaVP7cTfyV7mdBG47Jg4zu/view?usp=sharing)
) and add it to the videos folder.
4. Once you copy all the files from the repository to the telepresence folder, you can run the code by going to the web address http://localhost/telepresence

## ROS Files

1. Create a new workspace
2. Inside the workspace src folder copy the telepresence folder from the ROS files folder
3. Do a catkin_make
4. Start a new terminal and source it with your workspace
5. Then run the following command in the terminal 
```
roslaunch telepresence simulation.launch
```
6. Then open a new terminal and type the following command
```
rosrun rviz rviz -d ~/YourWorkspaceFolder/src/telepresence/include/telepresence/robot_marker.rviz
```
Now you should see the map in the RVIZ.

## Adding files to Xampp and then running the interface

1. Copy the telepresence folder from the Server files folder to your htdocs folder (See above tutorial to learn how to add the files).
2. Start Xampp (Follow the tutorial on top to learn this.)
3. Now open your browser and go to http://localhost/telepresence/360.php
4. You should see the map on the left side and the 360 interface on the right
