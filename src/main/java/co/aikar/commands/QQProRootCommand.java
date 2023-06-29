package co.aikar.commands;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.molean.tencent.channelbot.Bot;
import com.molean.tencent.channelbot.BotCommandExecutor;
import com.molean.tencent.channelbot.entity.Message;

import java.util.ArrayList;
import java.util.List;

public class QQProRootCommand implements RootCommand, BotCommandExecutor {

    private co.aikar.commands.QQProCommandManager QQProCommandManager;
    private String name;

    private BaseCommand defCommand;
    private SetMultimap<String, RegisteredCommand> subCommands = HashMultimap.create();

    private QQProLocales locales;

    private List<BaseCommand> children = new ArrayList<>();

    public QQProRootCommand(QQProCommandManager QQProCommandManager, String name) {
        this.QQProCommandManager = QQProCommandManager;
        this.name = name;
    }

    @Override
    public void addChild(BaseCommand command) {
        if (this.defCommand == null || !command.subCommands.get(BaseCommand.DEFAULT).isEmpty()) {
            this.defCommand = command;

        }
        this.addChildShared(this.children, this.subCommands, command);
    }

    @Override
    public QQProCommandManager getManager() {
        return QQProCommandManager;
    }

    @Override
    public SetMultimap<String, RegisteredCommand> getSubCommands() {
        return subCommands;
    }

    @Override
    public List<BaseCommand> getChildren() {
        return children;
    }

    @Override
    public BaseCommand getDefCommand() {
        return defCommand;
    }

    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public void execute(Bot bot, Message message, String cmd, List<String> args) {
        execute(QQProCommandManager.getCommandIssuer(message), getCommandName(), args.toArray(new String[]{}));
    }
}
