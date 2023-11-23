package com.greenatom.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class EntityPage {
    private int pageNumber = 0;
    private int pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "id";

    public EntityPage(int pageNumber, int pageSize, Sort.Direction sortDirection, String sortBy) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortDirection = sortDirection;
        this.sortBy = sortBy;
    }
}
