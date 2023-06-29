package co.aikar.commands;

import com.molean.tencent.channelbot.Bot;
import com.molean.tencent.channelbot.entity.Message;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Getter
public class QQProCommandIssuer implements CommandIssuer {

    private final co.aikar.commands.QQProCommandManager QQProCommandManager;
    private final Message message;

    private final Bot bot;

    public QQProCommandIssuer(QQProCommandManager QQProCommandManager, Message message, Bot bot) {
        this.QQProCommandManager = QQProCommandManager;
        this.message = message;
        this.bot = bot;
    }


    @Override
    public QQProCommandIssuer getIssuer() {
        return new QQProCommandIssuer(QQProCommandManager, message, bot);
    }

    @Override
    public QQProCommandManager getManager() {
        return QQProCommandManager;
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    public @NotNull UUID getUniqueId() {
        return UUID.nameUUIDFromBytes(("ChannelUser:" + message.getAuthor().getId()).getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public boolean hasPermission(String permission) {
        return bot.getBotCommandPermission().hasPermission(message.getAuthor(), permission);
    }

    @Override
    public void sendMessageInternal(String message) {
        reply(message);
    }

    public void reply(String message) {
        String content = "<@!" + this.message.getAuthor().getId() + ">" + message;
//        if (Bukkit.isde) {
//            content = "[DEBUG]" + content;
//        }

        while (content.length() > 0) {
            int len = Math.min(2000, content.length());
            bot.getMessageService().replyMessage(this.message, content.substring(0, len));
            content = content.substring(len);
        }

    }

    @Override
    public void sendMessage(String message) {
        reply(message);
    }


}
