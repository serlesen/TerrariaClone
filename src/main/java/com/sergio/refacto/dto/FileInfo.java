package com.sergio.refacto.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileInfo {

    /**
     * Name of the file
     */
    String fileName;

    /**
     * Name of the file without the extension
     */
    String name;
}
