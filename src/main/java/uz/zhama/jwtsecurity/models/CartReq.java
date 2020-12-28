package uz.zhama.jwtsecurity.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.zhama.jwtsecurity.entity.Product;
import uz.zhama.jwtsecurity.entity.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartReq {
    private User user;
    private Product product ;
    private Integer quantity;
}
