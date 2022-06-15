package de.php_perfect.intellij.ddev.tutorial;

import com.intellij.openapi.Disposable;
import com.intellij.ui.GotItTooltip;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import de.php_perfect.intellij.ddev.icons.DdevIntegrationIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;

public final class GotItTutorialImpl implements GotItTutorial {
    private static final @NotNull String ID_PREFIX = "ddev.features.";

    @Override
    public void showStatusBarTutorial(@NotNull JComponent component, @NotNull Disposable disposable) {
        try {
            new GotItTooltip(ID_PREFIX + "status", DdevIntegrationBundle.message("tutorial.status.text"), disposable)
                    .withHeader(DdevIntegrationBundle.message("tutorial.status.title"))
                    .withIcon(DdevIntegrationIcons.DdevLogoColor)
                    .withBrowserLink(DdevIntegrationBundle.message("tutorial.status.link"), new URL("https://github.com/php-perfect/ddev-intellij-plugin/wiki/Features#quick-access-to-ddev-services"))
                    .show(component, GotItTooltip.TOP_MIDDLE);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void showTerminalTutorial(@NotNull JComponent component, @NotNull Disposable disposable) {
        try {
            new GotItTooltip(ID_PREFIX + "terminal", DdevIntegrationBundle.message("tutorial.terminal.text"), disposable)
                    .withHeader(DdevIntegrationBundle.message("tutorial.terminal.title"))
                    .withIcon(DdevIntegrationIcons.DdevLogoColor)
                    .withBrowserLink(DdevIntegrationBundle.message("tutorial.terminal.link"), new URL("https://github.com/php-perfect/ddev-intellij-plugin/wiki/Features#integrated-ddev-terminal"))
                    .show(component, GotItTooltip.BOTTOM_MIDDLE);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
