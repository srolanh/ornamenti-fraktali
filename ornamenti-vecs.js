var canvas = document.getElementById("canvas");
var ctx = canvas.getContext("2d");

function drawImage(ctx, image, rWidth, xWidth, yHeight) {
	var color0 = "#00FF00";
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
	var net;
	net = [[1],[0]];
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
	//console.log(net);
	return net;
}

function buildSymmetry(image) {
	var i;
	for (i = image.length - 1; i >= 0; i--) {
		image.push(image[i]);
	}
	image.splice(image.length / 2, 1);
	return image;
}

function genFractal(prevImage, inverse, req) {
	var image = prevImage.slice(0);
	console.log(image);
	var i;
	var j;
	for (i = prevImage.length - 1; i >= 0; i--) {
		for (j = prevImage[i].length - 1; j >= 0; j--) {
			image[i].splice(j, 0, prevImage[i][j]);
		}
	}
	//if (req) {
	var net = genNet(image[0].length, inverse);
	var netIndex = 0;
	for (i = image.length - 1; i >= 0; i--) {
		image.splice(i, 0, net[netIndex]);
		//console.log(i);
		if (netIndex == 0) {
			netIndex = 1;
		} else {
			netIndex = 0;
		}
		//console.log(netIndex);
	}
	//debugger;
	if (netIndex == 0) {
		image.splice(image.length, 0, net[0]);
	} else {
		image.splice(image.length, 0, net[1]);
	}
	//}
	//console.log(image);
	return image;
}

i = [[0,1,1,0]];
drawImage(ctx, buildSymmetry(genFractal(genFractal(genFractal(i,false),false),false)), 10, 0, 0);
//drawImage(ctx, buildSymmetry(genFractal(i,false)), 10, 0, 0);
//drawImage(ctx, buildSymmetry(genFractal(i,true)), 10, 0, 0);
//drawImage(ctx, buildSymmetry(genFractal(genFractal(i,false),false)), 10, 0, 0);
//drawImage(ctx, buildSymmetry(genFractal(genFractal(i,false),true)), 10, 0, 0);
//drawImage(ctx, buildSymmetry(genFractal(genFractal(i,true),false)), 10, 0, 0);
//drawImage(ctx, buildSymmetry(genFractal(genFractal(i,true),true)), 10, 0, 0);
