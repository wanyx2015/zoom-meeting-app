import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';


import { Platform } from 'ionic-angular';
import { AppVersion } from '@ionic-native/app-version';

declare var ZoomPlugin: any;

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {

  plugindate;

  constructor(public navCtrl: NavController, platform: Platform, appVersion: AppVersion) {

    platform.ready().then(() => {


      // contact.find(['displayName']).then((c) => {
      //   console.log("Printing contacts:", c)
      //   this.contacts = c
      // });

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

