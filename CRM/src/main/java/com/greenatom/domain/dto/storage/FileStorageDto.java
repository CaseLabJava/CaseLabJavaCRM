package com.greenatom.domain.dto.storage;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileStorageDto {
    private String fileName;

    private String typeDocument;

    private Byte[] data;
}
