package co.test.springbootrabbitmq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String firstname;
    private String lastname;
    private String email;
    private int age;
    private String mobileNumber;
    private String address;
}
