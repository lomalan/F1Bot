package com.lomalan.main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatementItem {
  private String id;
  private String time;
  private String description;
  private Long mcc;
  private Boolean hold;
  private BigDecimal amount;
  private BigDecimal operationAmount;
  private Long currencyCode;
  private Long commissionRate;
  private BigDecimal cashbackAmount;
  private BigDecimal balance;
  private String comment;
  private String receiptId;
  private String counterEdrpou;
  private String counterIban;
}
