package team.hdt.blockadia.game_engine.client.gui.userInterfaces;

import java.util.ArrayList;
import java.util.List;

public class GuiButtonGroup {

    private List<GuiButton> buttons = new ArrayList<GuiButton>();
    private GuiButton currentlyActive = null;
    private boolean tabs = false;

    public GuiButtonGroup() {
    }

    public GuiButtonGroup(boolean tabs) {
        this.tabs = tabs;
    }

    public void addButton(final GuiButton button, boolean selected) {
        addButton(button);
        if (selected) {
            button.toggle();
        }
    }

    public void addButton(final GuiButton button) {
        if (tabs) {
            button.disableManualTurnOff();
        }
        buttons.add(button);
        button.addListener(new Listener() {
            @Override
            public void eventOccurred(boolean on) {
                if (on) {
                    select(button);
                } else {
                    currentlyActive = null;
                }
            }
        });
    }

    public List<GuiButton> getButtons() {
        return buttons;
    }

    private void select(GuiButton button) {
        turnOffCurrentlyActive();
        currentlyActive = button;
    }

    public void turnOffCurrentlyActive() {
        if (currentlyActive != null) {
            currentlyActive.toggle();
            currentlyActive = null;
        }
    }

    public boolean areAllOff() {
        for (GuiButton b : buttons) {
            if (b.isToggledOn()) {
                return false;
            }
        }
        return true;
    }


}
