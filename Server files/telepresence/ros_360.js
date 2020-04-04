  /**
   * Setup all GUI elements when the page is loaded.
   */
var ros;
var robot_x_pos;
var robot_y_pos;
var gridClient;
var current_robot_location_message;
var robot_loc_counter_topic;

var robot_current_loc_map = 0;

  function init_ros_360() {
	  
    //setInterval(get_robot_pose, 1000);
    // Connect to ROS.
    ros = new ROSLIB.Ros({
      url : 'ws://192.168.1.10:9090'
    });

     // Create the main viewer.
    var viewer = new ROS2D.Viewer({
      divID : 'rvizMap',
      width : 535,
      height : 361
    });

	// const robotPose = new ROSLIB.Topic({
      // ros : ros,
      // name : '/amcl_pose',
      // messageType : 'geometry_msgs/PoseWithCovarianceStamped'
    // });
	
	
	robot_loc_counter_topic = new ROSLIB.Topic({
		ros : ros,
		name : '/robot_loc_counter',
		messageType : 'std_msgs/Int32'
	});
	
	
	current_robot_location_message = new ROSLIB.Message({
         a: 'robot_current_loc_map'
    });
	
	

	
	gridClient = new ROS2D.OccupancyGridClient({
      ros : ros,
      rootObject : viewer.scene,
      // Use this property in case of continuous updates			
      continuous: true
    });
	
	let startTime;

    // Scale the canvas to fit to the map
    gridClient.on('change', function() {
      startTime = new Date()
      viewer.scaleToDimensions(gridClient.currentGrid.width, gridClient.currentGrid.height);
      viewer.shift(gridClient.currentGrid.pose.position.x, gridClient.currentGrid.pose.position.y);
    });

//    Setup the nav client.
     // var nav = NAV2D.OccupancyGridClientNav({
      // ros : ros,
      // rootObject : viewer.scene,
      // viewer : viewer,
      // serverName : '/move_base'
         

    // });
	findCanvas();
	draw_robot_location();
	//setInterval(update_robot_marker, 5);
	//update_robot_marker();
	
	
	  
}

// var createFunc = function (handlerToCall, discriminator, robotMarker) {
        // return discriminator.subscribe(function(pose){
            // robotMarker.x = pose.pose.pose.position.x;
            // robotMarker.y = -pose.pose.pose.position.y;
            // var quaZ = pose.pose.pose.orientation.z;
            // var degreeZ = 0;
            // if( quaZ >= 0 ) {
                // degreeZ = quaZ / 1 * 180
            // } else {
                // degreeZ = (-quaZ) / 1 * 180 + 180
            // };
            // robotMarker.rotation = -degreeZ + 35;
        // })
    // }

function createFunc (handlerToCall, discriminator, robotMarker) {
	return discriminator.subscribe(function(pose){
		//console.log(pose);
		robotMarker.x = pose.position.x;
		robotMarker.y = -pose.position.y 	;
		var quaZ = pose.orientation.z;
		var degreeZ = 0;
		if( quaZ >= 0 ) {
			degreeZ = quaZ / 1 * 180
		} else {
			degreeZ = (-quaZ) / 1 * 180 + 180
		};
		robotMarker.rotation = -degreeZ + 35;
	})
}
   
   
function advance_robot_location(){
	var sceneElROS = document.querySelector('a-scene');
    var imageSphereElROS = sceneElROS.querySelector("[id='360_image_sky']");
	var current_frameROS = imageSphereElROS.getAttribute('src');
	current_frameROS = current_frameROS.replace("frames/","");
	current_frameROS = current_frameROS.replace("_frame.jpg","");
	current_frameROS = parseInt(current_frameROS);
	robot_current_loc_map = (current_frameROS - 230)/5;
	current_robot_location_message = new ROSLIB.Message({
         data: robot_current_loc_map
    });
	robot_loc_counter_topic.publish(current_robot_location_message);
}
   

function reverse_robot_location(){
	var sceneElROS = document.querySelector('a-scene');
    var imageSphereElROS = sceneElROS.querySelector("[id='360_image_sky']");
	var current_frameROS = imageSphereElROS.getAttribute('src');
	current_frameROS = current_frameROS.replace("frames/","");
	current_frameROS = current_frameROS.replace("_frame.jpg","");
	current_frameROS = parseInt(current_frameROS);
	robot_current_loc_map = (current_frameROS - 230)/5;
	current_robot_location_message = new ROSLIB.Message({
         data: robot_current_loc_map
    });
	if(current_frameROS>230){
		robot_loc_counter_topic.publish(current_robot_location_message);
	}

}
   
function findCanvas(){

	var canvas = document.getElementById("rvizMap").getElementsByTagName('canvas')[0];
	//console.log(canvas.style);
}

function draw_robot_location(){
	var canvas = document.getElementById("rvizMap").getElementsByTagName('canvas')[0];
	var context = canvas.getContext('2d');
	context.strokeStyle = "blue";
	context.strokeRect(75, 60, 10, 10);
	
	// var robotMarker = new ROS2D.NavigationArrow({
		// size : 1.5,
		// strokeSize : 0.05,
		// pulse: true,
		// fillColor: createjs.Graphics.getRGB(100, 10, 20, 0.65)
	// });
	
	var robotMarker = new ROS2D.PolygonMarker({
		pointSize : 0.7,
		//strokeSize : 0.05,
		//pulse: true,
		fillColor: createjs.Graphics.getRGB(238, 100, 50, 1)
	});
	
	robotMarker.addPoint(0,0);
	
	
	var poseTopic = new ROSLIB.Topic({
		ros: ros,
		name: '/modified_robot_pose',
		messageType: 'geometry_msgs/Pose'
	});
	

	
	createFunc('subscribe', poseTopic, robotMarker);
	
	gridClient.rootObject.addChild(robotMarker);
	
}

function update_robot_marker(){
	console.log("Adding image");
	var canvas = document.getElementById("rvizMap").getElementsByTagName('canvas')[0];
	var context = canvas.getContext('2d');
	context.beginPath();
	context.arc(100, 75, 5, 0, 2 * Math.PI);
	context.fillStyle = "blue";
	context.fill();
	
}


// function get_robot_pose(){
	// var listener = new ROSLIB.Topic({
    // ros : ros,
    // name : '/amcl_pose',
    // messageType : 'geometry_msgs/PoseWithCovarianceStamped'
	  // });

	  // listener.subscribe(function(message) {
		// console.log('Received message on ' + listener.name + ': ' + message.pose.pose.position.x);
		// robot_x_pos = message.pose.pose.position.x;
		// robot_y_pos = message.pose.pose.position.y;
	    // listener.unsubscribe();
	  // });
// }



function radians_to_degrees(radians) {
      const pi = Math.PI;
      return radians * (180/pi);
};