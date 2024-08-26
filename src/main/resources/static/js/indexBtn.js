/**
 * 
 */
import {getBkColorByStatus, getColorByStatus} from './statusColor.js';

window.addEventListener('DOMContentLoaded', () => {
	var header = document.getElementsByClassName('cmHeader');
	for (let i = 0; i < header.length; i++){
		
		console.log('indexBtn : index = ' + i);

		let form = header[i].children.namedItem('tkForm');
		let btn = form.children.namedItem('tkBtn');
		let statusId = form.children.namedItem('tkStatus').value;//int
		let dblCk = form.children.namedItem('tkDblcheck').value;//boolean
		let btntext = btn.textContent;

		console.log('vars[statusId=' + statusId + ', dblcheck=' + dblCk + ', btntext=' + btntext + ']');
		
		//deleteのときはbtnのtypeがsubmitで、その場合はtoggleなし
		if (btn.getAttribute('type')!= 'submit') {
			btn.addEventListener('mouseover', () => {
				btn.textContent = getBtnText(statusId, dblCk) + "へ変更";
				btn.style.color = getColorByStatus(statusId, true, dblCk);
				btn.style.background = getBkColorByStatus(statusId, true, dblCk);
			});
			btn.addEventListener('mouseout', () => {
				btn.textContent = btntext;
				btn.style.color = 'black';
				btn.style.background = "white";
			});	
		}
	}
})

//DBのカラムの内容変更したらここも変える必要あり
function getBtnText(statusId, dblCk){
	switch(statusId){
		case '1':
			return '実行中';
		case '2':
			if (dblCk == 'true') return '精査前'
			else return '完了';
		case '3':
			return '精査中';
		case '4':
			return '完了へ';
		default:
			return '';
	}
}

window.addEventListener('DOMContentLoaded',() => {
	var header = document.getElementsByClassName('cmHeader');
		for (let i = 0; i < header.length; i++){
			let form = header[i].children.namedItem('tkForm');
			let btn = form.children.namedItem('tkBtn');
			let statusId = form.children.namedItem('tkStatus').value;//int
			let dblCk = form.children.namedItem('tkDblcheck').value;//boolean
			
			btn.addEventListener('click', function() {
				let result = confirm(getBtnText(statusId, dblCk) + 'へ変更します');
				if (result){
					form.submit();
				}
			});
		}
})