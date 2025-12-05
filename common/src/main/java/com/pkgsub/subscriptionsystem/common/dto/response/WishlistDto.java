package com.pkgsub.subscriptionsystem.common.dto.response;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class WishlistDto { Long id; Long packageId; Long userId; LocalDate createdAt; }