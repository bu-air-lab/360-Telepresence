<?php
?>
<html>
  <head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script src="https://aframe.io/releases/1.0.4/aframe.min.js"></script>
  </head>
  <body>
	<div class="container">
		<div class="row">
			<div class="col-sm-4">
			  <a-scene>
				<a-entity scale="1 1 1">
					<a-sky src="frames/0_frame.jpg" rotation="0 -130 0"></a-sky>

						<a-text font="kelsonsans" value="Puy de Sancy, France" width="6" position="-2.5 0.25 -1.5"
					  rotation="0 15 0"></a-text>
				<a-entity>
			</a-scene>
			</div>
			<div class="col-sm-4">
			  <h3>Column 2</h3>
			  <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit...</p>
			  <p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris...</p>
			</div>
			<div class="col-sm-4">
			  <h3>Column 3</h3>        
			  <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit...</p>
			  <p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris...</p>
			</div>
		</div>
	</div>
    
	
  </body>
</html>