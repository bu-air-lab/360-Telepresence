<?php
?>
<html>
  <head>
    <script src="https://aframe.io/releases/1.0.4/aframe.min.js"></script>
	<link rel="stylesheet" href="style.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script type="text/javascript" src="360_video.js?version=8"></script>
	<script type="text/javascript" src="http://static.robotwebtools.org/EaselJS/current/easeljs.min.js"></script>
	<script type="text/javascript" src="http://static.robotwebtools.org/EventEmitter2/current/eventemitter2.min.js"></script>
	<script type="text/javascript" src="http://static.robotwebtools.org/roslibjs/current/roslib.min.js"></script>
	<script type="text/javascript" src="http://static.robotwebtools.org/ros2djs/current/ros2d.min.js"></script>
	<script type="text/javascript" src="http://static.robotwebtools.org/nav2djs/current/nav2d.min.js"></script>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="ros_360.js?version=3"></script>
	<script>
		window.onload=function(){
			init_360_vid();
			init_ros_360();
		}
	</script>
	
	<style>
	a-scene {
		height: 300px;
		width: 550px;
		margin: 0px;
	}
	</style>
  </head>
  <body>
	<center> <h1> Telepresence 360 </h1> </center>
	
	  <div class="container">
			<div class="row">
				<center>
				<div class="col-md-6">
				
					<div id="nav"></div>					
				</div>
				</center>
				<center>
					<div class="col-md-6" id="embedded_scene">
						<a-scene embedded>
							<a-assets>
								<video id="360_vid" autoplay loop="true" src="videos/video1.mp4"> </video>
							</a-assets>
							
							<a-videosphere src="#360_vid" width="3" height="5" rotation="0 -70 0"></a-videosphere>

						</a-scene>
						
					</div>
				</center>
			</div>
		</div>
	
    
	
  </body>
</html>