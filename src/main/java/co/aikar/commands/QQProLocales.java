package co.aikar.commands;

import co.aikar.commands.CommandManager;
import co.aikar.commands.Locales;

public class QQProLocales extends Locales {

    private CommandManager manager;
    public QQProLocales(CommandManager manager) {
        super(manager);
        this.manager = manager;
    }

    @Override
    public void loadLanguages() {
        super.loadLanguages();
        addMessageBundles("acf-minecraft","acf-core");
    }
}
