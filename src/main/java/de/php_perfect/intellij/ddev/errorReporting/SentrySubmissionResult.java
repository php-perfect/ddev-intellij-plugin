package de.php_perfect.intellij.ddev.errorReporting;

import io.sentry.hints.SubmissionResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

class SentrySubmissionResult implements SubmissionResult {
    private Consumer<Boolean> onResult;

    public SentrySubmissionResult(@NotNull Consumer<Boolean> onResult) {
        this.onResult = onResult;
    }

    @Override
    public void setResult(boolean success) {
        if (this.onResult == null) {
            throw new IllegalStateException("result has already been set once");
        }

        this.onResult.accept(success);
        this.onResult = null;
    }

    @Override
    public boolean isSuccess() {
        throw new UnsupportedOperationException();
    }
}
