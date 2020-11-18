var baseline;
var trial_complete;
var disable_arrows;
var stop_randomization;
var trialStart;
var movements = 0;
var trialNum;
var trialType;
var startRotationDeg;

var trialObj = new Object();
/*
var trialStarts = [
  320, 43, //1
  320, 212, //2
  335, 273, //3
  335, 221, //4
  320, 78, //5
  320, 141, //6
  320, 257, //7
  335, 29, //8
  335, 265, //9
  320, 194 //10
];
*/

var possibleStarts = [10];
// var possibleStarts = [300, 310, 345, 355];

function test_simulation(){
  var trial = 1; //Math.floor(Math.random() * 2);
  var startFrameNum = possibleStarts[Math.floor(Math.random() * possibleStarts.length)];//(Math.round(Math.random()) * 15) + 320;//trialStarts[(Math.floor(trial * (1/2))) * 2];//(((Math.floor(Math.random() * 9)) * 5) + 300);
  startRotationDeg = Math.floor(Math.random() * 360); //trialStarts[((Math.floor(trial * (1/2))) * 2) + 1];
  var frame = "frames/" + startFrameNum + "_frame.jpg";
  trial_complete = false;
  var start_img = document.getElementById("360_image_sky")
  start_img.setAttribute('src', frame);
  start_img.setAttribute('rotation', {x: 0, y: 0, z: 0});

	var ImgDet_arrow_left = document.getElementById("left_arrow_aframe");
	ImgDet_arrow_left.setAttribute("visible",false);
  var ImgDet_arrow_right = document.getElementById("right_arrow_aframe");
	ImgDet_arrow_right.setAttribute("visible",false);

  if((trial % 2) === 0){
    trialType = "Baseline";
    baseline = true;
    //disable_arrows = true;
  }else{
    trialType = "Simulated Human"
    baseline = false;
    //disable_arrows = false;
  }
  
  console.log("Trial-Type: " + trialType);
  //alert("Started " + trialType + " Trial at frame " + startFrameNum + "with head oriented at " + startRotationDeg + "degrees.");
  trialObj.Type = trialType;
  trialObj.StartFrame = startFrameNum;
  trialObj.StartRotation = startRotationDeg;
  if(startFrameNum === 300 || startFrameNum === 355){
    trialObj.Radius = 10;
  }else{
    trialObj.Radius = 6;
  }
  init_human_simulation(0);
  trialStart = new Date();
  return;
}
