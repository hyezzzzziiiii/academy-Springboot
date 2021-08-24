function go_detail(pseq) {
	var theForm = document.frm;
	theForm.action = "admin_product_detail?pseq="+pseq;
	theForm.submit();
}


function go_mod(pseq) {
	var theForm = document.frm;
	theForm.action = "product_update_form?pseq="+pseq;
	theForm.submit();
}



function go_mod_save(tpage, pseq) {
	 var theForm = document.frm;

	 if (theForm.kind.value == '') {
		  alert('상품분류를 선택하세요');
		  theForm.kind.focus();
	 } else if (theForm.name.value == '') {
		  alert('상품명을 입력하세요');
		  theForm.name.focus();
	 } else if (theForm.price1.value == '') {
		  alert('원가를 입력하세요');
		  theForm.price1.focus();
	 } else if (theForm.price2.value == '') {
		  alert('판매가를 입력하세요');
		  theForm.price2.focus();
	 } else if (theForm.content.value == '') {
		  alert('상품상세를 입력하세요');
		  theForm.content.focus();
	 } else {
			  if (confirm('수정하시겠습니까?')) {
				  if (theForm.useyn.checked == true) {
				   		theForm.useyn.value = "y";
				  }
				  if(theForm.bestyn.checked == true) {
					  	theForm.bestyn.value = "y";
				  }
				   theForm.encoding = "multipart/form-data";				   
				   theForm.action = "product_update";
				   theForm.submit();
			  }
	 }
}

function go_mov()
{
	var theForm = document.frm;
	theForm.action = "product_list";
	theForm.submit();
}

function go_wrt() {
	var theForm = document.frm;
	theForm.action = "product_write_form";
	theForm.submit();
}



function go_save() 
{
	var theForm = document.frm;
	
	if (theForm.kind.value == '') {
		alert('상품분류를 선택하세요.');
		theForm.kind.focus();
	} else if (theForm.name.value == '') {
		alert('상품명을 입력하세요.');
		theForm.name.focus();
	} else if (theForm.price1.value == '') {
		alert('원가를 입력하세요.');
		theForm.price1.focus();
	} else if (theForm.price2.value == '') {
		alert('판매가를 입력하세요.');
		theForm.price2.focus();
	} else if (theForm.content.value == '') {
		alert('상품상세를 입력하세요.');
		theForm.content.focus();
	} else if (theForm.image.value == '') {
		alert('상품이미지들 입력하세요.');
		theForm.image.focus();
	} else {
		theForm.encoding = "multipart/form-data";
		theForm.action = "product_write";
		theForm.submit();
	}
}

function go_search() {
	var theForm = document.frm;
	theForm.all_view.value="n";
	theForm.action =  "product_list";
	theForm.submit();
}


function go_total() {
	var theForm = document.frm;
	theForm.all_view.value="y";
	theForm.key.value="";
	theForm.action =  "NonageServlet?command=admin_product_list";
	theForm.submit();
}


function removeComma(input) // ,을 빼고 값을 다시 넣어준다.
{
	return input.value.replace(/,/gi, "");
}





