package de.php_perfect.intellij.ddev.actions;

import com.intellij.icons.AllIcons;

public class DdevStopAction extends DdevRunAction {
    public DdevStopAction() {
        super("Stop", AllIcons.Actions.Pause, "stop");
    }
}
