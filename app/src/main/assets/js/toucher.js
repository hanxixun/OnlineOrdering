	var checkeds = $("input");
	for(var i = 0; i < checkeds.length; i++){
		checkeds[i].checked = false;
	}
	var a = [];
    for(var i = 0; i < checkeds.length; i++){
		if(checkeds[i].getAttribute('class') == 'checked dian'){
			a.push(i);
		}
    }
	a.push(checkeds.length);
function change1(item){
	var sum3 = 0;
	for(var i = 0; i < a.length; i++){
		if(item == checkeds[a[i]] && checkeds[a[i]].checked == true){
			for(var j = a[i]; j < a[i + 1]; j++){
				checkeds[j].checked = true;
			}
		}
		if(item == checkeds[a[i]] && checkeds[a[i]].checked == false){
			checkeds[0].checked = false;
			for(var j = a[i]; j < a[i + 1]; j++){
				checkeds[j].checked = false;
			}
		}
		for(var j = a[i]; j < a[i + 1]; j++){
			if(checkeds[j].checked == true){
				sum3 = sum3 + 1;
			}
		}
        if(sum3 == (checkeds.length - 1)){
				checkeds[0].checked = true;
		}		
    }
}
function change2(item){
	    for(var i = 0; i < checkeds.length; i++){
		if(item.checked){
			checkeds[i].checked = true;
		}
		if(item.checked == false){
			checkeds[i].checked = false;
		}
    }
}
function change3(item){
	var sum = 0;
	for(var i = 0; i < (a.length - 1); i++){
		var sum2 = 0;
		for(var j = a[i]; j < a[i + 1]; j++){
			
            if(checkeds[j].checked == false && item == checkeds[j]){
				checkeds[a[i]].checked = false;
				checkeds[0].checked = false;
		    }
		}
	    for(var j = a[i]; j < a[i + 1]; j++){
			if(checkeds[j].checked == true){
				sum = sum + 1;
				sum2 = sum2 + 1;
			}
			if(sum2 == (a[i + 1] - a[i] - 1)){
				checkeds[a[i]].checked = true;
			}
		}
}
	        if(sum == (checkeds.length - 2)){
				checkeds[0].checked = true;
			}
	
}
function send(){
	var s = parseFloat(document.getElementById('price').innerHTML);
	if(s != 0){
		window.AndroidWebView.showInfoFromJs(s);
	}
}
function showInfoFromJava(msg){
	alert('信息'+msg);
}





