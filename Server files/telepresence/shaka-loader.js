

var hls = 'https://bitmovin-a.akamaihd.net/content/playhouse-vr/m3u8s/105560.m3u8';
var dash = 'https://lab.eyevinn.technology/webm/orah2/stream.mpd';
var playlist = {
  streams: [ dash ],
  titles: [ 'Exodus (HDR/VP9)', 'STSWE17 (Wowza)' ],
  pos: -1,
};



function init(passIf) {
  console.log("init");
  if(playlist.streams.length > 0 || passIf === true){
    if(playlist.streams[0].indexOf("m3u8") != -1){
      console.log("hls");
      hlsLoad()
    }else{
      console.log("dash");
      initApp()
    }
  }
}

function initApp() {
  shaka.polyfill.installAll();
  if (shaka.Player.isBrowserSupported()) {
    var player = initPlayer();
    window.player = player;
    doPlay(player, pushNext());
  } else {
    console.error('Browser not supported!');
  }
}

function initPlayer() {
  var video = document.getElementById('video_1');
  var player = new shaka.Player(video);

  player.addEventListener('error', onErrorEvent);
  video.addEventListener('ended', function () {
    player.unload();
    doPlay(player, pushNext());
  });
  return player;
}

function doPlay(player, src) {
  player.load(src.manifest).then(function() {
    //var title = document.getElementById('title');
    //title.innerHTML = src.title;
  }).catch(onError);
}

function pushNext() {
  playlist.pos++;
  if (playlist.pos > playlist.streams.length-1) {
    playlist.pos = 0;
  }
  return {
    manifest: playlist.streams[playlist.pos],
    title: playlist.titles[playlist.pos]
  };
}

function onErrorEvent(event) {
  onError(event.detail);
}

function onError(error) {
  console.error('Error code', error.code, 'object', error);
}

function hlsLoad(stream) {
  if(Hls.isSupported()) {
    var video = document.getElementById('video_1');
    var hls = new Hls();
    hls.loadSource(playlist.streams[0]);
    hls.attachMedia(video);
    hls.on(Hls.Events.MANIFEST_PARSED,function() {
      video.play();
  });
}else {
  initApp();
}
}

document.addEventListener('DOMContentLoaded', init);
