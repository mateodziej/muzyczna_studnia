package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.commons;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Type;

@Data
@AllArgsConstructor
public class TypedPage<T> {

    private Page<T> page;
    private Type type;

    public T getPageEmbedded(){
        return page != null ? page.getEmbedded() : null;
    }

    public Link getNextPageUrl(){
        if(page == null) return null;
        return page.getLinks().getNext();
    }

    public Link getPrevPageUrl() {
        if(page == null) return null;
        return page.getLinks().getPrev();
    }
}
