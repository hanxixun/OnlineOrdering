function change4(){
	var checkeds = $("input");
	var s = 0;
	for(var i = 1; i < checkeds.length; i++){
		var input = checkeds[i]
		if(input.getAttribute('class') == 'checked dian'){
			continue;
		}else if(input.checked){
			var set = input.parentNode.parentNode.childNodes[3].childNodes[3].childNodes[1].childNodes;
			for(var j = 1; j < set.length; j = j + 2){
				if(set[j].getAttribute('class') == 'col-xs-9'){
					s = s + parseFloat(set[j].childNodes[1].innerText.split(' ')[0]);
				}
			}
		}
	}
	$('nav font')[3].innerHTML = s;
}
setInterval(change4,200);



