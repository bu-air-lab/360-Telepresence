  /**
   * Setup all GUI elements when the page is loaded.
   */
  function init_ros_360() {
    // Connect to ROS.
    var ros = new ROSLIB.Ros({
      url : 'ws://192.168.1.10:9090'
    });

     // Create the main viewer.
    var viewer = new ROS2D.Viewer({
      divID : 'nav',
      width : 550,
      height : 300
    });

    var gotogoal = new ROSLIB.ActionClient({
      ros : ros,
      serverName : '/move_base',
      actionName : 'move_base_msgs/MoveBaseAction'
    })
    var goalposition = new ROSLIB.Vector3(null);
    var goalorientation = new ROSLIB.Quaternion({x:0, y:0, z:0, w:1});
    goalposition.x = 0.0;
    goalposition.y = 0.0;
    var pose = new ROSLIB.Pose({
        position : goalposition,
        orientation : goalorientation
    });

    var goal = new ROSLIB.Goal({
        actionClient : gotogoal,
        goalMessage : {
            target_pose : {
                header : {
                    frame_id : '/map'
                },
                pose : pose
            }
        }
    });
           

    Setup the nav client.
     var nav = NAV2D.OccupancyGridClientNav({
      ros : ros,
      rootObject : viewer.scene,
      viewer : viewer,
      serverName : '/move_base'
         

    });
   }
function sendGoal(){
    var ros = new ROSLIB.Ros({
      url : 'ws://192.168.1.10:9090'
    });


    var gotogoal = new ROSLIB.ActionClient({
      ros : ros,
      serverName : '/move_base',
      actionName : 'move_base_msgs/MoveBaseAction'
    });
    var goalposition = new ROSLIB.Vector3(null);
    var goalorientation = new ROSLIB.Quaternion({x:0, y:0, z:0, w:1});
    goalposition.x = 21.3901;
    goalposition.y = 36.5216;
    var pose = new ROSLIB.Pose({
        position : goalposition,
        orientation : goalorientation
    });

    var goal = new ROSLIB.Goal({
        actionClient : gotogoal,
        goalMessage : {
            target_pose : {
                header : {
                    frame_id : '/map'
                },
                pose : pose
            }
        }
    });
    goal.send();
    console.log("send!");
}
function cancelGoal(){
    goal.cancel();
}