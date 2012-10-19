

cordova.define("phonegap/softwarelist", function(require, exports, module) {
	
	module.exports = {
		
		
		/**
		 * 列出android系统里面安装的所有非系统软件
		 */
		list: function(){
			
			return cordova.exec(null,null,'SoftwareListUtil','list',[]);
			
		},
		
		/**
		 * 卸载Android系统里面指定的软件
		 * @param {Object} successFn 卸载成功的回调函数
		 * @param {Object} packageName 要删除的软件包名字
		 */
		uninstall: function(successFn, packageName){
			
			return cordova.exec(successFn,null,'SoftwareListUtil','uninstall',[packageName]);
		}
		
	}
});


var softwareListUtil = cordova.require('phonegap/softwarelist');






