package de.ustutt.iaas.bpmn2bpel;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2015 IAAS University of Stuttgart <br>
 * <br>
 * 
 * Utility class providing operations on files.
 * 
 * @author Sebastian Wagner
 * 		
 */
public class FileUtil {
	
	public static Path createTempDir(String subdirectory) throws IOException {
		
		String systemTmpDir = System.getProperty("java.io.tmpdir");
		Path tempDirPath = Paths.get(systemTmpDir, subdirectory);
		if (Files.notExists(tempDirPath)) {
			return Files.createDirectory(tempDirPath);
		} else {
			return tempDirPath;
		}
		
	}
	
	public static Path writeStringToFile(String content, Path targetPath) throws IOException {
		return Files.write(targetPath, content.getBytes(), StandardOpenOption.CREATE);
	}
	
	public static Path createApacheOdeProcessArchive(Path zipFilePath, List<Path> filePaths) throws IOException {
		
		URI uri = URI.create("jar:file:" + zipFilePath.toUri().getPath());
		
		Map<String, String> env = new HashMap<String, String>();
		env.put("create", "true");
		
		try (FileSystem zipFileSystem = FileSystems.newFileSystem(uri, env)) {
			
			/* Iterate over files and add them to the zip */
			for (Path src : filePaths) {
				if (!Files.isDirectory(src)) {
					Path dest = zipFileSystem.getPath(src.getFileName().toString());
					Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
				}	
			}
		}
		return zipFilePath;
	}
	
}
