
function SoftwareListUtil(){}

SoftwareListUtil.prototype.list=function(){
	
	return Cordova.exec(null,null,'SoftwareListUtil','list',[]);
}

SoftwareListUtil.prototype.uninstall=function(successFn,packageName){
	return Cordova.exec(successFn,null,'SoftwareListUtil','uninstall',[packageName]);
}



function DateUtil(){}

DateUtil.prototype.showDate = function( successFn, errorFn){
	
	return Cordova.exec(successFn,errorFn,'DateUtil','date',[]);
}


DateUtil.prototype.showTime = function( successFn, errorFn){
	
	return Cordova.exec(successFn,errorFn,'DateUtil','time',[]);
}


Cordova.addConstructor(function() {
       Cordova.addPlugin("softwareListUtil", new SoftwareListUtil());
	   Cordova.addPlugin("DateUtil", new DateUtil());
});

