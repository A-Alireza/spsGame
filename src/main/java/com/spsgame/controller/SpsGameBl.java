package com.spsgame.controller;

import com.spsgame.message.messageObjectFromClientToServer;
import com.spsgame.message.messageObjectFromServerToClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * "SpsGameBl" class is used for handling SPS game business logic.
 * @author @lireza Alimohammadi
 */
@Service
public class SpsGameBl {

    @Autowired
    SimpMessagingTemplate template;
    private Map<String, String> playersInMemoryDataStructure = new HashMap<>();
    private List<String> keyList = new ArrayList<>();

    private static final String STONE = "1";
    private static final String PAPER = "2";
    private static final String SCISSOR = "3";

    /**
     * "playersSelection" method is used for receive players selection and prepare in memory data structure to store them.
     * @param messageObjectFromClientToServer
     * @return void
     * @author @lireza Alimohammadi
     */
    public void playersSelection(messageObjectFromClientToServer messageObjectFromClientToServer){
        playersInMemoryDataStructure.put(messageObjectFromClientToServer.getName(), messageObjectFromClientToServer.getChoice());
        if (playersInMemoryDataStructure.size() < 2) {
            template.convertAndSend("/messageBroker/publishSubscribe", new messageObjectFromServerToClient(" Waiting to Opponent..."));
            return;
        }

        for (String key : playersInMemoryDataStructure.keySet()) {
            keyList.add(key);
        }

        Boolean[] resultOfComparison = playersSelectionComparison(playersInMemoryDataStructure.get(keyList.get(0)), playersInMemoryDataStructure.get(keyList.get(1)));
        if (resultOfComparison != null) {
            resultOfGame(resultOfComparison);
        }

        playersInMemoryDataStructure.clear();
        keyList.removeAll(keyList);
    }

    /**
     * "playersSelectionComparison" method is used for comparing players selection.
     * @param firstPlayerChoice, secondPlayerChoice
     * @return Boolean[]
     * @author @lireza Alimohammadi
     */
    private Boolean[] playersSelectionComparison(String firstPlayerChoice, String secondPlayerChoice){

        Boolean result[] = new Boolean[2];

        Boolean firstPlayerResult = false;
        Boolean secondPlayerResult = false;

        switch (firstPlayerChoice) {
            case STONE:
                switch (secondPlayerChoice) {
                    case STONE:
                        firstPlayerResult = true;
                        secondPlayerResult = true;
                        break;
                    case PAPER:
                        secondPlayerResult = true;
                        break;
                    case SCISSOR:
                        firstPlayerResult = true;
                        break;
                    default:
                        template.convertAndSend("/messageBroker/publishSubscribe", new messageObjectFromServerToClient(keyList.get(1) + " has entered the wrong option"));
                }
                break;

            case PAPER:
                switch (secondPlayerChoice) {
                    case STONE:
                        firstPlayerResult = true;
                        break;
                    case PAPER:
                        firstPlayerResult = true;
                        secondPlayerResult = true;
                        break;
                    case SCISSOR:
                        secondPlayerResult = true;
                        break;
                    default:
                        template.convertAndSend("/messageBroker/publishSubscribe", new messageObjectFromServerToClient(keyList.get(1) + " has entered the wrong option"));
                }
                break;

            case SCISSOR:
                switch (secondPlayerChoice) {
                    case STONE:
                        secondPlayerResult = true;
                        break;
                    case PAPER:
                        firstPlayerResult = true;
                        break;
                    case SCISSOR:
                        firstPlayerResult = true;
                        secondPlayerResult = true;
                        break;
                    default:
                        template.convertAndSend("/messageBroker/publishSubscribe", new messageObjectFromServerToClient(keyList.get(1) + " has entered the wrong option"));
                }
                break;

            default:
                template.convertAndSend("/messageBroker/publishSubscribe", new messageObjectFromServerToClient(keyList.get(0) + " has entered the wrong option"));
        }

        result[0] = firstPlayerResult;
        result[1] = secondPlayerResult;

        return result;
    }

    /**
     * "resultOfGame" method is determine who is the winner.
     * @param Boolean[] resultOfComparison
     * @return void
     * @author @lireza Alimohammadi
     */
    private void resultOfGame (Boolean[] resultOfComparison){
        Boolean firstPlayerResult = resultOfComparison[0];
        Boolean secondPlayerResult = resultOfComparison[1];
        if (firstPlayerResult && secondPlayerResult) {
            template.convertAndSend("/messageBroker/publishSubscribe", new messageObjectFromServerToClient(" Draw "));
        } else if (firstPlayerResult && !secondPlayerResult) {
            template.convertAndSend("/messageBroker/publishSubscribe", new messageObjectFromServerToClient("Winner is: " + keyList.get(0)));
        } else if (!firstPlayerResult && secondPlayerResult) {
            template.convertAndSend("/messageBroker/publishSubscribe", new messageObjectFromServerToClient("Winner is: " + keyList.get(1)));
        }
    }
}
