package com.cursor.library;

import lombok.Data;

@Data
public class CreateBookDto {

    private String name;
    private String author;
    private Integer year;
    private String genre;

}
