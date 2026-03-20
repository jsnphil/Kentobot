(function () {
  $.bind('command', function (event) {
    var command = event.getCommand();
    var sender = event.getSender();
    var args = event.getArgs();

    $.say(
      'Command received: ' +
        command +
        ' from ' +
        sender +
        ' with arguments: ' +
        args.join(' ')
    );

    if (command.equalsIgnoreCase('songrequest')) {
      if (args.length === 0) {
        $.say('Usage: !songrequest <YouTube URL>');
        return;
      }

      // https://youtu.be/nsqtNovzQ5w?si=mXESatiMFIlLj57s
      var youtubeId = args[0].split('youtu.be/')[1].split('\\?')[0];

      var result = $.kentobotAPI.requestSong(youtubeId, sender);
      $.say('Request result: ' + JSON.stringify(result));
      if (result._success) {
        $.say('Song requested successfully!');
      }
    }
  });

  $.bind('initReady', function () {
    $.registerChatCommand('./custom/song-request-system.js', 'songrequest', 2);
  });
})();
