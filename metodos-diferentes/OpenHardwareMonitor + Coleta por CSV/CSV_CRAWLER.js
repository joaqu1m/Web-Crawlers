var FileReader = require('filereader')
, fileReader = new FileReader()
;
fileReader.setNodeChunkedEncoding(true || false);
fileReader.readAsDataURL(new File('OpenHardwareMonitorLog-2022-10-23.csv'));

fileReader.on('data', function (data) {
    console.log("chunkSize:", data.length);
  });
   
  // `onload` as listener
  fileReader.addEventListener('load', function (ev) {
    console.log("dataUrlSize:", ev.target.result.length);
  });
   
  // `onloadend` as property
  fileReader.onloadend, (function () {
    console.log("Success");
  });