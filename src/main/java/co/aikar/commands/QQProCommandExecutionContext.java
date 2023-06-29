package co.aikar.commands;

import com.molean.tencent.channelbot.Bot;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class QQProCommandExecutionContext extends CommandExecutionContext<QQProCommandExecutionContext, QQProCommandIssuer> {
    private final Bot bot;

    QQProCommandExecutionContext(Bot bot, RegisteredCommand cmd, CommandParameter param, QQProCommandIssuer sender, List<String> args, int index, Map<String, Object> passedArgs) {
        super(cmd, param, sender, args, index, passedArgs);
        this.bot = bot;
    }
}
