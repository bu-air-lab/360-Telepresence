var baseline;
var trial_complete;
var disable_arrows;
var stop_randomization;
var trialStart;
var movements = 0;
var trialNum;
var trialType;

var trialStarts = [
  325, 43, //1
  325, 212, //2
  330, 273, //3
  335, 221, //4
  305, 78, //5
  325, 141, //6
  300, 257, //7
  330, 29, //8
  335, 265, //9
  315, 194 //10
];

function test_simulation(trial){
  trialNum = trial;
  var startFrameNum = trialStarts[(Math.floor(trial * (1/2))) * 2];//(((Math.floor(Math.random() * 9)) * 5) + 300);
  var startRotationDeg = trialStarts[((Math.floor(trial * (1/2))) * 2) + 1];//Math.floor(Math.random() * 360);
  var frame = "frames/" + startFrameNum + "_frame.jpg";
  trial_complete = false;
  var start_img = document.getElementById("360_image_sky")
  start_img.setAttribute('src', frame);
  //start_img.setAttribute('rotation', {x: 0, y: startRotationDeg, z: 0});

	var ImgDet_arrow_left = document.getElementById("left_arrow_aframe");
	ImgDet_arrow_left.setAttribute("visible",false);
  var ImgDet_arrow_right = document.getElementById("right_arrow_aframe");
	ImgDet_arrow_right.setAttribute("visible",false);

  if((trial % 2) === 0){
    trialType = "Baseline";
    baseline = true;
    disable_arrows = true;
  }else{
    trialType = "Simulated Human"
    baseline = false;
    disable_arrows = false;
  }
  alert("Started " + trialType + " Trial at frame " + startFrameNum + "with head oriented at " + startRotationDeg + "degrees.");
  trialStart = new Date();
  return;
}
