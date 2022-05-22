package com.example.eBankBackEnd.dtos;

import com.example.eBankBackEnd.entities.BankAccount;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
public class CustomerDTO {

    private Long id;
    private String name;
    private String email;


}
