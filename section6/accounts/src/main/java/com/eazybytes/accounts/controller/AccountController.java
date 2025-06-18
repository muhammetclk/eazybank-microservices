package com.eazybytes.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.eazybytes.accounts.constants.AccountConstant;
import com.eazybytes.accounts.dto.AccountContactInfoDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;


@Tag(name = "Crud rest api for accounts in EazyBank", description = "Crud rest apis in EazyBank to Create,Fetch,Update,Delete accounts")
@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AccountController {

        @Autowired
        private IAccountService accountService;

        @Value("${build.version}")
        private String buildVersion;

        @Autowired
        private Environment environment;

        @Autowired
        private AccountContactInfoDto accountContactInfoDto;

        @Operation(summary = "Create Account Rest api", description = "Create Account Rest api in EazyBank to Create accounts")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "HTTP Status CREATED"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @PostMapping("/create")
        public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
                accountService.createAccount(customerDto);
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new ResponseDto(AccountConstant.STATUS_201, AccountConstant.MESSAGE_201));
        }

        @GetMapping("/fetch")
        @Operation(summary = "Fetch Account Details REST API", description = "REST API to fetch Customer &  Account details based on a mobile number")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        public ResponseEntity<CustomerDto> fetchAccountDetails(
                        @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
                CustomerDto customerDto = accountService.fetchAccountDetails(mobileNumber);
                return ResponseEntity.status(HttpStatus.OK).body(customerDto);
        }

        @Operation(summary = "Update Account Details REST API", description = "REST API to update Customer &  Account details based on a account number")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "417", description = "Expectation Failed"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @PutMapping("/update")
        public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
                boolean isUpdated = accountService.updateAccount(customerDto);
                if (isUpdated) {
                        return ResponseEntity
                                        .status(HttpStatus.OK)
                                        .body(new ResponseDto(AccountConstant.STATUS_200, AccountConstant.MESSAGE_200));
                } else {
                        return ResponseEntity
                                        .status(HttpStatus.EXPECTATION_FAILED)
                                        .body(new ResponseDto(AccountConstant.STATUS_417,
                                                        AccountConstant.MESSAGE_417_UPDATE));
                }
        }

        @Operation(summary = "Delete Account & Customer Details REST API", description = "REST API to delete Customer &  Account details based on a mobile number")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "417", description = "Expectation Failed"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @DeleteMapping("/delete")
        public ResponseEntity<ResponseDto> deleteAccountDetails(
                        @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
                boolean isDeleted = accountService.deleteAccount(mobileNumber);
                if (isDeleted) {
                        return ResponseEntity
                                        .status(HttpStatus.OK)
                                        .body(new ResponseDto(AccountConstant.STATUS_200, AccountConstant.MESSAGE_200));
                } else {
                        return ResponseEntity
                                        .status(HttpStatus.EXPECTATION_FAILED)
                                        .body(new ResponseDto(AccountConstant.STATUS_417,
                                                        AccountConstant.MESSAGE_417_DELETE));
                }
        }

        @Operation(summary = "Get Build Info REST API", description = "REST API to get build info")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @GetMapping("/build-info")
        public ResponseEntity<String> getBuildInfo() {
                return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
        }

        @Operation(summary = "Get Java version REST API", description = "REST API to get java version")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @GetMapping("/java-version")
        public ResponseEntity<String> getJavaVersion() {
                return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
        }

        @Operation(summary = "Get Contact info REST API", description = "REST API to get contact info")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @GetMapping("/contact-info")
        public ResponseEntity<AccountContactInfoDto> getContactInfo() {
                return ResponseEntity.status(HttpStatus.OK).body(accountContactInfoDto);
        }

}
