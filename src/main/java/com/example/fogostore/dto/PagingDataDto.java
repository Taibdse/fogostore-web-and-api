package com.example.fogostore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagingDataDto<T> implements Serializable {
    private List<T> pages;
    private int current;
    private boolean next;
    private boolean prev;
}
