package com.holub.kyle.game.logic.player;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.holub.kyle.game.util.RandomUtil.RANDOM;

@Data
@Slf4j
public class PlayerQueue {

    private Queue<Player> playerQ = new LinkedList<>();
    private Queue<Player> dealerQ = new LinkedList<>();

    public PlayerQueue(List<Player> playerList) {
        Player randDealer = initRandomDealer(playerList);
        initDealerQueue(playerList, randDealer);
        initPlayerQueue();
    }

    private Player initRandomDealer(List<Player> players) {
        Player randDealer = players.get(RANDOM.nextInt(players.size() - 1));
        log.debug(String.format("%s is the initial dealer", randDealer));
        return randDealer;
    }

    private void initDealerQueue(List<Player> players, Player randDealer) {
        dealerQ.add(randDealer);
        List<Player> copyOfPlayers = new ArrayList<>(players);
        copyOfPlayers.remove(randDealer);
        dealerQ.addAll(copyOfPlayers);
    }

    private void initPlayerQueue() {
        playerQ.addAll(dealerQ);
        nextPlayer();
    }

    public void nextPlayer() {
        playerQ.add(playerQ.peek());
        playerQ.remove();
    }

    public int numPlayers() {
        return playerQ.size();
    }

    public void nextDealer() {
        dealerQ.add(playerQ.peek());
        dealerQ.remove();
    }

    public Player getDealer() {
        return dealerQ.peek();
    }

    public Player getCurrentPlayer() {
        return playerQ.peek();
    }
}
