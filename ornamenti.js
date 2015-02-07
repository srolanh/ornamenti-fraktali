var canvas = document.getElementById("canvas");
var ctx = canvas.getContext("2d");

function drawImage(ctx, image, rWidth, xWidth, yHeight) {
	var color0 = "#FFFFFF";
	var color1 = "#0000FF";
	var widthPersistent = xWidth;
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

function genNet(size, inverse) {
	var black = inverse;
	var net = [[1],[0]];
	if (inverse) {
		net = [[0],[1]];
	}
	while (net[0].length < size) {
		if (black) {
			net[0].push(1,1);
			black = false;
		} else {
			net[0].push(0,0);
			black = true;
		}
	}
	black = !inverse;
	while (net[1].length < size) {
		if (black) {
			net[1].push(1,1);
			black = false;
		} else {
			net[1].push(0,0);
			black = true;
		}
	}
	if (net[0].length > size) {
		net[0].pop();
		net[1].pop();
	}
	return net;
}

function buildSymmetry(image) {
	for (i = image.length - 1; i >= 0; i--) {
		image.push(image[i]);
	}
	image.splice(image.length / 2, 1);
	return image;
}

function genFractal(prevImage, inverse, addCells = false) {
	var image = prevImage.slice(0);
	var i;
	var j;
	for (i = prevImage.length - 1; i >= 0; i--) {
		for (j = prevImage[i].length - 1; j >= 0; j--) {
			image[i].splice(j, 0, prevImage[i][j]);
		}
	}
	if (addCells) {
		for (i = 0; i < image.length; i++) {
			image[i].unshift(0, 0);
		}
		for (i = image.length - 1; i >= 0; i--) {
			image[i].push(0, 0);
		}
	}
	var net = genNet(image[0].length, inverse);
	var netIndex = 0;
	for (i = image.length - 1; i >= 0; i--) {
		image.splice(i, 0, net[netIndex]);
		netIndex = netIndex == 0 ? 1 : 0;
	}
	if (netIndex == 0) {
		image.splice(image.length, 0, net[0]);
	} else {
		image.splice(image.length, 0, net[1]);
	}
	return image;
}

i = [[1,1]];
drawImage(ctx, buildSymmetry(genFractal(genFractal(i, false, true), true)), 10, 0, 0);
