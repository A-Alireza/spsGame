package com.spsgame.controller;

import com.spsgame.message.messageObjectFromClientToServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import java.io.IOException;

@Controller
public class SpsGameController {

    @Autowired
    SpsGameBl spsGameBl;

    //Handling: /spsGameApp/player
    @MessageMapping("/player")
    @SendTo("/messageBroker/publishSubscribe")
    public void getPlayer(messageObjectFromClientToServer messageObjectFromClientToServer) throws IOException {
        spsGameBl.playersSelection(messageObjectFromClientToServer);
    }
}
