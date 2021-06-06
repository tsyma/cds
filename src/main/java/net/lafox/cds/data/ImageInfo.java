package net.lafox.cds.data;

import net.lafox.cds.service.exceptions.UnsupportedAlgorithmException;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ImageInfo {

    private String id;
    private String algorithm;
    private Integer width;
    private Integer height;
    private Integer quality;
    private String type;

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
