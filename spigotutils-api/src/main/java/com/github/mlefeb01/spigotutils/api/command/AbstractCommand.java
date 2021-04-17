package com.github.mlefeb01.spigotutils.api.command;

import com.github.mlefeb01.spigotutils.api.object.Cooldown;
import com.github.mlefeb01.spigotutils.api.utils.TextUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Wraps CommandExecutor to make creating commands significantly easier.
 * @author Matt Lefebvre
 */
public abstract class AbstractCommand implements CommandExecutor {
    public static final CommandFormat DEFAULT_FORMAT = new CommandFormat(
            TextUtils.color("&6&m----------&6["),
            TextUtils.color("&6]&m----------"),
            TextUtils.color("&e&lCommand Help"),
            TextUtils.color("&e*"),
            TextUtils.color("&8-"),
            TextUtils.color("&f"),
            TextUtils.color("&6&m------------------------------------")
    );
    private final String alias;
    private final List<AbstractCommand> children;
    private final List<Parameter<?>> parameters;
    private final List<AbstractRequirement> requirements;
    private final Map<UUID, Cooldown> cooldownMap;
    private AbstractCommand parent;
    private String permission;
    private String description;
    private boolean hidden;
    private CommandFormat format;

    /**
     * Constructor
     * @param alias alias
     */
    public AbstractCommand(String alias) {
        this.alias = alias;
        this.children = new ArrayList<>();
        this.parameters = new ArrayList<>();
        this.requirements = new ArrayList<>();
        this.cooldownMap = new HashMap<>();
        this.parent = null;
        this.permission = null;
        this.description = null;
        this.hidden = false;
        this.format = null;
    }

    /**
     * Adds a child command to this command
     * @param child child
     */
    public final void addChild(AbstractCommand child) {
        child.parent = this;
        this.children.add(child);
    }

    /**
     * Adds a parameter to this command
     * @param type type
     * @param name name
     * @param <T> T
     */
    public final <T> void addParameter(AbstractParameterType<T> type, String name) {
        this.parameters.add(new Parameter<>(type, name));
    }

    /**
     * Adds a parameter to this command
     * @param type type
     * @param name name
     * @param defaultValue defaultValue
     * @param <T> T
     */
    public final <T> void addParameter(AbstractParameterType<T> type, String name, T defaultValue) {
        this.parameters.add(new Parameter<>(type, name, defaultValue));
    }

    /**
     * Adds a requirement to this command
     * @param requirement requirement
     */
    public final void addRequirement(AbstractRequirement requirement) {
        this.requirements.add(requirement);
    }

    /**
     * Adds a permission to this command
     * @param permission permission
     */
    public final void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * Returns the permission required to use this command or null
     * @return permission
     */
    public final String getPermission() {
        return permission;
    }

    /**
     * Set the description of this command
     * @param description description
     */
    public final void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set whether this command will be hidden. Hidden means that it will not be shown in the auto generated help
     * message even if the player has permission
     * @param hidden hidden
     */
    public final void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * Getter for the command's hidden status
     * @return return
     */
    public final boolean isHidden() {
        return hidden;
    }

    /**
     * Sets the commands format
     * @param format format
     */
    public final void setFormat(CommandFormat format) {
        this.format = format;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            // TODO change to support command families with deeper hierarchies?
            final Optional<AbstractCommand> child = args.length == 0 ? Optional.empty() : this.children.stream().filter(cmd -> cmd.getAlias().equalsIgnoreCase(args[0])).findFirst();
            final AbstractCommand toExecute = child.orElse(this);

            final String commandPerm = toExecute.permission;
            if (commandPerm != null && !sender.hasPermission(commandPerm)) {
                sender.sendMessage(TextUtils.color("&cNo permission."));
                return true;
            }

            for (AbstractRequirement requirement : toExecute.requirements) {
                if (!requirement.isMet(sender)) {
                    sender.sendMessage(TextUtils.color(requirement.getErrorMessage()));
                    return true;
                }
            }

            // Check for command cooldown, non-players (i.e. - console) can bypass this
            final int cooldownInSeconds = toExecute.getCooldownInSeconds();
            if (cooldownInSeconds > 0 && sender instanceof Player) {
                final UUID uuid = ((Player) sender).getUniqueId();
                final Cooldown cooldown = toExecute.cooldownMap.get(uuid);
                if (cooldown == null || cooldown.isOver()) {
                    toExecute.cooldownMap.put(uuid, new Cooldown(cooldownInSeconds));
                } else {
                    sender.sendMessage(TextUtils.color(String.format("&cThis command is on cooldown for %,.2fs", cooldown.calculateRemaining() / 1000.0)));
                    return true;
                }
            }

            toExecute.initialize(sender, args);
            if (child.isPresent()) {
                toExecute.currentArg++;
            }
            toExecute.execute();

        } catch (Exception e) {
            e.printStackTrace();
            sender.sendMessage(TextUtils.color(e.getMessage()));
        }
        return true;
    }

    /**
     * The commands alias
     * @return alias
     */
    public final String getAlias() {
        return this.alias;
    }

    /**
     * The commands cooldown (set to any value less than 1 to disable)
     * @return cooldown
     */
    public int getCooldownInSeconds() {
        return 0;
    }

    private void initialize(CommandSender sender, String[] args) {
        this.sender = sender;
        this.player = sender instanceof Player ? (Player) sender : null;
        this.currentParam = 0;
        this.currentArg = 0;
        this.rawArgs = args;
    }

    protected CommandSender sender;
    protected Player player;
    protected  int currentParam;
    protected int currentArg;
    protected String[] rawArgs;

    /**
     * Executes the command
     * @throws Exception exception
     */
    public void execute() throws Exception {
        sender.sendMessage(generateHelpCommand(sender));
    }

    /**
     * Automatically converts a raw argument (String) into its respective type
     * @param <T> t
     * @return parsed arg
     * @throws Exception exception
     */
    protected <T> T readArg() throws Exception {
        if (currentArg >= rawArgs.length) {
            throw new RuntimeException(TextUtils.color("&cNot enough args provided"));
        }
        T t;
        final Parameter<?> param = parameters.get(currentParam);
        try {
            t = (T) param.getType().fromString(sender, rawArgs[currentArg]);
        } catch (Exception e) {
            if (param.getDefaultValue() != null) {
                t = (T) param.getDefaultValue();
            } else {
                throw e;
            }
        }
        currentParam++;
        currentArg++;
        return t;
    }

    private String generateHelper(AbstractCommand command, CommandFormat commandFormat) {
        final StringBuilder builder = new StringBuilder();

        builder.append(commandFormat.getCommandPrefix());
        builder.append(" ");
        builder.append("/");

        if (command.parent != null) {
            builder.append(command.parent.alias);
            builder.append(" ");
        }
        builder.append(command.alias);
        builder.append(" ");

        for (Parameter<?> parameter : command.parameters) {
            builder.append(String.format("<%s>", parameter.getName()));
            builder.append(" ");
        }

        if (command.description != null) {
            builder.append(commandFormat.getSeparator());
            builder.append(" ");
            builder.append(commandFormat.getDescriptionPrefix());
            builder.append(command.description);
        }

        return builder.toString();
    }

    private String generateHelpCommand(CommandSender sender) {
        final StringBuilder builder = new StringBuilder();

        final CommandFormat commandFormat = format == null ? DEFAULT_FORMAT : format;
        builder.append(commandFormat.getHeaderLeft());
        builder.append(" ");
        builder.append(commandFormat.getTitle());
        builder.append(" ");
        builder.append(commandFormat.getHeaderRight());
        builder.append("\n");

        if (permission == null || sender.hasPermission(permission)) {
            builder.append(generateHelper(this, commandFormat));
        }

        for (AbstractCommand child : children) {
            if (child.hidden) {
                continue;
            }
            if (child.permission != null && !sender.hasPermission(child.permission)) {
                continue;
            }
            builder.append("\n");
            builder.append(generateHelper(child, commandFormat));
        }

        builder.append("\n");
        builder.append(commandFormat.getFooter());

        return builder.toString();
    }

}
