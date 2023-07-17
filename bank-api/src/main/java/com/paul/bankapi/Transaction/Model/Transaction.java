package com.paul.bankapi.Transaction.Model;

import com.paul.bankapi.Account.Model.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer amount;
    private String transactionId;
    private TransactionType transactionType;

    public void setTransactionId() {
        this.transactionId = "tran" + String.format("%03d", id);
    }
}
