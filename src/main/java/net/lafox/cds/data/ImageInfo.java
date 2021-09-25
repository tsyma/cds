package net.lafox.cds.data;

import lombok.Builder;
import lombok.Value;
import net.lafox.cds.service.exceptions.UnsupportedAlgorithmException;

@Value
@Builder(toBuilder = true)
public class ImageInfo {

    String id;
    String algorithm;
    Integer width;
    Integer height;
    Integer quality;
    String type;

    @Override
    public String toString() {
        String idStr = id == null ? "" : id + "-";
        switch (algorithm) {
            case "h":
                return idStr + algorithm + height + "q" + quality + "." + type;
            case "w":
                return idStr + algorithm + width + "q" + quality + "." + type;
            case "c":
                return idStr + algorithm + width + 'x' + height + "q" + quality + "." + type;
            default:
                throw new UnsupportedAlgorithmException(algorithm);
        }
    }

}
