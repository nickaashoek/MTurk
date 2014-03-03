//(function() {
var data = {
	visibleFor: 1000 + Math.random() * 4000,
	numberBlue: Math.floor(1 + Math.random() * 99),
	numberOrange: Math.floor(1 + Math.random() * 99),

}
$(".next").click(function() {
	var that = $(this);
	that.parents("section").fadeOut(300, function() {
		var attr = that.attr("href");
		if (attr == "#main") {
			//User clicked on [Show the marbles -->]
			//Build the marbles
			for (var i = 0; i < data.numberBlue+data.numberOrange; i++) {
				//Build a marble
				var marble = $("<span class=\"marble-"+(i<data.numberBlue?"blue":"orange")+"\"></span>");
				//-180 = - 40 margin - 100 marble - 40 margin 
				marble.css({
					"left": Math.random() * (window.innerWidth - 180),
					"top": Math.random() * (window.innerHeight - 180),
					"position": "absolute"
				});
				$("#main").append(marble)
			}
			//Show the marbles for `data.visibleFor` milliseconds
			$("#main").fadeIn(0).delay(data.visibleFor).fadeOut(0, function() {
				//Show the observations panel
				$("#observations").fadeIn(300);
			});
		} else if(attr == "#results") {
			//Submit
			//Use this as a callback.
			var seen = document.getElementById("numberseen").value
			var uncertainty = parseInt(document.getElementById("certaintyselect").value)
			$("body").find("#results").fadeIn(300);
			document.getElementById("present").innerText += data.numberOrange
			document.getElementById("guessed").innerText += seen
			document.getElementById("error").innerText += data.numberOrange-seen
			document.getElementById("errorguess").innerText += uncertainty
			document.getElementById("time").innerText += (" "+Math.round(data.visibleFor*0.001)+" seconds to count")
		}else{
			//Take the user to where the button is leading
			$("body").find(that.attr("href")).fadeIn(300);
		}
	})
})
//})()