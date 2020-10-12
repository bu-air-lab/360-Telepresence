var sceneEl;
var imageSphereEl;
var frame_number;

//VARIABLE CHANGED//
//var stop_randomization = false;
////////////////////

var movementInterval;
var angleInterval;

disable_arrows = true;

function init_360_image(){
	//get canvas and set up call backs
	window.onkeydown = frame_changer;
	window.onkeyup = visual_reset
	document.getElementById("FOV_Slider").oninput = function() {
		var sV = document.getElementById("FOV_Slider").value;
		var cam = document.getElementById("camera");
		cam.setAttribute("fov",sV.toString());
		document.getElementById("FOV_display").innerHTML = "Use the scroll bar to adjust FOV: " + sV.toString();
		//console.log("FOV should be " + sV);
	};
	sceneEl = document.querySelector('a-scene');
    imageSphereEl = sceneEl.querySelector("[id='360_image_sky']");
	//setInterval(change_frame, 1000);
	img_src_frame_number = imageSphereEl.getAttribute('src');
	img_src_frame_number = img_src_frame_number.replace("frames/","");
	img_src_frame_number = img_src_frame_number.replace("_frame.jpg","");
	img_src_frame_number = parseInt(img_src_frame_number);
	console.log('Image src is :'+img_src_frame_number);
	frame_number = img_src_frame_number;
	movementInterval = setInterval(random_movement_generator, 4000);
	angleInterval = setInterval(printAngle, 1000);
	updateState = setInterval(updateCurrentState, 1000);
}

function updateCurrentState()
{
	jQuery.ajax({
                type: "POST",
                url: 'updateState.php',
                dataType: 'json',
                data: {functionname: 'updateCurrentState', arguments: [frame_number]},

                success: function (obj, textstatus) {
                              if( !('error' in obj) ) {
                                    console.log("Collab value of the information is :"+frame_number+":"+obj.result);
                                    // document.getElementById('collabButton').style.display = 'none';
                                    // document.getElementById('noCollabButton').style.display = 'none';

                              }
                              else {
                                  console.log("Collab Error is:"+obj.error);
                              }
                        }
                });
}

function printAngle(){
	console.log(rotation_angle_sim_human_head%360);
}

function random_movement_generator(){
	if(trial_complete === true){
		clearInterval(movementInterval);
		clearInterval(angleInterval);
	}
	console.log("Code reaches here");
	var rand_number;
	if(baseline == false){
		if(frame_number == 325){
			var action_space = [1,3,4];
			console.log("Restricting backward movement");
			rand_number = Math.floor(Math.random() * 3);
			rand_number = action_space[rand_number];
		}
		else if(frame_number == 330){
			var action_space = [2,3,4];
			console.log("Restricting forward movement");
			rand_number = Math.floor(Math.random() * 3);
			rand_number = action_space[rand_number];
		}
		else
		{
			rand_number = Math.floor((Math.random() * 4) + 1);
		}
	}
	else{
		rand_number = Math.floor((Math.random() * 4) + 1);
	}
	console.log("Code reaches here"+rand_number);
	if(stop_randomization){
		return;
	}
	switch(rand_number){
		case 1:
			document.getElementById("ArrowLeftBox").style.fill = "#efefee";
			document.getElementById("ArrowUpBox").style.fill = "#efefee";
			document.getElementById("ArrowRightBox").style.fill = "#efefee";
			document.getElementById("ArrowDownBox").style.fill = "#efefee";
			go_forward();
			advance_robot_location();
			changeMoveDirection("none");
			console.log("Moving forward");
			document.getElementById("ArrowUpBox").style.fill = "#404040"
			setTimeout(function(){ document.getElementById("ArrowUpBox").style.fill = "#efefee"; }, 500);
			break;

		case 2:
			document.getElementById("ArrowLeftBox").style.fill = "#efefee";
			document.getElementById("ArrowUpBox").style.fill = "#efefee";
			document.getElementById("ArrowRightBox").style.fill = "#efefee";
			document.getElementById("ArrowDownBox").style.fill = "#efefee";
			go_back();
			reverse_robot_location();
			changeMoveDirection("none");
			console.log("Moving back");
			document.getElementById("ArrowDownBox").style.fill = "#404040"
			setTimeout(function(){ document.getElementById("ArrowUpBox").style.fill = "#efefee"; }, 500);
			break;

		case 3:
			document.getElementById("ArrowLeftBox").style.fill = "#efefee";
			document.getElementById("ArrowUpBox").style.fill = "#efefee";
			document.getElementById("ArrowRightBox").style.fill = "#efefee";
			document.getElementById("ArrowDownBox").style.fill = "#efefee";
			//move_head_left();
			console.log("Moving left");
			document.getElementById("ArrowLeftBox").style.fill = "#404040"
			changeMoveDirection("left");
			break;

		case 4:
			document.getElementById("ArrowLeftBox").style.fill = "#efefee";
			document.getElementById("ArrowUpBox").style.fill = "#efefee";
			document.getElementById("ArrowRightBox").style.fill = "#efefee";
			document.getElementById("ArrowDownBox").style.fill = "#efefee";
			//move_head_right();
			console.log("Moving right");
			document.getElementById("ArrowRightBox").style.fill = "#404040"
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
			document.getElementById("ArrowLeftBox").style.fill = "#404040"
			break;

		case 38:
			go_forward();
			// advance_robot_location();
			changeMoveDirection("none");
			console.log("Moving forward");
			document.getElementById("ArrowUpBox").style.fill = "#404040"
			break;

		case 39:
			//move_head_right();
			console.log("Moving right");
			changeMoveDirection("right");
			document.getElementById("ArrowRightBox").style.fill = "#404040"
			break;

		case 40:
			go_back();
			// reverse_robot_location();
			changeMoveDirection("none");
			console.log("Moving back");
			document.getElementById("ArrowDownBox").style.fill = "#404040"
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

function visual_reset(e){
	switch (e.keyCode) {
		case 37:
			document.getElementById("ArrowLeftBox").style.fill = "#efefee";
			break;

		case 38:
			document.getElementById("ArrowUpBox").style.fill = "#efefee";
			break;

		case 39:
			document.getElementById("ArrowRightBox").style.fill = "#efefee";
			break;

		case 40:
			document.getElementById("ArrowDownBox").style.fill = "#efefee";
			break;
	}
}

function change_frame(){
	if (frame_number<345){
		frame_number = frame_number+5;
		var dummy_frame_obj_detection = document.getElementById('detection_img');
	}

/*
function sliderMove(){
	var sV = document.getElementById("FOV_Slider").value;
	console.log("FOV should be " + sV);
	document.getElementById("camera").fov = "" + sV;
}
*/

}

function go_back(){
	console.log("Current frame number of going back is: "+frame_number)
	if (frame_number>0){
		frame_number = frame_number-5;
		var dummy_frame_obj_detection = document.getElementById('detection_img');
		dummy_frame_obj_detection.setAttribute('src', 'frames/'+frame_number+'_frame.jpg');
		imageSphereEl.setAttribute('src', 'frames/'+frame_number+'_frame.jpg');
		set_sim_human_fov_marker()
		movements++;
	}


}

function go_forward(){
	if (frame_number<345){
		frame_number = frame_number+5;
		var dummy_frame_obj_detection = document.getElementById('detection_img');
		dummy_frame_obj_detection.setAttribute('src', 'frames/'+frame_number+'_frame.jpg');
		imageSphereEl.setAttribute('src', 'frames/'+frame_number+'_frame.jpg');
		set_sim_human_fov_marker()
		movements++;
	}

}
