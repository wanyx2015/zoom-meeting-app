var exec = require('cordova/exec');

var PLUGIN_NAME = 'ZoomPlugin';

var ZoomPlugin = {
  echo: function (phrase, cb) {
    console.log("www layer: " + phrase);
    exec(cb, null, PLUGIN_NAME, 'echo', [phrase]);
  },
  getDate: function (cb) {
    exec(cb, null, PLUGIN_NAME, 'getDate', []);
  },
  test: function (cb) {
    exec(cb, function (err) {
      console.log("Zoom error", err);
    }, PLUGIN_NAME, 'test', []);
  }
};

module.exports = ZoomPlugin;