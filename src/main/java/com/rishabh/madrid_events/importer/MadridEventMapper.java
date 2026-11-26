package com.rishabh.madrid_events.importer;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Component
public class MadridEventMapper {

    private static final DateTimeFormatter MADRID_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Optional<ImportedEvent> toImportedEvent(MadridEvent raw) {
        String externalId = clean(raw.id());
        String title = limit(clean(raw.title()), 500);
        LocalDateTime start = parseDate(raw.dtstart());

        if (externalId == null || title == null || start == null) {
            return Optional.empty();
        }

        Double latitude = raw.location() == null ? null : raw.location().latitude();
        Double longitude = raw.location() == null ? null : raw.location().longitude();

        return Optional.of(new ImportedEvent(
                externalId,
                title,
                toCategory(raw.type()),
                limit(clean(raw.eventLocation()), 500),
                start,
                parseDate(raw.dtend()),
                Integer.valueOf(1).equals(raw.free()),
                clean(raw.description()),
                clean(raw.price()),
                clean(raw.link()),
                latitude,
                longitude
        ));
    }

    private static String clean(String value) {
        if (value == null) {
            return null;
        }
        String cleaned = value.replaceAll("\\s+", " ").trim();
        return cleaned.isEmpty() ? null : cleaned;
    }

    private static String limit(String value, int maxLength) {
        if (value == null || value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength);
    }

    private static LocalDateTime parseDate(String value) {
        String cleaned = clean(value);
        if (cleaned == null || cleaned.length() < 19) {
            return null;
        }
        try {
            return LocalDateTime.parse(cleaned.substring(0, 19), MADRID_DATE);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static String toCategory(String type) {
        if (type == null) {
            return "other";
        }
        int index = type.indexOf("/actividades/");
        String key = (index >= 0 ? type.substring(index + "/actividades/".length()) : type).toLowerCase();

        if (key.contains("musica")) return "music";
        if (key.contains("teatro")) return "theatre";
        if (key.contains("danza")) return "dance";
        if (key.contains("circo") || key.contains("magia")) return "circus";
        if (key.contains("cine")) return "cinema";
        if (key.contains("exposicion")) return "exhibition";
        if (key.contains("conferencia") || key.contains("congreso")) return "talk";
        if (key.contains("recital") || key.contains("literari") || key.contains("lectura")) return "literature";
        if (key.contains("curso") || key.contains("taller")) return "workshop";
        if (key.contains("cuentacuentos") || key.contains("titeres") || key.contains("infantil")
                || key.contains("campamento") || key.contains("escolar")) return "kids";
        if (key.contains("fiesta") || key.contains("navidad")) return "festival";
        if (key.contains("deport")) return "sport";
        if (key.contains("excursion") || key.contains("itinerario") || key.contains("visita")) return "tours";
        if (key.contains("calle") || key.contains("urbano")) return "street";
        if (key.contains("destacada")) return "featured";
        return "other";
    }
}