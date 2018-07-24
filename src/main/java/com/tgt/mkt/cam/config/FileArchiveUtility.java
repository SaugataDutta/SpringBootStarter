package com.tgt.mkt.cam.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


@Slf4j
public class FileArchiveUtility {

    private final Path zipLocation;
    private final Path outputDirectory;

    public FileArchiveUtility(Path zipLocation, Path outputDir) {
        this.zipLocation = zipLocation;
        this.outputDirectory = outputDir;
    }

    public String expandZipFile() {
        int unzipCount = 0;
        String fileName = StringUtils.EMPTY;
        try (ZipInputStream stream = new ZipInputStream(new FileInputStream(zipLocation.toFile()))) {
            log.info("Zip file: " + zipLocation.toFile().getName() + " has been opened");
            ZipEntry entry;
            while ((entry = stream.getNextEntry()) != null) {
                log.info("Matched file " + entry.getName());
                if (!entry.isDirectory()) {
                    extractFileFromArchive(stream, entry.getName());
                } else {
                    if (unzipCount == 0) {
                        fileName = entry.getName();
                    }
                    if (!Files.exists(Paths.get(outputDirectory + "/" + entry.getName()))) {
                        Files.createDirectories(Paths.get(outputDirectory + "/" + entry.getName()));
                    }
                }
                unzipCount++;
            }
        } catch (IOException ex) {
            log.error("Exception reading zip", ex);
        }
        return fileName;
    }

    private void extractFileFromArchive(ZipInputStream stream, String outputName) throws IOException {
        // build the path to the output file and then create the file
        String outpath = outputDirectory + "/" + outputName;
        Path currentPath = Paths.get(outpath);
        Path parent = currentPath.getParent();
        if (!parent.toFile().exists()) {
            Files.createDirectories(parent);
        }
        try (FileOutputStream output = new FileOutputStream(outpath)) {

            // create a buffer to copy through
            byte[] buffer = new byte[2048];
            // now copy out of the zip archive until all bytes are copied
            int len;
            while ((len = stream.read(buffer)) > 0) {
                output.write(buffer, 0, len);
            }
        } catch (IOException e) {
            log.error("Exception writing file", e);
        }
    }
}
