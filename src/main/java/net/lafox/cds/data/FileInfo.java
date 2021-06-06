package net.lafox.cds.data;

import java.io.File;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class FileInfo {
    private final File file;
    private final String id;
}
