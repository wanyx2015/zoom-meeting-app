package com.example;

import us.zoom.sdk.JoinMeetingOptions;
import us.zoom.sdk.MeetingError;
import us.zoom.sdk.MeetingEvent;
import us.zoom.sdk.MeetingOptions;
import us.zoom.sdk.MeetingService;
import us.zoom.sdk.MeetingServiceListener;
import us.zoom.sdk.MeetingStatus;
import us.zoom.sdk.ZoomError;
import us.zoom.sdk.ZoomSDK;
import us.zoom.sdk.ZoomSDKInitializeListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements Constants, MeetingServiceListener, ZoomSDKInitializeListener {

	private final static String TAG = "Zoom SDK Example";

	public final static String ACTION_RETURN_FROM_MEETING = "us.zoom.sdkexample2.action.ReturnFromMeeting";
	public final static String EXTRA_TAB_ID = "tabId";

	public final static int TAB_WELCOME = 1;
	public final static int TAB_MEETING = 2;
	public final static int TAB_PAGE_2 = 3;

	private final static int STYPE = MeetingService.USER_TYPE_API_USER;
	private final static String DISPLAY_NAME = Build.MANUFACTURER + " " + Build.PRODUCT;

	private View viewTabWelcome;
	private View viewTabMeeting;
	private View viewTabPage2;
	private Button btnTabWelcome;
	private Button btnTabMeeting;
	private Button btnTabPage2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Log.i(TAG, "        onCreate");
		Log.i(TAG, Build.PRODUCT);
		Log.i(TAG, Build.HARDWARE);
		Log.i(TAG, Build.DEVICE);
		Log.i(TAG, Build.MANUFACTURER);
		Log.i(TAG, Build.ID);
		Log.i(TAG, Build.BOARD);
		Log.i(TAG, Build.USER);
		Log.i(TAG, Build.FINGERPRINT);

		super.onCreate(savedInstanceState);

		//		setContentView(R.layout.main);

		//		setupTabs();

		ZoomSDK zoomSDK = ZoomSDK.getInstance();

		if (savedInstanceState == null) {
			zoomSDK.initialize(this, APP_KEY, APP_SECRET, WEB_DOMAIN, this);
		}

		if (zoomSDK.isInitialized()) {
			registerMeetingServiceListener();
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		Log.i(TAG, "        onNewIntent");

		super.onNewIntent(intent);

		// disable animation
		overridePendingTransition(0, 0);

		String action = intent.getAction();

		if (ACTION_RETURN_FROM_MEETING.equals(action)) {
			int tabId = intent.getIntExtra(EXTRA_TAB_ID, TAB_WELCOME);
			// selectTab(tabId);
		}
	}

	@Override
	public void onZoomSDKInitializeResult(int errorCode, int internalErrorCode) {
		Log.i(TAG, "        onZoomSDKInitializeResult");

		Log.i(TAG, "onZoomSDKInitializeResult, errorCode=" + errorCode + ", internalErrorCode=" + internalErrorCode);

		if (errorCode != ZoomError.ZOOM_ERROR_SUCCESS) {
			Toast.makeText(this,
					"Failed to initialize Zoom SDK. Error: " + errorCode + ", internalErrorCode=" + internalErrorCode,
					Toast.LENGTH_LONG);
		} else {
			Toast.makeText(this, "Initialize Zoom SDK successfully.", Toast.LENGTH_LONG).show();

			registerMeetingServiceListener();
		}
	}

	private void registerMeetingServiceListener() {
		Log.i(TAG, "        registerMeetingServiceListener");

		ZoomSDK zoomSDK = ZoomSDK.getInstance();
		MeetingService meetingService = zoomSDK.getMeetingService();
		if (meetingService != null) {
			meetingService.addListener(this);
		}

		JoinMeetingOptions opts = new JoinMeetingOptions();

		int ret = meetingService.joinMeeting(this, MEETING_ID, DISPLAY_NAME, "", opts);

	}

	@Override
	protected void onDestroy() {
		Log.i(TAG, "        onDestroy");

		ZoomSDK zoomSDK = ZoomSDK.getInstance();

		if (zoomSDK.isInitialized()) {
			MeetingService meetingService = zoomSDK.getMeetingService();
			meetingService.removeListener(this);
		}

		super.onDestroy();
	}

	public void startMeeting() {
		Log.i(TAG, "        startMeeting");

		ZoomSDK zoomSDK = ZoomSDK.getInstance();

		if (!zoomSDK.isInitialized()) {
			Toast.makeText(this, "ZoomSDK has not been initialized successfully", Toast.LENGTH_LONG).show();
			return;
		}

		if (MEETING_ID == null) {
			Toast.makeText(this, "MEETING_ID in Constants can not be NULL", Toast.LENGTH_LONG).show();
			return;
		}

		MeetingService meetingService = zoomSDK.getMeetingService();

		MeetingOptions opts = new MeetingOptions();
		opts.no_driving_mode = true;
		//		opts.no_meeting_end_message = true;
		opts.no_titlebar = true;
		opts.no_bottom_toolbar = true;
		opts.no_invite = true;

		int ret = meetingService.startMeeting(this, USER_ID, ZOOM_TOKEN, STYPE, MEETING_ID, DISPLAY_NAME, opts);

		Log.i(TAG, "onClickBtnStartMeeting, ret=" + ret);
	}

	@Override
	public void onMeetingEvent(int meetingEvent, int errorCode, int internalErrorCode) {
		Log.i(TAG, "        onMeetingEvent " + meetingEvent);

		if (meetingEvent == MeetingEvent.MEETING_CONNECT_FAILED
				&& errorCode == MeetingError.MEETING_ERROR_CLIENT_INCOMPATIBLE) {
			Toast.makeText(this, "Version of ZoomSDK is too low!", Toast.LENGTH_LONG).show();
		}
		if (meetingEvent == MeetingEvent.MEETING_DISCONNECTED) {
			Toast.makeText(this, "Meeting finished!", Toast.LENGTH_LONG).show();
			this.finish();
		}

		// if(meetingEvent == MeetingEvent.MEETING_DISCONNECTED || meetingEvent == MeetingEvent.MEETING_CONNECT_FAILED) {
		// 	selectTab(TAB_WELCOME);
		// }
	}
}
