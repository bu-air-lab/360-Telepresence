function init_360_vid(){
	//get canvas and set up call backs
	pano_canvas = document.getElementById('canvas');
	pano_canvas.onmousedown = mouseDown;
	window.onmousemove = mouseMove;
	window.onmouseup = mouseUp;
	window.onmousewheel = mouseScroll;
	window.onkeydown = keyDown;
	draw();
	//setInterval(draw, 1000/FPS);
}
     