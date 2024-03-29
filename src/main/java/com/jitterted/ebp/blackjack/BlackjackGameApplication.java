package com.jitterted.ebp.blackjack;

import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.ShuffledDeck;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// COMPOSITE ROOT
// CONFIGURATOR
// BOOTSTRAP ASSEMBLER
@SpringBootApplication
public class BlackjackGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlackjackGameApplication.class, args);
    }

    @Bean
    public Game createGame() {
        return new Game(new ShuffledDeck());
    }

}
