var app = angular.module('myApp', []);
app.controller('myCtrl', function($scope, WebSocketService, $timeout) {
    $scope.username = '';
    $scope.messages 			= [];
    $scope.users 			= {};
    var TOPIC_PARTICIPANTS_PATH 		= "/app/chat.participants";
    var TOPIC_LOGIN_PATH 				= "/topic/chat.login";
    var TOPIC_LOGOUT_PATH 				= "/topic/chat.logout";
    
    var TOPIC_MESSAGES_PATH 		= "/app/chat.messages";
    var TOPIC_MESSAGE_PATH 		= "/topic/chat.message";
    var SEND_MESSAGE_PATH 		= "/app/chat.message";

	WebSocketService.subscribe(TOPIC_MESSAGE_PATH, function(message) {
        console.log("myCtrl - message from: " + TOPIC_MESSAGE_PATH + "\n" + message.body);
        var item = JSON.parse(message.body);
        $scope.messages.push(item);
        $scope.$apply();
        $scope.focusLastMessage();
        
	});
	WebSocketService.subscribe(TOPIC_MESSAGES_PATH, function(messages) {
        console.log("myCtrl - message from: " + TOPIC_MESSAGE_PATH + "\n" + messages.body);
        $scope.messages = JSON.parse(messages.body);
        $scope.$apply();
        $scope.focusLastMessage();
        
	});
	WebSocketService.subscribe(TOPIC_LOGIN_PATH, function(messages) {
        console.log("myCtrl - message from: " + TOPIC_LOGIN_PATH + "\n" + messages.body);
        var user = JSON.parse(messages.body);
        $scope.users[user.username] = user;
        $scope.$apply();        
	});

	WebSocketService.subscribe(TOPIC_LOGOUT_PATH, function(messages) {
        console.log("myCtrl - message from: " + TOPIC_LOGOUT_PATH + "\n" + messages.body);
        var user = JSON.parse(messages.body);
        delete $scope.users[user.username];
        $scope.$apply();        
	});

	WebSocketService.subscribe(TOPIC_PARTICIPANTS_PATH, function(messages) {
        console.log("myCtrl - message from: " + TOPIC_PARTICIPANTS_PATH + "\n" + messages.body);
        var users = JSON.parse(messages.body);
        angular.forEach(users, function(value, key) {
        	  $scope.users[key] = value;
        	});
        $scope.$apply();        
	});
    $scope.sendMessage = function () {

        console.log("chatController - sendMessage to: " + SEND_MESSAGE_PATH);
        var message 	= $scope.message;
        $scope.message 	= '';
        WebSocketService.send(SEND_MESSAGE_PATH, {
        											message: message,
        											sender:{username:$scope.username}
        										});
    };
    $scope.focusLastMessage = function () {
        $timeout(function() {
            var scroller = document.getElementById("chat-ul");
            scroller.scrollTop = scroller.scrollHeight;
          }, 500, false);
    };
});