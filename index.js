(function () {
    Parse.initialize("7V9KaI9SzZ2Iwk5ArgN5cCNzchHR6gapgcVKPHLY", "GJblVTRXEmZ1Q4jAzDO0NK5DdLtEDqqsHBQEoXCe");
    var SimulationFour = Parse.Object.extend("SimulationFour"),
        simulationFour = new SimulationFour(),
        uncertainties = [{
            tolerance: 1,
            reward: 0.25
        }, {
            tolerance: 2,
            reward: 0.20
        }, {
            tolerance: 5,
            reward: 0.10
        }, {
            tolerance: 10,
            reward: 0.05
        }, {
            tolerance: 20,
            reward: 0.01
        }],
        minNumberBlue = 40,
        maxNumberBlue = 200,
        minNumberOrange = 20,
        maxNumberOrange = 100,
        startTime = 0,
        completed = 0,
        data = {
            calibrated: {
                observationTime: 0,
                numberBlue: Math.floor(minNumberBlue + Math.random() * (maxNumberBlue - minNumberBlue + 1)),
                numberOrange: Math.floor(minNumberOrange + Math.random() * (maxNumberOrange - minNumberOrange + 1)),
                seen: minNumberOrange,
                uncertainty: uncertainties[0],
                reward: 0
            },
            student: { //<-- An assignment.
                observationTime: 0,
                numberBlue: Math.floor(minNumberBlue + Math.random() * (maxNumberBlue - minNumberBlue + 1)),
                numberOrange: Math.floor(minNumberOrange + Math.random() * (maxNumberOrange - minNumberOrange + 1)),
                seen: minNumberOrange,
                uncertainty: uncertainties[0],
                reward: 0
            }
        }, buildMarbles = function (assignment) {
            //Build the marbles
            var builder = $("<article></article>");
            for (var i = 0; i < assignment.numberBlue + assignment.numberOrange; i++) {
                //Build a marble
                var marble = $("<span class=\"marble-" + (i < assignment.numberBlue ? "blue" : "orange") + "\"></span>");
                //-180 = - 40 margin - 100 marble - 40 margin 
                marble.css({
                    "left": Math.random() * 100 + "%",
                    "top": Math.random() * 100 + "%",
                    "position": "absolute"
                });
                builder.append(marble)
            }
            return builder;
        };
    //Build the dropdown
    var dropdown = $(".certaintyselect");
    for (var i = 0; i < uncertainties.length; i++) {
        dropdown.append("<option>" + uncertainties[i].tolerance + "</option>");
    };
    //Build the HTML
    $(".content").appendTo("#calibrated");
    $(".content").clone().appendTo("#student");
    $(".numberseen").attr("min", minNumberOrange).attr("max", maxNumberOrange).val(minNumberOrange);
    $(".next").click(function () {
        var that = $(this),
            attr = that.attr("href"),
            callback = function () {
                if (attr == "#main") {
                    //User clicked on [Show the marbles -->]
                    //Build the marbles!
                    $("#student .count").append(buildMarbles(data.student));
                    $("#calibrated .count").append(buildMarbles(data.calibrated));
                    //Show all the marbles
                    $("#main").fadeIn(0);
                    startTime = Date.now();
                } else if (attr == "#observations") {
                    var context = $(that).parents(".panel"),
                        assignment = data[context.attr("id")];
                    assignment.observationTime = Date.now() - startTime;
                    context.find(".observations").fadeIn(300);
                } else if (attr == "#results") {
                    //Submit
                    var context = $(that).parents(".panel"),
                        assignment = data[context.attr("id")];
                    context.find(".todisable").attr("disabled", "disabled");
                    context.find(".next").hide();
                    context.find(".done").show();
                    assignment.reward = Math.abs(assignment.seen - assignment.numberOrange) <= assignment.uncertainty.tolerance ? assignment.uncertainty.reward : 0;
                    completed++;
                    if (completed >= 2) {
                        //We are done
                        simulationFour.save(data).then(function (object) {
                            $("#student .observations").fadeOut(300, function () {
                                //Prepare results
                                var assignment = data.calibrated,
                                    won = Math.abs(assignment.seen - assignment.numberOrange) <= assignment.uncertainty.tolerance;
                                $("#exclamation").text(won ? "Congratulations! You earned..." : "Unfortunately, you earned...");
                                $("#money").text("$" + assignment.reward);
                                $("#explanation").html("You guessed that there were " +
                                    assignment.seen + " orange marbles.<br>" +
                                    (won ? "There were really " : "However, there were actually ") +
                                    assignment.numberOrange +
                                    " orange marbles.<br>The actual number was " +
                                    (won ? "" : "not ") + "within " +
                                    assignment.uncertainty.tolerance + " marble" +
                                    (assignment.uncertainty.tolerance == 1 ? "" : "s") +
                                    (won ? "! Enjoy the reward." : ", so no money was awarded."));
                                $("#results").fadeIn(300);
                            });
                        });
                    };
                }
            };
        if (attr == "#results") {
            //Go! Fast!
            callback();
        } else {
            that.parents("section").fadeOut(300, callback);
        }
    })
    //Shuffle the student/calibrated
    if (Math.random() < 0.5) {
        $("#calibrated").addClass("left");
    } else {
        $("#student").addClass("left");
    }
    //Bind the events
    $(".numberseen").change(function () {
        var context = $(this).parents(".panel"),
            assignment = data[context.attr("id")];
        assignment.seen = this.value;
    });
    $(".certaintyselect").change(function () {
        var context = $(this).parents(".panel"),
            assignment = data[context.attr("id")];
        for (var i = 0; i < uncertainties.length; i++) {
            if (uncertainties[i].tolerance == (this.value)) {
                assignment.uncertainty = uncertainties[i];
                break;
            }
        };
        context.find(".certaintyexplanation").show();
        context.find(".within").text(assignment.uncertainty.tolerance + " marble" + (assignment.uncertainty.tolerance == 1 ? "" : "s"));
        context.find(".certaintyreward").text("$" + assignment.uncertainty.reward);
    }).trigger("change");

})();
