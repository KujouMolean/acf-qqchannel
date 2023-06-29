package co.aikar.commands;

public class QQProMessageFormater extends MessageFormatter<Object> {
    public QQProMessageFormater(Object... colors) {
        super(colors);
    }

    @Override
    String format(Object color, String message) {
        return message;
    }
}
