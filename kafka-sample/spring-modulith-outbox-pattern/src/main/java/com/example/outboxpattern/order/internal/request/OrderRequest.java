package com.example.outboxpattern.order.internal.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record OrderRequest(@NotEmpty(message = "ItemsList must not be empty") List<OrderItemRequest> itemsList) {}
