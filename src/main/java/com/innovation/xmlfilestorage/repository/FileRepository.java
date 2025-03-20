package com.innovation.xmlfilestorage.repository;

import java.nio.file.Path;
import java.util.List;;

public interface FileRepository {

    boolean delete(String fileName);

    void save(Path filePath, String file);

    String findBy(String fileName);

    List<String> findBy(String customer, String type, String date);

}
