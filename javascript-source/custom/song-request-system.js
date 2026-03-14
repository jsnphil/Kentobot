(function () {
  $.bind('command', function (event) {
    var command = event.getCommand();
    var args = event.getArgs();

    if (command.equalsIgnoreCase('songrequest')) {
      if (args.length === 0) {
        $.say('Usage: !songrequest <YouTube URL>');
        return;
      }
    }
  });

  $.bind('initReady', function () {
    $.registerChatCommand('./custom/song-request-system.js', 'songrequest', 2);
  });
})();
