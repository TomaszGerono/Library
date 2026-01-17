package com.tg.library.gui.controller;

import com.tg.library.AppContext;
import com.tg.library.gui.view.OverviewViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.springframework.stereotype.Component;

@Component
public class OverviewController {
    private AppContext ctx;
    private OverviewViewModel vm;

    @FXML private Label totalBooksLabel;
    @FXML private Label totalShelvesLabel;
    @FXML private Label unreadLabel;
    @FXML private Label readingLabel;
    @FXML private Label completedLabel;

    @FXML private ListView<String> recommendationsList;

    public void setContext(AppContext ctx) { this.ctx = ctx; }

    public void afterContextInjected() {
        // TODO uncomment
//        this.vm = new OverviewViewModel(ctx.books(), ctx.topics());
//        vm.load();
//
//        totalBooksLabel.textProperty().bind(vm.totalBooks.asString());
//        totalShelvesLabel.textProperty().bind(vm.totalShelves.asString());
//        unreadLabel.textProperty().bind(vm.unread.asString());
//        readingLabel.textProperty().bind(vm.reading.asString());
//        completedLabel.textProperty().bind(vm.completed.asString());
//
//        recommendationsList.setItems(vm.recommendations);
    }
}

