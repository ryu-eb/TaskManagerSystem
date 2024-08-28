/**
 * 
 */

function confirmSubmit(){
	var btn = document.getElementById('stopDbl');
	btn.disabled = true;
	var result = confirm('この情報で登録しますか？');
	if (result){
		document.rgForm.submit();
	}else {
		btn.disabled = false;
	}
}