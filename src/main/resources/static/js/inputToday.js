/**
 * 
 */
window.addEventListener('DOMContentLoaded', () => {
	let date = new Date();
	let y = date.getFullYear();
	let m = date.getMonth() + 1;
	let d = date.getDate();

	let yyyy = twoDigit(y,4);
	let mm = twoDigit(m,2);
	let dd = twoDigit(d,2);
	
	let today = yyyy + '-' + mm + '-' + dd;

	let ipt = document.getElementById('due');
	ipt.setAttribute('min',today);
	ipt.value = today;
})

function twoDigit(num, digit){
	num += '';
	if (num.length < digit){
		num = '0' + num;
	}
	return num;
}