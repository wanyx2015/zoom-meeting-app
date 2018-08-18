# Cordova plugin for Android Zoom SDK

## About the plugin

- `zoomus-plugin` is the plugin folder, right now it support android only
- Whenever you change code in that folder, you need to remove the plugin from the app and then add it again
- To remove and add the plugin again

```
# cd to project root folder
cordove plugin remove zoom-plugin
cordova plugin add zoomus-plugin/
```


# How to use the plugin

If you want to build the Ionic zoom demo app from scratch, here is the steps

## Build the initial framework
```
# create an initial app 
ionic start zoom-app android

# enable android platform support
cd zoom-app
ionic cordova platform add android

# very important library, never miss this step
ionic cordova plugin add cordova-plugin-android-support-v4-jar

# install app-version plugin
ionic cordova plugin add cordova-plugin-app-version
npm install --save @ionic-native/app-version
```
## Edit Zoom plugin parameters

You need to provide below parameters in Constants.java (*zoomus-plugin/src/android/com/example/Constants.java*):
* APP_KEY
* APP_SECRET
* USER_ID
* ZOOM_TOKEN
* MEETING_ID

```javascript
// TODO Change it to your APP Key
public final static String APP_KEY = "123";

// TODO Change it to your APP Secret
public final static String APP_SECRET = "123";

// TODO change it to your user ID
public final static String USER_ID = "your zoom.us login id, an email address";

// TODO change it to your token
public final static String ZOOM_TOKEN = "123";

// TODO Change it to your exist meeting ID to start meeting
public final static String MEETING_ID = "it can be your zoom.us personal meeting id";
```

## Add the Zoom plugin (zoomus-plugin folder)
After you save the Constants.java
- Add the plugin to Ionic app
- If you have previously added the plugin, remove it first

```
# remove the plugin
cordove plugin remove zoom-plugin

# add the plugin
cordova plugin add zoomus-plugin/
```

## Modify the home component

### app.module.ts
1. Load app-version
`import { AppVersion } from '@ionic-native/app-version';`

2. Add the AppVersion to providers

### home.component.ts

1. Import AppVersion and Platform
```javascript
import { Platform } from 'ionic-angular';
import { AppVersion } from '@ionic-native/app-version';

declare var ZoomPlugin: any;
```
2. Edit HomePage class
```javascript
export class HomePage {

  plugindate;

  constructor(public navCtrl: NavController, platform: Platform, appVersion: AppVersion) {

    platform.ready().then(() => {
      console.log(ZoomPlugin);
      ZoomPlugin.getDate((data) => {
        console.log("in getDate() callback," + data);
        this.plugindate = data;
      });
    });
  } // end of constructor


  joinZoom() {
    console.log("Join button clicked");

    ZoomPlugin.test((data) => {
      console.log("in test() callback," + data);
    });
  }
}
```
### home.component.html
1. Add a button that initialize the meeting
```html
<ion-header>
  <ion-navbar>
    <ion-title>Home</ion-title>
  </ion-navbar>
</ion-header>

<ion-content padding>
  <h2>Welcome to Ionic!</h2>
  <p>    <button ion-button (click)="joinZoom()">Click to Join Zoom Meeting</button>  </p>
  <p>{{ plugindate }}</p>
</ion-content>
```

## Runt app in your Android device

The last step
1. Connect you Android phone to your computer using USB cable
2. Run it!

Please note you need to run it on device, the plugin can not be deployed to emulator.

```
ionic cordova run android
```

Good Luck!
