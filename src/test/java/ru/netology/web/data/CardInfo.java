package ru.netology.web.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CardInfo {
    private String id;
    private String number;
    private int balance;
}
