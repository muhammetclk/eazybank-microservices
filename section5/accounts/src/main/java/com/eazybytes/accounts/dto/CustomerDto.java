package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "CustomerDto", description = "Customer Details")
public class CustomerDto {
    

    @Schema(
        description = "Name of the customer", example = "Eazy Bytes"
)
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, message = "Name should have at least 2 characters")
    private String name;

    @Schema(
        description = "Email address of the customer", example = "tutor@eazybytes.com"
)
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(
        description = "Mobile Number of the customer", example = "9345432123"
)
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Account details of the Customer"
    )
    private AccountDto accountDto;


}
