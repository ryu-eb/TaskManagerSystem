/**
 * 
 */
import {getBkColorByStatus, getColorByStatus} from './statusColor.js';

window.addEventListener('DOMContentLoaded', () => {		
		let list = document.getElementsByClassName('cmHeader');
		
		for (let i = 0; i < list.length; i++){
			//let statusId = list[i].childNodes[4].childNodes[1].value;
			let statusid = list[i].children.namedItem('tkForm').children.namedItem('tkStatus').value;
			let title = list[i].children.namedItem('tkTitle');
			
			list[i].style.backgroundColor = getBkColorByStatus(statusid,false);
			title.style.color = getColorByStatus(statusid, false);
		}
});