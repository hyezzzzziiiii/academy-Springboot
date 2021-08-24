function post_zip(){
 var url = "findZipNum";
 var pop = "toolbar=no, menubar=no, scrollbars=no, "
  + "resizable=no, width=550, height=300, top=300, left=300";
 window.open( url, "_blank_1",pop);
}



function find_id(){
 var url = "findIdForm";
 var pop = "toolbar=no, menubar=no, scrollbars=no, "
  + "resizable=no, width=550, height=300, top=300, left=300";
 window.open( url, "_blank_1",pop);
}

function go_next() {
  if (document.formm.okon1[0].checked == true) {
    document.formm.action = "joinForm";
    document.formm.submit();
  } else if (document.formm.okon1[1].checked == true) {
    alert('약관에 동의하셔야만 합니다.');
  }
}




function idcheck(){

 if(document.formm.id.value==""){
  	alert('아이디를 입력하여 주세요');
  	document.formm.id.focus();
  	return;
 }
 var url = "idCheckForm";
 url += "?id=" + document.formm.id.value;
 var pop = "toolbar=no, menubar=no, scrollbars=no, resizable=no, width=500, height=250";
 window.open( url, "_blank_1",pop);
}

