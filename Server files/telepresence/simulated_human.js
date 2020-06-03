var sim_human_current_arrow;
var sceneEl;
var Sim_human_Sim_human_imageSphereEl;
var frame_number;
var start_time_movement;
var move_direction = "none";
var rotation_angle_sim_human_head = 0; // CHANGED FROM 0 TO DETECT RANDOM HEAD ROTATION IN INIT: NOT UPDATING ??

//WOULD SET ROTATION TO CURRENT ROTATION AT START OF TIRAL
//var rotation_angle_sim_human_head;


//VARIABLE CHANGED//
//var baseline = false;
////////////////////

var probabilisitic_movement = false;
var probability = 0.8;



function init_human_simulation(rotation_angleIn){
	console.log("Human simulation called :"+rotation_angleIn);
	//get canvas and set up call backs
	sceneEl = document.querySelector('a-scene');
    Sim_human_imageSphereEl = sceneEl.querySelector("[id='360_image_sky']");

	//THIS WOULD CHANGE IT SO THE STARTING POSITION OF THE HEAD IS SET AT THE START OF THE TRIAL INSTEAD OF ASSUMED ZERO
	rotation_angle_sim_human_head = rotation_angleIn;



	setInterval(head_movement_timed, 50);

	if(baseline == true){
		probability = 0.0;
	}

	set_sim_human_fov_marker();

	// var videoSphere = sceneEl.querySelector('#360_videosphere');
	// videoSphere.setAttribute('rotation', {x: 0, y: 10, z: 0});
}

function move_head(){
	left_arrow = document.getElementById("left_arrow");
	console.log("Left human arrow properties:"+left_arrow);
	left_arrow_side = left_arrow.style.visibility;
	right_arrow = document.getElementById("right_arrow");
	right_arrow_side = right_arrow.style.visibility;
	if(left_arrow_side == "hidden"){
		sim_human_current_arrow = "right_arrow";
		start_time_movement = new Date();
		move_direction = "right";
		return;
	}
	else{
		sim_human_current_arrow = "left_arrow";
		start_time_movement = new Date();
		move_direction = "left";
		return;
	}
	if (left_arrow_side == "visible" && right_arrow_side == "visible"){
		sim_human_current_arrow = "none";
	}
}

function changeMoveDirection(directionIn){
	start_time_movement = new Date();
	move_direction = directionIn;
}

function head_movement_timed(){
	sceneEl = document.querySelector('a-scene');
	Sim_human_imageSphereEl = sceneEl.querySelector("[id='360_image_sky']");


	if (baseline == false){
		let SceneSimHuman = document.querySelector('a-scene');
		let left_arrow_Sim = SceneSimHuman.querySelector("[id='left_arrow_aframe']");
		let current_state_left_arrow = left_arrow_Sim.getAttribute("visible");
		let right_arrow_Sim = SceneSimHuman.querySelector("[id='right_arrow_aframe']");
		let current_state_right_arrow = right_arrow_Sim.getAttribute("visible");
		if (current_state_left_arrow && probabilisitic_movement == false){
			if(Math.random() < probability){
				console.log("Reversing movement");
				move_direction = "left";

			}
		}
		else if (current_state_right_arrow && probabilisitic_movement ==  false){
			if(Math.random() < probability){
				console.log("Reversing movement right to left");
				move_direction = "right";

			}
		}
		probabilisitic_movement = true;
	}


	if(move_direction == "none"){
		probabilisitic_movement = false;
		return;
	}
	else if(move_direction == "left"){
		var current_time_movement = new Date();
		var difference = (current_time_movement - start_time_movement) / 1000;
		Sim_human_imageSphereEl = sceneEl.querySelector("[id='360_image_sky']");
		var current_rotation = Sim_human_imageSphereEl.getAttribute('rotation').y;
		Sim_human_imageSphereEl.setAttribute('rotation', {x: 0, y: current_rotation - 1, z: 0});
		rotation_angle_sim_human_head = rotation_angle_sim_human_head - 1;
		set_sim_human_fov_marker();
		if(difference > 2){
			move_direction = "none";
			probabilisitic_movement = false;
		}
	}
	else if (move_direction == "right"){
		var current_time_movement = new Date();
		var difference = (current_time_movement - start_time_movement) / 1000;
		Sim_human_imageSphereEl = sceneEl.querySelector("[id='360_image_sky']");
		var current_rotation = Sim_human_imageSphereEl.getAttribute('rotation').y;
		Sim_human_imageSphereEl.setAttribute('rotation', {x: 0, y: current_rotation + 1, z: 0});
		rotation_angle_sim_human_head = rotation_angle_sim_human_head + 1;
		set_sim_human_fov_marker();
		if(difference > 2){
			move_direction = "none";
			probabilisitic_movement = false;
		}
	}
}

function move_head_left(){
	start_time_movement = new Date();
	move_direction = "left";
	return;

}

function move_head_right(){
	var current_rotation = Sim_human_imageSphereEl.getAttribute('rotation').y;
	Sim_human_imageSphereEl.setAttribute('rotation', {x: 0, y: current_rotation + 10, z: 0});
	return;
}

function degrees_to_radians(degrees){
  var pi = Math.PI;
  return degrees * (pi/180);
}

function set_sim_human_fov_marker(){
	var current_fov_rotation = document.getElementById("sim_human_fov");
	var human_head_angle_radians;
	if(frame_number > 310){
		human_head_angle_radians = degrees_to_radians((rotation_angle_sim_human_head + 270)%360);
	}else if(frame_number === 310){
		human_head_angle_radians = degrees_to_radians((rotation_angle_sim_human_head + 235)%360);
	}else{
		human_head_angle_radians = degrees_to_radians((rotation_angle_sim_human_head + 180)%360);
	}
	console.log("Human head angle is "+(rotation_angle_sim_human_head+1440)%360);
	document.getElementById("sim_human_fov").style.transform = "rotate("+human_head_angle_radians+"rad)";
}
