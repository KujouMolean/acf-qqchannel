package co.aikar.commands;


import com.molean.tencent.channelbot.Bot;

public class QQProCommandCompletionContext extends CommandCompletionContext<QQProCommandIssuer> {
    private final Bot bot;

    QQProCommandCompletionContext(Bot bot, RegisteredCommand command, QQProCommandIssuer issuer, String input, String config, String[] args) {

        super(command, issuer, input, config, args);
        this.bot = bot;
    }

}
