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

function genNet(size) {
	if (size % 2 == 1) {
		size += 1;
	}
	var black = false;
	var net = new Array;
	net = [[1],[0]];
	while (net[0].length < size) {
		if (black) {
			net[0].push(1,1);
			black = false;
		} else {
			net[0].push(0,0);
			black = true;
		}
	}
	black = true;
	while (net[1].length < size) {
		if (black) {
			net[1].push(1,1);
			black = false;
		} else {
			net[1].push(0,0);
			black = true;
		}
	}
	return net;
}

function genInverseNet(net) {
	var i;
	var j;
	for (i = 0; i < net.length; i++) {
		for (j = 0; j < net[i].length; j++) {
			if (net[i][j] == 1) {
				net[i][j] = 0;
			} else {
				net[i][j] = 1;
			}
		}
	}
	return net;
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