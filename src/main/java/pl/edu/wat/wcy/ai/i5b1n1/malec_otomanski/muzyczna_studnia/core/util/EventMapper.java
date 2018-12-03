package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.util;

import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.StoredEvent;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.commons.Image;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.event.Event;

public class EventMapper {

    private static final int IMAGE_WIDTH = 1024;

    public static StoredEvent map(Event event) {
        return StoredEvent.builder()
                .ticketmasterId(event.getId())
                .imageUrl(getImageUrlAboveSize(event, IMAGE_WIDTH))
                .currency(event.getPriceRanges().get(0).getCurrency())
                .priceMin(event.getPriceRanges().get(0).getMin())
                .priceMax(event.getPriceRanges().get(0).getMax())
                .saleStartDate(event.getSales().getPublicSale().getStartDateTime())
                .saleEndDate(event.getSales().getPublicSale().getEndDateTime())
                .localDate(event.getDates().getStart().getLocalDate())
                .localTime(event.getDates().getStart().getLocalTime())
                .dateTBA(event.getDates().getStart().isDateTBA())
                .dateTBD(event.getDates().getStart().isDateTBD())
                .genreName(event.getClassifications().get(0).getGenre().getName())
                .subGenreName(event.getClassifications().get(0).getSubGenre().getName())
                .name(event.getName())
                .venueName(event.getEmbedded().getVenues().get(0).getName())
                .venueCity(event.getEmbedded().getVenues().get(0).getCity().getName())
                .venueAddress(event.getEmbedded().getVenues().get(0).getAddress().getLine1())
                .build();
    }

    private static String getImageUrlAboveSize(Event event, int size){
        Image retVal = null;
        for(Image image : event.getImages()){
            if(image.getWidth() >= size){
                retVal = image;
                break;
            }
        }
        return retVal.getUrl();
    }
}
