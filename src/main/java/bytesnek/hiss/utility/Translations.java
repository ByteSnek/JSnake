package bytesnek.hiss.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by SnakerBone on 13/11/2023
 **/
public class Translations
{
    public static String joinKey(String key, String artifactId, String exclusion)
    {
        IllegalArgumentException exception = new IllegalArgumentException("Stupid translation key");

        if (key.isEmpty()) {
            throw exception;
        }

        if (key.startsWith(exclusion)) {
            return key;
        }

        Stream<String> stream = Arrays.stream(key.split("\\."));
        List<String> pieces = new ArrayList<>(stream.toList());

        int joinIndex = 1;

        if (pieces.isEmpty()) {
            throw exception;
        }

        if (pieces.contains(artifactId)) {
            throw new IllegalArgumentException("Translation key does not need your artifact id");
        }

        if (pieces.size() != 2) {
            if (pieces.size() < 2) {
                joinIndex--;
            }
        }

        pieces.add(joinIndex, artifactId);

        return Strings.join(pieces, ".");
    }
}
