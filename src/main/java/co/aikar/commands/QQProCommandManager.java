package co.aikar.commands;

import com.molean.tencent.channelbot.Bot;
import com.molean.tencent.channelbot.entity.Message;
import com.molean.tencent.channelbot.entity.User;

import java.util.*;

public class QQProCommandManager extends CommandManager<User, QQProCommandIssuer, Object, QQProMessageFormater, QQProCommandExecutionContext, QQProConditionContext> {

    private co.aikar.commands.QQProCommandContexts QQProCommandContexts;
    private QQProCommandCompletions QQProCommandCompletions;
    private final Map<String, QQProRootCommand> registeredCommands = new HashMap<>();

    private final Bot bot;

    private QQProLocales locales;

    public QQProCommandManager(Bot bot) {
        this.bot = bot;
        this.formatters.put(MessageType.ERROR, defaultFormatter = new QQProMessageFormater("", "", ""));
        this.formatters.put(MessageType.SYNTAX, new QQProMessageFormater("", "", ""));
        this.formatters.put(MessageType.INFO, new QQProMessageFormater("", "", ""));
        this.formatters.put(MessageType.HELP, new QQProMessageFormater("", "", ""));
    }

    @Override
    public CommandContexts<?> getCommandContexts() {
        if (QQProCommandContexts == null) {
            QQProCommandContexts = new QQProCommandContexts(this, bot);
        }
        return QQProCommandContexts;
    }

    @Override
    public CommandCompletions<?> getCommandCompletions() {
        if (QQProCommandCompletions == null) {
            QQProCommandCompletions = new QQProCommandCompletions(this, bot);
        }
        return QQProCommandCompletions;
    }

    @Override
    public void registerCommand(BaseCommand command) {
        command.onRegister(this);
        for (Map.Entry<String, RootCommand> entry : command.registeredCommands.entrySet()) {
            String commandName = entry.getKey().toLowerCase(Locale.ENGLISH);
            QQProRootCommand qqProRootCommand = (QQProRootCommand) entry.getValue();
            bot.getBotCommandMap().registerCommand(qqProRootCommand.getCommandName(), qqProRootCommand);
            registeredCommands.put(commandName, qqProRootCommand);
        }
    }

    @Override
    public boolean hasRegisteredCommands() {
        return !registeredCommands.isEmpty();
    }

    @Override
    public boolean isCommandIssuer(Class<?> type) {
        return Message.class.isAssignableFrom(type);
    }

    @Override
    public QQProCommandIssuer getCommandIssuer(Object issuer) {
        if (!(issuer instanceof Message message)) {
            throw new RuntimeException("Error with non-message issuer");
        }
        return new QQProCommandIssuer(this, (Message) issuer, bot);
    }


    @Override
    public RootCommand createRootCommand(String cmd) {
        return new QQProRootCommand(this, cmd);
    }

    @Override
    public Locales getLocales() {
        if (this.locales == null) {
            this.locales = new QQProLocales(this);
            this.locales.loadLanguages();
        }
        return locales;
    }

    @Override
    public CommandExecutionContext createCommandContext(RegisteredCommand command, CommandParameter parameter, CommandIssuer sender, List<String> args, int i, Map<String, Object> passedArgs) {
        return new QQProCommandExecutionContext(bot, command, parameter, (QQProCommandIssuer) sender, args, i, passedArgs);
    }

    @Override
    public CommandCompletionContext createCompletionContext(RegisteredCommand command, CommandIssuer sender, String input, String config, String[] args) {
        return new QQProCommandCompletionContext(bot, command, (QQProCommandIssuer) sender, input, config, args);
    }

    @Override
    public void log(LogLevel level, String message, Throwable throwable) {
        switch (level) {
            case ERROR -> {
                bot.getBotAccess().getLogger().severe(message);
                if (throwable != null) {
                    throwable.printStackTrace();
                }
            }
            case INFO -> bot.getBotAccess().getLogger().info(message);
        }
    }

    @Override
    public Collection<RootCommand> getRegisteredRootCommands() {
        return Collections.unmodifiableCollection(registeredCommands.values());
    }


}
