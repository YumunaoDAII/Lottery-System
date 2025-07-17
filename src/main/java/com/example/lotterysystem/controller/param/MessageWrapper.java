package com.example.lotterysystem.controller.param;

import lombok.Data;

@Data
public class MessageWrapper {
    private String messageId;
    private DrawPrizeParam messageData;
}
