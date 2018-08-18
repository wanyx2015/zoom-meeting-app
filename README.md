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


## How to use the plugin

If you want to build the Ionic zoom demo app from scratch, here is the steps

### Build the initial framework
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

### Add the Zoom plugin (zoomus-plugin folder)

```
cordove plugin remove zoom-plugin
cordova plugin add zoomus-plugin/
```

### Modify the home component

#### app.module.ts
1. Load app-version
`import { AppVersion } from '@ionic-native/app-version';`

2. Add the AppVersion to providers

#### home.component.ts

1. Import AppVersion and Platform
```
import { Platform } from 'ionic-angular';
import { AppVersion } from '@ionic-native/app-version';

declare var ZoomPlugin: any;
```
2. Edit HomePage class
```
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
#### home.component.html
1. Add a button that initialize the meeting
```
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
