package com.neusoft.psd;

import java.util.List;

import org.apache.cordova.DroidGap;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import com.phonegap.api.Plugin;

public class SoftwareListUtil extends Plugin {
	
	
	private static final String LIST_ACTION="list";
	
	private static final String UNINSTAL_ACTION="uninstall";
	
	
	private String callbackId;
	
	
	private  BroadcastReceiver receiver;

	@Override
	public PluginResult execute(String action, JSONArray args, String callbackId) {
		this.callbackId=callbackId;
		if (action.equals(LIST_ACTION)){
			PluginResult result=new PluginResult(PluginResult.Status.OK,getInstalledSoftware());
			return result;
		} else if(action.equals(UNINSTAL_ACTION)){
			PluginResult result=new PluginResult(PluginResult.Status.NO_RESULT);
			result.setKeepCallback(true);
			String packageName;
			try {
				packageName = args.getString(0);
	            //检查是否卸载成功
	            IntentFilter intentFilter = new IntentFilter(Intent.ACTION_PACKAGE_DATA_CLEARED) ;
	            intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
	            intentFilter.addDataScheme("package");
	            if (this.receiver == null) {
	                this.receiver = new BroadcastReceiver() {
	                    @Override
	                    public void onReceive(Context context, Intent intent) { 
	                    	updateSoftware(intent);              
	                    }
	                };
	                ctx.registerReceiver(this.receiver, intentFilter);
	            }
				this.showUninstallIntent(packageName);
			} catch (JSONException e) {
				return new PluginResult(PluginResult.Status.JSON_EXCEPTION);
			}
			return result;
		}
		return new PluginResult(PluginResult.Status.INVALID_ACTION);
		
	}
	
	/**
	 * 设置是否是同步方式调用，如果返回false就是异步，反之为同步
	 * 同步方式会直接返回结果
	 */
	public boolean isSynch(String action) {
		if(action.equals(LIST_ACTION)){
			return true;
		}
		return false;
	}
	
	
	private JSONArray getInstalledSoftware(){
		JSONArray array=new JSONArray();
        //获得安装程序列表
        PackageManager packMan=((DroidGap)this.ctx).getPackageManager();
        List<PackageInfo> packgeInfoList=packMan.getInstalledPackages(PackageManager.GET_ACTIVITIES);
        
        JSONObject object;
    
        if(packgeInfoList.size()!=0){
        	for(PackageInfo info:packgeInfoList){
        		ApplicationInfo appInfo=info.applicationInfo;
        		if((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0){
        			try {
        				object=new JSONObject();
						object.put("name", appInfo.loadLabel(packMan).toString()+"("+info.versionName+")");
						object.put("packageName",info.packageName);
						array.put(object);
					} catch (JSONException e) {
						Log.e(SoftwareListUtil.class.getName(), e.getMessage());
					}
        		}
        		
        	}
        }
        
        return array;
	}
	
	//检查到软件卸载
	private void updateSoftware(Intent intent){
		JSONObject object=new JSONObject();
		try {
			object.put("status", 1);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.success(object, callbackId);
	}
	
	
	private void showUninstallIntent(String packageName){
		Uri packageURI = Uri.parse("package:"+packageName);           
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI); 
		this.ctx.startActivity(uninstallIntent);
	}

}
