package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.util;


import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.UserEvent;

import java.beans.PropertyEditorSupport;

public class EventTypeConventer extends PropertyEditorSupport {

    public void setAsText(final String text) throws IllegalArgumentException {
        setValue(UserEvent.Type.valueOf(text.toUpperCase()));
    }

}