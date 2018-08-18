/**
 * 
 * Updated by Yuxi Wan
 * 
 * Reference https://developer.zoom.us/docs/android/getting-started/ for more details
 * 
 * Download Zoom MobileRTC SDK for Android: https://github.com/zoom/zoom-sdk-android
 * 
 */
package com.example;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.content.Context;

public class ZoomPlugin extends CordovaPlugin  {

  private static final String TAG = "ZoomPlugin";

  public final static String DISPLAY_NAME = "IONIC APP";

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);

    Log.d(TAG, "Initializing Zoom Plugin");

  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {

    // zoomSDK.initialize(this, APP_KEY, APP_SECRET, WEB_DOMAIN, this);

    if (action.equals("echo")) {
      String phrase = args.getString(0);
      // Echo back the first argument
      Log.d(TAG, phrase);
    } else if (action.equals("getDate")) {
      // An example of returning data back to the web layer
      final PluginResult result = new PluginResult(PluginResult.Status.OK, (new Date()).toString());
      callbackContext.sendPluginResult(result);
    } else if (action.equals("test")) {
      // Start Zoom.us meeting activity
      this.openNewActivity(this.cordova.getActivity().getApplicationContext());
    }
    return true;
    
  }
  
    //// zoom plugin started here

    // ZoomSDK zoomSDK = ZoomSDK.getInstance();
    // System.out.println("SDK Initialised in exec method: " + zoomSDK);

    // if (!zoomSDK.isInitialized()) {
    //   System.out.println("SDK Initializing");
    //   zoomSDK.initialize(this.cordova.getActivity().getApplicationContext(), APP_KEY, APP_SECRET, WEB_DOMAIN, this);
    // }

    // MeetingService meetingService = zoomSDK.getMeetingService();
    // if (meetingService != null) {
    //   meetingService.addListener(this);
    // }

    // MeetingOptions opts = new MeetingOptions();

    // opts.no_driving_mode = true;
    // //		opts.no_meeting_end_message = true;
    // opts.no_titlebar = true;
    // opts.no_bottom_toolbar = true;
    // opts.no_invite = true;

    // // int ret = meetingService.startMeeting(context, USER_ID, ZOOM_TOKEN, STYPE, MEETING_ID, DISPLAY_NAME, opts);

    // // An example of returning data back to the web layer
    // final PluginResult result = new PluginResult(PluginResult.Status.OK, "Meeting return code " + meetingService);
    // callbackContext.sendPluginResult(result);

    // cordova.getActivity().runOnUiThread(new Runnable() {
    //   public void run() {
    //     callbackContext.success(); // Thread-safe.
    //   }
    // });

  private void openNewActivity(Context context) {
    Intent intent = new Intent(context, MainActivity.class);
    this.cordova.getActivity().startActivity(intent);
  }

  // public void onMeetingEvent(int meetingEvent, int errorCode, int internalErrorCode) {

  //   if (meetingEvent == MeetingEvent.MEETING_CONNECT_FAILED
  //       && errorCode == MeetingError.MEETING_ERROR_CLIENT_INCOMPATIBLE) {
  //     // Toast.makeText(this, "Version of ZoomSDK is too low!", Toast.LENGTH_LONG).show();
  //   }

  // }

  // public void onZoomSDKInitializeResult(int errorCode, int internalErrorCode) {
  //   Log.i(TAG, "onZoomSDKInitializeResult, errorCode=" + errorCode + ", internalErrorCode=" + internalErrorCode);

  //   if (errorCode != ZoomError.ZOOM_ERROR_SUCCESS) {
  //     // Toast.makeText(this, "Failed to initialize Zoom SDK. Error: " + errorCode + ", internalErrorCode=" + internalErrorCode, Toast.LENGTH_LONG);
  //     System.out
  //         .println("Failed to initialize Zoom SDK. Error: " + errorCode + ", internalErrorCode=" + internalErrorCode);
  //   } else {
  //     // Toast.makeText(this, "Initialize Zoom SDK successfully.", Toast.LENGTH_LONG).show();
  //     System.out.println("Initialize Zoom SDK successfully.");

  //     registerMeetingServiceListener();
  //   }
  // }

  // private void registerMeetingServiceListener() {
  //   ZoomSDK zoomSDK = ZoomSDK.getInstance();
  //   MeetingService meetingService = zoomSDK.getMeetingService();
  //   if (meetingService != null) {
  //     meetingService.addListener(this);
  //   }
  // }
}
