package az.company.simplecrmtask.models.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class CustomerResponse {

    private Long id;

    private String companyName;

    private String name;

    private String phone;

}
