package az.company.simplecrmtask.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String companyName;

    @NotBlank
    @Pattern(regexp = "^\\+994(50|51|55|70|77|99)\\d{7}$")
    private String phone;

}
