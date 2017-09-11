var randomData;
$('#randomDataChart').highcharts({
  chart : {
    type : 'line',
    events : {
      load : function() {
        randomData = this.series[0];
      }
    }
  },
  title : {
    text : 'Spark Streaming Analysis Result'
  },
  xAxis : {
    type : 'datetime',
    minRange : 60 * 1000
  },
  yAxis : {
    title : {
      text : false
    },
    max: 100
  },
  legend : {
    enabled : false
  },
  plotOptions : {
    series : {
      threshold : 0,
      marker : {
        enabled : false
      }
    }
  },
  series : [ {
    name : 'Data',
      data : [ ]
    } ]
});

var socket = new SockJS('/kafka-spring-websocket/random');
var client = Stomp.over(socket);

client.connect('user', 'password', function(frame) {

  client.subscribe("/data", function(message) {
	//removing "" from messag.body
	message.body = message.body.substring(1,message.body.length);
	message.body = message.body.substring(0,message.body.length-1);
	message.body = message.body.replace(/\\/g, "");
	var messageBodyJSONObject = JSON.parse(message.body);
	document.getElementById("dataChartInfo").innerHTML='Summary of parameter read : '+ message.body;
    var point = [ (new Date()).getTime(), parseInt(messageBodyJSONObject.meanValue) ];
    var shift = randomData.data.length > 60;
    randomData.addPoint(point, true, shift);
  });

});