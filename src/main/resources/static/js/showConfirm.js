/**
 * 
 */

function confirmSubmit(){
	var result = confirm('この情報で登録しますか？');
	if (result){
		document.rgForm.submit();
	}
}