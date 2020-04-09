#!/usr/bin/python
from visualization_msgs.msg import Marker
from visualization_msgs.msg import MarkerArray
import rospy
import math
import urllib2
import threading
import time
import random
from geometry_msgs.msg import Point, Pose
from std_msgs.msg import String
from std_msgs.msg import Int32	

topic = 'visualization_marker'

robotMarker = Marker()

current_robot_location = 1

robot_location_array = [[0.326,-.565], [2.46, -0.473], [4.86,-0.50], [7.2,-0.53], [9.7,-0.65], [11.5,-0.74], [13.8,-0.8], [15.3,-0.823],[16.6,-0.931], [18.8,-0.8],[21,-0.90],[23.5,-0.9],[25.4,-0.9],[26.9,-0.9], [29,-0.9],[31,-0.9],[33.3,-1.1],
[33.4,-3.88],[33.2,-6.19],[33.2,-8.38],[32.9,-10.2],[33,-11.6],[33,-14.1],[33,-15.7],[33,-17.8],[33,-19.3],[34.2,-20.9],[36.4,-21],
[38.3,-21],[40,-21.2], [41.4,-21],[41.4,-21]]


def initialize_robot_location():
	robotMarker = Marker()


def move_segbot():
    marker = Marker()

    robot_pose = Pose()
    marker.header.frame_id = "/map"
    marker.type = marker.CYLINDER
    marker.action = marker.ADD
    marker.scale.x = 1
    marker.scale.y = 1
    marker.scale.z = 1
    marker.color.a = 1
    marker.color.r = 0.0
    marker.color.g = 0.0
    marker.color.b = 1.0
    marker.pose.orientation.w = 1.0
    robot_coordinates = robot_location_array[current_robot_location-1]
    print robot_coordinates
    marker.pose.position.x = robot_coordinates[0]
    marker.pose.position.y = robot_coordinates[1]
    marker.pose.position.z = 0

    robot_pose.position.x = robot_coordinates[0]
    robot_pose.position.y = robot_coordinates[1]
    robot_pose.position.z = 0

    robot_pose.orientation.w = 1.0

    marker.id = 20
    marker_publisher.publish(marker)

    robot_pose_publisher.publish(robot_pose)

    rospy.sleep(0.01)
    return

def callback_location(data):
	global current_robot_location
	current_robot_location = data.data
	move_segbot()


if __name__ == '__main__':
    global stop_threads
    try:
        rospy.init_node('test_marker')
        marker_publisher = rospy.Publisher('visualization_marker', Marker,queue_size=1)
        robot_pose_publisher = rospy.Publisher('modified_robot_pose', Pose,queue_size=1)
        robot_loc_counter_subscriber = rospy.Subscriber('/robot_loc_counter',Int32, callback_location)
        rospy.spin()

    except rospy.ROSInterruptException:
	    print "finished!"
