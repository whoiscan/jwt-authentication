package uz.zhama.jwtsecurity.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDelReq {
    private Integer userId;
    private Integer productId;
}
