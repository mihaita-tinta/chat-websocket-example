(function () {
    'use strict';

    angular.module('myApp').factory('WebSocketService', function($q) { 
    	var URL = 'https://lmf-chat.herokuapp.com/websocket';
//    	var URL = 'http://localhost:8088/websocket';
    	var counter = 0;
        var self = this;
        var subscribers = {};
        var connected = $q.defer();
        
    	var methods = {
    			connect : function() {          
 		           		   console.log('connect WebSocketService ... URL: ' + URL);
 		    	           var socket = new SockJS(URL);
	 		   	           self.stompClient = Stomp.over(socket);    
	 		   	           self.stompClient.debug = true;
	    		           self.stompClient.connect({}, function(frame) {
	    	                   connected.resolve("success");
	    		               console.log('Connected: ' + frame);
	    		               
	    		               self.stompClient.subscribe("/user/queue/errors", function(message) {
	    			                console.log("error: " + message.body);
	    				        });
	    		           });
    			},
    			send : function(destination, json) {
    	            if (self.stompClient != null && !self.stompClient.connected) {
    	            	methods.connect();
    	            }
                    connected.promise.then(function() {
	                    if (self.stompClient != null) {
		    	               console.log("WebSocketService - send to: " + destination);
		    	               self.stompClient.send(destination, {}, JSON.stringify(json));
	                    }
                    }, null, null);
    			},
    			subscribe : function(path, callback) {
    	            methods.connectIfNotConnected();
    	            
                    connected.promise.then(function() {
	                    if (self.stompClient != null) {
	    					  counter++;
			 	              console.log("WebSocketService - subscribe to: " + path);
			 	              var subscriber = self.stompClient.subscribe(path, callback);
			 	              subscribers[path] = {
			 	            		  path : path,
			 	            		  callback: callback,
			 	            		  subscriber : subscriber
			 	              };
	                    }
                    }, null, null);
    			},
    			unsubscribe : function(path) {
                    if (self.stompClient != null) {
	 	               if (subscribers[path] && subscribers[path].subscriber) {
	 	            	   subscribers[path].subscriber.unsubscribe();
	 	            	   subscribers[path] = null;
		 	               console.log("WebSocketService - unsubscribe from: " + path + " - OK");
	 	               }
                    }
    			},
    			disconnect : function () {
                    if (self.stompClient != null) {
                    	self.stompClient.disconnect();
                    	self.stompClient = null;
                    }
    			},
    			connectIfNotConnected : function() {
    				if (self.stompClient == null || self.stompClient.connected) {
    					connected = $q.defer();
    	            	methods.connect();
    	            }
    			}
    	};

    	return methods;
	});
	
})();