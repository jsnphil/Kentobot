/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

(function () {

    $.bind('command', function (event) {
        var sender = event.getSender(), // Gets the person who used the command
                command = event.getCommand(), // Gets the command being used
                args = event.getArgs() // Arguments used in the command
                ;

        if (command.equalsIgnoreCase('test')) {
            $.say("Testing command!");
            var result = $.kentobotApi.searchForSongRequest('QgW9PI9TbC8');
            $.say('Result of call: ' + result);
            return;
        }
    });

    /**
     * @event initReady
     */
    $.bind('initReady', function () {
        // `script` is the script location.
        // `command` is the command name without the `!` prefix.
        // `permission` is the group number. 0, 1, 2, 3, 4, 5, 6 and 7.
        // These are also used for the permcom command.
        // $.registerChatCommand('script', 'command', 'permission');

        $.registerChatCommand('./custom/testingSystem.js', 'test', 2);
    });
})();
