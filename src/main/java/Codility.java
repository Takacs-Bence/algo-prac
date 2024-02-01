import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

class Sol {

    public static String solution(String S) {
        String[] lines = S.split(System.lineSeparator());

        List<Photo> photos = new ArrayList<>(lines.length);
        for (String line : lines) {
            String[] photoProps = line.split(", ");

            try {
                Photo photo = new Photo(photoProps[0], photoProps[1], photoProps[2]);
                photos.add(photo);
            } catch (ParseException e) {
                throw new IllegalStateException("input photo list contains illegal date format");
            }

        }
        PhotoAlbum album = new PhotoAlbum(photos);

        Map<String, List<Photo>> collect = album.photos.stream().collect(Collectors.groupingBy(Photo::getCity));
        collect.values().forEach(ph -> ph.sort(Comparator.comparing(Photo::getDate)));
        Set<String> cities = collect.keySet();

        // string format %d leading zeroes by length
        Map<UUID, String> result = new HashMap<>();
        List<Photo> ordered = new ArrayList<>(album.photos.size());
        for (String city : cities) {

            Integer counter = 1;
            for (Photo photo: collect.get(city)) {
                result.put(photo.getId(), city + counter.toString() + "." + photo.getExtension());
                counter++;
            }
        }

        // sort by photoOrderMap

        StringBuilder sb = new StringBuilder();
        result.forEach((k, v) -> {
            sb.append(v);
            sb.append("\n");
        });
        return sb.toString();
    }

    static class Photo {

        private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        private final UUID id;

        private final String name;
        private final String city;
        private final Date date;

        public Photo(String name, String city, String date) throws ParseException {
            this.name = name;
            this.city = city;
            this.date = dateFormat.parse(date);
            this.id = UUID.randomUUID();
        }

        public String getExtension() {
            String[] split = this.name.split("\\.");
            return split[split.length - 1];
        }

        public UUID getId() {
            return id;
        }

        public String getCity() {
            return city;
        }

        public String getName() {
            return name;
        }

        public Date getDate() {
            return date;
        }
    }

    static class PhotoAlbum {
        Map<UUID, Photo> photoOrderMap = new HashMap<>();
        List<Photo> photos;

        public PhotoAlbum(List<Photo> photos) {
            this.photos = photos;
        }
    }
}

public class Codility {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("./photo.txt"));

        StringBuilder sb = new StringBuilder();

        String line = bufferedReader.readLine();
        while (line != null) {
            sb.append(line);
            sb.append("\n");
            line = bufferedReader.readLine();
        }

        // group photos by city

        // sort photos by time

        // assign numbers from 1

        // rename all the photos - city + number assigned + extension

        // assigned numbers need to be same length - leading zeros

        System.out.println(Sol.solution(sb.toString()));

        bufferedReader.close();
    }
}
