Dear Colleagues
I have developed the spsGame (Stone, Paper and Scissor Game) with following technologies:
Spring Boot, WebSocket, Publish/Subscribe message-oriented middleware, STOMP, SockJS, Java Script and HTML.
This game has designed just for two players via browser.
Please perform following steps to run the game:
-	Clone/Import the project and build and run it by Intellij IDEA
-	Open two tab of browser and type: http://localhost:8080
-	Press the connect button to connect the server
-	Put your name and put your choice (each player has just three choice: 1 for Stone, 2 for Paper and 3 for Scissor)
-	Server will receive both players selection (1 or 2 or 3 ) and it will calculate who is the winner and It will publish the result on the  message broker and then two clients will receive the result due to their subscription on the same message broker.
Good luck
