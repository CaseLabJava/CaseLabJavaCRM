package com.greenatom.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadDocDTO {

    private Long id;

    private String linkToFolder;

    private String fileName;

    private String filePath;

}
