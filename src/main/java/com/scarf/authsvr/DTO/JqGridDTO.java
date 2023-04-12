package com.scarf.authsvr.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class JqGridDTO<T> {
    private @Getter @Setter List<T> rows;
}
