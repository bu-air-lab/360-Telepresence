var current_arrow = "left";

function init_arrow_simulation(){
	//get canvas and set up call backs
	//document.querySelector('.arrow .arrow_side').innerHTML = current_arrow;
	setInterval(change_arrows, 5000);

}

function enable(arrow_side){
	document.getElementById(arrow_side).style.visibility = "visible";
	current_arrow = arrow_side;
}
function disable(arrow_side){
	document.getElementById(arrow_side).style.visibility = "hidden";
}


function change_arrows(){
	document.querySelector('.arrow .arrow_side').innerHTML = current_arrow;
	if(current_arrow == "left"){
		enable("left_arrow");
		disable("right_arrow");

	}
	else{
		enable("right_arrow");
		disable("left_arrow");
	}
}
