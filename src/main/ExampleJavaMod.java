package main;

import arc.*;
import arc.input.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;

import java.util.*;

public class ExampleJavaMod extends Mod {

    private BaseDialog adminDialog;

    public ExampleJavaMod(){
        Log.info("Loaded ExampleJavaMod constructor.");

        Events.on(ClientLoadEvent.class, e -> {
            adminDialog = buildAdminDialog();

            // Open by F8
            Events.run(Trigger.update, () -> {
                if(Core.input.keyTap(KeyCode.f8) && adminDialog != null){
                    adminDialog.show();
                }
            });

            // Loading hint
            Time.runTask(20f, () -> Vars.ui.showInfoToast("[accent]404AdminTool[]: " + t("404admin.hint.open", "F8"), 5f));
        });
    }

    private BaseDialog buildAdminDialog(){
        BaseDialog dialog = new BaseDialog("[ee0000]4[ee2800]0[ef5000]4[] [accent]" + t("404admin.title") + "[]");
        dialog.addCloseButton();

        dialog.cont.pane(root -> {
            root.defaults().pad(6f);

            // Top: level + nickname
            root.table(header -> {
                header.center();
                header.defaults().center().pad(2f);

                int level = Vars.player == null ? 0 : Vars.player.admin ? 999 : 1;
                String playerName = Vars.player == null ? "Unknown" : Vars.player.name;

                header.add("[lightgray]" + t("404admin.header.level") + ": [accent]" + level).row();
                header.add("[lightgray]" + t("404admin.header.nick") + ": [accent]" + playerName).row();
            }).growX().center().row();

            // Main block: left + right
            root.table(main -> {
                main.defaults().top().pad(4f);

                // Left: player stats
                main.table(left -> {
                    left.left().top();
                    left.defaults().left().pad(2f).growX();

                    addSectionTitle(left, t("404admin.section.stats"));
                    addInfoLine(left, t("404admin.stats.current-ip"), "95.27.148.130");
                    addInfoLine(left, t("404admin.stats.language"), Core.settings.getString("locale", "ru_RU"));
                    addInfoLine(left, t("404admin.stats.player-id"), Vars.player == null ? "-" : String.valueOf(Vars.player.id));
                    addInfoLine(left, t("404admin.stats.custom-client"), "false");
                    addInfoLine(left, t("404admin.stats.mobile-client"), Core.app.isMobile() ? "true" : "false");
                    addInfoLine(left, t("404admin.stats.join-count"), "4");
                    addInfoLine(left, t("404admin.stats.kick-count"), "1");
                    addInfoLine(left, t("404admin.stats.all-ips"), "95.27.148.130");
                    addInfoLine(left, t("404admin.stats.all-names"), Vars.player == null ? "Unknown" : Vars.player.name);
                }).width(420f).growY().top();

                // Right: 3 sections
                main.table(right -> {
                    right.top().left();
                    right.defaults().left().pad(4f).growX();

                    right.table(bans -> {
                        bans.left().defaults().left().pad(2f);
                        addSectionTitle(bans, t("404admin.section.bans"));
                        bans.add("griefing | id: 13").row();
                        bans.add("cheat-client | id: 25").row();
                    }).growX().row();

                    right.table(connects -> {
                        connects.left().defaults().left().pad(2f);
                        addSectionTitle(connects, t("404admin.section.connections"));
                        connects.add("19.03.2026 20:15").row();
                        connects.add("19.03.2026 20:42").row();
                        connects.add("19.03.2026 21:00").row();
                    }).growX().row();

                    right.table(alts -> {
                        alts.left().defaults().left().pad(2f);
                        addSectionTitle(alts, t("404admin.section.alts"));
                        alts.add("alt_nick_1 | id: 71").row();
                        alts.add("alt_nick_2 | id: 104").row();
                    }).growX().row();
                }).width(360f).growY().top();
            }).growX().top().row();
        }).grow().pad(10f);

        return dialog;
    }

    private void addSectionTitle(Table table, String title){
        table.add("[accent]" + title + "[]").left().row();
    }

    private void addInfoLine(Table table, String label, String value){
        table.add("[lightgray]" + label + ": []" + value).left().row();
    }

    private String t(String key){
        try{
            return Core.bundle.get(key);
        }catch(MissingResourceException ignored){
            return key;
        }
    }

    private String t(String key, Object... args){
        try{
            return Core.bundle.format(key, args);
        }catch(MissingResourceException ignored){
            return key;
        }
    }

    @Override
    public void loadContent(){
        Log.info("Loading 404AdminTool content.");
    }

}