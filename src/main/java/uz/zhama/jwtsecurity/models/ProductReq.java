package uz.zhama.jwtsecurity.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.zhama.jwtsecurity.entity.Category;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReq {
    @NotNull
    private String name;
    @NotNull
    private Integer price;
    private String description;
    private Category category;

}