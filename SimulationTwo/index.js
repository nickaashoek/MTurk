(function() {
Parse.initialize("7V9KaI9SzZ2Iwk5ArgN5cCNzchHR6gapgcVKPHLY", "GJblVTRXEmZ1Q4jAzDO0NK5DdLtEDqqsHBQEoXCe");
var SimulationTwo = Parse.Object.extend("SimulationTwo");
var simulationTwo = new SimulationTwo();
var maxReward = .25;
var minReward = .01;
var minNumberOrange = 1;
var maxNumberOrange = 100;
var uncertaintyPenaltyFactor = 0.1;
var deviationPenaltyFactor = 1;
var data = {
	visibleFor: 1000 + Math.random() * 4000,
	numberBlue: Math.floor(1 + Math.random() * 99),
	numberOrange: Math.floor(minNumberOrange + Math.random() * (maxNumberOrange - minNumberOrange + 1)),
	seen: 0,
	uncertainty: 1,
	reward: 0
}
var computeReward = function(seen, actual, uncertainty) {
	return minReward + (maxReward - minReward) * Math.min(1, Math.max(0, 1 - deviationPenaltyFactor * ((seen - actual) * (seen - actual)) / (uncertainty * uncertainty) - uncertaintyPenaltyFactor * Math.log(uncertainty)));
}
$("#numberseen").change(function(event) {
	data.seen = this.value;
	recomputeData();
});
$("#certaintyselect").change(function(event) {
	data.uncertainty = parseInt(this.value);
	recomputeData();
});
$(".next").click(function() {
	var that = $(this);
	that.parents("section").fadeOut(300, function() {
		var attr = that.attr("href");
		if (attr == "#main") {
			//User clicked on [Show the marbles -->]
			//Build the marbles
			for (var i = 0; i < data.numberBlue + data.numberOrange; i++) {
				//Build a marble
				var marble = $("<span class=\"marble-" + (i < data.numberBlue ? "blue" : "orange") + "\"></span>");
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
				$("#graph").fadeIn(300);
			});
		} else if (attr == "#results") {
			//Submit
			data.reward = computeReward(data.seen, data.numberOrange, data.uncertainty);
			simulationTwo.save(data).then(function(object) {
				$("#results").fadeIn(300);
			});
			//Setup data!
			finishlines.attr("d",
				"M" + xScale(data.numberOrange) + "," + yScale(0) +
				"L" + xScale(data.numberOrange) + "," + yScale(1) +
				"M" + xScale(minNumberOrange) + "," + yScale(data.reward) +
				"L" + xScale(maxNumberOrange) + "," + yScale(data.reward));
			$("#present").text(data.numberOrange);
			$("#guessed").text(data.seen);
			$("#error").text(Math.abs(data.numberOrange - data.seen));
			$("#errorguess").text(data.uncertainty);
			$("#reward").text(data.reward.toFixed(2));
		} else {
			//Take the user to where the button is leading
			$("body").find(that.attr("href")).fadeIn(300);
		}
	})
})
//Build the chart
var margin = {
	top: 20,
	right: 20,
	bottom: 30,
	left: 50
},
	width = 960 - margin.left - margin.right,
	height = 300 - margin.top - margin.bottom;

var xScale = d3.scale.linear().domain([minNumberOrange, maxNumberOrange])
	.range([0, width]);

var yScale = d3.scale.linear().domain([0, maxReward])
	.range([height, 0]);

var xAxis = d3.svg.axis()
	.scale(xScale)
	.orient("bottom");

var yAxis = d3.svg.axis()
	.scale(yScale)
	.orient("left");

var line = d3.svg.line()
	.x(function(d) {
		return xScale(d.actual);
	})
	.y(function(d) {
		return yScale(d.reward);
	});

var svg = d3.select("#graph").append("svg")
	.attr("width", width + margin.left + margin.right)
	.attr("height", height + margin.top + margin.bottom)
	.append("g")
	.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

// xScale.domain(d3.extent(data, function(d) {
// 	return d.date;
// }));
// yScale.domain(d3.extent(data, function(d) {
// 	return d.reward;
// }));

svg.append("g")
	.attr("class", "x axis")
	.attr("transform", "translate(0," + height + ")")
	.call(xAxis)
	.append("text")
	.attr("y", -20)
	.attr("x", 900)
	.style("text-anchor", "end")
	.attr("dy", ".71em")
	.text("Actual number of Orange Marbles");

svg.append("g")
	.attr("class", "y axis")
	.call(yAxis)
	.append("text")
	.attr("transform", "rotate(-90)")
	.attr("y", 6)
	.attr("dy", ".71em")
	.style("text-anchor", "end")
	.text("Reward ($)");

var path = svg.append("path")
	.attr("class", "line");
var finishlines = svg.append("path")
	.attr("class", "finishline");

var recomputeData = function() {
	var values = d3.range(minNumberOrange, maxNumberOrange + 0.5, 0.5).map(function(d) {
		return {
			actual: d,
			reward: computeReward(d, data.seen, data.uncertainty)
		};
	});
	path.attr("d", line(values));
}
})()
