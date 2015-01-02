var canvas = document.getElementById("canvas");
var ctx = canvas.getContext("2d");

function drawImage(ctx, image, rWidth, xWidth, yHeight) {
	var color0 = "#FFFFFF";
	var color1 = "#0000FF";
	var widthPersistent =xWidth;
	var i;
	var j;
	for (i = 0; i < image.length; i++) {
		for (j = 0; j < image[i].length; j++) {
			if (image[i][j] == 1) {
				ctx.fillStyle = color1;
			} else {
				ctx.fillStyle = color0;
			}
			ctx.fillRect(xWidth, yHeight, rWidth, rWidth);
			xWidth += rWidth;
		}
		xWidth = widthPersistent;
		yHeight += rWidth;
	}
}

i = [[0,1,0],[1,0,1],[0,1,0]];

drawImage(ctx, i, 10, 0, 0);