package net.lafox.cds.data;

import java.io.File;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class FileInfo {
    File file;
    String id;
}
