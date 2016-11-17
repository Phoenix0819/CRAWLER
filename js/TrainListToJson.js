var train=require("./train_list.json");
var fs=require("fs");

var allTtrainData=[];

for(var datekey in train){
	if(datekey=="2016-11-25"){
	    var dateTrain=train[datekey];
	    for(var trainTypeKey in dateTrain){
	        var trainData=dateTrain[trainTypeKey];
	        for(var i=0;i<trainData.length;i++){
	        	 allTtrainData.push(trainData[i]);
	            if(allTtrainData.findIndex(k=>k.train_no==trainData[i].train_no)<=0){
	                allTtrainData.push(trainData[i]);
	            }
	        }
	    }
    }

}

fs.writeFile('./train_list_format.json', JSON.stringify(allTtrainData));
