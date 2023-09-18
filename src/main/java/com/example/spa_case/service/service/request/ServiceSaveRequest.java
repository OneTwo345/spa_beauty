package com.example.spa_case.service.service.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ServiceSaveRequest {
    private String name;

    private String price;

    private String description;

    private String poster;

    private List<String> image;

}