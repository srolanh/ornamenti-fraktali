var canvas = document.getElementById("canvas");
var ctx = canvas.getContext("2d");

// zīmē doto rakstu uz ekrāna
function drawImage(ctx, image, rWidth, xWidth, yHeight) {
	// ctx - konteksts, vienmēr paliek tas pats
	// image - zīmējamais raksts
	// rWidth - elementa izmērs, parasti 10
	// xWidth - sākumpunkta x koordināta, parasti 0
	// yHeight - sākumpunkta y koordināta, parasti 0
	canvas.height = image.length * rWidth; 
	canvas.width = image[0].length * rWidth; // ekrāna bildes garums,platums = raksta garums,platums
	var color0 = "#FFFFFF"; // krāsa atbilst 0
	var color1 = "#0000FF"; // krāsa atbilst 1
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

// ģenerē tīklu
function genNet(size, inverse) {
	var color = inverse ? 0 : 1; // norāda, vai tīklā jāliek 1 vai 0
	var net;
	net = [[1],[0]]; // tīkla sākums
	if (!inverse) {
		net = [[0],[1]]; // inversā tīkla sākums
	}
	while (net[0].length < size) { // pirmā rinda
		if (color) {
			net[0].push(1,1); // liek tīklā 1
			color = 0; // pārslēdzas uz otru krāsu
		} else {
			net[0].push(0,0); // liek tīklā 0
			color = 1; // pārslēdzas uz otru krāsu
		}
	}
	color = inverse ? 1 : 0; // otrās rindas sākums
	while (net[1].length < size) {
		if (color) {
			net[1].push(1,1);
			color = 0;
		} else {
			net[1].push(0,0);
			color = 1;
		}
	}
	if (net[0].length > size) { // ja tīkls par 1 lielāks nekā vajag
		net[0].pop(); // noņemt elementu no pirmās rindas beigām
		net[1].pop(); // noņemt elementu no otrās rindas beigām
	}
	//console.log(net);
	return net;
}

// ģenerē ornamentu
function genFractal(prevImage, inverse, level) {
	debugger;
	console.log(prevImage);
	var image = prevImage.slice(0); // izveido rakstāmu kopiju
	var middle = Math.floor(prevImage.length / 2); // norāda vidu, kas atkārtojams divreiz
	var rowLength = Math.pow(2, level+2); // norāda vajadzīgo rindas garumu šim līmenim 
	console.log(image);
	var i;
	var j;
	for (i = prevImage.length - 1; i >= 0; i--) {
		if (image[i].length == rowLength) {
			continue;
		}
		for (j = prevImage[i].length - 1; j >= 0; j--) { // cikls pār katras rindas image[i] kartru elementu image[i][j]
			image[i].splice(j, 0, prevImage[i][j]); // atkārto katru elementu divreiz
		}
	}
	if (level > 1) {
		image.splice(middle+1, 0, image[middle]); // atkārto vidu divreiz
	}
	//console.log(image);
	var net = genNet(image[1].length, inverse); // ģenerē tīklu onamentam
	var netIndex = 0; // norāda, kura tīkla rinda jāievieto
	for (i = image.length - 1; i >= 0; i--) {
		image.splice(i, 0, net[netIndex]); // ievieto vajadzīgo tīkla rindu
		netIndex = netIndex ? 0 : 1; // nākamā rinda
	}
	image.push(net[Number(!netIndex)]);
	//console.log(image);
	return image;
}

i = [[0,1,1,0],[0,1,1,0]]; // pirmā ievade
//drawImage(ctx, i, 10, 0, 0)
drawImage(ctx, genFractal(genFractal(genFractal(i,false,1),false,2),false,3), 10, 0, 0);
//drawImage(ctx, genFractal(i,true,1), 10, 0, 0);
//drawImage(ctx, genFractal(i,false,1), 10, 0, 0);
//drawImage(ctx, genFractal(genFractal(i,false,1),false,2), 10, 0, 0);
//drawImage(ctx, genFractal(genFractal(i,false,1),true,2), 10, 0, 0);
//drawImage(ctx, genFractal(genFractal(i,true,1),false,2), 10, 0, 0);
//drawImage(ctx, genFractal(genFractal(i,true,1),true,2), 10, 0, 0);
