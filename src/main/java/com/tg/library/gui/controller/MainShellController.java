package com.tg.library.gui.controller;

import com.tg.library.AppContext;
import javafx.event.ActionEvent;
import org.springframework.stereotype.Component;

@Component
public class MainShellController {
    private AppContext ctx;

    public void setContext(AppContext ctx) { this.ctx = ctx; }

    public void onImport() {
//        FileChooser fc = new FileChooser();
//        fc.setTitle("Import");
//        fc.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("CSV", "*.csv"),
//                new FileChooser.ExtensionFilter("JSON", "*.json")
//        );
//        File f = fc.showOpenDialog(Dialogs.ownerWindow());
//        if (f == null) return;
//
//        try {
//            if (f.getName().toLowerCase().endsWith(".csv")) ctx.importExport().importFromCsv(f);
//            else ctx.importExport().importFromJson(f);
//            Dialogs.info("Import zakończony", "Plik został zaimportowany.");
//        } catch (Exception e) {
//            Dialogs.error("Import nieudany", e.getMessage());
//        }
    }

    public void onExport() {
//        FileChooser fc = new FileChooser();
//        fc.setTitle("Export");
//        fc.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("CSV", "*.csv"),
//                new FileChooser.ExtensionFilter("JSON", "*.json")
//        );
//        File f = fc.showSaveDialog(Dialogs.ownerWindow());
//        if (f == null) return;
//
//        try {
//            if (f.getName().toLowerCase().endsWith(".csv")) ctx.importExport().exportToCsv(f);
//            else ctx.importExport().exportToJson(f);
//            Dialogs.info("Export zakończony", "Plik został zapisany.");
//        } catch (Exception e) {
//            Dialogs.error("Export nieudany", e.getMessage());
//        }
    }

    public void onLogout(ActionEvent actionEvent) {
        
    }
}
