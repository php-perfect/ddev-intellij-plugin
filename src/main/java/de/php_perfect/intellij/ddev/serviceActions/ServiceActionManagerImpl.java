package de.php_perfect.intellij.ddev.serviceActions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.extensions.PluginId;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import de.php_perfect.intellij.ddev.actions.OpenServiceAction;
import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.cmd.Service;
import de.php_perfect.intellij.ddev.state.State;
import de.php_perfect.intellij.ddev.util.MapValueChanger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ServiceActionManagerImpl implements ServiceActionManager, Disposable {
    private static final PluginId PLUGIN_ID = PluginId.findId("de.php_perfect.intellij.ddev");
    private static final @NotNull Logger LOGGER = Logger.getLogger(ServiceActionManagerImpl.class.getName(), DdevIntegrationBundle.getName());
    private static final @NotNull String ACTION_PREFIX = "DdevIntegration.Services.";

    private final @NotNull Map<@NotNull String, @NotNull AnAction> actionMap = new HashMap<>();

    public synchronized AnAction @NotNull [] getServiceActions() {
        return this.actionMap.values().toArray(new AnAction[0]);
    }

    @Override
    public void updateActionsByState(@NotNull State state) {
        final Description description = state.getDescription();

        if (description == null) {
            return;
        }

        final Map<@NotNull String, @NotNull AnAction> newActionsMap = new HashMap<>();
        final Map<String, Service> serviceMap = state.getDescription().getServices();
        if (description.getMailHogHttpsUrl() != null || description.getMailHogHttpUrl() != null) {
            serviceMap.put("mailhog", new Service("ddev-config-test-mailhog", description.getMailHogHttpsUrl(), description.getMailHogHttpUrl()));
        }

        for (Map.Entry<String, Service> entry : serviceMap.entrySet()) {
            String fullName = entry.getValue().getFullName();
            URL url;
            try {
                url = extractServiceUrl(entry.getValue());
            } catch (MalformedURLException exception) {
                LOGGER.log(Level.WARNING, String.format("Skipping open action for service %s because of its invalid URL", fullName), exception);
                continue;
            }

            if (url == null) {
                continue;
            }

            final String actionId = ACTION_PREFIX + fullName;
            final AnAction action = buildAction(entry.getKey(), url, fullName);
            newActionsMap.put(actionId, action);
        }

        ActionManager actionManager = ActionManager.getInstance();

        synchronized (this) {
            MapValueChanger.apply(this.actionMap, newActionsMap, (String actionId, AnAction action) -> actionManager.unregisterAction(actionId), (String actionId, AnAction action) -> actionManager.registerAction(actionId, action, PLUGIN_ID));
        }
    }

    private @Nullable URL extractServiceUrl(Service service) throws MalformedURLException {
        String address = service.getHttpsUrl();
        if (address == null) {
            address = service.getHttpUrl();
        }

        if (address != null) {
            return new URL(address);
        }

        return null;
    }

    private @NotNull AnAction buildAction(String key, URL url, String fullName) {
        final String text = buildActionText(key, fullName);
        final String descriptionText = DdevIntegrationBundle.message("action.services.open.description", fullName);
        return new OpenServiceAction(url, text, descriptionText, AllIcons.General.Web);
    }

    private @NotNull String buildActionText(String key, String fullName) {
        switch (key) {
            case "dba":
                return DdevIntegrationBundle.message("action.services.open.dba");
            case "web":
                return DdevIntegrationBundle.message("action.services.open.web");
            case "mailhog":
                return DdevIntegrationBundle.message("action.services.open.mailHog");
            default:
                return DdevIntegrationBundle.message("action.services.open.any", fullName);
        }
    }

    @Override
    public void dispose() {
        for (String actionId : this.actionMap.keySet()) {
            ActionManager.getInstance().unregisterAction(actionId);
        }
    }
}
