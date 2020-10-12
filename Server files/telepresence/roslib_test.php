<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />

<script type="text/javascript" src="http://static.robotwebtools.org/EventEmitter2/current/eventemitter2.min.js"></script>
<script type="text/javascript" src="http://static.robotwebtools.org/roslibjs/current/roslib.min.js"></script>

<script type="text/javascript" type="text/javascript">
  // Connecting to ROS
  // -----------------

  var ros = new ROSLIB.Ros({
    url : 'ws://192.168.1.10:9090'
  });

  ros.on('connection', function() {
    console.log('Connected to websocket server.');
  });

  ros.on('error', function(error) {
    console.log('Error connecting to websocket server: ', error);
  });

  ros.on('close', function() {
    console.log('Connection to websocket server closed.');
  });

  // Publishing a Topic
  // ------------------

  var cmdVel = new ROSLIB.Topic({
    ros : ros,
    name : '/cmd_vel',
    messageType : 'geometry_msgs/Twist'
  });

  var twist = new ROSLIB.Message({
    linear : {
      x : 0.1,
      y : 0.2,
      z : 0.3
    },
    angular : {
      x : -0.1,
      y : -0.2,
      z : -0.3
    }
  });
  cmdVel.publish(twist);

  // Subscribing to a Topic
  // ----------------------

  var listener = new ROSLIB.Topic({
    ros : ros,
    name : '/amcl_pose',
    messageType : 'geometry_msgs/PoseWithCovarianceStamped'
  });

  listener.subscribe(function(message) {
    console.log('Received message on ' + listener.name + ': ' + message.pose.pose.position.x);
  //  listener.unsubscribe();
  });

  // Calling a service
  // -----------------

  

  // Getting and setting a param value
  // ---------------------------------

  
</script>
</head>

<body>
  <h1>Simple roslib Example</h1>
  <p>Check your Web Console for output.</p>
</body>
</html>