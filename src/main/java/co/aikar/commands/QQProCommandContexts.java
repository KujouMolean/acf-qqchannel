package co.aikar.commands;

import com.molean.tencent.channelbot.Bot;
import com.molean.tencent.channelbot.entity.Channel;
import com.molean.tencent.channelbot.entity.Member;
import com.molean.tencent.channelbot.entity.Message;
import com.molean.tencent.channelbot.entity.User;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class QQProCommandContexts extends CommandContexts<QQProCommandExecutionContext> {
    private final Bot bot;

    QQProCommandContexts(CommandManager manager, Bot bot) {
        super(manager);
        this.bot = bot;
        registerIssuerOnlyContext(Bot.class, QQProCommandExecutionContext::getBot);
        registerIssuerOnlyContext(Message.class, botCommandExecutionContext -> botCommandExecutionContext.getIssuer().getMessage());
        registerIssuerOnlyContext(QQProCommandIssuer.class, CommandExecutionContext::getIssuer);
        registerIssuerAwareContext(Channel.class, botCommandExecutionContext -> {
            Message message = botCommandExecutionContext.getIssuer().getMessage();
            if (botCommandExecutionContext.hasFlag("issuer")) {
                String channelId = message.getChannelId();
                return bot.getChannelService().getChannelById(channelId);
            } else {
                String s = botCommandExecutionContext.popFirstArg();

                if (s != null && s.matches("<#\\d*?>")) {
                    Pattern compile = Pattern.compile("<#(\\d*?)>");
                    Matcher matcher = compile.matcher(s);
                    if (matcher.find()) {
                        String group = matcher.group(1);
                        Channel channelById = bot.getChannelService().getChannelById(group);
                        if (channelById != null) {
                            return channelById;
                        }
                    }
                }
            }
            throw new InvalidCommandArgument("不是子频道.");
        });

        registerContext(Member.class, botCommandExecutionContext -> {
            Message message = botCommandExecutionContext.getIssuer().getMessage();
            if (botCommandExecutionContext.hasFlag("issuer")) {
                Member memberById = bot.getMemberService().getMemberById(message.getGuildId(), message.getAuthor().getId());
                if (memberById == null) {
                    throw new InvalidCommandArgument("不是一个用户");
                }
                return memberById;
            } else {
                String s = botCommandExecutionContext.popFirstArg();
                if (s.matches("<@!\\d*?>")) {

                    Pattern compile = Pattern.compile("<@!(\\d*?)>");
                    Matcher matcher = compile.matcher(s);

                    if (matcher.find()) {
                        String group = matcher.group(1);
                        String guildId = message.getGuildId();
                        Member memberById = bot.getMemberService().getMemberById(guildId, group);
                        if (memberById != null) {
                            return memberById;
                        }
                    }
                }
            }
            throw new InvalidCommandArgument("不是一个用户");
        });
        registerIssuerOnlyContext(User.class, botCommandExecutionContext -> botCommandExecutionContext.getIssuer().getMessage().getAuthor());
    }
}
