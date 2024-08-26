/**
 * 
 */

export function getBkColorByStatus(statusid, isnext, isDbl = false){
	return colorBind(statusid, isnext, false, isDbl);
}

export function getColorByStatus(statusid, isnext, isDbl = false){
	return colorBind(statusid, isnext, true, isDbl);
}

function colorBind(statusid, isnext, isfont, isDbl){
	let add = isnext ? 1 : 0;
	let rlt = Number(statusid) + Number(add);
	
	if (statusid == 2 && isnext && isDbl == 'false') rlt += 2;//test用で実行中→完了へ移動させるため。
	
	switch (rlt){
		case 1:
			if (isfont) return "black";
			return '#CFCFEEFF';//light blue
		case 2:
			if (isfont) return "white";
			return '#6666EEFF';//blue
		case 3:
			if (isfont) return "black";
			return '#FAFAA5FF';//light yellow
		case 4:
			if (isfont) return "black";
			return '#FAFA00FF';//yellow
		case 5:
			if (isfont) return "black";
			return '#DDDDDDFF';//grey
		default:
			console.log('statusId in switch results default')
			return '';
	}
}