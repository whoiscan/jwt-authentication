package uz.zhama.jwtsecurity.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponse {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private double amount;
    private boolean status;
    private String userName;
    private List<String> products;
}
