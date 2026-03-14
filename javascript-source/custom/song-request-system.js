(function () {
  $.bind('command', function (event) {
    var command = event.getCommand();
    var args = event.getArgs();

    if (command.equalsIgnoreCase('songrequest')) {
      //   if (args.length === 0) {
      //     $.say('Usage: !songrequest <YouTube URL>');
      //     return;
      //   }

      var result = $.kentobotAPI.requestSong('song123', 'jsnphil');
      if (result._success) {
        $.say('Song requested successfully!');
      }
    }
  });

  $.bind('initReady', function () {
    $.registerChatCommand('./custom/song-request-system.js', 'songrequest', 2);
  });
})();
