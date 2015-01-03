var canvas = document.getElementById("canvas");
var ctx = canvas.getContext("2d");

function drawImage(ctx, image, rWidth, xWidth, yHeight) {
	var color0 = "#00FF00";
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

function genFractal(prevImage) {
	var image = prevImage.slice(0);
	var i;
	var j;
	for (i = prevImage.length - 1; i >= 0; i--) {
		for (j = prevImage[i].length - 1; j >= 0; j--) {
			image[i].splice(j, 0, prevImage[i][j]);
		}
	}
	return image;
}

i = [[1]];
drawImage(ctx, genFractal(i), 10, 0, 0);