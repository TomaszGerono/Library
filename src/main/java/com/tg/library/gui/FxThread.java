package com.tg.library.gui;

import javafx.application.Platform;

public final class FxThread {
    private FxThread() {}

    public static void runOnFx(Runnable r) {
        if (Platform.isFxApplicationThread()) r.run();
        else Platform.runLater(r);
    }
}
