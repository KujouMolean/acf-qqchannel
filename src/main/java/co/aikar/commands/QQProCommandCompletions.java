package co.aikar.commands;


import com.molean.tencent.channelbot.Bot;

public class QQProCommandCompletions extends CommandCompletions<QQProCommandCompletionContext> {
    private final Bot bot;
    public QQProCommandCompletions(CommandManager manager, Bot bot) {

        super(manager);
        this.bot = bot;
    }
}
