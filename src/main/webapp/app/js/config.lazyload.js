// lazyload config

angular.module('app')
        /**
         * jQuery plugin config use ui-jq directive , config the js and css files that required
         * key: function name of the jQuery plugin
         * value: array of the css js file located
         */
        .constant('JQ_CONFIG', {
            moment: ['libs/jquery/moment/moment.js'],
            fullcalendar: ['libs/jquery/moment/moment.js',
                'libs/jquery/fullcalendar/dist/fullcalendar.min.js',
                'libs/jquery/fullcalendar/dist/fullcalendar.css',
                'libs/jquery/fullcalendar/dist/fullcalendar.theme.css'],
            daterangepicker: ['libs/jquery/moment/moment.js',
                'libs/jquery/bootstrap-daterangepicker/daterangepicker.js',
                'libs/jquery/bootstrap-daterangepicker/daterangepicker-bs3.css']
        }
        )
        .constant('MODULE_CONFIG', [
            {
                name: 'xeditable',
                files: [
                    'libs/angular/angular-xeditable/dist/js/xeditable.min.js',
                    'libs/angular/angular-xeditable/dist/css/xeditable.css'
                ]
            },
            {
                name: 'smart-table',
                files: [
                    'libs/angular/angular-smart-table/dist/smart-table.min.js'
                ]
            },
            {
                name: 'ui.select',
                files: [
                    'libs/angular/angular-ui-select/dist/select.min.js',
                    'libs/angular/angular-ui-select/dist/select.min.css'
                ]
            },
            {
                name: 'toaster',
                files: [
                    'libs/angular/angularjs-toaster/toaster.js',
                    'libs/angular/angularjs-toaster/toaster.css'
                ]
            },
            {
                name: 'ui.calendar',
                files: ['libs/angular/angular-ui-calendar/src/calendar.js']
            }
        ]
                )
        // oclazyload config
        .config(['$ocLazyLoadProvider', 'MODULE_CONFIG', function ($ocLazyLoadProvider, MODULE_CONFIG) {
                // We configure ocLazyLoad to use the lib script.js as the async loader
                $ocLazyLoadProvider.config({
                    debug: false,
                    events: true,
                    modules: MODULE_CONFIG
                });
            }])
        ;