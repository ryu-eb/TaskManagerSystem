/**
 * 
 */

function disableBtn(){
	console.log('func is called');
	
	var btn = document.getElementById('disableBtn');
	
	if (btn.tagName !== 'BUTTON' || btn.type !== 'submit') return;
	setTimeout(() => btn.disabled = true);
}