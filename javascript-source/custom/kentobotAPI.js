/*
 * Copyright (C) 2016-2026 phantombot.github.io/PhantomBot
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/* global Packages */

/**
 * kentobotAPI.js
 * 
 * JavaScript wrapper for the Kentobot API client
 * Provides easy access to Kentobot API functionality from PhantomBot scripts
 */

(function() {
    var KentobotAPI = Packages.kentobot.KentobotAPIv1;

    /**
     * @function requestSong
     * @export $.kentobotAPI
     * @param {String} songId
     * @param {String} username
     * @returns {Object} API response object
     */
    function requestSong(songId, username) {
        try {
            if (!songId || !username) {
                return {
                    _success: false,
                    _error: 'Missing required parameters',
                    _errorMessage: 'Both songId and username are required'
                };
            }

            var result = KentobotAPI.requestSong(songId, username);
            return JSON.parse(result.toString());
        } catch (ex) {
            $.log.error('KentobotAPI.requestSong failed: ' + ex.getMessage());
            return {
                _success: false,
                _error: 'Exception',
                _exception: ex.getMessage()
            };
        }
    }

    /**
     * @function getSongQueue
     * @export $.kentobotAPI
     * @returns {Object} API response object containing queue data
     */
    function getSongQueue() {
        try {
            var result = KentobotAPI.getSongQueue();
            return JSON.parse(result.toString());
        } catch (ex) {
            $.log.error('KentobotAPI.getSongQueue failed: ' + ex.getMessage());
            return {
                _success: false,
                _error: 'Exception',
                _exception: ex.getMessage()
            };
        }
    }

    /**
     * @function getCurrentSong
     * @export $.kentobotAPI
     * @returns {Object} API response object containing current song data
     */
    function getCurrentSong() {
        try {
            var result = KentobotAPI.getCurrentSong();
            return JSON.parse(result.toString());
        } catch (ex) {
            $.log.error('KentobotAPI.getCurrentSong failed: ' + ex.getMessage());
            return {
                _success: false,
                _error: 'Exception',
                _exception: ex.getMessage()
            };
        }
    }

    /**
     * @function getAPIStatus
     * @export $.kentobotAPI
     * @returns {Object} API response object containing status data
     */
    function getAPIStatus() {
        try {
            var result = KentobotAPI.getAPIStatus();
            return JSON.parse(result.toString());
        } catch (ex) {
            $.log.error('KentobotAPI.getAPIStatus failed: ' + ex.getMessage());
            return {
                _success: false,
                _error: 'Exception',
                _exception: ex.getMessage()
            };
        }
    }

    /**
     * @function testConnection
     * @export $.kentobotAPI
     * @returns {Boolean} true if API is reachable
     */
    function testConnection() {
        try {
            return KentobotAPI.testConnection();
        } catch (ex) {
            $.log.error('KentobotAPI.testConnection failed: ' + ex.getMessage());
            return false;
        }
    }

    /**
     * @function isEnabled
     * @export $.kentobotAPI
     * @returns {Boolean} true if Kentobot API is configured and enabled
     */
    function isEnabled() {
        var baseUrl = $.getIniDbString('settings', 'kentobotapi.baseurl', '');
        return baseUrl !== '' || $.getIniDbString('settings', 'kentobotapi.token', '') !== '';
    }

    // Export functions to the global scope
    $.kentobotAPI = {
        requestSong: requestSong,
        getSongQueue: getSongQueue,
        getCurrentSong: getCurrentSong,
        getAPIStatus: getAPIStatus,
        testConnection: testConnection,
        isEnabled: isEnabled
    };
})();