var train=require("./train_list.json");
var fs=require("fs");

var allTtrainData=[];

for(var datekey in train){
    var dateTrain=train[datekey];
    for(var trainTypeKey in dateTrain){
        var trainData=dateTrain[trainTypeKey];
        for(var i=0;i<trainData.length;i++){
            if(allTtrainData.findIndex(k=>k.train_no==trainData[i].train_no)<=0){
                allTtrainData.push(trainData[i]);
            }
        }
    }
}

fs.writeFile('./train_list_data.json', JSON.stringify(allTtrainData));
