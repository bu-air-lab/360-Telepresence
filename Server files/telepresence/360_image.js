var sceneEl;
var imageSphereEl;
var frame_number;

var stop_randomization = false;
disable_arrows = true;

function init_360_image(){
	//get canvas and set up call backs
	window.onkeydown = frame_changer;
	sceneEl = document.querySelector('a-scene');
    imageSphereEl = sceneEl.querySelector("[id='360_image_sky']");
	//setInterval(change_frame, 1000);
	img_src_frame_number = imageSphereEl.getAttribute('src');
	img_src_frame_number = img_src_frame_number.replace("frames/","");
	img_src_frame_number = img_src_frame_number.replace("_frame.jpg","");
	img_src_frame_number = parseInt(img_src_frame_number);
	console.log('Image src is :'+img_src_frame_number);
	frame_number = img_src_frame_number;
	setInterval(random_movement_generator, 4000);
	setInterval(printAngle, 1000);
}

function printAngle(){
	console.log(rotation_angle_sim_human_head%360);
}

function random_movement_generator(){
	
	var rand_number = Math.floor((Math.random() * 4) + 1);
	
	if(stop_randomization){
		return;
	}
	switch(rand_number){
		case 1:
			go_forward();
			advance_robot_location();
			changeMoveDirection("none");
			console.log("Moving forward");
			break;
			
		case 2:
			go_back();
			reverse_robot_location();
			changeMoveDirection("none");
			console.log("Moving back");
			break;
			
		case 3:
			//move_head_left();
			console.log("Moving left");
			changeMoveDirection("left");
			break;
			
		case 4:
			//move_head_right();
			console.log("Moving right");
			changeMoveDirection("right");
			break;
	}
}

function frame_changer(e){
	switch (e.keyCode) {
		case 37:
			//move_head_left();
			console.log("Moving left");
			changeMoveDirection("left");
			break;
		
		case 38:
			go_forward();
			advance_robot_location();
			changeMoveDirection("none");
			console.log("Moving forward");
			break;
			
		case 39:
			//move_head_right();
			console.log("Moving right");
			changeMoveDirection("right");
			break;
			
		case 40:
			go_back();
			reverse_robot_location();
			changeMoveDirection("none");
			console.log("Moving back");
			break;
			
		case 84:
			stop_randomization  = true;
			break;
		
		case 85:
			stop_randomization  = false;
			break;
		
		case 86:
			disable_all_arrows();
			break;
	}
}


function change_frame(){
	if (frame_number<345){
		frame_number = frame_number+5;
		var dummy_frame_obj_detection = document.getElementById('detection_img');
		
	}


}

function go_back(){
	if (frame_number>300){
		frame_number = frame_number-5;
		var dummy_frame_obj_detection = document.getElementById('detection_img');
		dummy_frame_obj_detection.setAttribute('src', 'frames/'+frame_number+'_frame.jpg');
		imageSphereEl.setAttribute('src', 'frames/'+frame_number+'_frame.jpg');
		
	}
	
	
}

function go_forward(){
	if (frame_number<345){
		frame_number = frame_number+5;	
		var dummy_frame_obj_detection = document.getElementById('detection_img');
		dummy_frame_obj_detection.setAttribute('src', 'frames/'+frame_number+'_frame.jpg');
		imageSphereEl.setAttribute('src', 'frames/'+frame_number+'_frame.jpg');
	}
	
}