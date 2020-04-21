<?php
?>
<html>
  <head>
  	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="style.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<script src="https://aframe.io/releases/1.0.4/aframe.min.js"></script>
	<script type="text/javascript" src="http://static.robotwebtools.org/EaselJS/current/easeljs.min.js"></script>
	<script type="text/javascript" src="http://static.robotwebtools.org/EventEmitter2/current/eventemitter2.min.js"></script>
	<script type="text/javascript" src="http://static.robotwebtools.org/roslibjs/current/roslib.min.js"></script>
	<script type="text/javascript" src="http://static.robotwebtools.org/ros2djs/current/ros2d.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/p5.js/0.9.0/p5.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/p5.js/0.9.0/addons/p5.dom.min.js"></script>
	<script src="https://unpkg.com/ml5@latest/dist/ml5.min.js" type="text/javascript"></script>
  <script type="text/javascript" src="init_trial.js?version=123321212131232212312312313123123321"></script>
	<script type="text/javascript" src="simulated_human.js?version=10182231111111221121122111111120211121"></script>
	<script type="text/javascript" src="360_image.js?version=10800001221213222111212112121212111121112212112"></script>
	<script type="text/javascript" src="ros_360.js?version=1021202111121111232212133111323211211211221231"></script>
	<script src="obj_det_coco.js?version=1212120221311122112121213111"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">







	<script>
		window.onload=function(){
			//init_arrow_simulation();
			//init_360_vid();
      test_simulation(9); //Even is baseline, odd is human sim, 0-19 //
			init_ros_360();
			init_human_simulation();
			init_360_image();

		}
	</script>

	<style>
	a-scene {
		height: 361px;
		width: 535px;
		margin: 0px;
	};

	</style>
  </head>
  <body>

	  <div class="container">
	  </br>
			<div class="row">
				<center>
				<div id="mapContainer" class="col-md-6">
					<div id="rvizMap">

					</div>
					<h2>2-D Map<h2>
				</div>
				</center>
				<center>
					<div class="col-md-6" id="embedded_scene">
						<img id="detection_img" src="frames/300_frame.jpg" width="500" height="300" style="display:none;"/>
						<a-scene embedded>
						<a-entity position="0 0 3.8">
							<a-camera></a-camera>
						</a-entity>
						<a-sky src="frames/300_frame.jpg" id="360_image_sky" rotation="0 -1 0"></a-sky>

						<a-assets>
							<image id="left_arrow_asset"  src="images/left_arrow.png"> </image>
							<image id="right_arrow_asset"  src="images/right_arrow.png"> </image>
						</a-assets>
						<a-image src="#left_arrow_asset" id="left_arrow_aframe" position="0 2 -4"></a-image>
						<a-image src="#right_arrow_asset" position="0 2 -4" id="right_arrow_aframe" ></a-image>

						</a-scene>
						<h2>360 Environment<h2>

						<center>
							<div style="background-color: rgba(0, 0, 0, 0.7); border-radius: 100%; height: 70px; right: 20px; top: 50%; width: 70px;">
							<svg id="sim_human_fov" width="70px" height="70px" viewBox="0 0 60 60" style="cursor: pointer; pointer-events: initial; transform: rotate(0.16223rad);">
							<g fill="#ffffff" stroke="none" fill-rule="evenodd">
								style="stroke: #006600;"/>
								<path d="M30,60 C46.5685425,60 60,46.5685425 60,30 C60,13.4314575 46.5685425,0 30,0 C13.4314575,0 0,13.4314575 0,30 C0,46.5685425 13.4314575,60 30,60 Z M30,58 C45.463973,58 58,45.463973 58,30 C58,14.536027 45.463973,2 30,2 C14.536027,2 2,14.536027 2,30 C2,45.463973 14.536027,58 30,58 Z"></path><path d="M42.7174074,12.0460131 C39.1265957,9.49790578 34.7382252,8 30,8 C25.2617748,8 20.8734043,9.49790578 17.2825926,12.0460131 L25.3754882,23.4712775 C26.6812379,22.544693 28.277009,22 30,22 C31.722991,22 33.3187621,22.544693 34.6245118,23.4712775 L42.7174074,12.0460131 Z"></path>
								<path d="M26,30 a4,4 0 1,1 8,0 a4,4 0 1,1 -8,0"></path>
							</g>
							</svg>
							</div>
							<h2>Human Field of View<h2>

							<div class="arrow" style="display:none">
								Arrow: <span class='arrow_side' id="arrow_direction_string"></span>

							</div>

						</center>
					</div>
				</center>
			</div>
		</div>



  </body>
</html>
